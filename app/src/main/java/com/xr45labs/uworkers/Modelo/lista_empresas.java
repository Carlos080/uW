package com.xr45labs.uworkers.Modelo;

import java.util.ArrayList;

/**
 * Created by xr45 on 20/05/17.
 */

public class lista_empresas {
    boolean status;
    String message;
    ArrayList<empresa> empresas;

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

    public ArrayList<empresa> getEmpresas() {
        return empresas;
    }

    public void setEmpresas(ArrayList<empresa> empresas) {
        this.empresas = empresas;
    }
}
