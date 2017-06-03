package com.xr45labs.uworkers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.mikepenz.iconics.context.IconicsContextWrapper;
import com.xr45labs.uworkers.Modelo.alumno;
import com.xr45labs.uworkers.Modelo.empresa;
import com.xr45labs.uworkers.Modelo.login;
import com.xr45labs.uworkers.Util.Connections;
import com.xr45labs.uworkers.Util.DataInterface;
import com.xr45labs.uworkers.Util.RetrofitConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText Et_usuario,Et_contrasena;
    Button Btn_login,btn_signup;
    RadioGroup radioGroup;
    Intent intent;
    String correo, contrasena,tipoc,foto_perfil,foto_fondo;
    String objetivos,conocimientos,experiencia_laboral,carrera,nombre,curriculum;

    int idusuario,tipo,no_control;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Et_usuario = (EditText) findViewById(R.id.Et_usuario);
        Et_usuario.setHintTextColor(getResources().getColor(R.color.colorAccent));
        Et_contrasena = (EditText) findViewById(R.id.Et_contrasena);
        Et_contrasena.setHintTextColor(getResources().getColor(R.color.colorAccent));
        Btn_login = (Button) findViewById(R.id.Btn_login);
        Btn_login.setOnClickListener(this);
        btn_signup = (Button) findViewById(R.id.btn_sigup);
        btn_signup.setOnClickListener(this);
        radioGroup = (RadioGroup) findViewById(R.id.rg);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch(checkedId){
                    case R.id.rb_alumno:
                        tipoc = "alumno";
                        break;

                    case R.id.rb_empresa:
                        tipoc = "empresa";
                        break;
                }
            }
        });

        comprobacion_sesion();

        HideKeyboard esconderteclado = new HideKeyboard();
        esconderteclado.setupUI(findViewById(R.id.activity_main),MainActivity.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.Btn_login:
                //tipo = Et_usuario.getText().toString();
                if((!Et_usuario.getText().toString().equals("")) && (!Et_contrasena.getText().toString().equals(""))) {
                    if(android.util.Patterns.EMAIL_ADDRESS.matcher(Et_usuario.getText().toString()).matches()){
                        correo = Et_usuario.getText().toString();
                        contrasena = Et_contrasena.getText().toString();
                        login_metodo(correo,contrasena);
                    }


                }else {
                    Toast.makeText(this,"Campos vacios...",Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.btn_sigup:
                if(tipoc!=null){
                    switch(tipoc){
                        case "alumno":
                            intent = new Intent(this, crearcuenta_alumno_content.class);
                            startActivity(intent);
                            break;

                        case "empresa":
                            intent = new Intent(this, crearcuenta_empresa_content.class);
                            startActivity(intent);
                            break;
                        default:
                            break;
                    }
                }else{
                    Toast toast = Toast.makeText(this, "Selecciona un tipo de usuario...", Toast.LENGTH_SHORT);
                    toast.show();
                }

                break;
        }
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(IconicsContextWrapper.wrap(newBase));
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

                        SharedPreferences sharedPreferences = getSharedPreferences("data_session",Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("correo",correo);
                        editor.putString("contrasena",contrasena);
                        editor.putInt("idusuario",l.getIdusuario());
                        editor.putString("foto_perfil",l.getFoto_perfil());
                        editor.putString("foto_fondo",l.getFoto_fondo());
                        editor.putInt("tipo",l.getTipo());
                        editor.commit();

                        ///
                        switch(l.getTipo()){
                            case 1:
                                alumno_datos(idusuario);
                                intent = new Intent(getApplicationContext(),principal_alumnos.class);
                                startActivity(intent);
                                break;

                            case 2:
                                empresa_datos(idusuario);
                                intent = new Intent(getApplicationContext(),principal_empresa.class);
                                startActivity(intent);
                                break;

                            case 3:
                                intent = new Intent(getApplicationContext(),principal_instituto.class);
                                startActivity(intent);
                                break;

                            default:
                                Toast.makeText(getApplicationContext(),"Error de identificacion",Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }else{
                        Toast.makeText(MainActivity.this,l.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(MainActivity.this,"No se encontro registro", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<login> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void comprobacion_sesion(){
        SharedPreferences sharedPreferences = getSharedPreferences("data_session",Context.MODE_PRIVATE);

        correo = sharedPreferences.getString("correo",null);
        contrasena = sharedPreferences.getString("contrasena",null);
        idusuario = sharedPreferences.getInt("idusuario",0);
        foto_perfil = sharedPreferences.getString("foto_perfil",null);
        foto_fondo = sharedPreferences.getString("foto_fondo",null);
        tipo = sharedPreferences.getInt("tipo",0);

        login_metodo(correo,contrasena);

    }

    public void alumno_datos(int idusuario){

        RetrofitConnection retrofitConnection = new RetrofitConnection();
        DataInterface dataInterface = retrofitConnection.connectRetrofit(Connections.API_URL);
        Call<alumno> service = dataInterface.perfil_alumno(idusuario);
        service.enqueue(new Callback<alumno>() {
            @Override
            public void onResponse(Call<alumno> call, Response<alumno> response) {
                if(response.isSuccessful()){
                    alumno a = response.body();
                    if(a.isStatus()==true){
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

                    }else {
                        Toast.makeText(MainActivity.this, a.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(MainActivity.this, "Error al cargar...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<alumno> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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
                        Toast.makeText(MainActivity.this, "Error al obtener los datos de la empresa...", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(MainActivity.this, "Error de operacion...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<empresa> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
