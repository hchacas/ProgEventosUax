package com.julian.proyectofinalpsp.Utils;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.julian.proyectofinalpsp.R;

public class MensajeContenedor extends RecyclerView.ViewHolder {

    private TextView usuario,telefono,mail,tiempo;

    private ImageView fotoUsuario,fotoChat;

    public MensajeContenedor(@NonNull View itemView) {
        super(itemView);
        usuario = itemView.findViewById(R.id.usuarioMail);
        telefono = itemView.findViewById(R.id.telefonoMail);
        mail = itemView.findViewById(R.id.mensajeMensaje);
        tiempo = itemView.findViewById(R.id.horaMensaje);
        fotoUsuario = itemView.findViewById(R.id.fotoPerfilMensaje);
        fotoChat = itemView.findViewById(R.id.mensajeFoto);
    }

    public TextView getUsuario() {
        return usuario;
    }

    public TextView getTelefono() {
        return telefono;
    }

    public TextView getMail() {
        return mail;
    }


    public TextView getHora() {
        return tiempo;
    }


    public ImageView getFotoUsuario() {
        return fotoUsuario;
    }


    public ImageView getFotoChat() {
        return fotoChat;
    }


}
