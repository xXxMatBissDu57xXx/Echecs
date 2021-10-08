package game;

public class Plateau {

    /**
     * Plateau de jeu
     */
    private Pion [][] plateau;

    /**
     * Listes des pièces de chaque joueur
     */
    private Pion [] liste_pions1;
    private Pion [] liste_pions2;

    /**
     * Listes de pièces perdues de chaque joueur
     */
    private Pion [] liste_pions_perdus1;
    private Pion [] liste_pions_perdus2;

    public void initialiser(){

        //Création des pièces du premier joueur
        this.liste_pions1[0] = new Pion('R', "joueur 1");
        this.liste_pions1[1] = new Pion('D', "joueur 1");
        for(int i = 0; i < 2; i++){
            this.liste_pions1[i + 2] = new Pion('F', "joueur 1");
        }
        for(int i = 0; i < 2; i++){
            this.liste_pions1[i + 4] = new Pion('C', "joueur 1");
        }
        for(int i = 0; i < 2; i++){
            this.liste_pions1[i + 6] = new Pion('T', "joueur 1");
        }
        for(int i = 0; i < 8; i++){
            this.liste_pions1[i + 8] = new Pion('P', "joueur 1");
        }

        //Création des pièces du second joueur
        this.liste_pions2[0] = new Pion('R', "joueur 2");
        this.liste_pions2[1] = new Pion('D', "joueur 2");
        for(int i = 0; i < 2; i++){
            this.liste_pions2[i + 2] = new Pion('F', "joueur 2");
        }
        for(int i = 0; i < 2; i++){
            this.liste_pions2[i + 4] = new Pion('C', "joueur 2");
        }
        for(int i = 0; i < 2; i++){
            this.liste_pions2[i + 6] = new Pion('T', "joueur 2");
        }
        for(int i = 0; i < 8; i++){
            this.liste_pions2[i + 8] = new Pion('P', "joueur 2");
        }

        //Initialisation du plateau
        this.plateau = new Pion [8][8];

        //Placement des pièces du premier joueur sur le plateau
        this.plateau[0][4] = this.liste_pions1[0];
        this.plateau[0][3] = this.liste_pions1[1];
        this.plateau[0][2] = this.liste_pions1[2];
        this.plateau[0][5] = this.liste_pions1[3];
        this.plateau[0][1] = this.liste_pions1[4];
        this.plateau[0][6] = this.liste_pions1[5];
        this.plateau[0][0] = this.liste_pions1[6];
        this.plateau[0][7] = this.liste_pions1[7];
        for(int i = 0; i < 8; i++){
            this.plateau[1][i] = liste_pions1[i + 8];
        }

        //Placement des pièces du second joueur sur le plateau
        this.plateau[7][4] = this.liste_pions2[0];
        this.plateau[7][3] = this.liste_pions2[1];
        this.plateau[7][2] = this.liste_pions2[2];
        this.plateau[7][5] = this.liste_pions2[3];
        this.plateau[7][1] = this.liste_pions2[4];
        this.plateau[7][6] = this.liste_pions2[5];
        this.plateau[7][0] = this.liste_pions2[6];
        this.plateau[7][7] = this.liste_pions2[7];
        for(int i = 0; i < 8; i++){
            this.plateau[6][i] = liste_pions1[i + 8];
        }

        //Placements de pièces fantômes
        //Elles sont utilisées pour ne pas avoir de case vide dans le tableau plateau
        for(int i = 2; i < 6; i++){
            for(int j = 0; j < 8; i++){
                this.plateau[i][j] = new Pion('F', "");
            }
        }

    }

    public void deplacer(Pion pion, int x, int y){

        if(x >= 0 && x <= 8 && y >= 0 && y <= 8){

            if(pion.getType() == 'P'){

                if(pion.getJoueur().equals("joueur 1")){

                }

                else if(pion.getJoueur().equals("joueur 2")){

                }
            }

            else if(pion.getType() == 'T'){
    
            }

            else if(pion.getType() == 'C'){
        
            }

            else if(pion.getType() == 'F'){
    
            }

            else if(pion.getType() == 'D'){
                
            }

            else if(pion.getType() == 'R'){
    
            }

        }
    }

    public void manger(Pion pion){

    }
    
}
