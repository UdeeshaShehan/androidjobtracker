package com.placetracker.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.placetracker.domain.PlaceSelfie;

public class DataBaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "job";

    private static final String TABLE_SELFIE = "selfies";

    // selfie Table Columns names
    private static final String KEY_ID = "id";
    private static final String FIRSTSELFIE = "firstSelfie";
    private static final String LASTSELFIE = "lastSelfie";
    private static final String LATITUDE1 = "latitude1";
    private static final String LONGITUDE1 = "longitude1";
    private static final String LATITUDE2 = "latitude2";
    private static final String LONGITUDE2 = "longitude2";
    private static final String FIRSTSELFIEDATE = "firstSelfieDate";
    private static final String LASTSELFIEDATE = "lastSelfieDate";
    private static final String JOBNAME = "jobName";
    private static final String ISJOBADDED = "isJobAdded";
    private static final String JOBDESCRIPTION = "jobDescription";
    private static final String DATEOFJOB = "dateOfJob";

    public DataBaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_SELFIE + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + FIRSTSELFIE + " BLOB,"  + LASTSELFIE + " BLOB,"
                + LATITUDE1 + " REAL," + LONGITUDE1 + " REAL,"
                + LATITUDE2 + " REAL," + LONGITUDE2 + " REAL," + FIRSTSELFIEDATE + " TEXT," +
                LASTSELFIEDATE + " TEXT," +
                JOBNAME + " TEXT,"+
                ISJOBADDED + " INTEGER,"+
                JOBDESCRIPTION + " TEXT,"+
                DATEOFJOB + " TEXT"+ ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SELFIE);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    public// Adding new contact
    PlaceSelfie addSelfie(PlaceSelfie placeSelfie) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FIRSTSELFIE, placeSelfie.getFirstSelfie());
        values.put(LASTSELFIE, placeSelfie.getLastSelfie());
        /*values.put(LATITUDE1, placeSelfie.getLatitude1());
        values.put(LATITUDE2, placeSelfie.getLatitude2());
        values.put(LONGITUDE1, placeSelfie.getLongitude1());
        values.put(LONGITUDE2, placeSelfie.getLongitude2());*/
        values.put(FIRSTSELFIEDATE, placeSelfie.getFirstSelfieDate() != null ? placeSelfie.getFirstSelfieDate().toString() : "");
        values.put(LASTSELFIEDATE, placeSelfie.getLastSelfieDate() != null ? placeSelfie.getLastSelfieDate().toString() : "");
        values.put(JOBNAME, placeSelfie.getJobName());
        values.put(ISJOBADDED, placeSelfie.getIsJobAdded());
        values.put(JOBDESCRIPTION, placeSelfie.getJobDescription());
        values.put(DATEOFJOB, placeSelfie.getDateOfJob() != null ? placeSelfie.getDateOfJob().toString() : "");


        // Inserting Row
        long id =db.insert(TABLE_SELFIE, null, values);
        db.close(); // Closing database connection
        if (id == -1)
            return null;
        return getPlaceSelfie(id);
    }

    // Getting single contact
    PlaceSelfie getPlaceSelfie(long id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_SELFIE, new String[] { KEY_ID,
                        FIRSTSELFIE, LASTSELFIE, LATITUDE1, LATITUDE2, LONGITUDE1, LONGITUDE2,
                        FIRSTSELFIEDATE, LASTSELFIEDATE, JOBNAME, ISJOBADDED, JOBDESCRIPTION, DATEOFJOB }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Date date1 = null;
        Date date2 = null;
        Date date3 = null;
        try {
            date1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(cursor.getString(7));

        } catch (Exception e) {
            e.printStackTrace();
        }

        try{
            date2 = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(cursor.getString(8));
        } catch (Exception e) {
            e.printStackTrace();
        }

        try{
            date3 = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(cursor.getString(12));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //PlaceSelfie placeSelfie = new PlaceSelfie();
        /*PlaceSelfie placeSelfie = new PlaceSelfie(Integer.parseInt(cursor.getString(0)),
                cursor.getBlob(1), cursor.getBlob(2), cursor.getDouble(3),
                cursor.getDouble(4), cursor.getDouble(5), cursor.getDouble(6),
                date1 , date2, cursor.getString(9), cursor.getInt(10),
                cursor.getString(11), date3);*/

        // return contact
        return null;

    }

    // Getting All Contacts
    public ArrayList<PlaceSelfie> getAllPlaceSelfies() {
        ArrayList<PlaceSelfie> selfieArrayList = new ArrayList<PlaceSelfie>();
        // Select All Query
        String selectQuery = "SELECT  * FROM selfies";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Date date1 = null;
                Date date2 = null;
                Date date3 = null;
                try {
                    date1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(cursor.getString(7));

                } catch (Exception e) {
                    e.printStackTrace();
                }

                try{
                    date2 = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(cursor.getString(8));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try{
                    date3 = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(cursor.getString(12));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                /*PlaceSelfie placeSelfie = new PlaceSelfie(Integer.parseInt(cursor.getString(0)),
                        cursor.getBlob(1), cursor.getBlob(2), cursor.getDouble(3),
                        cursor.getDouble(4), cursor.getDouble(5), cursor.getDouble(6),
                        date1 , date2, cursor.getString(9), cursor.getInt(10),
                        cursor.getString(11), date3);*/
                // Adding contact to list
                selfieArrayList.add(null);
            } while (cursor.moveToNext());
        }
        // close inserting data from database
        db.close();
        // return contact list
        return selfieArrayList;

    }

    // Updating single contact
    public int updateSelfie(PlaceSelfie placeSelfie) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FIRSTSELFIE, placeSelfie.getFirstSelfie());
        values.put(LASTSELFIE, placeSelfie.getLastSelfie());
        /*values.put(LATITUDE1, placeSelfie.getLatitude1());
        values.put(LATITUDE2, placeSelfie.getLatitude2());
        values.put(LONGITUDE1, placeSelfie.getLongitude1());
        values.put(LONGITUDE2, placeSelfie.getLongitude2());*/
        values.put(FIRSTSELFIEDATE, placeSelfie.getFirstSelfieDate()!= null ? placeSelfie.getFirstSelfieDate().toString() : "");
        values.put(LASTSELFIEDATE, placeSelfie.getLastSelfieDate() != null ? placeSelfie.getLastSelfieDate().toString() : "");

        // updating row
        return db.update(TABLE_SELFIE, values, KEY_ID + " = ?",
                new String[] { String.valueOf(placeSelfie.getId()) });

    }

    // Deleting single contact
    public void deleteSelfie(PlaceSelfie placeSelfie) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SELFIE, KEY_ID + " = ?",
                new String[] { String.valueOf(placeSelfie.getId()) });
        db.close();
    }

    // Deleting single contact
    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_SELFIE);
        db.close();
    }

    // Getting contacts Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_SELFIE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

}
