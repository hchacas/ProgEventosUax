package com.julian.proyectofinalpsp.rickmorty;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.julian.proyectofinalpsp.Adaptadores.AdaptadorSpinner;
import com.julian.proyectofinalpsp.Adaptadores.AdaptadorSpinnerRick;
import com.julian.proyectofinalpsp.R;
import com.julian.proyectofinalpsp.Utils.Recetas;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Random;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

public class ApiRestRick extends AppCompatActivity {
    private Personajes personaje;
    private Spinner spinnerRick;
    private AdaptadorSpinnerRick adaptadorSpinnerRick;
    private ArrayList listaPersonajes;
    private ImageView image;
    private TextView textoNombre,textoEspecie;
    private CardView cardViewRick;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api_rest_rick);
        instancias();
        rellenarLista();
        asociarElementos();
        iniciarSpinner();
        acciones();
    }

    private void acciones() {
        spinnerRick.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Personajes personajeRecuperado= (Personajes) adaptadorSpinnerRick.getItem(position);
                Toast.makeText(getApplicationContext(), personajeRecuperado.getNombre(), Toast.LENGTH_SHORT).show();
                Glide.with(getApplicationContext()).load(personajeRecuperado.getImagen()).into(image);
                textoNombre.setText("Nombre: "+personajeRecuperado.getNombre());
                textoEspecie.setText("Especie: "+personajeRecuperado.getEspecie());




            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void iniciarSpinner() {
        adaptadorSpinnerRick.notifyDataSetChanged();
    }

    private void asociarElementos() {
        spinnerRick.setAdapter(adaptadorSpinnerRick);
    }

    private void instancias() {
        listaPersonajes=new ArrayList();
        cardViewRick=findViewById(R.id.carViewRick);
        spinnerRick=findViewById(R.id.spinnerRick);
        adaptadorSpinnerRick=new AdaptadorSpinnerRick(listaPersonajes,getApplicationContext());
        image=findViewById(R.id.img_spinnerRick);
        textoNombre=findViewById(R.id.txt_nombre_spinnerRick);
        textoEspecie=findViewById(R.id.txt_especie_spinnerRick);

    }
    private void rellenarLista() {
        //REDES
        String url = String.format("https://rickandmortyapi.com/api/character");
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                procesarPeticion(response);
                Log.v("prueba","realizar peticion");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.v("prueba", "Conexion fallida");
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsonObjectRequest);
    }
    private void procesarPeticion(JSONObject response) {
        try {
            Log.v("prueba","procesar peticion");
            JSONArray listaPersonajes = response.getJSONArray("results");
            for (int i = 0; i <listaPersonajes.length(); i++) {
                JSONObject personajeObjeto = listaPersonajes.getJSONObject(i);
                String nombre = personajeObjeto.getString("name");
                String especie = personajeObjeto.getString("species");
                String img = personajeObjeto.getString("image");
                personaje = new Personajes(nombre,especie,img);
                adaptadorSpinnerRick.rellenarLista(personaje);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }




}
