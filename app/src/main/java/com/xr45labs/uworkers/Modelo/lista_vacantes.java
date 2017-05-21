package com.xr45labs.uworkers.Modelo;

import java.util.ArrayList;

/**
 * Created by xr45 on 4/05/17.
 */

public class lista_vacantes {
    boolean status;
    String message;
    ArrayList<vacante> vacantes;

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

    public ArrayList<vacante> getVacantes() {
        return vacantes;
    }

    public void setVacantes(ArrayList<vacante> vacantes) {
        this.vacantes = vacantes;
    }
}
