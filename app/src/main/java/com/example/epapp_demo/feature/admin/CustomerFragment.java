package com.example.epapp_demo.feature.admin;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.epapp_demo.R;
import com.example.epapp_demo.adapter.CustomerAdapter;
import com.example.epapp_demo.model.local.database.CustomerDAO;
import com.example.epapp_demo.model.local.modul.Customer;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class CustomerFragment extends Fragment {

     CustomerDAO customerDAO = new CustomerDAO(getActivity());
    private FirebaseAuth mAuth;

    public static CustomerAdapter khachHangAdapter;
    RecyclerView lv;
    ArrayList<Customer> list = new ArrayList<>();


    public CustomerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_customer, container, false);
        lv = view.findViewById(R.id.rcvQlyKH);
        mAuth = FirebaseAuth.getInstance();

        list = customerDAO.getAll();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        lv.setLayoutManager(layoutManager);
        khachHangAdapter = new CustomerAdapter(getActivity(),list);
        lv.setAdapter(khachHangAdapter);
        Log.d("test1", String.valueOf(list));

        return view;
    }
}