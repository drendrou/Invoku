package com.example.invoku;

//Основная вкладка с добавлением, выбором, редактированием и удалением изучаемых языков

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.concurrent.TimeUnit;

public class IvkMainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int open = 1;
    private static final int changeMain = 2;

    private static final int deleteMain = 3;


    IvkSql IvkSqlDelete;

    IvkSqlMain dbSQLM;

    String languagename_in;
    EditText LanguageNameEditText;
    final Context mDialogMain = this;

    FloatingActionButton mFBAM;

    ListView mListViewMain;

    SimpleCursorAdapter mSCAM;

    public long num_id;

    String wordlanguage;

    @Override

    //Меню вызываемое при долгом нажатии на один из языков
    public boolean onContextItemSelected(MenuItem paramMenuItem) {
        //Полное удаление языка из базы данных. Без возможности восстановления, посколько очищается связанная с ним память. Слова связанные с ним тоже удаляются
        if (paramMenuItem.getItemId() == deleteMain) {
            final AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) paramMenuItem.getMenuInfo();
            View view = LayoutInflater.from(mDialogMain).inflate(R.layout.ivkdelete, null);
            AlertDialog.Builder builder = new AlertDialog.Builder(mDialogMain);
            builder.setView(view);
            builder.setCancelable(true).setNegativeButton("отмена", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    paramDialogInterface.cancel();
                }
            }).setPositiveButton("ок", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    dbSQLM.deleteTable(acmi.id);
                    IvkSqlDelete.deleteTable(acmi.id);
                    getSupportLoaderManager().getLoader(0).forceLoad();
                }
            });
            builder.create().show();
        }
        //Изменение названия. Не влияет на хранимые данные
        if (paramMenuItem.getItemId() == changeMain) {
            final AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) paramMenuItem.getMenuInfo();
            View view = LayoutInflater.from(mDialogMain).inflate(R.layout.ivkdialogmain, null);
            AlertDialog.Builder builder = new AlertDialog.Builder(mDialogMain);
            builder.setView(view);
            LanguageNameEditText = view.findViewById(R.id.language_name);
            Cursor cursor = this.dbSQLM.getTable(acmi.id);
            if (cursor != null) {
                cursor.moveToFirst();
                num_id = cursor.getInt(0);
                languagename_in = cursor.getString(1);
                LanguageNameEditText.setText(languagename_in.trim());
                LanguageNameEditText.setSelection(LanguageNameEditText.getText().length());
            }
            builder.setCancelable(true).setNegativeButton("отмена", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    paramDialogInterface.cancel();
                }
            }).setPositiveButton("ок", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                }
            });
            final AlertDialog alertDialog = builder.create();
            alertDialog.show();
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View paramView) {
                    languagename_in = LanguageNameEditText.getText().toString();
                    if (!languagename_in.equals("")) {
                        dbSQLM.updateTable(num_id, languagename_in.trim());
                        getSupportLoaderManager().getLoader(0).forceLoad();
                        alertDialog.cancel();

                    } else {
                        Toast.makeText(getApplicationContext(), "Введите название языка", Toast.LENGTH_SHORT).show();

                        alertDialog.dismiss();
                    }
                }
            });
            return true;

        }
        //Просто открытие, как и при быстром нажатии
        if (paramMenuItem.getItemId() == open) {
            final AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) paramMenuItem.getMenuInfo();
            Cursor cursor = dbSQLM.getTable(acmi.id);
            if (cursor != null) {
                cursor.moveToFirst();
                num_id = cursor.getInt(0);
                wordlanguage = Long.toString(num_id);
            }
            Intent intent = new Intent(this, Ivkwordactivity.class);
            intent.putExtra("wordlanguage", wordlanguage);
            startActivity(intent);
            return true;
        }
        return super.onContextItemSelected(paramMenuItem);
    }

    @Override
    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        //Вызов xml файла и связанных с ним объектов, а также объявление используемых переменных
        setContentView(R.layout.ivkactivitymain);
        mFBAM = findViewById(R.id.floatingActionButtonMain);
        mListViewMain = findViewById(R.id.ListViewLanguage);
        dbSQLM = new IvkSqlMain(this);
        dbSQLM.getFullTable();
        IvkSqlDelete = new IvkSql(this);
        String[] arrayOfString = new String[1];
        Cursor cursor = dbSQLM.getFullTable();
        arrayOfString[0] = "lang";
        int[] arrayOfInt = {R.id.outputmaintext};
        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this, R.layout.ivkoutputmain, cursor, arrayOfString, arrayOfInt, 0);
        mSCAM = simpleCursorAdapter;
        mListViewMain.setAdapter(this.mSCAM);
        registerForContextMenu(this.mListViewMain);
        getSupportLoaderManager().initLoader(0, null, this);
        //Функция добавления новых языков, вызываемая при нажатии на кнопку
        mFBAM.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                View view = LayoutInflater.from(mDialogMain).inflate(R.layout.ivkdialogmain, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(IvkMainActivity.this.mDialogMain);
                builder.setView(view);
                LanguageNameEditText = view.findViewById(R.id.language_name);
                builder.setCancelable(true).setNegativeButton("отмена", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        paramDialogInterface.cancel();
                    }
                }).setPositiveButton("ок", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    }
                });
                final AlertDialog alertDialog = builder.create();
                alertDialog.show();
                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        languagename_in = LanguageNameEditText.getText().toString();
                        //Если название языка пустое, просит ввести название
                        if (languagename_in.equals("")) {
                            Toast toast = Toast.makeText(getApplicationContext(),
                                    "Введите название языка", Toast.LENGTH_SHORT);
                            Boolean wantToCloseDialog = false;
                            if(wantToCloseDialog) {

                                alertDialog.dismiss();
                            }

                            toast.show();
                        }
                        //Создание новой таблицы для добавленного языка
                        if (!languagename_in.equals("")) {
                            dbSQLM.createNewTable(languagename_in.trim());

                            getSupportLoaderManager().getLoader(0).forceLoad();
                            alertDialog.cancel();
                        }
                    }
                });
            }
        });
        mListViewMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            //Переход во вкладку со словами для выбранного языка
            public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong) {
                Cursor cursor = dbSQLM.getTable(paramLong);
                if (cursor != null) {
                    cursor.moveToFirst();
                    num_id = cursor.getInt(0);
                   wordlanguage = Long.toString(num_id);
                }
                Intent intent = new Intent(IvkMainActivity.this, Ivkwordactivity.class);
                intent.putExtra("wordlanguage", IvkMainActivity.this.wordlanguage);
                IvkMainActivity.this.startActivity(intent);
            }
        });
    }
    //Создание меню, вызываемого при долгом нажатии
    public void onCreateContextMenu(ContextMenu paramContextMenu, View paramView, ContextMenu.ContextMenuInfo paramContextMenuInfo) {
        super.onCreateContextMenu(paramContextMenu, paramView, paramContextMenuInfo);
        paramContextMenu.add(1, changeMain, 1, R.string.change_record);
        paramContextMenu.add(2, deleteMain, 2, R.string.delete_record);
        paramContextMenu.add(0, open, 0, R.string.opem);

    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int paramInt, @Nullable Bundle paramBundle) {
        return new MyCursorLoader(this, dbSQLM);
    }

    @Override
    //Вызов контекстного меню
    public boolean onCreateOptionsMenu(Menu paramMenu) {
        getMenuInflater().inflate(R.menu.ivkmenu_main, paramMenu);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbSQLM.close();
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> paramLoader, Cursor paramCursor) {
        this.mSCAM.swapCursor(paramCursor);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> paramLoader) {
    }

    @Override
    //Действия при нажатия на пункты меню
    public boolean onOptionsItemSelected(MenuItem paramMenuItem) {
        int i = paramMenuItem.getItemId();
        if (i != R.id.info_open) {
            if (i != R.id.ivk_open) {
                if (i != R.id.setting_open)
                    return true;
                startActivity(new Intent(this, Ivksettingactivity.class));
                return true;
            }
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://invoku.ru/")));
            return true;
        }
        startActivity(new Intent(this, Ivkinfo.class));
        return true;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    static class MyCursorLoader extends CursorLoader {
        IvkSqlMain db;

        public MyCursorLoader(Context paramContext, IvkSqlMain paramIvkSqlMain) {
            super(paramContext);
            db = paramIvkSqlMain;
        }

        public Cursor loadInBackground() {
            Cursor cursor = this.db.getFullTable();
            try {
                TimeUnit.SECONDS.sleep(0);
                return cursor;
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
                return cursor;
            }
        }
    }
}


