package com.example.invoku;
//Тестирование
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class Ivksecondactivity extends AppCompatActivity {

    //Вызов xml файла, объявление переменных
    public static String ostalos = "Осталось: ";

    public static String writestring;

    public static String wrongstring;

    Boolean checkinvent;

    public Boolean checksorting;

    CountDownTimer countDownTimer;

    IvkSql dbSQL;

    ivkSqlResult dbSQLresult;

    public String mFirstWord_in = "";

    public int mNumberOfWord;

    public String mNumberOfWordString;

    public String mSecondWord_in = "";

    TextView mTestFirstWord;

    EditText mTestSecondWord;

    public String mTestSeconwWordString;

    public int n = 1;

    public Integer num_in = 0;

    String num_insecond;

    public Button result;

    SharedPreferences sp;

    public int timer;

    TextView timerText;

    public String timercheck;

    public int write = 0;

    public int wrong = 0;

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        //Объявление xml объектов и присвоение им данных
        setContentView(R.layout.ivkactivity_secondactivity);
        this.mTestFirstWord = (TextView) findViewById(R.id.mSecondActivityFirstWord);
        this.mTestSecondWord = (EditText) findViewById(R.id.mSecondActivitySecondWord);
        this.result = (Button) findViewById(R.id.SecondActivityResult);
        this.dbSQL = new IvkSql(this);
        this.dbSQLresult = new ivkSqlResult(this);
        this.dbSQLresult.deleteTable();
        this.num_insecond = getIntent().getStringExtra("num_in");
        final Cursor newTable = this.dbSQL.getFullTableWithLangAndRand(this.num_insecond);
        this.sp = PreferenceManager.getDefaultSharedPreferences(this);
        this.timerText = (TextView) findViewById(R.id.SecondActivitytimer);
        this.checkinvent = Boolean.valueOf(this.sp.getBoolean("invent", false));
        newTable.moveToFirst();
        this.num_in = Integer.valueOf(newTable.getInt(0));
        this.mFirstWord_in = newTable.getString(1);
        this.mSecondWord_in = newTable.getString(2);
        //Вывод на экран слова или его перевода в зависимости от того, включена ли пользователем функция инвентирования тестирования
        if (!this.checkinvent.booleanValue()) {
            this.mTestFirstWord.setText(this.mFirstWord_in);
        } else {
            this.mTestFirstWord.setText(this.mSecondWord_in);
        }
        this.result.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                //Действия при нажатии на кнопку. В зависимости от того, соответствует ли введенное пользователем правильному переводу и написанию,
                //данные будут сохранены как "верные" и "неверные". Написанное пользователем также сохраняется

                if (!Ivksecondactivity.this.checkinvent.booleanValue()) {
                    Ivksecondactivity.this.mTestSeconwWordString = Ivksecondactivity.this.mTestSecondWord.getText().toString();
                    if (Ivksecondactivity.this.n < 1 + Ivksecondactivity.this.mNumberOfWord) {
                        if (Ivksecondactivity.this.mTestSeconwWordString.trim().equalsIgnoreCase(Ivksecondactivity.this.mSecondWord_in)) {
                            Ivksecondactivity ivksecondactivity1 = Ivksecondactivity.this;
                            ivksecondactivity1.write = 1 + ivksecondactivity1.write;
                            Ivksecondactivity.this.dbSQLresult.createNewTable(Ivksecondactivity.this.mFirstWord_in.trim(), Ivksecondactivity.this.mSecondWord_in.trim(), Ivksecondactivity.this.mTestSeconwWordString.trim(), "Верно");
                            Ivksecondactivity.this.mTestSecondWord.setText("");
                        } else {
                            Ivksecondactivity ivksecondactivity1 = Ivksecondactivity.this;
                            ivksecondactivity1.wrong = 1 + ivksecondactivity1.wrong;
                            Ivksecondactivity.this.dbSQLresult.createNewTable(Ivksecondactivity.this.mFirstWord_in.trim(), Ivksecondactivity.this.mSecondWord_in.trim(), Ivksecondactivity.this.mTestSeconwWordString.trim(), "Неверно");
                            Ivksecondactivity.this.mTestSecondWord.setText("");
                        }
                        if (Ivksecondactivity.this.checksorting.booleanValue() == true)
                            Ivksecondactivity.this.countDownTimer.start();
                        if (Ivksecondactivity.this.n != Ivksecondactivity.this.mNumberOfWord) {
                            newTable.moveToNext();
                            Ivksecondactivity.this.num_in = Integer.valueOf(newTable.getInt(0));
                            Ivksecondactivity.this.mFirstWord_in = newTable.getString(1);
                            Ivksecondactivity.this.mSecondWord_in = newTable.getString(2);
                            Ivksecondactivity.this.mTestFirstWord.setText(Ivksecondactivity.this.mFirstWord_in);
                        }
                    }
                    Ivksecondactivity ivksecondactivity = Ivksecondactivity.this;
                    ivksecondactivity.n = 1 + ivksecondactivity.n;
                    if (Ivksecondactivity.this.n == 1 + Ivksecondactivity.this.mNumberOfWord) {
                        Intent intent = new Intent(Ivksecondactivity.this, ivkresult.class);
                        intent.putExtra("num_in", Ivksecondactivity.this.num_insecond);
                        Ivksecondactivity.writestring = Integer.toString(Ivksecondactivity.this.write);
                        Ivksecondactivity.wrongstring = Integer.toString(Ivksecondactivity.this.wrong);
                        intent.putExtra("write", Ivksecondactivity.writestring);
                        intent.putExtra("wrong", Ivksecondactivity.wrongstring);
                        Ivksecondactivity.this.startActivity(intent);
                        return;
                    }
                } else {
                    Ivksecondactivity.this.mTestSeconwWordString = Ivksecondactivity.this.mTestSecondWord.getText().toString();
                    if (Ivksecondactivity.this.n < 1 + Ivksecondactivity.this.mNumberOfWord) {
                        if (Ivksecondactivity.this.mTestSeconwWordString.trim().equalsIgnoreCase(Ivksecondactivity.this.mFirstWord_in)) {
                            Ivksecondactivity ivksecondactivity1 = Ivksecondactivity.this;
                            ivksecondactivity1.write = 1 + ivksecondactivity1.write;
                            Ivksecondactivity.this.dbSQLresult.createNewTable(Ivksecondactivity.this.mSecondWord_in.trim(), Ivksecondactivity.this.mFirstWord_in.trim(), Ivksecondactivity.this.mTestSeconwWordString.trim(), "Верно");
                            Ivksecondactivity.this.mTestSecondWord.setText("");
                        } else {
                            Ivksecondactivity ivksecondactivity1 = Ivksecondactivity.this;
                            ivksecondactivity1.wrong = 1 + ivksecondactivity1.wrong;
                            Ivksecondactivity.this.dbSQLresult.createNewTable(Ivksecondactivity.this.mSecondWord_in.trim(), Ivksecondactivity.this.mFirstWord_in.trim(), Ivksecondactivity.this.mTestSeconwWordString.trim(), "Неверно");
                            Ivksecondactivity.this.mTestSecondWord.setText("");
                        }
                        if (Ivksecondactivity.this.checksorting.booleanValue() == true)
                            Ivksecondactivity.this.countDownTimer.start();
                        if (Ivksecondactivity.this.n != Ivksecondactivity.this.mNumberOfWord) {
                            newTable.moveToNext();
                            Ivksecondactivity.this.num_in = Integer.valueOf(newTable.getInt(0));
                            Ivksecondactivity.this.mFirstWord_in = newTable.getString(1);
                            Ivksecondactivity.this.mSecondWord_in = newTable.getString(2);
                            Ivksecondactivity.this.mTestFirstWord.setText(Ivksecondactivity.this.mSecondWord_in);
                        }
                    }
                    Ivksecondactivity ivksecondactivity = Ivksecondactivity.this;
                    ivksecondactivity.n = 1 + ivksecondactivity.n;
                    if (Ivksecondactivity.this.n == 1 + Ivksecondactivity.this.mNumberOfWord) {
                        Intent intent = new Intent(Ivksecondactivity.this, ivkresult.class);
                        intent.putExtra("num_in", Ivksecondactivity.this.num_insecond);
                        Ivksecondactivity.writestring = Integer.toString(Ivksecondactivity.this.write);
                        Ivksecondactivity.wrongstring = Integer.toString(Ivksecondactivity.this.wrong);
                        intent.putExtra("write", Ivksecondactivity.writestring);
                        intent.putExtra("wrong", Ivksecondactivity.wrongstring);
                        Ivksecondactivity.this.startActivity(intent);
                    }
                }
            }
        });
        //Объявление таймера
        this.checksorting = Boolean.valueOf(this.sp.getBoolean("timeron", false));
        if (!this.checksorting.booleanValue()) {
            this.timerText.setText("");
            return;
        }
        //Получение длительности таймера из настроек
        this.timercheck = this.sp.getString("timerset", "");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.timercheck);
        stringBuilder.append("000");
        this.timercheck = stringBuilder.toString();
        try {
            this.timer = Integer.parseInt(this.timercheck);
        } catch (NumberFormatException numberFormatException) {
        }
        CountDownTimer countDownTimer1 = new CountDownTimer((1000 + this.timer), 1000L) {
            public void onFinish() {
                if (!Ivksecondactivity.this.checkinvent.booleanValue()) {
                    //Действия при окончании таймера. Полностью соответствуют нажатию кнопке. Если пользователь успел ввести ответ, то он также корректно сохраняется
                    Ivksecondactivity.this.mTestSeconwWordString = Ivksecondactivity.this.mTestSecondWord.getText().toString();
                    if (Ivksecondactivity.this.n < 1 + Ivksecondactivity.this.mNumberOfWord) {
                        if (Ivksecondactivity.this.mTestSeconwWordString.trim().equalsIgnoreCase(Ivksecondactivity.this.mSecondWord_in)) {
                            Ivksecondactivity ivksecondactivity1 = Ivksecondactivity.this;
                            ivksecondactivity1.write = 1 + ivksecondactivity1.write;
                            Ivksecondactivity.this.dbSQLresult.createNewTable(Ivksecondactivity.this.mFirstWord_in.trim(), Ivksecondactivity.this.mSecondWord_in.trim(), Ivksecondactivity.this.mTestSeconwWordString.trim(), "Верно");
                            Ivksecondactivity.this.mTestSecondWord.setText("");
                        } else {
                            Ivksecondactivity ivksecondactivity1 = Ivksecondactivity.this;
                            ivksecondactivity1.wrong = 1 + ivksecondactivity1.wrong;
                            Ivksecondactivity.this.dbSQLresult.createNewTable(Ivksecondactivity.this.mFirstWord_in.trim(), Ivksecondactivity.this.mSecondWord_in.trim(), Ivksecondactivity.this.mTestSeconwWordString.trim(), "Неверно");
                            Ivksecondactivity.this.mTestSecondWord.setText("");
                        }
                        if (Ivksecondactivity.this.checksorting.booleanValue() == true)
                            Ivksecondactivity.this.countDownTimer.start();
                        if (Ivksecondactivity.this.n != Ivksecondactivity.this.mNumberOfWord) {
                            newTable.moveToNext();
                            Ivksecondactivity.this.num_in = Integer.valueOf(newTable.getInt(0));
                            Ivksecondactivity.this.mFirstWord_in = newTable.getString(1);
                            Ivksecondactivity.this.mSecondWord_in = newTable.getString(2);
                            Ivksecondactivity.this.mTestFirstWord.setText(Ivksecondactivity.this.mFirstWord_in);
                        }
                    }
                    Ivksecondactivity ivksecondactivity = Ivksecondactivity.this;
                    ivksecondactivity.n = 1 + ivksecondactivity.n;
                    if (Ivksecondactivity.this.n == 1 + Ivksecondactivity.this.mNumberOfWord) {
                        Intent intent = new Intent(Ivksecondactivity.this, ivkresult.class);
                        intent.putExtra("num_in", Ivksecondactivity.this.num_insecond);
                        Ivksecondactivity.writestring = Integer.toString(Ivksecondactivity.this.write);
                        Ivksecondactivity.wrongstring = Integer.toString(Ivksecondactivity.this.wrong);
                        intent.putExtra("write", Ivksecondactivity.writestring);
                        intent.putExtra("wrong", Ivksecondactivity.wrongstring);
                        Ivksecondactivity.this.startActivity(intent);
                        return;
                    }
                } else {
                    Ivksecondactivity.this.mTestSeconwWordString = Ivksecondactivity.this.mTestSecondWord.getText().toString();
                    if (Ivksecondactivity.this.n < 1 + Ivksecondactivity.this.mNumberOfWord) {
                        if (Ivksecondactivity.this.mTestSeconwWordString.trim().equalsIgnoreCase(Ivksecondactivity.this.mFirstWord_in)) {
                            Ivksecondactivity ivksecondactivity1 = Ivksecondactivity.this;
                            ivksecondactivity1.write = 1 + ivksecondactivity1.write;
                            Ivksecondactivity.this.dbSQLresult.createNewTable(Ivksecondactivity.this.mSecondWord_in.trim(), Ivksecondactivity.this.mFirstWord_in.trim(), Ivksecondactivity.this.mTestSeconwWordString.trim(), "Верно");
                            Ivksecondactivity.this.mTestSecondWord.setText("");
                        } else {
                            Ivksecondactivity ivksecondactivity1 = Ivksecondactivity.this;
                            ivksecondactivity1.wrong = 1 + ivksecondactivity1.wrong;
                            Ivksecondactivity.this.dbSQLresult.createNewTable(Ivksecondactivity.this.mSecondWord_in.trim(), Ivksecondactivity.this.mFirstWord_in.trim(), Ivksecondactivity.this.mTestSeconwWordString.trim(), "Неверно");
                            Ivksecondactivity.this.mTestSecondWord.setText("");
                        }
                        if (Ivksecondactivity.this.checksorting.booleanValue() == true)
                            Ivksecondactivity.this.countDownTimer.start();
                        if (Ivksecondactivity.this.n != Ivksecondactivity.this.mNumberOfWord) {
                            newTable.moveToNext();
                            Ivksecondactivity.this.num_in = Integer.valueOf(newTable.getInt(0));
                            Ivksecondactivity.this.mFirstWord_in = newTable.getString(1);
                            Ivksecondactivity.this.mSecondWord_in = newTable.getString(2);
                            Ivksecondactivity.this.mTestFirstWord.setText(Ivksecondactivity.this.mSecondWord_in);
                        }
                    }
                    Ivksecondactivity ivksecondactivity = Ivksecondactivity.this;
                    ivksecondactivity.n = 1 + ivksecondactivity.n;
                    if (Ivksecondactivity.this.n == 1 + Ivksecondactivity.this.mNumberOfWord) {
                        Intent intent = new Intent(Ivksecondactivity.this, ivkresult.class);
                        intent.putExtra("num_in", Ivksecondactivity.this.num_insecond);
                        Ivksecondactivity.writestring = Integer.toString(Ivksecondactivity.this.write);
                        Ivksecondactivity.wrongstring = Integer.toString(Ivksecondactivity.this.wrong);
                        intent.putExtra("write", Ivksecondactivity.writestring);
                        intent.putExtra("wrong", Ivksecondactivity.wrongstring);
                        Ivksecondactivity.this.startActivity(intent);
                    }
                }
            }

            public void onTick(long paramLong) {
                //Вывод работы таймера
                if (Ivksecondactivity.this.n <= Ivksecondactivity.this.mNumberOfWord) {
                    TextView textView = Ivksecondactivity.this.timerText;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(Ivksecondactivity.ostalos);
                    stringBuilder.append(paramLong / 1000L);
                    textView.setText(stringBuilder.toString());
                }
            }
        };
        this.countDownTimer = countDownTimer1.start();
    }

    public void onDestroy() {
        super.onDestroy();
    }

    public void onResume() {
        super.onResume();
        this.mNumberOfWordString = this.sp.getString("numberofwordCod", "");
        try {
            this.mNumberOfWord = Integer.parseInt(this.mNumberOfWordString);
            return;
        } catch (NumberFormatException numberFormatException) {
            return;
        }
    }
}

