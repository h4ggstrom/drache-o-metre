package com.example.drache_o_metre;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

public class Settings extends AppCompatActivity {

    private Switch notificationSwitch;
    private Spinner freqSpinner;
    private Button back;
    private Button moreButton;
    private Button applySpinner;

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
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        freqSpinner.setAdapter(adapter);

        // Pour afficher l'élément en sharedPreference au devant
        int pos = getFreqSpinnerPref();
        freqSpinner.setSelection(pos);

        //Initialisation du bouton apply pour le spinner
        applySpinner = findViewById(R.id.applySpinner);

        applySpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String spinnerString = (String)freqSpinner.getSelectedItem();

                SharedPreferences sharedPref = getSharedPreferences("freqSpinner", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("freqSpinner", spinnerString);
                editor.apply();
            }
        });

        //Initialisation de la switch pour les préférences de notification
        Switch notificationSwitch = findViewById(R.id.notificationSwitch);

        // Si la préférence est oui, elle coche le switch.
        if(isMeteoNotifSelected()) {
            notificationSwitch.setChecked(true);
        }

        notificationSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPref = getSharedPreferences("meteoNotification",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                if(notificationSwitch.isChecked()){
                    editor.putString("meteoNotification", "true");
                } else {
                    editor.putString("meteoNotification", "false");
                }
                editor.apply();
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

        // Ajout du bouton pour les informations supplémentaires.
        Button moreButton = findViewById(R.id.moreButton);
        moreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtube.com/shorts/xkAr7jMtETI?si=W2Bwe8b9BGmgo4JH"));
                startActivity(intent);
            }
        });

    }

    public boolean isMeteoNotifSelected(){
        // renvoie la val de météonotif en booleen
        SharedPreferences sharedPref = getSharedPreferences("meteoNotification", MODE_PRIVATE);
        String tmp = sharedPref.getString("meteoNotification", "");
        if(tmp.equals("true")) {
            return true;
        }
        return false;
    }

    public int getFreqSpinnerPref() {
        SharedPreferences sharedPref = getSharedPreferences("freqSpinner", MODE_PRIVATE);
        String tmp = sharedPref.getString("freqSpinner", "");
        if(tmp.equals("High")){
            return 0;
        } else if (tmp.equals("Balanced")) {
            return 1;
        }
        return 2;
    }
}

