/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatrmi.client;

import chatrmi.common.ChatClient;
import chatrmi.common.Message;
import chatrmi.server.Server;
import java.rmi.RemoteException;

/**
 *
 * @author kadir
 */
public class BotClient implements ChatClient {

    private String username = "HelperBot";

    private Server server;

    public BotClient(Server server) {
        this.server = server;
    }

    @Override
    public void notifyNewMessage(Message message) throws RemoteException {
        server.sendMessage(this, message.getFrom(), message.getMessage());
    }

    @Override
    public void notifyLogin(String username) {

    }

    @Override
    public void notifyLogout(String username) {

    }

    @Override
    public String getName() throws RemoteException {
        return username;
    }

}
