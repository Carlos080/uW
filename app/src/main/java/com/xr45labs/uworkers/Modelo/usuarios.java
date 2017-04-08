package com.xr45labs.uworkers.Modelo;

/**
 * Created by xr45 on 1/04/17.
 */

public class usuarios {
    int idusuario;
    String correo;
    String contrasena;
    String foto_perfil;
    String foto_fondo;
    boolean verificacion;

    public int getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(int idusuario) {
        this.idusuario = idusuario;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getFoto_perfil() {
        return foto_perfil;
    }

    public void setFoto_perfil(String foto_perfil) {
        this.foto_perfil = foto_perfil;
    }

    public String getFoto_fondo() {
        return foto_fondo;
    }

    public void setFoto_fondo(String foto_fondo) {
        this.foto_fondo = foto_fondo;
    }

    public boolean isVerificacion() {
        return verificacion;
    }

    public void setVerificacion(boolean verificacion) {
        this.verificacion = verificacion;
    }
}
