package com.omerfdemir.moviecollector;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.provider.*;
import android.provider.Settings;
import android.util.Log;

import java.io.File;
import java.io.FileFilter;
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

public class Database extends android.database.sqlite.SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DB_PATH = "/data/data/com.omerfdemir.moviecollector/databases/";
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
    private static final String DIRECTOR_ID = "director_id";
    private static final String PHOTOS = "photos";
    private static final String YEAR_ID = "year_id;";
    private static final String TABLE_DIRECTORS = "directors";
    private SQLiteDatabase sqLiteDatabase;
    private Context mContext;
    private static final String ACTION = "action";
    private static final String TABLE_WATCH ="watch";
    private static final String MOVIE_TYPE = "type";
    File file;
    public Database(Context context) {
        super(context, DB_NAME, null, VERSION);
        mContext = context;
        file = context.getDatabasePath(DB_NAME);

    }

    /*
            * Eğer veritabanı yoksa kopyalayıp oluşturacak varsa hiçbir şey yapmayacak
            * metodumuz.
            */
    public void createDataBase() throws IOException {
        //deleteDatabase(file);
        boolean dbExist = checkDataBase();

        if (dbExist) {
            System.out.println("Veritabanı var");
            //Veritabanı daha önce mevcut o yüzden herhangi bir işlem yapmaya gerek yok.

        } else {
            //Veritabanı daha önce hiç oluşturulmamış o yüzden veritabanını kopyala
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Veritabanı kopyalanamadı");
            }
        }

    }
    //  Veritabanı daha önce oluşturulmuş mu oluşturulmamış mı bunu öğrenmek için yazılan method.

    private boolean checkDataBase() {

        File dbFile = mContext.getDatabasePath(DB_NAME);
        return dbFile.exists();
    }

    /* Veritabanını assets'ten alıp
    * "/data/data/com.gelecegiyazanlar.orneksozluk/databases/" altına
    * kopyalayacak metod*/

    private void copyDataBase() throws IOException {

        InputStream myInput = mContext.getAssets().open(DB_NAME);
        String outFileName = DB_PATH + DB_NAME;
        OutputStream myOutput = new FileOutputStream(outFileName);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    //Uygulama içinde kullanacağımız db örneği

    public SQLiteDatabase getDatabase() {
        return sqLiteDatabase;
    }

    //  Veritabanını uygulamada kullanacağımız zaman açmak için gerekli metod

    public void openDataBase() throws SQLException {
        String myPath = DB_PATH + DB_NAME;
        sqLiteDatabase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    //Veritabanını işimiz bittiğinde kapatmak için kullanacağımız metod

    @Override
    public synchronized void close() {
        if (sqLiteDatabase != null)
            sqLiteDatabase.close();
        super.close();
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        File dbFile = mContext.getDatabasePath(DB_NAME);



    }
    public static boolean deleteDatabase(File file) {
        if (file == null) {
            throw new IllegalArgumentException("file must not be null");
        }

        boolean deleted = false;
        deleted |= file.delete();
        deleted |= new File(file.getPath() + "-journal").delete();
        deleted |= new File(file.getPath() + "-shm").delete();
        deleted |= new File(file.getPath() + "-wal").delete();

        File dir = file.getParentFile();
        if (dir != null) {
            final String prefix = file.getName() + "-mj";
            final FileFilter filter = new FileFilter() {
                @Override
                public boolean accept(File candidate) {
                    return candidate.getName().startsWith(prefix);
                }
            };
            for (File masterJournal : dir.listFiles(filter)) {
                deleted |= masterJournal.delete();
            }
        }
        return deleted;
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
    }

    public ArrayList<HashMap<String, String>> categorytoMovies(int genre_id) {
        SQLiteDatabase movies_db = this.getReadableDatabase();
        String selectQuery = "SELECT NAME,MOVIE_ID FROM " + TABLE_MOVIES + " WHERE genre_id = " + genre_id + " ORDER BY NAME";

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

    public ArrayList<HashMap<String, String>> topList() {
        SQLiteDatabase movies_db = this.getReadableDatabase();
        String selectQuery = "SELECT NAME,MOVIE_ID,IMDB_SCORE FROM " + TABLE_MOVIES + " WHERE IMDB_SCORE > 8.0 ORDER BY IMDB_SCORE DESC";

        Cursor cursor = movies_db.rawQuery(selectQuery, null);
        ArrayList<HashMap<String, String>> toplist = new ArrayList<HashMap<String, String>>();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    map.put(cursor.getColumnName(i), cursor.getString(i));
                }
                toplist.add(map);
            } while (cursor.moveToNext());

        }
        movies_db.close();
        return toplist;

    }

    public ArrayList<HashMap<String, String>> categories() {
        SQLiteDatabase movies_db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM CATEGORIES";

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

    public HashMap<String, String> movieDetails(int movie_id) {
        HashMap<String, String> movie = new HashMap<String, String>();
        String selectQuery = "SELECT * FROM " + TABLE_MOVIES + " WHERE movie_id = " + movie_id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor3 = db.rawQuery(selectQuery, null);

        //Move the first row
        cursor3.moveToFirst();
        if (cursor3.getCount() > 0) {
            movie.put(NAME, cursor3.getString(0));
            movie.put(DIRECTOR, cursor3.getString(1));
            movie.put(YEAR, cursor3.getString(2));
            movie.put(WRITER, cursor3.getString(3));
            movie.put(GENRE, cursor3.getString(4));
            movie.put(GENRE_ID, cursor3.getString(5));
            movie.put(MOVIE_ID, cursor3.getString(6));
            movie.put(DETAILS, cursor3.getString(7));
            movie.put(IMDB, cursor3.getString(8));
            movie.put(STARS, cursor3.getString(9));
            movie.put(DIRECTOR_ID, cursor3.getString(10));
            movie.put(YEAR_ID, cursor3.getString(11));
            movie.put(PHOTOS, cursor3.getString(12));


        }
        cursor3.close();

        db.close();
        return movie;

    }

    public ArrayList<HashMap<String, String>> directorList() {
        SQLiteDatabase movies_db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM DIRECTORS ORDER BY NAME";

        Cursor cursor2 = movies_db.rawQuery(selectQuery, null);
        ArrayList<HashMap<String, String>> director_list = new ArrayList<HashMap<String, String>>();

        if (cursor2 != null && cursor2.moveToFirst()) {
            do {
                HashMap<String, String> map2 = new HashMap<String, String>();
                for (int i = 0; i < cursor2.getColumnCount(); i++) {
                    map2.put(cursor2.getColumnName(i), cursor2.getString(i));
                }
                director_list.add(map2);
            } while (cursor2.moveToNext());
        }
        movies_db.close();
        return director_list;

    }

    public ArrayList<HashMap<String, String>> directortoMovies(int director_id) {
        SQLiteDatabase movies_db = this.getReadableDatabase();
        String selectQuery = "SELECT DISTINCT NAME,MOVIE_ID FROM " + TABLE_MOVIES + " WHERE director_id = " + director_id + " ORDER BY NAME";

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

    public ArrayList<HashMap<String, String>> yearList() {
        SQLiteDatabase movies_db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM YEARS ORDER BY YEAR_ID";

        Cursor cursor2 = movies_db.rawQuery(selectQuery, null);
        ArrayList<HashMap<String, String>> year_list = new ArrayList<HashMap<String, String>>();

        if (cursor2 != null && cursor2.moveToFirst()) {
            do {
                HashMap<String, String> map2 = new HashMap<String, String>();
                for (int i = 0; i < cursor2.getColumnCount(); i++) {
                    map2.put(cursor2.getColumnName(i), cursor2.getString(i));
                }
                year_list.add(map2);
            } while (cursor2.moveToNext());
        }
        movies_db.close();
        return year_list;

    }

    public ArrayList<HashMap<String, String>> yeartoMovies(int year_id) {
        SQLiteDatabase movies_db = this.getReadableDatabase();
        String selectQuery = "SELECT DISTINCT NAME,MOVIE_ID FROM " + TABLE_MOVIES + " WHERE year_id = " + year_id + " ORDER BY NAME";

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

    public ArrayList<HashMap<String, String>> latestMovies() {
        SQLiteDatabase movies_db = this.getReadableDatabase();
        String selectQuery = "SELECT DISTINCT NAME,MOVIE_ID FROM " + TABLE_MOVIES + " WHERE year_id = 9 ORDER BY NAME LIMIT 0,20";

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

    public HashMap<String, String> movieMain(int movie_id) {
        HashMap<String, String> movie = new HashMap<String, String>();
        String selectQuery = "SELECT * FROM " + TABLE_MOVIES + " WHERE movie_id = " + movie_id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor3 = db.rawQuery(selectQuery, null);

        //Move the first row
        cursor3.moveToFirst();
        if (cursor3.getCount() > 0) {
            movie.put(NAME, cursor3.getString(0));
            movie.put(DIRECTOR, cursor3.getString(1));
            movie.put(YEAR, cursor3.getString(2));
            movie.put(WRITER, cursor3.getString(3));
            movie.put(GENRE, cursor3.getString(4));
            movie.put(GENRE_ID, cursor3.getString(5));
            movie.put(MOVIE_ID, cursor3.getString(6));
            movie.put(DETAILS, cursor3.getString(7));
            movie.put(IMDB, cursor3.getString(8));
            movie.put(STARS, cursor3.getString(9));
            movie.put(DIRECTOR_ID, cursor3.getString(10));
            movie.put(YEAR_ID, cursor3.getString(11));
            movie.put(PHOTOS, cursor3.getString(12));


        }
        cursor3.close();

        db.close();
        return movie;

    }

    public ArrayList<HashMap<String, String>> watchedMovies() {
        SQLiteDatabase movies_db = this.getReadableDatabase();
        String selectQuery = "SELECT NAME,WATCH.MOVIE_ID FROM WATCH,MOVIES WHERE Movies.Movie_id==WATCH.Movie_id AND WATCH.Type=='Watched'";

        Cursor cursor = movies_db.rawQuery(selectQuery, null);
        ArrayList<HashMap<String, String>> watchedmoviesList = new ArrayList<HashMap<String, String>>();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    map.put(cursor.getColumnName(i), cursor.getString(i));
                }
                watchedmoviesList.add(map);
            } while (cursor.moveToNext());

        }
        movies_db.close();
        return watchedmoviesList;

    }
    public ArrayList<HashMap<String, String>> watchlistMovies() {
        SQLiteDatabase movies_db = this.getReadableDatabase();
        String selectQuery = "SELECT NAME,WATCH.MOVIE_ID FROM WATCH,MOVIES WHERE Movies.Movie_id==WATCH.Movie_id AND WATCH.Type=='Watchlist'";

        Cursor cursor = movies_db.rawQuery(selectQuery, null);
        ArrayList<HashMap<String, String>> watchlistmoviesList = new ArrayList<HashMap<String, String>>();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    map.put(cursor.getColumnName(i), cursor.getString(i));
                }
                watchlistmoviesList.add(map);
            } while (cursor.moveToNext());

        }
        movies_db.close();
        return watchlistmoviesList;

    }
    public void addWatch(int movie_id,String type){
        SQLiteDatabase movies_db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MOVIE_ID,movie_id);
        values.put(MOVIE_TYPE,type);
        movies_db.insert(TABLE_WATCH,null,values);
        movies_db.close();

    }
    public void changeWatch(int movie_id){
        SQLiteDatabase movies_db = this.getWritableDatabase();
        movies_db.delete(TABLE_WATCH, MOVIE_ID +"="+movie_id,null);
        addWatch(movie_id,"Watched");





    }

}