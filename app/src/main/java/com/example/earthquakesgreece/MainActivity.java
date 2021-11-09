package com.example.earthquakesgreece;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // Initialize variables
    SwipeRefreshLayout swipeRefreshLayout;
    ListView quakes;
    TextView tvCount;
    ArrayList<Integer> arrayList;
    ArrayAdapter<Integer> arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Context context = getApplicationContext();
        Service fetchData = new Service();
        fetchData.getData(context);

        // Assign variables
        swipeRefreshLayout = findViewById(R.id.swipe_refresh);
        quakes = findViewById(R.id.listQuakes);
        tvCount = findViewById(R.id.test);

        // Initialize array list
        arrayList = new ArrayList<>();

        // Set count on text  view
        tvCount.setText("Total count:" + arrayList.size());

        // Initialize array adapter
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);

        // Set adapter
        quakes.setAdapter(arrayAdapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // When refresh, add value in array list
                arrayList.add(arrayList.size() + 1);
                // Notify adapter
                arrayAdapter.notifyDataSetChanged();
                // Set next count on text view
                tvCount.setText("Total count: " + arrayList.size());
                // Dismiss refreshing dialogue
                swipeRefreshLayout.setRefreshing(false);

            }
        });

    }
}