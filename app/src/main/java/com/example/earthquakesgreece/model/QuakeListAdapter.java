package com.example.earthquakesgreece.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.earthquakesgreece.R;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class QuakeListAdapter extends ArrayAdapter<Quake> {

    private Context context;
    int resource;

    public QuakeListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Quake> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        String region = getItem(position).getRegionName();
        LocalDateTime date = getItem(position).getDate();
        int depth = getItem(position).getDepth();
        float magnitude = getItem(position).getMagnitude();

        LayoutInflater inflater = LayoutInflater.from(context);

        if(convertView == null) {
            convertView = inflater.inflate(resource, parent, false);
        }


        TextView tvRegion = (TextView) convertView.findViewById(R.id.region);
        TextView tvDate = (TextView) convertView.findViewById(R.id.date);
        TextView tvMagnitude = (TextView) convertView.findViewById(R.id.magnitude);

        tvRegion.setText(region);
        tvDate.setText(date.toString());
        tvMagnitude.setText(Float.toString(magnitude));

        return convertView;
    }
}
