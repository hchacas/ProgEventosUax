package com.julian.proyectofinalpsp;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RecuperarPass extends AppCompatActivity implements View.OnClickListener {
    private Button recuperarPwd;
    private EditText emailEditText;
    private TextView emailTextField;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_pass);
        instancias();
        acciones();
    }

    private void instancias() {
        firebaseAuth= FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        onStart();
        recuperarPwd = findViewById(R.id.recuperarPwd);
        emailEditText = findViewById(R.id.emailEditText);
        emailTextField=findViewById(R.id.emailTextField);
    }

    private void acciones() {
        recuperarPwd.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.recuperarPwd:
                sendEmail();
                break;
            case R.id.emailEditText:
                break;
        }

    }

    public void sendEmail() {

         if (TextUtils.isEmpty(emailEditText.getText())){
             Toast.makeText(this, "Introduce un email", Toast.LENGTH_SHORT).show();

         }else{
             String email=emailEditText.getText().toString();
             firebaseAuth.sendPasswordResetEmail(email)
                     .addOnCompleteListener(new OnCompleteListener<Void>() {
                         @Override
                         public void onComplete(@NonNull Task<Void> task) {
                             if (task.isSuccessful()){
                                 Toast.makeText(RecuperarPass.this, "Se le ha enviado un email para restablecer la contraseña", Toast.LENGTH_SHORT).show();

                             }

                         }
                     });


         }

    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            firebaseUser.reload();
        }
    }
}