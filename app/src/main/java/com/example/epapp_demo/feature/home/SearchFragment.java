package com.example.epapp_demo.feature.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.epapp_demo.R;
import com.example.epapp_demo.adapter.SearchAdapter;

import com.example.epapp_demo.model.local.modul.Food;
import com.example.epapp_demo.model.local.modul.Store;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchFragment extends Fragment {
    RecyclerView rcvStoreSuggest;
    RelativeLayout btnBack;

    SearchAdapter searchAdapter;
    ArrayList<Food> list = new ArrayList<>();
    ArrayList<Store> storeList = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_result, container, false);

        rcvStoreSuggest = view.findViewById(R.id.rcvStoreSuggest);
        btnBack = view.findViewById(R.id.btn_back);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rcvStoreSuggest.setLayoutManager(layoutManager);

        Bundle bundle = this.getArguments();
        String search = bundle.getString("search");

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("MonAn");
        databaseReference.addValueEventListener(new ValueEventListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                Food food =dataSnapshot1.getValue(Food.class);
                                if(food.getNameMonAn().contains(search)){
                                    list.add(food);
                                }
                                searchAdapter.notifyDataSetChanged();

                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.w("SearchFragment", "cancel", databaseError.toException());

                    }
                });

        searchAdapter =new SearchAdapter(list, getContext());
        rcvStoreSuggest.setAdapter(searchAdapter);

        searchAdapter.setOnStoreItemClickListener(position -> {
            Store store = storeList.get(position);
            String idStore = store.getStoreID();
            ShowMenuStoreFragment newFragment = new ShowMenuStoreFragment(idStore);
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });

        btnBack.setOnClickListener(view1 -> {
            FragmentTransaction transaction =  getFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout,new HomeFragment());
            transaction.commit();
        });
        return view;
    }
//    private void updateStore(final Food food){
//        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("CuaHang");
//        databaseReference.child("storeName")
//                .addValueEventListener(new ValueEventListener() {
//                    @SuppressLint("NotifyDataSetChanged")
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//
//                            Store store =dataSnapshot1.getValue(Store.class);
//                            food.setNameStore(store.getStoreName());
//                            list.add(food);
//                            searchAdapter.notifyDataSetChanged();
//
//                        }
//
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//                        Log.w("SearchFragment", "cancel", databaseError.toException());
//
//                    }
//                });
//    }
}
