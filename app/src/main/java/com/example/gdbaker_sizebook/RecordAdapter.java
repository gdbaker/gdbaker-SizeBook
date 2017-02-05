package com.example.gdbaker_sizebook;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Gregory on 2017-02-04.
 */

public class RecordAdapter extends ArrayAdapter<Record> {
    //https://github.com/codepath/android_guides/wiki/Using-an-ArrayAdapter-with-ListView
    //February 4, 2017 11:10pm

    public RecordAdapter(Context context, ArrayList<Record> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Record record = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.record_item, parent, false);
        }
        // Lookup view for data population
        TextView recordName = (TextView) convertView.findViewById(R.id.recordName);
        TextView recordChest = (TextView) convertView.findViewById(R.id.recordChest);
        TextView recordBust = (TextView) convertView.findViewById(R.id.recordBust);
        TextView recordWaist = (TextView) convertView.findViewById(R.id.recordWaist);
        TextView recordInseam = (TextView) convertView.findViewById(R.id.recordInseam);
        LinearLayout layout = (LinearLayout) convertView.findViewById(R.id.listElementLayout);
        //declaring OnClickListener as an object

        //http://stackoverflow.com/questions/29640674/issue-with-onclicklistener-for-button-in-fragment
        View.OnClickListener btnClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

            }
        };
        layout.setOnClickListener(btnClick);


        // Populate the data into the template view using the data object
        recordName.setText(record.getName());
        recordChest.setText(record.getChest().toString());
        recordBust.setText(record.getBust().toString());
        recordWaist.setText(record.getWaist().toString());
        recordInseam.setText(record.getInseam().toString());
        // Return the completed view to render on screen
        return convertView;
    }
}
