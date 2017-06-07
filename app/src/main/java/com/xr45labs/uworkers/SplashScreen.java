package com.xr45labs.uworkers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuAdapter;
import android.widget.Toast;

import com.xr45labs.uworkers.Modelo.alumno;
import com.xr45labs.uworkers.Modelo.empresa;
import com.xr45labs.uworkers.Modelo.login;
import com.xr45labs.uworkers.Util.Connections;
import com.xr45labs.uworkers.Util.DataInterface;
import com.xr45labs.uworkers.Util.RetrofitConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashScreen extends AppCompatActivity {
    Intent intent;
    int idusuario,tipo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

       comprobacion_sesion();
    }



    public void comprobacion_sesion(){
        SharedPreferences sharedPreferences = getSharedPreferences("data_session", Context.MODE_PRIVATE);
        String correo = sharedPreferences.getString("correo","");
        String contrasena = sharedPreferences.getString("contrasena","");

        if((!correo.equals(""))&&(!contrasena.equals(""))){
            login_metodo(correo,contrasena);
        }else{
            intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }
    }


    public void login_metodo(final String correo, final String contrasena){

        RetrofitConnection retrofitConnection = new RetrofitConnection();
        DataInterface dataInterface = retrofitConnection.connectRetrofit(Connections.API_URL);
        Call<login> service =  dataInterface.login_usuarios(correo,contrasena);
        service.enqueue(new Callback<login>() {
            @Override
            public void onResponse(Call<login> call, Response<login> response) {
                if(response.isSuccessful()){
                    login l = response.body();

                    if(l.isStatus()==true){
                        //Agragar el codigo de shared preferences
                        idusuario = l.getIdusuario();
                        String foto_perfil = l.getFoto_perfil();
                        String foto_fondo = l.getFoto_fondo();
                        tipo = l.getTipo();

                        SharedPreferences sharedPreferences = getSharedPreferences("data_session",Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("correo",correo);
                        editor.putString("contrasena",contrasena);
                        editor.putInt("idusuario",idusuario);
                        editor.putString("foto_perfil",foto_perfil);
                        editor.putString("foto_fondo",foto_fondo);
                        editor.putInt("tipo",tipo);
                        editor.commit();

                        tipo_session(tipo);

                    }else{
                        //Toast.makeText(SplashScreen.this,l.getMessage(), Toast.LENGTH_SHORT).show();
                        intent = new Intent(getApplicationContext(),MainActivity.class);
                    }
                }else{
                    intent = new Intent(getApplicationContext(),MainActivity.class);
                }
            }

            @Override
            public void onFailure(Call<login> call, Throwable t) {
                intent = new Intent(getApplicationContext(),MainActivity.class);
                //Toast.makeText(SplashScreen.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void tipo_session(int tipo){
        switch(tipo){
            case 1:
                //alumno_datos(idusuario);
                intent = new Intent(getApplicationContext(),principal_alumnos.class);
                startActivity(intent);

                break;

            case 2:
                empresa_datos(idusuario);
                intent = new Intent(getApplicationContext(),principal_alumnos.class);
                startActivity(intent);
                break;

            case 3:
                break;

            default:
                Toast.makeText(this, "kjsdjhlskjh", Toast.LENGTH_SHORT).show();
                break;
        }
    }



    public void empresa_datos(int idusuario){
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
                    }else{
                        //Toast.makeText(SplashScreen.this, "Error al obtener los datos de la empresa...", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    //Toast.makeText(SplashScreen.this, "Error de operacion...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<empresa> call, Throwable t) {
                //Toast.makeText(SplashScreen.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
