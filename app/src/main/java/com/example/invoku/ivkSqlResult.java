package com.example.invoku;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ivkSqlResult extends SQLiteOpenHelper {
  public static final String COLUMN_ID = "_id";
  
  private static final String DATABASE_CREATE = "create table resulttable(_id integer primary key,mFirst_word text,mSecond_word text,mUserInput text,mUserResult text)";
  
  private static final String DATABASE_NAME = "result.db";
  
  private static final String DATABASE_TABLE = "resulttable";
  
  private static final int DATABASE_VERSION = 1;
  
  public static final String FIRST_WORD = "mFirst_word";
  
  public static final String SECOND_WORD = "mSecond_word";
  
  public static final String USERS_INPUT = "mUserInput";
  
  public static final String USERS_RESULT = "mUserResult";
  
  public ivkSqlResult(Context paramContext) { super(paramContext, DATABASE_NAME, null, DATABASE_VERSION); }
  
  private ContentValues createContentValues(String paramString1, String paramString2, String paramString3, String paramString4) {
    ContentValues contentValues = new ContentValues();
    contentValues.put(FIRST_WORD, paramString1);
    contentValues.put(SECOND_WORD, paramString2);
    contentValues.put(USERS_INPUT, paramString3);
    contentValues.put(USERS_RESULT, paramString4);
    return contentValues;
  }
  
  public long createNewTable(String paramString1, String paramString2, String paramString3, String paramString4) {
    SQLiteDatabase sQLiteDatabase = getWritableDatabase();
    long l = sQLiteDatabase.insert(DATABASE_TABLE, null, createContentValues(paramString1, paramString2, paramString3, paramString4));
    sQLiteDatabase.close();
    return l;
  }
  
  public void deleteTable() {
    SQLiteDatabase sQLiteDatabase = getWritableDatabase();
    sQLiteDatabase.delete(DATABASE_TABLE, COLUMN_ID, null);
    sQLiteDatabase.close();
  }
  
  public Cursor getFullTable() { return getWritableDatabase().query(DATABASE_TABLE, new String[] { COLUMN_ID, FIRST_WORD, SECOND_WORD, USERS_INPUT, USERS_RESULT }, null, null, null, null, null); }
  
  public void onCreate(SQLiteDatabase paramSQLiteDatabase) { paramSQLiteDatabase.execSQL(DATABASE_CREATE); }
  
  public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2) {
    paramSQLiteDatabase.execSQL("drop table if exists resulttable");
    onCreate(paramSQLiteDatabase);
  }
}


