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
import com.sajjadamin.greentact.dataset.DeptData;

import java.util.ArrayList;

public class DataDeptAdapter extends RecyclerView.Adapter<DataDeptAdapter.DataDeptViewHolder>{
    Context context;
    ArrayList<DeptData> data;

    public DataDeptAdapter(Context context, ArrayList<DeptData> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public DataDeptViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DataDeptViewHolder(LayoutInflater.from(context).inflate(R.layout.data_dept_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull DataDeptViewHolder holder, int position) {
        holder.dept.setText(data.get(position).getTitle());
        holder.phone.setText(data.get(position).getPhone());
        holder.email.setText(data.get(position).getEmail());
        holder.phoneBtn.setOnClickListener(v -> {
            Intent phoneIntent = new Intent(Intent.ACTION_DIAL);
            phoneIntent.setData(Uri.parse("tel:"+data.get(position).getPhone()));
            context.startActivity(phoneIntent);
        });
        holder.emailBtn.setOnClickListener(v -> {
            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            String[] recipients = {data.get(position).getEmail()};
            emailIntent.putExtra(Intent.EXTRA_EMAIL,recipients);
            emailIntent.setType("text/html");
            emailIntent.setPackage("com.google.android.gm");
            context.startActivity(Intent.createChooser(emailIntent,"Send Email"));
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class DataDeptViewHolder extends RecyclerView.ViewHolder{
        TextView email, phone, dept;
        ImageButton emailBtn, phoneBtn;
        public DataDeptViewHolder(@NonNull View itemView) {
            super(itemView);
            dept = itemView.findViewById(R.id.dept_title);
            email = itemView.findViewById(R.id.data_email);
            phone = itemView.findViewById(R.id.data_phone);
            emailBtn = itemView.findViewById(R.id.data_email_btn);
            phoneBtn = itemView.findViewById(R.id.data_phone_btn);
        }
    }
}
