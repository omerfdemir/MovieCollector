package com.omerfdemir.moviecollector;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Movie;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by omerf on 14.11.2016.
 */

public class Categories extends AppCompatActivity {
    ArrayAdapter<String> adapter2;
    ArrayList<HashMap<String, String>> category_list;
    String[] category_names;
    int[] category_ids;
    ListView lv2;
    SharedPreferences preferences ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int theme = preferences.getInt("theme",1);
        if (theme==0){
            setTheme(R.style.AppThemeDark);
        }
        else{
            setTheme(R.style.AppTheme);

        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        setTitle("Categories");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Database movies_db = new Database(getApplicationContext());
        category_list = movies_db.categories();
    if (category_list.size() == 0) {
        Toast.makeText(getApplicationContext(), "There is no Database", Toast.LENGTH_LONG).show();
    } else {
        category_names = new String[category_list.size()];
        category_ids = new int[category_list.size()];
        for (int i = 0; i < category_list.size(); i++) {
            category_names[i] = category_list.get(i).get("Genre");
            category_ids[i] = Integer.parseInt(category_list.get(i).get("genre_id"));
        }
        lv2 = (ListView) findViewById(R.id.categories);
        adapter2 = new ArrayAdapter<String>(this,R.layout.list_item_2,R.id.li_tv2,category_names);
        lv2.setAdapter(adapter2);
    }
    lv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> arg0,View arg1, int arg2, long arg3) {
            Intent intent = new Intent(getApplicationContext(),Movies.class);
            intent.putExtra("genre_id",category_ids[arg2]);
            intent.putExtra("genre",category_names[arg2]);
            startActivity(intent);
        }
    });
}

}
