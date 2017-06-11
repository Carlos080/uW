package com.xr45labs.uworkers.Util;

import com.xr45labs.uworkers.Modelo.GeneralPOJO;
import com.xr45labs.uworkers.Modelo.alumno;
import com.xr45labs.uworkers.Modelo.empresa;
import com.xr45labs.uworkers.Modelo.empresa_usuario;
import com.xr45labs.uworkers.Modelo.lista_alumnos;
import com.xr45labs.uworkers.Modelo.lista_empresas;
import com.xr45labs.uworkers.Modelo.lista_vacantes;
import com.xr45labs.uworkers.Modelo.login;
import com.xr45labs.uworkers.Modelo.vacante;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

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
    Call<alumno> perfil_alumno(@Field("idusuario") int idusuario);

    @FormUrlEncoded
    @POST("Api/perfil_empresa.php")
    Call<empresa> perfil_empresa(@Field("idusuario") int idusuario);

    @FormUrlEncoded
    @POST("Api/mod_alumno.php")
    Call<GeneralPOJO> mod_alumno(@Field("idusuario") int idusuario, @Field("nombre") String nombre, @Field("telefono") String telefono, @Field("objetivos") String objetivos, @Field("conocimientos") String conocimientos, @Field("experiencia_laboral") String experiencia_laboral, @Field("curriculum") String curriculum);

    @FormUrlEncoded
    @POST("Api/mod_empresa.php")
    Call<GeneralPOJO> mod_empresa(@Field("idusuario") int idusuario, @Field("nombre") String nombre, @Field("descripcion") String descripcion, @Field("telefono") String telefono);

    @FormUrlEncoded
    @POST("Api/reg_vacante.php")
    Call<GeneralPOJO> reg_vacante(@Field("nombre") String nombre, @Field("descripcion") String descripcion, @Field("sueldo") int sueldo, @Field("turno") String turno, @Field("horario") String horrario, @Field("fecha_publicacion") String fecha_publicacion, @Field("EMPRESAS_idempresa") int idempresa);

    @FormUrlEncoded
    @POST("Api/vacantes_empresa.php")
    Call<lista_vacantes> lista_vacantes_empresa(@Field("idempresa") int idempresa);/**/

    @GET("Api/vacantes_alumno.php")
    Call<lista_vacantes> lista_vacantes_alumno();

    @GET("Api/lista_empresas.php")
    Call<lista_empresas> lista_empresas();

    @GET("Api/lista_alumnos.php")
    Call<lista_alumnos> lista_alumnos();

    @FormUrlEncoded
    @POST("Api/datos_vacante.php")
    Call<vacante> datos_vacante(@Field("idvacante") int idvacante);

    @FormUrlEncoded
    @POST("Api/datos_empresa.php")
    Call<empresa_usuario> datos_empresa(@Field("idempresa") int idempresa);

    @FormUrlEncoded
    @POST("Api/mod_vacante.php")
    Call<GeneralPOJO> mod_vacante(@Field("nombre") String nombre, @Field("descripcion") String descripcion, @Field("turno") String turno, @Field("horario")String horario, @Field("sueldo") int sueldo, @Field("fecha_publicacion") String fecha_publicacion, @Field("idvacante") int idvacante);

    @FormUrlEncoded
    @POST("Api/eliminar_vacante.php")
    Call<GeneralPOJO> eliminar_vacante(@Field("idvacante") int idvacante);

    @Multipart
    @POST("Api/upload.php")
    Call<GeneralPOJO> SubFoto_perfil(@Part MultipartBody.Part file);
}
