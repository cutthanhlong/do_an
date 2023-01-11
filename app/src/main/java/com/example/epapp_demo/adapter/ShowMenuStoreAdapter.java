package com.example.epapp_demo.adapter;

import android.annotation.SuppressLint;
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
import com.example.epapp_demo.model.local.modul.Food;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ShowMenuStoreAdapter extends RecyclerView.Adapter<ShowMenuStoreAdapter.ViewHolder> {
    Context context;
    private List<Food> foods = new ArrayList<>();
    ArrayList<Food> food;
    StoreDAO storeDAO;
    RecyclerView rcvMenu;
    DecimalFormat formatter = new DecimalFormat("###,###,###");
    private ShowMenuStoreAdapter.OnMenuClickListener mListener;
    public void setOnMenuItemClickListener (ShowMenuStoreAdapter.OnMenuClickListener onMenuItemClickListener){
        mListener = onMenuItemClickListener;
    }
    public ShowMenuStoreAdapter(ArrayList<Food> food, Context context){
        this.food = food;
        this.context = context;
        storeDAO = new StoreDAO(context);
    }

    @NonNull
    @Override
    public ShowMenuStoreAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_food,parent,false);
        storeDAO = new StoreDAO(context);
        return new ShowMenuStoreAdapter.ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Picasso.get().load(food.get(position).getHinhAnhMonAn()).into(holder.ivHinhMonAn);
        holder.tenMonAn.setText(food.get(position).getNameMonAn());
        holder.moTaMonAn.setText(food.get(position).getMoTa());
        holder.giaMonAn.setText(formatter.format(food.get(position).getGiaMonAn())+" VND");
    }

    @Override
    public int getItemCount() {
        return food.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView tenMonAn, moTaMonAn , giaMonAn;
        ImageView ivHinhMonAn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rcvMenu = itemView.findViewById(R.id.recyclerStoreMenu);
            tenMonAn = itemView.findViewById(R.id.item_ten_MA);
            moTaMonAn = itemView.findViewById(R.id.item_moTa_MA);
            giaMonAn = itemView.findViewById(R.id.item_gia_MA);
            ivHinhMonAn = itemView.findViewById(R.id.anh_MA);
            itemView.setOnClickListener(v -> mListener.onMenuItemClick(getPosition()));
        }
        @Override
        public void onClick(View v) {

        }
    }
    public interface OnMenuClickListener {
        void onMenuItemClick(int position);
    }
}
