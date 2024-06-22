package com.julian.proyectofinalpsp.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.julian.proyectofinalpsp.Dialogos.DialogoCond;
import com.julian.proyectofinalpsp.Dialogos.DialogoRecetas;
import com.julian.proyectofinalpsp.R;
import com.julian.proyectofinalpsp.Utils.Recetas;
import com.julian.proyectofinalpsp.fragments.FragmentDetalleRecetas;

import java.util.ArrayList;

public class AdaptadorRecetas extends RecyclerView.Adapter<AdaptadorRecetas.MyHolder> {

    private ArrayList<Recetas> listaRecetas;
    private Context context;
    private OnRecetaListener listener;

    public AdaptadorRecetas(Context context) {
        this.listaRecetas = new ArrayList<>();
        this.context = context;
        this.listener=(OnRecetaListener) context;

    }



    public void agregarReceta (Recetas receta){
        this.listaRecetas.add(receta);
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_fila_recetas,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        Recetas recetaActual = listaRecetas.get(position);
        String uid="";
        holder.nombreReceta.setText(recetaActual.getNombre());
        Glide.with(context).load(recetaActual.getImagen()).placeholder(R.drawable.charla).into(holder.imagenReceta);
        holder.verDetalle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              // ejecutado con la pulsacion de la fila
              listener.onRecetaSelected(recetaActual,uid);


            }
        });

    }

    @Override
    public int getItemCount() {
        return listaRecetas.size();
    }

    public interface OnRecetaListener{

        void onRecetaSelected(Recetas receta,String uid);

    }

    class MyHolder extends RecyclerView.ViewHolder{

        TextView nombreReceta,descripcion,nacionalidad,categoria;
        ImageView imagenReceta;
        Button verDetalle;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            nombreReceta = itemView.findViewById(R.id.nombre_item_receta);
            imagenReceta = itemView.findViewById(R.id.img_item_receta);
            verDetalle = itemView.findViewById(R.id.ver_detalle_receta);
        }

    }
}
