package com.xr45labs.uworkers.Modelo;

/**
 * Created by xr45 on 4/05/17.
 */

public class vacante {
    int idvacante;
    String nombre;
    String descripcion;
    int sueldo;
    String truno;
    String horario;
    String fecha_publicacion;
    int EMPRESAS_idempresa;

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

    public String getTruno() {
        return truno;
    }

    public void setTruno(String truno) {
        this.truno = truno;
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
