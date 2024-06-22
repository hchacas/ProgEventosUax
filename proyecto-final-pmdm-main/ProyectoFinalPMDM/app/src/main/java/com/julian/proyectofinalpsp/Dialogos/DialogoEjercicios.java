package com.julian.proyectofinalpsp.Dialogos;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.julian.proyectofinalpsp.Adaptadores.AdaptadorEjercicios;
import com.julian.proyectofinalpsp.Adaptadores.AdaptadorRecetas;
import com.julian.proyectofinalpsp.R;
import com.julian.proyectofinalpsp.Utils.Ejercicios;
import com.julian.proyectofinalpsp.Utils.Recetas;
import com.julian.proyectofinalpsp.fragments.FragmentDetalleEjercicio;
import com.julian.proyectofinalpsp.fragments.FragmentDetalleRecetas;

public class DialogoEjercicios extends DialogFragment implements AdaptadorEjercicios.OnEjercicioListener, View.OnClickListener {
    private View view;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private ImageView imagen;
    private TextView nombre,grupoMuscular,descripcion;
    private Ejercicios ejercicioRecuperado;
    private Button addEjercicio,close;
    private FirebaseDatabase firebaseDatabase;
    private  String uid="";
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        instancias();
       iniciarVista();
        addEjercicio=view.findViewById(R.id.agregar_ejercicio);
        close=view.findViewById(R.id.cerrar_ventana_ejercicio);
        acciones();
        return builder.create();
    }

    private void acciones() {
        addEjercicio.setOnClickListener(this);
        close.setOnClickListener(this);
    }

    private void iniciarVista() {
        nombre.setText(ejercicioRecuperado.getNombre());
        grupoMuscular.setText(ejercicioRecuperado.getGrupoMuscularTrabajado());
        descripcion.setText(ejercicioRecuperado.getDescripcion());
        Glide.with(getContext()).load(ejercicioRecuperado.getImagen()).into(imagen);


    }
    private void instancias() {
        imagen=view.findViewById(R.id.imagen_detalle_ejercicio);
        nombre=view.findViewById(R.id.nombre_detalle_ejercicio);
        descripcion=view.findViewById(R.id.descripcion_detalle_ejercicio);
        grupoMuscular=view.findViewById(R.id.grupoMuscular_detalle_ejercicio);
        firebaseDatabase = FirebaseDatabase.getInstance("https://proyectofinalpsp-7c012-default-rtdb.europe-west1.firebasedatabase.app/");


    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        view = View.inflate(context, R.layout.fragment_detalle_ejercicio, null);
        if(this.getArguments() !=null){
            ejercicioRecuperado =(Ejercicios) this.getArguments().getSerializable("ejercicio");
            uid = getArguments().getString("uid");

        }

    }
    public static DialogoEjercicios newInstance(Ejercicios ejercicio,String uid){
        Bundle args =new Bundle();
        args.putSerializable("ejercicio",ejercicio);
        args.putString("uid",uid);
        DialogoEjercicios dialogoEjercicios = new DialogoEjercicios();
        dialogoEjercicios.setArguments(args);
        return dialogoEjercicios;


    }


    @Override
    public void onEjercicioSelected(Ejercicios ejercicio,String uid) {
        fragmentManager = getParentFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.sitio_fragment, FragmentDetalleEjercicio.newInstance(ejercicio,uid));
        fragmentTransaction.addToBackStack("fdetalle");
        fragmentTransaction.commit();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.agregar_ejercicio:
                DatabaseReference nodoReferencia = firebaseDatabase.getReference("Usuarios").child(uid).child("ejercicios").child(ejercicioRecuperado.getNombre());
                nodoReferencia.setValue(ejercicioRecuperado);
                Toast.makeText(getContext(), "Ejercicio agregado correctamente", Toast.LENGTH_SHORT).show();

                break;
            case R.id.cerrar_ventana_ejercicio:
                dismiss();
                break;
        }
    }

}
