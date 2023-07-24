package dialogue;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Dialogue extends Remote {

	String getClient() throws RemoteException;
	String[] getClients() throws RemoteException;
	boolean sendMessage(String to, String message) throws RemoteException;
	String[] getMessages() throws RemoteException;
	void receiveMessage(String from, String message) throws RemoteException;

}
