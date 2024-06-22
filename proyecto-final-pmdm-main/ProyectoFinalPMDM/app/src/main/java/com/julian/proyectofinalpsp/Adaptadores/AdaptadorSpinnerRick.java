package com.julian.proyectofinalpsp.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.julian.proyectofinalpsp.R;
import com.julian.proyectofinalpsp.Utils.Recetas;
import com.julian.proyectofinalpsp.rickmorty.Personajes;

import java.util.List;

public class AdaptadorSpinnerRick extends BaseAdapter {
    private List<Personajes> listaPersonajes;
    private Context context;

    @Override
    public int getCount() {

        return listaPersonajes.size();
    }

    @Override
    public Object getItem(int position) {
        return listaPersonajes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Personajes personajeActual=listaPersonajes.get(position);
        convertView= LayoutInflater.from(context).inflate(R.layout.fila_spinner_rick,parent,false);
        TextView textoPersonaje=convertView.findViewById(R.id.texto_fila_personajes);
        ImageView imagePersonaje=convertView.findViewById(R.id.img_fila_spinnerRick);
        textoPersonaje.setText(personajeActual.getNombre());
        Glide.with(context).load(personajeActual.getImagen()).into(imagePersonaje);





        return convertView;
    }
    public void rellenarLista(Personajes personaje){
        listaPersonajes.add(personaje);
        notifyDataSetChanged();
    }

    public AdaptadorSpinnerRick(List<Personajes> listaPersonajes, Context context) {
        this.listaPersonajes = listaPersonajes;
        this.context = context;
    }
}
