package com.example.epapp_demo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.epapp_demo.R;
import com.example.epapp_demo.model.local.database.CustomerDAO;
import com.example.epapp_demo.model.local.modul.Customer;

import java.util.ArrayList;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.ViewHolder> {

    Context context;
    ArrayList<Customer> list;
    CustomerDAO customerDAO;



    public CustomerAdapter(Context context, ArrayList<Customer> list) {
        this.context = context;
        this.list = list;
        customerDAO = new CustomerDAO(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_customer,parent,false);
        customerDAO = new CustomerDAO(context);
        return new CustomerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvIDKH.setText(list.get(position).getUserID());
        holder.tvNGaySinhKH.setText(list.get(position).getUserNgaySinh());
        holder.tvTenKH.setText(list.get(position).getUserName());
        holder.tvEmailKH.setText(list.get(position).getUserMail());
        holder.tvSDTKH.setText(list.get(position).getUserSDT());
        holder.tvDiaChiKH.setText(list.get(position).getUserDiaChi());



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tvIDKH, tvNGaySinhKH, tvTenKH, tvEmailKH, tvSDTKH,tvDiaChiKH;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIDKH = itemView.findViewById(R.id.tvIDKH);
            tvNGaySinhKH = itemView.findViewById(R.id.tvNGaySinhKH);
            tvTenKH = itemView.findViewById(R.id.tvTenKH);
            tvEmailKH = itemView.findViewById(R.id.tvEmailKH);
            tvSDTKH = itemView.findViewById(R.id.tvSDTKH);
            tvDiaChiKH = itemView.findViewById(R.id.tvDiaChiKH);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }

}
