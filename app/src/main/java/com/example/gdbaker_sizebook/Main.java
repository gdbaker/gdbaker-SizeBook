package com.example.gdbaker_sizebook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }

    public void showRecords(View view){
        Intent intent = new Intent(this, Records.class);
        startActivity(intent);
    }

    public void createRecord(View view){
        Intent intent = new Intent(this, NewRecord.class);
        startActivity(intent);
    }
}
