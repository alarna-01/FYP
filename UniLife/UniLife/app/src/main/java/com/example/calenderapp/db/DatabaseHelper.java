package com.example.calenderapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.calenderapp.model.Module;
import com.example.calenderapp.model.UsersModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "calenderApp";
    private static final String TABLE_USERS = "users";
    private static final String TABLE_MODULES = "modules";
    private static final String KEY_TITLE = "title";
    private static final String KEY_DATE = "date";
    private static final String KEY_TYPE = "type";
    private static final String USER_ID = "userid";
    private static final String KEY_ID = "id";
    private static final String KEY_PICTURE = "picture";
    private static final String KEY_FIRST_NAME = "fName";
    private static final String KEY_LAST_NAME = "lName";
    private static final String KEY_COURSE_TITLE = "title";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_YEAR = "year";
    private static final String KEY_PASSWORD = "password";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        //UsersTable
        db.execSQL(" CREATE TABLE " + TABLE_USERS + " (" +
                KEY_ID + " INTEGER PRIMARY KEY, " +
                KEY_PICTURE + " TEXT NOT NULL, " +
                KEY_FIRST_NAME + " TEXT NOT NULL, " +
                KEY_LAST_NAME + " TEXT NOT NULL, " +
                KEY_COURSE_TITLE + " TEXT NOT NULL, " +
                KEY_EMAIL + " TEXT NOT NULL, " +
                KEY_YEAR + " TEXT NOT NULL, " +
                KEY_PASSWORD + " TEXT NOT NULL);"
        );
        db.execSQL(" CREATE TABLE " + TABLE_MODULES + " (" +
                KEY_ID + " INTEGER PRIMARY KEY, " +
                USER_ID + " INTEGER NOT NULL, " +
                KEY_DATE + " INTEGER NOT NULL, " +
                KEY_TITLE + " TEXT NOT NULL, " +
                KEY_TYPE + " TEXT NOT NULL);"
        );

    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MODULES);

        // Create tables again
        onCreate(db);
    }

    // code to add the new user
    public void register(UsersModel users) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PICTURE, users.getImageUri());
        values.put(KEY_FIRST_NAME, users.getfName());
        values.put(KEY_LAST_NAME, users.getlName());
        values.put(KEY_COURSE_TITLE, users.getCourseTitle());
        values.put(KEY_EMAIL, users.getEmail());
        values.put(KEY_YEAR, users.getYear());
        values.put(KEY_PASSWORD, users.getPassword());

        // Inserting Row
        db.insert(TABLE_USERS, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    public void addModule(Module module) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(USER_ID, module.getUserid());
        values.put(KEY_DATE, module.getTime());
        values.put(KEY_TITLE, module.getName());
        values.put(KEY_TYPE, module.getType());

        // Inserting Row
        db.insert(TABLE_MODULES, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }


    // code to get all users in a list view
    public List<UsersModel> getAllUsers() {
        List<UsersModel> usersList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_USERS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                UsersModel users = new UsersModel();
                users.setUserId(Integer.parseInt(cursor.getString(0)));
                users.setImageUri(cursor.getString(1));
                users.setfName(cursor.getString(2));
                users.setlName(cursor.getString(3));
                users.setCourseTitle(cursor.getString(4));
                users.setEmail(cursor.getString(5));
                users.setYear(cursor.getString(6));
                users.setPassword(cursor.getString(7));

                // Adding contact to list
                usersList.add(users);
            } while (cursor.moveToNext());
        }

        // return contact list
        return usersList;
    }

    public List<Module> getModule(String id) {
        List<Module> modules = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_MODULES;

        SQLiteDatabase db = this.getWritableDatabase();
      //  Cursor cursor = db.rawQuery(selectQuery, null);
        Cursor cursor = db.query(TABLE_MODULES, null, USER_ID + "=?", new String[]{id}, null, null, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Module module = new Module();
                module.setId(Long.parseLong(cursor.getString(0)));
                module.setUserid(Long.parseLong(cursor.getString(1)));
                module.setTime(Long.parseLong(cursor.getString(2)));
                module.setName(cursor.getString(3));
                module.setType(cursor.getString(4));

                // Adding contact to list
                modules.add(module);
            } while (cursor.moveToNext());
        }

        // return contact list
        return modules;
    }


    // code to update the single user
    public int updateUser(UsersModel user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, user.getUserId());
        values.put(KEY_PICTURE, user.getImageUri());
        values.put(KEY_FIRST_NAME, user.getfName());
        values.put(KEY_LAST_NAME, user.getlName());
        values.put(KEY_COURSE_TITLE, user.getCourseTitle());
        values.put(KEY_EMAIL, user.getEmail());
        values.put(KEY_YEAR, user.getYear());
        values.put(KEY_PASSWORD, user.getPassword());

        // updating row
        return db.update(TABLE_USERS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(user.getUserId())});
    }

    // Deleting single user
    public void deleteUser(UsersModel user) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USERS, KEY_ID + " = ?",
                new String[]{String.valueOf(user.getUserId())});
        db.close();
    }
}