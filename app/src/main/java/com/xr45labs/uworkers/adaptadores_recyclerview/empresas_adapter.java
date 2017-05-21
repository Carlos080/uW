package com.xr45labs.uworkers.adaptadores_recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xr45labs.uworkers.Modelo.empresa;
import com.xr45labs.uworkers.R;

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
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_nombre_empresa;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_nombre_empresa = (TextView) itemView.findViewById(R.id.tv_nombre_empresa);
        }
    }
}
