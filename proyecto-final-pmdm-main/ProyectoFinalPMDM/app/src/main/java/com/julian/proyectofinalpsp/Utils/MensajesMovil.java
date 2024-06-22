package com.julian.proyectofinalpsp.Utils;

public class MensajesMovil extends Mensaje{
    private Long hora;

    public MensajesMovil() {
    }

    public MensajesMovil(Long hora) {
        this.hora = hora;
    }

    public MensajesMovil(String mensaje, String urlFoto, String nombre, String telefono, String fotoPerfil, String type_mensaje, Long hora) {
        super(mensaje, urlFoto, nombre,telefono, fotoPerfil, type_mensaje);
        this.hora = hora;
    }

    public Long getHora() {
        return hora;
    }

    public void setHora(Long hora) {
        this.hora = hora;
    }
}
