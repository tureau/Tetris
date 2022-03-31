package fr.cours.projettetris;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class Score extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        ((ListView) findViewById(R.id.listScore))
                .setAdapter(new ArrayAdapter<ScoreJoueur>(this, android.R.layout.simple_list_item_1, Memoire.meilleurScore));
    }

    public void menu(View v) {
        finish();
    }
}