package com.xr45labs.uworkers.adaptadores_recyclerview;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xr45labs.uworkers.Modelo.alumno_item;
import com.xr45labs.uworkers.R;
import com.xr45labs.uworkers.fragments.fr_perfil_alumno_externo;

import java.util.List;

/**
 * Created by xr45 on 1/06/17.
 */

public class alumnos_adapter extends RecyclerView.Adapter<alumnos_adapter.ViewHolder> {

    Context context;
    List<alumno_item> list;

    public alumnos_adapter(Context context, List<alumno_item> list) {
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
        ////
           Glide.with(context).load(list.get(position).getFoto_perfil()).into(holder.profile_image);
        ////
        holder.tv_nombre_alumno_item.setText(list.get(position).getNombre());
        holder.tv_email_item_alumno.setText(list.get(position).getCorreo());
        holder.tv_carrera_item.setText(list.get(position).getCarrera());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        de.hdodenhof.circleimageview.CircleImageView profile_image;
        TextView tv_nombre_alumno_item, tv_carrera_item, tv_email_item_alumno;
        public ViewHolder(View itemView) {
            super(itemView);
            profile_image = (de.hdodenhof.circleimageview.CircleImageView) itemView.findViewById(R.id.profile_image);
            tv_nombre_alumno_item = (TextView) itemView.findViewById(R.id.tv_nombre_alumno_item);
            tv_email_item_alumno = (TextView) itemView.findViewById(R.id.tv_email_item_alumno);
            tv_carrera_item = (TextView) itemView.findViewById(R.id.tv_carrera_item);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            int idalumno = list.get(position).getIdalumno();

            AppCompatActivity appCompatActivity = (AppCompatActivity) v.getContext();
            Fragment fragment = new fr_perfil_alumno_externo();

            Bundle bundle = new Bundle();
            bundle.putInt("idalumno",idalumno);

            fragment.setArguments(bundle);

            appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.content_principal_empresa,fragment).commit();

        }
    }
}
