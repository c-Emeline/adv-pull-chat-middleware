package connection;

import dialogue.Dialogue;
import dialogue.DialogueImpl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ConnectionImpl extends UnicastRemoteObject implements Connection {

    private ArrayList<String> clientsName = new ArrayList<>();
    private ArrayList<Dialogue> dialogues = new ArrayList<>();

    public ConnectionImpl() throws RemoteException {
        super();
    }

    @Override
    public Dialogue connect(String nickname) throws RemoteException {
        if (clientsName.contains(nickname)){
            for (Dialogue d : dialogues){
                if (d.getClient()==nickname){
                    return d;
                }
            }
        }
        clientsName.add(nickname);
        Dialogue dialogue = new DialogueImpl(this, nickname);
        this.dialogues.add(dialogue);
        System.out.println("User "+nickname+" connected !");
        return dialogue;
    }

    @Override
    public void disconnect(String nickname) throws RemoteException {
        System.out.println(dialogues.get(0).getClient());
        for (Dialogue d : dialogues){
            if (d.getClient().equals(nickname)){
                UnicastRemoteObject.unexportObject(d, true);
                this.clientsName.remove(d.getClient());
            }
        }
        System.out.println("User "+nickname+" disconnected !");
    }

    public ArrayList<String> getClientsName() {
        return clientsName;
    }

    public ArrayList<Dialogue> getDialogues() {
        return dialogues;
    }
}
