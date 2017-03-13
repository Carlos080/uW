package com.xr45labs.uworkers;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.mikepenz.iconics.context.IconicsContextWrapper;
import com.xr45labs.uworkers.fragments.fr_balumnos;
import com.xr45labs.uworkers.fragments.fr_bempresas;
import com.xr45labs.uworkers.fragments.fr_bvacantes;
import com.xr45labs.uworkers.fragments.instituto.fr_perfil_instituto;
import com.xr45labs.uworkers.fragments.instituto.fr_perfil_instituto_config;

public class principal_instituto extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        fr_balumnos.OnFragmentInteractionListener,
        fr_bvacantes.OnFragmentInteractionListener,
        fr_bempresas.OnFragmentInteractionListener,
        fr_perfil_instituto.OnFragmentInteractionListener,
        fr_perfil_instituto_config.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_instituto);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
        Fragment fragment = null;
        boolean fragmenttransaction = false;

        if (id == R.id.nav_balumnos) {
            fragment = new fr_balumnos();
            fragmenttransaction = true;

        } else if (id == R.id.nav_bvacantes) {
            fragment = new fr_bvacantes();
            fragmenttransaction = true;

        } else if (id == R.id.nav_bempresas) {
            fragment = new fr_bempresas();
            fragmenttransaction = true;
        } else if (id == R.id.nav_addvacantes) {

        } else if (id == R.id.nav_perfil) {
            fragment = new fr_perfil_instituto();
            fragmenttransaction = true;

        } else if (id == R.id.nav_logout) {

        }

        if(fragmenttransaction){
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_principal_instituto,fragment).commit();
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
        }
