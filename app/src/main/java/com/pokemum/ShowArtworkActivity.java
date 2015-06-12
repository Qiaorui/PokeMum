package com.pokemum;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.pokemum.dataLayer.MuseumContract;
import com.pokemum.dataLayer.MuseumProvider;

import java.util.List;
import java.util.Random;


public class ShowArtworkActivity extends ActionBarActivity {

    private final Random rand = new Random();
    private final int MIN_SQLITE_ID = 1;
    private final String LOG_TAG = ShowArtworkActivity.class.getSimpleName();

    private ContentValues cv;
    private static final String[] OBRA_COLUMNS = {
            MuseumContract.ObraEntry._ID,
            MuseumContract.ObraEntry.COLUMN_TITULO,
            MuseumContract.ObraEntry.COLUMN_AUTOR,
            MuseumContract.ObraEntry.COLUMN_ANO_CREACION,
            MuseumContract.ObraEntry.COLUMN_ESTILO_ARTISTICO,
            MuseumContract.ObraEntry.COLUMN_TIPO,
            MuseumContract.ObraEntry.COLUMN_DESCRIPCION
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_artwork);

        Button dismiss = (Button) findViewById(R.id.dismiss_button);
        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        MuseumProvider mp = new MuseumProvider();
        List<Integer> ids = mp.getAllIds();
        int randomNum = rand.nextInt(((ids.size()-1) - MIN_SQLITE_ID) + 1) + MIN_SQLITE_ID;

        String randomNumStr = ids.get(randomNum).toString();
        Log.v(LOG_TAG, "Random generated artwork => " + randomNumStr);

        Cursor obraCursor = getContentResolver().query(
                MuseumContract.ObraEntry.CONTENT_URI,
                OBRA_COLUMNS,
                MuseumContract.ObraEntry._ID + " = ?",
                new String[] {randomNumStr},
                null);
        if (obraCursor.moveToFirst()) {
            cv = new ContentValues();
            DatabaseUtils.cursorRowToContentValues(obraCursor, cv);
        }
        obraCursor.close();
    }

    @Override
    protected void onStart() {
        super.onStart();

        ((TextView)findViewById(R.id.title_textView)).setText(cv.getAsString(MuseumContract.ObraEntry.COLUMN_TITULO));
        ((TextView)findViewById(R.id.author_textView)).setText(cv.getAsString(MuseumContract.ObraEntry.COLUMN_AUTOR));
        ((TextView)findViewById(R.id.year_creation_textView)).setText(cv.getAsString(MuseumContract.ObraEntry.COLUMN_ANO_CREACION));
        ((TextView)findViewById(R.id.style_textView)).setText(cv.getAsString(MuseumContract.ObraEntry.COLUMN_ESTILO_ARTISTICO));
        ((TextView)findViewById(R.id.type_textView)).setText(cv.getAsString(MuseumContract.ObraEntry.COLUMN_TIPO));
        ((TextView)findViewById(R.id.description_textView)).setText(cv.getAsString(MuseumContract.ObraEntry.COLUMN_DESCRIPCION));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_artwork, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
