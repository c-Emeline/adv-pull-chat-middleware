package msgapplication;

import connection.Connection;
import dialogue.Dialogue;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import static javafx.geometry.Pos.CENTER;

public class MsgApplication {

    static public VBox createConnectionPane(Stage primaryStage){
        GridPane gp = new GridPane();
        Label convLabel = new Label("Username");
        TextField userName = new TextField();
        Label connectionErrorLabel = new Label("");

        Button btn = new Button("Connect");
        btn.setOnAction(
                (evt) -> {
                    Dialogue userDialog = null;
                    boolean connected = false;
                    try {
                        //example of a RMI URL used to retrieve a remote reference
                        Connection myComponent = (Connection) Naming.lookup("rmi://localhost:1099/Connection");
                        userDialog = myComponent.connect(userName.getText());

                    } catch (MalformedURLException | RemoteException | NotBoundException e) {
                        // TODO Auto-generated catch blocks
                        e.printStackTrace();
                    }

                    primaryStage.setScene(new Scene(createMessagePane(userDialog, primaryStage), 500, 500));
                    primaryStage.setTitle(userName.getText()+" tchat 8_8");
                }
        );


        gp.add( convLabel, 0, 0 );
        gp.add( userName, 0, 1 );
        gp.add(btn, 0,2);
        gp.add( connectionErrorLabel, 0, 3 );

        HBox hbox = new HBox(gp);
        hbox.setAlignment(CENTER);

        VBox vbox = new VBox(hbox);
        vbox.setAlignment(CENTER);

        return vbox;
    }


    static public VBox createMessagePane(Dialogue userDialog, Stage primaryStage){
        GridPane gp = new GridPane();
        Label msgLabel = new Label("Message");

        TextField message = new TextField();
        Label receiver = new Label("Receiver");
        TextField receiverName = new TextField();

        Button btnSend = new Button("Send");
        Label errorLabel = new Label("");
        Button btnGetMsg = new Button("Get Messages");
        Button btnDisconnect = new Button("Disconnect");

        btnSend.setOnAction(
                (evt) -> {
                    Dialogue myComponent;
                    try {
                        //example of a RMI URL used to retrieve a remote reference
                        userDialog.sendMessage(receiverName.getText(), message.getText());

                    } catch (RemoteException e) {
                        // TODO Auto-generated catch blocks
                        e.printStackTrace();
                    }
                    message.setText("");
                    receiverName.setText("");
                }
        );

        btnDisconnect.setOnAction(
                (evt) -> {
                    try {
                        //example of a RMI URL used to retrieve a remote reference
                        Connection myComponent = (Connection) Naming.lookup("rmi://localhost:1099/Connection");
                        myComponent.disconnect(userDialog.getClient());

                    } catch (MalformedURLException | RemoteException | NotBoundException e) {
                        // TODO Auto-generated catch blocks
                        e.printStackTrace();
                    }

                    primaryStage.setScene(new Scene(createConnectionPane(primaryStage), 300, 300));
                    primaryStage.setTitle("Connection");
                }
        );

        btnGetMsg.setOnAction(
                (evt) -> {
                    String[] msg = new String[2];
                    try {
                        //example of a RMI URL used to retrieve a remote reference
                        msg = userDialog.getMessages();

                    } catch (RemoteException e) {
                        // TODO Auto-generated catch blocks
                        e.printStackTrace();
                    }

                    for (int i=0; i<msg.length; i++){
                        gp.add(new Label(msg[i]), 0,i+1);
                    }
                }
        );


        gp.add( msgLabel, 0, 94 );
        gp.add( message, 0, 95 );
        gp.add( receiver, 0, 96 );
        gp.add( receiverName, 0, 97 );
        gp.add( btnSend, 0, 98 );
        gp.add( errorLabel, 0, 99);
        gp.add( btnGetMsg, 0, 100 );
        gp.add( btnDisconnect, 0, 101 );

        HBox hbox = new HBox(gp);
        hbox.setAlignment(CENTER);

        VBox vbox = new VBox(hbox);
        vbox.setAlignment(CENTER);

        return vbox;
    }
}
