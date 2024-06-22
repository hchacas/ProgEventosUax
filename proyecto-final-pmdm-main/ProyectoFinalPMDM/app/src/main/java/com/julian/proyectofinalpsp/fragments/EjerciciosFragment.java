package com.julian.proyectofinalpsp.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.julian.proyectofinalpsp.Adaptadores.AdaptadorEjercicios;
import com.julian.proyectofinalpsp.Adaptadores.AdaptadorRecetas;
import com.julian.proyectofinalpsp.R;
import com.julian.proyectofinalpsp.Utils.Ejercicios;
import com.julian.proyectofinalpsp.Utils.Recetas;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class EjerciciosFragment extends Fragment {
    private View view;
    private Ejercicios ejercicio;
    private RecyclerView recyclerView;
    private AdaptadorEjercicios adaptadorEjercicios;
    private AdaptadorEjercicios.OnEjercicioListener listener;


    public EjerciciosFragment() {
    }

    @Override
    public void onStart() {
        super.onStart();
        instancias();
        asociarDatos();
        realizarPeticion();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_ejercicios, container, false);

        return view;
    }
    private void realizarPeticion() {

        String url = "https://run.mocky.io/v3/76340c6e-2e65-448e-85c5-5130c0bb5ff5";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(1, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                procesarPeticion(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("error","Peticion fallida");

            }
        });

        Volley.newRequestQueue(getContext()).add(jsonObjectRequest);


        //adaptadorEquipos.notifyDataSetChanged();
    }

    private void procesarPeticion(JSONObject response) {
        try {
            JSONArray equipos = response.getJSONArray("results");
            for (int i = 0; i < equipos.length(); i++) {
                JSONObject equipo = equipos.getJSONObject(i);
                String nombre = equipo.getString("nombre");
                String imagen = equipo.getString("imagen");
                String descripcion = equipo.getString("descripcion");
                String musculoTrabajado = equipo.getString("musculo");


                Ejercicios ejercicioActual = new Ejercicios(nombre, imagen,descripcion,musculoTrabajado);
                adaptadorEjercicios.agregarEjercicio(ejercicioActual);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void asociarDatos() {
        recyclerView.setAdapter(adaptadorEjercicios);

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,RecyclerView.VERTICAL));
    }

    private void instancias() {

        recyclerView = view.findViewById(R.id.lista_ejercicios);
        adaptadorEjercicios = new AdaptadorEjercicios(getContext());
    }






}
