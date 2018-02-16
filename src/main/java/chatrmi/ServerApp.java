/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatrmi;

import chatrmi.client.BotClient;
import chatrmi.common.ChatClient;
import chatrmi.common.ChatService;
import chatrmi.server.Server;
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

        Runtime.getRuntime().exec("rmiregistry 2021");
        LocateRegistry.createRegistry(2021);

        try {
            Server chatServer = new Server();
            ChatService stub = (ChatService) UnicastRemoteObject.exportObject(chatServer, 0);

            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.getRegistry(2021);
            registry.bind("ChatService", stub);

            BotClient bot = new BotClient(chatServer);
            ChatClient bot_stub = (ChatClient) UnicastRemoteObject.exportObject(bot, 0);
            stub.registerClient(bot_stub);
            
            System.out.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }

        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                System.out.println("Shutdown Hook is running !");
            }
        });

    }

}
