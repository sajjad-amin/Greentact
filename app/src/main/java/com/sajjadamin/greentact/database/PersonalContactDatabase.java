package com.sajjadamin.greentact.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.sajjadamin.greentact.dataset.PersonalContactData;

import java.util.ArrayList;
import java.util.Collections;

public class PersonalContactDatabase extends SQLiteOpenHelper {

    Context context;
    SQLiteDatabase sdb;
    String sql_db_create = "CREATE TABLE personal_contact(id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT,phone TEXT,email TEXT)";
    String sql_db_get_all = "SELECT * FROM personal_contact";

    public PersonalContactDatabase(Context context) {
        super(context, "greentact.db", null, 1);
        this.context = context;
        this.sdb = this.getWritableDatabase();
    }

    public long createContact(String name, String phone, String email) {
        ContentValues cv = new ContentValues();
        cv.put("name",name);
        cv.put("phone",phone);
        cv.put("email",email);
        return sdb.insert("personal_contact",null,cv);
    }

    public ArrayList<PersonalContactData> getPersonalContactList(){
        ArrayList<PersonalContactData> list = new ArrayList<>();
        Cursor cursor = sdb.rawQuery(sql_db_get_all,null);
        while(cursor.moveToNext()){
            PersonalContactData pcd = new PersonalContactData();
            pcd.setId(Integer.parseInt(cursor.getString(0)));
            pcd.setName(cursor.getString(1));
            pcd.setPhone(cursor.getString(2));
            pcd.setEmail(cursor.getString(3));
            list.add(pcd);
        }
        Collections.reverse(list);
        return list;
    }

    public int updateContact(String id, String name, String phone, String email) {
        ContentValues cv = new ContentValues();
        cv.put("id", id);
        cv.put("name",name);
        cv.put("phone",phone);
        cv.put("email",email);
        return sdb.update("personal_contact",cv,"id = ?",new String[]{id});
    }

    public int deleteContact(String id){
        return sdb.delete("personal_contact","id = ?", new String[]{id});
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
            db.execSQL(sql_db_create);
        } catch (SQLException e) {
            Toast.makeText(context,"Exception: "+e, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
