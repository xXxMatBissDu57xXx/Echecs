package client;

import java.io.IOException;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Interface graphique du client
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
    private Label statut;

    private boolean running = false;

    //Le client
    private Client client;

    /**
     * Retourne si le client tourne
     * 
     * @return boolean
     */
    public boolean getRunning(){
        return running;
    }

    /**
     * Ajout de message dans la zone de log
     * 
     * @param message
     */
    public void appendMessage(String message){
        textArea.appendText(message);
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
        statut = new Label("Arrêté");
        input = new TextField();
        input.addEventFilter(KeyEvent.KEY_RELEASED, this);
        bottomBox.getChildren().addAll(input, statut);
        borderPane.setBottom(bottomBox);

        setDisconnectedState();

        stage.setTitle("Client de jeu");
        stage.show();
    }

    /**
     * Connexion au serveur
     * 
     * @throws NumberFormatException
     * @throws IOException
     */
    public void connectToServer() throws NumberFormatException, IOException{
        if(ip.getText().trim().length() == 0){
            setStatut("Veuillez entrer une adresse ip valide");
            return;
        }
        if(port.getText().trim().length() == 0){
            setStatut("Veuillez entrer un port valide");
            return;
        }
        if(pseudo.getText().trim().length() == 0){
            setStatut("Veuillez entrer un pseudo valide");
            return;
        }
        running = true;
        setConnectedState();

        client = new Client(this, ip.getText(), Integer.parseInt(port.getText()), pseudo.getText());
        client.startClient();
    }

    /**
     * Déconnexion du serveur
     */
    public void disconnectFromServer(){
        running = false;
        setDisconnectedState();

        this.clearLog();
        this.setStatut("Prêt");

        client.interrupt();
    }

    /**
     * Met l'UI dans l'état running
     */
    public void setConnectedState(){
        ip.setDisable(true);
        port.setDisable(true);
        pseudo.setDisable(true);
        run.setDisable(true);
        stop.setDisable(false);
        input.setDisable(false);
    }

    /**
     * Met l'UI dans l'état non-running
     */
    public void setDisconnectedState(){
        ip.setDisable(false);
        port.setDisable(false);
        pseudo.setDisable(false);
        run.setDisable(false);
        stop.setDisable(true);
        input.setDisable(true); 
    }

    /**
     * Envoie un message si l'utilisateur appuie sur la touche entrée
     * 
     * @param event
     * @throws IOException
     */
    public void processEnter(KeyEvent event) throws IOException{
        if(event.getCode() == KeyCode.ENTER && input.getText().trim().length() > 0){
            client.addMessage(input.getText() + System.getProperty("line.separator"));
            input.setText("");
        }
    }

    /**
     * Lance l'application
     * 
     * @param args
     */
    public static void main(String [] args){
        launch(args);
    }

    /**
     * Prise en charge des évènements
     * 
     * @param event
     */
    public void handle(Event event){
        if(event.getSource() == run){
            try{
                connectToServer();
            }catch(NumberFormatException | IOException e){
                System.out.println(e.getMessage());
            }
        }
        if(event.getSource() == stop){
            disconnectFromServer();
        }
        if(event.getSource() == input){
            try{
                processEnter((KeyEvent) event);
            }catch(IOException e){
                System.out.println(e.getMessage());
            }
        }
    }
}
