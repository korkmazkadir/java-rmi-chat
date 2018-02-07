/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kadirkorkmaz.chatrmi.client.gui;

import com.kadirkorkmaz.chatrmi.common.Message;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

/**
 *
 * @author kadir
 */
public class MessageCellRenderer implements ListCellRenderer {

    protected static TitledBorder titiledBorder;
    protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

    protected Color color1 = new Color(122, 186, 207);
    protected Color color2 = new Color(244, 223, 162);
    
    private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    private final String clientName;

    private static final int titleFontSize = 9;
    private static final int messageFontSize = 17;
    
    MessageCellRenderer(String clientName) {
        this.clientName = clientName;
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

        Message mesage = (Message) value;
        JLabel label;

        final String dateString = dateFormat.format(mesage.getDate());
        final String titleText = mesage.getFrom() + "  " +  dateString;
        

        titiledBorder = new TitledBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY), titleText);
        titiledBorder.setTitleFont(new Font(Font.SANS_SERIF, Font.PLAIN, titleFontSize));
        
        label = (JLabel) defaultRenderer.getListCellRendererComponent(list, mesage.getMessage(), index, false, cellHasFocus);
        label.setBorder(titiledBorder);
        label.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, messageFontSize));
        

        if (clientName.equals(mesage.getFrom())) {
            label.setBackground(color1);
            titiledBorder.setTitleJustification(TitledBorder.RIGHT);
            label.setHorizontalAlignment(SwingConstants.LEFT);
        } else {
            label.setBackground(color2);
            titiledBorder.setTitleJustification(TitledBorder.LEFT);
            label.setHorizontalAlignment(SwingConstants.RIGHT);
        }

        return label;

    }

}
