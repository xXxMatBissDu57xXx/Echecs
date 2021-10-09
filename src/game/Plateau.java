package game;

import java.util.ArrayList;

public class Plateau {

    /**
     * Plateau de jeu
     */
    private Pion [][] plateau;
    private int [][] plateau_fantome;

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

        //Initialisation du plateau fantôme
        //Il est utilisé pour marquer les cases accessibles par les différentes pièces
        this.plateau_fantome = new int [8][8];
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                this.plateau_fantome[i][j] = 0;
            }
        }
    }

    /**
     * Indique sur quelles cases une pièce peut se déplacer
     * 
     * @param pion
     * @param plateau
     * @param plateau_fantome
     * @return int [][]
     */
    public int [][] deplacable(Pion pion, Pion [][] plateau, int [][] plateau_fantome){

        //Réinitialisation du plateau fantôme
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                this.plateau_fantome[i][j] = 0;
            }
        }

        //Pour un simple pion
        if(pion.getType() == 'P'){

            if(pion.getJoueur() == 1){
                if(pion.getX() < 8){
                    int x = pion.getX() + 1;
                    int y = pion.getY();
                    if((plateau[x][y]).getJoueur() != pion.getJoueur()){
                        //déplaçable
                        plateau_fantome[x][y] = 1;
                    }
                }
            }

            else if(pion.getJoueur() == 2){
                if(pion.getX() >= 0){
                    int x = pion.getX() - 1;
                    int y = pion.getY();
                    if(plateau[x][y].getJoueur() != pion.getJoueur()){
                        //déplaçable
                        plateau_fantome[x][y] = 1;
                    }
                }
            }
        }

        //Pour une tour
        else if(pion.getType() == 'T'){

            for(int i = 0; i < 8; i++){
                if(plateau[i][pion.getY()].getJoueur() != pion.getJoueur()){
                    //déplaçable
                    plateau_fantome[i][pion.getY()] = 1;
                }
            }

            for(int j = 0; j < 8; j++){
                if(plateau[pion.getX()][j].getJoueur() != pion.getJoueur()){
                    //déplaçable
                    plateau_fantome[pion.getX()][j] = 1;
                }
            }
        }

        //Pour un cavalier
        else if(pion.getType() == 'C'){
    
            if(pion.getX() < 6 && pion.getY() < 7){
                if(plateau[pion.getX() + 2][pion.getY() + 1].getJoueur() != pion.getJoueur()){
                    //déplaçable
                    plateau_fantome[pion.getX() + 2][pion.getY() + 1] = 1;
                }
            }

            if(pion.getX() < 6 && pion.getY() > 0){
                if(plateau[pion.getX() + 2][pion.getY() - 1].getJoueur() != pion.getJoueur()){
                    //déplaçable
                    plateau_fantome[pion.getX() + 2][pion.getY() - 1] = 1;
                }
            }

            if(pion.getX() > 1 && pion.getY() < 7){
                if(plateau[pion.getX() - 2][pion.getY() + 1].getJoueur() != pion.getJoueur()){
                    //déplaçable
                    plateau_fantome[pion.getX() - 2][pion.getY() + 1] = 1;
                }
            }

            if(pion.getX() > 1 && pion.getY() > 0){
                if(plateau[pion.getX() - 2][pion.getY() - 1].getJoueur() != pion.getJoueur()){
                    //déplaçable
                    plateau_fantome[pion.getX() - 2][pion.getY() - 1] = 1;
                }
            }

            if(pion.getX() < 7 && pion.getY() < 6){
                if(plateau[pion.getX() + 1][pion.getY() + 2].getJoueur() != pion.getJoueur()){
                    //déplaçable
                    plateau_fantome[pion.getX() + 1][pion.getY() + 2] = 1;
                }
            }

            if(pion.getX() < 7 && pion.getY() > 1){
                if(plateau[pion.getX() + 1][pion.getY() - 2].getJoueur() != pion.getJoueur()){
                    //déplaçable
                    plateau_fantome[pion.getX() + 1][pion.getY() - 2] = 1;
                }
            }

            if(pion.getX() > 0 && pion.getY() < 6){
                if(plateau[pion.getX() - 1][pion.getY() + 2].getJoueur() != pion.getJoueur()){
                    //déplaçable
                    plateau_fantome[pion.getX() - 1][pion.getY() + 2] = 1;
                }
            }

            if(pion.getX() > 0 && pion.getY() > 1){
                if(plateau[pion.getX() - 1][pion.getY() - 2].getJoueur() != pion.getJoueur()){
                    //déplaçable
                    plateau_fantome[pion.getX() - 1][pion.getY() - 2] = 1;
                }
            }
        }

        //Pour un fou
        else if(pion.getType() == 'F'){

            int i = pion.getX() + 1;
            int j = pion.getY() + 1;
            while(i < 8 && j < 8){
                if(plateau[i][j].getJoueur() != pion.getJoueur()){
                    //déplaçable
                    plateau_fantome[i][j] = 1;
                }
                i++;
                j++;
            }

            i = pion.getX() + 1;
            j = pion.getY() - 1;
            while(i < 8 && j >= 0){
                if(plateau[i][j].getJoueur() != pion.getJoueur()){
                    //déplaçable
                    plateau_fantome[i][j] = 1;
                }
                i++;
                j--;
            }

            i = pion.getX() - 1;
            j = pion.getY() + 1;
            while(i >= 0 && j < 8){
                if(plateau[i][j].getJoueur() != pion.getJoueur()){
                    //déplaçable
                    plateau_fantome[i][j] = 1;
                }
                i--;
                j++;
            }

            i = pion.getX() - 1;
            j = pion.getY() - 1;
            while(i >= 0 && j >= 0){
                if(plateau[i][j].getJoueur() != pion.getJoueur()){
                    //déplaçable
                    plateau_fantome[i][j] = 1;
                }
                i--;
                j--;
            }
        }

        //Pour une dame
        else if(pion.getType() == 'D'){
            
            for(int i = 0; i < 8; i++){
                if(plateau[i][pion.getY()].getJoueur() != pion.getJoueur()){
                    //déplaçable
                    plateau_fantome[i][pion.getY()] = 1;                }
            }

            for(int j = 0; j < 8; j++){
                if(plateau[pion.getX()][j].getJoueur() != pion.getJoueur()){
                    //déplaçable
                    plateau_fantome[pion.getX()][j] = 1;
                }
            }

            int i = pion.getX() + 1;
            int j = pion.getY() + 1;
            while(i < 8 && j < 8){
                if(plateau[i][j].getJoueur() != pion.getJoueur()){
                    //déplaçable
                    plateau_fantome[i][j] = 1;
                }
                i++;
                j++;
            }

            i = pion.getX() + 1;
            j = pion.getY() - 1;
            while(i < 8 && j >= 0){
                if(plateau[i][j].getJoueur() != pion.getJoueur()){
                    //déplaçable
                    plateau_fantome[i][j] = 1;
                }
                i++;
                j--;
            }

            i = pion.getX() - 1;
            j = pion.getY() + 1;
            while(i >= 0 && j < 8){
                if(plateau[i][j].getJoueur() != pion.getJoueur()){
                    //déplaçable
                    plateau_fantome[i][j] = 1;
                }
                i--;
                j++;
            }

            i = pion.getX() - 1;
            j = pion.getY() - 1;
            while(i >= 0 && j >= 0){
                if(plateau[i][j].getJoueur() != pion.getJoueur()){
                    //déplaçable
                    plateau_fantome[i][j] = 1;
                }
                i--;
                j--;
            }
        }

        //Pour un roi 
        else if(pion.getType() == 'R'){

            if(pion.getY() < 7){
                int x = pion.getX();
                int y = pion.getY() + 1;
                if(plateau[x][y].getJoueur() != pion.getJoueur()){
                    //déplaçable
                    plateau_fantome[x][y] = 1;
                }
            }

            if(pion.getY() > 0){
                int x = pion.getX();
                int y = pion.getY() - 1;
                if(plateau[x][y].getJoueur() != pion.getJoueur()){
                    //déplaçable
                    plateau_fantome[x][y] = 1;
                }
            }

            if(pion.getX() < 7){
                int x = pion.getX() + 1;
                int y = pion.getY();
                if(plateau[x][y].getJoueur() != pion.getJoueur()){
                    //déplaçable
                    plateau_fantome[x][y] = 1;
                }
            }

            if(pion.getX() < 7 && pion.getY() < 7){
                int x = pion.getX() + 1;
                int y = pion.getY() + 1;
                if(plateau[x][y].getJoueur() != pion.getJoueur()){
                    //déplaçable
                    plateau_fantome[x][y] = 1;
                }
            }

            if(pion.getX() < 7 && pion.getY() > 0){
                int x = pion.getX() + 1;
                int y = pion.getY() - 1;
                if(plateau[x][y].getJoueur() != pion.getJoueur()){
                    //déplaçable
                    plateau_fantome[x][y] = 1;
                }
            }

            if(pion.getX() > 0){
                int x = pion.getX() - 1;
                int y = pion.getY();
                if(plateau[x][y].getJoueur() != pion.getJoueur()){
                    //déplaçable
                    plateau_fantome[x][y] = 1;
                }
            }

            if(pion.getX() > 0 && pion.getY() < 7){
                int x = pion.getX() - 1;
                int y = pion.getY() + 1;
                if(plateau[x][y].getJoueur() != pion.getJoueur()){
                    //déplaçable
                    plateau_fantome[x][y] = 1;
                }
            }

            if(pion.getX() > 0 && pion.getY() > 0){
                int x = pion.getX() - 1;
                int y = pion.getY() - 1;
                if(plateau[x][y].getJoueur() != pion.getJoueur()){
                    //déplaçable
                    plateau_fantome[x][y] = 1;
                }
            }
        }
        return plateau_fantome;
    }

    /**
     * Supprime une pièce du plateau
     * 
     * @param pion
     * @param plateau
     * @param x
     * @param y
     * @return Pion [][]
     */
    public Pion [][] manger(Pion pion, Pion [][] plateau, int x, int y){

        plateau[x][y] = new Pion(99, 'F', 99);

        if(pion.getJoueur() == 1){
            liste_pions_perdus1.add(pion);
            liste_pions1[pion.getId()] = new Pion(99, 'F', 99);
        }
        else if(pion.getJoueur() == 2){
            liste_pions_perdus2.add(pion);
            liste_pions2[pion.getId()] = new Pion(99, 'F', 99);
        }
        return plateau;
    }

    /**
     * Vérifie si un joueur est en "échec et math"
     * 
     * @param plateau
     * @param plateau_fantome
     * @param liste_pions
     * @return boolean
     */
    public boolean checkVictory(Pion [][] plateau, int [][] plateau_fantome, Pion [] liste_pions){

        //Plateau temporaire utilisé comme relais pour le plateau fantôme
        int [][] plateau_tmp = new int [8][8];

        //Réinitialisation du plateau fantôme
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                plateau_fantome[i][j] = 0;
            }
        }

        //Vérification de la portée de chaque pièce
        for (Pion pion : liste_pions) {
            plateau_tmp = deplacable(pion, plateau, plateau_fantome);
            for(int i = 0; i < 8; i++){
                for(int j = 0; j < 8; j++){
                    if(plateau_tmp[i][j] == 1){
                        plateau_fantome[i][j] = 1;
                    }
                }
            }
        }

        //Récupération du roi adverse
        Pion roi = null;
        if(liste_pions[0].getJoueur() == 1){
            for (Pion pion : this.liste_pions2) {
                if(pion.getType() == 'R'){
                    roi = pion;
                    continue;
                }
            }
        }
        else if(liste_pions[0].getJoueur() == 2){
            for (Pion pion : this.liste_pions1) {
                if(pion.getType() == 'R'){
                    roi = pion;
                    continue;
                }
            }
        }

        //Vérification des mouvements possibles du roi adverse
        if(roi.getY() < 7){
            if(plateau_fantome[roi.getX()][roi.getY() + 1] == 0){
                return false;
            }
        }
        if(roi.getY() > 0){
            if(plateau_fantome[roi.getX()][roi.getY() - 1] == 0){
                return false;
            }
        }
        if(roi.getX() < 7){
            if(plateau_fantome[roi.getX() + 1][roi.getY()] == 0){
                return false;
            }
        }
        if(roi.getX() < 7 && roi.getY() < 7){
            if(plateau_fantome[roi.getX() + 1][roi.getY() + 1] == 0){
                return false;
            }
        }
        if(roi.getX() < 7 && roi.getY() > 0){
            if(plateau_fantome[roi.getX() + 1][roi.getY() - 1] == 0){
                return false;
            }
        }
        if(roi.getX() > 0){
            if(plateau_fantome[roi.getX() - 1][roi.getY()] == 0){
                return false;
            }
        }
        if(roi.getX() > 0 && roi.getY() < 7){
            if(plateau_fantome[roi.getX() - 1][roi.getY() + 1] == 0){
                return false;
            }
        }
        if(roi.getX() > 0 && roi.getY() > 0){
            if(plateau_fantome[roi.getX() - 1][roi.getY() - 1] == 0){
                return false;
            }
        }

        return true;
    }
}
