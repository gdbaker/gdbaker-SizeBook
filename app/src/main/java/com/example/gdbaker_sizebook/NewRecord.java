package com.example.gdbaker_sizebook;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NewRecord extends AppCompatActivity {
    private EditText nameText;
    private EditText dateText;
    private EditText neckText;
    private EditText bustText;
    private EditText chestText ;
    private EditText waistText;
    private EditText hipText;
    private EditText inseamText;
    private EditText commentsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_record);

        nameText = (EditText) findViewById(R.id.editName);
        dateText = (EditText) findViewById(R.id.editDate);
        neckText = (EditText) findViewById(R.id.editNeck);
        bustText = (EditText) findViewById(R.id.editBust);
        chestText = (EditText) findViewById(R.id.editChest);
        waistText = (EditText) findViewById(R.id.editWaist);
        hipText = (EditText) findViewById(R.id.editHip);
        inseamText = (EditText) findViewById(R.id.editInseam);
        commentsText = (EditText) findViewById(R.id.editComments);
    }

    public void submit(View view){
        Intent intent = new Intent(this, Records.class);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Toast toast;
        Date date;

        Context context = getApplicationContext();

        String name = nameText.getText().toString();
        Log.d("typeadfn", name);
        if(name.matches("")){


            CharSequence text = "You Must Enter A Name!";
            int duration = Toast.LENGTH_SHORT;
            Log.d("typeadfn", "thies");

            toast = Toast.makeText(context, text, duration);
            toast.show();
            return;
        }

        String dateString = dateText.getText().toString();

        try {
            date = df.parse(dateString);
        } catch(ParseException e){
            if(dateString.matches("")){
                date = null;
            } else{
                CharSequence text = "Invalid Date Entry!";
                int duration = Toast.LENGTH_SHORT;
                Log.d("typeadfn", "thies");

                toast = Toast.makeText(context, text, duration);
                toast.show();
                return;
            }
        }

        Float neck = Float.valueOf(neckText.getText().toString());
        Float bust = Float.valueOf(bustText.getText().toString());
        Float chest = Float.valueOf(chestText.getText().toString());
        Float waist = Float.valueOf(waistText.getText().toString());
        Float hip = Float.valueOf(hipText.getText().toString());
        Float inseam = Float.valueOf(inseamText.getText().toString());
        String comments = commentsText.getText().toString();

        Record record = new Record(name, date, neck, bust, chest, waist, hip, inseam, comments);
        Gson gson = new Gson();
        String toSend = gson.toJson(record);
        Log.d("recordsSring", toSend);

        intent.putExtra("record", toSend);

        startActivity(intent);
    }


}
