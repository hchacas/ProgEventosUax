package com.julian.proyectofinalpsp.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.julian.proyectofinalpsp.R;
import com.julian.proyectofinalpsp.Utils.MensajeContenedor;
import com.julian.proyectofinalpsp.Utils.MensajesMovil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AdaptadorVer extends RecyclerView.Adapter<MensajeContenedor> {

    private List<MensajesMovil> listMensaje = new ArrayList<>();
    private Context context;

    public AdaptadorVer(Context c) {
        this.context = c;
    }

    public void addMensaje(MensajesMovil m) {
        listMensaje.add(m);
        notifyItemInserted(listMensaje.size());
    }

    @NonNull
    @Override
    public MensajeContenedor onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.card_view_mensaje,parent,false);
        return new MensajeContenedor(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MensajeContenedor holder, int position) {
    holder.getUsuario().setText(listMensaje.get(position).getNombre());
        holder.getTelefono().setText(listMensaje.get(position).getTelefono());
        holder.getMail().setText(listMensaje.get(position).getMensaje());
            Glide.with(context).load(listMensaje.get(position).getUrlFoto()).into(holder.getFotoChat());
        if (listMensaje.get(position).getFotoPerfil().isEmpty()){
            holder.getFotoUsuario().setImageResource(R.drawable.charla);
        }else {
            Glide.with(context).load(listMensaje.get(position).getFotoPerfil()).into(holder.getFotoUsuario());
        }

        Long codeHora = listMensaje.get(position).getHora();
        Date date = new Date(codeHora);
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a");
        holder.getHora().setText(dateFormat.format(date));
    }

    @Override
    public int getItemCount() {
        return listMensaje.size();
    }
}
