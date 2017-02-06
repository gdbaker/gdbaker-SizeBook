package com.example.gdbaker_sizebook;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * An app that can store clothing measurements.
 * All files are stored in the "JSON" format.
 * Uses Record class to store data.
 * NewRecord creates a Record then send it to Main
 * using intent.
 *
 * @see Record
 * @author gdbaker
 * @version 1
 */
public class Main extends AppCompatActivity {

    private static final String FILENAME = "file.sav";
    private ArrayList<Record> recordsList;
    private ListView recordsListView;
    private Integer numRecords;
    private RecordAdapter adapter;
    private Context context;
    private PopupWindow popup;
    private LinearLayout recordsLayout;

    private TextView popName;
    private  TextView popDate;
    private  TextView popNeck;
    private  TextView popBust;
    private  TextView popChest;
    private  TextView popWaist;
    private  TextView popHip;
    private  TextView popInseam;
    private  TextView popComments;

    private EditText popNameEdit;
    private EditText popDateEdit;
    private EditText popNeckEdit;
    private EditText popBustEdit;
    private EditText popChestEdit;
    private EditText popWaistEdit;
    private EditText popHipEdit;
    private EditText popInseamEdit;
    private EditText popCommentsEdit;

    private Button edit;
    private Button delete;
    private Boolean editting;
    private Record popRecord;
    private TextView recordCount;
    private SimpleDateFormat df;
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();

        recordsListView = (ListView) findViewById(R.id.recordListView);
        recordsLayout = (LinearLayout) findViewById(R.id.recordsLayout);
        recordCount = (TextView) findViewById(R.id.recordCount);

        recordsListView.setItemsCanFocus(false);

        df = new SimpleDateFormat("yyyy-MM-dd");

