package com.pokemum;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.pokemum.dataLayer.MuseumContract;

/**
 * Created by qiaorui on 2/06/15.
 */
public class LoginActivity extends Activity {
    public static final String SYSTEM_INFO = "system info";
    public static final String IS_SIGNED_IN = "is signed-in";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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

    private boolean containUser(String username, String password) {
        Cursor userCursor = this.getContentResolver().query(
                MuseumContract.UserEntry.CONTENT_URI,
                new String[]{MuseumContract.UserEntry._ID},
                MuseumContract.UserEntry.COLUMN_USERNAME + " = ? AND " +
                MuseumContract.UserEntry.COLUMN_PASSWORD + " = ?",
                new String[]{username, password},
                null);
        boolean b = userCursor.moveToFirst();
        userCursor.close();
        return b;
    }

    private boolean newUser(String username, String password, String type) {
        Cursor userCursor = this.getContentResolver().query(
                MuseumContract.UserEntry.CONTENT_URI,
                new String[]{MuseumContract.UserEntry._ID},
                MuseumContract.UserEntry.COLUMN_USERNAME + " = ?",
                new String[]{username},
                null);
        boolean b = userCursor.moveToFirst();

        if (!b) {
            ContentValues userValues = new ContentValues();

            // Then add the data, along with the corresponding name of the data type,
            // so the content provider knows what kind of value is being inserted.
            userValues.put(MuseumContract.UserEntry.COLUMN_USERNAME, username);
            userValues.put(MuseumContract.UserEntry.COLUMN_PASSWORD, password);
            userValues.put(MuseumContract.UserEntry.COLUMN_TYPE, type);
            // Finally, insert User data into the database.
            Uri insertedUri = this.getContentResolver().insert(
                    MuseumContract.UserEntry.CONTENT_URI,
                    userValues);
            long userId = ContentUris.parseId(insertedUri);
            Log.v("LoginActity", "new User_id:"+userId);
        }

        userCursor.close();
        return !b;
    }


    public void click(View v) {
        String username = ((EditText)findViewById(R.id.username_text)).getText().toString();
        String password = ((EditText)findViewById(R.id.password_text)).getText().toString();
        switch (v.getId()) {
            case R.id.register_button:
                boolean success = newUser(username,password,"visitante");
                if (success) {
                    Toast toast = Toast.makeText(getApplicationContext(),"New User: " + username +" is created",Toast.LENGTH_SHORT);
                    toast.show();
                    SharedPreferences sharedPreferences = getSharedPreferences(SYSTEM_INFO, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean(IS_SIGNED_IN, true);
                    editor.commit();
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(),"username: " + username +" already existed",Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;
            case R.id.signIn_button:

                if (containUser(username, password)) {
                    SharedPreferences sharedPreferences = getSharedPreferences(SYSTEM_INFO, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean(IS_SIGNED_IN, true);
                    editor.commit();
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(),"wrong username or password",Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;
        }
    }
}
