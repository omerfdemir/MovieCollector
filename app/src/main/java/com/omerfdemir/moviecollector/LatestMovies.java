package com.omerfdemir.moviecollector;

import android.content.Intent;
import android.os.Bundle;
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

public class LatestMovies extends AppCompatActivity {
    ArrayAdapter<String> adapter2;
    ArrayList<HashMap<String, String>> movie_list;
    String[] movie_names;
    int[] movie_ids;
    ListView lv2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_latest_movies);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Database movies_db = new Database(getApplicationContext());
        movie_list = movies_db.latestMovies();
        if (movie_list.size() == 0) {
            Toast.makeText(getApplicationContext(), "There is no Database", Toast.LENGTH_LONG).show();
        } else {
            movie_names = new String[movie_list.size()];
            movie_ids = new int[movie_list.size()];
            for (int i = 0; i < movie_list.size(); i++) {
                movie_names[i] = movie_list.get(i).get("Name");
                movie_ids[i] = Integer.parseInt(movie_list.get(i).get("movie_id"));
            }
            lv2 = (ListView) findViewById(R.id.latest_movies);
            adapter2 = new ArrayAdapter<String>(this,R.layout.list_item_2,R.id.li_tv2,movie_names);
            lv2.setAdapter(adapter2);
        }
        lv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Intent intent = new Intent(getApplicationContext(),SingleMovie.class);
                intent.putExtra("movie_id",movie_ids[arg2]);
                startActivity(intent);
            }
        });
    }
}
