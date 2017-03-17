package com.xr45labs.uworkers;

import android.content.Context;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import com.mikepenz.iconics.context.IconicsContextWrapper;
import com.xr45labs.uworkers.fragments.alumnos.fr_registro_alumnos;

public class crearcuenta_alumno_content extends AppCompatActivity implements fr_registro_alumnos.OnFragmentInteractionListener {

    Fragment fragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crearcuenta_alumno_content);

        fragment = new fr_registro_alumnos();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.activiy_crearcuenta_alumno_content,fragment).commit();


    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(IconicsContextWrapper.wrap(newBase));
    }
}
