package com.sajjadamin.greentact.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.sajjadamin.greentact.ContactActivity;
import com.sajjadamin.greentact.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DomainAdapter extends RecyclerView.Adapter<DomainAdapter.DomainViewHolder> {
    private Context context;
    private ArrayList<String> data;

    public DomainAdapter(Context context, ArrayList<String> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public DomainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DomainViewHolder(LayoutInflater.from(context).inflate(R.layout.domain_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DomainViewHolder holder, int position) {
        try {
            JSONObject jsonObject = new JSONObject(data.get(position));
            String title = jsonObject.getString("domain");
            holder.title.setText(title);
            holder.card.setOnClickListener(v -> {
                Intent intent = new Intent(context, ContactActivity.class);
                intent.putExtra("data", data.get(position));
                context.startActivity(intent);
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class DomainViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        CardView card;
        public DomainViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.domain_title);
            card = itemView.findViewById(R.id.domain_card);
        }
    }
}
