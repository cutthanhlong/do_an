package com.example.epapp_demo.feature.home;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;


import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.epapp_demo.R;
import com.example.epapp_demo.adapter.CartAdapter;
import com.example.epapp_demo.model.local.database.DbHelper;
import com.example.epapp_demo.model.local.modul.CartDetails;

import com.example.epapp_demo.model.local.modul.Categories;
import com.example.epapp_demo.model.local.modul.Customer;
import com.example.epapp_demo.model.local.modul.Food;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.List;


public class CartFragment extends Fragment {
    RecyclerView rcv;
    Button Add;
    CartAdapter adapter;
    ArrayList<CartDetails> list = new ArrayList<>();
    DbHelper db;
    List<Customer> listCustomer;

    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        rcv = view.findViewById(R.id.rcvGioHang);
        Add = view.findViewById(R.id.btnAddGioHang);
        db = new DbHelper(getContext());

        final LinearLayoutManager place = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rcv.setLayoutManager(place);

        list = db.listGioHang();
        adapter = new CartAdapter(list, getContext());
        rcv.setAdapter(adapter);
        rcv.clearOnChildAttachStateChangeListeners();

        Add.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference mdata = firebaseDatabase.getReference("Đơn hàng");
                if (list.isEmpty()) {
                    Toast.makeText(getContext(), "Bạn chưa thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    View view1 = getLayoutInflater().inflate(R.layout.add_address_dialog,null);
                    final EditText edtAddress = view1.findViewById(R.id.edtAddress);

                    builder.setView(view1);


                    builder.setPositiveButton("Thêm", (dialogInterface, i1) -> {
                        String address = edtAddress.getText().toString();

                        String id = mdata.push().getKey();
                        mdata.child(id).setValue(list, (databaseError, databaseReference) -> {
                            list.forEach(item -> db.delete(String.valueOf(item.getMonAnId())));
                            rcv.setAdapter(null);
                            Toast.makeText(getContext(), "Đặt hàng thành công", Toast.LENGTH_SHORT).show();

                        });

                    }).setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.setView(view1);
                    builder.show();



                }


            }
        });

        return view;
    }



}