package com.example.epapp_demo.model.local.database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.epapp_demo.feature.home.OrderFragment;
import com.example.epapp_demo.feature.cuahang.StoreActivitiesFragment;
import com.example.epapp_demo.model.local.modul.CartDetails;
import com.example.epapp_demo.model.local.modul.Order;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OrderDAO {
    DatabaseReference mDatabase;
    Context context;
    public OrderDAO(Context context) {
        this.mDatabase = FirebaseDatabase.getInstance().getReference("Đơn hàng");
        this.context = context;
    }


    public ArrayList<Order> getDonByKhachID(String idKhachHang) {
        final ArrayList<Order> list = new ArrayList<Order>();

        mDatabase.orderByChild("userID").equalTo(idKhachHang).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    ds.getKey();
                    Order hd = ds.getValue(Order.class);
                    Log.d("ab1", hd.getStoreID());
                    list.add(hd);

                }
//                OrderFragment.donHangApdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return list;
    }



}
