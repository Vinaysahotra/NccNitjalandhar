package com.example.nccnitjalandhar;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class information_storage extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "login_info1.db";
    public static final String TABLE_NAME = "LOGIN";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "email";
    public static final String COL_3 = "username";
    public static final String COL_4 = "password";


    public information_storage(Context context) {
        super(context,TABLE_NAME, null,1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    db.execSQL("CREATE TABLE LOGIN  (ID INTEGER PRIMARY KEY AUTOINCREMENT, email TEXT, username TEXT, password TEXT)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("DROP TABLE IF EXISTS LOGIN");
        onCreate(db);

    }

    public boolean adduser(String email, String user, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_2,email);
        values.put(COL_3,user);

        values.put(COL_4,password);

        long row = db.insert(TABLE_NAME, null, values);
db.close();

        if(row==-1){
            return false;
        }
else {
            return true;
        }

    }
    public  Cursor getdata(){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res=db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }





}
