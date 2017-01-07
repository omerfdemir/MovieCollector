package com.omerfdemir.moviecollector;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;

/**
 * Created by omerf on 15.12.2016.
 */

public class SplashScreen extends Activity{
    public static int SPLASH_TIME_OUT = 3000;
    SharedPreferences preferences ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int theme = preferences.getInt("theme",1);
        if (theme==0){
            setTheme(R.style.AppThemeDark);
}
        else{
            setTheme(R.style.AppTheme);

        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashScreen.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        },SPLASH_TIME_OUT);
    }
}
