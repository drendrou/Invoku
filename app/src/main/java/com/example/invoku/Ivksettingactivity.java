package com.example.invoku;
//Файл настроек
import android.os.Bundle;
import android.preference.PreferenceActivity;



public class Ivksettingactivity extends PreferenceActivity {
  public void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    addPreferencesFromResource(R.xml.ivksettings);
  }
}


