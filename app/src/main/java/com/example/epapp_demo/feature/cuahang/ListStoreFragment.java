package com.example.epapp_demo.feature.cuahang;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.epapp_demo.R;
import com.example.epapp_demo.adapter.ShowStoreAdapter;
import com.example.epapp_demo.feature.home.HomeFragment;
import com.example.epapp_demo.feature.home.ShowMenuStoreFragment;
import com.example.epapp_demo.model.local.database.StoreDAO;
import com.example.epapp_demo.model.local.modul.Store;

import java.util.ArrayList;


public class ListStoreFragment extends Fragment {
    RecyclerView rcvStore;
    RelativeLayout btnBack;
    StoreDAO storeDAO;
    ArrayList <Store> list = new ArrayList<>();
    public static ShowStoreAdapter showcuaHangAdapter;

    public ListStoreFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_store, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        storeDAO = new StoreDAO(getActivity());
        rcvStore = view.findViewById(R.id.rcvStore);
        btnBack = view.findViewById(R.id.btn_back);

        LinearLayoutManager place = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rcvStore.setLayoutManager(place);
        list = storeDAO.getShowCuahang();
        showcuaHangAdapter = new ShowStoreAdapter(list,getActivity());
        rcvStore.setAdapter(showcuaHangAdapter);
        showcuaHangAdapter.setOnStoreItemClickListener(position -> {
            Store store = list.get(position);
            String idStore = store.getStoreID();
            ShowMenuStoreFragment newFragment = new ShowMenuStoreFragment(idStore);
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