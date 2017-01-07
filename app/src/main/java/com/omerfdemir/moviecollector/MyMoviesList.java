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
 * Created by omerf on 5.01.2017.
 */

public class MyMoviesList extends AppCompatActivity {
    SharedPreferences preferences;
    ListView lv;
    String[] movie_names;
    int[] movie_ids;
    ArrayAdapter<String> adapter;

    int type;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int theme = preferences.getInt("theme", 1);
        if (theme == 0) {
            setTheme(R.style.AppThemeDark);
        } else {
            setTheme(R.style.AppTheme);

        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mymovieslist);

        final Intent intent = getIntent();
        type = intent.getIntExtra("type",1);
        System.out.println(type);
        if (type ==1) {
            setTitle("Watched Movies");
            Database movies_db = new Database(getApplicationContext());
            ArrayList<HashMap<String, String>> movie_list = movies_db.watchedMovies();
            if (movie_list.size() == 0) {
                Toast.makeText(getApplicationContext(), "There is no Database", Toast.LENGTH_LONG).show();
            } else {
                movie_names = new String[movie_list.size()];
                movie_ids = new int[movie_list.size()];
                for (int i = 0; i < movie_list.size(); i++) {
                    movie_names[i] = movie_list.get(i).get("Name");
                    movie_ids[i] = Integer.parseInt(movie_list.get(i).get("movie_id"));

                }

                lv = (ListView) findViewById(R.id.mymovieslist);
                adapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.li_tv, movie_names);
                lv.setAdapter(adapter);

            }
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                    Intent intent = new Intent(getApplicationContext(), SingleMovie.class);
                    intent.putExtra("movie_id", movie_ids[arg2]);
                    startActivity(intent);
                }
            });



        } else if (type ==0) {
            setTitle("Watchlist");
            Database movies_db = new Database(getApplicationContext());
            ArrayList<HashMap<String, String>> movie_list = movies_db.watchlistMovies();
            if (movie_list.size() == 0) {
                Toast.makeText(getApplicationContext(), "There is no Database", Toast.LENGTH_LONG).show();
            } else {
                movie_names = new String[movie_list.size()];
                movie_ids = new int[movie_list.size()];
                for (int i = 0; i < movie_list.size(); i++) {
                    movie_names[i] = movie_list.get(i).get("Name");
                    movie_ids[i] = Integer.parseInt(movie_list.get(i).get("movie_id"));

                }

                lv = (ListView) findViewById(R.id.mymovieslist);
                adapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.li_tv, movie_names);
                lv.setAdapter(adapter);

            }
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                    Intent intent = new Intent(getApplicationContext(), SingleMovie.class);
                    intent.putExtra("movie_id", movie_ids[arg2]);
                    startActivity(intent);
                }
            });

        }
    }
}
