/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatrmi.client;

import chatrmi.common.ChatClient;
import chatrmi.common.Message;

/**
 *
 * @author kadir
 */
public class CommandLineClient implements ChatClient {

    private final String name;

    public CommandLineClient(String name) {
        this.name = name;
    }

    @Override
    public void notifyNewMessage(Message message) {
        System.out.println(message);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void notifyLogin(String username) {
        System.out.println("User login : " + username);
    }

    @Override
    public void notifyLogout(String username) {
        System.out.println("User logout : " + username);
    }

}
