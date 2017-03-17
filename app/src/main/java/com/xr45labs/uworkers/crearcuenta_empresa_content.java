package com.xr45labs.uworkers;

import android.content.Context;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mikepenz.iconics.context.IconicsContextWrapper;
import com.xr45labs.uworkers.fragments.empresas.fr_registro_empresa;

public class crearcuenta_empresa_content extends AppCompatActivity implements fr_registro_empresa.OnFragmentInteractionListener {

    Fragment fragment = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crearcuenta_empresa_content);

        fragment = new fr_registro_empresa();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.activity_crearcuenta_empresa_content,fragment).commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(IconicsContextWrapper.wrap(newBase));
    }
}
