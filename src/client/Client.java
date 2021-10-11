package client;

import javafx.application.Platform;

import java.io.IOException;
import java.net.InetSocketAddress;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

import java.util.Iterator;
import java.util.Set;

/**
 * Client de tchat
 */
public class Client extends Thread{

    /**
     * ip, port, pseudo et interface client donnes en parametres
     */
    private String ip;
    private int port;
    private String pseudo;
    private String mdp;
    private ClientUI interfaceClient;

    /**
     * Buffer de type ByteBuffer pour ecrire ou lire dans un channel
     */
    private ByteBuffer buffer = ByteBuffer.allocate(8192);
    
    /**
     * SocketChannel utilise pour se connecter a un serveur
     */
    private SocketChannel sc;

    /**
     * Selecteur utilise pour gerer les channels
     */
    private Selector selector;

    /**
     * Thread Client
     */
    private Client tc; 

    /**
     * Constructeur Client
     * 
     * @param interfaceClient
     * @param ip
     * @param port
     * @param pseudo
     * @param mdp
     */
    public Client(ClientUI interfaceClient, String ip, int port, String pseudo, String mdp){
        this.ip = ip;
        this.port = port;
        this.pseudo = pseudo;
        this.mdp = mdp;
        this.interfaceClient = interfaceClient;   
    }
    /**
     * Envoie le message au serveur, en ecrivant sur le channel
     * 
     * @param message
     * @throws IOException
     */
    public void addMessage(String message) throws IOException{
        String finalMessage = pseudo +": "+ message;
        ByteBuffer buffer = ByteBuffer.wrap(finalMessage.getBytes());    
        tc.sc.write(buffer);

        tc.sc.register(tc.selector, SelectionKey.OP_WRITE);
    }

    /**
     * Demarre un thread Client afin de ne pas bloquer l'interface client
     */
    public void startClient(){
        tc = new Client(interfaceClient, ip, port, pseudo, mdp);
        tc.start();
    }

    /**
     * Execution du thread Client
     */
    public void run(){
        try{

            //Ouverture des SocketChannel et Selector
            sc = SocketChannel.open();
            sc.configureBlocking(false);
            selector = Selector.open();

            sc.register(selector, SelectionKey.OP_CONNECT);

            //Tentative de connexion a un ServerSocketChannel
            sc.connect(new InetSocketAddress(ip, port));

            int cpt = 0;
            String message = "";

            while(interfaceClient.getRunning()){

                selector.select();

                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> iter = selectedKeys.iterator();
                while (iter.hasNext()){
                    SelectionKey key = iter.next();

                    //Si le serveur accepte la connexion
                    if (key.isConnectable()){

                        //Validation de la connexion 
                        sc.finishConnect();
                        System.out.println("finish connect");

                        sc.register(selector, SelectionKey.OP_WRITE);
                    }

                    //Si un message a ete ecrit dans le channel
                    else if (key.isReadable()){
                        SocketChannel sc = (SocketChannel) key.channel();

                            //Lecture du channel par un ByteBuffer, jusqu a ce qu il soit vide
                            message = "";
                            while(sc.read(buffer) > 0){
                                buffer.flip();
                                for (int i = 0; i < buffer.limit(); i++){
                                    message += (char)buffer.get();
                                }
                                buffer.clear();
                            }

                            //Envoie le pseudo au serveur
                            if(message.equals("&pseudo&")){
                                System.out.println("message reçu: "+ message);
                                message = pseudo;
                                ByteBuffer buffer = ByteBuffer.wrap(message.getBytes());    
                                sc.write(buffer);  
                                buffer.clear(); 
                                System.out.println("message envoyé: "+ message);
                            }
    
                            //Envoie le mot de passe au serveur
                            else if(message.equals("&mdp&")){
                                System.out.println("message reçu: "+ message);
                                message = mdp;
                                ByteBuffer buffer = ByteBuffer.wrap(message.getBytes());    
                                sc.write(buffer); 
                                buffer.clear();
                                System.out.println("message envoyé: "+ message);
                            }
    
                            //Demande de déconnexion du serveur
                            else if(message.equals("&deco&")){
                                System.out.println("message reçu: "+ message);
                                Platform.runLater(new Runnable(){
                                    @Override
                                    public void run(){
                                        interfaceClient.disconnectFromServer();
                                    }
                                });
                            }
                            //Connexion validée par le serveur
                            else if(message.equals("&co&")){
                                System.out.println("message reçu: "+ message);
                                message = (pseudo +" vient de se connecter !\n");
                                ByteBuffer buffer = ByteBuffer.wrap(message.getBytes());    
                                sc.write(buffer); 
                                buffer.clear();
                                Platform.runLater(new Runnable(){
                                    @Override
                                    public void run(){
                                        interfaceClient.setStatut("Connecté.");
                                        interfaceClient.clearLog();
                                    }
                                });
                                cpt = 2;
                            }
                            else if(cpt == 2){
                                //Afficher le message lu dans l'interface client en temps reel
                                System.out.println("message reçu: "+ message);
                                final String msg = message;
                                Platform.runLater(new Runnable(){
                                    @Override
                                    public void run(){
                                        interfaceClient.appendMessage(msg);
                                    }
                                });
                            }
                            sc.register(selector, SelectionKey.OP_WRITE);
                    }

                    //Si le client a ecrit dans le channel
                    else if (key.isWritable()){

                        if(cpt == 0){
                            message = "&pseudo&";
                            ByteBuffer buffer = ByteBuffer.wrap(message.getBytes());    
                            sc.write(buffer);
                            System.out.println("message envoyé: "+ message);
                            buffer.clear();
                            cpt = 1;
                        }
                        sc.register(selector, SelectionKey.OP_READ);
                    }
                    iter.remove();
                }
            }

            //Fermeture des SocketChannel et Selector
            this.sc.close(); 
            this.selector.wakeup();
            this.selector.close();
            System.out.println("Arrêt client");
            this.interrupt();

        }catch(IOException e){
            //e.printStackTrace();
            Platform.runLater(new Runnable(){
                @Override
                public void run(){
                    interfaceClient.appendMessage("Echec de la connexion au serveur: "+ ip +":"+ port +".\n");
                    interfaceClient.setStatut("Prêt.");
                    interfaceClient.setDisconnectedState();                 }
            });

        }
    }
}
