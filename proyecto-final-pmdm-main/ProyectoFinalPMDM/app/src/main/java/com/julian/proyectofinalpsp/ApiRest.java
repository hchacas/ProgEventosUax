package com.julian.proyectofinalpsp;

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

public class ApiRest extends AppCompatActivity {
    private Recetas receta;
    private Spinner spinner;
    private AdaptadorSpinner adaptadorSpinner;
    private ArrayList listaRecetas;
    private ImageView image;
    private TextView textoNombre,textoCategoria,textoNacionalidad,textoTag;
    private CardView cardView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api_rest);
        instancias();
        rellenarLista();
        asociarElementos();
        iniciarSpinner();
        acciones();
    }

    private void acciones() {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinner.setBackgroundColor(getRandomColor());
                cardView.setBackgroundColor(getRandomColor());
                Recetas recetaRecuperada= (Recetas)adaptadorSpinner.getItem(position);
                Toast.makeText(getApplicationContext(), recetaRecuperada.getNombre(), Toast.LENGTH_SHORT).show();
                Glide.with(getApplicationContext()).load(recetaRecuperada.getImagen()).into(image);
                textoNombre.setText("Nombre: "+recetaRecuperada.getNombre());
                textoCategoria.setText("Categoria: "+recetaRecuperada.getCategoria());
                textoNacionalidad.setText("Nacionalidad: "+recetaRecuperada.getNacionalidad());
                textoTag.setText("Tag: "+recetaRecuperada.getDescripcion());




            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void iniciarSpinner() {
        adaptadorSpinner.notifyDataSetChanged();
    }

    private void asociarElementos() {
        spinner.setAdapter(adaptadorSpinner);
    }

    private void instancias() {
        listaRecetas=new ArrayList();
        cardView=findViewById(R.id.carView);
        cardView.setBackgroundColor(getColor(R.color.cardView));
        spinner=findViewById(R.id.spinner);
        spinner.setBackgroundColor(getColor(R.color.cardView));
        adaptadorSpinner=new AdaptadorSpinner(listaRecetas,getApplicationContext());
        image=findViewById(R.id.img_spinner);
        textoNombre=findViewById(R.id.txt_nombre_spinner);
        textoCategoria=findViewById(R.id.txt_categoria_spinner);
        textoNacionalidad=findViewById(R.id.txt_nacionalidad_spinner);
        textoTag=findViewById(R.id.txt_tag_spinner);

    }
    private void rellenarLista() {
        //REDES
        String url = String.format("https://themealdb.com/api/json/v1/1/search.php?s");
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
    private SSLSocketFactory newSslSocketFactory() {
        // Obtenemos el TrustManager que tengamos almacenado en memoria
        // TrustManagerFactory tmf;
        // Generar el SSLSocketFactory mediante el TrustManager
        SSLContext context = null;
        TrustManagerFactory tmf=null;
        try {
            context = SSLContext.getInstance("TLS");

            context.init(null, tmf.getTrustManagers(), null);
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }

        return context.getSocketFactory();
    }
    private void procesarPeticion(JSONObject response) {
        try {
            Log.v("prueba","procesar peticion");
            JSONArray arrayRecetas = response.getJSONArray("meals");
            for (int i = 0; i <arrayRecetas.length(); i++) {
                JSONObject recetaOnjeto = arrayRecetas.getJSONObject(i);
                String nombre = recetaOnjeto.getString("strMeal");
                String categoria = recetaOnjeto.getString("strCategory");
                String nacionalidad = recetaOnjeto.getString("strArea");
                String tag = recetaOnjeto.getString("strTags");
                String img = recetaOnjeto.getString("strMealThumb");
                receta = new Recetas(nombre,categoria,nacionalidad,tag,img);
                adaptadorSpinner.rellenarLista(receta);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public int getRandomColor(){
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }



}
