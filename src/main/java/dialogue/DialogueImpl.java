package dialogue;

import connection.ConnectionImpl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;

public class DialogueImpl extends UnicastRemoteObject implements Dialogue {

	private String clientName;
	private ConnectionImpl connection;
	private ArrayList<ArrayList<String>> messagesSend = new ArrayList<>();
	private ArrayList<ArrayList<String>> messagesReceived = new ArrayList<>();

	public DialogueImpl(ConnectionImpl connection, String nickname) throws RemoteException {
		super();
		this.connection = connection;
		this.clientName = nickname;
	}

	@Override
	public String getClient() throws RemoteException {
		return this.clientName;
	}

	@Override
	public String[] getClients() throws RemoteException {
		return this.connection.getClientsName().toArray(new String[0]);
	}


	@Override
	public boolean sendMessage(String to, String message) throws RemoteException {
		this.messagesSend.add(new ArrayList<String>(Arrays.asList(to, message)));
		ArrayList<Dialogue> dialogues = connection.getDialogues();
		for (Dialogue d : dialogues) {
			if (d.getClient().equals(to)){
				d.receiveMessage(this.getClient(), message);
			}
		}
		return false;
	}

	@Override
	public String[] getMessages() throws RemoteException {
		ArrayList<String> msgReceived = new ArrayList<>();
		for (ArrayList<String> m : this.messagesReceived){
			msgReceived.add(m.get(0)+" : "+m.get(1));
		}
		return msgReceived.toArray(new String[msgReceived.size()]);
	}

	public void receiveMessage(String from, String message){
		this.messagesReceived.add(new ArrayList<String>(Arrays.asList(from, message)));
	}

}