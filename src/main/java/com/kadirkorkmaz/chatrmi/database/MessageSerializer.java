/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kadirkorkmaz.chatrmi.database;

import com.kadirkorkmaz.chatrmi.common.Message;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import org.mapdb.DataInput2;
import org.mapdb.DataOutput2;
import org.mapdb.Serializer;

/**
 *
 * @author kadir
 */
public class MessageSerializer implements Serializer<Message>, Serializable {

    @Override
    public void serialize(DataOutput2 d, Message a) throws IOException {
        d.writeUTF(a.getFrom());
        d.writeUTF(a.getTo());
        d.writeUTF( Long.toString(a.getDate().getTime()) );
        d.writeUTF(a.getMessage());
    }

    @Override
    public Message deserialize(DataInput2 di, int i) throws IOException {
        return new Message(di.readUTF(), di.readUTF(), new Date(Long.parseLong(di.readUTF())),di.readUTF());
    }

}
