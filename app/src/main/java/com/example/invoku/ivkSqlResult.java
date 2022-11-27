package com.example.invoku;
//База данных для тестирования
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

  private ContentValues createContentValues(String paramFW, String paramSW, String paramUI, String paramRes) {
    //Функция добавления в БД слов, введенного пользователем и результата
    ContentValues contentValues = new ContentValues();
    contentValues.put(FIRST_WORD, paramFW);
    contentValues.put(SECOND_WORD, paramSW);
    contentValues.put(USERS_INPUT, paramUI);
    contentValues.put(USERS_RESULT, paramRes);
    return contentValues;
  }
  
  public long createNewTable(String paramFW, String paramSW, String paramUI, String paramRes) {
    //Добавление в БД слов, введенного пользователем и результата
    SQLiteDatabase sQLiteDatabase = getWritableDatabase();
    long l = sQLiteDatabase.insert(DATABASE_TABLE, null, createContentValues(paramFW, paramSW, paramUI, paramRes));
    sQLiteDatabase.close();
    return l;
  }
  
  public void deleteTable() {
    //Удаление БД результата. Не влияет на основную БД слов
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


