package com.julian.proyectofinalpsp.fragments;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.julian.proyectofinalpsp.Adaptadores.AdaptadorFavoritos;
import com.julian.proyectofinalpsp.Adaptadores.AdaptadorFavoritosEjercicios;
import com.julian.proyectofinalpsp.R;
import com.julian.proyectofinalpsp.Utils.Ejercicios;
import com.julian.proyectofinalpsp.Utils.Recetas;

import java.util.Iterator;


public class FavoritosFragment extends Fragment {

    private View view;
    private RecyclerView recyclerRecetas,recyclerEjercicios;
    private AdaptadorFavoritos adaptadorFavoritos;
    private AdaptadorFavoritosEjercicios adaptadorFavoritosejercicios;
    private FirebaseDatabase firebaseDatabase;
    private String uid="";


    public FavoritosFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater,@Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view=inflater.inflate(R.layout.fragment_favoritos, container, false);

        return view; }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (getArguments()!=null){
            this.uid = getArguments().getString("uid");
        }
        adaptadorFavoritos = new AdaptadorFavoritos(context);
        adaptadorFavoritosejercicios = new AdaptadorFavoritosEjercicios(context);

    }
    public static FavoritosFragment newInstance(String uid) {

        Bundle args = new Bundle();
        args.putString("uid",uid);
        FavoritosFragment fragmentFav = new FavoritosFragment();
        fragmentFav.setArguments(args);
        return fragmentFav;
    }


    @Override
    public void onStart() {
        super.onStart();
        instancias();
        recuperarFavoritos();
        recuperarFavoritosEjercicios();
        rellenarRecycler();

    }
    private void rellenarRecycler() {
        recyclerRecetas.setAdapter(adaptadorFavoritos);
        recyclerRecetas.setLayoutManager(new GridLayoutManager(getContext(),2));
        recyclerEjercicios.setAdapter(adaptadorFavoritosejercicios);
        recyclerEjercicios.setLayoutManager(new GridLayoutManager(getContext(),2));
    }

    private void instancias() {
        recyclerRecetas = view.findViewById(R.id.listaRecetasFavoritas);
        recyclerEjercicios = view.findViewById(R.id.listaEjerciciosFavoritos);
        firebaseDatabase = FirebaseDatabase.getInstance("https://proyectofinalpsp-7c012-default-rtdb.europe-west1.firebasedatabase.app/");


    }
    private void recuperarFavoritos() {
        firebaseDatabase = FirebaseDatabase.getInstance("https://proyectofinalpsp-7c012-default-rtdb.europe-west1.firebasedatabase.app/");
        Query nodoReferencia =  firebaseDatabase.getReference("Usuarios")
                .child(uid).child("recetas").limitToLast(1);
        nodoReferencia.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                DataSnapshot dataSnapshot = task.getResult();
                Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();
                while (iterator.hasNext()){
                    Recetas receta1 = iterator.next().getValue(Recetas.class);
                    adaptadorFavoritos.rellenarLista(receta1);

                }
            }
        });

    }
    private void recuperarFavoritosEjercicios() {
        firebaseDatabase = FirebaseDatabase.getInstance("https://proyectofinalpsp-7c012-default-rtdb.europe-west1.firebasedatabase.app/");
        Query nodoReferencia =  firebaseDatabase.getReference("Usuarios")
                .child(uid).child("ejercicios").limitToLast(1);
        nodoReferencia.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                DataSnapshot dataSnapshot = task.getResult();
                Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();
                while (iterator.hasNext()){
                    Ejercicios ejercicio1 = iterator.next().getValue(Ejercicios.class);
                    adaptadorFavoritosejercicios.rellenarLista(ejercicio1);

                }
            }
        });

    }



}
