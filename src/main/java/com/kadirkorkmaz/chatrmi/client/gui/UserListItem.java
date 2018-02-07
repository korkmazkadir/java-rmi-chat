/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kadirkorkmaz.chatrmi.client.gui;

/**
 *
 * @author kadir
 */
public class UserListItem {

    private String username;
    private int unreadMessageNumber = 0;

    public UserListItem(String username, int unreadMessageNumber, boolean isSpecialUser) {
        this.username = username;
        this.unreadMessageNumber = unreadMessageNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getUnreadMessageNumber() {
        return unreadMessageNumber;
    }

    public void incrementUnreadMessageNumber(){
        unreadMessageNumber += 1;
    }

    @Override
    public String toString() {
        return unreadMessageNumber == 0 ?  username : username + " (" + unreadMessageNumber + ")";
    }

}