        //Taken from http://stackoverflow.com/questions/21295328/android-listview-with-onclick-items
        //Feb 5, 2017 3:30
        recordsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (popup != null && popup.isShowing()) {
                    popup.dismiss();
                }
                else {
                    editting = false;
                    popRecord = adapter.getItem(position);

                    //referenced and used some code from
                    // https://android--code.blogspot.ca/2016/01/android-popup-window-example.html
                    // Feb 5, 2017 2:30
                    LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
                    final View popView = inflater.inflate(R.layout.more_info_popup, null);

                    popup = new PopupWindow(popView, LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT);

                    //TextViews
                    popName = (TextView) popup.getContentView().findViewById(R.id.popName);
                    popDate = (TextView) popup.getContentView().findViewById(R.id.popDate);
                    popNeck = (TextView) popup.getContentView().findViewById(R.id.popNeck);
                    popBust = (TextView) popup.getContentView().findViewById(R.id.popBust);
                    popChest = (TextView) popup.getContentView().findViewById(R.id.popChest);
                    popWaist = (TextView) popup.getContentView().findViewById(R.id.popWaist);
                    popHip = (TextView) popup.getContentView().findViewById(R.id.popHip);
                    popInseam = (TextView) popup.getContentView().findViewById(R.id.popInseam);
                    popComments = (TextView) popup.getContentView().findViewById(R.id.popComments);

                    //EditText
                    popNameEdit = (EditText) popup.getContentView().findViewById(R.id.popNameEdit);
                    popDateEdit = (EditText) popup.getContentView().findViewById(R.id.popDateEdit);
                    popNeckEdit = (EditText) popup.getContentView().findViewById(R.id.popNeckEdit);
                    popBustEdit = (EditText) popup.getContentView().findViewById(R.id.popBustEdit);
                    popChestEdit = (EditText) popup.getContentView().findViewById(R.id.popChestEdit);
                    popWaistEdit = (EditText) popup.getContentView().findViewById(R.id.popWaistEdit);
                    popHipEdit = (EditText) popup.getContentView().findViewById(R.id.popHipEdit);
                    popInseamEdit = (EditText) popup.getContentView().findViewById(R.id.popInseamEdit);
                    popCommentsEdit = (EditText) popup.getContentView().findViewById(R.id.popCommentsEdit);

                    //limits decimal input
                    //Taken from http://stackoverflow.com/questions/5357455/limit-decimal-places-in-android-edittext
                    //Feb 5, 2017 11:45
                    popNeckEdit.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(5,1)});
                    popBustEdit.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(5,1)});
                    popChestEdit.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(5,1)});
                    popWaistEdit.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(5,1)});
                    popHipEdit.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(5,1)});
                    popInseamEdit.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(5,1)});

                    // Populate the data into the template view using the data object

                    setValues(true);

                    popup.setOutsideTouchable(true);
                    popup.showAtLocation(recordsLayout, Gravity.CENTER,0,0);

                    edit = (Button) popup.getContentView().findViewById(R.id.editButton);
                    delete = (Button) popup.getContentView().findViewById(R.id.deleteButton);

                    edit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if(editting){
                                //save
                                String nameText = popNameEdit.getText().toString();
                                String dateString = popDateEdit.getText().toString();

                                //ensure name is not empty
                                if(nameText.matches("")){
                                    CharSequence text = "You Must Enter A Name!";
                                    int duration = Toast.LENGTH_SHORT;

                                    //taken from https://developer.android.com/guide/topics/ui/notifiers/toasts.html#Basics
                                    //Feb 4, 2017 11:30

                                    toast = Toast.makeText(context, text, duration);
                                    toast.show();
                                    return;
                                }

                                //attempt to parse date from string, otherwise set date = null
                                Date date;

                                try {
                                    date = df.parse(dateString);
                                } catch(ParseException e){
                                    if(dateString.matches("")){
                                        date = null;
                                    } else{
                                        CharSequence text = "Invalid Date Entry!";
                                        int duration = Toast.LENGTH_SHORT;

                                        toast = Toast.makeText(context, text, duration);
                                        toast.show();
                                        return;
                                    }
                                }

                                //save new values
                                popRecord.setName(nameText);
                                popRecord.setDate(date);
                                popRecord.setNeckString(popNeckEdit.getText().toString());
                                popRecord.setBustString(popBustEdit.getText().toString());
                                popRecord.setChestString(popChestEdit.getText().toString());
                                popRecord.setWaistString(popWaistEdit.getText().toString());
                                popRecord.setHipString(popHipEdit.getText().toString());
                                popRecord.setInseamString(popInseamEdit.getText().toString());
                                popRecord.setComments(popCommentsEdit.getText().toString());

                                setValues(false);

                                edit.setText("Edit");

                                //change visibility, hide EditTextViews and show TextView
                                delete.setVisibility(View.GONE);

                                popNameEdit.setVisibility(v.GONE);
                                popDateEdit.setVisibility(v.GONE);
                                popNeckEdit.setVisibility(v.GONE);
                                popBustEdit.setVisibility(v.GONE);
                                popChestEdit.setVisibility(v.GONE);
                                popWaistEdit.setVisibility(v.GONE);
                                popHipEdit.setVisibility(v.GONE);
                                popInseamEdit.setVisibility(v.GONE);
                                popCommentsEdit.setVisibility(v.GONE);

                                popName.setVisibility(v.VISIBLE);
                                popDate.setVisibility(v.VISIBLE);
                                popNeck.setVisibility(v.VISIBLE);
                                popBust.setVisibility(v.VISIBLE);
                                popChest.setVisibility(v.VISIBLE);
                                popWaist.setVisibility(v.VISIBLE);
                                popHip.setVisibility(v.VISIBLE);
                                popInseam.setVisibility(v.VISIBLE);
                                popComments.setVisibility(v.VISIBLE);

                                //edit button no longer used to save
                                editting = false;

                                //Taken from http://stackoverflow.com/questions/21793908/edittext-in-popupwindow-not-showing-keyboard-even-if-setfocusabletrue
                                //Feb 5, 6:10
                                popup.setFocusable(false);
                                popup.update();

                                adapter.notifyDataSetChanged();

                                saveInFile();

                            } else{
                                //the button is not used to save data
                                editting = true;

                                //Taken from http://stackoverflow.com/questions/21793908/edittext-in-popupwindow-not-showing-keyboard-even-if-setfocusabletrue
                                //Feb 5, 6:10
                                popup.setFocusable(true);
                                popup.update();

                                //display EditText and hide TextView
                                popName.setVisibility(v.GONE);
                                popDate.setVisibility(v.GONE);
                                popNeck.setVisibility(v.GONE);
                                popBust.setVisibility(v.GONE);
                                popChest.setVisibility(v.GONE);
                                popWaist.setVisibility(v.GONE);
                                popHip.setVisibility(v.GONE);
                                popInseam.setVisibility(v.GONE);
                                popComments.setVisibility(v.GONE);

                                popNameEdit.setVisibility(v.VISIBLE);
                                popDateEdit.setVisibility(v.VISIBLE);
                                popNeckEdit.setVisibility(v.VISIBLE);
                                popBustEdit.setVisibility(v.VISIBLE);
                                popChestEdit.setVisibility(v.VISIBLE);
                                popWaistEdit.setVisibility(v.VISIBLE);
                                popHipEdit.setVisibility(v.VISIBLE);
                                popInseamEdit.setVisibility(v.VISIBLE);
                                popCommentsEdit.setVisibility(v.VISIBLE);

                                delete.setVisibility(v.VISIBLE);

                                edit.setText("Save");
                            }


                            delete.setOnClickListener(new View.OnClickListener() {
                                /**
                                 * deletes selected record
                                 * @param v view of click
                                 */
                                @Override
                                public void onClick(View v) {
                                    adapter.remove(popRecord);
                                    adapter.notifyDataSetChanged();

                                    saveInFile();

                                    numRecords = recordsList.size();
                                    recordCount.setText(String.valueOf(numRecords));
                                    popup.dismiss();
                                }
                            });
                        }
                    });
                }
            }
        });
    }

    /**
     * initialises adapter
     */
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        loadFromFile();
        Intent intent = getIntent();

        adapter = new RecordAdapter(this, recordsList);

        recordsListView.setAdapter(adapter);

        String recordString = intent.getStringExtra("record");

        if(recordString != null) {
            Gson gson = new Gson();

            Record record = gson.fromJson(recordString, Record.class);

            recordsList.add(record);
            adapter.notifyDataSetChanged();

            saveInFile();
        }

        numRecords = recordsList.size();
        recordCount.setText(String.valueOf(numRecords));


    }

    /**
     * used some code from lonelyTwitter
     * loads json data from a file
     */
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

    /**
     * used some code from lonelyTwitter
     *
     * saves json data in a file
     */
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

    //Taken from http://stackoverflow.com/questions/21898723/dismiss-the-popup-window-by-back-button
    //Feb 5, 2017 4:30

    /**
     * allows back button to be used on popup window
     */
    @Override
    public void onBackPressed() {
        if (popup != null && popup.isShowing()) {
            popup.dismiss();
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Assigns TexViews to values
     * @param all determines whether to set EditTexts as well
     */
    private void setValues(Boolean all){
        //set values

        popName.setText(popRecord.getName());
        popDate.setText(popRecord.getDateString(df));
        popNeck.setText(popRecord.getNeckString());
        popBust.setText(popRecord.getBustString());
        popChest.setText(popRecord.getChestString());
        popWaist.setText(popRecord.getWaistString());
        popHip.setText(popRecord.getHipString());
        popInseam.setText(popRecord.getInseamString());
        popComments.setText(popRecord.getComments());

        if(all){
            popNameEdit.setText(popRecord.getName());
            popDateEdit.setText(popRecord.getDateString(df));
            popNeckEdit.setText(popRecord.getNeckString());
            popBustEdit.setText(popRecord.getBustString());
            popChestEdit.setText(popRecord.getChestString());
            popWaistEdit.setText(popRecord.getWaistString());
            popHipEdit.setText(popRecord.getHipString());
            popInseamEdit.setText(popRecord.getInseamString());
            popCommentsEdit.setText(popRecord.getComments());
        }
    }

    public void createRecord(View view){
        Intent intent = new Intent(this, NewRecord.class);
        startActivity(intent);
    }
}
