package com.xr45labs.uworkers.Util;

import com.xr45labs.uworkers.Modelo.GeneralPOJO;
import com.xr45labs.uworkers.Modelo.login;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by xr45 on 2/04/17.
 */

public interface DataInterface {
    @FormUrlEncoded
    @POST("Api/login_uworkers.php")
    Call<login> login_usuarios(@Field("correo") String correo, @Field("contrasena") String contrasena);



    @POST("Api/reg_alumnos.php")
    Call<GeneralPOJO> reg_alumnos(@Field("no_control") int no_control, @Field("nombre") String nombre, @Field("correo") String correo, @Field("contrasena") String contrasena);

    @POST("Api/reg_empresas.php")
    Call<GeneralPOJO> reg_empresas(@Field("nombre") String nombre, @Field("correo") String correo, @Field("contrasena") String contrasena);
}