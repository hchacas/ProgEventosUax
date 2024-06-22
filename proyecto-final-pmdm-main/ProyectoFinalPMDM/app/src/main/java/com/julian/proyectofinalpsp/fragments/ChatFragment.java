package com.julian.proyectofinalpsp.fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
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
import com.julian.proyectofinalpsp.Chat;
import com.julian.proyectofinalpsp.Dialogos.DialogoCond;
import com.julian.proyectofinalpsp.Login;
import com.julian.proyectofinalpsp.R;
import com.julian.proyectofinalpsp.Utils.MensajeAjeno;
import com.julian.proyectofinalpsp.Utils.MensajesMovil;
import com.julian.proyectofinalpsp.Utils.Usuario;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;


public class ChatFragment extends Fragment {
    private View view;
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


    public ChatFragment() {
        // Required empty public constructor
    }
    public static ChatFragment newInstance(TextView nombreUser) {
        ChatFragment fragment = new ChatFragment();
        Bundle args = new Bundle();
        args.putString("nombreUsuario", String.valueOf(nombreUser));
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_chat, container, false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        instancias();
        acciones();
    }
    private void acciones() {


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
    }



    private void instancias() {
        fotoproducto = view.findViewById(R.id.fotoVender);
        User = view.findViewById(R.id.nombreUsuario);
        telephone = view.findViewById(R.id.nombreTelefono);
        recogerMensaje = view.findViewById(R.id.listaMensajes);
        mensajeReal = view.findViewById(R.id.enviarTextos);
        btnEnviar = view.findViewById(R.id.btn_enviarTexto);
        btnEnviarFoto = view.findViewById(R.id.btn_Enviar_Producto);
        btn_link = view.findViewById(R.id.boton_link);
        fotodePerfil = "";
        database = FirebaseDatabase.getInstance("https://proyectofinalpsp-7c012-default-rtdb.europe-west1.firebasedatabase.app/");
        databaseReference = database.getReference("chat");
        storage = FirebaseStorage.getInstance("gs://proyectofinalpsp-7c012.appspot.com");
        firebaseAuth = FirebaseAuth.getInstance();
        adapter = new AdaptadorVer(getContext());
        LinearLayoutManager l = new LinearLayoutManager(getContext());
        recogerMensaje.setLayoutManager(l);
        recogerMensaje.setAdapter(adapter);
        toolbar = view.findViewById(R.id.toolbar);
        drawerLayout = view.findViewById(R.id.drawer);
        nav = view.findViewById(R.id.menu_navigation);

        fotoPerfil=view.findViewById(R.id.fotoPerfilMensaje);



    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
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
                        Glide.with(getContext()).load(uri.toString()).into(fotoproducto);


                    }
                }
            });
        }
    }

    @Override
    public void onResume() {
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
            startActivity(new Intent(getContext(), Login.class));
            getActivity().finish();
        }
    }
}
