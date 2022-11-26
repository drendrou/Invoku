package com.example.invoku;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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

public class Ivkwordactivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int change = 1;

    private static final int delete = 2;

    public static String mFirstWord_in = "";

    public static String mSecondWord_in = "";

    public static String num_in;

    public static SharedPreferences sp;

    public static Boolean trans;

    public static String transkript = "";

    FloatingActionButton back;

    IvkSql dbSQL;

    final Context mDialogContext = this;

    final Context mDialogMain = this;

    FloatingActionButton mFab;

    FloatingActionButton mFabSA;

    ListView mListViewWord;

    public int mNumberOfWord;

    public String mNumberOfWordString;

    SimpleCursorAdapter mSCA;

    public long sql_id;

    public int test;

    public Boolean timerboolean;

    public int timercheck;

    public String timercheckstring;

    public boolean onContextItemSelected(MenuItem paramMenuItem) {
        if (paramMenuItem.getItemId() == change) {
            if (trans == false) {
                final AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) paramMenuItem.getMenuInfo();
                View view = LayoutInflater.from(this.mDialogMain).inflate(R.layout.ivkdialog, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(this.mDialogMain);
                builder.setView(view);
                final EditText mFirstWordEditText = view.findViewById(R.id.mDialogFirstWord);
                final EditText mSecondWordEditText = view.findViewById(R.id.mDialogSecondWord);
                Cursor cursor = dbSQL.getTableWitfTrans(acmi.id);
                if (cursor != null) {
                    cursor.moveToFirst();
                    sql_id = cursor.getInt(0);
                    mFirstWord_in = cursor.getString(1);
                    mSecondWord_in = cursor.getString(2);
                    transkript = cursor.getString(4);
                    mFirstWordEditText.setText(mFirstWord_in);
                    mSecondWordEditText.setText(mSecondWord_in);
                }
                builder.setCancelable(true).setNegativeButton("отмена", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface param1DialogInterface, int param1Int) {
                        param1DialogInterface.cancel();
                    }
                }).setPositiveButton("ок", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface param1DialogInterface, int param1Int) {
                    }
                });
                final AlertDialog alertDialog = builder.create();
                alertDialog.show();
                Button button = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                View.OnClickListener onClickListener = new View.OnClickListener() {
                    public void onClick(View param1View) {
                        Ivkwordactivity.mFirstWord_in = mFirstWordEditText.getText().toString();
                        Ivkwordactivity.mSecondWord_in = mSecondWordEditText.getText().toString();
                        if (!Ivkwordactivity.mFirstWord_in.equals("")) {
                            if (!Ivkwordactivity.mSecondWord_in.equals("")) {
                                Ivkwordactivity.this.dbSQL.updateTable(Ivkwordactivity.this.sql_id, Ivkwordactivity.mFirstWord_in.trim(), Ivkwordactivity.mSecondWord_in.trim(), Ivkwordactivity.num_in, Ivkwordactivity.transkript.trim());
                                Ivkwordactivity.this.getSupportLoaderManager().getLoader(0).forceLoad();
                                alertDialog.cancel();
                                return;
                            }
                            Toast.makeText(Ivkwordactivity.this.getApplicationContext(), "Введите второе слово", Toast.LENGTH_SHORT).show();
                            Boolean wantToCloseDialog = false;
                            if (wantToCloseDialog)
                                alertDialog.dismiss();

                            return;
                        }
                        Toast.makeText(Ivkwordactivity.this.getApplicationContext(), "Введите первое слово", Toast.LENGTH_SHORT).show();
                        Boolean wantToCloseDialog = false;
                        if (wantToCloseDialog)
                            alertDialog.dismiss();

                    }
                };
                button.setOnClickListener(onClickListener);
            }
            else {
            final AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) paramMenuItem.getMenuInfo();
            View view = LayoutInflater.from(this.mDialogMain).inflate(R.layout.ivkdialogtrans, null);
            AlertDialog.Builder builder = new AlertDialog.Builder(this.mDialogMain);
            builder.setView(view);
            final EditText mFirstWordEditText = view.findViewById(R.id.mDialogFirstWordTrans);
            final EditText mSecondWordEditText = view.findViewById(R.id.mDialogSecondWordTrans);
            final EditText mTrans = view.findViewById(R.id.trans);
            Cursor cursor = dbSQL.getTableWitfTrans(acmi.id);
            if (cursor != null) {
                cursor.moveToFirst();
                sql_id = cursor.getInt(0);
                mFirstWord_in = cursor.getString(1);
                mSecondWord_in = cursor.getString(2);
                transkript = cursor.getString(4);
                mFirstWordEditText.setText(mFirstWord_in);
                mSecondWordEditText.setText(mSecondWord_in);
                mTrans.setText(transkript);
            }
            builder.setCancelable(true).setNegativeButton("отмена", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface param1DialogInterface, int param1Int) {
                    param1DialogInterface.cancel();
                }
            }).setPositiveButton("ок", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface param1DialogInterface, int param1Int) {
                }
            });
            final AlertDialog alertDialog = builder.create();
            alertDialog.show();
            Button button = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
            View.OnClickListener onClickListener = new View.OnClickListener() {
                public void onClick(View param1View) {
                    Ivkwordactivity.mFirstWord_in = mFirstWordEditText.getText().toString();
                    Ivkwordactivity.mSecondWord_in = mSecondWordEditText.getText().toString();
                    Ivkwordactivity.transkript = mTrans.getText().toString();
                    if (!Ivkwordactivity.mFirstWord_in.equals("")) {
                        if (!Ivkwordactivity.mSecondWord_in.equals("")) {
                            Ivkwordactivity.this.dbSQL.updateTable(Ivkwordactivity.this.sql_id, Ivkwordactivity.mFirstWord_in.trim(), Ivkwordactivity.mSecondWord_in.trim(), Ivkwordactivity.num_in, Ivkwordactivity.transkript.trim());
                            Ivkwordactivity.this.getSupportLoaderManager().getLoader(0).forceLoad();
                            alertDialog.cancel();
                            return;
                        }
                        Toast.makeText(Ivkwordactivity.this.getApplicationContext(), "Введите второе слово", Toast.LENGTH_SHORT).show();
                        Boolean wantToCloseDialog = false;
                        if (wantToCloseDialog)
                            alertDialog.dismiss();

                        return;
                    }
                    Toast.makeText(Ivkwordactivity.this.getApplicationContext(), "Введите первое слово", Toast.LENGTH_SHORT).show();
                    Boolean wantToCloseDialog = false;
                    if (wantToCloseDialog)
                        alertDialog.dismiss();

                }
            };
            button.setOnClickListener(onClickListener);
        }
            return true;
        }
        if (paramMenuItem.getItemId() == delete) {
            final AdapterView.AdapterContextMenuInfo acm2 = (AdapterView.AdapterContextMenuInfo) paramMenuItem.getMenuInfo();
            View view = LayoutInflater.from(mDialogMain).inflate(R.layout.ivkdelete, null);
            AlertDialog.Builder builder = new AlertDialog.Builder(mDialogMain);
            builder.setView(view);
            builder.setCancelable(true).setNegativeButton("отмена", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface param1DialogInterface, int param1Int) {
                    param1DialogInterface.cancel();
                }
            }).setPositiveButton("ок", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface param1DialogInterface, int param1Int) {
                    dbSQL.deleteTableWord(acm2.id);
                    getSupportLoaderManager().getLoader(0).forceLoad();
                }
            });
            builder.create().show();

        }
        return super.onContextItemSelected(paramMenuItem);
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.ivkactivity_wordactivity);
        mFab = findViewById(R.id.mWordActivityFab);
        mFabSA = findViewById(R.id.mWordActivityFabSA);
        back = findViewById(R.id.mWordActivityFabBack);
        dbSQL = new IvkSql(this);
        num_in = getIntent().getStringExtra("wordlanguage");
        mListViewWord = findViewById(R.id.WordActivityListViewWord);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        trans = sp.getBoolean("trans", false);
        Cursor cursor = dbSQL.getFullTableWithLangAndTrans(num_in);
        if (trans == false) {
            String[] arrayOfString = new String[2];
            arrayOfString[0] = "mFirst_word";
            arrayOfString[1] = "mSecond_word";
            int[] arrayOfInt = {R.id.outputfirst, R.id.outputsecond};
            SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this, R.layout.ivkoutput, cursor, arrayOfString, arrayOfInt, 0);
            mSCA = simpleCursorAdapter;
        } else {
            String[] arrayOfString = new String[3];

            arrayOfString[0] = "mFirst_word";

            arrayOfString[1] = "mSecond_word";

            arrayOfString[2] = "trans";
            int[] arrayOfInt = {R.id.outputtrans1, R.id.outputtrans2, R.id.outputtrans3};
            SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this, R.layout.ivkoutputwithtrans, cursor, arrayOfString, arrayOfInt, 0);
            mSCA = simpleCursorAdapter;
        }
        mListViewWord.setAdapter(mSCA);
        registerForContextMenu(mListViewWord);
        getSupportLoaderManager().initLoader(0, null, this);
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View param1View) {
                Intent intent = new Intent(Ivkwordactivity.this, IvkMainActivity.class);
                startActivity(intent);
            }
        });
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View param1View) {
                if (trans == false) {
                    View view = LayoutInflater.from(Ivkwordactivity.this.mDialogContext).inflate(R.layout.ivkdialog, null);
                    AlertDialog.Builder builder = new AlertDialog.Builder(Ivkwordactivity.this.mDialogContext);
                    builder.setView(view);
                    final EditText mFirstWordEditText = view.findViewById(R.id.mDialogFirstWord);
                    final EditText mSecondWordEditText = view.findViewById(R.id.mDialogSecondWord);
                    builder.setCancelable(true).setNegativeButton("отмена", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface param2DialogInterface, int param2Int) {
                            param2DialogInterface.cancel();
                        }
                    }).setPositiveButton("ок", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface param2DialogInterface, int param2Int) {
                        }
                    });
                    final AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                    Button button = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                    View.OnClickListener onClickListener = new View.OnClickListener() {
                        public void onClick(View param2View) {
                            mFirstWord_in = mFirstWordEditText.getText().toString();
                            mSecondWord_in = mSecondWordEditText.getText().toString();
                            if (!mFirstWord_in.equals("")) {
                                if (!mSecondWord_in.equals("")) {
                                    dbSQL.createNewTable(mFirstWord_in, mSecondWord_in, num_in, "");
                                    getSupportLoaderManager().getLoader(0).forceLoad();
                                    alertDialog.cancel();
                                    return;
                                }
                                Toast.makeText(Ivkwordactivity.
                                        this.getApplicationContext(), "Введите второе слово", Toast.LENGTH_SHORT).show();
                                Boolean wantToCloseDialog = false;
                                if (wantToCloseDialog)
                                    alertDialog.dismiss();

                                return;
                            }
                            Toast.makeText(Ivkwordactivity.
                                    this.getApplicationContext(), "Введите первое слово", Toast.LENGTH_SHORT).show();
                            Boolean wantToCloseDialog = false;
                            if (wantToCloseDialog)
                                alertDialog.dismiss();

                        }
                    };
                    button.setOnClickListener(onClickListener);
                } else {
                    View view = LayoutInflater.from(Ivkwordactivity.this.mDialogContext).inflate(R.layout.ivkdialogtrans, null);
                    AlertDialog.Builder builder = new AlertDialog.Builder(Ivkwordactivity.this.mDialogContext);
                    builder.setView(view);
                    final EditText mFirstWordEditText = view.findViewById(R.id.mDialogFirstWordTrans);
                    final EditText mSecondWordEditText = view.findViewById(R.id.mDialogSecondWordTrans);
                    final EditText mTrans = view.findViewById(R.id.trans);
                    builder.setCancelable(true).setNegativeButton("отмена", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface param2DialogInterface, int param2Int) {
                            param2DialogInterface.cancel();
                        }
                    }).setPositiveButton("ок", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface param2DialogInterface, int param2Int) {
                        }
                    });
                    final AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                    Button button = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                    View.OnClickListener onClickListener = new View.OnClickListener() {
                        public void onClick(View param2View) {
                            mFirstWord_in = mFirstWordEditText.getText().toString();
                            mSecondWord_in = mSecondWordEditText.getText().toString();
                            transkript = mTrans.getText().toString();
                            if (!mFirstWord_in.equals("")) {
                                if (!mSecondWord_in.equals("")) {
                                    dbSQL.createNewTable(mFirstWord_in, mSecondWord_in, num_in, transkript);
                                    getSupportLoaderManager().getLoader(0).forceLoad();
                                    alertDialog.cancel();
                                    return;
                                }
                                Toast.makeText(Ivkwordactivity.
                                        this.getApplicationContext(), "Введите второе слово", Toast.LENGTH_SHORT).show();
                                Boolean wantToCloseDialog = false;
                                if (wantToCloseDialog)
                                    alertDialog.dismiss();

                                return;
                            }
                            Toast.makeText(Ivkwordactivity.
                                    this.getApplicationContext(), "Введите первое слово", Toast.LENGTH_SHORT).show();
                            Boolean wantToCloseDialog = false;
                            if (wantToCloseDialog)
                                alertDialog.dismiss();

                        }
                    };
                    button.setOnClickListener(onClickListener);
                }
            }
        });
        this.mListViewWord.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> param1AdapterView, View param1View, int param1Int, long param1Long) {
                if (trans == false) {
                    View view = LayoutInflater.from(mDialogMain).inflate(R.layout.ivkdialog, null);
                    AlertDialog.Builder builder = new AlertDialog.Builder(Ivkwordactivity.this.mDialogMain);
                    builder.setView(view);
                    final EditText mFirstWordEditText = view.findViewById(R.id.mDialogFirstWord);
                    final EditText mSecondWordEditText = view.findViewById(R.id.mDialogSecondWord);
                    Cursor cursor = Ivkwordactivity.this.dbSQL.getTableWitfTrans(param1Long);
                    if (cursor != null) {
                        cursor.moveToFirst();
                        Ivkwordactivity.this.sql_id = cursor.getInt(0);
                        Ivkwordactivity.mFirstWord_in = cursor.getString(1);
                        Ivkwordactivity.mSecondWord_in = cursor.getString(2);
                        Ivkwordactivity.transkript = cursor.getString(4);
                        mFirstWordEditText.setText(Ivkwordactivity.mFirstWord_in);
                        mSecondWordEditText.setText(Ivkwordactivity.mSecondWord_in);
                    }
                    builder.setCancelable(true).setNegativeButton("отмена", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface param2DialogInterface, int param2Int) {
                            param2DialogInterface.cancel();
                        }
                    }).setPositiveButton("ок", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface param2DialogInterface, int param2Int) {
                        }
                    });
                    final AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                    Button button = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                    View.OnClickListener onClickListener = new View.OnClickListener() {
                        public void onClick(View param2View) {
                            Ivkwordactivity.mFirstWord_in = mFirstWordEditText.getText().toString();
                            Ivkwordactivity.mSecondWord_in = mSecondWordEditText.getText().toString();
                            if (!Ivkwordactivity.mFirstWord_in.equals("")) {
                                if (!Ivkwordactivity.mSecondWord_in.equals("")) {
                                    Ivkwordactivity.this.dbSQL.updateTable(Ivkwordactivity.this.sql_id, Ivkwordactivity.mFirstWord_in.trim(), Ivkwordactivity.mSecondWord_in.trim(), Ivkwordactivity.num_in, transkript);
                                    Ivkwordactivity.
                                            this.getSupportLoaderManager().getLoader(0).forceLoad();
                                    alertDialog.cancel();
                                    return;
                                }
                                Toast.makeText(Ivkwordactivity.
                                        this.getApplicationContext(), "Введите второе слово", Toast.LENGTH_SHORT).show();
                                if (Boolean.valueOf(false).booleanValue())
                                    alertDialog.dismiss();
                                return;
                            }
                            Toast.makeText(Ivkwordactivity.
                                    this.getApplicationContext(), "Введите первое слово", Toast.LENGTH_SHORT).show();
                            if (Boolean.valueOf(false).booleanValue())
                                alertDialog.dismiss();
                        }
                    };
                    button.setOnClickListener(onClickListener);
                } else {
                    View view = LayoutInflater.from(mDialogMain).inflate(R.layout.ivkdialogtrans, null);
                    AlertDialog.Builder builder = new AlertDialog.Builder(Ivkwordactivity.this.mDialogMain);
                    builder.setView(view);
                    final EditText mFirstWordEditText = view.findViewById(R.id.mDialogFirstWordTrans);
                    final EditText mSecondWordEditText = view.findViewById(R.id.mDialogSecondWordTrans);
                    final EditText mTrans = view.findViewById(R.id.trans);
                    Cursor cursor = Ivkwordactivity.this.dbSQL.getTableWitfTrans(param1Long);
                    if (cursor != null) {
                        cursor.moveToFirst();
                        Ivkwordactivity.this.sql_id = cursor.getInt(0);
                        Ivkwordactivity.mFirstWord_in = cursor.getString(1);
                        Ivkwordactivity.mSecondWord_in = cursor.getString(2);
                        Ivkwordactivity.transkript = cursor.getString(4);
                        mFirstWordEditText.setText(Ivkwordactivity.mFirstWord_in);
                        mSecondWordEditText.setText(Ivkwordactivity.mSecondWord_in);
                        mTrans.setText(Ivkwordactivity.transkript);
                    }
                    builder.setCancelable(true).setNegativeButton("отмена", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface param2DialogInterface, int param2Int) {
                            param2DialogInterface.cancel();
                        }
                    }).setPositiveButton("ок", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface param2DialogInterface, int param2Int) {
                        }
                    });
                    final AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                    Button button = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                    View.OnClickListener onClickListener = new View.OnClickListener() {
                        public void onClick(View param2View) {
                            Ivkwordactivity.mFirstWord_in = mFirstWordEditText.getText().toString();
                            Ivkwordactivity.mSecondWord_in = mSecondWordEditText.getText().toString();
                            Ivkwordactivity.transkript = mTrans.getText().toString();
                            if (!Ivkwordactivity.mFirstWord_in.equals("")) {
                                if (!Ivkwordactivity.mSecondWord_in.equals("")) {
                                    Ivkwordactivity.this.dbSQL.updateTable(Ivkwordactivity.this.sql_id, Ivkwordactivity.mFirstWord_in.trim(), Ivkwordactivity.mSecondWord_in.trim(), Ivkwordactivity.num_in, Ivkwordactivity.transkript.trim());
                                    Ivkwordactivity.
                                            this.getSupportLoaderManager().getLoader(0).forceLoad();
                                    alertDialog.cancel();
                                    return;
                                }
                                Toast.makeText(Ivkwordactivity.
                                        this.getApplicationContext(), "Введите второе слово", Toast.LENGTH_SHORT).show();
                                if (Boolean.valueOf(false).booleanValue())
                                    alertDialog.dismiss();
                                return;
                            }
                            Toast.makeText(Ivkwordactivity.
                                    this.getApplicationContext(), "Введите первое слово", Toast.LENGTH_SHORT).show();
                            if (Boolean.valueOf(false).booleanValue())
                                alertDialog.dismiss();
                        }
                    };
                    button.setOnClickListener(onClickListener);
                }
            }
        });
        this.mFabSA.setOnClickListener(new View.OnClickListener() {
            public void onClick(View param1View) {
                Cursor cursor = Ivkwordactivity.this.dbSQL.getFullTableWithLangAndTrans(Ivkwordactivity.num_in);
                test = cursor.getCount();
                mNumberOfWordString = Ivkwordactivity.sp.getString("numberofwordCod", "");
                try {
                    mNumberOfWord = Integer.parseInt(mNumberOfWordString);
                } catch (NumberFormatException numberFormatException) {
                }
                timerboolean = Boolean.valueOf(sp.getBoolean("timeron", false));
                if (timerboolean.booleanValue() == true) {
                    timercheckstring = Ivkwordactivity.sp.getString("timerset", "");
                    try {
                        Ivkwordactivity.this.timercheck = Integer.parseInt(Ivkwordactivity.this.timercheckstring);
                    } catch (NumberFormatException numberFormatException) {
                    }
                }
                if (Ivkwordactivity.this.mNumberOfWord == 0) {
                    Toast.makeText(Ivkwordactivity.this.getApplicationContext(), "Количество слов не может быть 0", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Ivkwordactivity.this.mNumberOfWord > Ivkwordactivity.this.test)
                    Toast.makeText(Ivkwordactivity.this.getApplicationContext(), "Общее количество слов не может быть меньше заданного количества слов", Toast.LENGTH_SHORT).show();
                if (Ivkwordactivity.this.mNumberOfWord <= Ivkwordactivity.this.test) {
                    if (timerboolean.booleanValue() == true) {
                        if (timercheck != 0) {
                            Intent intent1 = new Intent(Ivkwordactivity.this, Ivksecondactivity.class);
                            intent1.putExtra("num_in", Ivkwordactivity.num_in);
                            Ivkwordactivity.this.startActivity(intent1);
                            return;
                        }
                        Toast.makeText(Ivkwordactivity.this.getApplicationContext(), "Таймер не может быть 0", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Intent intent = new Intent(Ivkwordactivity.this, Ivksecondactivity.class);
                    intent.putExtra("num_in", Ivkwordactivity.num_in);
                    Ivkwordactivity.this.startActivity(intent);
                }
            }
        });
    }

    public void onCreateContextMenu(ContextMenu paramContextMenu, View paramView, ContextMenu.ContextMenuInfo paramContextMenuInfo) {
        super.onCreateContextMenu(paramContextMenu, paramView, paramContextMenuInfo);
        paramContextMenu.add(1, 2, 1, "Удалить");
        paramContextMenu.add(0, 1, 0, "Изменить");
    }

    @NonNull
    public Loader<Cursor> onCreateLoader(int paramInt, @Nullable Bundle paramBundle) {
        return new MyCursorLoader(this, dbSQL);
    }

    public boolean onCreateOptionsMenu(Menu paramMenu) {
        getMenuInflater().inflate(R.menu.ivkmenu_main, paramMenu);
        return true;
    }

    protected void onDestroy() {
        super.onDestroy();
        this.dbSQL.close();
    }

    public void onLoadFinished(@NonNull Loader<Cursor> paramLoader, Cursor paramCursor) {
        this.mSCA.swapCursor(paramCursor);
    }

    public void onLoaderReset(@NonNull Loader<Cursor> paramLoader) {
    }

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

    public void onResume() {
        super.onResume();
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        Cursor cursor = dbSQL.getFullTableWithLangAndTrans(num_in);
        trans = Boolean.valueOf(sp.getBoolean("trans", false));
        if (!trans.booleanValue()) {
            String[] arrayOfString1 = new String[2];
            arrayOfString1[0] = "mFirst_word";
            arrayOfString1[1] = "mSecond_word";
            int[] arrayOfInt1 = {R.id.outputfirst, R.id.outputsecond};
            SimpleCursorAdapter simpleCursorAdapter1 = new SimpleCursorAdapter(this, R.layout.ivkoutput, cursor, arrayOfString1, arrayOfInt1, 0);
            this.mSCA = simpleCursorAdapter1;
            this.mListViewWord.setAdapter(this.mSCA);
            registerForContextMenu(this.mListViewWord);
            getSupportLoaderManager().initLoader(0, null, this);
            return;
        } else {
            String[] arrayOfString = new String[3];
            arrayOfString[0] = "mFirst_word";
            arrayOfString[1] = "mSecond_word";
            arrayOfString[2] = "trans";
            int[] arrayOfInt = {R.id.outputtrans1, R.id.outputtrans2, R.id.outputtrans3};
            SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this, R.layout.ivkoutputwithtrans, cursor, arrayOfString, arrayOfInt, 0);
            this.mSCA = simpleCursorAdapter;
            this.mListViewWord.setAdapter(this.mSCA);
            registerForContextMenu(this.mListViewWord);
            getSupportLoaderManager().initLoader(0, null, this);
        }
    }

    static class MyCursorLoader extends CursorLoader {
        IvkSql db;

        public MyCursorLoader(Context param1Context, IvkSql param1IvkSql) {
            super(param1Context);
            this.db = param1IvkSql;
        }

        public Cursor loadInBackground() {
            return this.db.getFullTableWithLangAndTrans(Ivkwordactivity.num_in);
        }
    }
}


