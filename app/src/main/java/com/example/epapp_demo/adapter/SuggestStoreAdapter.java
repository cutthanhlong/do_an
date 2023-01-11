package com.example.epapp_demo.adapter;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.epapp_demo.R;
import com.example.epapp_demo.model.local.database.StoreDAO;
import com.example.epapp_demo.model.local.modul.SuggestStore;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;


public class SuggestStoreAdapter extends RecyclerView.Adapter<SuggestStoreAdapter.ViewHolder> {
    Context context;
    List<SuggestStore> suggestStoreList;
    StoreDAO storeDAO;
    private SuggestStoreAdapter.OnSuggestStoreClickListener mListener;
    public void setOnSuggestStoreItemClickListener (SuggestStoreAdapter.OnSuggestStoreClickListener onSuggestStoreItemClickListener){
        mListener = onSuggestStoreItemClickListener;
    }

    public SuggestStoreAdapter(List<SuggestStore> suggestStoreList, Context context) {
        this.suggestStoreList = suggestStoreList;
        this.context = context;
        storeDAO = new StoreDAO(context);
    }

    @NonNull
    @Override
    public SuggestStoreAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_nearby_stote,parent,false);
        storeDAO = new StoreDAO(context);
        return new SuggestStoreAdapter.ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final SuggestStore cuaHang = this.suggestStoreList.get(position);
        if (cuaHang.getTencuahang().length() > 20) {
            holder.tv_tenquan.setText(cuaHang.getTencuahang().substring(0, 20) + " ...");
        } else {
            holder.tv_tenquan.setText(cuaHang.getTencuahang());
        }
        if (cuaHang.getDiachi().length() > 30) {
            holder.tv_diachi.setText(cuaHang.getDiachi().substring(0, 30) + " ...");
        } else {
            holder.tv_diachi.setText(cuaHang.getDiachi());
        }

        holder.tv_rating.setText(String.valueOf(cuaHang.getRating()));
        try {
            Picasso.get().load(cuaHang.getHinhanh()).into(holder.img_cuahang);
        } catch (Exception e) {

        }
        holder.item_cuahang.setOnClickListener(v -> mListener.onSuggestStoreItemClick(position));

        LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && context.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        }

        Location location = manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (location != null) {
            holder.tv_khoangcach.setText(distanceBetween2Points(location.getLatitude(), location.getLongitude(), cuaHang.getLatitude(), cuaHang.getLongitude()) + " km");
        }
    }

    @Override
    public int getItemCount() {
        return suggestStoreList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements OnSuggestStoreClickListener {
        ImageView img_cuahang;
        TextView tv_tenquan, tv_diachi, tv_rating, tv_khoangcach;
        LinearLayout item_cuahang;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_cuahang = itemView.findViewById(R.id.img_cuahang);
            tv_tenquan = itemView.findViewById(R.id.tv_tenquan);
            tv_diachi = itemView.findViewById(R.id.tv_address);
            tv_rating = itemView.findViewById(R.id.tv_rating);
            tv_khoangcach = itemView.findViewById(R.id.tv_khoangcach);
            item_cuahang = itemView.findViewById(R.id.item_cuahnag);
        }

        @Override
        public void onSuggestStoreItemClick(int position) {

        }
    }

    public static String distanceBetween2Points(double la1, double lo1, double la2, double lo2) {
        double dLat = (la2 - la1) * (Math.PI / 180);
        double dLon = (lo2 - lo1) * (Math.PI / 180);
        double la1ToRad = la1 * (Math.PI / 180);
        double la2ToRad = la2 * (Math.PI / 180);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(la1ToRad) * Math.cos(la2ToRad) * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double d = 6400 * c;

        //format number
        NumberFormat formatter = new DecimalFormat("#0.0");
        return formatter.format(d);
    }
    public interface OnSuggestStoreClickListener {
        void onSuggestStoreItemClick(int position);
    }
}
