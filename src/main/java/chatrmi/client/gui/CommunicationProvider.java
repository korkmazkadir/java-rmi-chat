/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatrmi.client.gui;

import chatrmi.common.Message;
import java.util.List;

/**
 *
 * @author kadir
 */
public interface CommunicationProvider {
     
    public void sendMessage(String receiverName, String message);
    public void sendMessageBroadcast(List<String> receiverName, String message);
    public List<Message> loadMessages(String userName);
    
}
