package com.example.epapp_demo.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.epapp_demo.R;
import com.example.epapp_demo.model.local.database.FoodDAO;
import com.example.epapp_demo.model.local.database.CategoriesDAO;
import com.example.epapp_demo.model.local.modul.Food;
import com.example.epapp_demo.model.local.modul.Categories;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {

    List<Food> list;
    Context context;
    FoodDAO foodDAO;
    DecimalFormat formatter = new DecimalFormat("###,###,###");
    DatabaseReference mData = FirebaseDatabase.getInstance().getReference("MonAn");
    public FoodAdapter(List<Food> list, Context context){
        this.list = list;
        this.context = context;
        foodDAO = new FoodDAO(context);
    }

    private FoodAdapter.OnClickListener mListener;

    public void setOnItemClickListener (FoodAdapter.OnClickListener onItemClickListener){
        mListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_food,parent,false);
        foodDAO = new FoodDAO(context);
        return new FoodAdapter.ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        Food food = list.get(position);
        if (null != food.getHinhAnhMonAn() && !TextUtils.isEmpty(food.getHinhAnhMonAn())) {
            Picasso.get().load(food.getHinhAnhMonAn()).into(holder.anh_MA);
        } else {
            Picasso.get().load(R.drawable.logo).into(holder.anh_MA);
        }
        holder.ten_Ma.setText(list.get(position).getNameMonAn());
        holder.gia_MA.setText(formatter.format(list.get(position).getGiaMonAn())+" VND");
        holder.moTa_MA.setText(list.get(position).getMoTa());

        final FoodDAO foodDAO = new FoodDAO(context);

        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final ArrayList<Categories> listPL = new CategoriesDAO(context).getAllspn();
        //delete
        holder.item_mon_an.setOnLongClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("THÔNG BÁO!");
            builder.setMessage("Bạn có muốn xóa không?");

            //btn Yes
            builder.setNegativeButton("Yes", (dialog, which) -> {

                String id = list.get(position).getMonAnID();
                foodDAO.delete(id);

                notifyDataSetChanged();
                dialog.dismiss();
            });

            //btn No
            builder.setPositiveButton("No", (dialog, which) -> dialog.dismiss());

            AlertDialog myAlert = builder.create();
            myAlert.show();
            return false;
        });

        // edit
        holder.item_mon_an.setOnClickListener(view -> {
            final AlertDialog.Builder builder = new AlertDialog.Builder(context);
            View view1 = LayoutInflater.from(context).inflate(R.layout.add_food_dialog,null);

            final EditText tenmon = view1.findViewById(R.id.edtTenMon);
            final Spinner spn = view1.findViewById(R.id.spnTheLoai);
            final EditText mota = view1.findViewById(R.id.edtMotaMon);
            final EditText gia = view1.findViewById(R.id.edtGiaMon);
            final EditText url = view1.findViewById(R.id.edtUrlMon);

            Food ma = list.get(position);
            tenmon.setText(ma.getNameMonAn());
            mota.setText(ma.getMoTa());
            gia.setText(String.valueOf(ma.getGiaMonAn()));
            url.setText(ma.getHinhAnhMonAn());

            //Test
            ArrayAdapter adapter = new ArrayAdapter (context, android.R.layout.simple_spinner_item, listPL);
            spn.setAdapter(adapter);

            int idxLS = -1;
            for (int i = 0; i < listPL.size(); i++){
                if(listPL.get(i).getLoaiID().toString().equalsIgnoreCase(ma.getPhanLoaiID())){
                    idxLS = i;
                    break;
                }
            }
            spn.setSelection(idxLS);

            builder.setView(view1);
            builder.setPositiveButton("Sửa", (dialogInterface, i) -> {

                String tenmon1 = tenmon.getText().toString();
                String mota1 = mota.getText().toString();
                int gia1 = Integer.parseInt(gia.getText().toString());
                String url1 = url.getText().toString();
                Categories loai = (Categories) spn.getSelectedItem();
                String matheloai = loai.getLoaiID();
                String idMonAn = list.get(position).getMonAnID();
                String a = mAuth.getCurrentUser().getUid();
                String nameStore=mAuth.getCurrentUser().getDisplayName();
                Log.d("StoreName","NAME: "+nameStore);
                Food s = new Food(idMonAn,tenmon1,gia1,url1,a,matheloai,mota1);
                foodDAO.update(s, idMonAn);
            }).setNegativeButton("Hủy", (dialog, which) -> {

            });
            builder.setView(view1);
            builder.show();
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView ten_Ma, moTa_MA, gia_MA;
        public ImageView anh_MA;
        LinearLayout item_mon_an;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ten_Ma = itemView.findViewById(R.id.item_ten_MA);
            moTa_MA = itemView.findViewById(R.id.item_moTa_MA);
            gia_MA = itemView.findViewById(R.id.item_gia_MA);
            anh_MA = itemView.findViewById(R.id.anh_MA);
            item_mon_an = itemView.findViewById(R.id.item_mon_an);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }

    public interface OnClickListener {
        void onItemClick(int position);
    }
}
