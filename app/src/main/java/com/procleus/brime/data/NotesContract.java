package com.procleus.brime.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by syedaamir on 25-06-2017.
 */

public class NotesContract {
    public static final String CONTENT_AUTHORITY = "com.procleus.brime";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_SAVED_NOTES = "notes";
    public static final String PATH_LABELS = "labels";

    public static final class NotesEntry implements BaseColumns {
        public static final int COL_NOTES_ID = 0;
        public static final int COL_NOTES_CONTENT = 1;
        public static final int COL_NOTES_TITLE = 2;
        public static final int COL_CREATED_ON = 3;
        public static final int COL_MODIFIED_ON = 4;
        public static final int COL_ACCESS_TYPE = 5;
        public static final int COL_IS_DELETED = 6;
        public static final int COL_LABEL = 7;

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_SAVED_NOTES).build();
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SAVED_NOTES;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SAVED_NOTES;

        /**
         *
         *  TABLE COLUMNS
         *
         */

        public static final String TABLE_NAME = "notes";
        public static final String COLUMN_NOTES_CONTENT = "content";
        public static final String COLUMN_NOTES_TITLE = "title";
        public static final String COLUMN_CREATED_ON = "created_on";
        public static final String COLUMN_MODIFIED_ON = "modified_on";
        public static final String COLUMN_ACCESS_TYPE = "access_type";
        public static final String COLUMN_IS_DELETED = "is_deleted";
        public static final String COLUMN_LABEL_ID = "label";

        public static Uri buildNotesUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
    public static final class LabelEntry implements BaseColumns{

        public static final int COL_ID = 0;
        public static final int COL_LABEL_NAME = 1;

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_SAVED_NOTES).build();
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_LABELS;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_LABELS;

        /**
         *
         *  TABLE COLUMNS
         *
         */

        public static final String TABLE_NAME = "labels";
        public static final String COLUMN_LABEL_NAME = "name";

        public static Uri buildLabelsUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
