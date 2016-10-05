package com.example.justine.taragala;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Justine on 10/3/2016.
 */
public class LoginCRUD {
    static final String DATABASE_NAME = "login.db";
    static final int DATABASE_VERSION = 1;
    public static final int NAME_COLUMN = 1;
    // TODO: Create public field for each column in your table.
    // SQL Statement to create a new database.
    static final String DATABASE_CREATE = "create table " + "LOGIN" +
            "( " + "ID" + " integer primary key autoincrement," + "USERNAME  text,PASSWORD text, EMAIL text, FIRSTNAME text, LASTNAME text); ";
    // Variable to hold the database instance
    public SQLiteDatabase db;
    // Context of the application using the database.
    private final Context context;
    // Database open/upgrade helper
    private DBHelp dbHelper;
    public LoginCRUD(Context _context) {
        context = _context;
        dbHelper = new DBHelp(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public LoginCRUD open() throws SQLException {
        db = dbHelper.getWritableDatabase();
        return this;
    }
    public void close() {
        db.close();
    }
    public SQLiteDatabase getDatabaseInstance() {
        return db;
    }
    public void insertEntry(String userName, String password, String email, String firstname, String lastname) {
        ContentValues newValues = new ContentValues();
        // Assign values for each row.
        newValues.put("USERNAME", userName);
        newValues.put("PASSWORD", password);
        newValues.put("EMAIL", email);
        newValues.put("FIRSTNAME", firstname);
        newValues.put("LASTNAME", lastname);
        // Insert the row into your table
        db.insert("LOGIN", null, newValues);
    }
    public int deleteEntry(String UserName) {
        //String id=String.valueOf(ID);
        String where = "USERNAME=?";
        int numberOFEntriesDeleted = db.delete("LOGIN", where, new String[]{UserName});
        // Toast.makeText(context, "Number fo Entry Deleted Successfully : "+numberOFEntriesDeleted, Toast.LENGTH_LONG).show();
        return numberOFEntriesDeleted;
    }
    public String getSinlgeEntry(String userName) {
        Cursor cursor = db.query("LOGIN", null, " USERNAME=?", new String[]{userName}, null, null, null);

        Log.d(String.valueOf(this),"username =" + userName);

        if (cursor.getCount() < 1) // UserName Not Exist
        {
            Log.d(String.valueOf(this),"email entry");
            cursor.close();

            Cursor cursor1 = db.query("LOGIN", null, "Email=?", new String[]{userName}, null, null, null);
            if (cursor1.getCount() < 1) // UserName Not Exist
            {
                Log.d(String.valueOf(this),"email fail");
                cursor1.close();
                return "Not Exist";
            }
            Log.d(String.valueOf(this),"email =" + userName);
            cursor1.moveToFirst();
            String password1 = cursor1.getString(cursor1.getColumnIndex("PASSWORD"));
            return password1;
        }
        cursor.moveToFirst();
        String password = cursor.getString(cursor.getColumnIndex("PASSWORD"));
        cursor.close();
        return password;
    }
    public boolean getUsernameEntry(String userName) {
        Cursor cursor = db.query("LOGIN", null, " USERNAME=?", new String[]{userName}, null, null, null);
        if (cursor.getCount() < 1) // UserName Not Exist
        {
            cursor.close();
            return false;
        }
        else {
            return true;
        }
    }
    public boolean getEmailEntry(String email) {
        Cursor cursor = db.query("LOGIN", null, " EMAIL=?", new String[]{email}, null, null, null);
        if (cursor.getCount() < 1) // UserName Not Exist
        {
            cursor.close();
            return false;
        }
        else {
            return true;
        }
    }
    public void updateEntry(String userName, String password) {
        // Define the updated row content.
        ContentValues updatedValues = new ContentValues();
        // Assign values for each row.
        updatedValues.put("USERNAME", userName);
        updatedValues.put("PASSWORD", password);

        String where = "USERNAME = ?";
        db.update("LOGIN", updatedValues, where, new String[]{userName});
    }
}
