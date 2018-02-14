/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatrmi.client.gui;

import chatrmi.common.Message;
import java.awt.event.KeyEvent;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.ListSelectionModel;

/**
 *
 * @author kadir
 */
public class ClientForm extends javax.swing.JFrame {

    private String currentUser;
    private final DefaultListModel userListModel;
    private final DefaultListModel messageListModel;

    private final CommunicationProvider communicationProvider;

    private String username;

    private boolean sentWithEnter = true;

    /**
     * Creates new form GUIClient
     *
     * @param communicationProvider
     */
    public ClientForm(CommunicationProvider communicationProvider) {

        this.communicationProvider = communicationProvider;

        initComponents();
        userListModel = new DefaultListModel();
        messageListModel = new DefaultListModel();

        lstUserList.setModel(userListModel);
        lstMessageList.setModel(messageListModel);

        lstUserList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        lstMessageList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        txtMessage.setEnabled(false);

    }

    public void updateUserList(String[] users) {
        userListModel.clear();
        for (String user : users) {
            UserListItem item = new UserListItem(username, user);
            userListModel.addElement(item);
        }
    }

    public void userLogin(String loginUsername) {
        System.out.println("User login : " + loginUsername);
        UserListItem item = getUserListItem(loginUsername);
        if (item == null) {
            item = new UserListItem(username, loginUsername);
            userListModel.addElement(item);
        }
        item.setIsOnline(true);

    }

    public void userLogout(String logoutUsername) {
        System.out.println("User logout : " + logoutUsername);
        UserListItem item = getUserListItem(logoutUsername);
        if (item == null) {
            return;
        }
        item.setIsOnline(false);
    }

    private UserListItem getUserListItem(String username) {
        int size = userListModel.getSize();
        for (int i = 0; i < size; i++) {
            UserListItem listItem = (UserListItem) userListModel.get(i);
            if (listItem.getUsername().equals(username)) {
                return listItem;
            }
        }

        return null;
    }

    public void notifyNewMessage(Message message) {

        if (!message.getFrom().equals(username) && (currentUser == null || !currentUser.equals(message.getFrom())) ) {
            
            UserListItem item = getUserListItem(message.getFrom());
            if (item != null) {
                item.incrementUnreadMessageCount();
            } else {
                System.out.println("!OPPS : We get message before adding user");
            }
         
            lstUserList.repaint();
            return;
        }

        messageListModel.addElement(message);
        int lastIndex = messageListModel.getSize() - 1;
        if (lastIndex >= 0) {
            lstMessageList.ensureIndexIsVisible(lastIndex);
        }

    }

    public void setUsername(String name) {
        this.username = name;
        lstMessageList.setCellRenderer(new MessageCellRenderer(username));
        lstUserList.setCellRenderer(new UserCellRenderer(username));
        this.setTitle(name);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        lstUserList = new javax.swing.JList<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        lstMessageList = new javax.swing.JList<>();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtMessage = new javax.swing.JTextArea();
        btnSend = new javax.swing.JButton();
        cbSendWithEnter = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lstUserList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        lstUserList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                lstUserListValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(lstUserList);

        lstMessageList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(lstMessageList);

        txtMessage.setColumns(20);
        txtMessage.setRows(5);
        txtMessage.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtMessageKeyPressed(evt);
            }
        });
        jScrollPane3.setViewportView(txtMessage);

        btnSend.setText("Send");
        btnSend.setToolTipText("");
        btnSend.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                btnSendMouseReleased(evt);
            }
        });

        cbSendWithEnter.setSelected(true);
        cbSendWithEnter.setText("Send with enter");
        cbSendWithEnter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbSendWithEnterActionPerformed(evt);
            }
        });

        jLabel1.setText("messages");

        jLabel2.setText("users");

        jLabel3.setText("new message box");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane2)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 436, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
                        .addComponent(btnSend, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
                        .addComponent(cbSendWithEnter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel2))
                .addGap(37, 37, 37))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 297, Short.MAX_VALUE)
                    .addComponent(jScrollPane2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnSend, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbSendWithEnter, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(14, 14, 14))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lstUserListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_lstUserListValueChanged
        currentUser = ((UserListItem) userListModel.get(lstUserList.getSelectedIndex())).getUsername();
        if (currentUser != null) {

            List<Message> messages = communicationProvider.loadMessages(currentUser);
            messageListModel.clear();
            for (Message message : messages) {
                messageListModel.addElement(message);
            }
            txtMessage.setEnabled(true);
            UserListItem userListItem = getUserListItem(currentUser);
            userListItem.resetUnreadMessageCount();

        } else {
            txtMessage.setEnabled(false);
        }
    }//GEN-LAST:event_lstUserListValueChanged

    private void sendMessage() {
        String message = txtMessage.getText();
        if (message != null && message.isEmpty() == false) {
            communicationProvider.sendMessage(currentUser, message);
            txtMessage.setText("");
        }
    }

    private void btnSendMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSendMouseReleased
        sendMessage();
    }//GEN-LAST:event_btnSendMouseReleased

    private void cbSendWithEnterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbSendWithEnterActionPerformed
        if (cbSendWithEnter.isSelected()) {
            sentWithEnter = true;
        } else {
            sentWithEnter = false;
        }
    }//GEN-LAST:event_cbSendWithEnterActionPerformed

    private void txtMessageKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMessageKeyPressed
        if (sentWithEnter && evt.getKeyCode() == KeyEvent.VK_ENTER) {
            evt.consume();
            sendMessage();
        }
    }//GEN-LAST:event_txtMessageKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSend;
    private javax.swing.JCheckBox cbSendWithEnter;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JList<String> lstMessageList;
    private javax.swing.JList<String> lstUserList;
    private javax.swing.JTextArea txtMessage;
    // End of variables declaration//GEN-END:variables
}