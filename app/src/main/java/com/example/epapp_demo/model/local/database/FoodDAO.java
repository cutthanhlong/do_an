package com.example.epapp_demo.model.local.database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.epapp_demo.feature.cuahang.FoodOfStoreFragment;
import com.example.epapp_demo.feature.cuahang.HomeStoreFragment;
import com.example.epapp_demo.feature.cuahang.ListStoreFragment;
import com.example.epapp_demo.feature.home.FoodOfCategoriesFragment;
import com.example.epapp_demo.feature.home.HomeFragment;
import com.example.epapp_demo.feature.home.ShowMenuStoreFragment;
import com.example.epapp_demo.model.local.modul.Categories;
import com.example.epapp_demo.model.local.modul.Food;
import com.example.epapp_demo.model.local.modul.Store;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class FoodDAO {
    DatabaseReference mDatabase;
    Context context;
    String monAnID;
    public FoodDAO(Context context) {
        this.mDatabase = FirebaseDatabase.getInstance().getReference("MonAn");
        this.context = context;
    }

    public ArrayList<Food> getAll(String idCuaHang) {
        final ArrayList<Food> list = new ArrayList<Food>();
        mDatabase.orderByChild("storeID").equalTo(idCuaHang).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    ds.getKey();
                    Food hd = ds.getValue(Food.class);
                    list.add(hd);

                }
                FoodOfStoreFragment.monAnAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return list;
    }



    public ArrayList<Food> getAllMenu(String idCuaHang) {
        final ArrayList<Food> list = new ArrayList<Food>();
        mDatabase.orderByChild("storeID").equalTo(idCuaHang)
        .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    list.clear();
                    Iterable<DataSnapshot> dataSnapshotIterable = dataSnapshot.getChildren();
                    Iterator<DataSnapshot> iterator = dataSnapshotIterable.iterator();
                    while (iterator.hasNext()) {
                        DataSnapshot next = (DataSnapshot) iterator.next();
                        Food pl = next.getValue(Food.class);
                        list.add(pl);
                        HomeStoreFragment.foodAdapter.notifyDataSetChanged();
                    }
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return list;
    }

    public ArrayList<Food> getAllCat(String idCategories) {
        final ArrayList<Food> list = new ArrayList<Food>();
        mDatabase.orderByChild("phanLoaiID").equalTo(idCategories).addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                Iterable<DataSnapshot> dataSnapshotIterable = dataSnapshot.getChildren();
                Iterator<DataSnapshot> iterator = dataSnapshotIterable.iterator();
                while (iterator.hasNext()) {
                    DataSnapshot next = (DataSnapshot) iterator.next();
                    Food cat = next.getValue(Food.class);
                    list.add(cat);
                    FoodOfCategoriesFragment.foodAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return list;
    }

    public ArrayList<Food> getToanBoMonAn() {

        final ArrayList<Food> list = new ArrayList<Food>();

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    list.clear();
                    Iterable<DataSnapshot> dataSnapshotIterable = dataSnapshot.getChildren();
                    Iterator<DataSnapshot> iterator = dataSnapshotIterable.iterator();
                    while (iterator.hasNext()) {
                        DataSnapshot next = (DataSnapshot) iterator.next();
                        Food food = next.getValue(Food.class);
                        list.add(food);
                        FoodOfStoreFragment.monAnAdapter.notifyDataSetChanged();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return list;
    }
    public void update(final Food s, String id) {
        mDatabase.child(id).setValue(s)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("update", "update Thanh cong");
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("update", "update That bai");
            }
        });
    }
    public  void delete( String id){
        mDatabase.child(id).removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("delete", "delete Thanh cong");
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("delete", "delete That bai");
            }
        });

    }
    public void insert(Food s) {
        monAnID = mDatabase.push().getKey();
        String MaSach = mDatabase.child(monAnID).getKey();
        s.setMonAnID(MaSach);
        mDatabase.child(monAnID).setValue(s)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Log.d("insert", "insert Thanh cong");

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("insert", "insert That bai");
            }
        });
    }

    public ArrayList<Food> getAllByIDMonAn(String idMonAn) {
        final ArrayList<Food> list = new ArrayList<Food>();
        mDatabase.orderByChild("monAnID").equalTo(idMonAn).addValueEventListener(new ValueEventListener() {
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
