package com.omerfdemir.moviecollector;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.HashMap;

/**
 * Created by omerf on 14.11.2016.
 */

public class SingleMovie extends AppCompatActivity{
    int movie_id;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_movie);

        TextView tv_imdb_score = (TextView) findViewById(R.id.tv_imdb);
        TextView tv_genre = (TextView) findViewById(R.id.tv_genre);
        TextView tv_director = (TextView) findViewById(R.id.tv_director);
        TextView tv_year = (TextView) findViewById(R.id.tv_year);
        TextView tv_writers = (TextView) findViewById(R.id.tv_writers);
        TextView tv_stars = (TextView) findViewById(R.id.tv_stars);
        TextView tv_storyline = (TextView) findViewById(R.id.tv_storyline);
        final Intent intent = getIntent();
        movie_id = intent.getIntExtra("movie_id",1);
        Database movie_db = new Database(getApplicationContext());

        HashMap<String,String> map = movie_db.movieDetails(movie_id);
        setTitle(map.get("name"));
        tv_imdb_score.setText(map.get("imdb_score"));
        tv_genre.setText(map.get("genre"));
        tv_director.setText(map.get(("director")));
        tv_year.setText(map.get("year"));
        tv_writers.setText(map.get("writer"));
        tv_stars.setText(map.get("stars"));
        tv_storyline.setText((map.get("details")));

    }
}
