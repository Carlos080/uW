package com.xr45labs.uworkers.adaptadores_recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xr45labs.uworkers.Modelo.alumno;
import com.xr45labs.uworkers.Modelo.alumno_datos;
import com.xr45labs.uworkers.R;

import java.util.List;

/**
 * Created by xr45 on 1/06/17.
 */

public class alumnos_adapter extends RecyclerView.Adapter<alumnos_adapter.ViewHolder> {

    Context context;
    List<alumno_datos> list;

    public alumnos_adapter(Context context, List<alumno_datos> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alumno,parent,false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tv_nombre_alumno_item.setText(list.get(position).getNombre());
        holder.tv_carrera_item.setText(list.get(position).getCarrera());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_nombre_alumno_item, tv_carrera_item;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_nombre_alumno_item = (TextView) itemView.findViewById(R.id.tv_nombre_alumno_item);
            tv_carrera_item = (TextView) itemView.findViewById(R.id.tv_carrera_item);
        }
    }
}
