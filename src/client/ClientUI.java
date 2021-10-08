package client;

import java.util.Random;

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Interface graphique du client
 * 
 * @author xXxMatBissDu57xXx
 */
public class ClientUI extends Application implements EventHandler{

     /**
     * Champs pour choisir l'ip, le port et le pseudo
     */
    private TextField ip;
    private TextField port;
    private TextField pseudo;

    /**
     * Boutons d'action pour lancer ou arrêter le client 
     */
    private Button run;
    private Button stop;

    /**
     * Zones de log, d'écriture et de statut
     */
    private TextArea textArea;
    private TextField input;
    private Label status;
    
    /**
     * Démarrage de l'UI
     * 
     * @param stage
     * @throws Exception
     */
    public void start(Stage stage) throws Exception {

        //Fenêtre principale
        BorderPane borderPane = new BorderPane();
        Scene scene = new Scene(borderPane);
        stage.setScene(scene);

        //Toolbar
        ToolBar toolBar = new ToolBar();
        ip = new TextField("127.0.0.1");
        port = new TextField("6699");
        pseudo = new TextField("user" + (new Random().nextInt(100)));
        run = new Button("Se connecter");
        run.setOnAction(this);
        stop = new Button("Se déconnecter");
        stop.setOnAction(this);
        toolBar.getItems().addAll(ip, port, pseudo, run, stop);
        borderPane.setTop(toolBar);

        //Logger
        textArea = new TextArea();
        borderPane.setCenter(textArea);

        //Zone d'écriture et statut
        VBox bottomBox = new VBox();
        status = new Label("Arrêté");
        input = new TextField();
        input.addEventFilter(KeyEvent.KEY_RELEASED, this);
        bottomBox.getChildren().addAll(input, status);
        borderPane.setBottom(bottomBox);

        stage.setTitle("Client de jeu");
        stage.show();
    }

    public static void main(String [] args){
        launch(args);
    }

    public void handle(Event event){}
}
