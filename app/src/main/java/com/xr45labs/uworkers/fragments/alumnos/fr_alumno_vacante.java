package com.xr45labs.uworkers.fragments.alumnos;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.xr45labs.uworkers.R;
import com.xr45labs.uworkers.fragments.fr_perfil_empresa_externo;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link fr_alumno_vacante.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link fr_alumno_vacante#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fr_alumno_vacante extends Fragment implements View.OnClickListener {
    TextView tv_nombre_vacante, tv_descripcion_vacante,tv_horario,tv_turno,tv_sueldo,tv_fecha_publicacion;
    Button btn_perfil_publicante;
    int idempresa;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public fr_alumno_vacante() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fr_alumno_vacante.
     */
    // TODO: Rename and change types and number of parameters
    public static fr_alumno_vacante newInstance(String param1, String param2) {
        fr_alumno_vacante fragment = new fr_alumno_vacante();
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

        View rootview =  inflater.inflate(R.layout.fragment_fr_alumno_vacante, container, false);
        btn_perfil_publicante  = (Button) rootview.findViewById(R.id.btn_perfil_publicante);
        btn_perfil_publicante.setOnClickListener(this);
        tv_nombre_vacante = (TextView) rootview.findViewById(R.id.tv_nombre_vacante);
        tv_descripcion_vacante =(TextView) rootview.findViewById(R.id.tv_descripcion_vacante);
        tv_horario = (TextView) rootview.findViewById(R.id.tv_horario);
        tv_turno = (TextView) rootview.findViewById(R.id.tv_turno);
        tv_sueldo = (TextView) rootview.findViewById(R.id.tv_sueldo);
        tv_fecha_publicacion = (TextView) rootview.findViewById(R.id.tv_fecha_publicacion);
        idempresa = getArguments().getInt("idempresa");
        String nombre,descripcion,turno,horario,sueldo,fecha;
        nombre = getArguments().getString("nombre");
        descripcion = getArguments().getString("descripcion");
        turno = getArguments().getString("turno");
        horario = getArguments().getString("horario");
        sueldo = getArguments().getString("sueldo");
        fecha = getArguments().getString("fecha");

        tv_nombre_vacante.setText(nombre);
        tv_descripcion_vacante.setText(descripcion);
        tv_horario.setText(horario);
        tv_turno.setText(turno);
        tv_sueldo.setText(sueldo);
        tv_fecha_publicacion.setText(fecha);


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
        Fragment fragment = new fr_perfil_empresa_externo();
        Bundle bundle = new Bundle();
        bundle.putInt("idempresa",idempresa);
        fragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_principal_alumnos,fragment,null);
        fragmentTransaction.commit();
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

}
