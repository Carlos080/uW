package com.xr45labs.uworkers.Modelo;

/**
 * Created by xr45 on 4/05/17.
 */

public class vacante {
    boolean status;
    String message;
    int idvacante;
    String nombre;
    String descripcion;
    int sueldo;
    String turno;
    String horario;
    String fecha_publicacion;
    int EMPRESAS_idempresa;

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

    public int getIdvacante() {
        return idvacante;
    }

    public void setIdvacante(int idvacante) {
        this.idvacante = idvacante;
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

    public int getSueldo() {
        return sueldo;
    }

    public void setSueldo(int sueldo) {
        this.sueldo = sueldo;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getFecha_publicacion() {
        return fecha_publicacion;
    }

    public void setFecha_publicacion(String fecha_publicacion) {
        this.fecha_publicacion = fecha_publicacion;
    }

    public int getEMPRESAS_idempresa() {
        return EMPRESAS_idempresa;
    }

    public void setEMPRESAS_idempresa(int EMPRESAS_idempresa) {
        this.EMPRESAS_idempresa = EMPRESAS_idempresa;
    }
}
