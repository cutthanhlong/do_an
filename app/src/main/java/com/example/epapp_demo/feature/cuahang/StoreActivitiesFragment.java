package com.example.epapp_demo.feature.cuahang;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.epapp_demo.R;
import com.example.epapp_demo.adapter.CartAdapter;
import com.example.epapp_demo.model.local.database.OrderDAO;
import com.example.epapp_demo.model.local.modul.CartDetails;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class StoreActivitiesFragment extends Fragment {
    RecyclerView rcvSA;
    OrderDAO orderDAO = new OrderDAO(getActivity());
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public static CartAdapter donHangApdapter;
    ArrayList<CartDetails> list = new ArrayList<>();

    public StoreActivitiesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_store_activities, container, false);

        String i = mAuth.getCurrentUser().getUid();
        rcvSA = view.findViewById(R.id.rcv_store_acti);


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rcvSA.setLayoutManager(layoutManager);
        mAuth = FirebaseAuth.getInstance();
        getDonByCuaHangID();
        donHangApdapter = new CartAdapter(list, getActivity());
        rcvSA.setAdapter(donHangApdapter);
        return view;
    }

    public void getDonByCuaHangID() {
        String i = mAuth.getCurrentUser().getUid();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Đơn hàng");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    for (DataSnapshot data : ds.getChildren()) {
                        CartDetails cartDetails = data.getValue(CartDetails.class);
                        if (cartDetails != null) {
                            if (cartDetails.getCuaHangID().equals(i)) {
                                list.add(cartDetails);
                                Log.d("DonHang", "ListDH" + list);
                            }
                            donHangApdapter.notifyDataSetChanged();
                        }


                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}