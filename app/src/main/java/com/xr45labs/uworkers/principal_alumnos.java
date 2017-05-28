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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mikepenz.iconics.context.IconicsContextWrapper;
import com.xr45labs.uworkers.Modelo.alumnos;
import com.xr45labs.uworkers.Util.Connections;
import com.xr45labs.uworkers.Util.DataInterface;
import com.xr45labs.uworkers.Util.RetrofitConnection;
import com.xr45labs.uworkers.fragments.alumnos.fr_alumno_vacante;
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
        fr_bempresas.OnFragmentInteractionListener,
        fr_alumno_vacante.OnFragmentInteractionListener {

    Fragment fragment = null;
    boolean FragmentTransaction = false;
    int idusuario,no_control;
    String objetivos,conocimientos,experiencia_laboral,carrera,nombre,correo,curriculum;
    TextView tv_nombre,tv_nocontrol;

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
        View vistaheader = navigationView.getHeaderView(0);

        tv_nombre = (TextView) vistaheader.findViewById(R.id.tv_nombre_nav);
        tv_nocontrol = (TextView) vistaheader.findViewById(R.id.tv_nocontrol_nav);
        tv_nombre.setText(nombre);
        tv_nocontrol.setText(String.valueOf(no_control));

        Fragment fragment = new fr_perfil_alumno();
        boolean fragmentTransaction = true;
        if(fragmentTransaction)
        {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_principal_alumnos, fragment)
                    .commit();
        }


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

    public void datos_perfil(){
        SharedPreferences sharedPreferences = getSharedPreferences("data_session",Context.MODE_PRIVATE);
        nombre = sharedPreferences.getString("nombre",null);
        no_control = sharedPreferences.getInt("no_control",0);
    }

}
