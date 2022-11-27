package com.example.invoku;

//Вкладка с текстовой инфориацией о программе и как ей пользоваться.

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;


//Вызов текстовой информации
public class Ivkinfo extends AppCompatActivity {
  protected void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    setContentView(R.layout.ivkactivity_info);
  }

  //Вызов контекстного меню
  public boolean onCreateOptionsMenu(Menu paramMenu) {
    getMenuInflater().inflate(R.menu.ivkmenu_main, paramMenu);
    return true;
  }
  //Действия при нажатии на пункты меню
  public boolean onOptionsItemSelected(MenuItem paramMenuItem) {
    int i = paramMenuItem.getItemId();
    if (i != R.id.ivk_open) {
      if (i != R.id.setting_open)
        return true; 
      startActivity(new Intent(this, Ivksettingactivity.class));
      return true;
    } 
    startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://invoku.ru/")));
    return true;
  }
}


