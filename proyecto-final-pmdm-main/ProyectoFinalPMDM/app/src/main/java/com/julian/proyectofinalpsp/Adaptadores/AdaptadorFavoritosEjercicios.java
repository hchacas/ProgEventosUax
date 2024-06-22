package com.julian.proyectofinalpsp.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.julian.proyectofinalpsp.R;
import com.julian.proyectofinalpsp.Utils.Ejercicios;
import com.julian.proyectofinalpsp.Utils.Recetas;

import java.security.interfaces.ECKey;
import java.util.ArrayList;

public class AdaptadorFavoritosEjercicios extends RecyclerView.Adapter<AdaptadorFavoritosEjercicios.MyHolder>{
    private Context context;
    private ArrayList<Ejercicios> listaEjercicios;
    private AdaptadorEjercicios.OnEjercicioListener listener;

    public AdaptadorFavoritosEjercicios(Context context) {
        this.context = context;
        this.listaEjercicios = new ArrayList<>();
        this.listener = (AdaptadorEjercicios.OnEjercicioListener) context;
    }

    @NonNull
    @Override
    public AdaptadorFavoritosEjercicios.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recycler_receta,parent,false);

        return new AdaptadorFavoritosEjercicios.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        final Ejercicios ejercicioActual = listaEjercicios.get(position);
        String uid="";
        Glide.with(context).load(ejercicioActual.getImagen()).into(holder.imagen);

    }



    @Override
    public int getItemCount() {
        return listaEjercicios.size();
    }
    public interface OnEquipoListener {
        void onEquipoSelected(Ejercicios ejercicio);
    }
    public void rellenarLista(Ejercicios ejercicio){
        listaEjercicios.add(ejercicio);
        notifyDataSetChanged();
    }
    public void limpiarLista(){
        listaEjercicios.clear();
        notifyDataSetChanged();
    }
    public int verTamañoLista(){
        listaEjercicios.size();
        int longitud=listaEjercicios.size();
        notifyDataSetChanged();
        return longitud;
    }

    class MyHolder extends RecyclerView.ViewHolder{

        private ImageView imagen;
        private Button boton;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            imagen = itemView.findViewById(R.id.imagen_equipo_recycler);
        }

        public ImageView getImagen() {
            return imagen;
        }

        public Button getBoton() {
            return boton;
        }
    }
}
