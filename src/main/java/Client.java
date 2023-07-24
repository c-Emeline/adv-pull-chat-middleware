import connection.Connection;
import dialogue.Dialogue;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import msgapplication.MsgApplication;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.List;

public class Client extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setScene(new Scene(MsgApplication.createConnectionPane(primaryStage), 300, 300));
		primaryStage.setTitle("Connection");
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);

	}


	//old version without interface
	/*public static void main(String[] args) {
		// TODO Auto-generated method stub
		Connection myComponent;
		try {
			//example of a RMI URL used to retrieve a remote reference 
			myComponent = (Connection) Naming.lookup("rmi://localhost:1099/Connection");

			Dialogue titiDial = myComponent.connect("Titi");
			Dialogue totoDial = myComponent.connect("Toto");

			String[] clients = totoDial.getClients();
			System.out.println("Clients : \n"+Arrays.toString(clients));

			totoDial.sendMessage("Titi", "Salut !");

			String[] msgTiti = titiDial.getMessages();
			System.out.println("Messages : \n"+ Arrays.toString(msgTiti));

		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			// TODO Auto-generated catch blocks
			e.printStackTrace();
		}

	}*/

}
