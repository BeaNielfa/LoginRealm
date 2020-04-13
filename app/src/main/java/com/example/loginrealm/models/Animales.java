package com.example.loginrealm.models;

import com.example.loginrealm.app.MyApp;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Animales extends RealmObject {

    public static final String ANIMAL_ID = "id";
    public static final String ANIMAL_NOMBRE = "nombre";
    public static final String ANIMAL_RAZA = "raza";
    public static final String ANIMAL_COLOR = "color";


    @PrimaryKey
    private long id;
    private String nombre;
    private String color;
    private String urlFoto;
    private String raza;


    public Animales() {
        this.id =  MyApp.ANIMALID.incrementAndGet();
    }

    public Animales(String nombre, String color, String urlFoto, String raza) {
        this.id =  MyApp.ANIMALID.incrementAndGet();
        this.nombre = nombre;
        this.color = color;
        this.urlFoto = urlFoto;
        this.raza = raza;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }
}
