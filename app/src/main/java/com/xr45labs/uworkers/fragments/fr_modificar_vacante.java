package com.xr45labs.uworkers.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.xr45labs.uworkers.Modelo.GeneralPOJO;
import com.xr45labs.uworkers.Modelo.vacante;
import com.xr45labs.uworkers.R;
import com.xr45labs.uworkers.Util.Connections;
import com.xr45labs.uworkers.Util.DataInterface;
import com.xr45labs.uworkers.Util.RetrofitConnection;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link fr_modificar_vacante.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link fr_modificar_vacante#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fr_modificar_vacante extends Fragment implements View.OnClickListener {
    EditText et_nombre_vacante,et_descripcion_vacante,et_sueldo;
    RadioGroup rg_horario,rg_turno;
    Button btn_guardar;
    String nombre,descripcion,turno="",horario="",fecha_publicacion;
    int idvacante,sueldo;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public fr_modificar_vacante() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fr_modificar_vacante.
     */
    // TODO: Rename and change types and number of parameters
    public static fr_modificar_vacante newInstance(String param1, String param2) {
        fr_modificar_vacante fragment = new fr_modificar_vacante();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_fr_modificar_vacante, container, false);
        idvacante = getArguments().getInt("idvacante");
        datos_vacante(idvacante);

        et_nombre_vacante = (EditText) rootview.findViewById(R.id.et_nombre_vacante);
        et_descripcion_vacante = (EditText) rootview.findViewById(R.id.et_descripcion_vacante);
        et_sueldo = (EditText) rootview.findViewById(R.id.et_sueldo);
        rg_horario = (RadioGroup) rootview.findViewById(R.id.rg_horario);
        btn_guardar = (Button) rootview.findViewById(R.id.btn_guardar);
        btn_guardar.setOnClickListener(this);

        rg_horario.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int horarioId) {
                switch(horarioId){
                    case R.id.rb_matutino:
                        horario = "Matutino";
                        break;

                    case R.id.rb_vespertino:
                        horario = "Vespertino";
                        break;

                    case R.id.rb_nocturno:
                        horario = "Nocturno";
                        break;

                    default:
                        horario = "";
                        break;
                }
            }
        });
        rg_turno = (RadioGroup) rootview.findViewById(R.id.rg_turno);
        rg_turno.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int turnoId) {
                switch(turnoId){
                    case R.id.rb_mediotiempo:
                        turno = "Medio tiempo";
                        break;

                    case R.id.rb_tiempo_completo:
                        turno = "Tiempo completo";
                        break;

                    default:
                        turno ="";
                        break;
                }
            }
        });



        return rootview;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        //datos();
        dialog();
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void datos_vacante(final int idvacante){
        RetrofitConnection retrofitConnection = new RetrofitConnection();
        DataInterface dataInterface = retrofitConnection.connectRetrofit(Connections.API_URL);
        Call<vacante> service = dataInterface.datos_vacante(idvacante);
        service.enqueue(new Callback<vacante>() {
            @Override
            public void onResponse(Call<vacante> call, Response<vacante> response) {
                if(response.isSuccessful()){
                    vacante v = response.body();
                    if(v.isStatus()==true){
                        //Toast.makeText(getContext(), v.getNombre(), Toast.LENGTH_SHORT).show();
                        et_nombre_vacante.setText(v.getNombre());
                        et_descripcion_vacante.setText(v.getDescripcion());
                        et_sueldo.setText(String.valueOf(v.getSueldo()));
                    }else{
                        Toast.makeText(getContext(), v.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getContext(), "Error...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<vacante> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void mod_vacante(String nombre, String descripcion, String turno, String horario, int sueldo, String fecha_publicacion, int idvacante){
        RetrofitConnection retrofitConnection = new RetrofitConnection();
        DataInterface dataInterface = retrofitConnection.connectRetrofit(Connections.API_URL);
        Call<GeneralPOJO> servicio = dataInterface.mod_vacante(nombre,descripcion,turno,horario,sueldo,fecha_publicacion,idvacante);
        servicio.enqueue(new Callback<GeneralPOJO>() {
            @Override
            public void onResponse(Call<GeneralPOJO> call, Response<GeneralPOJO> response) {
                if(response.isSuccessful()){
                    GeneralPOJO generalPOJO = response.body();
                    if(generalPOJO.isStatus()==true){
                        Toast.makeText(getContext(), generalPOJO.getMessage(), Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getContext(), generalPOJO.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getContext(), "Error...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GeneralPOJO> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void datos(){
        if((!et_nombre_vacante.getText().toString().equals("")) && (!et_descripcion_vacante.getText().toString().equals("")) && (!et_sueldo.getText().toString().equals("")) && (!horario.equals("")) && (!turno.equals(""))) {
            nombre = et_nombre_vacante.getText().toString();
            descripcion = et_descripcion_vacante.getText().toString();
            sueldo = Integer.parseInt(et_sueldo.getText().toString());
            Date fechaActual = new Date();
            SimpleDateFormat apptivaWeb = new SimpleDateFormat("dd-MM-yyyy");
            fecha_publicacion = apptivaWeb.format(fechaActual);

            mod_vacante(nombre,descripcion,turno,horario,sueldo,fecha_publicacion,idvacante);
            //Toast.makeText(getContext(), nombre+" "+horario+" "+turno+" "+" "+String.valueOf(sueldo)+" "+String.valueOf(idvacante)+" "+ fecha_publicacion, Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getContext(), "No se han llenado los campos correctamente...", Toast.LENGTH_SHORT).show();
        }



    }

    public void dialog(){
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());

        builder.setMessage("¿Confirma la acción seleccionada?")
                .setTitle("Confirmacion")
                .setPositiveButton("Cancelar", new DialogInterface.OnClickListener()  {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                })
                .setNegativeButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        datos();
                        dialog.cancel();
                    }
                });

        android.app.AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}
