package com.example.invoku;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class IvkSql extends SQLiteOpenHelper {
    public static final String COLUMN_ID = "_id";

    private static final String DATABASE_CREATE = "create table languagetable(_id integer primary key,mFirst_word text,mSecond_word text,lang text,trans text)";

    private static final String DATABASE_NAME = "language.db";

    private static final String DATABASE_TABLE = "languagetable";

    public static final String DATABASE_TRANS = "trans";

    private static final int DATABASE_VERSION = 3;

    public static final String FIRST_WORD = "mFirst_word";

    public static final String LANGUAGE = "lang";

    public static final String SECOND_WORD = "mSecond_word";

    public IvkSql(Context paramContext) {
        super(paramContext, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private ContentValues createContentValues(String paramString1, String paramString2, String paramString3, String paramString4) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(FIRST_WORD, paramString1);
        contentValues.put(SECOND_WORD, paramString2);
        contentValues.put(LANGUAGE, paramString3);
        contentValues.put(DATABASE_TRANS, paramString4);
        return contentValues;
    }

    public long createNewTable(String paramString1, String paramString2, String paramString3, String paramString4) {
        SQLiteDatabase sQLiteDatabase = getWritableDatabase();
        long l = sQLiteDatabase.insert(DATABASE_TABLE, null, createContentValues(paramString1, paramString2, paramString3, paramString4));
        sQLiteDatabase.close();
        return l;
    }

    public void deleteTable(long paramLong) {
        SQLiteDatabase sQLiteDatabase = getWritableDatabase();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("lang=");
        stringBuilder.append(paramLong);
        sQLiteDatabase.delete(DATABASE_TABLE, stringBuilder.toString(), null);
        sQLiteDatabase.close();
    }

    public void deleteTableWord(long paramLong) {
        SQLiteDatabase sQLiteDatabase = getWritableDatabase();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("_id=");
        stringBuilder.append(paramLong);
        sQLiteDatabase.delete(DATABASE_TABLE, stringBuilder.toString(), null);
        sQLiteDatabase.close();
    }


    public Cursor getFullTableWithLang(String paramString) {
        SQLiteDatabase sQLiteDatabase = getWritableDatabase();
        String[] arrayOfString = {COLUMN_ID, FIRST_WORD, SECOND_WORD, LANGUAGE};
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("lang=");
        stringBuilder.append(paramString);
        return sQLiteDatabase.query(DATABASE_TABLE, arrayOfString, stringBuilder.toString(), null, paramString, null, null);
    }

    public Cursor getFullTableWithLangAndRand(String paramString) {
        SQLiteDatabase sQLiteDatabase = getWritableDatabase();
        String[] arrayOfString = {COLUMN_ID, FIRST_WORD, SECOND_WORD, LANGUAGE};
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("lang=");
        stringBuilder.append(paramString);
        return sQLiteDatabase.query(DATABASE_TABLE, arrayOfString, stringBuilder.toString(), null, paramString, null, "RANDOM()");
    }

    public Cursor getFullTableWithLangAndTrans(String paramString) {
        SQLiteDatabase sQLiteDatabase = getWritableDatabase();
        String[] arrayOfString = {COLUMN_ID, FIRST_WORD, SECOND_WORD, LANGUAGE, DATABASE_TRANS};
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("lang=");
        stringBuilder.append(paramString);
        return sQLiteDatabase.query(DATABASE_TABLE, arrayOfString, stringBuilder.toString(), null, paramString, null, null);
    }

    public Cursor getTable(long paramLong) throws SQLException {
        SQLiteDatabase sQLiteDatabase = getReadableDatabase();
        String[] arrayOfString = {COLUMN_ID, FIRST_WORD, SECOND_WORD, LANGUAGE};
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("_id=");
        stringBuilder.append(paramLong);
        Cursor cursor = sQLiteDatabase.query(true, DATABASE_TABLE, arrayOfString, stringBuilder.toString(), null, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        return cursor;
    }

    public Cursor getTableWitfTrans(long paramLong) throws SQLException {
        SQLiteDatabase sQLiteDatabase = getReadableDatabase();
        String[] arrayOfString = {COLUMN_ID, FIRST_WORD, SECOND_WORD, LANGUAGE, DATABASE_TRANS};
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("_id=");
        stringBuilder.append(paramLong);
        Cursor cursor = sQLiteDatabase.query(true, DATABASE_TABLE, arrayOfString, stringBuilder.toString(), null, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        return cursor;
    }

    public void onCreate(SQLiteDatabase paramSQLiteDatabase) {
        paramSQLiteDatabase.execSQL(DATABASE_CREATE);
    }

    public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2) {
        paramSQLiteDatabase.execSQL("drop table if exists languagetable");
        onCreate(paramSQLiteDatabase);
    }

    public boolean updateTable(long paramLong, String paramString1, String paramString2, String paramString3, String paramString4) {
        SQLiteDatabase sQLiteDatabase = getWritableDatabase();
        ContentValues contentValues = createContentValues(paramString1, paramString2, paramString3, paramString4);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("_id=");
        stringBuilder.append(paramLong);
        return (sQLiteDatabase.update(DATABASE_TABLE, contentValues, stringBuilder.toString(), null) > 0);
    }

}

