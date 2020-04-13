package com.example.loginrealm.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.loginrealm.R;
import com.example.loginrealm.fragments.EditAnimalDialogFragment;
import com.example.loginrealm.fragments.NuevaAveriaDialogo;
import com.example.loginrealm.interfaces.OnAnimalesInteractionListener;
import com.example.loginrealm.interfaces.OnNuevaAveriaListener;
import com.example.loginrealm.models.Animales;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity implements OnAnimalesInteractionListener, OnNuevaAveriaListener {

    DialogFragment dialogoNuevoAnimal;
    DialogFragment dialogoEditAnimal;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogoNuevoAnimal = new NuevaAveriaDialogo();
                dialogoNuevoAnimal.show(getSupportFragmentManager(),"NuevaAveríaDialogo");
            }
        });

        realm = Realm.getDefaultInstance();
    }

    @Override
    public void onAnimalClick(Animales animal) {
        Intent i = new Intent(this, DetalleAnimalActivity.class);
        i.putExtra(Animales.ANIMAL_ID, animal.getId());
        startActivity(i);
    }

    @Override
    public void onAnimalEdit(Animales animal) {
        dialogoEditAnimal = EditAnimalDialogFragment.newInstance(animal.getId(), animal.getNombre(), animal.getColor(), animal.getRaza());
        dialogoEditAnimal.show(getSupportFragmentManager(), "EditAnimalDialogo");
    }

    @Override
    public void onAnimalEliminar(final Animales animal) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Eliminar Animal");
        builder.setMessage("¿Desea eliminar definitivamente el animal "+animal.getNombre()+"?");
        builder.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        long idAnimalEliminar = animal.getId();
                        Animales animalesEliminar = realm.where(Animales.class).equalTo(Animales.ANIMAL_ID,idAnimalEliminar).findFirst();
                        animalesEliminar.deleteFromRealm();
                    }
                });

                dialog.dismiss();

            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }


    @Override
    public void onAveriaGuardarClickListener(final String nombre, final String color, final String raza) {

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Animales nuevoAnimal = new Animales();
                nuevoAnimal.setNombre(nombre);
                nuevoAnimal.setColor(color);
                nuevoAnimal.setRaza(raza);
                nuevoAnimal.setUrlFoto("");

                realm.copyToRealm(nuevoAnimal);
                Toast.makeText(MainActivity.this, "Animal "+nombre+" guardado", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onAveriaUpdateClickListener(final Long id, final String nombre, final String color, final String raza) {

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Animales nuevoAnimal = new Animales();
                nuevoAnimal.setId(id);
                nuevoAnimal.setNombre(nombre);
                nuevoAnimal.setColor(color);
                nuevoAnimal.setRaza(raza);
                nuevoAnimal.setUrlFoto("");

                realm.copyToRealmOrUpdate(nuevoAnimal);

                Toast.makeText(MainActivity.this, "Animal  actualizado", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
