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
import android.view.View;
import android.widget.TextView;

import com.mikepenz.iconics.context.IconicsContextWrapper;
import com.xr45labs.uworkers.fragments.*;
import com.xr45labs.uworkers.fragments.empresas.fr_add_vacante_empresa;
import com.xr45labs.uworkers.fragments.empresas.fr_perfil_empresa;
import com.xr45labs.uworkers.fragments.empresas.fr_perfil_empresa_config;

public class principal_empresa extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        fr_perfil_empresa.OnFragmentInteractionListener,
        fr_bvacantes.OnFragmentInteractionListener,
        fr_balumnos.OnFragmentInteractionListener,
        fr_perfil_empresa_config.OnFragmentInteractionListener,
        fr_add_vacante_empresa.OnFragmentInteractionListener{
    String nombre,correo;
    TextView tv_nombre_nav,tv_correo_nav;
    Fragment fragment = null;
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

        datos_perfil();
        View vistaheader = navigationView.getHeaderView(0);
        tv_nombre_nav = (TextView) vistaheader.findViewById(R.id.tv_nombre_nav);
        tv_correo_nav = (TextView) vistaheader.findViewById(R.id.tv_correo_nav);
        tv_nombre_nav.setText(nombre);
        tv_correo_nav.setText(correo);

        Fragment fragment = new fr_perfil_empresa();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_principal_empresa,fragment)
                .commit();

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
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

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
        nombre = sharedPreferences.getString("nombre",null);
        correo = sharedPreferences.getString("correo",null);
    }
}
