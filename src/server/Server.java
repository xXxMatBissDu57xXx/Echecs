package server;

import javafx.application.Platform;

import java.io.IOException;

import java.net.BindException;
import java.net.InetSocketAddress;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Processus serveur qui ecoute les connexion entrantes,
 * les messages entrant et les rediffuse au clients connectes
 */
public class Server extends Thread{

    /**
     * Variable d'accès à la base de données
     */
    private final String pgUrl = "jdbc:postgresql://localhost/java";
    private final String pgUser = "lambda";
    private final String pgPassword = "1234";

    /**
     * ip, port et interface serveur donnes en parametres
     */
    private String ip;
    private int port;
    private ServerUI interfaceServeur;

    /**
     * Buffer de type ByteBuffer pour ecrire ou lire dans un channel
     */
    private ByteBuffer buffer = ByteBuffer.allocate(8192);

    /**
     * un Logger et un FileHandler pour enregistrer des logs dans un fichier
     */
    private Logger logger;
    private FileHandler fh;

    /**
     * ServeSocketChannel pour ecouter et recevoir des connexions clients
     */
    private ServerSocketChannel ssc;

    /**
     * SocketChannel pour conserver les connexions clients
     */
    private SocketChannel sc;

    /**
     * Selecteur pour gerer les channels
     */
    private Selector selector;

    /**
     * Hashmap pour stocker le pseudo d'un client, et une autre pour le mot de passe
     */
    private HashMap<SocketChannel, String> map_pseudo = new HashMap<SocketChannel, String>();
    private HashMap<String, String> map_mdp = new HashMap<String, String>();

    /**
     * Thread Server
     */
    private Server ts;

    /**
     * Constructeur Server
     * 
     * @param interfaceServeur
     * @param ip
     * @param port
     * @throws IOException
     */
    public Server(ServerUI interfaceServeur, String ip, int port)  throws IOException {
        this.ip = ip;
        this.port = port;
        this.interfaceServeur = interfaceServeur;
    }

    /**
     * Demarre un Thread Server afin de ne pas bloquer l'interface serveur
     * 
     * @throws IOException
     */
    public void startServer() throws IOException{
            ts = new Server(interfaceServeur, ip, port);
            ts.start();
    }

