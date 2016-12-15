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

import static com.omerfdemir.moviecollector.R.id.toplist;

/**
 * Created by omerf on 16.11.2016.
 */

public class Toplist extends AppCompatActivity{
    ArrayAdapter<String> adapter3;
    ArrayList<HashMap<String, String>> top_list;
    String[] movie_names;
    int[] movie_ids;
    String [] imdb_score;
    ListView lv3;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toplist);
        setTitle("Toplist");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Database movies_db = new Database(getApplicationContext());
        top_list = movies_db.topList();
        if (top_list.size() == 0) {
            Toast.makeText(getApplicationContext(), "There is no Database", Toast.LENGTH_LONG).show();
        }  else{
            movie_names = new String[top_list.size()];
            movie_ids = new int[top_list.size()];
            for (int i =0 ; i < top_list.size() ; i++){
                movie_names[i]=top_list.get(i).get("Name") +" - "  + top_list.get(i).get("imdb_score");
                movie_ids[i] = Integer.parseInt(top_list.get(i).get("movie_id"));
            }
            lv3 = (ListView) findViewById(R.id.toplist);
            adapter3 = new ArrayAdapter<String>(this,R.layout.list_item_3,R.id.li_tv3,movie_names);
            lv3.setAdapter(adapter3);

        }
        lv3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Intent intent = new Intent(getApplicationContext(),SingleMovie.class);
                intent.putExtra("movie_id",movie_ids[arg2]);
                startActivity(intent);
            }
        });
    }
}
