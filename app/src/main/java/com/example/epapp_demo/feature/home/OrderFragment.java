package com.example.epapp_demo.feature.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.epapp_demo.R;
import com.example.epapp_demo.adapter.CartAdapter;
import com.example.epapp_demo.model.local.modul.CartDetails;
import com.example.epapp_demo.model.local.modul.Order;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;


public class OrderFragment extends Fragment {

    RecyclerView rcv;
    TextView tvDelete;

   private FirebaseAuth mAuth = FirebaseAuth.getInstance();


    ArrayList<Order> list = new ArrayList<>();


    ArrayList<CartDetails> listCar=new ArrayList<>();
    CartAdapter adapter;

    public OrderFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order, container, false);

          String id=mAuth.getCurrentUser().getUid();
        rcv = view.findViewById(R.id.rcv_DonHang);
        tvDelete=view.findViewById(R.id.tvDelete);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rcv.setLayoutManager(layoutManager);

        listCar=getDonByKhachID(id);
        adapter = new CartAdapter(listCar, getContext());
        rcv.setAdapter(adapter);
        tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteHistoryCart();
                Toast.makeText(getContext(),"Xóa thành công",Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }
    public ArrayList<CartDetails> getDonByKhachID(String idKhachHang) {
        final ArrayList<CartDetails> list = new ArrayList<CartDetails>();
        FirebaseDatabase data = FirebaseDatabase.getInstance();
      DatabaseReference mdata=data.getReference("Đơn hàng");
        mdata.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot ds1 : dataSnapshot.getChildren()){
                    for (DataSnapshot ds : ds1.getChildren()){
                        CartDetails hd = ds.getValue(CartDetails.class);
                        if(idKhachHang.equals(hd.getUserId())){
                            list.add(hd);
                        }
                    }

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return list;
    }

    public void deleteHistoryCart(){
        FirebaseDatabase mAuth = FirebaseDatabase.getInstance();
        DatabaseReference mdata=mAuth.getReference().child("Đơn hàng");
        mdata.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot templateSnapshot : dataSnapshot.getChildren()){
                    for(DataSnapshot snap : templateSnapshot.getChildren()){
                       snap.getRef().removeValue();
                       rcv.setAdapter(null);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Delete", "onCancelled", databaseError.toException());
            }
        });
    }


}