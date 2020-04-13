package com.example.loginrealm.fragments;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.loginrealm.R;
import com.example.loginrealm.interfaces.OnNuevaAveriaListener;
import com.example.loginrealm.models.Animales;


public class EditAnimalDialogFragment extends DialogFragment {

    AlertDialog.Builder builder;
    OnNuevaAveriaListener mListener;
    View v;
    EditText etNombre, etColor, etRaza;
    Context ctx;

    private long idAnimal;
    private String nombre, raza, color;


    public EditAnimalDialogFragment() {
        // Required empty public constructor
    }


    public static EditAnimalDialogFragment newInstance(long idAnimal, String nombre, String color, String raza) {
        EditAnimalDialogFragment fragment = new EditAnimalDialogFragment();
        Bundle args = new Bundle();
        args.putLong(Animales.ANIMAL_ID, idAnimal);
        args.putString(Animales.ANIMAL_NOMBRE, nombre);
        args.putString(Animales.ANIMAL_COLOR, color);
        args.putString(Animales.ANIMAL_RAZA, raza);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idAnimal = getArguments().getLong(Animales.ANIMAL_ID);
            nombre = getArguments().getString(Animales.ANIMAL_NOMBRE);
            color = getArguments().getString(Animales.ANIMAL_COLOR);
            raza = getArguments().getString(Animales.ANIMAL_RAZA);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        builder =  new AlertDialog.Builder(getActivity());

        ctx = getActivity();

        LayoutInflater inflater = getActivity().getLayoutInflater();

        v = inflater.inflate(R.layout.dialogo_nueva_averia, null);

        etNombre = (EditText) v.findViewById(R.id.txtNombre);
        etColor = (EditText) v.findViewById(R.id.txtColor);
        etRaza = (EditText) v.findViewById(R.id.txtRaza);

        etNombre.setText(nombre);
        etColor.setText(color);
        etRaza.setText(raza);

        builder.setView(v);

        builder.setTitle("Editar Animal")
                .setPositiveButton("Actualizar",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {



                        String nombre = etNombre.getText().toString();
                        String color = etColor.getText().toString();
                        String raza = etRaza.getText().toString();

                        if (!nombre.isEmpty() && !raza.isEmpty() && !color.isEmpty()){
                            mListener.onAveriaUpdateClickListener(idAnimal, nombre, color, raza);
                        } else  {
                            Toast.makeText(ctx, "Introduzca todos los datos", Toast.LENGTH_SHORT).show();
                        }



                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            mListener = (OnNuevaAveriaListener)context;
        }catch (ClassCastException e){
            throw new ClassCastException((context.toString()+" must implement OnNuevaAveriaListener"));
        }
    }
}
