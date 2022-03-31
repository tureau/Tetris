package fr.cours.projettetris;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

public class Option extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private ArrayList<String> faconDeJouer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);
        System.out.println("created");

        Spinner spin = (Spinner) findViewById(R.id.spinnerMenu);

        faconDeJouer = new ArrayList<>();
        faconDeJouer.add(Memoire.faconChoisi);
        for (String str : Memoire.faconDeJouer) {
            if (!str.equals(faconDeJouer.get(0)))
                faconDeJouer.add(str);
        }


        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, faconDeJouer);

        if (Memoire.nomJoueur != null)
            ((EditText) findViewById(R.id.editNomJoueur)).setText(Memoire.nomJoueur);

        spin.setOnItemSelectedListener(this);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Memoire.faconChoisi= this.faconDeJouer.get(i);
        Memoire.save();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {}

    @Override
    protected void onStop() {
        Memoire.nomJoueur = ((EditText) findViewById(R.id.editNomJoueur)).getText().toString();
        Memoire.save();
        super.onStop();
    }

    public void supScore(View v) {
        Memoire.meilleurScore.clear();
        this.invalidateOptionsMenu();
    }

    public void menu(View v) {
        finish();
    }
}