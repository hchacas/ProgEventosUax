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
import com.google.firebase.database.Query;
import com.julian.proyectofinalpsp.Adaptadores.AdaptadorRecetas;
import com.julian.proyectofinalpsp.R;
import com.julian.proyectofinalpsp.Utils.Ejercicios;
import com.julian.proyectofinalpsp.Utils.Recetas;
import com.julian.proyectofinalpsp.fragments.FragmentDetalleEjercicio;
import com.julian.proyectofinalpsp.fragments.FragmentDetalleRecetas;

public class DialogoRecetas extends DialogFragment implements AdaptadorRecetas.OnRecetaListener, View.OnClickListener {
    private View view;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private ImageView imagen;
    private TextView nombre,categoria,descripcion,nacionalidad;
    private Recetas recetaaRecuperda;
    private Button addReceta,close;
    private  String uid="";
    private FirebaseDatabase firebaseDatabase;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        instancias();
        iniciarVista();

        acciones();
        return builder.create();
    }

    private void iniciarVista() {
        nombre.setText(recetaaRecuperda.getNombre());
        descripcion.setText(recetaaRecuperda.getDescripcion());
        nacionalidad.setText(recetaaRecuperda.getNacionalidad());
        categoria.setText(recetaaRecuperda.getCategoria());
        Glide.with(getContext()).load(recetaaRecuperda.getImagen()).into(imagen);


    }
    private void instancias() {
        addReceta=view.findViewById(R.id.agregar_receta);
        close=view.findViewById(R.id.cerrar_ventana_recetas);
        imagen=view.findViewById(R.id.imagen_detalle_recetas);
        nombre=view.findViewById(R.id.nombre_detalle_recetas);
        descripcion=view.findViewById(R.id.descipcion_detalle_recetas);
        categoria=view.findViewById(R.id.categoria_detalle_recetas);
        nacionalidad=view.findViewById(R.id.nacionalidad_detalle_recetas);
        firebaseDatabase = FirebaseDatabase.getInstance("https://proyectofinalpsp-7c012-default-rtdb.europe-west1.firebasedatabase.app/");


    }
    private void acciones() {
        addReceta.setOnClickListener(this);
        close.setOnClickListener(this);

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        view = View.inflate(context, R.layout.fragment_detalle_recetas, null);
        if(this.getArguments() !=null){
            recetaaRecuperda =(Recetas) this.getArguments().getSerializable("receta");
            uid = getArguments().getString("uid");

        }


    }

    @Override
    public void onRecetaSelected(Recetas receta,String uid) {
        fragmentManager = getParentFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.sitio_fragment, FragmentDetalleRecetas.newInstance(receta,uid));
        fragmentTransaction.addToBackStack("fdetalle");
        fragmentTransaction.commit();
    }
    public static DialogoRecetas newInstance(Recetas receta,String uid){
        Bundle args =new Bundle();
        args.putSerializable("receta",receta);
        args.putString("uid",uid);
        DialogoRecetas dialogoRecetas = new DialogoRecetas();
        dialogoRecetas.setArguments(args);
        return dialogoRecetas;


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.agregar_receta:
                DatabaseReference nodoReferencia = firebaseDatabase.getReference("Usuarios").child(uid).child("recetas").child(recetaaRecuperda.getNombre());
                nodoReferencia.setValue(recetaaRecuperda);
                Toast.makeText(getContext(), "Receta agregada correctamente", Toast.LENGTH_SHORT).show();

                break;
            case R.id.cerrar_ventana_recetas:
                dismiss();
                break;
        }
    }
    }
