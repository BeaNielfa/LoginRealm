package com.example.loginrealm.interfaces;

import com.example.loginrealm.models.Animales;

public interface OnAnimalesInteractionListener {
    public void onAnimalClick (Animales animal);
    public void onAnimalEdit (Animales animal);
    public void onAnimalEliminar (Animales animal);
}

