package com.example.epapp_demo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.epapp_demo.R;
import com.example.epapp_demo.model.local.database.StoreDAO;
import com.example.epapp_demo.model.local.modul.Store;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ShowStoreAdapter extends RecyclerView.Adapter<ShowStoreAdapter.ViewHolder> {

    Context context;
    private List<Store> stores = new ArrayList<>();
    ArrayList<Store> cuahang;
    StoreDAO storeDAO;
    private ShowStoreAdapter.OnStoreClickListener mListener;
    public void setOnStoreItemClickListener (ShowStoreAdapter.OnStoreClickListener onStoreItemClickListener){
        mListener = onStoreItemClickListener;
    }
    public ShowStoreAdapter(ArrayList<Store> cuahang, Context context){
        this.cuahang = cuahang;
        this.context = context;
        storeDAO = new StoreDAO(context);
    }


    @NonNull
    @Override
    public ShowStoreAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_one_store,parent,false);
        storeDAO = new StoreDAO(context);
        return new ShowStoreAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            Picasso.get().load(cuahang.get(position).getStoreHinhAnh()).into(holder.ivStorePicture);
            holder.storeName.setText(cuahang.get(position).getStoreName());
            holder.storeLocation.setText(cuahang.get(position).getStoreDiaChi());
            holder.storeRating.setText(String.valueOf(cuahang.get(position).getStoreDanhGia()));
        }catch (Exception ex){

        }
    }
    @Override
    public int getItemCount() {
        return cuahang.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView storeName, storeLocation, storeRating ;
        public ImageView ivStorePicture;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            storeName = itemView.findViewById(R.id.place_name);
            storeLocation = itemView.findViewById(R.id.place_location);
            storeRating = itemView.findViewById(R.id.place_rating);
            ivStorePicture = itemView.findViewById(R.id.ivStorePicture);
            itemView.setOnClickListener(v -> mListener.onStoreItemClick(getPosition()));
        }
        @Override
        public void onClick(View v) {

        }
    }
    public interface OnStoreClickListener {
        void onStoreItemClick(int position);
//        void onPlaceFavoriteClick(Place place);
    }

}
