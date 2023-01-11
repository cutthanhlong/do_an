package com.example.epapp_demo.feature.cuahang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.epapp_demo.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomNavigationStore extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottom__navigation__store);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation_1);
        navigation.setItemIconTintList(null);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        // set fragment home đầu tiên
        if (savedInstanceState == null) {
            HomeStoreFragment gt  = new HomeStoreFragment();
            FragmentManager mn = getSupportFragmentManager();
            mn.beginTransaction()
                    .add(R.id.fragment_1, gt)
                    .commit();
        }

    }
    @SuppressLint("NonConstantResourceId")
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
                switch (item.getItemId()) {
                    case R.id.Home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_1, new HomeStoreFragment()).commit();
                        return true;
                    case R.id.Hoat_Dong:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_1, new StoreActivitiesFragment()).commit();
                        return true;
                    case R.id.Mon_An:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_1, new FoodOfStoreFragment()).commit();
                        return true;
                    case R.id.Tai_Khoan:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_1, new StoreSettingsFragment()).commit();
                        return true;

                }
                return false;
            };
}