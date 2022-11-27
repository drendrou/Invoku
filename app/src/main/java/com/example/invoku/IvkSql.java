package com.example.invoku;
//База данных со словами. Создание базы данных происходит при добавление новых языков в IvkSqlMain.java
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class IvkSql extends SQLiteOpenHelper {
    public static final String COLUMN_ID = "_id";
//Создание элементов БД
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

    private ContentValues createContentValues(String paramFW, String paramSW, String paramLang, String paramTrans) {
        //Фунция добавления слов
        ContentValues contentValues = new ContentValues();
        contentValues.put(FIRST_WORD, paramFW);
        contentValues.put(SECOND_WORD, paramSW);
        contentValues.put(LANGUAGE, paramLang);
        contentValues.put(DATABASE_TRANS, paramTrans);
        return contentValues;
    }

    public long createNewTable(String paramFW, String paramSW, String paramLang, String paramTrans) {
        //Добавление в БД слов
        SQLiteDatabase sQLiteDatabase = getWritableDatabase();
        long l = sQLiteDatabase.insert(DATABASE_TABLE, null, createContentValues(paramFW, paramSW, paramLang, paramTrans));
        sQLiteDatabase.close();
        return l;
    }

    public void deleteTable(long paramLong) {
        //Удаление всей БД для выбранного языка
        SQLiteDatabase sQLiteDatabase = getWritableDatabase();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("lang=");
        stringBuilder.append(paramLong);
        sQLiteDatabase.delete(DATABASE_TABLE, stringBuilder.toString(), null);
        sQLiteDatabase.close();
    }

    public void deleteTableWord(long paramLong) {
        //Удаление из БД выбранного слова
        SQLiteDatabase sQLiteDatabase = getWritableDatabase();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("_id=");
        stringBuilder.append(paramLong);
        sQLiteDatabase.delete(DATABASE_TABLE, stringBuilder.toString(), null);
        sQLiteDatabase.close();
    }



    public Cursor getFullTableWithLangAndRand(String paramString) {
        //Получение БД с элементами перемешанными в случайном порядке. Используется в тестировании
        SQLiteDatabase sQLiteDatabase = getWritableDatabase();
        String[] arrayOfString = {COLUMN_ID, FIRST_WORD, SECOND_WORD, LANGUAGE};
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("lang=");
        stringBuilder.append(paramString);
        return sQLiteDatabase.query(DATABASE_TABLE, arrayOfString, stringBuilder.toString(), null, paramString, null, "RANDOM()");
    }

    public Cursor getFullTableWithLangAndTrans(String paramString) {
        //Получение БД
        SQLiteDatabase sQLiteDatabase = getWritableDatabase();
        String[] arrayOfString = {COLUMN_ID, FIRST_WORD, SECOND_WORD, LANGUAGE, DATABASE_TRANS};
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("lang=");
        stringBuilder.append(paramString);
        return sQLiteDatabase.query(DATABASE_TABLE, arrayOfString, stringBuilder.toString(), null, paramString, null, null);
    }


    public Cursor getTableWitfTrans(long paramLong) throws SQLException {
        //Получение элемента БД с данными транскрипции
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

    public boolean updateTable(long paramLong, String paramFW, String paramSW, String paramLang, String paramTrans) {
        //Обновление элементов БД
        SQLiteDatabase sQLiteDatabase = getWritableDatabase();
        ContentValues contentValues = createContentValues(paramFW, paramSW, paramLang, paramTrans);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("_id=");
        stringBuilder.append(paramLong);
        return (sQLiteDatabase.update(DATABASE_TABLE, contentValues, stringBuilder.toString(), null) > 0);
    }

}

