package com.xr45labs.uworkers.Util;

import com.xr45labs.uworkers.Modelo.GeneralPOJO;
import com.xr45labs.uworkers.Modelo.alumnos;
import com.xr45labs.uworkers.Modelo.empresas;
import com.xr45labs.uworkers.Modelo.login;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.PUT;

/**
 * Created by xr45 on 2/04/17.
 */

public interface DataInterface {
    @FormUrlEncoded
    @POST("Api/login_uworkers.php")
    Call<login> login_usuarios(@Field("correo") String correo, @Field("contrasena") String contrasena);


    @FormUrlEncoded
    @POST("Api/reg_alumnos.php")
    Call<GeneralPOJO> reg_alumnos(@Field("no_control") int no_control, @Field("nombre") String nombre, @Field("correo") String correo, @Field("contrasena") String contrasena);

    @FormUrlEncoded
    @POST("Api/reg_empresas.php")
    Call<GeneralPOJO> reg_empresas(@Field("nombre") String nombre, @Field("correo") String correo, @Field("contrasena") String contrasena);

    @FormUrlEncoded
    @POST("Api/perfil_alumno.php")
    Call<alumnos> perfil_alumno(@Field("idusuario") int idusuario);

    @FormUrlEncoded
    @POST("Api/perfil_empresa.php")
    Call<empresas> perfil_empresa(@Field("idusuario") int idusuario);

    @FormUrlEncoded
    @POST("Api/mod_alumno.php")
    Call<GeneralPOJO> mod_alumno(@Field("idusuario") int idusuario, @Field("nombre") String nombre, @Field("telefono") String telefono, @Field("objetivos") String objetivos, @Field("conocimientos") String conocimientos, @Field("experiencia_laboral") String experiencia_laboral, @Field("curriculum") String curriculum);

    @FormUrlEncoded
    @POST("Api/mod_empresa.php")
    Call<GeneralPOJO> mod_empresa(@Field("idusuario") int idusuario, @Field("nombre") String nombre, @Field("descripcion") String descripcion, @Field("telefono") String telefono);

    @FormUrlEncoded
    @POST("Api/reg_vacante.php")
    Call<GeneralPOJO> reg_vacante(@Field("nombre") String nombre, @Field("descripcion") String descripcion, @Field("sueldo") int sueldo, @Field("turno") String turno, @Field("horario") String horrario, @Field("fecha_publicacion") String fecha_publicacion, @Field("EMPRESAS_idempresa") int idempresa);
}
