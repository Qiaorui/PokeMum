package com.pokemum;

/**
 * Created by qiaorui on 4/06/15.
 */
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.text.format.Time;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.pokemum.dataLayer.MuseumContract;
import com.pokemum.dataLayer.MuseumContract.ObraEntry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

public class FetchArtworkTask extends AsyncTask<Void, Void, String[]> {

    private final String LOG_TAG = FetchArtworkTask.class.getSimpleName();

    private ArrayAdapter<String> mArtworkAdapter;
    private final Context mContext;

    public FetchArtworkTask(Context context, ArrayAdapter<String> forecastAdapter) {
        mContext = context;
        mArtworkAdapter = forecastAdapter;
    }

    private boolean DEBUG = true;


    private String[] convertContenToUXFormat(Vector<ContentValues> cv) {
        String[] result = new String[cv.size()];
        for (int i = 0; i < cv.size(); i++) {
            ContentValues artworkValues = cv.elementAt(i);
            result[i] = artworkValues.getAsString(ObraEntry.COLUMN_TITULO) + ", " +
                    artworkValues.getAsString(ObraEntry.COLUMN_AUTOR) + ", S." +
                    artworkValues.getAsString(ObraEntry.COLUMN_PERIODO_HISTORICO);
        }
        return result;

    }

    @Override
    protected String[] doInBackground(Void... params) {
        Cursor obraCursor = mContext.getContentResolver().query(
                ObraEntry.CONTENT_URI,
                new String[]{ObraEntry._ID, ObraEntry.COLUMN_TITULO, ObraEntry.COLUMN_AUTOR, ObraEntry.COLUMN_PERIODO_HISTORICO},
                null,
                null,
                ObraEntry.COLUMN_TITULO+ " ASC");
        String[] result = null;
        Vector<ContentValues> vector = new Vector<>(obraCursor.getCount());
        if (obraCursor.moveToFirst()) {
            do {
                ContentValues contentValues = new ContentValues();
                DatabaseUtils.cursorRowToContentValues(obraCursor, contentValues);
                vector.add(contentValues);
            } while (obraCursor.moveToNext());

            result = convertContenToUXFormat(vector);
        }


        obraCursor.close();

        return result;
    }

    @Override
    protected void onPostExecute(String[] result) {
        if (result != null && mArtworkAdapter != null) {
            mArtworkAdapter.clear();
            for(String artwork : result) {
                mArtworkAdapter.add(artwork);
            }
            // New data is back from the server.  Hooray!
        }
    }
}