    /**
     * Execution du thread Server
     */
    public void run(){
        try{

            //Ouverture des SocketChannel, ServerSocketChannel et Selector
            sc = SocketChannel.open();
            ssc = ServerSocketChannel.open();
            ssc.configureBlocking(false);
            selector = Selector.open();

            //Affectation de l adresse et du port du serveur
            try{
                ssc.bind(new InetSocketAddress(ip, port));
            }catch(BindException e){
                System.out.println(e.getMessage());
            }
            
            //Selection de l operation de reception de connexion, sur le selecteur
            ssc.register(selector, SelectionKey.OP_ACCEPT);

            int cpt = 0;

            while (interfaceServeur.getRunning()){ 
                
                //Recherche d une operation selectionnee sur le selecteur
                selector.select();

                //Creation de liste d operations selectionnees
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> iter = selectedKeys.iterator();
                while (iter.hasNext()) {
                    SelectionKey key = iter.next();

                    //Si la connexion est recue
                    if (key.isAcceptable()){

                        //Accepte la connexion
                        sc = ssc.accept();
                        sc.configureBlocking(false);

                        //Mise a jour de l interface serveur en temps reel
                        Platform.runLater(new Runnable(){
                            @Override
                            public void run(){
                                interfaceServeur.log("Connexion reçue depuis " + sc.socket().getRemoteSocketAddress() +".\n");
                            }
                        });

                        //Selection de l operation de lecture du channel, sur le selecteur
                        sc.register(selector, SelectionKey.OP_READ);
                    }

                    //Si un message a ete ecrit dans le channel
                    else if (key.isReadable()){

                        //Recuperation du channel
                        SocketChannel sc = (SocketChannel) key.channel();

                        //Lecture du channel par un ByteBuffer, jusqu a ce qu il soit vide
                        String message = null;
                        while(sc.read(buffer) > 0){
                            buffer.flip();
                            for (int i = 0; i < buffer.limit(); i++){
                                message += (char)buffer.get();
                            }
                            buffer.clear();
                        }
                        System.out.println(message);
                        System.out.println(cpt);

                        if(message.equals("&pseudo&")){
                            buffer = ByteBuffer.wrap(message.getBytes());
                            sc.write(buffer);
                            cpt ++;
                        }

                        //Enregistre le pseudo du client
                        else if(cpt == 1){
                            map_pseudo.put(sc, message);
                            message = "&mdp&";
                            buffer = ByteBuffer.wrap(message.getBytes());
                            sc.write(buffer);
                            cpt ++;
                        }

                        //Enregistre le mot de passe du client
                        if(cpt == 2){
                            map_mdp.put(map_pseudo.get(sc),message);
                            if(pg_login(map_pseudo.get(sc), map_mdp.get(map_pseudo.get(sc)))){
                                message = "&deco&";
                                buffer = ByteBuffer.wrap(message.getBytes());
                                sc.write(buffer);
                            }
                            cpt = 0;
                        }

                        else{

                            //Configuration du logger et du FileHandler
                            //logger = Logger.getLogger("MonLog");
                            try{
                                fh = new FileHandler("C:/Users/Mathieu/Desktop/Devoirs/Echecs/logs/log.txt");
                                logger.addHandler(fh);
                                SimpleFormatter formatter = new SimpleFormatter();
                                fh.setFormatter(formatter);

                                //Ajout du message de log
                                logger.info(message);

                            }catch(SecurityException | IOException e){
                                System.out.println(e.getMessage());;
                            }

                            //Renvoie le message recu sur chaque channel connecte
                            buffer = ByteBuffer.wrap(message.getBytes());
                            for(SelectionKey sKey : selector.keys()){
                                if(sKey.isValid() && sKey.channel() instanceof SocketChannel){
                                    SocketChannel sch=(SocketChannel) sKey.channel();
                                    sch.write(buffer);
                                    buffer.rewind();
                                }
                            }
                        }

                        //Selection de l operation d ecriture dans le channel, sur le selecteur
                        sc.register(selector, SelectionKey.OP_WRITE);

                        //Vider le buffer
                        buffer.clear();
                    }

                    //Si le serveur a ecrit dans un channel
                    else if(key.isWritable()){

                        //Recuperation du channel
                        SocketChannel sc = (SocketChannel) key.channel();

                        //Selection de l operation de lecture du channel, sur le selecteur
                        sc.register(selector, SelectionKey.OP_READ);
                    }
                    iter.remove();
                }
            }

            //Fermeture des ServerSocketChannel, SocketChannel et Selector
            this.sc.close();
            this.ssc.close();
            this.selector.wakeup();
            this.selector.close();
            Platform.runLater(new Runnable(){
                @Override
                public void run(){
                    interfaceServeur.clearLog();
                }
            });
            System.out.println("Arrêt serveur");
            this.interrupt();

        }catch(IOException | NullPointerException e){
            e.printStackTrace();
            Platform.runLater(new Runnable(){
                @Override
                public void run(){
                    interfaceServeur.clearLog();
                }
            });
        }
    }
        /**
     * Connexion à la base de données et identification de l'utilisateur
     * 
     * @return Connection
     */
    public Connection pg_connect(){

        Connection conn = null;

        try{
            conn = DriverManager.getConnection(pgUrl, pgUser, pgPassword);
            System.out.println("Connecté à pg");
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return conn;
    }

    /**
     * Vérification des identifiants de l'utilisateur
     * 
     * @param pseudo
     * @param mdp
     */
    public boolean pg_login(String pseudo, String mdp){

        Connection conn = pg_connect();
        ResultSet resultats = null;
        String requete = "SELECT pseudo, mdp FROM utilisateurs";

        try{
            Statement stmt = conn.createStatement();
            resultats = stmt.executeQuery(requete);
    
            boolean encore = resultats.next();
            boolean login = false;

            while(encore){
                if(resultats.getString(1).equals(pseudo)){
                    if(resultats.getString(2).equals(mdp)){
                        login = true;
                    }
                }
                encore = resultats.next();
            }
            resultats.close();

            if(! login){
                System.out.println("Mauvais identifiant ou mot de passe.");
                return false;
            }
            return true;

        }catch(SQLException e){
            System.out.println(e.getMessage());
            return false;
        }
    }
}