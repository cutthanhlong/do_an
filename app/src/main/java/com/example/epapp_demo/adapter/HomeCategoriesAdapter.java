package com.example.epapp_demo.adapter;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import java.util.List;

public class HomeCategoriesAdapter extends RecyclerView.Adapter<HomeCategoriesAdapter.ViewHolder> {

    List<Categories> list;
    Context context;
    CategoriesDAO categoriesDAO;
    DatabaseReference mData = FirebaseDatabase.getInstance().getReference();

    private OnItemClickListener mListener;
    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public HomeCategoriesAdapter(List<Categories> list, Context context) {
        this.list = list;
        this.context = context;
        categoriesDAO = new CategoriesDAO(context);
    }


    @NonNull
    @Override
    public HomeCategoriesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_one_categories, parent, false);
        categoriesDAO = new CategoriesDAO(context);
        return new HomeCategoriesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeCategoriesAdapter.ViewHolder holder, int position) {

        if(TextUtils.isEmpty(list.get(position).getHinhanh())) {
            // Load default image
            holder.iv.setImageResource(R.drawable.slider3);
        } else {
            Picasso.get().load(list.get(position).getHinhanh()).into(holder.iv);
        }


        holder.name.setText(list.get(position).getNameLoai());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView name;
        public ImageView iv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.category_name);
            iv = itemView.findViewById(R.id.category_photo);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onItemClick(getPosition());

        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }


}
