package client;

import javafx.application.Platform;

import java.io.IOException;
import java.net.ConnectException;
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
    private String name;
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
     * @param name
     * @param mdp
     */
    public Client(ClientUI interfaceClient, String ip, int port, String name, String mdp){
        this.ip = ip;
        this.port = port;
        this.name = name;
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
        String finalMessage = name +": "+ message;
        ByteBuffer buffer = ByteBuffer.wrap(finalMessage.getBytes());    
        tc.sc.write(buffer);

        //Selection de l operation de demande de connexion dans le channel, sur le selecteur
        tc.sc.register(tc.selector, SelectionKey.OP_WRITE);
    }

    /**
     * Demarre un thread Client afin de ne pas bloquer l'interface client
     */
    public void startClient(){
        tc = new Client(interfaceClient, ip, port, name, mdp);
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

            //Selection de l operation de demande de connexion dans le channel, sur le selecteur
            sc.register(selector, SelectionKey.OP_CONNECT);

            //Tentative de connexion a un ServerSocketChannel
            sc.connect(new InetSocketAddress(ip, port));

            int cpt = 0;

            while(interfaceClient.getRunning()){

                //Recherche d une operation selectionnee sur le selecteur
                selector.selectNow();

                //Creation de liste d operations selectionnees
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> iter = selectedKeys.iterator();
                while (iter.hasNext()){
                    SelectionKey key = iter.next();

                    //Si le serveur accepte la connexion
                    if (key.isConnectable()){

                        //Validation de la connexion 
                        sc.finishConnect();
                        System.out.println("finish connect");

                        //Selection de l operation d ecriture dans le channel, sur le selecteur
                        sc.register(selector, SelectionKey.OP_READ);
                    }

                    //Si un message a ete ecrit dans le channel
                    else if (key.isReadable()){

                        //Recuperation du channel
                        SocketChannel sc = (SocketChannel) key.channel();

                        String message = null;
                        System.out.println(cpt);

                        if(cpt > 0){
                            //Envoi du nom de l'utilisateur au serveur
                            message = name +" vient de se connecter !\n";
                            buffer=ByteBuffer.wrap(message.getBytes());
                            sc.write(buffer);

                            //Mise a jour de l interface client en temps reel
                            Platform.runLater(new Runnable(){
                                @Override
                                public void run(){
                                    interfaceClient.setStatut("Connecté.");
                                    interfaceClient.clearLog();
                                }
                            });
                        }
                        else if(cpt == 0){
                            message = "&pseudo&";
                            buffer = ByteBuffer.wrap(message.getBytes());
                            sc.write(buffer);
                            cpt ++;
                        }
                        else{

                            //Lecture du channel par un ByteBuffer, jusqu a ce qu il soit vide
                            while(sc.read(buffer) > 0){
                                buffer.flip();
                                for (int i = 0; i < buffer.limit(); i++){
                                    message += (char)buffer.get();
                                }
                                buffer.clear();
                            }

                            if(message.equals("&pseudo&")){
                                message = name;
                                ByteBuffer buffer = ByteBuffer.wrap(message.getBytes());    
                                sc.write(buffer);   
                            }
    
                            else if(message.equals("&mdp&")){
                                message = mdp;
                                ByteBuffer buffer = ByteBuffer.wrap(message.getBytes());    
                                sc.write(buffer); 
                            }
    
                            else if(message.equals("&deco&")){
                                Platform.runLater(new Runnable(){
                                    @Override
                                    public void run(){
                                        interfaceClient.disconnectFromServer();
                                    }
                                });
                            }

                            //Afficher le message lu dans l'interface client en temps reel
                            final String msg = message;
                            Platform.runLater(new Runnable(){
                                @Override
                                public void run(){
                                    interfaceClient.appendMessage(msg);
                                }
                            });

                            sc.register(selector, SelectionKey.OP_WRITE);

                        }

                    }

                    //Si le client a ecrit dans le channel
                    else if (key.isWritable()){

                        //Selection de l operation de lecture du channel, sur le selecteur
                        sc.register(selector, SelectionKey.OP_READ);

                        //Vider le buffer
                        buffer.clear();
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
