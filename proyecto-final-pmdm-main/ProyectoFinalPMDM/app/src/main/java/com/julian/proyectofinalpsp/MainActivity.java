package com.julian.proyectofinalpsp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.julian.proyectofinalpsp.Adaptadores.AdaptadorEjercicios;
import com.julian.proyectofinalpsp.Adaptadores.AdaptadorFragments;
import com.julian.proyectofinalpsp.Adaptadores.AdaptadorRecetas;
import com.julian.proyectofinalpsp.Dialogos.DialogoCond;
import com.julian.proyectofinalpsp.Dialogos.DialogoEjercicios;
import com.julian.proyectofinalpsp.Dialogos.DialogoRecetas;
import com.julian.proyectofinalpsp.Utils.Ejercicios;
import com.julian.proyectofinalpsp.Utils.Recetas;
import com.julian.proyectofinalpsp.fragments.ChatFragment;
import com.julian.proyectofinalpsp.fragments.EjerciciosFragment;
import com.julian.proyectofinalpsp.fragments.FavoritosFragment;
import com.julian.proyectofinalpsp.fragments.RecetasFragment;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements AdaptadorRecetas.OnRecetaListener, AdaptadorEjercicios.OnEjercicioListener {
    private static final int SELECT_FILE = 1;
    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private AdaptadorFragments adaptadorFragments;
    private ArrayList<Fragment> listaFG;
    private NavigationView nav;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private  String uid="";
    private Recetas receta;
    private String nombreUsuarioRecuperado;
    private TextView nombreUsuario;
    private CircleImageView imagenUsuario;
    private Bitmap bmp;
    private Uri selectedImageUri ;
    private Uri selectedImage;
    private String filePath;
    private View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instancias();
        iniciarPager();
        configurarToolbar();
        acciones();

    }



    private void personalizarPestanias() {
        for (int i=0;i<=0;i++){
            Fragment fragment = adaptadorFragments.getItem(i);
            System.out.println(fragment.getClass().getSimpleName());
            System.out.println(fragment.getView().getScrollX());

            Drawable drawable = fragment.getView().findViewById(R.id.fondo).getBackground();
            TabLayout.Tab seleccionada = tabLayout.getTabAt(i);
            seleccionada.view.setBackground(drawable);
            tabLayout.selectTab(tabLayout.getTabAt(1));

        }
    }

    private void acciones() {


        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.v("scroll", String.valueOf(position));
                Fragment fragment = adaptadorFragments.getItem(position);
                Drawable drawable = fragment.getView().findViewById(R.id.fondo).getBackground();
                tabLayout.setBackground(drawable);
                //TabLayout.Tab seleccionada = tabLayout.getTabAt(position);
                //seleccionada.view.setBackground(drawable);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.getCurrentItem();
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
                        startActivity(new Intent(MainActivity.this, Login.class));
                        finish();
                        break;
                    case R.id.opcion_salir_4:
                        finish();
                        break;
                }
                return true;
            }
        });
        imagenUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirGaleria(view);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        selectedImageUri = null;
        filePath = null;
        switch (requestCode) {
            case SELECT_FILE:
                if (resultCode == Activity.RESULT_OK) {
                    selectedImage = data.getData();
                    String selectedPath=selectedImage.getPath();
                    if (requestCode == SELECT_FILE) {
                        if (selectedPath != null) {
                            InputStream imageStream = null;
                            try {
                                imageStream = getContentResolver().openInputStream(
                                        selectedImage);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            bmp = BitmapFactory.decodeStream(imageStream);
                            imagenUsuario =  findViewById(R.id.img_header_menu);
                            imagenUsuario.setImageBitmap(bmp);
                        }
                    }
                }
                break;
        }
    }

    private void iniciarPager() {
        listaFG = new ArrayList();
        listaFG.add(new RecetasFragment().newInstance(receta,uid));
        listaFG.add(new EjerciciosFragment());
        listaFG.add(new ChatFragment());
        listaFG.add(new FavoritosFragment().newInstance(uid));
        adaptadorFragments = new AdaptadorFragments(getSupportFragmentManager(),0,listaFG);
        viewPager.setAdapter(adaptadorFragments);

    }

    private void instancias() {
        viewPager = findViewById(R.id.view_pager);
        toolbar = findViewById(R.id.toolbar);
        tabLayout = findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);
        nav = findViewById(R.id.menu_navigation);
        Intent i=getIntent();
        nombreUsuarioRecuperado=i.getStringExtra("nombreUser");
        nombreUsuario=nav.getHeaderView(0).findViewById(R.id.nombre_usuario_header);
        imagenUsuario=nav.getHeaderView(0).findViewById(R.id.img_header_menu);
        nombreUsuario.setText(nombreUsuarioRecuperado);
        drawerLayout = findViewById(R.id.drawer);
        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());



    }
    private void configurarToolbar() {
        actionBarDrawerToggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, toolbar, R.string.abierto, R.string.cerrado);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();


    }

    @Override
    public void onRecetaSelected(Recetas receta ,String uid) {
        DialogoRecetas dialogoRecetas=new DialogoRecetas().newInstance(receta,uid);
        dialogoRecetas.show(getSupportFragmentManager(),"receta");
    }

    @Override
    public void onEjercicioSelected(Ejercicios ejercicio,String uid) {
        DialogoEjercicios dialogoEjercicios=new DialogoEjercicios().newInstance(ejercicio,uid);
        dialogoEjercicios.show(getSupportFragmentManager(),"ejercicio");

    }
    private void abrirGaleria(View v){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(intent, "Seleccione una imagen"),
                SELECT_FILE);




    }

}
