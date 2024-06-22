package com.julian.proyectofinalpsp.rickmorty;

import java.io.Serializable;

public class Personajes  {
    private String nombre, especie, imagen;

    public Personajes(String nombre, String especie, String imagen) {
        this.nombre = nombre;
        this.especie = especie;
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}