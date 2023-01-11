package com.example.epapp_demo.feature.cuahang;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.epapp_demo.R;
import com.example.epapp_demo.adapter.ShowFoodAdapter;
import com.example.epapp_demo.adapter.SliderAdapter1;
import com.example.epapp_demo.model.local.database.FoodDAO;
import com.example.epapp_demo.model.local.modul.CartDetails;
import com.example.epapp_demo.model.local.modul.Food;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class HomeStoreFragment extends Fragment {
    SliderView sliderView;
    RecyclerView rcvMenu;
    TextView tvShowMenu;
    TextView tvDoanhThu;
    public static ShowFoodAdapter foodAdapter;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FoodDAO foodDAO = new FoodDAO(getActivity());
    ArrayList<Food> list = new ArrayList<>();
    ArrayList<CartDetails> listCart = new ArrayList<>();
    public HomeStoreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_store, container, false);

        return view;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sliderView = view.findViewById(R.id.imgSlider);

        tvShowMenu = view.findViewById(R.id.tv_show_menu);
        rcvMenu = view.findViewById(R.id.rcvMenu);
        tvDoanhThu=view.findViewById(R.id.tv_Doanhthu);

        String id = mAuth.getCurrentUser().getUid();


        LinearLayoutManager menu = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rcvMenu.setLayoutManager(menu);
        list = foodDAO.getAllMenu(id);
        getDonByCuaHangID();
        foodAdapter = new ShowFoodAdapter(list, getActivity());
        rcvMenu.setAdapter(foodAdapter);

        //custom slider
        SliderAdapter1 adapter = new SliderAdapter1(getActivity());
        sliderView.setSliderAdapter(adapter);
        //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setIndicatorAnimation(IndicatorAnimations.FILL); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.CUBEINDEPTHTRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(3);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();

        tvShowMenu.setOnClickListener(view1 -> {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_1, new FoodOfStoreFragment());
            transaction.commit();
        });
    }
    public void getDonByCuaHangID() {
        String i = mAuth.getCurrentUser().getUid();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Đơn hàng");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    for (DataSnapshot data : ds.getChildren()) {
                        CartDetails cartDetails = data.getValue(CartDetails.class);
                        if (cartDetails != null) {
                            if (cartDetails.getCuaHangID().equals(i)) {
                                listCart.add(cartDetails);
                                Log.d("DonHang", "ListDH" + list);
                                final DecimalFormat formatter = new DecimalFormat("###,###,###");
                                int sum=0;
                                for (CartDetails element : listCart) {
                                    sum +=element.getGia()*element.getSoluong();
                                    tvDoanhThu.setText(formatter.format(sum)+ " VNĐ");
                                }
                            }
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