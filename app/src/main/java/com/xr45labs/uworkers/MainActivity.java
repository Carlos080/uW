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

        //comprobacion_sesion();

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
                nueva_cuenta();
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
                                intent = new Intent(getApplicationContext(),principal_alumnos.class);
                                startActivity(intent);
                                break;

                            case 2:
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

    public void nueva_cuenta(){
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
    }




}
