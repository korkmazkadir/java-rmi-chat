/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatrmi.client.gui;

/**
 *
 * @author kadir
 */
public class UserListItem {

    private final String currrentUsername;
    private final String username;
    private int unreadMessageCount = 0;
    private boolean isOnline = true;

    public UserListItem(String currrentUsername, String username) {
        this.currrentUsername = currrentUsername;
        this.username = username;
    }

    public String getCurrrentUsername() {
        return currrentUsername;
    }

    public String getUsername() {
        return username;
    }

    public int getUnreadMessageCount() {
        return unreadMessageCount;
    }

    public void incrementUnreadMessageCount() {
        unreadMessageCount += 1;
    }

    public void resetUnreadMessageCount() {
        this.unreadMessageCount = 0;
    }

    public boolean isIsOnline() {
        return isOnline;
    }

    public void setIsOnline(boolean isOnline) {
        this.isOnline = isOnline;
    }
    
    @Override
    public boolean equals(Object obj) {

        if (!obj.getClass().equals(this.getClass())) {
            return false;
        }

        return username.equals(((UserListItem) obj).getUsername());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(username).append(" ");
        if (username.equals(currrentUsername)) {
            sb.append("(me)").append(" ");
        }
        
        if(unreadMessageCount > 0){
           sb.append("(").append(unreadMessageCount).append(") ");
        }
        
        if(!isOnline){
            sb.append("(offline) ");
        }

        return sb.toString();
    }

}
