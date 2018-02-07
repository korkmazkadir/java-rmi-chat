/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kadirkorkmaz.chatrmi.client;

import com.kadirkorkmaz.chatrmi.common.ChatClient;
import com.kadirkorkmaz.chatrmi.common.Message;
import com.kadirkorkmaz.chatrmi.server.Server;
import java.rmi.RemoteException;

/**
 *
 * @author kadir
 */
public class BotClient implements ChatClient {

    private String username = "HelperBot";
    
    private Server  server;
    
    public BotClient(Server server) {
        this.server = server;
    }

    @Override
    public void notifyNewMessage(Message message) throws RemoteException {
        server.sendMessage(this, message.getFrom(), message.getMessage());
    }

    @Override
    public void notifyUserListUpdate(String[] currentUserNames) throws RemoteException {
        
    }

    @Override
    public String getName() throws RemoteException {
        return username;
    }

}
