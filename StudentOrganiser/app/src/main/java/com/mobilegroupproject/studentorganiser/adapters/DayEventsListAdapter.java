package com.mobilegroupproject.studentorganiser.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mobilegroupproject.studentorganiser.R;

import java.util.ArrayList;

/**
 * Created by joshuahughes on 07/05/2016.
 */
public class DayEventsListAdapter extends ArrayAdapter<String> {

    private final Context context;
    private ArrayList<String> tempStringArray;

    public DayEventsListAdapter(Context c, ArrayList<String> array){
        super(c, -1, array);
        this.context = c;
        this.tempStringArray = array;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.list_row_events, parent, false);

        final String title = tempStringArray.get(position);

        TextView tempTitle = (TextView) rowView.findViewById(R.id.list_item_title);
        tempTitle.setText(title);


        return rowView;

    }
}
