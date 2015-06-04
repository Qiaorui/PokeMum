package com.pokemum.dataLayer;

/**
 * Created by qiaorui on 4/06/15.
 */
import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.format.Time;


public class MuseumContract {

    public static final String CONTENT_AUTHORITY = "com.pokemum";

    // Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
    // the content provider.
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // Possible paths (appended to base content URI for possible URI's)
    // For instance, content://com.example.android.sunshine.app/weather/ is a valid path for
    // looking at weather data. content://com.example.android.sunshine.app/givemeroot/ will fail,
    // as the ContentProvider hasn't been given any information on what to do with "givemeroot".
    // At least, let's hope not.  Don't be that dev, reader.  Don't be that dev.
    public static final String PATH_USER = "user";
    public static final String PATH_OBRA = "obra";



    /*
        Inner class that defines the table contents of the location table
        Students: This is where you will add the strings.  (Similar to what has been
        done for WeatherEntry)
     */
    public static final class UserEntry implements BaseColumns {

        public static final String TABLE_NAME = "user";

        public static final String COLUMN_USERNAME = "username";

        public static final String COLUMN_PASSWORD = "password";

        public static final String COLUMN_TYPE = "type";


        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_USER).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_USER;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_USER;

        public static Uri buildUserUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }

    /* Inner class that defines the table contents of the weather table */
    public static final class ObraEntry implements BaseColumns {

        public static final String TABLE_NAME = "obra";

        // Column with the foreign key into the location table.
        public static final String COLUMN_TITULO = "titulo";
        // Date, stored as long in milliseconds since the epoch
        public static final String COLUMN_AUTOR = "autor";
        // Weather id as returned by API, to identify the icon to be used
        public static final String COLUMN_ESTILO_ARTISTICO = "estilo_artistico";

        // Short description and long description of the weather, as provided by API.
        // e.g "clear" vs "sky is clear".
        public static final String COLUMN_RAMA_ARTISTICO = "rama_artistico";

        // Min and max temperatures for the day (stored as floats)
        public static final String COLUMN_DESCRIPCION = "descripcion";
        public static final String COLUMN_ANO_CREACION = "ano_creacion";
        public static final String COLUMN_ANO_ADQUISICION = "ano_adquisicion";

        public static final String COLUMN_PERIODO_HISTORICO = "periodo_historico";

        // Windspeed is stored as a float representing windspeed  mph
        public static final String COLUMN_TIPO = "tipo";


        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_OBRA).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_OBRA;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_OBRA;


        public static Uri buildobraUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }

}
