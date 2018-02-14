/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatrmi;

import chatrmi.client.CommandLineClient;
import chatrmi.common.ChatClient;
import chatrmi.common.ChatService;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author kadir
 */
public class CmdlClientApp {

    public static void main(String[] args) {

//        if(args.length < 1){
//            System.err.println("Error : missign parameter username!");
//            return;
//        }
        String userName = "kadir_cmd";

        try {

            System.out.println("Client is running...");
            
            Registry registry = LocateRegistry.getRegistry(2020);
            ChatService chatService = (ChatService) registry.lookup("ChatService");

            CommandLineClient chatClient = new CommandLineClient(userName);
            ChatClient clintStub = (ChatClient)UnicastRemoteObject.exportObject(chatClient, 0);

            chatService.registerClient(clintStub);

            chatService.sendMessage(clintStub, clintStub.getName(), "Hello there :)");
            //System.out.println("Message send");
            //chatService.unregisterClient(clintStub);

        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }

    }

}
