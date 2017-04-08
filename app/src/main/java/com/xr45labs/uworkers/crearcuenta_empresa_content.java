package com.xr45labs.uworkers;

import android.content.Context;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mikepenz.iconics.context.IconicsContextWrapper;
import com.xr45labs.uworkers.Modelo.GeneralPOJO;
import com.xr45labs.uworkers.Util.Connections;
import com.xr45labs.uworkers.Util.DataInterface;
import com.xr45labs.uworkers.Util.RetrofitConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class crearcuenta_empresa_content extends AppCompatActivity implements View.OnClickListener {

    EditText et_nombre_empresa, et_email, et_contrasena, et_contrasena_confir;
    Button btn_guardar;
    String nombre_empresa, email, contrasena;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crearcuenta_empresa_content);

        et_nombre_empresa = (EditText) findViewById(R.id.et_nombre_empresa);
        et_email = (EditText) findViewById(R.id.et_email);
        et_contrasena = (EditText) findViewById(R.id.et_contrasena);
        et_contrasena_confir = (EditText) findViewById(R.id.et_contrasena_confir);
        btn_guardar = (Button) findViewById(R.id.btn_guardar);
        btn_guardar.setOnClickListener(this);

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(IconicsContextWrapper.wrap(newBase));
    }

    @Override
    public void onClick(View v) {

        if ((!et_nombre_empresa.getText().toString().equals("")) && (!et_email.getText().toString().equals("")) && (!et_contrasena.getText().toString().equals("")) && (!et_contrasena_confir.getText().toString().equals(""))) {

            if (android.util.Patterns.EMAIL_ADDRESS.matcher(et_email.getText().toString()).matches()) {

                if (et_contrasena.getText().toString().equals(et_contrasena_confir.getText().toString())) {

                    nombre_empresa = et_nombre_empresa.getText().toString();
                    email = et_email.getText().toString();
                    contrasena = et_contrasena.getText().toString();

                    registro(nombre_empresa,email,contrasena);

                } else {
                    Toast.makeText(this, "La contrasena no coincide...", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(this, "El email no es valido...", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "No se llenaron los campos correctamente...", Toast.LENGTH_SHORT).show();
        }

    }

    public void registro(String nombre_empresa, String email, String contrasena){

        RetrofitConnection retrofitConnection = new RetrofitConnection();
        DataInterface dataInterface = retrofitConnection.connectRetrofit(Connections.API_URL);
        final Call<GeneralPOJO> service = dataInterface.reg_empresas(nombre_empresa,email,contrasena);
        service.enqueue(new Callback<GeneralPOJO>() {
            @Override
            public void onResponse(Call<GeneralPOJO> call, Response<GeneralPOJO> response) {
                if(response.isSuccessful()){
                    GeneralPOJO generalPOJO = response.body();

                    if(generalPOJO.isStatus()!= false){
                        Toast.makeText(getApplicationContext(), generalPOJO.getMessage()+" Insercion correcta...", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(), generalPOJO.getMessage()+" Hubo algun problema...", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Error..." , Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GeneralPOJO> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}

