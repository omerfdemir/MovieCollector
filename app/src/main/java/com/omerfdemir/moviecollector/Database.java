package com.omerfdemir.moviecollector;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.hardware.camera2.params.StreamConfigurationMap;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by omerf on 12.12.2016.
 */

public class Database extends android.database.sqlite.SQLiteOpenHelper{
    private static final int VERSION = 1;
    private static final String DB_PATH = "/data/data/moviecollector/databases/";
    private static final String DB_NAME = "movies_db.db";
    private static final String TABLE_CATEGORIES = "categories";
    private static final String GENRE = "genre";
    private static final String GENRE_ID = "genre_id";

    private static final String TABLE_MOVIES = "Movies";
    private static final String MOVIE_ID = "movie_id";
    private static final String NAME = "name";
    private static final String DIRECTOR = "director";
    private static final String YEAR = "year";
    private static final String WRITER = "writer";
    private static final String MOVIE_GENRE = "genre";
    private static final String DETAILS = "details";
    private static final String IMDB = "imdb_score";
    private static final String STARS = "stars";
    private static final String TABLE_DIRECTORS = "directors";
    private SQLiteDatabase sqLiteDatabase;
    private Context mContext;
    private static  final String ACTION = "action";


    public Database(Context context){
        super(context, DB_NAME ,null ,VERSION);
        mContext = context;
    }



    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
    } public ArrayList<HashMap<String, String>> allMovies(int genre_id) {
        SQLiteDatabase movies_db = this.getReadableDatabase();
        String selectQuery = "SELECT NAME,MOVIE_ID FROM " + TABLE_MOVIES + " WHERE genre_id = " + genre_id;

        Cursor cursor = movies_db.rawQuery(selectQuery, null);
        ArrayList<HashMap<String, String>> movieList = new ArrayList<HashMap<String, String>>();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    map.put(cursor.getColumnName(i), cursor.getString(i));
                }
                movieList.add(map);
            } while (cursor.moveToNext());

        }
        movies_db.close();
        return movieList;

    }
    public ArrayList<HashMap<String, String>> categories() {
    SQLiteDatabase movies_db = this.getReadableDatabase();
    String selectQuery = "SELECT * FROM CATEGORIES"  ;

    Cursor cursor2 = movies_db.rawQuery(selectQuery, null);
    ArrayList<HashMap<String, String>> categories_list = new ArrayList<HashMap<String, String>>();

    if (cursor2 != null && cursor2.moveToFirst()) {
        do {
            HashMap<String, String> map2 = new HashMap<String, String>();
            for (int i = 0; i < cursor2.getColumnCount(); i++) {
                map2.put(cursor2.getColumnName(i), cursor2.getString(i));
            }
            categories_list.add(map2);
        } while (cursor2.moveToNext());
    }
    movies_db.close();
    return categories_list;

}


    }
