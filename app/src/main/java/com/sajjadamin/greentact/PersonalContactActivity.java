package com.sajjadamin.greentact;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sajjadamin.greentact.adapters.PersonalContactAdapter;
import com.sajjadamin.greentact.database.PersonalContactDatabase;
import com.sajjadamin.greentact.dataset.PersonalContactData;

import java.util.ArrayList;
import java.util.Objects;

public class PersonalContactActivity extends AppCompatActivity {

    Dialog personalContactDialog;
    PersonalContactDatabase pcdb;
    ArrayList<PersonalContactData> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_contact);
        getSupportActionBar().setTitle(R.string.personal_contact);

        pcdb = new PersonalContactDatabase(this);
        data = pcdb.getPersonalContactList();
        if(data.size() == 0){
            findViewById(R.id.no_contact_tv).setVisibility(View.VISIBLE);
        }
        RecyclerView rv = findViewById(R.id.personal_contact_recyclerview);
        rv.setAdapter(new PersonalContactAdapter(this,data));
        rv.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.personal_contact_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.add_contact_menu){
            openDialog(true,new PersonalContactData());
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case 991:
                openDialog(false,data.get(item.getGroupId()));
                break;
            case 992:
                AlertDialog.Builder alb = new AlertDialog.Builder(this);
                alb.setTitle(R.string.delete_dialog_title);
                alb.setMessage(R.string.delete_dialog_message);
                alb.setPositiveButton("Yes", (dialog, which) -> {
                    dialog.dismiss();
                    int rowCount = pcdb.deleteContact(Integer.toString(data.get(item.getGroupId()).getId()));
                    if(rowCount > 0){
                        reloadActivity();
                    }
                });
                alb.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
                alb.show();
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void openDialog(Boolean isCreate, PersonalContactData data){
        personalContactDialog = new Dialog(this);
        personalContactDialog.setContentView(R.layout.add_or_update_contact_dialog);
        Button PCSaveBtn = personalContactDialog.findViewById(R.id.personal_contact_save_btn);
        EditText nameEt = personalContactDialog.findViewById(R.id.personal_contact_name_et);
        EditText phoneEt = personalContactDialog.findViewById(R.id.personal_contact_phone_et);
        EditText emailEt = personalContactDialog.findViewById(R.id.personal_contact_email_et);
        if(!isCreate){
            nameEt.setText(data.getName());
            emailEt.setText(data.getEmail());
            phoneEt.setText(data.getPhone());
            PCSaveBtn.setText(R.string.update);
        }
        PCSaveBtn.setOnClickListener(v -> {
            String id = Integer.toString(data.getId());
            String name = nameEt.getText().toString();
            String phone = phoneEt.getText().toString();
            String email = emailEt.getText().toString();
            boolean canExecute = !name.equals("") && !phone.equals("") && phone.length() >= 11;
            if(isCreate){
                if(canExecute){
                    long rowId = pcdb.createContact(name,phone,email);
                    if(rowId > 0){
                        personalContactDialog.dismiss();
                        reloadActivity();
                    }
                } else {
                    Toast.makeText(this,R.string.action_error_warning,Toast.LENGTH_SHORT).show();
                }
            } else {
                if(canExecute){
                    int rowEffected = pcdb.updateContact(id,name,phone,email);
                    if(rowEffected > 0){
                        personalContactDialog.dismiss();
                        reloadActivity();
                    }
                } else {
                    Toast.makeText(this,R.string.action_error_warning,Toast.LENGTH_SHORT).show();
                }
            }
        });
        personalContactDialog.show();
    }

    private void reloadActivity() {
        finish();
        startActivity(new Intent(PersonalContactActivity.this,PersonalContactActivity.class));
    }
}