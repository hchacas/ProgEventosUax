package com.julian.proyectofinalpsp;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.julian.proyectofinalpsp.Utils.Usuario;

import java.util.regex.Pattern;

public class Registro extends AppCompatActivity implements View.OnClickListener {
    private static final String PREFS_UID = "";
    private final String TAG = "GoogleSignInRegistro";
    private final int RC_SIGN_IN = 1;
    private EditText email, contraseña,usuario,telefono,pass1,pass2;
    private Button registrarse2, google;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private GoogleSignInClient googleSignInClient;
    private GoogleSignInOptions googleSignInOptions;
    private GoogleSignInAccount googleSignInAccount;
    private AuthCredential credential;
    private AwesomeValidation awesomeValidation;
    private FirebaseDatabase database;
    private String uid;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        instancias();
        acciones();

    }


    private void instancias() {
        usuario=findViewById(R.id.registroUsuario);
        telefono=findViewById(R.id.registroTelefono);
        pass1=findViewById(R.id.contraseña);
        pass2=findViewById(R.id.contraseña2);
        email = findViewById(R.id.email);
        contraseña = findViewById(R.id.contraseña);
        registrarse2 = findViewById(R.id.registrarse2);
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance("https://proyectofinalpsp-7c012-default-rtdb.europe-west1.firebasedatabase.app/");

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this,R.id.registroUsuario, RegexTemplate.NOT_EMPTY, R.string.invalid_user);
        awesomeValidation.addValidation(this, R.id.registroTelefono, "[9]", R.string.invalid_telephone);
        awesomeValidation.addValidation(this, R.id.email, Patterns.EMAIL_ADDRESS, R.string.invalid_mail);
        awesomeValidation.addValidation(this, R.id.contraseña, ".{6,}", R.string.invalid_password);
        awesomeValidation.addValidation(this, R.id.contraseña2, ".{6,}", R.string.invalid_password2);


        google = findViewById(R.id.google);
        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);
        googleSignInAccount = GoogleSignIn.getLastSignedInAccount(this);

        firebaseAuth = FirebaseAuth.getInstance();
        onStart();


    }


    private void acciones() {
        registrarse2.setOnClickListener(this);
        google.setOnClickListener(this);

    }


    private void mantenerSesion() {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            updateUI(firebaseUser);
            firebaseUser.getUid();

        }


    }


    private void dameToastdeerror(String error) {

        switch (error) {

            case "ERROR_INVALID_CUSTOM_TOKEN":
                Toast.makeText(Registro.this, "El formato del token personalizado es incorrecto. Por favor revise la documentación", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_CUSTOM_TOKEN_MISMATCH":
                Toast.makeText(Registro.this, "El token personalizado corresponde a una audiencia diferente.", Toast.LENGTH_LONG).show();
                break;
            case "ERROR_INVALID_USER":
                Toast.makeText(Registro.this, "El nombre de suaurio no es valido.", Toast.LENGTH_LONG).show();
                break;
            case "ERROR_INVALID_TELEPHONE":
                Toast.makeText(Registro.this, "El numero de telefono no es valido.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_INVALID_CREDENTIAL":
                Toast.makeText(Registro.this, "La credencial de autenticación proporcionada tiene un formato incorrecto o ha caducado.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_INVALID_EMAIL":
                Toast.makeText(Registro.this, "La dirección de correo electrónico está mal formateada.", Toast.LENGTH_LONG).show();
                email.setError("La dirección de correo electrónico está mal formateada.");
                email.requestFocus();
                break;

            case "ERROR_WRONG_PASSWORD":
                Toast.makeText(Registro.this, "La contraseña no es válida o el usuario no tiene contraseña.", Toast.LENGTH_LONG).show();
                contraseña.setError("la contraseña es incorrecta ");
                contraseña.requestFocus();
                contraseña.setText("");
                break;

            case "ERROR_USER_MISMATCH":
                Toast.makeText(Registro.this, "Las credenciales proporcionadas no corresponden al usuario que inició sesión anteriormente..", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_REQUIRES_RECENT_LOGIN":
                Toast.makeText(Registro.this, "Esta operación es sensible y requiere autenticación reciente. Inicie sesión nuevamente antes de volver a intentar esta solicitud.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL":
                Toast.makeText(Registro.this, "Ya existe una cuenta con la misma dirección de correo electrónico pero diferentes credenciales de inicio de sesión. Inicie sesión con un proveedor asociado a esta dirección de correo electrónico.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_EMAIL_ALREADY_IN_USE":
                Toast.makeText(Registro.this, "La dirección de correo electrónico ya está siendo utilizada por otra cuenta..   ", Toast.LENGTH_LONG).show();
                email.setError("La dirección de correo electrónico ya está siendo utilizada por otra cuenta.");
                email.requestFocus();
                break;

            case "ERROR_CREDENTIAL_ALREADY_IN_USE":
                Toast.makeText(Registro.this, "Esta credencial ya está asociada con una cuenta de usuario diferente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_USER_DISABLED":
                Toast.makeText(Registro.this, "La cuenta de usuario ha sido inhabilitada por un administrador..", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_USER_TOKEN_EXPIRED":
                Toast.makeText(Registro.this, "La credencial del usuario ya no es válida. El usuario debe iniciar sesión nuevamente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_USER_NOT_FOUND":
                Toast.makeText(Registro.this, "No hay ningún registro de usuario que corresponda a este identificador. Es posible que se haya eliminado al usuario.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_INVALID_USER_TOKEN":
                Toast.makeText(Registro.this, "La credencial del usuario ya no es válida. El usuario debe iniciar sesión nuevamente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_OPERATION_NOT_ALLOWED":
                Toast.makeText(Registro.this, "Esta operación no está permitida. Debes habilitar este servicio en la consola.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_WEAK_PASSWORD":
                Toast.makeText(Registro.this, "La contraseña proporcionada no es válida..", Toast.LENGTH_LONG).show();
                contraseña.setError("La contraseña no es válida, debe tener al menos 6 caracteres");
                contraseña.requestFocus();
                break;

        }

    }




    private void signIn() {
        Intent signIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signIntent, RC_SIGN_IN);
    }

    private void firebaseAuthWithGoogle(String idToken) {
        credential = GoogleAuthProvider.getCredential(idToken, null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithCredential:success");
                            Intent intent = new Intent(Registro.this, MainActivity.class);
                            startActivity(intent);
                            Registro.this.finish();


                        } else {
                            Log.d(TAG, "signWithCredential:failure", task.getException());
                            FirebaseUser firebaseUser1 = firebaseAuth.getCurrentUser();
                            updateUI(null);
                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            if (task.isSuccessful()) {
                try {
                    googleSignInAccount = task.getResult(ApiException.class);
                    Log.d(TAG, "firebaseAuthWithGoogle:" + googleSignInAccount.getId());
                    firebaseAuthWithGoogle(googleSignInAccount.getIdToken());
                } catch (ApiException e) {
                    Log.d(TAG, "Registro con google fallido", e);
                }
            } else {
                Log.d(TAG, "Error,login sin exito:" + task.getException().toString());
                Toast.makeText(this, "Ha ocurrido un error" + task.getException().toString(),
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            updateUI(firebaseUser);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {


            case R.id.registrarse2:


            String mail = email.getText().toString();
            String user = usuario.getText().toString();
            String telephone = telefono.getText().toString();


            if (isValidEmail(mail)
                    && contraseñaCorrecta()
                    && telefonoAceptado()
                    && nombreCorrecto(user)
                    && telefonoCorrecto(telephone)) {
                String pass = pass1.getText().toString();
                firebaseAuth.createUserWithEmailAndPassword(mail, pass)
                        .addOnCompleteListener(Registro.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Usuario usuario = new Usuario();
                                    usuario.setCorreo(mail);
                                    usuario.setNombre(user);
                                    usuario.setTelefono(telephone);
                                    FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                                    DatabaseReference reference = database.getReference("Usuarios/" + currentUser.getUid());
                                    reference.setValue(usuario);
                                    uid=firebaseAuth.getCurrentUser().getUid();
                                    Toast.makeText(Registro.this, "Se ha creado el usuario correctamente", Toast.LENGTH_SHORT).show();
                                    Intent datos = new Intent(Registro.this, MainActivity.class);
                                    datos.putExtra("nombreUser",user);
                                    datos.putExtra("uid",uid);
                                    startActivity(datos);
                                    finish();
                                } else {
                                    String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();
                                    dameToastdeerror(errorCode);

                                }}


                        });


            }

                break;
            case R.id.google:
                signIn();
                break;

        }

    }


    private void updateUI(FirebaseUser firebaseUser) {


    }
    private boolean isValidEmail(CharSequence charSequence) {
        return !TextUtils.isEmpty(charSequence) && Patterns.EMAIL_ADDRESS.matcher(charSequence).matches();
    }

    public boolean nombreCorrecto(String nombre) {
        return !nombre.isEmpty();
    }

    public boolean telefonoCorrecto(String telefono) {
        return !telefono.isEmpty();
    }

    public boolean contraseñaCorrecta() {
        String contraseña, contraseñarRepetida;
        contraseña = pass1.getText().toString();
        contraseñarRepetida = pass2.getText().toString();
        if (contraseña.equals(contraseñarRepetida)) {
            if (contraseña.length() > 5) {
                return true;
            } else {
                return false;
            }

        } else {
            return false;
        }

    }

    public boolean telefonoAceptado() {
        String telefonoo;
        telefonoo = telefono.getText().toString();
        if (telefonoo.length() == 9) {
            return true;
        } else {
            return false;
        }

    }


}