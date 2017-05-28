package com.xr45labs.uworkers.adaptadores_recyclerview;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.xr45labs.uworkers.Modelo.vacante;
import com.xr45labs.uworkers.R;
import com.xr45labs.uworkers.fragments.alumnos.fr_alumno_vacante;
import com.xr45labs.uworkers.fragments.alumnos.fr_perfil_alumno_config;
import com.xr45labs.uworkers.fragments.fr_bvacantes;

import java.util.List;

/**
 * Created by xr45 on 15/05/17.
 */

public class vacantes_adapter extends RecyclerView.Adapter<vacantes_adapter.ViewHolder> {
    Context context;
    List<vacante> list;
    ViewHolder vh;
    int position = 0;

    //private View.OnClickListener listener;
    public vacantes_adapter(Context context, List<vacante> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item_layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vacante, parent, false);


        final ViewHolder viewHolder = new ViewHolder(item_layout);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tv_nombre_vacante.setText(list.get(position).getNombre());
        holder.tv_sueldo.setText(String.valueOf(list.get(position).getSueldo()));
        holder.tv_fecha.setText(list.get(position).getFecha_publicacion());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    /*
    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if(listener != null){
            listener.onClick(v);
        }
    }*/


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_nombre_vacante, tv_sueldo, tv_fecha;

        public ViewHolder(final View item_layout) {
            super(item_layout);
            tv_nombre_vacante = (TextView) item_layout.findViewById(R.id.tv_nombre_vacante);
            tv_sueldo = (TextView) item_layout.findViewById(R.id.tv_sueldo);
            tv_fecha = (TextView) item_layout.findViewById(R.id.tv_fecha);

            item_layout.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int idvacante = list.get(getAdapterPosition()).getIdvacante();
            //Toast.makeText(context, String.valueOf(), Toast.LENGTH_SHORT).show();
            AppCompatActivity activity = (AppCompatActivity) v.getContext();
            Fragment fragment = new fr_alumno_vacante();
            Bundle bundle = new Bundle();
            bundle.putInt("idvacante",idvacante);
            fragment.setArguments(bundle);

            activity.getSupportFragmentManager().beginTransaction().replace(R.id.content_principal_alumnos,fragment).commit();
        }
    }
}
