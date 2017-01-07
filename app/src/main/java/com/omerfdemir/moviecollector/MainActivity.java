package com.omerfdemir.moviecollector;

import android.Manifest;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private Button btn_details, btn_categories, btn_my_movies, btn_toplist, btn_latest_movies, btn_years, btn_directors;
    private TextView tv_movie_name, tv_imdb, tv_genre, tv_director;
    private ImageView iv_main;
    ArrayAdapter<String> adapter2;
    ArrayList<HashMap<String, String>> movie_list;
    String[] movie_names;
    int[] movie_ids;
    ListView lv2;
    Random random = new Random();
    int movie_id = random.nextInt(628) + 1;
    private SQLiteDatabase veritabanı;
    private Database dbDb;
    SharedPreferences preferences ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*Changing theme with shared preference*/
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int theme = preferences.getInt("theme",1);
        if (theme==0){
            setTheme(R.style.AppThemeDark);}
        else{
            setTheme(R.style.AppTheme);
        }
        setContentView(R.layout.activity_main);
        Database movies_db = new Database(getApplicationContext());

        dbDb = new Database(this);
        try {
            dbDb.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }



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
            iv_main = (ImageView) findViewById(R.id.iv1);
            tv_movie_name.setText((map.get("name")));
            tv_imdb.setText(map.get("imdb_score"));
            tv_genre.setText(map.get("genre"));
            tv_director.setText(map.get(("director")));
        Resources resources = this.getResources();
        final int resourceId = resources.getIdentifier(map.get("photos"),
                "drawable", this.getPackageName());
        iv_main.setImageResource(resourceId);

        }


    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu,menu);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_about_us:
                Intent intent = new Intent(MainActivity.this, AboutUs.class);
                startActivity(intent);
                return true;
            case R.id.action_contact:
                Intent intent2 = new Intent(MainActivity.this,Contact.class);
                startActivity(intent2);
                return true;
            case R.id.action_settings:
                Intent intent3 = new Intent(MainActivity.this,Settings.class);
                startActivity(intent3);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
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
        btn_my_movies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MyMovies.class);
                startActivity(i);
            }
        });


    }
}
