/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kadirkorkmaz.chatrmi.client;

import com.kadirkorkmaz.chatrmi.common.ChatClient;
import com.kadirkorkmaz.chatrmi.common.Message;

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
    public void notifyUserListUpdate(String[] currentUserNames) {
        System.out.println("---------- User List Updated ----------");
        for (String currentUserName : currentUserNames) {
            System.out.println(currentUserName);
        }
        System.out.println("---------------------------------------");
    }

    @Override
    public String getName() {
        return this.name;
    }

}
