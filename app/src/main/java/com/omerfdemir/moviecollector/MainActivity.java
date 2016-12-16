package com.omerfdemir.moviecollector;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private Button btn_details, btn_categories, btn_my_movies, btn_toplist, btn_latest_movies, btn_years, btn_directors;
    private TextView tv_movie_name, tv_imdb, tv_genre, tv_director;
    ArrayAdapter<String> adapter2;
    ArrayList<HashMap<String, String>> movie_list;
    String[] movie_names;
    int[] movie_ids;
    ListView lv2;
    Random random = new Random();
    int movie_id = random.nextInt(660-1) + 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Database movies_db = new Database(getApplicationContext());
        movie_list = movies_db.categories();
            final Intent intent = getIntent();
            Database movie_db = new Database(getApplicationContext());
            HashMap<String, String> map = movie_db.movieMain(movie_id);


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
            tv_movie_name.setText((map.get("name")));
            tv_imdb.setText(map.get("imdb_score"));
            tv_genre.setText(map.get("genre"));
            tv_director.setText(map.get(("director")));

        }



    protected void onResume() {
        super.onResume();

        btn_categories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Categories.class);
                startActivity(i);
            }
        });

        btn_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),SingleMovie.class);
                intent.putExtra("movie_id",movie_id);
                startActivity(intent);
            }
        });

        btn_toplist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Toplist.class);
                startActivity(i);
            }
        });

        btn_latest_movies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), LatestMovies.class);
                startActivity(i);
            }
        });

        btn_years.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Years.class);
                startActivity(i);
            }
        });
        btn_directors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Directors.class);
                startActivity(i);
            }
        });

    }
}
