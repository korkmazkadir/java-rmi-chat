/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatrmi.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author kadir
 */
public interface ChatClient extends Remote {

    public void notifyNewMessage(Message message) throws RemoteException;

    public void notifyLogin(String username) throws RemoteException;

    public void notifyLogout(String username) throws RemoteException;

    public String getName() throws RemoteException;

}
