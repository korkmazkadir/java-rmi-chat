/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kadirkorkmaz.chatrmi.database;

import com.kadirkorkmaz.chatrmi.common.Message;
import java.util.List;
import org.mapdb.DB;
import org.mapdb.DBMaker;

/**
 *
 * @author kadir
 */
public class DBManager {

    private static final String pathToCreateDB = System.getProperty("user.home") + "/RMIChatDB";
    private static final String messageListName = "MessageList";

    private static DB db;

    public static void OpenDB() {
        db = DBMaker
                .fileDB(pathToCreateDB)
                .transactionEnable()
                .fileMmapEnable()
                .make();
    }

    public static List<Message> GetPersistentMessageList() {
        List<Message> messageList = db.indexTreeList(messageListName, new MessageSerializer()).createOrOpen();
        return messageList;
    }

    public static void CloseDB() {
        if (db != null) {
            db.close();
        }
    }

}
