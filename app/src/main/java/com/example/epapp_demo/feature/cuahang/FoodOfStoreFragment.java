package com.example.epapp_demo.feature.cuahang;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.epapp_demo.R;
import com.example.epapp_demo.adapter.FoodAdapter;
import com.example.epapp_demo.model.local.database.FoodDAO;
import com.example.epapp_demo.model.local.modul.Food;
import com.example.epapp_demo.model.local.modul.Categories;
import com.example.epapp_demo.model.local.database.CategoriesDAO;
import com.example.epapp_demo.model.local.modul.Store;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;

import java.util.ArrayList;

public class FoodOfStoreFragment extends Fragment {

    RecyclerView rcv;
    FloatingActionButton add;

    FoodDAO foodDAO = new FoodDAO(getActivity());
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public static FoodAdapter monAnAdapter;
    ArrayList<Food> list = new ArrayList<>();

    public FoodOfStoreFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_food_of_store, container, false);
        rcv = view.findViewById(R.id.recycler_mon_an_cua_hang);
        add = view.findViewById(R.id.btn_add_mon_an);

        String i = mAuth.getCurrentUser().getUid();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rcv.setLayoutManager(layoutManager);
        mAuth = FirebaseAuth.getInstance();
        list = foodDAO.getAll(i);
        monAnAdapter = new FoodAdapter(list,getActivity());
        rcv.setAdapter(monAnAdapter);
        final ArrayList<Categories> listPL = new CategoriesDAO(getActivity()).getAllspn();

        add.setOnClickListener(v -> {
            final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            View view1 = getLayoutInflater().inflate(R.layout.add_food_dialog,null);
            final EditText tenmon = view1.findViewById(R.id.edtTenMon);
            final Spinner spn = view1.findViewById(R.id.spnTheLoai);
            final EditText mota = view1.findViewById(R.id.edtMotaMon);
            final EditText gia = view1.findViewById(R.id.edtGiaMon);
            final EditText url = view1.findViewById(R.id.edtUrlMon);

            //Test

            ArrayAdapter adapter = new ArrayAdapter (getActivity(), android.R.layout.simple_spinner_item, listPL);
            spn.setAdapter(adapter);

            builder.setView(view1);
            builder.setPositiveButton("Thêm", (dialogInterface, i1) -> {
                String tenmon1 = tenmon.getText().toString();
                String mota1 = mota.getText().toString();
                int gia1 = Integer.parseInt(gia.getText().toString());
                String url1 = url.getText().toString();
                Categories loai = (Categories) spn.getSelectedItem();
                String matheloai = loai.getLoaiID();


                String a = mAuth.getCurrentUser().getUid();;
                Food s = new Food(null,tenmon1,gia1,url1,a,matheloai,mota1);
                foodDAO.insert(s);
            }).setNegativeButton("Hủy", (dialog, which) -> {

            });
            builder.setView(view1);
            builder.show();
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }
}