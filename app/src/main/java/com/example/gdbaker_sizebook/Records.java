package com.example.gdbaker_sizebook;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class Records extends AppCompatActivity {

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
        setContentView(R.layout.activity_records);

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

                    LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
                    View popView = inflater.inflate(R.layout.more_info_popup, null);

                    popup = new PopupWindow(popView, LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT);

                    popName = (TextView) popup.getContentView().findViewById(R.id.popName);
                    popDate = (TextView) popup.getContentView().findViewById(R.id.popDate);
                    popNeck = (TextView) popup.getContentView().findViewById(R.id.popNeck);
                    popBust = (TextView) popup.getContentView().findViewById(R.id.popBust);
                    popChest = (TextView) popup.getContentView().findViewById(R.id.popChest);
                    popWaist = (TextView) popup.getContentView().findViewById(R.id.popWaist);
                    popHip = (TextView) popup.getContentView().findViewById(R.id.popHip);
                    popInseam = (TextView) popup.getContentView().findViewById(R.id.popInseam);
                    popComments = (TextView) popup.getContentView().findViewById(R.id.popComments);

                    popNameEdit = (EditText) popup.getContentView().findViewById(R.id.popNameEdit);
                    popDateEdit = (EditText) popup.getContentView().findViewById(R.id.popDateEdit);
                    popNeckEdit = (EditText) popup.getContentView().findViewById(R.id.popNeckEdit);
                    popBustEdit = (EditText) popup.getContentView().findViewById(R.id.popBustEdit);
                    popChestEdit = (EditText) popup.getContentView().findViewById(R.id.popChestEdit);
                    popWaistEdit = (EditText) popup.getContentView().findViewById(R.id.popWaistEdit);
                    popHipEdit = (EditText) popup.getContentView().findViewById(R.id.popHipEdit);
                    popInseamEdit = (EditText) popup.getContentView().findViewById(R.id.popInseamEdit);
                    popCommentsEdit = (EditText) popup.getContentView().findViewById(R.id.popCommentsEdit);

                    // Populate the data into the template view using the data object
                    popName.setText(popRecord.getName());
                    popDate.setText(df.format(popRecord.getDate()));
                    popNeck.setText(popRecord.getNeck().toString());
                    popBust.setText(popRecord.getBust().toString());
                    popChest.setText(popRecord.getChest().toString());
                    popWaist.setText(popRecord.getWaist().toString());
                    popHip.setText(popRecord.getHip().toString());
                    popInseam.setText(popRecord.getInseam().toString());
                    popComments.setText(popRecord.getComments());

                    popNameEdit.setText(popRecord.getName());
                    popDateEdit.setText(df.format(popRecord.getDate()));
                    popNeckEdit.setText(popRecord.getNeck().toString());
                    popBustEdit.setText(popRecord.getBust().toString());
                    popChestEdit.setText(popRecord.getChest().toString());
                    popWaistEdit.setText(popRecord.getWaist().toString());
                    popHipEdit.setText(popRecord.getHip().toString());
                    popInseamEdit.setText(popRecord.getInseam().toString());
                    popCommentsEdit.setText(popRecord.getComments());

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
                                Float neckF = Float.valueOf(popNeckEdit.getText().toString());
                                Float bustF = Float.valueOf(popBustEdit.getText().toString());
                                Float chestF = Float.valueOf(popChestEdit.getText().toString());
                                Float waistF = Float.valueOf(popWaistEdit.getText().toString());
                                Float hipF = Float.valueOf(popHipEdit.getText().toString());
                                Float inseamF = Float.valueOf(popInseamEdit.getText().toString());
                                String commentsText = popCommentsEdit.getText().toString();

                                if(nameText.matches("")){

                                    CharSequence text = "You Must Enter A Name!";
                                    int duration = Toast.LENGTH_SHORT;

                                    toast = Toast.makeText(context, text, duration);
                                    toast.show();
                                    return;
                                }

                                Date date;

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

                                //set values
                                popRecord.setName(nameText);
                                popRecord.setDate(date);
                                popRecord.setNeck(neckF);
                                popRecord.setBust(bustF);
                                popRecord.setChest(chestF);
                                popRecord.setWaist(waistF);
                                popRecord.setHip(hipF);
                                popRecord.setInseam(inseamF);
                                popRecord.setComments(commentsText);

                                popName.setText(popRecord.getName());
                                popDate.setText(df.format(popRecord.getDate()));
                                popNeck.setText(popRecord.getNeck().toString());
                                popBust.setText(popRecord.getBust().toString());
                                popChest.setText(popRecord.getChest().toString());
                                popWaist.setText(popRecord.getWaist().toString());
                                popHip.setText(popRecord.getHip().toString());
                                popInseam.setText(popRecord.getInseam().toString());
                                popComments.setText(popRecord.getComments());

                                edit.setText("Edit");

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

                                editting = false;
                                popup.setFocusable(false);
                                popup.update();

                                adapter.notifyDataSetChanged();

                                saveInFile();

                            } else{
                                editting = true;
                                Log.d("hee", "not eddintg");

                                //Taken from http://stackoverflow.com/questions/21793908/edittext-in-popupwindow-not-showing-keyboard-even-if-setfocusabletrue
                                //Feb 5, 6:10
                                popup.setFocusable(true);
                                popup.update();

                                popName.setVisibility(v.GONE);
                                popDate.setVisibility(v.GONE);
                                popNeckEdit.setVisibility(v.GONE);
                                popBust.setVisibility(v.GONE);
                                popChest.setVisibility(v.GONE);
                                popWaist.setVisibility(v.GONE);
                                popHip.setVisibility(v.GONE);
                                popInseam.setVisibility(v.GONE);
                                popComments.setVisibility(v.GONE);

                                popNameEdit.setVisibility(v.VISIBLE);
                                popDateEdit.setVisibility(v.VISIBLE);
                                popNeckEdit.setVisibility(v.GONE);
                                popBustEdit.setVisibility(v.VISIBLE);
                                popChestEdit.setVisibility(v.VISIBLE);
                                popWaistEdit.setVisibility(v.VISIBLE);
                                popHipEdit.setVisibility(v.VISIBLE);
                                popInseamEdit.setVisibility(v.VISIBLE);
                                popCommentsEdit.setVisibility(v.VISIBLE);

                                delete.setVisibility(v.VISIBLE);

                                edit.setText("Save");
                            }

                            /**
                             * deletes selected record
                             */
                            delete.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    adapter.remove(popRecord);
                                    adapter.notifyDataSetChanged();

                                    saveInFile();

                                    numRecords = recordsList.size();
                                    recordCount.setText(numRecords.toString());
                                    popup.dismiss();
                                }
                            });
                        }
                    });
                }
            }
        });
    }

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

            Log.d("recordsSring", recordString);

            recordsList.add(record);
            adapter.notifyDataSetChanged();

            saveInFile();
        }

        numRecords = recordsList.size();
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

    public void edit(View view){
        EditText popNameEdit = (EditText) view.findViewById(R.id.popNameEdit);
        /*TextView popDateEdit = (TextView) popup.getContentView().findViewById(R.id.popDateEdit);
        TextView popNeckEdit = (TextView) popup.getContentView().findViewById(R.id.popNeckEdit);
        TextView popBustEdit = (TextView) popup.getContentView().findViewById(R.id.popBustEdit);
        TextView popChestEdit = (TextView) popup.getContentView().findViewById(R.id.popChestEdit);
        TextView popWaistEdit = (TextView) popup.getContentView().findViewById(R.id.popWaistEdit);
        TextView popHipEdit = (TextView) popup.getContentView().findViewById(R.id.popHipEdit);
        TextView popInseamEdit = (TextView) popup.getContentView().findViewById(R.id.popInseamEdit);
        TextView popCommentsEdit = (TextView) popup.getContentView().findViewById(R.id.popCommentsEdit);*/
        popName.setVisibility(view.GONE);
        popNameEdit.setVisibility(view.VISIBLE);
    }
    //Taken from http://stackoverflow.com/questions/21898723/dismiss-the-popup-window-by-back-button
    //Feb 5, 2017 4:30
    @Override
    public void onBackPressed() {
        if (popup != null && popup.isShowing()) {
            popup.dismiss();
        } else {
            super.onBackPressed();
        }
    }
}
