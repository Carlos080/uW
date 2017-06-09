package com.xr45labs.uworkers.fragments.empresas;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.xr45labs.uworkers.Modelo.GeneralPOJO;
import com.xr45labs.uworkers.R;
import com.xr45labs.uworkers.Util.Connections;
import com.xr45labs.uworkers.Util.DataInterface;
import com.xr45labs.uworkers.Util.RetrofitConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link fr_perfil_empresa_config.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link fr_perfil_empresa_config#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fr_perfil_empresa_config extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    int idusuario;
    EditText et_nombre_empresa,et_giro,et_descripcion,et_phone,et_contrasena,et_contrasena_confir;
    String nombre,giro,descripcion,phone;
    Button btn_guardar;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public fr_perfil_empresa_config() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fr_perfil_empresa_config.
     */
    // TODO: Rename and change types and number of parameters
    public static fr_perfil_empresa_config newInstance(String param1, String param2) {
        fr_perfil_empresa_config fragment = new fr_perfil_empresa_config();
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
        View rootview = inflater.inflate(R.layout.fragment_fr_perfil_empresa_config, container, false);
        et_nombre_empresa = (EditText) rootview.findViewById(R.id.et_nombre_empresa);
        et_giro = (EditText) rootview.findViewById(R.id.et_giro);
        et_descripcion = (EditText) rootview.findViewById(R.id.et_descripcion);
        et_phone = (EditText) rootview.findViewById(R.id.et_phone);
        btn_guardar = (Button) rootview.findViewById(R.id.btn_guardar);
        btn_guardar.setOnClickListener(this);
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

    public void datos_empresa(){
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("data_session",Context.MODE_PRIVATE);
        idusuario = sharedPreferences.getInt("idusuario",0);
        et_nombre_empresa.setText(sharedPreferences.getString("nombre",""));
        et_giro.setText(sharedPreferences.getString("giro",""));
        et_descripcion.setText(sharedPreferences.getString("descripcion",""));
        et_phone.setText(sharedPreferences.getString("telefono",""));

    }

    public void mod_empresa(){
        nombre = et_nombre_empresa.getText().toString();
        descripcion = et_descripcion.getText().toString();
        phone = et_phone.getText().toString();

        RetrofitConnection retrofitConnection = new RetrofitConnection();
        DataInterface dataInterface = retrofitConnection.connectRetrofit(Connections.API_URL);
        Call<GeneralPOJO> servicio = dataInterface.mod_empresa(idusuario,nombre,descripcion,phone);
        servicio.enqueue(new Callback<GeneralPOJO>() {
            @Override
            public void onResponse(Call<GeneralPOJO> call, Response<GeneralPOJO> response) {
                if(response.isSuccessful()){
                    GeneralPOJO generalPOJO = response.body();
                    if(generalPOJO.isStatus()==true){
                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("data_session",Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("nombre",nombre);
                        editor.putString("descripcion",descripcion);
                        editor.putString("telefono",phone);
                        editor.commit();
                        Toast.makeText(getContext(), generalPOJO.getMessage(), Toast.LENGTH_SHORT).show();
                    }else {
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
                        mod_empresa();
                        dialog.cancel();
                    }
                });

        android.app.AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}
