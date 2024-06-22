package com.julian.proyectofinalpsp.Utils;

import java.util.Map;

public class MensajeAjeno extends Mensaje {
    private Map hora;

    public MensajeAjeno() {
    }

    public MensajeAjeno(Map hora) {
        this.hora = hora;
    }

    public MensajeAjeno(String mensaje, String nombre, String telefono, String fotoPerfil, String type_mensaje, Map hora) {
        super(mensaje, nombre,telefono, fotoPerfil, type_mensaje);
        this.hora = hora;
    }

    public MensajeAjeno(String mensaje, String urlFoto, String nombre, String telefono, String fotoPerfil, String type_mensaje, Map hora) {
        super(mensaje, urlFoto, nombre,telefono, fotoPerfil, type_mensaje);
        this.hora = hora;
    }

    public Map getHora() {
        return hora;
    }

    public void setHora(Map hora) {
        this.hora = hora;
    }
}
