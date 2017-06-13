package com.xr45labs.uworkers.fragments.alumnos;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.xr45labs.uworkers.Modelo.GeneralPOJO;
import com.xr45labs.uworkers.Modelo.alumno;
import com.xr45labs.uworkers.Modelo.foto_perfil_descarga;
import com.xr45labs.uworkers.R;
import com.xr45labs.uworkers.Util.Connections;
import com.xr45labs.uworkers.Util.DataInterface;
import com.xr45labs.uworkers.Util.RetrofitConnection;
import com.xr45labs.uworkers.principal_alumnos;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;


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
    Uri imageUri;
    private static final int PICK_IMAGE = 100;
    String nombre,carrera,email,objetivos,conocimientos,experiencia_laboral;
    TextView tv_carrera,tv_email,tv_objetivos,tv_conocimientos,tv_experiencia,tv_nombre_nav,tv_secundario_nav;
    de.hdodenhof.circleimageview.CircleImageView civ;
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
        civ = (de.hdodenhof.circleimageview.CircleImageView) rootView.findViewById(R.id.profile_image);
        civ.setOnClickListener(this);
        btn_perfil_a_config = (Button) rootView.findViewById(R.id.btn_perfil_a_conf);
        btn_perfil_a_config.setOnClickListener(this);
        tv_nombre_nav = (TextView) rootView.findViewById(R.id.tv_nombre_nav);
        tv_secundario_nav = (TextView) rootView.findViewById(R.id.tv_secundario_nav);
        tv_carrera = (TextView) rootView.findViewById(R.id.tv_carrera);
        tv_email = (TextView) rootView.findViewById(R.id.tv_email);
        tv_objetivos = (TextView) rootView.findViewById(R.id.tv_objetivos);
        tv_conocimientos = (TextView) rootView.findViewById(R.id.tv_conocimientos);
        tv_experiencia = (TextView) rootView.findViewById(R.id.tv_experiencia);

        descarga_foto_perfil();
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




        switch(v.getId()){
            case R.id.btn_perfil_a_conf:
                Fragment fragment = new fr_perfil_alumno_config();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.content_principal_alumnos,fragment,null);
                fragmentTransaction.commit();
                break;

            case R.id.profile_image:
                openGallery();
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

    public void datos_perfil(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("data_session",Context.MODE_PRIVATE);
        idusuario = sharedPreferences.getInt("idusuario",0);

        RetrofitConnection retrofitConnection = new RetrofitConnection();
        DataInterface dataInterface = retrofitConnection.connectRetrofit(Connections.API_URL);
        Call<alumno> service = dataInterface.perfil_alumno(idusuario);
        service.enqueue(new Callback<alumno>() {
            @Override
            public void onResponse(Call<alumno> call, Response<alumno> response) {
                if(response.isSuccessful()){
                    alumno a = response.body();
                    if(a.isStatus()==true){
                        nombre = a.getNombre();
                        no_control = a.getNo_control();
                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("data_session",Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("nombre",a.getNombre());
                        editor.putInt("no_control",a.getNo_control());
                        editor.putString("telefono",a.getTelefono());
                        editor.putString("carrera",a.getCarrera());
                        editor.putString("objetivos",a.getObjetivos());
                        editor.putString("conocimientos",a.getConocimientos());
                        editor.putString("experiencia_laboral",a.getExperiencia_laboral());
                        editor.commit();

                        datos_alumno();
                    }else {
                        Toast.makeText(getContext(), a.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(getContext(), "Error al cargar...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<alumno> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void datos_alumno(){
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("data_session",Context.MODE_PRIVATE);

        nombre = sharedPreferences.getString("nombre",null);
        no_control = sharedPreferences.getInt("no_control",0);
        carrera = sharedPreferences.getString("carrera",null);
        email = sharedPreferences.getString("correo",null);
        objetivos =sharedPreferences.getString("objetivos",null);
        conocimientos = sharedPreferences.getString("conocimientos",null);
        experiencia_laboral = sharedPreferences.getString("experiencia_laboral",null);

        tv_nombre_nav.setText(nombre);
        tv_secundario_nav.setText(String.valueOf(no_control));
        tv_carrera.setText(carrera);
        tv_email.setText(email);
        tv_objetivos.setText(objetivos);
        tv_conocimientos.setText(conocimientos);
        tv_experiencia.setText(experiencia_laboral);


    }

    //Modulo de subida de imagen///////////////////////////////////////////////////////////////////////
    private void openGallery(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageUri = data.getData();
            civ.setImageURI(imageUri);
            //String path = imageUri.getPath();
            String ruta = getRealPathFromURI(getContext(),imageUri);
            // Log.e("ruta",ruta);
            File file = new File(ruta);

            SubFoto_perfil(file);
        }
    }



    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri,proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }


    public void SubFoto_perfil(File file){
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("foto", file.getName(), requestFile);

        RetrofitConnection retrofitConnection = new RetrofitConnection();
        DataInterface dataInterface = retrofitConnection.connectRetrofit(Connections.API_URL);
        Call<GeneralPOJO> service = dataInterface.SubFoto_perfil(idusuario,body);
        service.enqueue(new Callback<GeneralPOJO>() {
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
                Log.e("mesaje",t.getMessage());
            }
        });
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void descarga_foto_perfil() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("data_session",Context.MODE_PRIVATE);
        idusuario = sharedPreferences.getInt("idusuario",0);
        RetrofitConnection retrofitConnection = new RetrofitConnection();
        DataInterface dataInterface = retrofitConnection.connectRetrofit(Connections.API_URL);
        Call<foto_perfil_descarga> service = dataInterface.foto_perfil_descarga(idusuario);
        service.enqueue(new Callback<foto_perfil_descarga>() {
            @Override
            public void onResponse(Call<foto_perfil_descarga> call, Response<foto_perfil_descarga> response) {
                if(response.isSuccessful()){
                    foto_perfil_descarga fpd = response.body();
                    if(fpd.isStatus()==true){
                        //Toast.makeText(getContext(), fpd.getFoto_perfil(), Toast.LENGTH_SHORT).show();
                        Glide.with(getContext()).load(fpd.getFoto_perfil()).into(civ);
                    }
                    Toast.makeText(getContext(), String.valueOf(idusuario), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<foto_perfil_descarga> call, Throwable t) {

            }
        });
    }
}
