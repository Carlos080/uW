package com.xr45labs.uworkers.adaptadores_recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xr45labs.uworkers.Modelo.vacante;
import com.xr45labs.uworkers.R;

import java.util.List;

/**
 * Created by xr45 on 15/05/17.
 */

public class vacantes_adapter extends RecyclerView.Adapter<vacantes_adapter.ViewHolder> {
    Context context;
    List<vacante> list;
    public vacantes_adapter(Context context, List<vacante> list){
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item_layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vacante,parent,false);
        ViewHolder viewHolder = new ViewHolder(item_layout);
        item_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tv_nombre_vacante.setText(list.get(position).getNombre());
        holder.tv_sueldo.setText(String.valueOf(list.get(position).getSueldo()));
        holder.tv_fecha.setText(list.get(position).getFecha_publicacion());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_nombre_vacante,tv_sueldo,tv_fecha;
        public ViewHolder(View item_layout) {
            super(item_layout);
            tv_nombre_vacante = (TextView) item_layout.findViewById(R.id.tv_nombre_vacante);
            tv_sueldo = (TextView) item_layout.findViewById(R.id.tv_sueldo);
            tv_fecha = (TextView) item_layout.findViewById(R.id.tv_fecha);
        }
    }
}
