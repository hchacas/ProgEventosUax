package com.julian.proyectofinalpsp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PantallaDeCargaRecetas extends AppCompatActivity {
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_de_carga_recetas);
        instancias();

        //Hilo que arranca la pantalla donde esta la lista de recetas
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(getApplicationContext(), ApiRest.class);
                startActivity(i);
                finish();
            }
        },5000);
    }
    private void instancias() {
        progressBar=findViewById(R.id.progresoBarra);
        progressBar.setVisibility(View.VISIBLE);
        new Hilo().start();
    }
//Hilo que muestra un mensaje cuando se accede a la lista de las recetas
    class Hilo extends Thread {
        @Override
        public void run() {
            try {
                Thread.sleep(5000);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Carga de la lista de recetas completada", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

}
