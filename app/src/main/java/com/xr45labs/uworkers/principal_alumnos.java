package com.xr45labs.uworkers;

import android.content.Context;
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
import android.widget.Toast;

import com.mikepenz.iconics.context.IconicsContextWrapper;
import com.xr45labs.uworkers.Modelo.alumnos;
import com.xr45labs.uworkers.Util.Connections;
import com.xr45labs.uworkers.Util.DataInterface;
import com.xr45labs.uworkers.Util.RetrofitConnection;
import com.xr45labs.uworkers.fragments.fr_bempresas;
import com.xr45labs.uworkers.fragments.fr_bvacantes;
import com.xr45labs.uworkers.fragments.alumnos.fr_perfil_alumno;
import com.xr45labs.uworkers.fragments.alumnos.fr_perfil_alumno_config;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class principal_alumnos extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        fr_perfil_alumno.OnFragmentInteractionListener,
        fr_bvacantes.OnFragmentInteractionListener,
        fr_perfil_alumno_config.OnFragmentInteractionListener,
        fr_bempresas.OnFragmentInteractionListener{

    Fragment fragment = null;
    boolean FragmentTransaction = false;
    int idusuario,no_control;
    String objetivos,conocimientos,experiencia_laboral,carrera,nombre,correo,curriculum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_alumnos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        datos_perfil();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_bvacantes) {
            fragment = new fr_bvacantes();
            FragmentTransaction = true;
        }  else if(id==R.id.nav_bempresas){
            fragment = new fr_bempresas();
            FragmentTransaction = true;
        }  else if (id == R.id.nav_perfil) {
            fragment = new fr_perfil_alumno();
            FragmentTransaction = true;
        }  else if (id == R.id.nav_logout) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

        if(FragmentTransaction){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_principal_alumnos,fragment)
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

    public void idusuario_almacenado(){
        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        idusuario = 0;
        if(sharedPreferences!=null) {
            idusuario =  sharedPreferences.getInt("idusuario",0);
            correo = sharedPreferences.getString("correo",null);
        }

    }

    public void datos_perfil (){

        idusuario_almacenado();

        if(idusuario!=0){
            RetrofitConnection retrofitConnection = new RetrofitConnection();
            DataInterface dataInterface = retrofitConnection.connectRetrofit(Connections.API_URL);
            Call<alumnos> service = dataInterface.perfil_alumno(idusuario);
            service.enqueue(new Callback<alumnos>() {
                @Override
                public void onResponse(Call<alumnos> call, Response<alumnos> response) {
                    if(response.isSuccessful()){
                        alumnos a = response.body();

                        if(a.getStatus().equals(true)){
                            nombre = a.getNombre().toString();
                            no_control = a.getNo_control();
                            carrera = a.getCarrera().toString();
                            objetivos = a.getObjetivos().toString();
                            conocimientos = a.getConocimientos().toString();
                            experiencia_laboral = a.getExperiencia_laboral();
                            curriculum =a.getCurriculum().toString();

                            SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("nombre",nombre);
                            editor.putInt("no_control",no_control);
                            editor.putString("carrera",carrera);
                            editor.putString("objetivos",objetivos);
                            editor.putString("conocimientos",conocimientos);
                            editor.putString("experiencia_laboral",experiencia_laboral);
                            editor.putString("curriculum",curriculum);
                            editor.commit();


                        }else {
                            Toast.makeText(getApplicationContext(), a.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(),"Error...",Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<alumnos> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
