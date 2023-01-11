package com.example.epapp_demo.feature.home;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.epapp_demo.R;
import com.example.epapp_demo.adapter.HomeCategoriesAdapter;
import com.example.epapp_demo.adapter.ShowStoreAdapter;
import com.example.epapp_demo.adapter.SuggestStoreAdapter;
import com.example.epapp_demo.adapter.SliderAdapter;

import com.example.epapp_demo.feature.admin.ListCategoriesFragment;
import com.example.epapp_demo.feature.cuahang.ListStoreFragment;
import com.example.epapp_demo.model.local.database.StoreDAO;
import com.example.epapp_demo.model.local.database.CategoriesDAO;
import com.example.epapp_demo.model.local.modul.SuggestStore;
import com.example.epapp_demo.model.local.modul.Categories;
import com.example.epapp_demo.model.local.modul.Store;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment implements LocationListener {
    SliderView sliderView;
    RecyclerView rcvCategories;
    RecyclerView rcvQuanGoiY;
    public static SuggestStoreAdapter suggestStoreAdapter;
    public static ShowStoreAdapter showStoreAdapter;
    List<SuggestStore> suggestStoreList = new ArrayList<>();
    public static HomeCategoriesAdapter homeCategoriesAdapter;
    ArrayList<Categories> list = new ArrayList<>();
    ArrayList<Store> listSt = new ArrayList<>();
    boolean GpsStatus;
    CategoriesDAO categoriesDAO = new CategoriesDAO(getActivity());
    StoreDAO storeDAO = new StoreDAO(getActivity());
    ImageView btn_reload;
    LocationManager locationManager;
    TextView tv_list_cuahang, tv_list_phanloai;
    EditText edtSearch;
    RelativeLayout btnSearch;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_home, container, false);

     return view;
    }
    @SuppressLint("MissingPermission")
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LocationManager manager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //Permission already Granted
            //Do your work here
            //Perform operations here only which requires permission
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        }
        manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, (LocationListener) this);
        final Location location = manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        sliderView = view.findViewById(R.id.imgSlider);
        rcvCategories = (RecyclerView)view.findViewById(R.id.rcv_category);
        rcvQuanGoiY = view.findViewById(R.id.rcvQuanGoiY);
        btn_reload = view.findViewById(R.id.btn_reload);
        tv_list_cuahang = view.findViewById(R.id.place_list);
        tv_list_phanloai = view.findViewById(R.id.categories_list);
        edtSearch = view.findViewById(R.id.edtSearch);
        btnSearch = view.findViewById(R.id.btnSearch);

        LinearLayoutManager llmTrending = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rcvCategories.setLayoutManager(llmTrending);
        list = categoriesDAO.getAllMenu();
        homeCategoriesAdapter = new HomeCategoriesAdapter(list,getActivity());
        rcvCategories.setAdapter(homeCategoriesAdapter);

        LinearLayoutManager menu = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rcvQuanGoiY.setLayoutManager(menu);
        listSt = storeDAO.getAllMenu();
        showStoreAdapter = new ShowStoreAdapter(listSt,getActivity());
        rcvQuanGoiY.setAdapter(showStoreAdapter);

        homeCategoriesAdapter.setOnItemClickListener(position -> {
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

        showStoreAdapter.setOnStoreItemClickListener(position -> {
            Store cuaHangTemp = listSt.get(position);
            String idStore = cuaHangTemp.getStoreID();
            ShowMenuStoreFragment newFragment = new ShowMenuStoreFragment(idStore);
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });

        //custom slider
        SliderAdapter adapter = new SliderAdapter(getActivity());
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

        btn_reload.setVisibility(View.INVISIBLE);
        getTemp();
        if (location == null) {
            btn_reload.setVisibility(View.VISIBLE);
        }

        btn_reload.setOnClickListener(v -> {
            locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            GpsStatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            if (GpsStatus == true) {
                getTemp();
                btn_reload.setVisibility(View.INVISIBLE);
            } else {
                Toast.makeText(getActivity(), "Bạn chưa bật vị trí của thiết bị!", Toast.LENGTH_SHORT).show();
            }
        });

        tv_list_cuahang.setOnClickListener(view1 -> {
            FragmentTransaction transaction =  getFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout,new ListStoreFragment());
            transaction.commit();
        });

        tv_list_phanloai.setOnClickListener(view12 -> {
            FragmentTransaction transaction =  getFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout,new ListCategoriesFragment());
            transaction.commit();
        });

        btnSearch.setOnClickListener(view13 -> {
            Bundle bundle = new Bundle();
            bundle.putString("search",edtSearch.getText().toString().trim());
            SearchFragment searchFragment = new SearchFragment();
            searchFragment.setArguments(bundle);
            FragmentTransaction transaction =  getFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout,searchFragment);
            transaction.commit();
        });


    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public void getTemp() {
        StoreDAO storeDAO = new StoreDAO(getActivity());
        suggestStoreList = storeDAO.getTemp(getActivity());
        suggestStoreAdapter = new SuggestStoreAdapter(suggestStoreList,getActivity());
        Log.d("size","temp: "+suggestStoreList.size());
    }
}