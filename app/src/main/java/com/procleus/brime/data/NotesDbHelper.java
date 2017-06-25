package com.procleus.brime.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.procleus.brime.data.NotesContract.NotesEntry;
import com.procleus.brime.data.NotesContract.LabelEntry;

/**
 * Created by syedaamir on 25-06-2017.
 */

public class NotesDbHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "brime.db";

    public NotesDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_NOTES_TABLE = "CREATE TABLE " + NotesEntry.TABLE_NAME + " (" +
                NotesEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                NotesEntry.COLUMN_NOTES_CONTENT + " TEXT NOT NULL, " +
                NotesEntry.COLUMN_NOTES_TITLE + " TEXT NOT NULL, " +
                NotesEntry.COLUMN_CREATED_ON + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                NotesEntry.COLUMN_MODIFIED_ON + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                NotesEntry.COLUMN_ACCESS_TYPE + " TEXT NOT NULL, " +
                NotesEntry.COLUMN_IS_DELETED + " INTEGER DEFAULT 0, " +
                NotesEntry.COLUMN_LABEL_ID + " INTEGER NOT NULL " +
                " );";
        final String SQL_CREATE_LABELS_TABLE = "CREATE TABLE " + LabelEntry.TABLE_NAME + " (" +
                LabelEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                LabelEntry.COLUMN_LABEL_NAME + " TEXT NOT NULL " +
                " );";

        sqLiteDatabase.execSQL(SQL_CREATE_NOTES_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_LABELS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        // Note that this only fires if you change the version number for your database.
        // It does NOT depend on the version number for your application.
        // If you want to update the schema without wiping data, commenting out the next 2 lines
        // should be your top priority before modifying this method.
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + NotesEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + LabelEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
