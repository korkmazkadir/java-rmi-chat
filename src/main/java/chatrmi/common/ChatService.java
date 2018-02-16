/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatrmi.common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author kadir
 */
public interface ChatService extends Remote {

    public String[] registerClient(ChatClient client) throws RemoteException;

    public void unregisterClient(ChatClient client) throws RemoteException;

    public void sendMessage(ChatClient from, String to, String message) throws RemoteException;
    
    public void sendMessageBroadcast(ChatClient from, List<String> to, String message) throws RemoteException;

    public List<Message> getMessageHistory(ChatClient client, String otherClientName) throws RemoteException;

}
