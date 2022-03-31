package fr.cours.projettetris;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Memoire {
    public static String[] faconDeJouer = new String[] {"Accéléromètre", "Swipe", "Scroll"};
    public static String faconChoisi = "Scroll";
    public static String nomJoueur = "Joueur";
    public static ArrayList<ScoreJoueur> meilleurScore = new ArrayList<>();
    public static Menu menu;

    public static void ajouterScore(int score) {
        meilleurScore.add(new ScoreJoueur(nomJoueur, score));
        Collections.sort(meilleurScore);
        if (meilleurScore.size() > 10)
            meilleurScore.remove(meilleurScore.size()-1);

    }

    public static void save() {
        String tableauScore = "";
        for (ScoreJoueur sj : meilleurScore) {
            tableauScore += sj.nom+","+sj.score+";";
        }

        SharedPreferences sharedPreferences = menu.getSharedPreferences("shared_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("FaconChoisi", faconChoisi);
        editor.putString("NomJoueur", nomJoueur);
        editor.putString("TableauScore", tableauScore);

        editor.apply();
    }

    public static void load() {
        SharedPreferences sharedPreferences = menu.getSharedPreferences("shared_prefs", MODE_PRIVATE);

        faconChoisi = sharedPreferences.getString("FaconChoisi", "Scroll");
        nomJoueur = sharedPreferences.getString("NomJoueur", "Joueur");
        String tabScore = sharedPreferences.getString("TableauScore", " ");

        if (tabScore.length() < 1) return;

        meilleurScore = new ArrayList<>();
        for (String str : tabScore.split(";")) {
            int posV = str.lastIndexOf(',');
            if (posV > -1 ) meilleurScore.add(new ScoreJoueur(str.substring(0, posV), Integer.parseInt(str.substring(posV+1))));
        }
    }
}

class ScoreJoueur implements Comparable<ScoreJoueur> {
    public String nom;
    public int score;

    public ScoreJoueur(String nom, int score) {
        this.nom = nom;
        this.score = score;
    }

    @Override
    public int compareTo(ScoreJoueur j) {
        return j.score - this.score;
    }

    @Override
    public String toString() {
        return nom + " : " + score;
    }
}
