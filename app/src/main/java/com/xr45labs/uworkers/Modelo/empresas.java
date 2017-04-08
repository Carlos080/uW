package com.xr45labs.uworkers.Modelo;

/**
 * Created by xr45 on 1/04/17.
 */

public class empresas {
    int idempresa;
    String nombre;
    String descripcion;
    String telefono;
    int USUARIOS_idusuario;
    int GIRO_idgiro;

    public int getIdempresa() {
        return idempresa;
    }

    public void setIdempresa(int idempresa) {
        this.idempresa = idempresa;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public int getUSUARIOS_idusuario() {
        return USUARIOS_idusuario;
    }

    public void setUSUARIOS_idusuario(int USUARIOS_idusuario) {
        this.USUARIOS_idusuario = USUARIOS_idusuario;
    }

    public int getGIRO_idgiro() {
        return GIRO_idgiro;
    }

    public void setGIRO_idgiro(int GIRO_idgiro) {
        this.GIRO_idgiro = GIRO_idgiro;
    }
}
