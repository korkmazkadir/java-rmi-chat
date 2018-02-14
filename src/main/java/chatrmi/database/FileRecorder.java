/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatrmi.database;

import chatrmi.common.Message;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author kadir
 */
public class FileRecorder {

    private static final String filePath = "Chat-DB-File";

    public static List<Message> loadPreviosMessages() {

        List<Message> messageList = new LinkedList<>();
        
        try {
            FileInputStream fileIn = new FileInputStream(filePath);
            InputStreamReader isr = new InputStreamReader(fileIn, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(isr);            
            
            String line;
            while( (line =  reader.readLine()) != null){
                Message m = new Message();
                m.loadFromString(line);
                messageList.add(m);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return messageList;
    }

    public static void appendNewMessage(Message message) {

        try {
            FileOutputStream fos = new FileOutputStream(filePath,true);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
            
            bw.write(message.getFileString());

            bw.close();
            fos.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
    }

}
