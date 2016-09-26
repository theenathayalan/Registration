package com.zoho.task.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.zoho.task.model.UserInformation;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "registration";
    private static final String TABLE_NAME = "user_information";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_PH_NO = "phone_number";

    /**
     * Constructor for DatabaseHandler.
     */
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + KEY_NAME + " TEXT," + KEY_EMAIL + " TEXT,"
                + KEY_PASSWORD + " TEXT," + KEY_PH_NO + " TEXT" + ")";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    /**
     * This is the method for add UserInformation in registration table.
     *
     * @param userInformation userInformation
     */
    public void addUserInformation(UserInformation userInformation) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, userInformation.getName());
        values.put(KEY_EMAIL, userInformation.getEmail());
        values.put(KEY_PASSWORD, userInformation.getPassword());
        values.put(KEY_PH_NO, userInformation.getContactNumber());
        db.insert(TABLE_NAME, null, values);
    }

    /**
     * This is the method for get UserInformation from registration table.
     *
     * @param email email
     * @return UserInformation
     */
    public UserInformation getSingleUserInformation(String email) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, new String[]{
                        KEY_NAME, KEY_EMAIL, KEY_PASSWORD, KEY_PH_NO}, KEY_EMAIL + "=?",
                new String[]{email}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        UserInformation userInformation = new UserInformation(cursor.getString(0),
                cursor.getString(1), cursor.getString(2), cursor.getString(3));
        cursor.close();
        db.close();

        return userInformation;
    }

    /**
     * isEmailAlreadyExist method is used to find the email is already in the registration table.
     *
     * @param email
     * @return boolean
     */
    public boolean isEmailAlreadyExist(String email) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, new String[]{
                        KEY_NAME, KEY_EMAIL, KEY_PASSWORD, KEY_PH_NO}, KEY_EMAIL + "=?",
                new String[]{email}, null, null, null, null);
        if (cursor == null || cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    /**
     * isValidEmailAndPassword method is used to find the email and password is correct in the registration table.
     *
     * @param email
     * @param password
     * @return boolean
     */
    public boolean isValidEmailAndPassword(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, new String[]{
                        KEY_NAME, KEY_EMAIL, KEY_PASSWORD, KEY_PH_NO}, KEY_EMAIL + "=? and " + KEY_PASSWORD + " = ?",
                new String[]{email, password}, null, null, null, null);
        if (cursor == null || cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }
}
