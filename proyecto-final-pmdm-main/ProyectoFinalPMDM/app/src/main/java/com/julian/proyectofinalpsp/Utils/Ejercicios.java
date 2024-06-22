package com.julian.proyectofinalpsp.Utils;

import java.io.Serializable;

public class Ejercicios implements Serializable {
    private String nombre,imagen,descripcion,grupoMuscularTrabajado;

    public Ejercicios(String nombre, String imagen) {
        this.nombre = nombre;
        this.imagen = imagen;
    }

    public Ejercicios() {
    }

    public Ejercicios(String nombre, String imagen, String descripcion, String grupoMuscularTrabajado) {
        this.nombre = nombre;
        this.imagen = imagen;
        this.descripcion = descripcion;
        this.grupoMuscularTrabajado = grupoMuscularTrabajado;
    }

    public Ejercicios(String nombre, String imagen, String grupoMuscularTrabajado) {
        this.nombre = nombre;
        this.imagen = imagen;
        this.grupoMuscularTrabajado = grupoMuscularTrabajado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Ejercicios(String imagen) {
        this.imagen = imagen;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getGrupoMuscularTrabajado() {
        return grupoMuscularTrabajado;
    }

    public void setGrupoMuscularTrabajado(String grupoMuscularTrabajado) {
        this.grupoMuscularTrabajado = grupoMuscularTrabajado;
    }
}
