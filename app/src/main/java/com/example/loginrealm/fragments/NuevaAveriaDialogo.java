package com.example.loginrealm.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.loginrealm.R;
import com.example.loginrealm.interfaces.OnNuevaAveriaListener;

public class NuevaAveriaDialogo  extends DialogFragment {
    AlertDialog.Builder builder;
    OnNuevaAveriaListener mListener;
    View v;
    EditText etNombre, etColor, etRaza;
    Context ctx;


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

        builder.setView(v);

        builder.setTitle("Nuevo Animal")
                .setPositiveButton("Guardar",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {



                        String nombre = etNombre.getText().toString();
                        String color = etColor.getText().toString();
                        String raza = etRaza.getText().toString();

                        if (!nombre.isEmpty() && !raza.isEmpty() && !color.isEmpty()){
                            mListener.onAveriaGuardarClickListener(nombre, color, raza);
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
