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
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.julian.proyectofinalpsp.R;
import com.julian.proyectofinalpsp.Utils.Ejercicios;
import com.julian.proyectofinalpsp.Utils.Recetas;

import java.util.ArrayList;

public class AdaptadorEjercicios extends RecyclerView.Adapter<AdaptadorEjercicios.MyHolder> {

    private ArrayList<Ejercicios> listaEjercicios;
    private Context context;
    private OnEjercicioListener listener;

    public AdaptadorEjercicios(Context context) {
        this.listaEjercicios = new ArrayList<>();
        this.context = context;
        this.listener=(OnEjercicioListener) context;

    }



    public void agregarEjercicio (Ejercicios ejercicio){
        this.listaEjercicios.add(ejercicio);
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_fila_ejercicios,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        String uid="";
        Ejercicios ejercicioActual = listaEjercicios.get(position);
        holder.nombreEjercicio.setText(ejercicioActual.getNombre());
        Glide.with(context).load(ejercicioActual.getImagen()).placeholder(R.drawable.charla).into(holder.ejercicio);
        holder.verDetalle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              // ejecutado con la pulsacion de la fila
              listener.onEjercicioSelected(ejercicioActual,uid);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaEjercicios.size();
    }

    public interface OnEjercicioListener{

        void onEjercicioSelected(Ejercicios ejercicio,String uid);

    }

    class MyHolder extends RecyclerView.ViewHolder{

        TextView nombreEjercicio;
        ImageView ejercicio;
        Button verDetalle;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            nombreEjercicio = itemView.findViewById(R.id.nombre_item_ejercicio);
            ejercicio = itemView.findViewById(R.id.img_item_ejercicio);
            verDetalle = itemView.findViewById(R.id.ver_item_ejercicio);
        }

    }
}
