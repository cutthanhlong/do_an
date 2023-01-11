package com.example.epapp_demo.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.epapp_demo.R;
import com.example.epapp_demo.model.local.database.CategoriesDAO;
import com.example.epapp_demo.model.local.database.FoodDAO;
import com.example.epapp_demo.model.local.modul.Categories;
import com.example.epapp_demo.model.local.modul.Food;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ShowFoodAdapter extends RecyclerView.Adapter<ShowFoodAdapter.ViewHolder> {
    List<Food> list;
    Context context;
    FoodDAO foodDAO;
    DecimalFormat formatter = new DecimalFormat("###,###,###");
    DatabaseReference mData = FirebaseDatabase.getInstance().getReference("MonAn");
    private ViewHolder holder;
    private int position;

    public ShowFoodAdapter(List<Food> list, Context context) {
        this.list = list;
        this.context = context;
        foodDAO = new FoodDAO(context);
    }

//    private FoodAdapter.OnClickListener mListener;
//
//    public void setOnItemClickListener (FoodAdapter.OnClickListener onItemClickListener){
//        mListener = onItemClickListener;
//    }

    @NonNull
    @Override
    public ShowFoodAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_food, parent, false);
        foodDAO = new FoodDAO(context);
        return new ShowFoodAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowFoodAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        this.holder = holder;
        this.position = position;
        Food food = list.get(position);
        if (null != food.getHinhAnhMonAn() && !TextUtils.isEmpty(food.getHinhAnhMonAn())) {
            Picasso.get().load(food.getHinhAnhMonAn()).into(holder.anh_MA);
        } else {
            Picasso.get().load(R.drawable.logo).into(holder.anh_MA);
        }

        holder.ten_Ma.setText(food.getNameMonAn());
        holder.gia_MA.setText(formatter.format(food.getGiaMonAn()) + " VND");
        holder.moTa_MA.setText(food.getMoTa());

        final FoodDAO foodDAO = new FoodDAO(context);

        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final ArrayList<Categories> listPL = new CategoriesDAO(context).getAllspn();

        //delete
        holder.item_mon_an.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
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
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
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
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    mListener.onItemClick(getAdapterPosition());
//                }
//            });
        }
    }
//    public interface OnClickListener {
//        void onItemClick(int position);
//    }
}
