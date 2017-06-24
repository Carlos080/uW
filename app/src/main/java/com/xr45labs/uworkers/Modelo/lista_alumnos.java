package com.xr45labs.uworkers.Modelo;

import java.util.ArrayList;

/**
 * Created by xr45 on 1/06/17.
 */

public class lista_alumnos {
    boolean status;
    String message;
    ArrayList<alumno_item> alumnos;

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

    public ArrayList<alumno_item> getAlumnos() {
        return alumnos;
    }

    public void setAlumnos(ArrayList<alumno_item> alumnos) {
        this.alumnos = alumnos;
    }
}
