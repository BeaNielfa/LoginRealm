package com.example.loginrealm.activities;

import android.os.Bundle;

import com.example.loginrealm.models.Animales;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.TextView;

import com.example.loginrealm.R;

import io.realm.Realm;

public class DetalleAnimalActivity extends AppCompatActivity {

    TextView tvInfo;
    long idAnimal;
    Realm realm;
    Animales animales;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_animal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tvInfo = (TextView) findViewById(R.id.textoAnimal);

        Bundle extras = getIntent().getExtras();
        idAnimal = extras.getLong(Animales.ANIMAL_ID);

        realm = Realm.getDefaultInstance();

        animales = realm.where(Animales.class).equalTo(Animales.ANIMAL_ID, idAnimal).findFirst();

        setTitle(animales.getNombre());
        tvInfo.setText("Color: "+animales.getColor()+" \n Raza:"+animales.getRaza());


    }
}
