package game;

public class Pion {
    
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
    private String joueur;

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
    public String getJoueur(){
        return this.joueur;
    }

    /**
     * Constructeur
     * 
     * @param type
     * @param joueur
     */
    public Pion(char type, String joueur){
        this.type = type;
        this.joueur = joueur;
    }

}
