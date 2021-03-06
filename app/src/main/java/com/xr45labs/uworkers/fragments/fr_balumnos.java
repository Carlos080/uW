package com.xr45labs.uworkers.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xr45labs.uworkers.Modelo.alumno_item;
import com.xr45labs.uworkers.Modelo.lista_alumnos;
import com.xr45labs.uworkers.R;
import com.xr45labs.uworkers.Util.Connections;
import com.xr45labs.uworkers.Util.DataInterface;
import com.xr45labs.uworkers.Util.RetrofitConnection;
import com.xr45labs.uworkers.adaptadores_recyclerview.alumnos_adapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link fr_balumnos.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link fr_balumnos#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fr_balumnos extends Fragment {
    List<alumno_item> list = new ArrayList();
    RecyclerView recyclerView_balumnos;
    alumnos_adapter adapter;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public fr_balumnos() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fr_balumnos.
     */
    // TODO: Rename and change types and number of parameters
    public static fr_balumnos newInstance(String param1, String param2) {
        fr_balumnos fragment = new fr_balumnos();
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
        View rootview = inflater.inflate(R.layout.fragment_fr_balumnos, container, false);
        recyclerView_balumnos = (RecyclerView) rootview.findViewById(R.id.recyclerView_balumnos);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView_balumnos.setLayoutManager(linearLayoutManager);

        lista_alumnos();
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

    public void lista_alumnos(){
        RetrofitConnection retrofitConnection = new RetrofitConnection();
        DataInterface dataInterface = retrofitConnection.connectRetrofit(Connections.API_URL);
        Call<lista_alumnos> service = dataInterface.lista_alumnos();
        service.enqueue(new Callback<lista_alumnos>() {
            @Override
            public void onResponse(Call<lista_alumnos> call, Response<lista_alumnos> response) {
                if(response.isSuccessful()){
                    lista_alumnos la = response.body();
                    if(la.isStatus()==true){
                        list = la.getAlumnos();
                        adapter = new alumnos_adapter(getContext(),list);
                        recyclerView_balumnos.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<lista_alumnos> call, Throwable t) {

            }
        });
    }
}
