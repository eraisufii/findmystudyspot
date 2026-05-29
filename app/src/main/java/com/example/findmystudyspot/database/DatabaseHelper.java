package com.example.findmystudyspot.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.findmystudyspot.R;
import com.example.findmystudyspot.model.StudySpot;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "StudySpot.db";

    private static final int DATABASE_VERSION = 2;

    private static final String TABLE_USERS = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";

    public static final String TABLE_SPOTS = "study_spots";
    public static final String COL_SPOT_ID = "spot_id";
    public static final String COL_NAME = "name";
    public static final String COL_DESC = "description";
    public static final String COL_IMAGE = "image_resource_id";
    public static final String COL_LAT = "latitude";
    public static final String COL_LNG = "longitude";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUsersTable = "CREATE TABLE " + TABLE_USERS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_USERNAME + " TEXT,"
                + COLUMN_EMAIL + " TEXT,"
                + COLUMN_PASSWORD + " TEXT"
                + ")";
        db.execSQL(createUsersTable);

        String createSpotsTable = "CREATE TABLE " + TABLE_SPOTS + " ("
                + COL_SPOT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_NAME + " TEXT, "
                + COL_DESC + " TEXT, "
                + COL_IMAGE + " INTEGER, "
                + COL_LAT + " REAL, "
                + COL_LNG + " REAL)";
        db.execSQL(createSpotsTable);

        seedDefaultSpots(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older tables if they exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SPOTS);
        // Create tables again
        onCreate(db);
    }

    public boolean insertUser(String username, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASSWORD, password);

        long result = db.insert(TABLE_USERS, null, values);
        db.close(); // Clean up closing the database connection
        return result != -1;
    }

    public boolean checkUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TABLE_USERS +
                        " WHERE email=? AND password=?",
                new String[]{email, password});

        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close(); // Clean up closing the database connection
        return exists;
    }

    private void seedDefaultSpots(SQLiteDatabase db) {
        insertSpotDirectly(db, "Blue Bottle Café", "Excellent WiFi • Quiet • Prishtinë", R.drawable.cafe, 42.6629, 21.1655);
        insertSpotDirectly(db, "University Library", "Super quiet • Perfect for studying", R.drawable.library, 42.6489, 21.1622);
        insertSpotDirectly(db, "Study Hub", "Modern space • Group friendly", R.drawable.studyhub, 42.6590, 21.1605);
        insertSpotDirectly(db, "Coffee House", "Modern café • Great WiFi", R.drawable.cafe, 42.6629, 21.1655);
        insertSpotDirectly(db, "City Library", "Quiet study area • Free seating", R.drawable.library, 42.6489, 21.1622);
    }

    private void insertSpotDirectly(SQLiteDatabase db, String name, String desc, int image, double lat, double lng) {
        ContentValues values = new ContentValues();
        values.put(COL_NAME, name);
        values.put(COL_DESC, desc);
        values.put(COL_IMAGE, image);
        values.put(COL_LAT, lat);
        values.put(COL_LNG, lng);
        db.insert(TABLE_SPOTS, null, values);
    }

    public boolean addStudySpot(StudySpot spot) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NAME, spot.getName());
        values.put(COL_DESC, spot.getDescription());
        values.put(COL_IMAGE, spot.getImage());
        values.put(COL_LAT, spot.getLatitude());
        values.put(COL_LNG, spot.getLongitude());

        long result = db.insert(TABLE_SPOTS, null, values);
        db.close();
        return result != -1;
    }

    public ArrayList<StudySpot> getAllStudySpots() {
        ArrayList<StudySpot> spotList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_SPOTS + " ORDER BY " + COL_SPOT_ID + " DESC", null);

        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COL_NAME));
                String desc = cursor.getString(cursor.getColumnIndexOrThrow(COL_DESC));
                int image = cursor.getInt(cursor.getColumnIndexOrThrow(COL_IMAGE));
                double lat = cursor.getDouble(cursor.getColumnIndexOrThrow(COL_LAT));
                double lng = cursor.getDouble(cursor.getColumnIndexOrThrow(COL_LNG));

                spotList.add(new StudySpot(name, desc, image, lat, lng));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return spotList;
    }
}