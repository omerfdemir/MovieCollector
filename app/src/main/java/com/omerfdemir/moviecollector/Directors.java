package com.omerfdemir.moviecollector;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by omerf on 16.11.2016.
 */

public class Directors extends AppCompatActivity{
    SharedPreferences preferences ;
    ArrayAdapter<String> adapter4;
    ArrayList<HashMap<String, String>> director_list;
    String[] director_names;
    int[] director_ids;
    ListView lv4;
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
        setContentView(R.layout.activity_directors);
        setTitle("Directors");

    }



    @Override
    protected void onResume() {
        super.onResume();
        Database movies_db = new Database(getApplicationContext());
        director_list = movies_db.directorList();
        if (director_list.size() == 0) {
            Toast.makeText(getApplicationContext(), "There is no Database", Toast.LENGTH_LONG).show();
        } else {
            director_names = new String[director_list.size()];
            director_ids = new int[director_list.size()];
            for (int i = 0; i < director_list.size(); i++) {
                director_ids[i] = Integer.parseInt(director_list.get(i).get("id"));
                director_names[i] = director_list.get(i).get("name");
            }
            lv4 = (ListView) findViewById(R.id.directors);
            adapter4 = new ArrayAdapter<String>(this,R.layout.list_item_4,R.id.li_tv4,director_names);
            lv4.setAdapter(adapter4);
        }

        lv4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0,View arg1, int arg2, long arg3) {
                Intent intent = new Intent(getApplicationContext(),Movies.class);
                intent.putExtra("director_id",director_ids[arg2]);
                intent.putExtra("name",director_names[arg2]);
                startActivity(intent);
            }
        });
    }

}
