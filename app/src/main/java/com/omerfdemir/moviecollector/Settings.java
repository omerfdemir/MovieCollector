package com.omerfdemir.moviecollector;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

/**
 * Created by omerf on 6.01.2017.
 */

public class Settings extends Activity implements View.OnClickListener {
    RadioButton rbLight, rbDark;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;


    public void onCreate(Bundle savedInstanceState)
    { super.onCreate(savedInstanceState);
        rbDark = (RadioButton) findViewById(R.id.radioButtonDark);
        rbLight = (RadioButton) findViewById(R.id.radioButtonLight);
        preferences  = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        int theme = preferences.getInt("theme",1);

        editor = preferences.edit();
        editor.putInt("theme",1);
        setContentView(R.layout.activity_settings);

        findViewById(R.id.radioButtonDark).setOnClickListener(this);
        findViewById(R.id.radioButtonLight).setOnClickListener(this);

    }
    @Override
    public void onClick(View v)
    {
        // TODO Auto-generated method stub
        switch (v.getId())
        {
            case R.id.radioButtonLight:

                editor.putInt("theme",1);
                editor.commit();
                Toast.makeText(getApplicationContext(),"Please restart the application to applying changes",Toast.LENGTH_LONG).show();

                break;
            case R.id.radioButtonDark:

                editor.putInt("theme",0);
                editor.commit();
                Toast.makeText(getApplicationContext(),"Please restart the application to applying changes",Toast.LENGTH_LONG).show();
                break;

        }

    }
}


