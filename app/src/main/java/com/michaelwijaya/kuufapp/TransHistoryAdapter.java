package com.michaelwijaya.kuufapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;

public class TransHistoryAdapter extends RecyclerView.Adapter<TransHistoryAdapter.TransHistoryViewHolder> {
    public ArrayList<TransactionHistory> transHistoryList;
    public TransHistoryAdapter(ArrayList<TransactionHistory> transHistoryData){
        transHistoryList = transHistoryData;
    }

    public static class TransHistoryViewHolder extends RecyclerView.ViewHolder{
        public CardView cvTransHistory;
        public TextView tvProductName;
        public TextView tvTransDate;
        public TextView tvProductPrice;
        public Button btnDeleteHistory;

        public TransHistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            cvTransHistory = itemView.findViewById(R.id.cv_trans_history);
            tvProductName = itemView.findViewById(R.id.tv_product_name);
            tvTransDate = itemView.findViewById(R.id.tv_trans_date);
            tvProductPrice = itemView.findViewById(R.id.tv_product_price);
            btnDeleteHistory = itemView.findViewById(R.id.btn_delete_trans);
        }
    }

    @NonNull
    @Override
    public TransHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View transHistoryView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false);
        return new TransHistoryViewHolder(transHistoryView);
    }

    @Override
    public void onBindViewHolder(@NonNull TransHistoryViewHolder holder, int position) {
        TransactionHistory transHistory = transHistoryList.get(position);

        holder.tvProductName.setText(transHistory.getName());
        holder.tvTransDate.setText(transHistory.getDate());
        holder.tvProductPrice.setText("Rp" + transHistory.getPrice());

        holder.btnDeleteHistory.setOnClickListener(v -> {
            transHistoryList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, getItemCount());

            SharedPreferences sp = v.getContext().getApplicationContext().getSharedPreferences("user", 0);
            SharedPreferences.Editor editor = sp.edit();

            Gson gson = new Gson();
            String jsonSave = gson.toJson(transHistoryList);
            editor.remove("transHistory");
            editor.putString("transHistory", jsonSave);
            editor.apply();

            v.getContext().startActivity(new Intent(v.getContext(), MainActivity.class));
        });
    }

    @Override
    public int getItemCount() {
        return transHistoryList.size();
    }
}
