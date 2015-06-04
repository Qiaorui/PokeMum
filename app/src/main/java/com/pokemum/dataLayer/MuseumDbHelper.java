package com.pokemum.dataLayer;

/**
 * Created by qiaorui on 4/06/15.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.pokemum.dataLayer.MuseumContract.UserEntry;
import com.pokemum.dataLayer.MuseumContract.ObraEntry;

public class MuseumDbHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 2;

    static final String DATABASE_NAME = "museum.db";

    public MuseumDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Create a table to hold locations.  A location consists of the string supplied in the
        // location setting, the city name, and the latitude and longitude
        final String SQL_CREATE_USER_TABLE = "CREATE TABLE " + UserEntry.TABLE_NAME + " (" +
                UserEntry._ID + " INTEGER PRIMARY KEY," +
                UserEntry.COLUMN_USERNAME + " TEXT UNIQUE NOT NULL, " +
                UserEntry.COLUMN_PASSWORD + " TEXT NOT NULL, " +
                UserEntry.COLUMN_TYPE + " TEXT NOT NULL " +
                " );";

        final String SQL_CREATE_OBRA_TABLE = "CREATE TABLE " + ObraEntry.TABLE_NAME + " (" +
                // Why AutoIncrement here, and not above?
                // Unique keys will be auto-generated in either case.  But for weather
                // forecasting, it's reasonable to assume the user will want information
                // for a certain date and all dates *following*, so the forecast data
                // should be sorted accordingly.
                ObraEntry._ID + " INTEGER PRIMARY KEY," +

                // the ID of the location entry associated with this weather data
                ObraEntry.COLUMN_TITULO + " TEXT NOT NULL, " +
                ObraEntry.COLUMN_AUTOR + " TEXT NOT NULL, " +
                ObraEntry.COLUMN_DESCRIPCION + " TEXT, " +
                ObraEntry.COLUMN_TIPO + " TEXT NOT NULL," +

                ObraEntry.COLUMN_ESTILO_ARTISTICO + " TEXT NOT NULL, " +
                ObraEntry.COLUMN_RAMA_ARTISTICO + " TEXT NOT NULL, " +
                ObraEntry.COLUMN_ANO_CREACION + " INTEGER NOT NULL, " +
                ObraEntry.COLUMN_ANO_ADQUISICION + " INTEGER NOT NULL, " +

                ObraEntry.COLUMN_PERIODO_HISTORICO + " TEXT NOT NULL " +
                ");";

        sqLiteDatabase.execSQL(SQL_CREATE_USER_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_OBRA_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        // Note that this only fires if you change the version number for your database.
        // It does NOT depend on the version number for your application.
        // If you want to update the schema without wiping data, commenting out the next 2 lines
        // should be your top priority before modifying this method.
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + UserEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ObraEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}