/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kadirkorkmaz.chatrmi;

import com.kadirkorkmaz.chatrmi.client.BotClient;
import com.kadirkorkmaz.chatrmi.common.ChatClient;
import com.kadirkorkmaz.chatrmi.common.ChatService;
import com.kadirkorkmaz.chatrmi.database.DBManager;
import com.kadirkorkmaz.chatrmi.server.Server;
import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author kadir
 */
public class ServerApp {

    public static void main(String[] args) throws IOException {

        Runtime.getRuntime().exec("rmiregistry 2020");
        LocateRegistry.createRegistry(2020);

        DBManager.OpenDB();

        try {
            Server chatServer = new Server();
            ChatService stub = (ChatService) UnicastRemoteObject.exportObject(chatServer, 0);

            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.getRegistry(2020);
            registry.bind("ChatService", stub);

            BotClient bot = new BotClient(chatServer);
            ChatClient bot_stub = (ChatClient) UnicastRemoteObject.exportObject(bot, 0);
            stub.registerClient(bot_stub);
            
            System.err.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }

        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                System.out.println("Shutdown Hook is running !");
                DBManager.CloseDB();
            }
        });

    }

}
