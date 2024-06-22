package com.julian.proyectofinalpsp.Utils;

import java.io.Serializable;

public class Recetas implements Serializable {
    private String nombre,categoria,nacionalidad,descripcion,imagen;

    public Recetas(String nombre, String categoria, String nacionalidad, String descripcion, String imagen) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.nacionalidad = nacionalidad;
        this.descripcion=descripcion;
        this.imagen = imagen;

    }

    public Recetas() {
    }

    public Recetas(String nombre, String imagen) {
        this.nombre = nombre;
        this.imagen=imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }


    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
