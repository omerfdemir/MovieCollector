package com.omerfdemir.moviecollector;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;


/**
 * Created by omerf on 14.11.2016.
 */

public class SingleMovie extends AppCompatActivity{
    SharedPreferences preferences ;
    Set ids;
    int movie_id;
    int[] watched_movie_ids;
    int[] watchlist_movie_ids;
    String watch;
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
        setContentView(R.layout.activity_single_movie);
        ImageView img_view = (ImageView) findViewById(R.id.single_iv);

        TextView tv_imdb_score = (TextView) findViewById(R.id.tv_imdb);
        TextView tv_genre = (TextView) findViewById(R.id.tv_genre);
        TextView tv_director = (TextView) findViewById(R.id.tv_director);
        TextView tv_year = (TextView) findViewById(R.id.tv_year);
        TextView tv_writers = (TextView) findViewById(R.id.tv_writers);
        TextView tv_stars = (TextView) findViewById(R.id.tv_stars);
        TextView tv_storyline = (TextView) findViewById(R.id.tv_storyline);
        final Intent intent = getIntent();
        movie_id = intent.getIntExtra("movie_id", 1);
        Database movie_db = new Database(getApplicationContext());

        HashMap<String, String> map = movie_db.movieDetails(movie_id);
        setTitle(map.get("name"));
        tv_imdb_score.setText(map.get("imdb_score"));
        tv_genre.setText(map.get("genre"));
        tv_director.setText(map.get(("director")));
        tv_year.setText(map.get("year"));
        tv_writers.setText(map.get("writer"));
        tv_stars.setText(map.get("stars"));
        tv_storyline.setText((map.get("details")));
        //Resimleri çekmek
        Resources resources = this.getResources();
        final int resourceId = resources.getIdentifier(map.get("photos"),
                "drawable", this.getPackageName());
        img_view.setImageResource(resourceId);

       Database movies_db = new Database(getApplicationContext());

        ArrayList<HashMap<String, String>> watched_movie_list = movies_db.watchedMovies();

        if (watched_movie_list.size() == 0) {
            Toast.makeText(getApplicationContext(), "There is no Database", Toast.LENGTH_LONG).show();
        } else {
            watched_movie_ids = new int[watched_movie_list.size()];
            for (int i = 0; i < watched_movie_list.size(); i++) {

                watched_movie_ids[i] = Integer.parseInt(watched_movie_list.get(i).get("movie_id"));


            }
        }


        ArrayList<HashMap<String, String>> watchlist_movie_list = movies_db.watchlistMovies();

        if (watchlist_movie_list.size() == 0) {
            Toast.makeText(getApplicationContext(), "There is no Database", Toast.LENGTH_LONG).show();
        } else {
            watchlist_movie_ids = new int[watchlist_movie_list.size()];
            for (int i = 0; i < watchlist_movie_list.size(); i++) {

                watchlist_movie_ids[i] = Integer.parseInt(watchlist_movie_list.get(i).get("movie_id"));



            }
        }
        for(int i = 0; i<watched_movie_ids.length;i++){
            if(watched_movie_ids[i]==movie_id){;
                watch = "Watched";
            }
            else{

            }
        }
        for(int i = 0; i<watchlist_movie_ids.length;i++){
            if(watchlist_movie_ids[i]==movie_id){;
                watch = "Watchlist";
            }
            else{

            }
        }
        System.out.println(watch);



    }


    protected void onResume() {
        super.onResume();
        final Database movies_db = new Database(getApplicationContext());
        Button btn_will = (Button) findViewById(R.id.iwillwatch);
        Button btn_watched = (Button) findViewById(R.id.ihavewatched);
        final Intent refresh = new Intent(this,SingleMovie.class);
        refresh.putExtra("movie_id",movie_id);


        if (watch == "Watched") {
            btn_watched.setVisibility(View.GONE);
            btn_will.setVisibility(View.GONE);

        } else if (watch == "Watchlist") {
            btn_will.setVisibility(View.GONE);
            btn_watched.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(refresh);


                    movies_db.changeWatch(movie_id);
                    Toast.makeText(getApplicationContext(), "Movie has been added to your Watched Movies List", Toast.LENGTH_LONG).show();
                }
            });

        } else if (watch == null) {
            btn_will.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(refresh);
                    movies_db.addWatch(movie_id, "Watchlist");
                    Toast.makeText(getApplicationContext(), "Movie has been added to your Watchlist", Toast.LENGTH_LONG).show();
                }
            });
            btn_watched.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    movies_db.addWatch(movie_id, "Watched");
                    startActivity(refresh);

                    Toast.makeText(getApplicationContext(), "Movie has been added to your Watched Movies List", Toast.LENGTH_LONG).show();
                }
            });

        }
    }


}
