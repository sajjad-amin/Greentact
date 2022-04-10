package com.sajjadamin.greentact;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.sajjadamin.greentact.adapters.DomainAdapter;
import com.sajjadamin.greentact.helper.DataHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView rv = findViewById(R.id.domain_recycler_view);
        ArrayList<String> data = new ArrayList<>();

        DataHelper dh = new DataHelper(this);
        String json = dh.getJson();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                data.add(jsonObject.toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        rv.setAdapter(new DomainAdapter(this,data));
        rv.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_titlebar_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.personal_contact:
                startActivity(new Intent(MainActivity.this,PersonalContactActivity.class));
                break;
            case R.id.about:
                showAboutInfo();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showAboutInfo() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.about_layout);
        dialog.findViewById(R.id.share_app).setOnClickListener(v -> {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            String shareBody = "https://play.google.com/store/apps/details?id=com.sajjadamin.greentact";
            shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(shareIntent, "Share Greentect via"));
        });
        dialog.findViewById(R.id.facebook).setOnClickListener(v -> {
            try {
                Intent fbIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://profile/100004603357087"));
                startActivity(fbIntent);
            } catch(Exception e) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/sajjad.amin.100")));
            }
            dialog.dismiss();
        });
        dialog.findViewById(R.id.website).setOnClickListener(v -> {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.sajjadamin.com/")));
            dialog.dismiss();
        });
        dialog.show();
    }
}