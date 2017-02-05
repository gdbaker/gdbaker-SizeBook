package com.example.gdbaker_sizebook;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;


public class Records extends AppCompatActivity {

    private static final String FILENAME = "file.sav";
    private ArrayList<Record> recordsList;
    private ListView recordsListView;
    private Integer numRecords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);

    }

    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        loadFromFile();
        Intent intent = getIntent();

        recordsListView = (ListView) findViewById(R.id.recordListView);
        RecordAdapter adapter = new RecordAdapter(this, recordsList);

        recordsListView.setAdapter(adapter);

        String recordString = intent.getStringExtra("record");

        if(recordString != null) {
            Gson gson = new Gson();

            Record record = gson.fromJson(recordString, Record.class);

            Log.d("recordsSring", recordString);

            recordsList.add(record);
            adapter.notifyDataSetChanged();

            saveInFile();
        }
        numRecords = recordsList.size();
        TextView recordCount = (TextView) findViewById(R.id.recordCount);
        recordCount.setText(numRecords.toString());


    }

    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();

            // Taken from http://stackoverflow.com/questions/12384064/gson-convert-from-json-to-a-typed-arraylistt
            //2017-01-24 18:19

            Type listType = new TypeToken<ArrayList<Record>>(){}.getType();

            recordsList = gson.fromJson(in, listType);

        } catch (FileNotFoundException e) {
            recordsList  = new ArrayList<Record>();
        }
    }

    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME,
                    Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            Gson gson = new Gson();
            gson.toJson(recordsList, out);
            out.flush();

            fos.close();
        } catch (FileNotFoundException e) {
            // TODO: Handle the Exception properly later
            throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public void openPopup(View view){

        Log.d("herhe", "yoyo");
    }
}
