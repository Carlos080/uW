package com.xr45labs.uworkers;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mikepenz.iconics.context.IconicsContextWrapper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText Et_usuario,Et_contrasena;
    Button Btn_login;
    String tipo;
    Intent intent;
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

        HideKeyboard esconderteclado = new HideKeyboard();
        esconderteclado.setupUI(findViewById(R.id.activity_main),MainActivity.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Btn_login:
                tipo = Et_usuario.getText().toString();
                if(!tipo.isEmpty()) {
                    switch (tipo) {
                        case "alumno":
                            intent = new Intent(this, principal_alumnos.class);
                            startActivity(intent);
                            break;

                        case "empresa":
                            intent = new Intent(this, principal_empresa.class);
                            startActivity(intent);
                            break;

                        case "instituto":
                            intent = new Intent(this, principal_instituto.class);
                            startActivity(intent);
                            break;

                        default:
                            Toast toast = Toast.makeText(this, "El usuario o contrasena son incorrectos...", Toast.LENGTH_SHORT);
                            toast.show();
                            break;
                    }
                    //Intent intent = new Intent(this,principal.class);
                    //startActivity(intent);

                    break;
                }else {
                    Toast.makeText(this,"Campos vacios...",Toast.LENGTH_SHORT).show();
                }
        }
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(IconicsContextWrapper.wrap(newBase));
    }
}
