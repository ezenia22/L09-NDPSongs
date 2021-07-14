package sg.edu.rp.c346.id20011155.l09_ndpsongs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import android.content.Context;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ndpsongs.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_SONG = "song";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "title";
    private static final String COlUMN_SINGERS = "singers";
    private static final String COlUMN_YEAR = "year";
    private static final String COlUMN_STARS = "stars";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createSongTableSql = "CREATE TABLE " + TABLE_SONG + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_TITLE + " TEXT , " + COlUMN_SINGERS + " TEXT, " + COlUMN_YEAR + " int, " + COlUMN_STARS + " int ) ";
        db.execSQL(createSongTableSql);
        Log.i("infosql", "created tables");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SONG);
        onCreate(db);
    }

    public long insertSong(String title, String singers, int year,  int stars) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COlUMN_SINGERS, singers);
        values.put(COlUMN_YEAR, year);
        values.put(COlUMN_STARS, stars);
        long result = db.insert(TABLE_SONG, null, values);
        if (result == -1){
            Log.d("DBHelper", "Insert failed");
        }
        db.close();
        Log.d("SQL Insert","ID:"+ result);
        return result;
    }

    public int updateSong(Song data){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, data.getTitle());
        values.put(COlUMN_SINGERS, data.getSingers());
        values.put(COlUMN_YEAR, data.getYear());
        values.put(COlUMN_STARS, data.getStars());
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(data.getId())};
        int result = db.update(TABLE_SONG, values, condition, args);
        if (result < 1){
            Log.d("DBHelper", "Update failed");
        }
        db.close();
        Log.d("result", "result" + result);
        return result;
    }

    public int deleteSong(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(id)};
        int result = db.delete(TABLE_SONG, condition, args);
        db.close();
        return result;
    }

    public ArrayList<Song> getAllSongs() {
        ArrayList<Song> songsList = new ArrayList<Song>();
        String selectQuery = "SELECT " + COLUMN_ID + " , "
                + COLUMN_TITLE + " , "
                + COlUMN_SINGERS + " , "
                + COlUMN_YEAR + ", "
                + COlUMN_STARS+ " FROM " + TABLE_SONG;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String singers = cursor.getString(2);
                int year = cursor.getInt(3);
                int stars = cursor.getInt(4);
                Song newSong = new Song(title, singers, year, stars);
                songsList.add(newSong);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return songsList;
    }

    public ArrayList<Song> getAllSongs(String stars) {
        ArrayList<Song> songs = new ArrayList<Song>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns= {COLUMN_ID, COLUMN_TITLE, COlUMN_SINGERS, COlUMN_YEAR, COlUMN_STARS};
        String condition = COlUMN_STARS + " Like ?";
        String[] args = { "%" +  stars + "%"};
        Cursor cursor = db.query(TABLE_SONG, columns, condition, args,
                null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String singers = cursor.getString(2);
                int year = cursor.getInt(3);
                int star = cursor.getInt(4);
                Song newSong = new Song(id, title, singers, year, star);
                songs.add(newSong);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return songs;
    }

}
