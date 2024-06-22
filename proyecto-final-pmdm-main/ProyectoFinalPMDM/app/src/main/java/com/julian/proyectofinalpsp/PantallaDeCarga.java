package com.julian.proyectofinalpsp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PantallaDeCarga extends AppCompatActivity {
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_de_carga);
        instancias();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
                finish();
            }
        },3000);
    }

    private void instancias() {
        progressBar=findViewById(R.id.progresoBarra);
        progressBar.setVisibility(View.VISIBLE);
        new Hilo1().start();
    }


class Hilo1 extends Thread {
    @Override
    public void run() {
        try {
            Thread.sleep(5000);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "Inicio de sesion completada con exito", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
}
