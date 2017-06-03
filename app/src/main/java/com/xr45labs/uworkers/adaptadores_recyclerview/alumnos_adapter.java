package com.xr45labs.uworkers.adaptadores_recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xr45labs.uworkers.Modelo.alumno;
import com.xr45labs.uworkers.Modelo.alumno_usuario;
import com.xr45labs.uworkers.R;

import java.util.List;

/**
 * Created by xr45 on 1/06/17.
 */

public class alumnos_adapter extends RecyclerView.Adapter<alumnos_adapter.ViewHolder> {
    Context context;
    List<alumno> list;

    public alumnos_adapter(Context context, List<alumno> list) {
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
        holder.tv_nombre.setText(list.get(position).getNombre());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_nombre;
        public ViewHolder(View itemView) {
            super(itemView);

            tv_nombre = (TextView) itemView.findViewById(R.id.tv_nombre_alumno_item);
            //tv_carrera = (TextView) itemView.findViewById(R.id.tv_carrera);
        }
    }
}
