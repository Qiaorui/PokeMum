package com.pokemum;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.pokemum.dataLayer.MuseumContract;
import com.pokemum.dataLayer.MuseumContract.ObraEntry;


public class ArtworkActivity extends ActionBarActivity {

    private static final String[] TYPE = {"Escultura","Pintura","Mural","Audiovisual","Otros"};
    private static final String[] STYLE = {"Barroco","Clasicismo","Romanticismo","Cubismo","Surrealismo",
    "Expresionismo","ArteNaif","Abstractismo","ArtePop","Fauvismo"};
    private ArrayAdapter<String> typeAdapter;
    private ArrayAdapter<String> styleAdapter;
    private String typeSelected;
    private String styleSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artwork);
        String action = getIntent().getStringExtra("action");


        Spinner typeSpinner = (Spinner)findViewById(R.id.type_spinner);
        typeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, TYPE);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(typeAdapter);
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                typeSelected = typeAdapter.getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        typeSpinner.setSelection(0);
        typeSelected = TYPE[0];

        Spinner styleSpinner = (Spinner)findViewById(R.id.style_spinner);
        styleAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, STYLE);
        styleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        styleSpinner.setAdapter(styleAdapter);
        styleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                styleSelected = styleAdapter.getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        styleSpinner.setSelection(0);
        styleSelected = STYLE[0];




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_artwork, menu);
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


    public void click(View view) {
        switch(view.getId()) {
            case R.id.create_button:
                String title = ((EditText)findViewById(R.id.title_text)).getText().toString();
                String author = ((EditText)findViewById(R.id.author_text)).getText().toString();
                String branch = ((EditText)findViewById(R.id.branch_text)).getText().toString();
                Integer yearCreation = Integer.valueOf(((EditText) findViewById(R.id.year_creation_text)).getText().toString());
                Integer yearAcquisition = Integer.valueOf(((EditText) findViewById(R.id.year_acquisition_text)).getText().toString());
                String period = ((EditText)findViewById(R.id.period_text)).getText().toString();
                String description = ((EditText)findViewById(R.id.description_text)).getText().toString();
                ContentValues artworkValues = new ContentValues();
                artworkValues.put(ObraEntry.COLUMN_TITULO,title);
                artworkValues.put(ObraEntry.COLUMN_AUTOR,author);
                artworkValues.put(ObraEntry.COLUMN_PERIODO_HISTORICO,period);
                artworkValues.put(ObraEntry.COLUMN_ANO_ADQUISICION,yearAcquisition);
                artworkValues.put(ObraEntry.COLUMN_ANO_CREACION,yearCreation);
                artworkValues.put(ObraEntry.COLUMN_ESTILO_ARTISTICO,styleSelected);
                artworkValues.put(ObraEntry.COLUMN_TIPO,typeSelected);
                artworkValues.put(ObraEntry.COLUMN_RAMA_ARTISTICO,branch);
                artworkValues.put(ObraEntry.COLUMN_DESCRIPCION, description);
                Uri insertedUri = this.getContentResolver().insert(
                        MuseumContract.ObraEntry.CONTENT_URI,
                        artworkValues);
                long artworkId = ContentUris.parseId(insertedUri);
                Log.v("artworkActity", "new artwork_id:" + artworkId);

                finish();
                break;
        }
    }
}
