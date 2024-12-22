package com.example.drache_o_metre;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.LinearLayoutManager;


public class Settings extends AppCompatActivity {

    private Switch notificationSwitch;
    private Spinner freqSpinner;
    private Button back;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Initialisation du spinner pour choisir la fréquence d'actualisation
        Spinner freqSpinner = findViewById(R.id.freqSpinner);
        //freqSpinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this); // Pour récupérer la donnée choisie dans le spinner

        // Insertion des valeurs du tableau freqRefresh dans le spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.freqRefresh,
                android.R.layout.simple_spinner_item
        );
        // Précisions sur l'affichage lorsqu'on clique sur le spinner
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        freqSpinner.setAdapter(adapter);


        //Initialisation de la switch pour les préférences de notification
        Switch notificationSwitch = findViewById(R.id.notificationSwitch);

        notificationSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(notificationSwitch.isChecked()){
                    //TODO : valeurs pour l'envoi de messages
                    System.out.println("ok");
                } else {
                    //Supprimer les notifications de météo
                    System.out.println("ko");

                }
            }
        });

        // Initialisation du bouton back pour revenir à la main activity.
        Button back = findViewById(R.id.backMain);

        // Ajout d'un action listener pour venir à la main activity.
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Settings.this, CurrentWeather.class);
                startActivity(intent);
            }
        });



    }
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        parent.getItemAtPosition(pos);

        //Pour récupérer les informations
    }
    public void onNothingSelected(AdapterView<?> parent) {
        //Another interface callback;
    }
}

