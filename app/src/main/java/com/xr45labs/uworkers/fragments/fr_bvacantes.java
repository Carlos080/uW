package com.xr45labs.uworkers.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.xr45labs.uworkers.Modelo.lista_vacantes;
import com.xr45labs.uworkers.Modelo.vacante;
import com.xr45labs.uworkers.R;
import com.xr45labs.uworkers.Util.Connections;
import com.xr45labs.uworkers.Util.DataInterface;
import com.xr45labs.uworkers.Util.RetrofitConnection;
import com.xr45labs.uworkers.adaptadores_recyclerview.vacantes_adapter;
import com.xr45labs.uworkers.fragments.alumnos.fr_alumno_vacante;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link fr_bvacantes.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link fr_bvacantes#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fr_bvacantes extends Fragment{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    int tipo,idempresa;
    //ListView listView;
    RecyclerView recyclerView;
    vacantes_adapter adapter;
    List<vacante> list = new ArrayList();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public fr_bvacantes() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fr_bvacantes.
     */
    // TODO: Rename and change types and number of parameters
    public static fr_bvacantes newInstance(String param1, String param2) {
        fr_bvacantes fragment = new fr_bvacantes();
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
        View rootview = inflater.inflate(R.layout.fragment_fr_bvacantes, container, false);
        /*listView = (ListView) rootview.findViewById(R.id.lv_vacantes);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });*/
        recyclerView = (RecyclerView) rootview.findViewById(R.id.rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);


        tipo_usuario();


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

    public void tipo_usuario(){
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("data_session",Context.MODE_PRIVATE);
        tipo = sharedPreferences.getInt("tipo",0);

        switch(tipo){
            case 1:
                lista_vacantes_alumnos();
                break;
            case 2:
                idempresa = sharedPreferences.getInt("idempresa",0);
                lista_vacantes_empresa(idempresa);
                break;
            case 3:
                break;
            default:
                Toast.makeText(getContext(), "Error al cargar...", Toast.LENGTH_SHORT).show();
                break;
        }

    }

    public void lista_vacantes_empresa(int idempresa){
        RetrofitConnection retrofitConnection = new RetrofitConnection();
        DataInterface dataInterface = retrofitConnection.connectRetrofit(Connections.API_URL);
        Call<lista_vacantes> service = dataInterface.lista_vacantes_empresa(idempresa);
        service.enqueue(new Callback<lista_vacantes>() {
            @Override
            public void onResponse(Call<lista_vacantes> call, Response<lista_vacantes> response) {
                if(response.isSuccessful()){
                    lista_vacantes lv = response.body();
                    if(lv.isStatus() == true){
                        //Toast.makeText(getContext(), "Se realizo consulta con exito", Toast.LENGTH_SHORT).show();
                        //llenar_lv(lv.getVacantes());
                        //List<vacante> vacantes
                        //////////////////////////////////////////
                        list = lv.getVacantes();
                        adapter = new vacantes_adapter(getContext(),list);

                        recyclerView.setAdapter(adapter);

                        //metodo_adaptador(list);
                    }else{
                        Toast.makeText(getContext(), "Error...", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getContext(), "Error...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<lista_vacantes> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void lista_vacantes_alumnos(){
        RetrofitConnection retrofitConnection = new RetrofitConnection();
        DataInterface dataInterface = retrofitConnection.connectRetrofit(Connections.API_URL);
        Call<lista_vacantes> service = dataInterface.lista_vacantes_alumno();
        service.enqueue(new Callback<lista_vacantes>() {
            @Override
            public void onResponse(Call<lista_vacantes> call, Response<lista_vacantes> response) {
                if(response.isSuccessful()){
                    lista_vacantes lv = response.body();
                    if(lv.isStatus()==true){
                        list = lv.getVacantes();
                        adapter = new vacantes_adapter(getContext(),list);
                        recyclerView.setAdapter(adapter);
                    }else{
                        Toast.makeText(getContext(), lv.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getContext(), "Error al cargar los datos...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<lista_vacantes> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    /*public void llenar_lv(ArrayList<vacante> vacates){
        List<String> list = new ArrayList<String>();
        for(vacante v : vacates){
            list.add(v.getNombre());
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(),R.layout.support_simple_spinner_dropdown_item,list);
        listView.setAdapter(arrayAdapter);
    }*/
    /*
    public void metodo_adaptador(List<vacante> list){
        vacantes_adapter adapter = new vacantes_adapter(getContext(),list);

    }*/


    public void switch_fragment(Context context){
        Fragment fragment = new fr_alumno_vacante();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_principal_alumnos,fragment,null);
        fragmentTransaction.commit();
    }
}
