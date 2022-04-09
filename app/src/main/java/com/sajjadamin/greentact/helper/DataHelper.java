package com.sajjadamin.greentact.helper;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

public class DataHelper {
    Context context;
    public DataHelper(Context context) {
        this.context = context;
    }

    public String getJson(){
        StringBuilder json = new StringBuilder();
        try {
            InputStream is = context.getAssets().open("contact-list.json");
            byte[] data = new byte[is.available()];
            is.read(data);
            is.close();
            json.append(new String(data));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json.toString();
    }
}
