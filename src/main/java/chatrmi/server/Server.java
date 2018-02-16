/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatrmi.server;

import chatrmi.common.ChatClient;
import chatrmi.common.ChatService;
import chatrmi.common.Message;
import chatrmi.database.FileRecorder;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author kadir
 */
public class Server implements ChatService {

    private final Map<String, List<ChatClient>> nameClientObjetMap;
    private final List<Message> messageList;

    private final static String BROATCAST_CLIENT = "Broadcast";

    public Server() {
        nameClientObjetMap = new LinkedHashMap<>();
        messageList = FileRecorder.loadPreviosMessages();
        System.out.println("Message list size loaded from DB : " + messageList.size());
    }

    @Override
    public String[] registerClient(ChatClient client) throws RemoteException {

        final String clientName = client.getName();
        System.out.println("Registered client : " + clientName);
        notifyLogin(clientName);

        if (!nameClientObjetMap.containsKey(client.getName())) {
            nameClientObjetMap.put(clientName, new LinkedList<>());
        }
        nameClientObjetMap.get(clientName).add(client);

        Set<String> currentUsers = nameClientObjetMap.keySet();
        return currentUsers.toArray(new String[currentUsers.size()]);
    }

    @Override
    public void unregisterClient(ChatClient client) throws RemoteException {
        System.out.println("Unregistered client : " + client.getName());
        final String clientName = client.getName();
        nameClientObjetMap.remove(clientName);
        notifyLogout(clientName);
    }

    @Override
    public void sendMessage(ChatClient from, String to, String message) throws RemoteException {
        System.out.println(from.getName() + " - " + to + " message : " + message);
        List<ChatClient> recievers = nameClientObjetMap.get(to);
        if (recievers != null) {
            Message m = new Message(from.getName(), to, new Date(), message, false);
            messageList.add(m);
            FileRecorder.appendNewMessage(m);

            //Sending same message to sender :)
            if (from.getName().equals(to) == false && !(from.getName().equals("HelperBot"))) {
                from.notifyNewMessage(m);
            }

            for (ChatClient reciever : recievers) {
                reciever.notifyNewMessage(m);
            }

        }
    }

    @Override
    public List<Message> getMessageHistory(ChatClient client, String otherClientName) throws RemoteException {
        String clientName = client.getName();
        return getMessagesBetween(clientName, otherClientName);
    }

    private void notifyLogin(String loginUsername) throws RemoteException {

        if (nameClientObjetMap.containsKey(loginUsername)) {
            System.out.println("User is already here, no need to notify");
            //no need to de something
            return;
        }

        System.out.println("login notify : " + loginUsername);

        Iterator<Map.Entry<String, List<ChatClient>>> clientIterator = nameClientObjetMap.entrySet().iterator();
        while (clientIterator.hasNext()) {
            Map.Entry<String, List<ChatClient>> pair = clientIterator.next();
            List<ChatClient> recievers = pair.getValue();
            for (ChatClient reciever : recievers) {
                reciever.notifyLogin(loginUsername);
            }
        }
    }

    private void notifyLogout(String logoutUsername) throws RemoteException {

        if (nameClientObjetMap.containsKey(logoutUsername)) {
            System.out.println("User is already here, no need to notify");
            return;
        }

        System.out.println("logout notify : " + logoutUsername);

        Iterator<Map.Entry<String, List<ChatClient>>> clientIterator = nameClientObjetMap.entrySet().iterator();
        while (clientIterator.hasNext()) {
            Map.Entry<String, List<ChatClient>> pair = clientIterator.next();
            List<ChatClient> recievers = pair.getValue();
            for (ChatClient reciever : recievers) {
                reciever.notifyLogout(logoutUsername);
            }
        }
    }

    @Override
    public void broadcastMessage(ChatClient from, String message) throws RemoteException {
        System.out.println(from.getName() + " - BROATCAST - message : " + message);

        //To save file and insert into list
        Message m = new Message(from.getName(), BROATCAST_CLIENT, new Date(), message, true);
        FileRecorder.appendNewMessage(m);
        messageList.add(m);

        Iterator<Map.Entry<String, List<ChatClient>>> clientIterator = nameClientObjetMap.entrySet().iterator();
        while (clientIterator.hasNext()) {
            Map.Entry<String, List<ChatClient>> pair = clientIterator.next();
            List<ChatClient> recievers = pair.getValue();
            for (ChatClient reciever : recievers) {
                m = new Message(from.getName(), reciever.getName(), new Date(), message, true);
                reciever.notifyNewMessage(m);
            }
        }

    }

    @Override
    public String[] getContactList() throws RemoteException {

        return null;
    }

    private List<Message> getMessagesBetween(String user1, String user2) {
        List<Message> messages;

        if (user1.equals(BROATCAST_CLIENT) || user2.equals(BROATCAST_CLIENT)) {

            messages = messageList.stream()
                    .filter(m -> m.isBroadcastMessage()).collect(Collectors.toList());
            System.out.println("Sending broadcast messages size : " + messages.size());
        } else {
            messages = messageList.stream()
                    .filter(m -> (m.getFrom().equals(user1) && m.getTo().equals(user2))
                    || (m.getFrom().equals(user2) && m.getTo().equals(user1))).collect(Collectors.toList());
        }

        return messages;
    }

}
