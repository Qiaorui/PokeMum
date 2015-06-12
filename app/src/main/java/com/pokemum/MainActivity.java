package com.pokemum;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class MainActivity extends ActionBarActivity {

    public static final String SYSTEM_INFO = "system info";
    public static final String IS_SIGNED_IN = "is signed-in";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences preferences = getSharedPreferences(SYSTEM_INFO,MODE_PRIVATE);
        if (preferences.getBoolean(IS_SIGNED_IN,false)) {
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, new MainFragment()).commit();
            }
        } else {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        switch (view.getId()) {
            case R.id.new_artwork_button:
                Intent intent = new Intent(this, ArtworkActivity.class);
                intent.putExtra("action","new");
                startActivity(intent);
                break;
            case R.id.populate_button:
                Toast.makeText(getApplicationContext(), "Fetching artworks from web app", Toast.LENGTH_SHORT);
                PupulateDBTask weatherTask = new PupulateDBTask();
                weatherTask.execute();
                break;
        }

    }

    public class PupulateDBTask extends AsyncTask<String, Void, String[]> {

        private final String LOG_TAG = PupulateDBTask.class.getSimpleName();

        /**
         * Take the String representing the complete forecast in JSON Format and
         * pull out the data we need to construct the Strings needed for the wireframes.
         *
         * Fortunately parsing is easy:  constructor takes the JSON string and converts it
         * into an Object hierarchy for us.
         */
        private String[] getObrasDataFromJson(String obrasJsonStr, int numObras)
                throws JSONException {

            // These are the names of the JSON objects that need to be extracted.
            final String PJSON_LIST = "list";
            final String PJSON_ID = "id";
            final String PJSON_TITULO = "titulo";
            final String PJSON_AUTOR = "autor";
            final String PJSON_TIPO = "tipo";
            final String PJSON_ANO = "ano";
            final String PJSON_ESTILO = "estilo";
            final String PJSON_URL = "url";

            //JSONObject obrasJson = new JSONObject(obrasJsonStr);
            JSONArray obrasArray = new JSONArray(obrasJsonStr);

            // OWM returns daily forecasts based upon the local time of the city that is being
            // asked for, which means that we need to know the GMT offset to translate this data
            // properly.


            String[] resultStrs = new String[numObras];
            for(int i = 0; i < obrasArray.length(); i++) {

                // Get the JSON object representing the day
                JSONObject obra = obrasArray.getJSONObject(i);

                String id = obra.getString(PJSON_ID);
                String titulo = obra.getString(PJSON_TITULO);
                String autor = obra.getString(PJSON_AUTOR);
                String tipo = obra.getString(PJSON_TIPO);
                String ano = obra.getString(PJSON_ANO);
                String estilo = obra.getString(PJSON_ESTILO);
                String url = obra.getString(PJSON_URL);

                resultStrs[i] = id + " - " + titulo + " - " + autor + " - " + tipo + " - " + ano + " - " + estilo + " - " + url;
            }

            for (String s : resultStrs) {
                Log.v(LOG_TAG, "Obra entry: " + s);
            }

            return resultStrs;

        }

        @Override
        protected String[] doInBackground(String... params) {

            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String obrasJsonStr = null;

            String format = "json";
            int numDays = 7;

            try {
                // Construct the URL for the OpenWeatherMap query
                // Possible parameters are avaiable at OWM's forecast API page, at
                // http://openweathermap.org/API#forecast
                final String FORECAST_BASE_URL = "https://pocket-museum.herokuapp.com/obras.json";

                Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                        .build();

                URL url = new URL(builtUri.toString());
/*
                Log.v(LOG_TAG, "Built URI: " + builtUri);
*/
                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                obrasJsonStr = buffer.toString();
                Log.v(LOG_TAG, "Obras JSON string: " + obrasJsonStr);

            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                return null;
            } finally{
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }

            try {
                return getObrasDataFromJson(obrasJsonStr, numDays);
            }
            catch (JSONException e){
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }

            return null;
        }

    }


}
