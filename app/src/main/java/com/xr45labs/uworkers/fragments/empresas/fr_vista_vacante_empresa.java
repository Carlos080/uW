package com.xr45labs.uworkers.fragments.empresas;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import com.xr45labs.uworkers.Modelo.GeneralPOJO;
import com.xr45labs.uworkers.Modelo.vacante;
import com.xr45labs.uworkers.R;
import com.xr45labs.uworkers.Util.Connections;
import com.xr45labs.uworkers.Util.DataInterface;
import com.xr45labs.uworkers.Util.RetrofitConnection;
import com.xr45labs.uworkers.fragments.fr_bvacantes;
import com.xr45labs.uworkers.fragments.fr_modificar_vacante;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link fr_vista_vacante_empresa.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link fr_vista_vacante_empresa#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fr_vista_vacante_empresa extends Fragment implements View.OnClickListener {
    TextView tv_nombre_vacante, tv_descripcion_vacante,tv_horario,tv_turno,tv_sueldo,tv_fecha_publicacion;
    Button btn_modificar_vacante, btn_eliminar_vacante;
    String nombre,descripcion,turno,horario,sueldo,fecha;
    int idvacante;
    Fragment fragment;
    Bundle bundle;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public fr_vista_vacante_empresa() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fr_vista_vacante_empresa.
     */
    // TODO: Rename and change types and number of parameters
    public static fr_vista_vacante_empresa newInstance(String param1, String param2) {
        fr_vista_vacante_empresa fragment = new fr_vista_vacante_empresa();
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
        View rootview =  inflater.inflate(R.layout.fragment_fr_vista_vacante_empresa, container, false);
        tv_nombre_vacante = (TextView) rootview.findViewById(R.id.tv_nombre_vacante);
        tv_descripcion_vacante =(TextView) rootview.findViewById(R.id.tv_descripcion_vacante);
        tv_horario = (TextView) rootview.findViewById(R.id.tv_horario);
        tv_turno = (TextView) rootview.findViewById(R.id.tv_turno);
        tv_sueldo = (TextView) rootview.findViewById(R.id.tv_sueldo);
        tv_fecha_publicacion = (TextView) rootview.findViewById(R.id.tv_fecha_publicacion);

        btn_modificar_vacante = (Button) rootview.findViewById(R.id.btn_modificar_vacante);
        btn_modificar_vacante.setOnClickListener(this);
        btn_eliminar_vacante = (Button) rootview.findViewById(R.id.btn_eliminar_vacante);
        btn_eliminar_vacante.setOnClickListener(this);

        idvacante = getArguments().getInt("idvacante");
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
        switch(v.getId()){
            case R.id.btn_modificar_vacante:
                fragment = new fr_modificar_vacante();
                FragmentTransaction fragmentTransaction;
                bundle = new Bundle();
                bundle.putInt("idvacante",idvacante);
                fragment.setArguments(bundle);
                fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.content_principal_empresa,fragment,null);
                fragmentTransaction.commit();

                break;

            case R.id.btn_eliminar_vacante:
                //eliminar_vacante(idvacante);
                dialog();

                break;
        }
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

    public void eliminar_vacante(int idvacante){
        RetrofitConnection retrofitConnection = new RetrofitConnection();
        DataInterface dataInterface = retrofitConnection.connectRetrofit(Connections.API_URL);
        Call<GeneralPOJO> service = dataInterface.eliminar_vacante(idvacante);
        service.enqueue(new Callback<GeneralPOJO>() {
            @Override
            public void onResponse(Call<GeneralPOJO> call, Response<GeneralPOJO> response) {
                if(response.isSuccessful()){
                    GeneralPOJO generalPOJO = response.body();
                    if(generalPOJO.isStatus()==true){
                        fragment = new fr_bvacantes();
                        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.content_principal_empresa,fragment,null);
                        fragmentTransaction.commit();
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

    public void dialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage("¿Confirma la acción seleccionada?")
                .setTitle("Confirmacion")
                .setPositiveButton("Cancelar", new DialogInterface.OnClickListener()  {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                })
                .setNegativeButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        eliminar_vacante(idvacante);
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
