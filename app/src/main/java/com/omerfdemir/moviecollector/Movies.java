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
 * Created by omerf on 14.11.2016.
 */

public class Movies extends AppCompatActivity{
    int genre_id;
    ArrayAdapter<String> adapter;
    String[] movie_names;
    int [] movie_ids;
    ListView lv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        final Intent intent = getIntent();
        genre_id = intent.getIntExtra("genre_id",1);
        Database movies_db = new Database(getApplicationContext());
        ArrayList<HashMap<String,String>> movie_list = movies_db.allMovies(genre_id);
        if (movie_list.size() == 0) {
            Toast.makeText(getApplicationContext(), "There is no Database", Toast.LENGTH_LONG).show();
        }
        else{
            movie_names = new String[movie_list.size()];
            movie_ids = new int[movie_list.size()];
            for (int i =0 ; i < movie_list.size() ; i++){
                movie_names[i]=movie_list.get(i).get("Name");
                movie_ids[i] = Integer.parseInt(movie_list.get(i).get("movie_id"));
            }
            lv = (ListView) findViewById(R.id.movies);
            adapter = new ArrayAdapter<String>(this,R.layout.list_item,R.id.li_tv,movie_names);
            lv.setAdapter(adapter);

        }
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0,View arg1, int arg2, long arg3) {
                Intent intent = new Intent(getApplicationContext(),SingleMovie.class);
                intent.putExtra("movie_id",movie_ids[arg2]);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {


        super.onResume();


    }
}
