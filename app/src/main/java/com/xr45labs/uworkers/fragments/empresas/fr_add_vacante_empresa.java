package com.xr45labs.uworkers.fragments.empresas;

import android.content.Context;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.xr45labs.uworkers.R;
import com.xr45labs.uworkers.Util.RetrofitConnection;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link fr_add_vacante_empresa.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link fr_add_vacante_empresa#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fr_add_vacante_empresa extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    String nombre,descripcion,horario,turno,fecha;
    int sueldo;
    EditText et_nombre_vacante,et_descripcion,et_sueldo;
    RadioGroup rg_horario,rg_turno;
    Button btn_guardar;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public fr_add_vacante_empresa() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fr_add_vacante_empresa.
     */
    // TODO: Rename and change types and number of parameters
    public static fr_add_vacante_empresa newInstance(String param1, String param2) {
        fr_add_vacante_empresa fragment = new fr_add_vacante_empresa();
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
        final View rootview = inflater.inflate(R.layout.fragment_fr_add_vacante, container, false);
        et_nombre_vacante = (EditText) rootview.findViewById(R.id.et_descripcion_vacante);
        et_descripcion = (EditText) rootview.findViewById(R.id.et_descripcion_vacante);
        et_sueldo = (EditText) rootview.findViewById(R.id.et_sueldo);
        rg_horario = (RadioGroup) rootview.findViewById(R.id.rg_horario);
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
                        turno = "Turno completo";
                        break;

                    default:
                        turno = "";
                        break;
                }
            }
        });
        btn_guardar = (Button) rootview.findViewById(R.id.btn_guardar);
        btn_guardar.setOnClickListener(this);

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
        Toast.makeText(getContext(), horario+"   "+turno, Toast.LENGTH_SHORT).show();
        guardar_vacante();
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

    public void guardar_vacante(){
        Date fechaActual = new Date();
        SimpleDateFormat apptivaWeb = new SimpleDateFormat("dd-MM-yyyy");
        fecha = apptivaWeb.format(fechaActual);
        Toast.makeText(getContext(), fecha, Toast.LENGTH_SHORT).show();
    }
}
