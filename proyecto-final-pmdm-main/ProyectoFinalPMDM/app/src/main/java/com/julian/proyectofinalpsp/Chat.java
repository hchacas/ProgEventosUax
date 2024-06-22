package com.julian.proyectofinalpsp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.julian.proyectofinalpsp.Adaptadores.AdaptadorVer;
import com.julian.proyectofinalpsp.Dialogos.DialogoCond;
import com.julian.proyectofinalpsp.Utils.MensajeAjeno;
import com.julian.proyectofinalpsp.Utils.MensajesMovil;
import com.julian.proyectofinalpsp.Utils.Usuario;

import de.hdodenhof.circleimageview.CircleImageView;

public class Chat extends AppCompatActivity {
    private ImageView fotoproducto,fotoPerfil;
    private CircleImageView fotoPerfilMenu;
    private static final int PICK_IMAGE = 100;
    private Uri imageUri;
    private TextView User,nombreUsuario;
    private TextView telephone;
    private RecyclerView recogerMensaje;
    private EditText mensajeReal;
    private Button btnEnviar;
    private ImageButton btnEnviarFoto;
    private Toolbar toolbar;
    private NavigationView nav;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Button btn_link;
    private String url = "https://doctorstrangetridente.herokuapp.com/formUser";
    private AdaptadorVer adapter;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private String fotodePerfil;
    private String fotodePerfilMenu;
    private FirebaseAuth firebaseAuth;
    private String usuarioenchat;
    private String telefonoenchat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);
        instancias();
        configurarToolbar();
        acciones();



    }

    private void acciones() {

        fotoPerfilMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.setType("image/jpeg");
                i.putExtra(Intent.EXTRA_LOCAL_ONLY,true);
                startActivityForResult(Intent.createChooser(i,"tag"),2);

            }
        });
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.push().setValue(new MensajeAjeno(mensajeReal.getText().toString(), usuarioenchat,telefonoenchat, fotodePerfil,"1", ServerValue.TIMESTAMP));
                mensajeReal.setText("");

            }
        });

        btnEnviarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY,true);
                startActivityForResult(Intent.createChooser(intent,"tag"),1);
            }
        });

        fotoproducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.setType("image/jpeg");
                i.putExtra(Intent.EXTRA_LOCAL_ONLY,true);
                startActivityForResult(Intent.createChooser(i,"tag"),2);
            }
        });

        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                recogerMensaje.scrollToPosition(adapter.getItemCount()-1);

            }
        });
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                MensajesMovil mensajesMovil = dataSnapshot.getValue(MensajesMovil.class);
                adapter.addMensaje(mensajesMovil);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.opcion_cond_term_2:
                        DialogoCond dialogo1 = new DialogoCond();
                        dialogo1.show(getSupportFragmentManager(),"tag");
                        break;
                    case R.id.opcion_cerrarSesion_3:
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(Chat.this, Login.class));
                        finish();
                        break;
                    case R.id.opcion_salir_4:
                        finish();
                        break;
                }
                return true;
            }
        });


    }

    private void configurarToolbar() {
       // setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(Chat.this, drawerLayout, toolbar, R.string.abierto, R.string.cerrado);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();


    }

    private void instancias() {
        fotoproducto = findViewById(R.id.fotoVender);
        User = findViewById(R.id.nombreUsuario);
        telephone = findViewById(R.id.nombreTelefono);
        recogerMensaje = findViewById(R.id.listaMensajes);
        mensajeReal = findViewById(R.id.enviarTextos);
        btnEnviar = findViewById(R.id.btn_enviarTexto);
        btnEnviarFoto = findViewById(R.id.btn_Enviar_Producto);
        btn_link = findViewById(R.id.boton_link);
        fotodePerfil = "";
        database = FirebaseDatabase.getInstance("https://proyectofinalpsp-7c012-default-rtdb.europe-west1.firebasedatabase.app/");
        databaseReference = database.getReference("chat");
        storage = FirebaseStorage.getInstance("gs://proyectofinalpsp-7c012.appspot.com");
        firebaseAuth = FirebaseAuth.getInstance();
        adapter = new AdaptadorVer(this);
        LinearLayoutManager l = new LinearLayoutManager(this);
        recogerMensaje.setLayoutManager(l);
        recogerMensaje.setAdapter(adapter);
        toolbar = findViewById(R.id.toolbar);
        nav = findViewById(R.id.menu_navigation);
        drawerLayout = findViewById(R.id.drawer);
        nombreUsuario = nav.getHeaderView(0).findViewById(R.id.nombre_usuario_header);
        fotoPerfilMenu = nav.getHeaderView(0).findViewById(R.id.img_header_menu);
        fotoPerfil=findViewById(R.id.fotoPerfilMensaje);



    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Uri u = data.getData();
            storageReference = storage.getReference("imagen_producto");
            final StorageReference fotoRecogida = storageReference.child(u.getLastPathSegment());
            fotoRecogida.putFile(u).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()){
                        throw  task.getException();
                    }
                    return fotoRecogida.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()){
                        Uri uri = task.getResult();
                        MensajeAjeno m = new MensajeAjeno(usuarioenchat +" a enviado un producto",uri.toString(), User.getText().toString(),telephone.getText().toString(), fotodePerfil,"2", ServerValue.TIMESTAMP);
                        databaseReference.push().setValue(m);

                    }
                }
            });
        }else if (requestCode == 2 && resultCode == RESULT_OK){
            Uri u = data.getData();
            storageReference = storage.getReference("imagen_perfil");
            final StorageReference fotoReferencia = storageReference.child(u.getLastPathSegment());
            fotoReferencia.putFile(u).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()){
                        throw  task.getException();
                    }
                    return fotoReferencia.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()){
                        Uri uri = task.getResult();
                        fotodePerfil = uri.toString();
                        Glide.with(Chat.this).load(uri.toString()).into(fotoproducto);
                        Uri uri2 = task.getResult();
                        fotodePerfilMenu = uri2.toString();
                        Glide.with(Chat.this).load(uri2.toString()).into(fotoPerfilMenu);

                    }
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser!=null){
            btnEnviar.setEnabled(false);
            DatabaseReference reference = database.getReference("Usuarios/"+currentUser.getUid());
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Usuario usuario = snapshot.getValue(Usuario.class);
                    usuarioenchat = usuario.getNombre();
                    nombreUsuario.setText(usuarioenchat);
                    telefonoenchat = usuario.getTelefono();
                    User.setText(usuarioenchat);
                    telephone.setText(telefonoenchat);
                    btnEnviar.setEnabled(true);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }else {
            startActivity(new Intent(Chat.this, Login.class));
            finish();
        }
    }


   }