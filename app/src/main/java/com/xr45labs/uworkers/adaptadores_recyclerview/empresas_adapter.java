package com.xr45labs.uworkers.adaptadores_recyclerview;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.fragment.*;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.xr45labs.uworkers.Modelo.empresa;
import com.xr45labs.uworkers.R;
import com.xr45labs.uworkers.fragments.empresas.fr_vista_vacante_empresa;
import com.xr45labs.uworkers.fragments.fr_perfil_empresa_externo;

import java.util.List;

/**
 * Created by xr45 on 20/05/17.
 */

public class empresas_adapter extends RecyclerView.Adapter<empresas_adapter.ViewHolder> {
    Context context;
    List<empresa> list;

    public empresas_adapter(Context context, List<empresa> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_empresa,parent,false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tv_nombre_empresa.setText(list.get(position).getNombre());
        holder.tv_giro_empresa.setText(list.get(position).getTelefono());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tv_nombre_empresa, tv_giro_empresa;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_nombre_empresa = (TextView) itemView.findViewById(R.id.tv_nombre_empresa);
            tv_giro_empresa = (TextView) itemView.findViewById(R.id.tv_giro_empresa);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            int idempresa = list.get(position).getIdempresa();
            Fragment fragment;
            AppCompatActivity activity = (AppCompatActivity) v.getContext();

            SharedPreferences sharedPreferences = activity.getSharedPreferences("data_session",Context.MODE_PRIVATE);
            int tipo_usuario = sharedPreferences.getInt("tipo",0);

            switch(tipo_usuario){
                case 1:
                    fragment = new fr_perfil_empresa_externo();
                    Bundle bundle = new Bundle();
                    bundle.putInt("idempresa",idempresa);

                    fragment.setArguments(bundle);
                    //Toast.makeText(context, list.get(position).getNombre(), Toast.LENGTH_SHORT).show();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.content_principal_alumnos,fragment,null).commit();
                    break;
                case 2:
                    Toast.makeText(context, "kjdhfsjhfkjshdf", Toast.LENGTH_SHORT).show();
                    break;
                case 3:

                    break;
                
            }
        }
    }
}
