package com.xr45labs.uworkers;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mikepenz.iconics.context.IconicsContextWrapper;
import com.xr45labs.uworkers.Modelo.empresa;
import com.xr45labs.uworkers.Modelo.foto_perfil_descarga;
import com.xr45labs.uworkers.Util.Connections;
import com.xr45labs.uworkers.Util.DataInterface;
import com.xr45labs.uworkers.Util.RetrofitConnection;
import com.xr45labs.uworkers.fragments.*;
import com.xr45labs.uworkers.fragments.empresas.fr_add_vacante_empresa;
import com.xr45labs.uworkers.fragments.empresas.fr_perfil_empresa;
import com.xr45labs.uworkers.fragments.empresas.fr_perfil_empresa_config;
import com.xr45labs.uworkers.fragments.empresas.fr_vista_vacante_empresa;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class principal_empresa extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        fr_perfil_empresa.OnFragmentInteractionListener,
        fr_bvacantes.OnFragmentInteractionListener,
        fr_balumnos.OnFragmentInteractionListener,
        fr_perfil_empresa_config.OnFragmentInteractionListener,
        fr_add_vacante_empresa.OnFragmentInteractionListener,
        fr_vista_vacante_empresa.OnFragmentInteractionListener,
        fr_modificar_vacante.OnFragmentInteractionListener,
        fr_perfil_alumno_externo.OnFragmentInteractionListener{
    String nombre,correo;
    int idusuario;
    Context context = this;
    TextView tv_nombre_nav,tv_correo_nav;
    Fragment fragment = null;
    de.hdodenhof.circleimageview.CircleImageView profile_image;
    boolean FragmentTransaction = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_empresa);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        View vistaheader = navigationView.getHeaderView(0);
        profile_image = (de.hdodenhof.circleimageview.CircleImageView) vistaheader.findViewById(R.id.profile_image);
        tv_nombre_nav = (TextView) vistaheader.findViewById(R.id.tv_nombre_nav);
        tv_correo_nav = (TextView) vistaheader.findViewById(R.id.tv_correo_nav);

        datos_perfil();
        descarga_foto_perfil();

        Fragment fragment = new fr_perfil_empresa();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_principal_empresa,fragment)
                .commit();

    }

    @Override
    public void onBackPressed() {
        /*DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }*/
        this.moveTaskToBack(true);
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_balumnos) {
            fragment = new fr_balumnos();
            FragmentTransaction = true;

        } else if (id == R.id.nav_bvacantes) {
            fragment = new fr_bvacantes();
            FragmentTransaction = true;

        } else if(id==R.id.nav_addvacantes){
            fragment = new fr_add_vacante_empresa();
            FragmentTransaction = true;

        }else if (id == R.id.nav_perfil) {
            fragment = new fr_perfil_empresa();
            FragmentTransaction = true;

        } else if (id == R.id.nav_logout) {
            /*SharedPreferences sharedPreferences = getSharedPreferences("data_session",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear().commit();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);*/
            dialog();
        }

        if(FragmentTransaction){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_principal_empresa,fragment)
                    .commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(IconicsContextWrapper.wrap(newBase));
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    public void datos_perfil(){
        SharedPreferences sharedPreferences = getSharedPreferences("data_session",Context.MODE_PRIVATE);
        idusuario = sharedPreferences.getInt("idusuario",0);
        correo = sharedPreferences.getString("correo",null);

        RetrofitConnection retrofitConnection = new RetrofitConnection();
        DataInterface dataInterface = retrofitConnection.connectRetrofit(Connections.API_URL);
        Call<empresa> service = dataInterface.perfil_empresa(idusuario);
        service.enqueue(new Callback<empresa>() {
            @Override
            public void onResponse(Call<empresa> call, Response<empresa> response) {
                if(response.isSuccessful()){
                    empresa e = response.body();
                    if(e.isStatus()==true){
                        SharedPreferences sharedPreferences = getSharedPreferences("data_session",Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("idempresa",e.getIdempresa());
                        editor.putString("nombre",e.getNombre());
                        editor.putString("descripcion",e.getDescripcion());
                        editor.putString("telefono",e.getTelefono());
                        editor.putString("giro",e.getGiro());
                        editor.commit();

                        tv_nombre_nav.setText(e.getNombre());
                        tv_correo_nav.setText(correo);
                    }else{
                        Toast.makeText(principal_empresa.this, "Error al obtener los datos de la empresa...", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(principal_empresa.this, "Error de operacion...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<empresa> call, Throwable t) {
                Toast.makeText(principal_empresa.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void dialog(){
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);

        builder.setMessage("¿Confirma la acción seleccionada?")
                .setTitle("Confirmacion")
                .setPositiveButton("Cancelar", new DialogInterface.OnClickListener()  {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                })
                .setNegativeButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SharedPreferences sharedPreferences = getSharedPreferences("data_session",Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear().commit();
                        Intent intent = new Intent(context, MainActivity.class);
                        startActivity(intent);
                        dialog.cancel();
                    }
                });

        android.app.AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void descarga_foto_perfil() {
        SharedPreferences sharedPreferences = getSharedPreferences("data_session",Context.MODE_PRIVATE);
        idusuario = sharedPreferences.getInt("idusuario",0);
        RetrofitConnection retrofitConnection = new RetrofitConnection();
        DataInterface dataInterface = retrofitConnection.connectRetrofit(Connections.API_URL);
        Call<foto_perfil_descarga> service = dataInterface.foto_perfil_descarga(idusuario);
        service.enqueue(new Callback<foto_perfil_descarga>() {
            @Override
            public void onResponse(Call<foto_perfil_descarga> call, Response<foto_perfil_descarga> response) {
                if(response.isSuccessful()){
                    foto_perfil_descarga fpd = response.body();
                    if(fpd.isStatus()==true){
                        //Toast.makeText(getContext(), fpd.getFoto_perfil(), Toast.LENGTH_SHORT).show();
                        Glide.with(context).load(fpd.getFoto_perfil()).into(profile_image);
                    }
                    Toast.makeText(context, String.valueOf(idusuario), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<foto_perfil_descarga> call, Throwable t) {

            }
        });
    }

}
