package com.sajjadamin.greentact.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sajjadamin.greentact.R;
import com.sajjadamin.greentact.dataset.PersonalContactData;

import java.util.ArrayList;

public class PersonalContactAdapter extends RecyclerView.Adapter<PersonalContactAdapter.PersonalContactViewHolder> {
    Context context;
    ArrayList<PersonalContactData> data;

    public PersonalContactAdapter(Context context, ArrayList<PersonalContactData> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public PersonalContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PersonalContactViewHolder(LayoutInflater.from(context).inflate(R.layout.personal_contact_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull PersonalContactViewHolder holder, int position) {
        int id = data.get(position).getId();
        String name = data.get(position).getName();
        String email = data.get(position).getEmail();
        String phone = data.get(position).getPhone();
        holder.name.setText(name);
        holder.email.setText(email);
        holder.phone.setText(phone);
        holder.phoneBtn.setOnClickListener(v -> {
            Intent phoneIntent = new Intent(Intent.ACTION_DIAL);
            phoneIntent.setData(Uri.parse("tel:"+phone));
            context.startActivity(phoneIntent);
        });
        holder.emailBtn.setOnClickListener(v -> {
            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            String[] recipients = {email};
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

    static class PersonalContactViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
        TextView name, email, phone;
        ImageButton emailBtn, phoneBtn;
        public PersonalContactViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.personal_contact_name);
            email = itemView.findViewById(R.id.personal_contact_email);
            phone = itemView.findViewById(R.id.personal_contact_phone);
            emailBtn = itemView.findViewById(R.id.personal_contact_email_btn);
            phoneBtn = itemView.findViewById(R.id.personal_contact_phone_btn);
            itemView.setOnCreateContextMenuListener(this);
        }
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(getAdapterPosition(),991,0,"Edit");
            menu.add(getAdapterPosition(),992,1,"Delete");
        }
    }
}
