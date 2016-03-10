package com.example.susie.invite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by susie on 3/3/2016.
 */
public class DBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "invite.db";

    public static final String TABLE_HOST = "host";
    public static final String COLUMN_HOST_ID = "_id";
    public static final String COLUMN_HOST_NAME = "host_name";
    public static final String COLUMN_HOST_EMAIL = "host_email";

    public static final String TABLE_MEETING = "meeting";
    public static final String COLUMN_MEETING_ID = "_id";
    public static final String COLUMN_MEETING_NAME = "meeting_name";
    public static final String COLUMN_MEETING_LOCATION = "meeting_location";
    public static final String COLUMN_MEETING_DATE = "meeting_date";
    public static final String COLUMN_MEETING_HOST_ID = "host_id";

    public DBHandler (Context context, SQLiteDatabase.CursorFactory factory){
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE " + TABLE_HOST + "(" +
                COLUMN_HOST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_HOST_NAME + " TEXT," +
                COLUMN_HOST_EMAIL + " TEXT" +
                ");";
        db.execSQL(query);

        // Second database created for the meeting
        String query2 = "CREATE TABLE " + TABLE_MEETING + "(" +
                COLUMN_MEETING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_MEETING_NAME + " TEXT," +
                COLUMN_MEETING_LOCATION + " TEXT," +
                COLUMN_MEETING_DATE + " TEXT," +
                COLUMN_MEETING_HOST_ID + " INTEGER" +
                ");";
        db.execSQL(query2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HOST);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEETING);
        onCreate(db);
    }

    public void addHost(String name, String email){

        ContentValues values = new ContentValues();

        values.put(COLUMN_HOST_NAME, name);
        values.put(COLUMN_HOST_EMAIL, email);

        SQLiteDatabase db = getWritableDatabase();

        db.insert(TABLE_HOST, null, values);

        db.close();
    }

    // Adds the meeting information
    public void addMeeting (String name, String location, String date, Integer hostId){

        ContentValues values = new ContentValues();

        values.put(COLUMN_MEETING_NAME, name);
        values.put(COLUMN_MEETING_LOCATION, location);
        values.put(COLUMN_MEETING_DATE, date);
        values.put(COLUMN_MEETING_HOST_ID, hostId);

        SQLiteDatabase db = getWritableDatabase();

        db.insert(TABLE_MEETING, null, values);
        db.close();
    }

    // This keeps track on the number of hosts stored in the database
    public String getHostTotal(){

        String dbString = "";

        String query = "SELECT * FROM " + TABLE_HOST;

        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery(query, null);

        int numHosts = c.getCount();

        dbString = String.valueOf(numHosts);
        db.close();

        return dbString;
    }

    public Cursor getHosts() {

        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_HOST, null);

        return cursor;
    }
}
