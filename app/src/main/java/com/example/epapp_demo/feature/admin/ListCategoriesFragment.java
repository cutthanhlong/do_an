package com.example.epapp_demo.feature.admin;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;


import com.example.epapp_demo.R;

import com.example.epapp_demo.adapter.ShowCategoriesAdapter;
import com.example.epapp_demo.feature.home.FoodOfCategoriesFragment;
import com.example.epapp_demo.feature.home.HomeFragment;
import com.example.epapp_demo.feature.home.SearchFragment;
import com.example.epapp_demo.model.local.database.CategoriesDAO;
import com.example.epapp_demo.model.local.modul.Categories;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListCategoriesFragment extends Fragment {
    RecyclerView rcvCategories;
    CategoriesDAO categoriesDAO;
    RelativeLayout btnBack;
    ArrayList<Categories> list = new ArrayList<>();

    public static ShowCategoriesAdapter showCategoriesAdapter;

    public ListCategoriesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_list_categories, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        categoriesDAO = new CategoriesDAO(getActivity());
        rcvCategories = view.findViewById(R.id.rcvCategories);
        btnBack = view.findViewById(R.id.btn_back);
        LinearLayoutManager place = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rcvCategories.setLayoutManager(place);
        list = categoriesDAO.getShowCat();
        showCategoriesAdapter = new ShowCategoriesAdapter(list,getActivity());
        rcvCategories.setAdapter(showCategoriesAdapter);
        showCategoriesAdapter.setOnStoreItemClickListener(position -> {

            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("PhanLoai");
            databaseReference.orderByChild("loaiID").addValueEventListener(new ValueEventListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Categories categories = dataSnapshot1.getValue(Categories.class);
                    dataSnapshot1.getKey();
                        list.add(categories);
                        list.forEach(Categories::getLoaiID);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            Bundle bundle = new Bundle();
            bundle.putString("cat",list.get(position).getLoaiID());

            FoodOfCategoriesFragment newFragment = new FoodOfCategoriesFragment();
            newFragment.setArguments(bundle);
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
    }
}