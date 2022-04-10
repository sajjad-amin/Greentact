package com.sajjadamin.greentact.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sajjadamin.greentact.R;

import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.DataViewHolder> {
    Context context;
    ArrayList<String> data;

    public DataAdapter(Context context, ArrayList<String> data) {
        this.context = context;
        this.data = data;
    }
    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DataViewHolder(LayoutInflater.from(context).inflate(R.layout.data_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {
        holder.phone.setText(data.get(position));
        holder.phoneBtn.setOnClickListener(v -> {
            Intent phoneIntent = new Intent(Intent.ACTION_DIAL);
            phoneIntent.setData(Uri.parse("tel:"+data.get(position)));
            context.startActivity(phoneIntent);
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class DataViewHolder extends RecyclerView.ViewHolder{
        TextView phone;
        ImageButton phoneBtn;
        public DataViewHolder(@NonNull View itemView) {
            super(itemView);
            phone = itemView.findViewById(R.id.data_phone);
            phoneBtn = itemView.findViewById(R.id.data_phone_btn);
        }
    }
}
