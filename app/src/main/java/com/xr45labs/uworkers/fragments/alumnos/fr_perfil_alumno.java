package com.xr45labs.uworkers.fragments.alumnos;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xr45labs.uworkers.MainActivity;
import com.xr45labs.uworkers.Modelo.alumnos;
import com.xr45labs.uworkers.R;
import com.xr45labs.uworkers.Util.Connections;
import com.xr45labs.uworkers.Util.DataInterface;
import com.xr45labs.uworkers.Util.RetrofitConnection;
import com.xr45labs.uworkers.principal_alumnos;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link fr_perfil_alumno.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link fr_perfil_alumno#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fr_perfil_alumno extends Fragment implements View.OnClickListener {
    principal_alumnos pa = new principal_alumnos();
    Button btn_perfil_a_config;
    int idusuario,no_control;
    String nombre,carrera,email,objetivos,conocimientos,experiencia_laboral;
    TextView tv_carrera,tv_email,tv_objetivos,tv_conocimientos,tv_experiencia,tv_nombre_nav,tv_nocontrol_nav;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public fr_perfil_alumno() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fr_perfil_alumno.
     */
    // TODO: Rename and change types and number of parameters
    public static fr_perfil_alumno newInstance(String param1, String param2) {
        fr_perfil_alumno fragment = new fr_perfil_alumno();
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
        View rootView =  inflater.inflate(R.layout.fragment_fr_perfil_alumno, container, false);

        btn_perfil_a_config = (Button) rootView.findViewById(R.id.btn_perfil_a_conf);
        btn_perfil_a_config.setOnClickListener(this);
        tv_nombre_nav = (TextView) rootView.findViewById(R.id.tv_nombre_nav);
        tv_nocontrol_nav = (TextView) rootView.findViewById(R.id.tv_nocontrol_nav);
        tv_carrera = (TextView) rootView.findViewById(R.id.tv_carrera);
        tv_email = (TextView) rootView.findViewById(R.id.tv_email);
        tv_objetivos = (TextView) rootView.findViewById(R.id.tv_objetivos);
        tv_conocimientos = (TextView) rootView.findViewById(R.id.tv_conocimientos);
        tv_experiencia = (TextView) rootView.findViewById(R.id.tv_experiencia);


        datos_perfil();
        

        return rootView;
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

            /*Toast toast = Toast.makeText(getContext(), "El usuario o contrasena son incorrectos...", Toast.LENGTH_SHORT);
            toast.show();*/


            Fragment fragment = new fr_perfil_alumno_config();
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

    public void datos_perfil(){
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("data_session",Context.MODE_PRIVATE);

        nombre = sharedPreferences.getString("nombre",null);
        no_control = sharedPreferences.getInt("no_control",0);
        carrera = sharedPreferences.getString("carrera",null);
        email = sharedPreferences.getString("correo",null);
        objetivos =sharedPreferences.getString("objetivos",null);
        conocimientos = sharedPreferences.getString("conocimientos",null);
        experiencia_laboral = sharedPreferences.getString("experiencia_laboral",null);

        tv_nombre_nav.setText(nombre);
        tv_nocontrol_nav.setText(String.valueOf(no_control));
        tv_carrera.setText(carrera);
        tv_email.setText(email);
        tv_objetivos.setText(objetivos);
        tv_conocimientos.setText(conocimientos);
        tv_experiencia.setText(experiencia_laboral);


    }


}
