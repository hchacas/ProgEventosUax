package com.julian.proyectofinalpsp.Utils;

public class Mensaje {
    private String mensaje,urlFoto,nombre,telefono,fotoPerfil,type_mensaje;

    public Mensaje() {
    }

    public Mensaje(String mensaje, String nombre, String telefono, String fotoPerfil, String type_mensaje) {
        this.mensaje = mensaje;
        this.nombre = nombre;
        this.telefono = telefono;
        this.fotoPerfil = fotoPerfil;
        this.type_mensaje = type_mensaje;

    }

    public Mensaje(String mensaje, String urlFoto, String nombre, String telefono, String fotoPerfil, String type_mensaje ) {
        this.mensaje = mensaje;
        this.urlFoto = urlFoto;
        this.nombre = nombre;
        this.telefono = telefono;
        this.fotoPerfil = fotoPerfil;
        this.type_mensaje = type_mensaje;


    }

    public String getMensaje() {
        return mensaje;
    }


    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }


    public String getUrlFoto() {
        return urlFoto;
    }

    public String getType_mensaje() {
        return type_mensaje;
    }
}
