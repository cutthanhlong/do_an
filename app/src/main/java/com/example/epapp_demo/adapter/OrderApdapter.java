package com.example.epapp_demo.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.epapp_demo.R;
import com.example.epapp_demo.model.local.database.OrderDAO;
import com.example.epapp_demo.model.local.modul.Store;
import com.example.epapp_demo.model.local.modul.Order;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class OrderApdapter extends RecyclerView.Adapter<OrderApdapter.ViewHolder> {

    List<Order> list;
    Context context;
    OrderDAO orderDAO;
    DatabaseReference mData = FirebaseDatabase.getInstance().getReference();

    public OrderApdapter(List<Order> list, Context context){
        this.list = list;
        this.context = context;
        orderDAO = new OrderDAO(context);
    }

    @NonNull
    @Override
    public OrderApdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_order,parent,false);
        orderDAO = new OrderDAO(context);
        return new OrderApdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final OrderApdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        String i = list.get(position).getStoreID();
        holder.ivID.setText(list.get(position).getDHID());
        holder.ivThoiGian.setText(list.get(position).getDHThoiGian());
        holder.ivTrangThai.setText(list.get(position).getDHTrangThai());

        mData.child("CuaHang").child(i).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Store user = dataSnapshot.getValue(Store.class);

                holder.ivCuaHang.setText(user.getStoreName());

                Log.d("abc1","" + list.get(position).getDHID());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView ivID, ivCuaHang, ivThoiGian, ivTrangThai;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivID = itemView.findViewById(R.id.tvmaDonHang);
            ivCuaHang = itemView.findViewById(R.id.tvTenCuaHang);
            ivThoiGian = itemView.findViewById(R.id.tvNgaythuchien);
            ivTrangThai = itemView.findViewById(R.id.tvTrangthai);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }

}
