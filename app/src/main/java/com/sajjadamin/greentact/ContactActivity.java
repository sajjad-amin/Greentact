package com.sajjadamin.greentact;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sajjadamin.greentact.adapters.DataAdapter;
import com.sajjadamin.greentact.adapters.DataDeptAdapter;
import com.sajjadamin.greentact.helper.DeptData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ContactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        try {
            String data = getIntent().getExtras().getString("data");
            String t = new JSONObject(data).getString("domain");
            String title = t.equals("") ? getSupportActionBar().getTitle().toString() : t;
            getSupportActionBar().setTitle(title);
            //
            JSONObject dataObject = new JSONObject(data);

            if(title.equals("Departmental Queries")){
                LinearLayout commonDataLayout = findViewById(R.id.common_data_layout);
                commonDataLayout.setVisibility(View.GONE);
                RecyclerView ddrv = findViewById(R.id.dept_recycler_view);
                ddrv.setVisibility(View.VISIBLE);
                // Preparing data
                ArrayList<DeptData> deptList = new ArrayList<>();
                JSONArray deptArr = dataObject.getJSONArray("dept");
                for(int i = 0; i < deptArr.length(); i++){
                    JSONObject obj = deptArr.getJSONObject(i);
                    String dept = obj.getString("domain");
                    String phone = new JSONArray(obj.getString("mobile")).get(0).toString();
                    String email = obj.getString("email");
                    DeptData dd = new DeptData();
                    dd.setTitle(dept);
                    dd.setPhone(phone);
                    dd.setEmail(email);
                    deptList.add(dd);
                }
                ddrv.setLayoutManager(new LinearLayoutManager(this));
                ddrv.setAdapter(new DataDeptAdapter(this,deptList));
            } else {
                // Set phone recycler view
                RecyclerView drv = findViewById(R.id.data_recycler_view);
                ArrayList<String> phoneList = new ArrayList<>();
                // Preparing data
                JSONArray phone = new JSONArray(dataObject.getString("mobile"));
                for(int i = 0; i < phone.length(); i++){
                    phoneList.add(phone.getString(i));
                }
                drv.setLayoutManager(new LinearLayoutManager(this));
                drv.setAdapter(new DataAdapter(this, phoneList));
                // Set email data
                TextView emailTv = findViewById(R.id.data_email);
                ImageButton emailBtn = findViewById(R.id.data_email_btn);
                String email = dataObject.getString("email");
                emailTv.setText(email);
                emailBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent emailIntent = new Intent(Intent.ACTION_SEND);
                        String[] recipients = {email};
                        emailIntent.putExtra(Intent.EXTRA_EMAIL,recipients);
                        emailIntent.setType("text/html");
                        emailIntent.setPackage("com.google.android.gm");
                        startActivity(Intent.createChooser(emailIntent,"Send Email"));
                    }
                });
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}