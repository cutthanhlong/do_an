package com.example.epapp_demo.model.local.database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.epapp_demo.feature.home.ShowMenuStoreFragment;
import com.example.epapp_demo.model.local.modul.Food;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShowMenuDAO {
    DatabaseReference mDatabase;
    Context context;
    String CuaHangID;
    public ShowMenuDAO(Context context) {
        this.mDatabase = FirebaseDatabase.getInstance().getReference("MonAn");
        this.context = context;
    }

    public ArrayList<Food> getMonAnByCuaHangID(String idCuaHang) {
        final ArrayList<Food> list = new ArrayList<Food>();
        mDatabase.orderByChild("storeID").equalTo(idCuaHang).addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    ds.getKey();
                    Food food = ds.getValue(Food.class);
                    Log.d("ab1", food.getMonAnID());
                    list.add(food);

                }
                ShowMenuStoreFragment.showMenuStoreAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return list;
    }


}
