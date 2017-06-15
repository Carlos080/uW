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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mikepenz.iconics.context.IconicsContextWrapper;
import com.xr45labs.uworkers.Modelo.alumno;
import com.xr45labs.uworkers.Modelo.foto_perfil_descarga;
import com.xr45labs.uworkers.Util.Connections;
import com.xr45labs.uworkers.Util.DataInterface;
import com.xr45labs.uworkers.Util.RetrofitConnection;
import com.xr45labs.uworkers.fragments.alumnos.fr_alumno_vacante;
import com.xr45labs.uworkers.fragments.fr_bempresas;
import com.xr45labs.uworkers.fragments.fr_bvacantes;
import com.xr45labs.uworkers.fragments.alumnos.fr_perfil_alumno;
import com.xr45labs.uworkers.fragments.alumnos.fr_perfil_alumno_config;
import com.xr45labs.uworkers.fragments.fr_perfil_empresa_externo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class principal_alumnos extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        fr_perfil_alumno.OnFragmentInteractionListener,
        fr_bvacantes.OnFragmentInteractionListener,
        fr_perfil_alumno_config.OnFragmentInteractionListener,
        fr_bempresas.OnFragmentInteractionListener,
        fr_alumno_vacante.OnFragmentInteractionListener,
        fr_perfil_empresa_externo.OnFragmentInteractionListener{

    Fragment fragment = null;
    boolean FragmentTransaction = false;
    de.hdodenhof.circleimageview.CircleImageView profile_image;
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


        View vistaheader = navigationView.getHeaderView(0);

        profile_image = (de.hdodenhof.circleimageview.CircleImageView) vistaheader.findViewById(R.id.profile_image);
        tv_nombre = (TextView) vistaheader.findViewById(R.id.tv_nombre_nav);
        tv_nocontrol = (TextView) vistaheader.findViewById(R.id.tv_nocontrol_nav);

        datos_perfil();
        descarga_foto_perfil();

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

        if (id == R.id.nav_bvacantes) {
            fragment = new fr_bvacantes();
            Bundle bundle = new Bundle();
            bundle.putInt("idempresa",0);
            fragment.setArguments(bundle);
            FragmentTransaction = true;
        }  else if(id==R.id.nav_bempresas){
            fragment = new fr_bempresas();
            FragmentTransaction = true;
        }  else if (id == R.id.nav_perfil) {
            fragment = new fr_perfil_alumno();
            FragmentTransaction = true;
        }  else if (id == R.id.nav_logout) {
            SharedPreferences sharedPreferences = getSharedPreferences("data_session",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear().commit();
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
        idusuario = sharedPreferences.getInt("idusuario",0);

        RetrofitConnection retrofitConnection = new RetrofitConnection();
        DataInterface dataInterface = retrofitConnection.connectRetrofit(Connections.API_URL);
        Call<alumno> service = dataInterface.perfil_alumno(idusuario);
        service.enqueue(new Callback<alumno>() {
            @Override
            public void onResponse(Call<alumno> call, Response<alumno> response) {
                if(response.isSuccessful()){
                    alumno a = response.body();
                    if(a.isStatus()==true){
                        nombre = a.getNombre();
                        no_control = a.getNo_control();
                        SharedPreferences sharedPreferences = getSharedPreferences("data_session",Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("nombre",a.getNombre());
                        editor.putInt("no_control",a.getNo_control());
                        editor.putString("telefono",a.getTelefono());
                        editor.putString("carrera",a.getCarrera());
                        editor.putString("objetivos",a.getObjetivos());
                        editor.putString("conocimientos",a.getConocimientos());
                        editor.putString("experiencia_laboral",a.getExperiencia_laboral());
                        editor.commit();

                        tv_nombre.setText(nombre);
                        tv_nocontrol.setText(String.valueOf(no_control));
                    }else {
                        Toast.makeText(principal_alumnos.this, a.getMessage(), Toast.LENGTH_SHORT).show();

                    }

                }else{
                    Toast.makeText(principal_alumnos.this, "Error al cargar...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<alumno> call, Throwable t) {
                Toast.makeText(principal_alumnos.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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
                        Glide.with(principal_alumnos.this).load(fpd.getFoto_perfil()).into(profile_image);
                    }
                    Toast.makeText(principal_alumnos.this, String.valueOf(idusuario), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<foto_perfil_descarga> call, Throwable t) {
                Toast.makeText(principal_alumnos.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
