package fr.cours.projettetris.tetris;

import android.graphics.Point;

import java.util.Arrays;

public class Grille {
    char[][] grille;
    int score;
    float niveau;

    public Grille(int largeur, int hauteur) {
        this.grille = new char[hauteur][largeur];
        this.score = 0;
        this.niveau = 0f;
    }

    public void addTetraminos(Tetraminos t) {
        for (Point p : t.getTetraminos()) {
            this.grille[p.y][p.x] = t.getType();
        }
        
        this.completeLine();
    }
    
    private void completeLine() {
        boolean line;
        int[] score = new int[] {0, 40, 100, 300, 1200};
        int cpt = 0;
        for (int i = 0; i < grille.length; i++) {
            line = true;
            for (char c : this.grille[i]) {
                if (c == 0) {
                    line = false;
                    break;
                }
            }
            if (line) {
                delLine(i);
                cpt++;
                niveau += 0.3f;
            }
        }

        this.score += score[cpt] * ((int)niveau+1);
    }
    
    private void delLine(int line) {
        if (line >= 0) System.arraycopy(this.grille, 0, this.grille, 1, line);
        grille[0] = new char[grille[0].length];
    }

    public char[][] getGrille() {
        return grille;
    }

    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (char[] chars : grille) {
            str.append(Arrays.toString(chars)).append("\n");
        }
        return str.toString();
    }
}
