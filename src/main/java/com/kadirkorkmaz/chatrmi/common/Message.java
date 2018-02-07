/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kadirkorkmaz.chatrmi.common;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author kadir
 */
public class Message implements Serializable {

    private String from;
    private String to;
    private Date date;
    private String message;

    public Message() {
    }

    public Message(String from, String to, Date date, String message) {
        this.from = from;
        this.to = to;
        this.date = date;
        this.message = message;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    
    
    @Override
    public String toString() {
        return "Message{" + "from=" + from + ", to=" + to + ", date=" + date + ", message=" + message + '}';
    }

}
