package game;

public class Pion {
    
    /**
     * Id de la pièce
     */
    private int id;

    /**
     * Type de pièce
     *   - R = roi
     *   - D = dame
     *   - F = fou
     *   - C = cavalier
     *   - T = tour
     *   - F = fantôme
     */
    private char type;

    /**
     * Joueur auquel appartient la pièce
     */
    private int joueur;

    /**
     * Coordonnées de la pièce sur le plateau
     */
    private int x;
    private int y;

    /**
     * Retourne l'id de la pièce
     * 
     * @return int
     */
    public int getId(){
        return this.id;
    }
    /**
     * Retourne le type de la pièce
     * 
     * @return String 
     */
    public char getType(){
        return this.type;
    }
    
    /**
     * Retourne le nom du joueur possédant la pièce
     * 
     * @return String
     */
    public int getJoueur(){
        return this.joueur;
    }

    /**
     * Retourne la valeur de x
     * 
     * @return int
     */
    public int getX(){
        return this.x;
    }

    /**
     * Retourne la valeur de y
     * 
     * @return
     */
    public int getY(){
        return this.y; 
    }

    /**
     * Modifie la valeur de x
     * 
     * @param x
     */
    public void setX(int x){
        this.x = x;
    }

    /**
     * Modifie la valeur de y
     * 
     * @param y
     */
    public void setY(int y){
        this.y = y;
    }

    /**
     * Constructeur
     * 
     * @param type
     * @param joueur
     */
    public Pion(int id, char type, int joueur){
        this.id = id;
        this.type = type;
        this.joueur = joueur;
    }

}
