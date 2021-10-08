package game;

import java.util.ArrayList;

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
    private ArrayList<Pion> liste_pions_perdus1 = new ArrayList<Pion>();
    private ArrayList<Pion> liste_pions_perdus2 = new ArrayList<Pion>();

    /**
     * Initialisation du plateau et des pièces
     */
    public void initialiser(){

        //Création des pièces du premier joueur
        this.liste_pions1[0] = new Pion(0, 'R', 1);
        this.liste_pions1[1] = new Pion(1, 'D', 1);
        for(int i = 0; i < 2; i++){
            this.liste_pions1[i + 2] = new Pion(i + 2, 'F', 1);
        }
        for(int i = 0; i < 2; i++){
            this.liste_pions1[i + 4] = new Pion(i + 4, 'C', 1);
        }
        for(int i = 0; i < 2; i++){
            this.liste_pions1[i + 6] = new Pion(i + 6, 'T', 1);
        }
        for(int i = 0; i < 8; i++){
            this.liste_pions1[i + 8] = new Pion(i + 8, 'P', 1);
        }

        //Création des pièces du second joueur
        this.liste_pions2[0] = new Pion(0, 'R', 2);
        this.liste_pions2[1] = new Pion(1, 'D', 2);
        for(int i = 0; i < 2; i++){
            this.liste_pions2[i + 2] = new Pion(i + 2, 'F', 2);
        }
        for(int i = 0; i < 2; i++){
            this.liste_pions2[i + 4] = new Pion(i + 4, 'C', 2);
        }
        for(int i = 0; i < 2; i++){
            this.liste_pions2[i + 6] = new Pion(i + 6, 'T', 2);
        }
        for(int i = 0; i < 8; i++){
            this.liste_pions2[i + 8] = new Pion(i + 8, 'P', 2);
        }

        //Initialisation du plateau
        this.plateau = new Pion [8][8];

        //Placement des pièces du premier joueur sur le plateau
        this.plateau[0][4] = this.liste_pions1[0];
        this.plateau[0][4].setX(0);
        this.plateau[0][4].setY(4);
        this.plateau[0][3] = this.liste_pions1[1];
        this.plateau[0][3].setX(0);
        this.plateau[0][3].setY(3);
        this.plateau[0][2] = this.liste_pions1[2];
        this.plateau[0][2].setX(0);
        this.plateau[0][2].setY(2);
        this.plateau[0][5] = this.liste_pions1[3];
        this.plateau[0][5].setX(0);
        this.plateau[0][5].setY(5);
        this.plateau[0][1] = this.liste_pions1[4];
        this.plateau[0][1].setX(0);
        this.plateau[0][1].setY(1);
        this.plateau[0][6] = this.liste_pions1[5];
        this.plateau[0][6].setX(0);
        this.plateau[0][6].setY(6);
        this.plateau[0][0] = this.liste_pions1[6];
        this.plateau[0][0].setX(0);
        this.plateau[0][0].setY(0);
        this.plateau[0][7] = this.liste_pions1[7];
        this.plateau[0][7].setX(0);
        this.plateau[0][7].setY(7);
        for(int i = 0; i < 8; i++){
            this.plateau[1][i] = liste_pions1[i + 8];
        }

        //Placement des pièces du second joueur sur le plateau
        this.plateau[7][4] = this.liste_pions2[0];
        this.plateau[7][4].setX(7);
        this.plateau[7][4].setY(4);
        this.plateau[7][3] = this.liste_pions2[1];
        this.plateau[7][3].setX(7);
        this.plateau[7][3].setY(3);
        this.plateau[7][2] = this.liste_pions2[2];
        this.plateau[7][2].setX(7);
        this.plateau[7][2].setY(2);
        this.plateau[7][5] = this.liste_pions2[3];
        this.plateau[7][5].setX(7);
        this.plateau[7][5].setY(5);
        this.plateau[7][1] = this.liste_pions2[4];
        this.plateau[7][1].setX(7);
        this.plateau[7][1].setY(1);
        this.plateau[7][6] = this.liste_pions2[5];
        this.plateau[7][6].setX(7);
        this.plateau[7][6].setY(6);
        this.plateau[7][0] = this.liste_pions2[6];
        this.plateau[7][0].setX(7);
        this.plateau[7][0].setY(0);
        this.plateau[7][7] = this.liste_pions2[7];
        this.plateau[7][7].setX(7);
        this.plateau[7][7].setY(7);
        for(int i = 0; i < 8; i++){
            this.plateau[6][i] = liste_pions1[i + 8];
        }

        //Placements de pièces fantômes
        //Elles sont utilisées pour ne pas avoir de case vide dans le tableau plateau
        for(int i = 2; i < 6; i++){
            for(int j = 0; j < 8; i++){
                this.plateau[i][j] = new Pion(99, 'F', 0);
            }
        }
    }

    /**
     * Déplace une pièce sur le plateau
     * 
     * @param pion
     * @param x
     * @param y
     */
    public void deplacable(Pion pion){

        if(pion.getType() == 'P'){

            if(pion.getJoueur() == 1){
                if(pion.getX() < 8){
                    if((this.plateau[pion.getX() + 1][pion.getY()]).getJoueur() == 0){
                        //déplaçable
                    }
                    else if((this.plateau[pion.getX() + 1][pion.getY()]).getJoueur() != 1){
                        //mangeable
                    }
                }
            }

            else if(pion.getJoueur() == 2){
                if(pion.getX() >= 0){
                    if(this.plateau[pion.getX() - 1][pion.getY()].getJoueur() == 0){
                        //déplaçable
                    }
                    else if((this.plateau[pion.getX() - 1][pion.getY()]).getJoueur() != 2){
                        //mangeable
                    }
                }
            }
        }

        else if(pion.getType() == 'T'){

            for(int i = 0; i < 8; i++){
                if(this.plateau[i][pion.getY()].getJoueur() == 0){
                    //déplaçable
                }
                else if(this.plateau[i][pion.getY()].getJoueur() != pion.getJoueur()){
                    //mangeable
                }
            }

            for(int j = 0; j < 8; j++){
                if(this.plateau[pion.getX()][j].getJoueur() == 0){
                    //déplaçable
                }
                else if(this.plateau[pion.getX()][j].getJoueur() != pion.getJoueur()){
                    //mangeable
                }
            }
        }

        else if(pion.getType() == 'C'){
    
            if(pion.getX() < 6 && pion.getY() < 7){
                if(this.plateau[pion.getX() + 2][pion.getY() + 1].getJoueur() == 0){
                    //déplaçable
                }
                else if(this.plateau[pion.getX() + 2][pion.getY() + 1].getJoueur() != pion.getJoueur()){
                    //mangeable
                }
            }

            if(pion.getX() < 6 && pion.getY() > 0){
                if(this.plateau[pion.getX() + 2][pion.getY() - 1].getJoueur() == 0){
                    //déplaçable
                }
                else if(this.plateau[pion.getX() + 2][pion.getY() - 1].getJoueur() != pion.getJoueur()){
                    //mangeable
                }
            }

            if(pion.getX() > 1 && pion.getY() < 7){
                if(this.plateau[pion.getX() - 2][pion.getY() + 1].getJoueur() == 0){
                    //déplaçable
                }
                else if(this.plateau[pion.getX() - 2][pion.getY() + 1].getJoueur() != pion.getJoueur()){
                    //mangeable
                }
            }

            if(pion.getX() > 1 && pion.getY() > 0){
                if(this.plateau[pion.getX() - 2][pion.getY() - 1].getJoueur() == 0){
                    //déplaçable
                }
                else if(this.plateau[pion.getX() - 2][pion.getY() - 1].getJoueur() != pion.getJoueur()){
                    //mangeable
                }
            }

            if(pion.getX() < 7 && pion.getY() < 6){
                if(this.plateau[pion.getX() + 1][pion.getY() + 2].getJoueur() == 0){
                    //déplaçable
                }
                else if(this.plateau[pion.getX() + 1][pion.getY() + 2].getJoueur() != pion.getJoueur()){
                    //mangeable
                }
            }

            if(pion.getX() < 7 && pion.getY() > 1){
                if(this.plateau[pion.getX() + 1][pion.getY() - 2].getJoueur() == 0){
                    //déplaçable
                }
                else if(this.plateau[pion.getX() + 1][pion.getY() - 2].getJoueur() != pion.getJoueur()){
                    //mangeable
                }
            }

            if(pion.getX() > 0 && pion.getY() < 6){
                if(this.plateau[pion.getX() - 1][pion.getY() + 2].getJoueur() == 0){
                    //déplaçable
                }
                else if(this.plateau[pion.getX() - 1][pion.getY() + 2].getJoueur() != pion.getJoueur()){
                    //mangeable
                }
            }

            if(pion.getX() > 0 && pion.getY() > 1){
                if(this.plateau[pion.getX() - 1][pion.getY() - 2].getJoueur() == 0){
                    //déplaçable
                }
                else if(this.plateau[pion.getX() - 1][pion.getY() - 2].getJoueur() != pion.getJoueur()){
                    //mangeable
                }
            }
        }

        else if(pion.getType() == 'F'){

            int i = pion.getX() + 1;
            int j = pion.getY() + 1;
            while(i < 8 && j < 8){
                if(this.plateau[i][j].getJoueur() == 0){
                    //déplaçable
                }
                else if(this.plateau[i][j].getJoueur() != pion.getJoueur()){
                    //mangeable
                }
                i++;
                j++;
            }

            i = pion.getX() + 1;
            j = pion.getY() - 1;
            while(i < 8 && j >= 0){
                if(this.plateau[i][j].getJoueur() == 0){
                    //déplaçable
                }
                else if(this.plateau[i][j].getJoueur() != pion.getJoueur()){
                    //mangeable
                }
                i++;
                j--;
            }

            i = pion.getX() - 1;
            j = pion.getY() + 1;
            while(i >= 0 && j < 8){
                if(plateau[i][j].getJoueur() == 0){
                    //déplaçable
                }
                else if(plateau[i][j].getJoueur() != pion.getJoueur()){
                    //mangeable
                }
                i--;
                j++;
            }

            i = pion.getX() - 1;
            j = pion.getY() - 1;
            while(i >= 0 && j >= 0){
                if(plateau[i][j].getJoueur() == 0){
                    //déplaçable
                }
                else if(plateau[i][j].getJoueur() != pion.getJoueur()){
                    //mangeable
                }
                i--;
                j--;
            }
        }

        else if(pion.getType() == 'D'){
            
            for(int i = 0; i < 8; i++){
                if(this.plateau[i][pion.getY()].getJoueur() == 0){
                    //déplaçable
                }
                else if(this.plateau[i][pion.getY()].getJoueur() != pion.getJoueur()){
                    //mangeable
                }
            }

            for(int j = 0; j < 8; j++){
                if(this.plateau[pion.getX()][j].getJoueur() == 0){
                    //déplaçable
                }
                else if(this.plateau[pion.getX()][j].getJoueur() != pion.getJoueur()){
                    //mangeable
                }
            }

            int i = pion.getX() + 1;
            int j = pion.getY() + 1;
            while(i < 8 && j < 8){
                if(this.plateau[i][j].getJoueur() == 0){
                    //déplaçable
                }
                else if(this.plateau[i][j].getJoueur() != pion.getJoueur()){
                    //mangeable
                }
                i++;
                j++;
            }

            i = pion.getX() + 1;
            j = pion.getY() - 1;
            while(i < 8 && j >= 0){
                if(this.plateau[i][j].getJoueur() == 0){
                    //déplaçable
                }
                else if(this.plateau[i][j].getJoueur() != pion.getJoueur()){
                    //mangeable
                }
                i++;
                j--;
            }

            i = pion.getX() - 1;
            j = pion.getY() + 1;
            while(i >= 0 && j < 8){
                if(plateau[i][j].getJoueur() == 0){
                    //déplaçable
                }
                else if(plateau[i][j].getJoueur() != pion.getJoueur()){
                    //mangeable
                }
                i--;
                j++;
            }

            i = pion.getX() - 1;
            j = pion.getY() - 1;
            while(i >= 0 && j >= 0){
                if(plateau[i][j].getJoueur() == 0){
                    //déplaçable
                }
                else if(plateau[i][j].getJoueur() != pion.getJoueur()){
                    //mangeable
                }
                i--;
                j--;
            }
        }

        else if(pion.getType() == 'R'){

            if(pion.getY() < 7){
                if(plateau[pion.getX()][pion.getY() + 1].getJoueur() == 0){
                    //déplaçable
                }
                else if(plateau[pion.getX()][pion.getY() + 1].getJoueur() != pion.getJoueur()){
                    //mangeable
                }
            }

            if(pion.getY() > 0){
                if(plateau[pion.getX()][pion.getY() - 1].getJoueur() == 0){
                    //déplaçable
                }
                else if(plateau[pion.getX()][pion.getY() - 1].getJoueur() != pion.getJoueur()){
                    //mangeable
                }
            }

            if(pion.getX() < 7){
                if(plateau[pion.getX() + 1][pion.getY()].getJoueur() == 0){
                    //déplaçable
                }
                else if(plateau[pion.getX() + 1][pion.getY()].getJoueur() != pion.getJoueur()){
                    //mangeable
                }
            }

            if(pion.getX() < 7 && pion.getY() < 7){
                if(plateau[pion.getX() + 1][pion.getY() + 1].getJoueur() == 0){
                    //déplaçable
                }
                else if(plateau[pion.getX() + 1][pion.getY() + 1].getJoueur() != pion.getJoueur()){
                    //mangeable
                }
            }

            if(pion.getX() < 7 && pion.getY() > 0){
                if(plateau[pion.getX() + 1][pion.getY() - 1].getJoueur() == 0){
                    //déplaçable
                }
                else if(plateau[pion.getX() + 1][pion.getY() - 1].getJoueur() != pion.getJoueur()){
                    //mangeable
                }
            }

            if(pion.getX() > 0){
                if(plateau[pion.getX() - 1][pion.getY()].getJoueur() == 0){
                    //déplaçable
                }
                else if(plateau[pion.getX() - 1][pion.getY()].getJoueur() != pion.getJoueur()){
                    //mangeable
                }
            }

            if(pion.getX() > 0 && pion.getY() < 7){
                if(plateau[pion.getX() - 1][pion.getY() + 1].getJoueur() == 0){
                    //déplaçable
                }
                else if(plateau[pion.getX() - 1][pion.getY() + 1].getJoueur() != pion.getJoueur()){
                    //mangeable
                }
            }

            if(pion.getX() > 0 && pion.getY() > 0){
                if(plateau[pion.getX() - 1][pion.getY() - 1].getJoueur() == 0){
                    //déplaçable
                }
                else if(plateau[pion.getX() - 1][pion.getY() - 1].getJoueur() != pion.getJoueur()){
                    //mangeable
                }
            }
        }
    }

    /**
     * Supprime une pièce du plateau
     * 
     * @param pion
     * @param x
     * @param y
     */
    public void manger(Pion pion, int x, int y){

        this.plateau[x][y] = new Pion(99, 'F', 99);

        if(pion.getJoueur() == 1){
            liste_pions_perdus1.add(pion);
            liste_pions1[pion.getId()] = new Pion(99, 'F', 99);
        }
        else if(pion.getJoueur() == 2){
            liste_pions_perdus2.add(pion);
            liste_pions2[pion.getId()] = new Pion(99, 'F', 99);
        }
    } 
}
