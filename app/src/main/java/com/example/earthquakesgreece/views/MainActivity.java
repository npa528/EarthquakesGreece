package com.example.earthquakesgreece.views;

import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.earthquakesgreece.R;
import com.example.earthquakesgreece.model.Quake;
import com.example.earthquakesgreece.model.QuakeListAdapter;
import com.example.earthquakesgreece.viewmodels.ServiceViewModel;

import java.util.ArrayList;

import lombok.SneakyThrows;

public class MainActivity extends AppCompatActivity {

    // Initialize variables
    SwipeRefreshLayout swipeRefreshLayout;
    ListView quakes;
    ArrayList<Integer> arrayList;
    ArrayList<Quake> callbackQuakes = new ArrayList<>();
    ServiceViewModel serviceViewModel;

    @SneakyThrows
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Context context = getApplicationContext();
        serviceViewModel = new ServiceViewModel(context);

        Thread thread = new Thread(serviceViewModel);
        thread.start();
        thread.join();

        callbackQuakes = serviceViewModel.getQuakes();

//        serviceViewModel.readXML(context);


        // Assign variables
        swipeRefreshLayout = findViewById(R.id.swipe_refresh);
        quakes = findViewById(R.id.listQuakes);

        // Initialize array list
        arrayList = new ArrayList<>();


        QuakeListAdapter quakeAdapter = new QuakeListAdapter(this, R.layout.quakes_list, callbackQuakes);
        // Set adapter
        quakes.setAdapter(quakeAdapter);



        // Initialize array adapter
//        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);
//        // Set adapter
//        quakes.setAdapter(arrayAdapter);





//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @SneakyThrows
//            @Override
//            public void onRefresh() {
//                // When refresh, add value in array list
//                arrayList.add(arrayList.size() + 1);
//
////                sm.readXML(context);
//
//                // Notify adapter
//                arrayAdapter.notifyDataSetChanged();
//
//                // Dismiss refreshing dialogue
//                swipeRefreshLayout.setRefreshing(false);
//
//            }
//        });

    }
}