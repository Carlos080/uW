package com.xr45labs.uworkers.Modelo;

/**
 * Created by xr45 on 1/04/17.
 */

public class alumnos {
    int idusuario;
    int no_control;
    String nombre;
    String telefono;
    String objetivos;
    String conocimientos;
    String experiencia_laboral;
    String curriculum;
    int CARRERAS_idcarrera;
    int USUARIOS_idusuario;

    public int getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(int idusuario) {
        this.idusuario = idusuario;
    }

    public int getNo_control() {
        return no_control;
    }

    public void setNo_control(int no_control) {
        this.no_control = no_control;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getObjetivos() {
        return objetivos;
    }

    public void setObjetivos(String objetivos) {
        this.objetivos = objetivos;
    }

    public String getConocimientos() {
        return conocimientos;
    }

    public void setConocimientos(String conocimientos) {
        this.conocimientos = conocimientos;
    }

    public String getExperiencia_laboral() {
        return experiencia_laboral;
    }

    public void setExperiencia_laboral(String experiencia_laboral) {
        this.experiencia_laboral = experiencia_laboral;
    }

    public String getCurriculum() {
        return curriculum;
    }

    public void setCurriculum(String curriculum) {
        this.curriculum = curriculum;
    }

    public int getCARRERAS_idcarrera() {
        return CARRERAS_idcarrera;
    }

    public void setCARRERAS_idcarrera(int CARRERAS_idcarrera) {
        this.CARRERAS_idcarrera = CARRERAS_idcarrera;
    }

    public int getUSUARIOS_idusuario() {
        return USUARIOS_idusuario;
    }

    public void setUSUARIOS_idusuario(int USUARIOS_idusuario) {
        this.USUARIOS_idusuario = USUARIOS_idusuario;
    }
}
