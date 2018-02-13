/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kadirkorkmaz.chatrmi.client.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.border.MatteBorder;

/**
 *
 * @author kadir
 */
public class UserCellRenderer implements ListCellRenderer{

    protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();
    
    private final Color selectedColor = new Color(211, 238, 244);
    private final Color unreadMessageColor = new Color(102, 170, 50);
    private final Color offlineUserColor = new Color(100, 121, 168);
    
    private final MatteBorder selectedBorder = BorderFactory.createMatteBorder(0, 0, 0, 0, Color.GRAY);
    
    private final int fontSize = 16;
    private final String username;

    public UserCellRenderer(String username) {
        this.username = username;
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        UserListItem userListItem = (UserListItem) value;
                
        JLabel label = (JLabel) defaultRenderer.getListCellRendererComponent(list, userListItem.toString(), index, false, cellHasFocus);
        label.setFont(new Font(Font.SERIF, Font.PLAIN, fontSize));
        
        if(userListItem.getUnreadMessageCount() > 0){
            label.setBackground(unreadMessageColor);
        }
        
        if(userListItem.isIsOnline() == false){
            label.setBackground(offlineUserColor);
        }
        
        if(isSelected){
            label.setBorder(selectedBorder);
            label.setBackground(selectedColor);
        }
        
        return label;
    }
    
}
