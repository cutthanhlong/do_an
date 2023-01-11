package com.example.epapp_demo.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.epapp_demo.R;
import com.example.epapp_demo.model.local.database.CategoriesDAO;
import com.example.epapp_demo.model.local.modul.Categories;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {

    Context context;
    ArrayList<Categories> phanloai;
    CategoriesDAO categoriesDAO;

    public CategoriesAdapter(ArrayList<Categories> phanloai, Context context){
        this.phanloai =phanloai;
        this.context = context;
        categoriesDAO = new CategoriesDAO(context);
    }

    public CategoriesAdapter(Context context) {
    }

    @NonNull
    @Override
    public CategoriesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_categories,parent,false);
        categoriesDAO = new CategoriesDAO(context);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesAdapter.ViewHolder holder, int position) {

        holder.tvidloai.setText(phanloai.get(position).getLoaiID());
        holder.tvnameloai.setText(phanloai.get(position).getNameLoai());
        holder.tvmota.setText(phanloai.get(position).getMota());
        Categories categories = phanloai.get(position);
        if (null != categories.getHinhanh() && !TextUtils.isEmpty(categories.getHinhanh())) {
            Picasso.get().load(categories.getHinhanh()).into(holder.ivPhanLoaiPicture);
        } else {
            Picasso.get().load(R.drawable.logo).into(holder.ivPhanLoaiPicture);
        }

    }

    @Override
    public int getItemCount() {
        return phanloai.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView tvidloai, tvnameloai, tvmota;
        ImageView ivPhanLoaiPicture;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvidloai = itemView.findViewById(R.id.tvLoaiid);
            tvnameloai = itemView.findViewById(R.id.Nameloai);
            tvmota = itemView.findViewById(R.id.tvMota);
            ivPhanLoaiPicture = itemView.findViewById(R.id.ivPhanLoaiPicture);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            final int position = getLayoutPosition();
            if (getAdapterPosition() == RecyclerView.NO_POSITION) return;
            final Categories gd =phanloai.get(position);

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            View view1 = layoutInflater.inflate(R.layout.edit_category,null);
            final TextView ten = view1.findViewById(R.id.edtEditLoai);
            final TextView mota = view1.findViewById(R.id.edtEditMota);
            final TextView anh = view1.findViewById(R.id.edtEditUrl);
            ten.setText(gd.getNameLoai());
            mota.setText(gd.getMota());


            builder.setPositiveButton("Sửa", (dialogInterface, i) -> {
                final String ten1 = ten.getText().toString();
                final String mota1 = mota.getText().toString();
                final String anh1 = anh.getText().toString();
                Categories s = new Categories(gd.getLoaiID(),ten1,mota1,anh1);
                categoriesDAO.update(s);
            });
            builder.setNegativeButton("Hủy", (dialog, which) -> {

            });
            builder.setView(view1);
            builder.show();
        }
        @Override
        public boolean onLongClick(View view) {
            final int position = getLayoutPosition();
            if (getAdapterPosition() == RecyclerView.NO_POSITION);
            final Categories gd =phanloai.get(position);

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            View view1 = layoutInflater.inflate(R.layout.delete_alert_dialog,null);


            builder.setPositiveButton("Có", (dialogInterface, i) -> {

                Categories s = new Categories(gd.getLoaiID(),gd.getNameLoai(),gd.getMota(),gd.getHinhanh());
                categoriesDAO.delete(s);
            });
            builder.setNegativeButton("Không", (dialog, which) -> {

            });
            builder.setView(view1);
            builder.show();

            return true;
        }
    }

}
