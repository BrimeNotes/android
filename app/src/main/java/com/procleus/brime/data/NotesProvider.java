package com.procleus.brime.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.os.CancellationSignal;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by syedaamir on 25-06-2017.
 */

public class NotesProvider extends ContentProvider{

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private NotesDbHelper mOpenHelper;

    static final int NOTES = 100;
    static final int NOTES_WITH_ID =101;
    static final int LABELS = 300;
    static final int LABELS_WITH_ID = 301;

    public static final String mNotesByLabelSelection = NotesContract.NotesEntry.TABLE_NAME + "."
            + NotesContract.NotesEntry.COLUMN_LABEL_ID + " = ? ";


    private static final SQLiteQueryBuilder mNotesWithLabelNameQueryBuilder;

    static {
        mNotesWithLabelNameQueryBuilder = new SQLiteQueryBuilder();
        mNotesWithLabelNameQueryBuilder.setTables(
                NotesContract.NotesEntry.TABLE_NAME + " INNER JOIN " +
                        NotesContract.LabelEntry.TABLE_NAME +
                        " ON " + NotesContract.NotesEntry.TABLE_NAME +
                        "." + NotesContract.NotesEntry.COLUMN_LABEL_ID +
                        " = " + NotesContract.LabelEntry.TABLE_NAME +
                        "." + NotesContract.LabelEntry._ID );
    }
    static UriMatcher buildUriMatcher() {
        // I know what you're thinking.  Why create a UriMatcher when you can use regular
        // expressions instead?  Because you're not crazy, that's why.

        // All paths added to the UriMatcher have a corresponding code to return when a match is
        // found.  The code passed into the constructor represents the code to return for the root
        // URI.  It's common to use NO_MATCH as the code for this case.
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = NotesContract.CONTENT_AUTHORITY;

        // For each type of URI you want to add, create a corresponding code.
        matcher.addURI(authority, NotesContract.PATH_SAVED_NOTES, NOTES);
        matcher.addURI(authority, NotesContract.PATH_SAVED_NOTES + "/#", NOTES_WITH_ID);
        matcher.addURI(authority, NotesContract.PATH_LABELS, LABELS);
        matcher.addURI(authority, NotesContract.PATH_LABELS + "/#", LABELS_WITH_ID);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new NotesDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        // Use the Uri Matcher to determine what kind of URI this is.
        final int match = sUriMatcher.match(uri);
        switch (match) {
            // Student: Uncomment and fill out these two cases
            case NOTES:
                return NotesContract.NotesEntry.CONTENT_TYPE;
            case NOTES_WITH_ID:
                return NotesContract.NotesEntry.CONTENT_ITEM_TYPE;
            case LABELS:
                return NotesContract.LabelEntry.CONTENT_TYPE;
            case LABELS_WITH_ID:
                return NotesContract.LabelEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        // Here's the switch statement that, given a URI, will determine what kind of request it is,
        // and query the database accordingly.
        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {
            // "FOR NOTES"
            case NOTES: {
                retCursor = mNotesWithLabelNameQueryBuilder.query(
                        mOpenHelper.getReadableDatabase(),
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case NOTES_WITH_ID: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        NotesContract.NotesEntry.TABLE_NAME,
                        projection,
                        "WHERE " + NotesContract.NotesEntry._ID + " = ?",
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            // "FOR LABELS"
            case LABELS: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        NotesContract.LabelEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case NOTES: {
                long _id = db.insert(NotesContract.NotesEntry.TABLE_NAME, null, contentValues);
                if ( _id > 0 )
                    returnUri = NotesContract.NotesEntry.buildNotesUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case LABELS: {
                long _id = db.insert(NotesContract.LabelEntry.TABLE_NAME, null, contentValues);
                if ( _id > 0 )
                    returnUri = NotesContract.LabelEntry.buildLabelsUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;

    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case NOTES:
                db.beginTransaction();
                int returnCount = 0;
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(NotesContract.NotesEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            case LABELS:
                db.beginTransaction();
                int returnCount2 = 0;
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(NotesContract.LabelEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount2++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount2;
            default:
                return super.bulkInsert(uri, values);
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;
        // this makes delete all rows return the number of rows deleted
        if ( null == selection ) selection = "1";
        switch (match) {
            case NOTES:
                rowsDeleted = db.delete(
                        NotesContract.NotesEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case LABELS:
                rowsDeleted = db.delete(
                        NotesContract.LabelEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        // Because a null deletes all rows
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        switch (match) {
            case NOTES:
                rowsUpdated = db.update(NotesContract.NotesEntry.TABLE_NAME, contentValues, selection,
                        selectionArgs);
                break;
            case LABELS:
                rowsUpdated = db.update(NotesContract.LabelEntry.TABLE_NAME, contentValues, selection,
                        selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

    // You do not need to call this method. This is a method specifically to assist the testing
    // framework in running smoothly. You can read more at:
    // http://developer.android.com/reference/android/content/ContentProvider.html#shutdown()
    @Override
    public void shutdown() {
        mOpenHelper.close();
        super.shutdown();
    }
}
