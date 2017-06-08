package com.xr45labs.uworkers.fragments.empresas;

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
import android.widget.TextView;
import android.widget.Toast;

import com.xr45labs.uworkers.Modelo.empresa;
import com.xr45labs.uworkers.R;
import com.xr45labs.uworkers.Util.Connections;
import com.xr45labs.uworkers.Util.DataInterface;
import com.xr45labs.uworkers.Util.RetrofitConnection;
import com.xr45labs.uworkers.principal_empresa;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link fr_perfil_empresa.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link fr_perfil_empresa#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fr_perfil_empresa extends Fragment implements View.OnClickListener {
    TextView tv_nombre_nav,tv_secundario_nav,tv_giro,tv_descripcion,tv_telefono;
    String nombre,correo,giro,descripcion,telefono;
    int idempresa,idusuario;
    Button btn_perfil_emp_config;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public fr_perfil_empresa() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fr_perfil_empresa.
     */
    // TODO: Rename and change types and number of parameters
    public static fr_perfil_empresa newInstance(String param1, String param2) {
        fr_perfil_empresa fragment = new fr_perfil_empresa();
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
        View rootView =  inflater.inflate(R.layout.fragment_fr_perfil_empresa, container, false);
        btn_perfil_emp_config = (Button) rootView.findViewById(R.id.btn_perfil_emp_conf);
        btn_perfil_emp_config.setOnClickListener(this);
        tv_nombre_nav = (TextView) rootView.findViewById(R.id.tv_nombre_nav);
        tv_secundario_nav = (TextView) rootView.findViewById(R.id.tv_secundario_nav);
        tv_giro = (TextView) rootView.findViewById(R.id.tv_giro);
        tv_descripcion = (TextView) rootView.findViewById(R.id.tv_descripcion);
        tv_telefono = (TextView) rootView.findViewById(R.id.tv_phone);

        //datos_empresa();
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
        Fragment fragment = new fr_perfil_empresa_config();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_principal_empresa,fragment).commit();
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
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("data_session",Context.MODE_PRIVATE);
        idusuario = sharedPreferences.getInt("idusuario",0);
        correo = sharedPreferences.getString("correo",null);

        RetrofitConnection retrofitConnection = new RetrofitConnection();
        DataInterface dataInterface = retrofitConnection.connectRetrofit(Connections.API_URL);
        Call<empresa> service = dataInterface.perfil_empresa(idusuario);
        service.enqueue(new Callback<empresa>() {
            @Override
            public void onResponse(Call<empresa> call, Response<empresa> response) {
                if(response.isSuccessful()){
                    empresa e = response.body();
                    if(e.isStatus()==true){
                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("data_session",Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("idempresa",e.getIdempresa());
                        editor.putString("nombre",e.getNombre());
                        editor.putString("descripcion",e.getDescripcion());
                        editor.putString("telefono",e.getTelefono());
                        editor.putString("giro",e.getGiro());
                        editor.commit();

                        datos_empresa();
                    }else{
                        Toast.makeText(getContext(), "Error al obtener los datos de la empresa...", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getContext(), "Error de operacion...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<empresa> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void datos_empresa(){
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("data_session",Context.MODE_PRIVATE);
        nombre = sharedPreferences.getString("nombre",null);
        correo = sharedPreferences.getString("correo",null);
        giro = sharedPreferences.getString("giro",null);
        descripcion = sharedPreferences.getString("descripcion",null);
        telefono = sharedPreferences.getString("telefono",null);

        tv_nombre_nav.setText(nombre);
        tv_secundario_nav.setText(correo);
        tv_giro.setText(giro);
        tv_descripcion.setText(descripcion);
        tv_telefono.setText(telefono);

    }
}
