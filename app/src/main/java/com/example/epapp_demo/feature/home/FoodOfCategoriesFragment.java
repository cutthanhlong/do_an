package com.example.epapp_demo.feature.home;

import android.annotation.SuppressLint;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.RelativeLayout;

import com.example.epapp_demo.R;
import com.example.epapp_demo.adapter.FoodAdapter;

import com.example.epapp_demo.model.local.database.FoodDAO;
import com.example.epapp_demo.model.local.modul.Food;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;



public class FoodOfCategoriesFragment extends Fragment {
    RecyclerView rcvCat;
    RelativeLayout btnBack;
    FoodDAO foodDAO;
    ArrayList<Food> list = new ArrayList<>();
    public static FoodAdapter foodAdapter;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public FoodOfCategoriesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_food_of_categories, container, false);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        foodDAO = new FoodDAO(getActivity());
        rcvCat = view.findViewById(R.id.rcvCat);
        btnBack = view.findViewById(R.id.btn_back);
        LinearLayoutManager place = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rcvCat.setLayoutManager(place);
        foodAdapter = new FoodAdapter(list, getActivity());
        rcvCat.setAdapter(foodAdapter);

        Bundle bundle = this.getArguments();
        String cat = bundle.getString("cat");

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("MonAn");
        Query query=databaseReference.orderByChild("phanLoaiID").equalTo(cat);
        query.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Food food = dataSnapshot1.getValue(Food.class);
                    list.add(food);
                }
                foodAdapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("Fragment error", "cancel", databaseError.toException());

            }
        });



        btnBack.setOnClickListener(view1 -> {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, new HomeFragment());
            transaction.commit();
        });
    }
}