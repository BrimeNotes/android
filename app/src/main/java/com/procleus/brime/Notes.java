package com.procleus.brime;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


public class Notes extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "notes";

    public Notes(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTextNotesTable = "CREATE TABLE textNotes (id INTEGER PRIMARY KEY AUTOINCREMENT, note text, title text, created TIMESTAMP default CURRENT_TIMESTAMP, edited TIMESTAMP default CURRENT_TIMESTAMP, owner integer,accessType text,isDeleted integer,label text)";
        Log.d("Sql Query Create :",createTextNotesTable);
        db.execSQL(createTextNotesTable);
        String createLabelTable = "CREATE TABLE label (label text)";
        Log.d("Sql Query Create :",createLabelTable);
        db.execSQL(createLabelTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String createTextNotesTable = "CREATE TABLE if not exists textNotes (id INTEGER PRIMARY KEY AUTOINCREMENT, note text, title text, created TIMESTAMP default CURRENT_TIMESTAMP, edited TIMESTAMP default CURRENT_TIMESTAMP, owner integer,accessType text,isDeleted integer,label text)";
        db.execSQL(createTextNotesTable);
        String createLabelTable = "CREATE TABLE if not exists label (label text)";
        db.execSQL(createLabelTable);
    }

    public void insertLabel(String l){

        SQLiteDatabase db = this.getWritableDatabase();
        String query="INSERT INTO label  VALUES('" + l + "')";
        db.execSQL(query);
        Log.d("Sql Query insert  ",query);
        db.close();

    }


    public ArrayList<String> retrieveLabel(){

        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<String> ArrayLis = new ArrayList<String>();
        String query="select * from label";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do {
                ArrayLis.add(cursor.getString(0));

                Log.d("Student Label Name ", cursor.getString(0));
            }while (cursor.moveToNext());
        }

        return ArrayLis;
    }

    public void insertTextNote(String n, String t,String a, int o,String l) {
        n = n.replaceAll("'", "''");
        t = t.replaceAll("'", "''");
        a = a.replaceAll("'", "''");
        SQLiteDatabase db = this.getWritableDatabase();
        String query="INSERT INTO textNotes(note,title,owner,accessType,isDeleted,label) VALUES('" + n + "','" + t + "'," + o +  ",'" + a + "',0,'"+ l + "')";
        db.execSQL(query);
        Log.d("Sql Query insert  ",query);
        db.close();
    }

    public void updateTextNote(int id, String n , String t) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query ="UPDATE textNotes set note = '" + n + "', title = '" + t + "'  WHERE id = " + id ;
        db.execSQL(query);
        Log.d("Sql Query Access  ", query);
        db.close();
    }

    public void accessChange(int id,String access) {

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE textNotes set accessType = '"+ access +"' WHERE id='" + id + "'";
        db.execSQL(query);
        Log.d("Sql Query Access  ", query);
        db.close();

    }
    public void moveToTrash(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE textNotes set isDeleted = 1  WHERE id='" + id + "'";
        db.execSQL(query);
        Log.d("Sql Query Trash  ", query);
        db.close();
    }

    public void deleteTextNote(String i) {

        SQLiteDatabase db = this.getWritableDatabase();
        String query="DELETE FROM label WHERE label='" + i + "'";
        db.execSQL(query);
        query = "UPDATE textNotes set isDeleted = 1  WHERE label='" + i + "'";
        db.execSQL(query);
        Log.i("BOOM",query);
        db.close();
    }
/*
    public TextNote getTextNoteById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query("textNotes", new String[]{"id", "note", "title", "created", "edited", "owner"}, "id = " + id, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        TextNote note;
        try {
            note = new TextNote(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), Timestamp.valueOf(cursor.getString(3)), Timestamp.valueOf(cursor.getString(4)), Integer.parseInt(cursor.getString(5)));
        } catch (Exception e) {
            note = new TextNote(0, "Error Fetching Note", "Error", Timestamp.valueOf("0"), Timestamp.valueOf("0"), 0);
            Log.e("Exception", e.toString());
        }
        return note;
    }

    public List<TextNote> getTextNoteByOwner(int o) {
        //Log.e("Error","1Nothing fetched");
        List textNotesList = new ArrayList<TextNote>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("textNotes", new String[]{"id", "note", "title", "created", "edited", "owner"}, "owner = " + o, null, null, null, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    Log.e("Fetched", String.valueOf(Integer.parseInt(cursor.getString(0))));
                    TextNote textNote = new TextNote(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), Timestamp.valueOf(cursor.getString(3)), Timestamp.valueOf(cursor.getString(4)), Integer.parseInt(cursor.getString(5)));
                    textNotesList.add(textNote);
                    //Log.i("success","fetched");
                } while (cursor.moveToNext());
            } else {
                // Log.e("Error","Nothing fetched");
            }
        } catch (Exception e) {
            Log.e("Exception", e.toString());
        }
        return textNotesList;
    }
*/
    /* Retrive  data from database */
    public List<NotesModel> getDataFromDB(String type,int trashVal){
        List<NotesModel> modelList = new ArrayList<NotesModel>();
        String query;
        if (type == "both"){
            query = "select * from textNotes where isDeleted ="+trashVal ;
        }
        else
        {
            query = "select * from textNotes where isDeleted ="+trashVal+" AND accessType = '"+type+"'";
        }
        Log.d("Sql Query get :",query);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()){
            do {
                NotesModel model = new NotesModel();
                model.setId(cursor.getInt(0));
                model.setTitle(cursor.getString(2));
                model.setDesc(cursor.getString(1));
                model.setAccess_type(cursor.getString(6));
                model.setIsDeleted(cursor.getInt(7));
                model.setLable(cursor.getString(8));
                modelList.add(model);

                Log.d("Student Data Name ",cursor.getInt(0)+" title :  "+ cursor.getString(2) +" access type :Walal "+ cursor.getString(5) +" Trash Val "+cursor.getInt(6) + "Label Val" + cursor.getString(8));
            }while (cursor.moveToNext());
        }

        Log.d("student data", modelList.toString());

        return modelList;
    }


    public List<NotesModel> getDataFromDBWithLabel(String label){
        List<NotesModel> modelList = new ArrayList<NotesModel>();
        String query;

        query = "select * from textNotes where label ='"+label+"' AND isDeleted = 0";

        Log.d("Sql Query get :",query);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()){
            do {
                NotesModel model = new NotesModel();
                model.setId(cursor.getInt(0));
                model.setTitle(cursor.getString(2));
                model.setDesc(cursor.getString(1));
                model.setAccess_type(cursor.getString(6));
                model.setIsDeleted(cursor.getInt(7));
                model.setLable(cursor.getString(8));
                modelList.add(model);

                Log.d("Student Data Name ",cursor.getInt(0)+" title :  "+ cursor.getString(2) +" access type :Walal "+ cursor.getString(5) +" Trash Val "+cursor.getInt(6) + "Label Val" + cursor.getString(8));
            }while (cursor.moveToNext());
        }

        Log.d("student data", modelList.toString());

        return modelList;
    }



}
