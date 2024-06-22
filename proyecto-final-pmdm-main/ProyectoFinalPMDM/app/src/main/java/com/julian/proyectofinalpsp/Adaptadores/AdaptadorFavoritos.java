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
import com.julian.proyectofinalpsp.Utils.Recetas;

import java.util.ArrayList;

public class AdaptadorFavoritos extends RecyclerView.Adapter<AdaptadorFavoritos.MyHolder>{
    private Context context;
    private ArrayList<Recetas> listaRecetas;
    private AdaptadorRecetas.OnRecetaListener listener;

    public AdaptadorFavoritos(Context context) {
        this.context = context;
        this.listaRecetas = new ArrayList<>();
        this.listener = (AdaptadorRecetas.OnRecetaListener) context;
    }

    @NonNull
    @Override
    public AdaptadorFavoritos.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recycler_receta,parent,false);

        return new AdaptadorFavoritos.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        final Recetas recetaActual = listaRecetas.get(position);
        String uid="";
        Glide.with(context).load(recetaActual.getImagen()).into(holder.imagen);

    }



    @Override
    public int getItemCount() {
        return listaRecetas.size();
    }
    public interface OnEquipoListener {
        void onEquipoSelected(Recetas receta);
    }
    public void rellenarLista(Recetas equipo){
        listaRecetas.add(equipo);
        notifyDataSetChanged();
    }
    public void limpiarLista(){
        listaRecetas.clear();
        notifyDataSetChanged();
    }
    public int verTamañoLista(){
        listaRecetas.size();
        int longitud=listaRecetas.size();
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
