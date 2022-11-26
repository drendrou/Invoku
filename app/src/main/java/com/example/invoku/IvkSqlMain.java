package com.example.invoku;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class IvkSqlMain extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "main.db";
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_TABLE = "maintable";

    public static final String COLUMN_ID = "_id";
    public static final String LANGUAGE = "lang";


    private static final String DATABASE_CREATE = "create table " + DATABASE_TABLE + "(" + COLUMN_ID
            + " integer primary key," + LANGUAGE + " text" + ")";


    public IvkSqlMain(Context paramContext) {
        super(paramContext, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private ContentValues createContentValues(String paramString) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(LANGUAGE, paramString);
        return contentValues;
    }

    public long createNewTable(String paramString) {
        SQLiteDatabase sQLiteDatabase = getWritableDatabase();
        long l = sQLiteDatabase.insert(DATABASE_TABLE, null, createContentValues(paramString));
        sQLiteDatabase.close();
        return l;
    }

    public void deleteTable(long rowId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DATABASE_TABLE, COLUMN_ID + "=" + rowId, null);
        db.close();
    }


    public Cursor getFullTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.query(DATABASE_TABLE, new String[]{COLUMN_ID, LANGUAGE},
                null, null, null, null, null);
    }


    public Cursor getTable(long rowId) throws SQLException {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor mCursor = db.query(true, DATABASE_TABLE,
                new String[]{COLUMN_ID, LANGUAGE}, COLUMN_ID + "=" + rowId, null,
                null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }


    public void onCreate(SQLiteDatabase paramSQLiteDatabase) {
        paramSQLiteDatabase.execSQL(DATABASE_CREATE);
    }

    public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2) {
        paramSQLiteDatabase.execSQL("drop table if exists maintable");
        onCreate(paramSQLiteDatabase);
    }

    public boolean updateTable(long rowId, String lang) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues updateValues = createContentValues(lang);

        return db.update(DATABASE_TABLE, updateValues, COLUMN_ID + "=" + rowId,
                null) > 0;
    }

}


