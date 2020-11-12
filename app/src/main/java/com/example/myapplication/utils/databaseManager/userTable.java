package com.example.myapplication.utils.databaseManager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.myapplication.data.model.tb_user;

import java.util.ArrayList;
import java.util.List;

public class userTable extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "db_progandro";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "tb_user";

    public userTable(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        System.loadLibrary("sqliteX");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + "userId INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "displayName TEXT,"
                + "username TEXT,"
                + "password TEXT"
                + ")";
        sqLiteDatabase.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void addRecord(tb_user user){
        SQLiteDatabase db  = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("displayName", user.getDisplayName());
        values.put("username", user.getUsername());
        values.put("password", user.getPassword());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public tb_user getUserWithUserId(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, new String[] {"userId", "displayName", "username", "password"}, "userId=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            return new tb_user(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3));
        } else {
            return null;
        }
    }

    public tb_user getUserWithUsernamePassword(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, new String[] {"userId", "displayName", "username", "password"}, "username=? AND password=?",
                new String[] {username, password}, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            return new tb_user(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3));
        } else {
            return null;
        }
    }

    public List<tb_user> getAllUser() {
        List<tb_user> contactList = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                tb_user tb_user = new tb_user();
                tb_user.setUserId(Integer.parseInt(cursor.getString(0)));
                tb_user.setDisplayName(cursor.getString(1));
                tb_user.setUsername(cursor.getString(2));
                tb_user.setPassword(cursor.getString(3));
                contactList.add(tb_user);
            } while (cursor.moveToNext());
        }

        return contactList;
    }

    public int getUserSize() {
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }

    public int updateUser(tb_user user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("displayName", user.getDisplayName());
        values.put("username", user.getUsername());
        values.put("password", user.getPassword());

        return db.update(TABLE_NAME, values, "userId = ?",
                new String[] { String.valueOf(user.getUserId()) });
    }

    public void deleteUser(tb_user user) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "userId = ?",
                new String[] { String.valueOf(user.getUserId()) });
        db.close();
    }
}
