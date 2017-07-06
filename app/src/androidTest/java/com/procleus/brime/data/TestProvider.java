package com.procleus.brime.data;

import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import java.util.Map;
import java.util.Set;

/**
 * Created by Syed Saad on 25-06-2017.
 */

public class TestProvider extends AndroidTestCase {

    public static final String LOG_TAG = TestProvider.class.getSimpleName();

    public void deleteAllRecordsFromProvider() {
        mContext.getContentResolver().delete(
                NotesContract.NotesEntry.CONTENT_URI,
                null,
                null
        );
        mContext.getContentResolver().delete(
                NotesContract.LabelEntry.CONTENT_URI,
                null,
                null
        );

        Cursor cursor = mContext.getContentResolver().query(
                NotesContract.NotesEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );
        assertEquals("Error: Records not deleted from Notes table during delete", 0, cursor.getCount());
        cursor.close();

        cursor = mContext.getContentResolver().query(
                NotesContract.LabelEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );
        assertEquals("Error: Records not deleted from Labels table during delete", 0, cursor.getCount());
        cursor.close();
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        deleteAllRecordsFromProvider();
    }

    public void testProviderRegistry() {
        PackageManager pm = mContext.getPackageManager();

        // We define the component name based on the package name from the context and the
        // WeatherProvider class.
        ComponentName componentName = new ComponentName(mContext.getPackageName(),
                NotesProvider.class.getName());
        try {
            // Fetch the provider info using the component name from the PackageManager
            // This throws an exception if the provider isn't registered.
            ProviderInfo providerInfo = pm.getProviderInfo(componentName, 0);

            // Make sure that the registered authority matches the authority from the Contract.
            assertEquals("Error: NotesProvider registered with authority: " + providerInfo.authority +
                            " instead of authority: " + NotesContract.CONTENT_AUTHORITY,
                    providerInfo.authority, NotesContract.CONTENT_AUTHORITY);
        } catch (PackageManager.NameNotFoundException e) {
            // I guess the provider isn't registered correctly.
            assertTrue("Error: NotesProvider not registered at " + mContext.getPackageName(),
                    false);
        }
    }

    private static final String noteTitle = "Test Title";
    private static final String noteCotent = "Some Random Content Here.";
    private static final String noteCreated = "2017-07-06 09:38:43";
    private static final String noteEdited = "2018-07-06 09:38:43";
    private static final String accessType = "1";
    private static final int isDeleted = 0;
    private static final int label = 2;

    private static final String labelName = "Personal";

    static ContentValues createLabelContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(NotesContract.LabelEntry._ID, label);
        contentValues.put(NotesContract.LabelEntry.COLUMN_LABEL_NAME, labelName);
        return contentValues;
    }

    static ContentValues createNotesContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(NotesContract.NotesEntry.COLUMN_NOTES_TITLE, noteTitle);
        contentValues.put(NotesContract.NotesEntry.COLUMN_NOTES_CONTENT, noteCotent);
        contentValues.put(NotesContract.NotesEntry.COLUMN_CREATED_ON, noteCreated);
        contentValues.put(NotesContract.NotesEntry.COLUMN_MODIFIED_ON, noteEdited);
        contentValues.put(NotesContract.NotesEntry.COLUMN_ACCESS_TYPE, accessType);
        contentValues.put(NotesContract.NotesEntry.COLUMN_IS_DELETED, isDeleted);
        contentValues.put(NotesContract.NotesEntry.COLUMN_LABEL_ID, label);
        return contentValues;
    }

    static long insertLabelValues(Context context) {

        NotesDbHelper dbHelper = new NotesDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues testValues = createLabelContentValues();
        long rowId;
        rowId = db.insert(NotesContract.LabelEntry.TABLE_NAME, null, testValues);

        // Verify we got a row back.
        assertTrue("Error: Failure to insert Labels Values", rowId != -1);

        return rowId;
    }
    static long insertNotesValues(Context context) {
        // insert our test records into the database

        NotesDbHelper dbHelper = new NotesDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues testValues = createNotesContentValues();
        long rowId;
        rowId = db.insert(NotesContract.NotesEntry.TABLE_NAME, null, testValues);

        // Verify we got a row back.
        assertTrue("Error: Failure to insert Notes Values", rowId != -1);

        return rowId;
    }
    public void testBasicNotesQuery() {
        // insert our test records into the database
        NotesDbHelper dbHelper = new NotesDbHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues testValues = createNotesContentValues();
        long rowId = insertNotesValues(mContext);

        db.close();

        // Test the basic content provider query
        Cursor notesCursor = mContext.getContentResolver().query(
                NotesContract.NotesEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        // Make sure we get the correct cursor out of the database
        validateCursor("testNotesQuery", notesCursor, testValues);
        //deleteAllRecordsFromProvider();
    }
    public void testLabelsQuery() {
        // insert our test records into the database
        NotesDbHelper dbHelper = new NotesDbHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues testValues = createLabelContentValues();
        long rowId = insertLabelValues(mContext);

        db.close();

        // Test the basic content provider query
        Cursor notesCursor = mContext.getContentResolver().query(
                NotesContract.LabelEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        // Make sure we get the correct cursor out of the database
        validateCursor("testLabelsQuery", notesCursor, testValues);
        //deleteAllRecordsFromProvider();
    }
    static void validateCursor(String error, Cursor valueCursor, ContentValues expectedValues) {
        assertTrue("Empty cursor returned. " + error, valueCursor.moveToFirst());
        validateCurrentRecord(error, valueCursor, expectedValues);
        valueCursor.close();
    }
    static void validateCurrentRecord(String error, Cursor valueCursor, ContentValues expectedValues) {
        Set<Map.Entry<String, Object>> valueSet = expectedValues.valueSet();
        for (Map.Entry<String, Object> entry : valueSet) {
            String columnName = entry.getKey();
            int idx = valueCursor.getColumnIndex(columnName);
            assertFalse("Column '" + columnName + "' not found. " + error, idx == -1);
            String expectedValue = entry.getValue().toString();
            assertEquals("Value '" + entry.getValue().toString() +
                    "' did not match the expected value '" +
                    expectedValue + "'. " + error, expectedValue, valueCursor.getString(idx));
        }
    }
}