package com.procleus.brime;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.Timestamp;


public class Notes extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "notes";

    public Notes(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTextNotesTable = "CREATE TABLE textNotes (id int primary key AUTO_INCREMENT, note TIMESTAMP default CURRENT_TIMESTAMP, created TIMESTAMP default CURRENT_TIMESTAMP, edited int, owner int)";
        db.execSQL(createTextNotesTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String createTextNotesTable = "CREATE TABLE if not exists textNotes (id int primary key AUTO_INCREMENT, note text, created TIMESTAMP default CURRENT_TIMESTAMP, edited TIMESTAMP default CURRENT_TIMESTAMP, owner int)";
        db.execSQL(createTextNotesTable);
    }

    public void insertTextNote(String n, int o) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO textNotes(note,owner) VALUES('" + n + "'," + o + ")");
        db.close();
    }

    public textNote getTextNoteById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query("textNotes", new String[]{"id", "note", "created", "edited", "owner"}, "id = " + id, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        textNote note;
        try {
            note = new textNote(Integer.parseInt(cursor.getString(0)), cursor.getString(1), Timestamp.valueOf(cursor.getString(2)), Timestamp.valueOf(cursor.getString(3)), Integer.parseInt(cursor.getString(4)));
        } catch (Exception e) {
            note = new textNote(0, "Error Fetching Note", Timestamp.valueOf("0"), Timestamp.valueOf("0"), 0);
            Log.e("Exception", e.toString());
        }
        return note;
    }

}
