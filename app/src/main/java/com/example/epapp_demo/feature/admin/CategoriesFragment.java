package com.example.epapp_demo.feature.admin;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.epapp_demo.R;
import com.example.epapp_demo.adapter.CategoriesAdapter;
import com.example.epapp_demo.model.local.database.CategoriesDAO;
import com.example.epapp_demo.model.local.modul.Categories;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CategoriesFragment extends Fragment {

    public static String id;
    CategoriesDAO categoriesDAO = new CategoriesDAO(getActivity());
    private FirebaseAuth mAuth;
    DatabaseReference mData = FirebaseDatabase.getInstance().getReference("PhanLoai");
    String LoaiID;
    public static CategoriesAdapter categoriesAdapter;
    RecyclerView lv;
    ArrayList<Categories> list = new ArrayList<>();
    FloatingActionButton add;

    public CategoriesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_categories, container, false);
        lv = view.findViewById(R.id.rcvPhanLoai);
        add = view.findViewById(R.id.btnAddPhanLoai);
        mAuth = FirebaseAuth.getInstance();

        list = categoriesDAO.getAll();
        categoriesAdapter = new CategoriesAdapter(list,getActivity());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        lv.setLayoutManager(layoutManager);
        lv.setAdapter(categoriesAdapter);
        Log.d("test2", String.valueOf(list));

        add.setOnClickListener(view12 -> {
            final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            View view1 = getLayoutInflater().inflate(R.layout.add_categories_dialog,null);
            final EditText tenloai = view1.findViewById(R.id.edttenlaoi);
            final EditText motalaoi = view1.findViewById(R.id.edtMota);
            final EditText anh = view1.findViewById(R.id.edtUrlAnh);

            builder.setView(view1);

            builder.setPositiveButton("Thêm", (dialogInterface, i) -> {

                String anh1 = anh.getText().toString();
                String tenl1 = tenloai.getText().toString();
                String motal1 = motalaoi.getText().toString();
                Categories s = new Categories(null,tenl1,motal1,anh1);
                categoriesDAO.insert(s);

            }).setNegativeButton("Hủy", (dialog, which) -> {

            });
            builder.setView(view1);
            builder.show();
        });

        return view;
    }
}
