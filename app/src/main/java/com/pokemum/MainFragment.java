package com.pokemum;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.pokemum.SlideCutListView.RemoveDirection;
import com.pokemum.SlideCutListView.RemoveListener;
import com.pokemum.dataLayer.MuseumContract;

import android.widget.AdapterView.OnItemClickListener;

public class MainFragment extends Fragment implements RemoveListener {

    public static final String SYSTEM_INFO = "system info";
    public static final String IS_SIGNED_IN = "is signed-in";
    private Spinner spinner;
    private String searchTypeSelected;
    private ArrayAdapter<String> adapter;
    private static final String[] SEARCH_TYPE = {MuseumContract.ObraEntry.COLUMN_AUTOR,MuseumContract.ObraEntry.COLUMN_TITULO};
    private ArrayAdapter<String> artworkAdapter;
    private SlideCutListView slideCutListView;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public void onResume() {
        super.onResume();
        spinner = (Spinner)getActivity().findViewById(R.id.spinner);
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, SEARCH_TYPE);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                searchTypeSelected = adapter.getItem(position);
                Toast toast = Toast.makeText(getActivity(), "serach type: " + searchTypeSelected, Toast.LENGTH_SHORT);
                toast.show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner.setSelection(1);
        searchTypeSelected = MuseumContract.ObraEntry.COLUMN_TITULO;
        init();
    }

    private void updateData() {
        FetchArtworkTask fetchArtworkTask = new FetchArtworkTask(getActivity(),artworkAdapter);
        fetchArtworkTask.execute();
    }

    private void init() {
        slideCutListView = (SlideCutListView) getActivity().findViewById(R.id.slideCutListView);
        slideCutListView.setRemoveListener(this);

        artworkAdapter = new ArrayAdapter<String>(getActivity(), R.layout.list_item_artwork_xml, R.id.list_item);
        updateData();
        slideCutListView.setAdapter(artworkAdapter);

        slideCutListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_sign_out, menu);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        updateData();
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_sign_out) {
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SYSTEM_INFO, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(IS_SIGNED_IN,false);
            editor.commit();
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private String parseId(String s) {
        String result = "";
        boolean initFlag = false;
        boolean finishFlag = false;
        for (int i = 0; i < s.length() && !finishFlag; i++) {
            if (s.charAt(i) == ')') finishFlag =true;
            if (initFlag && !finishFlag) result += s.charAt(i);
            if (s.charAt(i)== '(') initFlag = true;

        }
        return result;
    }

    @Override
    public void removeItem(RemoveDirection direction, int position) {
        String id = parseId(artworkAdapter.getItem(position));
        Log.v("remove","id:"+id);
        getActivity().getContentResolver().delete(
                MuseumContract.ObraEntry.CONTENT_URI,
                MuseumContract.ObraEntry._ID + " = ?",
                new String[]{id}
                );
        updateData();
        //adapter.remove(adapter.getItem(position));
        switch (direction) {
            case RIGHT:
                Toast.makeText(getActivity(), "向右删除  "+ position, Toast.LENGTH_SHORT).show();
                break;
            case LEFT:
                Toast.makeText(getActivity(), "向左删除  "+ position, Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }


    }



}
