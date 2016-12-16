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

public class Years extends AppCompatActivity{
    ArrayAdapter<String> adapter2;
    ArrayList<HashMap<String, String>> year_list;
    String[] year_names;
    int[] year_ids;
    ListView lv2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_years);
        setTitle("Years");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Database movies_db = new Database(getApplicationContext());
        year_list = movies_db.yearList();
        if (year_list.size() == 0) {
            Toast.makeText(getApplicationContext(), "There is no Database", Toast.LENGTH_LONG).show();
        } else {
            year_names = new String[year_list.size()];
            year_ids = new int[year_list.size()];
            for (int i = 0; i < year_list.size(); i++) {
                year_names[i] = year_list.get(i).get("year_name");
                year_ids[i] = Integer.parseInt(year_list.get(i).get("year_id"));
            }
            lv2 = (ListView) findViewById(R.id.years);
            adapter2 = new ArrayAdapter<String>(this,R.layout.list_item_2,R.id.li_tv2,year_names);
            lv2.setAdapter(adapter2);
        }
        lv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Intent intent = new Intent(getApplicationContext(),Movies.class);
                intent.putExtra("year_id",year_ids[arg2]);
                intent.putExtra("year_name",year_names[arg2]);
                startActivity(intent);
            }
        });
    }
    }

