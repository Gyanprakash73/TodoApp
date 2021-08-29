package com.gyan.todoapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.gyan.todoapp.Model.User;

import java.util.ArrayList;
import java.util.List;

public class SqLiteHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="User_Database";
    public static final int DATABASE_VERSION=1;
    public static final String TABLE_USERS="User";
    public static final String USER_DATA="data";

    public static final String SQL_TABLE_USERS = "CREATE TABLE "+TABLE_USERS+"("+USER_DATA+" TEXT )";

    private String SQL_TABLE_DROP = "DROP TABLE IF EXISTS "+ TABLE_USERS;

    public SqLiteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_TABLE_USERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_TABLE_DROP);
        onCreate(db);
    }

    //Data insert into Database
    public void addData(User user) {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(USER_DATA,user.getData());
        db.insert(TABLE_USERS,null,values);
        db.close();
    }

    //Data get from database
    public List<User> getData(){
        SQLiteDatabase db=this.getWritableDatabase();
        ArrayList<User> userList = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ TABLE_USERS,null);
        while (cursor.moveToNext()){
            User user = new User();
            user.setData(cursor.getString(cursor.getColumnIndex(USER_DATA)));
            userList.add(user);
        }
        return  userList;
    }

}
