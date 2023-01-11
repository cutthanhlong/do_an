package com.example.epapp_demo.feature.cuahang;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.epapp_demo.R;
import com.example.epapp_demo.feature.login.LoginActivity;
import com.example.epapp_demo.model.local.database.StoreDAO;
import com.example.epapp_demo.model.local.modul.Store;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;


public class StoreSettingsFragment extends Fragment {
    ImageView ivAvt;
    ImageView ivEditViTri, ivLogoutCH, ivEditProfileCH;
    TextView tvNameCHa, tvMailCHa,tvDiaChiCHa, tvDanhgia, kinhdo, vido;
    FirebaseAuth fAuth = FirebaseAuth.getInstance();;
    DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
    String userID = fAuth.getCurrentUser().getUid();
    StoreDAO storeDAO = new StoreDAO(getActivity());

    public StoreSettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=  inflater.inflate(R.layout.fragment_store_settings, container, false);
        return  view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        ivAvt = view.findViewById(R.id.ivCH);
        ivEditViTri = view.findViewById(R.id.ivEditViTri);
        ivLogoutCH = view.findViewById(R.id.ivLogoutCH);
        tvNameCHa = view.findViewById(R.id.tvNameCHa);
        tvMailCHa = view.findViewById(R.id.tvMailCHa);
        tvDiaChiCHa = view.findViewById(R.id.tvDiaChiCHa);
        tvDanhgia = view.findViewById(R.id.tvDanhgia);
        kinhdo = view.findViewById(R.id.kinhdo);
        vido = view.findViewById(R.id.vido);
        ivEditProfileCH = view.findViewById(R.id.ivEditProfileCH);

        ivAvt.setOnClickListener(view12 -> {
            final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            View view1 = getLayoutInflater().inflate(R.layout.edit_avatar,null);
            final EditText url = view1.findViewById(R.id.url);
            builder.setView(view1);
            builder.setPositiveButton("Sửa", new DialogInterface.OnClickListener() {
                @SuppressLint("RestrictedApi")
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    mData.child("CuaHang").child(userID).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Store user = dataSnapshot.getValue(Store.class);
                            String url1 = url.getText().toString();
                            userID = fAuth.getCurrentUser().getUid();
//                                        String userId = mData.push().getKey();
                            Store s = new Store(userID,user.getStoreMail(),user.getStorePass(),user.getStoreMonAn(), user.getStoreName(),user.getStoreDiaChi(),user.getStoreDanhGia(),url1,user.getStoreViDo(),user.getStoreKinhDo(),1);
                            storeDAO.update(s);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                }
            });
            builder.setNegativeButton("Hủy", (dialog, which) -> {

            });
            builder.setView(view1);
            builder.show();
        });

        ivEditViTri.setOnClickListener(view13 -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view1 = layoutInflater.inflate(R.layout.add_locate_store,null);

            final EditText kinhdo = view1.findViewById(R.id.edtKinhDo);
            final EditText vido = view1.findViewById(R.id.edtViDo);

            builder.setPositiveButton("Sửa", new DialogInterface.OnClickListener() {
                @SuppressLint("RestrictedApi")
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    mData.child("CuaHang").child(userID).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Store user = dataSnapshot.getValue(Store.class);
                            double kinhdo1 = Double.parseDouble(kinhdo.getText().toString());
                            double vido1 = Double.parseDouble(vido.getText().toString());

                            Store s = new Store(userID,user.getStoreMail(),user.getStorePass(),user.getStoreMonAn(), user.getStoreName(),user.getStoreDiaChi(),user.getStoreDanhGia(),user.getStoreHinhAnh(),vido1,kinhdo1,1);
                            storeDAO.update(s);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                }
            });
            builder.setNegativeButton("Hủy", (dialog, which) -> {

            });
            builder.setView(view1);
            builder.show();

        });

        ivEditProfileCH.setOnClickListener(view14 -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view1 = layoutInflater.inflate(R.layout.edit_store,null);

            final EditText name = view1.findViewById(R.id.edtNameCHa);
            final EditText diachi = view1.findViewById(R.id.edtDiaCHiCHa);

            builder.setPositiveButton("Sửa", new DialogInterface.OnClickListener() {
                @SuppressLint("RestrictedApi")
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    mData.child("CuaHang").child(userID).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            try{
                                Store user = dataSnapshot.getValue(Store.class);
                                String name1 = name.getText().toString();
                                String diachi1 = diachi.getText().toString();

                                Store s = new Store(userID,user.getStoreMail(),user.getStorePass(),user.getStoreMonAn(), name1,diachi1,user.getStoreDanhGia(),user.getStoreHinhAnh(),user.getStoreViDo(),user.getStoreKinhDo(),1);
                                storeDAO.update(s);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                }
            });
            builder.setNegativeButton("Hủy", (dialog, which) -> {

            });
            builder.setView(view1);
            builder.show();
        });

        mData.child("CuaHang").child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Store user = dataSnapshot.getValue(Store.class);

                try {
                    Picasso.get().load(user.getStoreHinhAnh()).into(ivAvt);
                    tvNameCHa.setText(user.getStoreName());
                    tvMailCHa.setText(user.getStoreMail());
                    tvDiaChiCHa.setText(user.getStoreDiaChi());
                    tvDanhgia.setText(String.valueOf(user.getStoreDanhGia()));
                    kinhdo.setText(String.valueOf(user.getStoreKinhDo()));
                    vido.setText(" , "+String.valueOf(user.getStoreViDo()));
                } catch (Exception e) {

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        ivLogoutCH.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
                View view1 = layoutInflater.inflate(R.layout.logout_alert_dialog,null);


                builder.setPositiveButton("Có", (dialogInterface, i) -> {
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                });
                builder.setNegativeButton("Không", (dialog, which) -> {

                });
                builder.setView(view1);
                builder.show();
            }
        });

    }
}