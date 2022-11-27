package com.example.invoku;
//Вкладка с резульатами тестирования
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ivkresult extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    public static String num_in;

    ivkSqlResult dbSQLResult;

    FloatingActionButton mFabGoWord;

    ListView mListViewWord;

    SimpleCursorAdapter mSCA;

    public String write;

    public String wrong;

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        //Вызов xml файла и связанных с ним объектов, вызов базы данных с данными тестирования, объявление переменных
        setContentView(R.layout.ivkresult);
        mFabGoWord = findViewById(R.id.floatingActionButtonBack);
        write = getIntent().getStringExtra("write");
        wrong = getIntent().getStringExtra("wrong");
        dbSQLResult = new ivkSqlResult(this);
        num_in = getIntent().getStringExtra("num_in");
        Cursor cursor = this.dbSQLResult.getFullTable();
        String[] arrayOfString = new String[4];

        arrayOfString[0] = "mFirst_word";

        arrayOfString[1] = "mSecond_word";

        arrayOfString[2] = "mUserInput";

        arrayOfString[3] = "mUserResult";
        //Вывод результатов тестирования. Выводится слово, проверяка его перевода и написания, введенное пользователем и ответ: правильно/неправильно
        int[] arrayOfInt = {R.id.outputresultfirst, R.id.outputresultsecond, R.id.outputresultthird, R.id.outputresultfour};
        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this, R.layout.ivkresultoutput, cursor, arrayOfString, arrayOfInt, 0);
        this.mSCA = simpleCursorAdapter;
        this.mListViewWord = findViewById(R.id.ListViewResult);
        this.mListViewWord.setAdapter(this.mSCA);
        registerForContextMenu(this.mListViewWord);
        getSupportLoaderManager().initLoader(0, null, this);
        this.mFabGoWord.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                Intent intent = new Intent(ivkresult.this, Ivkwordactivity.class);
                intent.putExtra("wordlanguage", ivkresult.num_in);
                ivkresult.this.startActivity(intent);
            }
        });
    }

    @NonNull
    public Loader<Cursor> onCreateLoader(int paramInt, @Nullable Bundle paramBundle) {
        return new MyCursorLoader(this, this.dbSQLResult);
    }

    protected void onDestroy() {
        super.onDestroy();
        this.dbSQLResult.close();
    }

    public void onLoadFinished(@NonNull Loader<Cursor> paramLoader, Cursor paramCursor) {
        this.mSCA.swapCursor(paramCursor);
    }

    public void onLoaderReset(@NonNull Loader<Cursor> paramLoader) {
    }

    static class MyCursorLoader extends CursorLoader {
        ivkSqlResult db;

        public MyCursorLoader(Context paramContext, ivkSqlResult paramivkSqlResult) {
            super(paramContext);
            this.db = paramivkSqlResult;
        }

        public Cursor loadInBackground() {
            return this.db.getFullTable();
        }
    }
}
