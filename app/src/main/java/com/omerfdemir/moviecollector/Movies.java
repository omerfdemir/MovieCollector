package com.omerfdemir.moviecollector;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by omerf on 14.11.2016.
 */

public class Movies extends AppCompatActivity {
    SharedPreferences preferences ;
    int genre_id, director_id, year_id;
    String genre, director, year_name;
    ArrayAdapter<String> adapter;
    String[] movie_names;
    int[] movie_ids;
    ListView lv;

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
        setContentView(R.layout.activity_movies);
        final Intent intent = getIntent();
        genre_id = intent.getIntExtra("genre_id", 0);
        genre = intent.getStringExtra("genre");
        director_id = intent.getIntExtra("director_id", 0);
        director = intent.getStringExtra("name");
        year_id = intent.getIntExtra("year_id", 0);
        year_name = intent.getStringExtra("year_name");
        if (genre_id != 0) {
            setTitle(genre);
            Database movies_db = new Database(getApplicationContext());
            ArrayList<HashMap<String, String>> movie_list = movies_db.categorytoMovies(genre_id);
            if (movie_list.size() == 0) {
                Toast.makeText(getApplicationContext(), "There is no Database", Toast.LENGTH_LONG).show();
            } else {
                movie_names = new String[movie_list.size()];
                movie_ids = new int[movie_list.size()];
                for (int i = 0; i < movie_list.size(); i++) {
                    movie_names[i] = movie_list.get(i).get("Name");
                    movie_ids[i] = Integer.parseInt(movie_list.get(i).get("movie_id"));
                }
                lv = (ListView) findViewById(R.id.movies);
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
        } else if (director_id != 0) {
            setTitle(director);
            Database movies_db = new Database(getApplicationContext());
            ArrayList<HashMap<String, String>> movie_list = movies_db.directortoMovies(director_id);
            if (movie_list.size() == 0) {
                Toast.makeText(getApplicationContext(), "There is no Database", Toast.LENGTH_LONG).show();
            } else {
                movie_names = new String[movie_list.size()];
                movie_ids = new int[movie_list.size()];
                for (int i = 0; i < movie_list.size(); i++) {
                    movie_names[i] = movie_list.get(i).get("Name");
                    movie_ids[i] = Integer.parseInt(movie_list.get(i).get("movie_id"));
                }
                lv = (ListView) findViewById(R.id.movies);
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
        } else if (year_id != 0) {
            setTitle(year_name);
            Database movies_db = new Database(getApplicationContext());
            final ArrayList<HashMap<String, String>> movie_list = movies_db.yeartoMovies(year_id);
            if (movie_list.size() == 0) {
                Toast.makeText(getApplicationContext(), "There is no Database", Toast.LENGTH_LONG).show();
            } else {
                movie_names = new String[movie_list.size()];
                movie_ids = new int[movie_list.size()];
                for (int i = 0; i < movie_list.size(); i++) {
                    movie_names[i] = movie_list.get(i).get("Name");
                    movie_ids[i] = Integer.parseInt(movie_list.get(i).get("movie_id"));
                }
                lv = (ListView) findViewById(R.id.movies);
                adapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.li_tv, movie_names);
                lv.setAdapter(adapter);

            }
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
             /*   public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    // HERE IS YOUR ITEM ID:):)
                    int clickedItem=movie_list.indexOf(parent.getAdapter().getItem(position));
                    Intent intent = new Intent(getApplicationContext(), SingleMovie.class);
                    intent.putExtra("movie_id", clickedItem);
                }
            });*/
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                    Intent intent = new Intent(getApplicationContext(), SingleMovie.class);
                    intent.putExtra("movie_id", movie_ids[arg2]);

                    startActivity(intent);
                }
            });
        }
    }







        @Override
        protected void onResume () {


            super.onResume();


        }



    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);

        SearchView.OnQueryTextListener textChangeListener = new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextChange(String newText)
            {
                // this is your adapter that will be filtered
                Movies.this.adapter.getFilter().filter(newText.toString().trim());
                adapter.notifyDataSetChanged();

                int search_list[];
                String search_list_name[];
                System.out.println("on text chnge text: "+newText);





                return true;

            }
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                // this is your adapter that will be filtered
                adapter.getFilter().filter(query.toString().trim());
                System.out.println("on query submit: "+query);
                System.out.println(adapter);

                return true;
            }


        };
        lv = (ListView) findViewById(R.id.movies);
        adapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.li_tv, movie_names);
        lv.setAdapter(adapter);
        searchView.setOnQueryTextListener(textChangeListener);



        return super.onCreateOptionsMenu(menu);

    }
}




