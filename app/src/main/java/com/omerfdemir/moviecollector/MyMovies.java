package com.omerfdemir.moviecollector;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by omerf on 5.01.2017.
 */

public class MyMovies extends AppCompatActivity {
    SharedPreferences preferences ;

    private Button btn1,btn2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int theme = preferences.getInt("theme",1);
        if (theme==0){
            setTheme(R.style.AppThemeDark);
        }
        else{
            setTheme(R.style.AppTheme);

        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mymovies);
        btn1= (Button) findViewById(R.id.watched);
        btn2= (Button) findViewById(R.id.watchlist);

    }

    @Override
    protected void onResume() {
        super.onResume();
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MyMoviesList.class);
                intent.putExtra("type",1);
                startActivity(intent);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MyMoviesList.class);
                intent.putExtra("type",0);
                startActivity(intent);
            }
        });

    }
}
