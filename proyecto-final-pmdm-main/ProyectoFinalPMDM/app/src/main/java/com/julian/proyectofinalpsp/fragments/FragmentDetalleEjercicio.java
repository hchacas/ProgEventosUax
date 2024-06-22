package com.julian.proyectofinalpsp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.julian.proyectofinalpsp.R;
import com.julian.proyectofinalpsp.Utils.Ejercicios;
import com.julian.proyectofinalpsp.Utils.Recetas;


public class FragmentDetalleEjercicio extends Fragment {

    private View view;
    private ImageView imagen;
    private TextView nombre,grupoMuscular,descripcion;
    private Ejercicios ejercicioRecuperado;



    public static FragmentDetalleEjercicio newInstance(Ejercicios ejercicio,String uid){
        Bundle args =new Bundle();
        args.putSerializable("ejercicio",ejercicio);
        args.putSerializable("uid",uid);
        FragmentDetalleEjercicio fragment = new FragmentDetalleEjercicio();
        fragment.setArguments(args);
        return fragment;


    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(this.getArguments() !=null){
            ejercicioRecuperado =(Ejercicios) this.getArguments().getSerializable("ejercicio");

        }


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.fragment_detalle_ejercicio,container,false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        instancias();
        iniciarVista();
    }



    private void iniciarVista() {
        nombre.setText(ejercicioRecuperado.getNombre());
        descripcion.setText(ejercicioRecuperado.getDescripcion());
        grupoMuscular.setText(ejercicioRecuperado.getGrupoMuscularTrabajado());
        Glide.with(getContext()).load(ejercicioRecuperado.getImagen()).into(imagen);


    }

    private void instancias() {
        imagen=view.findViewById(R.id.imagen_detalle_ejercicio);
        nombre=view.findViewById(R.id.nombre_detalle_ejercicio);
        descripcion=view.findViewById(R.id.descripcion_detalle_ejercicio);
        grupoMuscular=view.findViewById(R.id.grupoMuscular_detalle_ejercicio);


    }
}
