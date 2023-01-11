package com.example.epapp_demo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.epapp_demo.R;
import com.example.epapp_demo.model.local.database.StoreDAO;
import com.example.epapp_demo.model.local.modul.Store;

import java.util.ArrayList;

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.ViewHolder> {

    Context context;
    ArrayList<Store> cuahang;
    StoreDAO storeDAO;

    public StoreAdapter(ArrayList<Store> cuahang, Context context){
        this.cuahang = cuahang;
        this.context = context;
        storeDAO = new StoreDAO(context);
    }

    @NonNull
    @Override
    public StoreAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_store,parent,false);
        storeDAO = new StoreDAO(context);
        return new StoreAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.tvidCH.setText(cuahang.get(position).getStoreID());
        holder.tvmailCH.setText(cuahang.get(position).getStoreMail());
        holder.tvNameCH.setText(cuahang.get(position).getStoreName());
        holder.tvpassCH.setText(cuahang.get(position).getStorePass());
        holder.tvDiaChiCH.setText(cuahang.get(position).getStoreDiaChi());

    }

    @Override
    public int getItemCount() {
        return cuahang.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tvidCH, tvmailCH, tvNameCH, tvpassCH, tvDiaChiCH;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvidCH = itemView.findViewById(R.id.tvidCH);
            tvmailCH = itemView.findViewById(R.id.tvmailCH);
            tvNameCH = itemView.findViewById(R.id.tvNameCH);
            tvpassCH = itemView.findViewById(R.id.tvpassCH);
            tvDiaChiCH = itemView.findViewById(R.id.tvDiaChiCH);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }

}
