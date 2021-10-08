package server;

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Interface graphique du serveur de jeu
 * 
 * @author xXxMatBissDu57xXx
 */

public class ServerUI extends Application implements EventHandler{

    /**
     * Boutons d'action pour lancer ou arrêter le serveur
     */
    private Button run;
    private Button stop;

    /**
     * Champs de choix d'ip et de port
     */
    private TextField ip;
    private TextField port;

    /**
     * Zone de log et de statut
     */
    private  TextArea textArea;
    private Label statut;

    //////////////////////////////////////////////////

    //////////////////////////////////////////////////

    /**
     * Démarrage de l'UI
     * 
     * @param stage
     * @throws Exception 
     */
    public void start(Stage stage) throws Exception{

        //Fenêtre principale
        BorderPane borderPane = new BorderPane();
        Scene mainScene = new Scene(borderPane);
        stage.setScene(mainScene);
        stage.setWidth(600.0);
        stage.setHeight(600.0);

        //Toolbar
        ToolBar toolBar = new ToolBar();
        Label labelIp = new Label("IP : ");
        ip = new TextField("127.0.0.1");
        Label labelPort = new Label("Port : ");
        port = new TextField("6699");
        run = new Button("Démarrer");
        run.setOnAction(this);
        stop = new Button("Arrêter");
        stop.setOnAction(this);
        toolBar.getItems().addAll(labelIp, ip, labelPort, port, run, stop);
        borderPane.setTop(toolBar);

        //Logger
        textArea = new TextArea();
        textArea.setEditable(false);
        borderPane.setCenter(textArea);

        //Statut
        statut = new Label("Arrêté");
        borderPane.setBottom(statut);

        stage.setTitle("Serveur de jeu");
        stage.show();
    }

    public static void main(String [] args){
        launch(args);
    }

    public void handle(Event event){

    }
}
