package com.nodexsolutions.bankingapplication.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nodexsolutions.bankingapplication.Databases.PrefManager;
import com.nodexsolutions.bankingapplication.MainActivity;
import com.nodexsolutions.bankingapplication.ModelClasses.CashPojo;
import com.nodexsolutions.bankingapplication.R;

import java.text.DecimalFormat;
import java.util.List;

public class CashAdapter extends RecyclerView.Adapter<CashAdapter.MyViewHolder> {

    private Context context;
    private List<CashPojo> data;
    private PrefManager prefManager;
    private CashAdapter.onItemClick onItemClick;

    public CashAdapter(Context context, List<CashPojo> data, CashAdapter.onItemClick onItemClick) {
        this.context = context;
        this.data = data;
        prefManager = new PrefManager(context);
        this.onItemClick = onItemClick;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.cash_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final CashPojo pojo = data.get(position);
        holder.name.setText(pojo.getName());
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        float num = Float.parseFloat(pojo.getAmount());

        holder.amount.setText("INR "+formatter.format(num));
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.onClick(pojo);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name,amount;
        LinearLayout root;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name  = itemView.findViewById(R.id.name);
            amount = itemView.findViewById(R.id.amount);
            root = itemView.findViewById(R.id.root);
        }
    }

    public interface onItemClick{
        void onClick(CashPojo pojo);
    }
}
