package server;

import java.io.IOException;

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Interface graphique du serveur de jeu
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

    /**
     * Indique si le serveur tourne
     */
    private boolean running = false;

    /**
     * Le serveur
     */
    private Server server;

    /**
     * Retourne si le serveur tourne
     * 
     * @return boolean
     */
    public boolean getRunning(){
        return running;
    }

    /**
     * Ajoute un message dans la fenêtre de log
     * 
     * @param message
     */
    public void log(String message){
        textArea.appendText(System.getProperty("line.separator") + message);
    }

    /**
     * Vide la zone de log
     */
    public void clearLog(){
        textArea.setText("");
    }

    /**
     * Change le message de statut
     * 
     * @param message
     */
    public void setStatut(String message){
        statut.setText(message);
    }

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

    /**
     * Démarre le serveur
     */
    public void startServer(){
        running = true;
        setRunningState();
        setStatut("En marche");

        try{
            server = new Server(this, ip.getText(), Integer.parseInt(port.getText()));
            server.startServer();
        }catch(NumberFormatException | IOException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Arrête le serveur
     */
    public void stopServer(){
        running = false;
        setNonRunningState();

        server.interrupt();
    }

    /**
     * Met l'UI en état running
     */
    public void setRunningState(){
        ip.setDisable(true);
        port.setDisable(true);
        run.setDisable(true);
        stop.setDisable(false);
        textArea.setDisable(false);
    }

    /**
     * Met l'UI en état non-running
     */
    public void setNonRunningState(){
        ip.setDisable(false);
        port.setDisable(false);
        run.setDisable(false);
        stop.setDisable(true);
        textArea.setDisable(true);
    }

    /**
     * Lancement de l'application
     * 
     * @param args
     */
    public static void main(String [] args){
        launch(args);
    }

    /**
     * Prise en charge des évènements
     */
    public void handle(Event event){
        if(event.getSource() == run){
            startServer();
        }
        if (event.getSource() == stop){
            stopServer();
        }
    }
}
