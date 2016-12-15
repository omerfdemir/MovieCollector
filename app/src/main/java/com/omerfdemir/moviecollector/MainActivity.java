package com.omerfdemir.moviecollector;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button btn_details,btn_categories,btn_my_movies,btn_toplist,btn_latest_movies,btn_years,btn_directors;
    private TextView tv_movie_name,tv_imdb,tv_genre,tv_director;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Buttons on Home Screen
        btn_details = (Button) findViewById(R.id.btn_details);
        btn_categories = (Button) findViewById(R.id.btn_categories);
        btn_my_movies = (Button) findViewById(R.id.btn_my_movies);
        btn_toplist = (Button) findViewById(R.id.btn_toplist);
        btn_latest_movies = (Button) findViewById(R.id.btn_latest_movies);
        btn_years = (Button) findViewById(R.id.btn_years);
        btn_directors = (Button) findViewById(R.id.btn_directors);
        //Text Views on Home Screen
        tv_movie_name = (TextView) findViewById(R.id.tv_movie_name);
        tv_imdb = (TextView) findViewById(R.id.tv_imdb);
        tv_genre = (TextView) findViewById(R.id.tv_genre);
        tv_director = (TextView) findViewById(R.id.tv_director);

    }

    @Override
    protected void onResume() {
        super.onResume();

        btn_categories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),Categories.class);
                startActivity(i);
            }
        });

        btn_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),SingleMovie.class);
                startActivity(i);
            }
        });

        btn_toplist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),Toplist.class);
                startActivity(i);
            }
        });

        btn_latest_movies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),LatestMovies.class);
                startActivity(i);
            }
        });

        btn_years.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),Years.class);
                startActivity(i);
            }
        });
        btn_directors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),Directors.class);
                startActivity(i);
            }
        });

    }
}
