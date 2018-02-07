/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kadirkorkmaz.chatrmi.server;

import com.kadirkorkmaz.chatrmi.common.ChatClient;
import com.kadirkorkmaz.chatrmi.common.ChatService;
import com.kadirkorkmaz.chatrmi.common.Message;
import com.kadirkorkmaz.chatrmi.database.DBManager;
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

    public Server() {
        nameClientObjetMap = new LinkedHashMap<>();
        messageList = DBManager.GetPersistentMessageList();
        System.out.println("Message list size : " + messageList.size());
    }

    @Override
    public void registerClient(ChatClient client) throws RemoteException {
        final String clientName = client.getName();
        System.out.println("Registered client : " + clientName);
        if(!nameClientObjetMap.containsKey(client.getName())){
            nameClientObjetMap.put(clientName, new LinkedList<>());
        }
        nameClientObjetMap.get(clientName).add(client);
        
        notifyUsers();
    }

    @Override
    public void unregisterClient(ChatClient client) throws RemoteException {
        System.out.println("Unregistered client : " + client.getName());
        nameClientObjetMap.remove(client.getName());
        notifyUsers();
    }

    @Override
    public void sendMessage(ChatClient from, String to, String message) throws RemoteException {
        System.out.println("Sending message");
        List<ChatClient> recievers = nameClientObjetMap.get(to);
        if (recievers != null) {
            Message m = new Message(from.getName(), to, new Date(), message);
            messageList.add(m);
            for (ChatClient reciever : recievers) {
                reciever.notifyNewMessage(m);
            }
            
            //Sending same message to sender :)
            if (from.getName().equals(to) == false && !(from.getName().equals("HelperBot") || to.equals("HelperBot"))) {
                from.notifyNewMessage(m);
            }
        }
    }

    @Override
    public List<Message> getMessageHistory(ChatClient client, String otherClientName) throws RemoteException {
        String clientName = client.getName();
        return getMessagesBetween(clientName, otherClientName);
    }

    private void notifyUsers() throws RemoteException {
        Set<String> userNameSet = nameClientObjetMap.keySet();
        String[] availableUserNames = userNameSet.toArray(new String[userNameSet.size()]);
        Iterator<Map.Entry<String, List<ChatClient>>> clientIterator = nameClientObjetMap.entrySet().iterator();
        while (clientIterator.hasNext()) {
            Map.Entry<String, List<ChatClient>> pair = clientIterator.next();
            List<ChatClient> recievers = pair.getValue();
            for (ChatClient reciever : recievers) {
                reciever.notifyUserListUpdate(availableUserNames);
            }
        }
    }

    private List<Message> getMessagesBetween(String user1, String user2) {
        List<Message> messages = messageList.stream()
                .filter(m -> (m.getFrom().equals(user1) && m.getTo().equals(user2))
                || (m.getFrom().equals(user2) && m.getTo().equals(user1))).collect(Collectors.toList());
        return messages;
    }

}
