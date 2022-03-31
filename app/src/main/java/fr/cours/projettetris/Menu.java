package fr.cours.projettetris;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        Memoire.menu = this;
        Memoire.load();
    }

    public void jouer(View v) {
        startActivity(new Intent(this, Tetris.class));
    }

    public void option(View v) {
        startActivity(new Intent(this, Option.class));
    }

    public void score(View v) {
        startActivity(new Intent(this, Score.class));
    }

    public void quitter(View v) {
        this.finish();
    }

}