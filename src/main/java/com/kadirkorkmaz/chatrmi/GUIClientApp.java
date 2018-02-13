/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kadirkorkmaz.chatrmi;

import com.kadirkorkmaz.chatrmi.client.gui.ClientForm;
import com.kadirkorkmaz.chatrmi.client.gui.CommunicationProvider;
import com.kadirkorkmaz.chatrmi.client.gui.LoginDialog;
import com.kadirkorkmaz.chatrmi.common.ChatClient;
import com.kadirkorkmaz.chatrmi.common.ChatService;
import com.kadirkorkmaz.chatrmi.common.Message;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

/**
 *
 * @author kadir
 */
public class GUIClientApp implements ChatClient, CommunicationProvider {

    private final LoginDialog loginForm;
    private ClientForm clientForm;

    private String serverAddress;
    private String username;

    private ChatClient clientStub;
    private ChatService chatService;

    public GUIClientApp() {
        loginForm = new LoginDialog();
        clientForm = new ClientForm(this);

        loginForm.setLocationRelativeTo(null);
        clientForm.setLocationRelativeTo(null);
    }

    public void run() {

        loginForm.setVisible(true);
        serverAddress = loginForm.getServeAddress();
        username = loginForm.getUsername();

        System.out.println("Server Address : " + serverAddress);
        System.out.println("Username : " + username);

        clientForm.setUsername(username);
        clientForm.setVisible(true);

        connectToServer();

        clientForm.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(WindowEvent winEvt) {
                try {
                    chatService.unregisterClient(clientStub);
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }
            }

        });

    }

    private void connectToServer() {

        try {

            System.out.println("Connecting to server : " + serverAddress);

            Registry registry = LocateRegistry.getRegistry(serverAddress, 2020);

            if (registry == null) {
                System.out.println("Registry is null :(");
            }

            chatService = (ChatService) registry.lookup("ChatService");
            clientStub = (ChatClient) UnicastRemoteObject.exportObject(this, 0);

            chatService.registerClient(clientStub);

            //chatService.unregisterClient(clintStub);
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }

    }

    @Override
    public void notifyNewMessage(Message message) throws RemoteException {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                clientForm.notifyNewMessage(message);
            }
        });
    }

    @Override
    public void notifyUserListUpdate(String[] currentUserNames) throws RemoteException {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                clientForm.updateUserList(currentUserNames);
            }
        });
    }

    @Override
    public void notifyLogin(String username) {
        System.out.println("---> New user : " + username);
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                clientForm.userLogin(username);
            }
        });
    }

    @Override
    public void notifyLogout(String username) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                clientForm.userLogout(username);
            }
        });
    }

    @Override
    public String getName() throws RemoteException {
        return username;
    }

    @Override
    public void sendMessage(String receiverName, String message) {
        try {
            chatService.sendMessage(clientStub, receiverName, message);
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List<Message> loadMessages(String userName) {
        try {
            return chatService.getMessageHistory(clientStub, userName);
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        GUIClientApp client = new GUIClientApp();
        client.run();
    }

}
