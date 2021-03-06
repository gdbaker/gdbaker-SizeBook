package com.example.gdbaker_sizebook;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Gregory on 2017-02-04.
 * Adapter used to convert ArrayList to ListView
 */

public class RecordAdapter extends ArrayAdapter<Record> {
    //taken from https://github.com/codepath/android_guides/wiki/Using-an-ArrayAdapter-with-ListView
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

        // Populate the data into the template view using the data object
        recordName.setText(record.getName());
        recordChest.setText(record.getChestString());
        recordBust.setText(record.getBustString());
        recordWaist.setText(record.getWaistString());
        recordInseam.setText(record.getInseamString());
        // Return the completed view to render on screen
        return convertView;
    }

    @Nullable
    @Override
    public Record getItem(int position) {
        return super.getItem(position);
    }
}
