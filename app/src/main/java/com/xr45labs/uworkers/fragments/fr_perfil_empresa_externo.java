package com.xr45labs.uworkers.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.telecom.Connection;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.xr45labs.uworkers.Modelo.empresa;
import com.xr45labs.uworkers.Modelo.empresa_usuario;
import com.xr45labs.uworkers.R;
import com.xr45labs.uworkers.Util.Connections;
import com.xr45labs.uworkers.Util.DataInterface;
import com.xr45labs.uworkers.Util.RetrofitConnection;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link fr_perfil_empresa_externo.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link fr_perfil_empresa_externo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fr_perfil_empresa_externo extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    int idempresa,tipo;
    TextView tv_giro, tv_descripcion, tv_telefono,tv_nombre_nav,tv_secundario_nav;
    Button btn_ver_vacantes_empresa;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public fr_perfil_empresa_externo() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fr_perfil_empresa_externo.
     */
    // TODO: Rename and change types and number of parameters
    public static fr_perfil_empresa_externo newInstance(String param1, String param2) {
        fr_perfil_empresa_externo fragment = new fr_perfil_empresa_externo();
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
        View rootview =  inflater.inflate(R.layout.fragment_fr_perfil_empresa_externo, container, false);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("data_session",Context.MODE_PRIVATE);
        tipo = sharedPreferences.getInt("tipo",0);
        idempresa = getArguments().getInt("idempresa");
        //Toast.makeText(getContext(), String.valueOf(idempresa), Toast.LENGTH_SHORT).show();
        tv_nombre_nav = (TextView) rootview.findViewById(R.id.tv_nombre_nav);
        tv_secundario_nav = (TextView) rootview.findViewById(R.id.tv_secundario_nav);
        tv_giro = (TextView) rootview.findViewById(R.id.tv_giro);
        tv_descripcion = (TextView) rootview.findViewById(R.id.tv_descripcion);
        tv_telefono = (TextView) rootview.findViewById(R.id.tv_phone);
        btn_ver_vacantes_empresa = (Button) rootview.findViewById(R.id.btn_ver_vacantes_empresa);
        btn_ver_vacantes_empresa.setOnClickListener(this);

        datos_empresa();
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
        Bundle bundle = new Bundle();
        bundle.putInt("idempresa",idempresa);

        Fragment fragment = new fr_bvacantes();
        fragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_principal_alumnos,fragment);
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

    public void datos_empresa(){
        RetrofitConnection retrofitConnection = new RetrofitConnection();
        DataInterface dataInterface = retrofitConnection.connectRetrofit(Connections.API_URL);
        Call<empresa_usuario> service = dataInterface.datos_empresa(idempresa);
        service.enqueue(new Callback<empresa_usuario>() {
            @Override
            public void onResponse(Call<empresa_usuario> call, Response<empresa_usuario> response) {
                if(response.isSuccessful()){
                    empresa_usuario eu = response.body();
                    if(eu.isStatus()==true){
                        //Toast.makeText(getContext(), eu.getNombre().toString(), Toast.LENGTH_SHORT).show();
                        tv_nombre_nav.setText(eu.getNombre());
                        tv_secundario_nav.setText(eu.getCorreo());
                        tv_giro.setText(eu.getGiro());
                        tv_descripcion.setText(eu.getDescripcion());
                        tv_telefono.setText(eu.getTelefono());
                    }else{
                        Toast.makeText(getContext(),"errorooooooo", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getContext(), "Error...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<empresa_usuario> call, Throwable t) {
                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}
