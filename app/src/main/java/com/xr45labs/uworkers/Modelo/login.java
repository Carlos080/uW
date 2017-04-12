package com.xr45labs.uworkers.Modelo;

/**
 * Created by xr45 on 7/04/17.
 */

public class login {
    private boolean status;
    private String message;
    private String foto_perfil;
    private String foto_fondo;

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

    private int idusuario;
    private int tipo;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(int idusuario) {
        this.idusuario = idusuario;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
}
