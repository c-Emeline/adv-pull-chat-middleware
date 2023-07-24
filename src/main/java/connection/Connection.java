package connection;

import dialogue.Dialogue;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Connection extends Remote {
    Dialogue connect(String nickname) throws RemoteException;
    void disconnect(String nickname) throws RemoteException;
}
