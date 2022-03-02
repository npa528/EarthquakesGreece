package com.example.earthquakesgreece.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.earthquakesgreece.R;
import com.example.earthquakesgreece.model.Quake;
import com.example.earthquakesgreece.model.QuakeListAdapter;
import com.example.earthquakesgreece.viewmodels.ResponseCallback;
import com.example.earthquakesgreece.viewmodels.ServiceViewModel;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import lombok.SneakyThrows;

public class MainActivity extends AppCompatActivity {

    // Initialize variables
    SwipeRefreshLayout swipeRefreshLayout;
    ServiceViewModel serviceViewModel;
    QuakeListAdapter quakeAdapter;
    ArrayList<Quake> currentQuakesList = new ArrayList<>();

    @SneakyThrows
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView quakesListView =  findViewById(R.id.quakes_list);
        Context context = getApplicationContext();
        serviceViewModel = new ServiceViewModel(context);

        serviceViewModel.fetchData(new ResponseCallback() {
               @Override
               public LinkedHashMap<String, Quake> onSuccess(LinkedHashMap<String, Quake> quakes) {

                   if (currentQuakesList.isEmpty()) {
                       currentQuakesList = new ArrayList<>(quakes.values());
                       quakeAdapter = new QuakeListAdapter(context, R.layout.quakes_list, currentQuakesList);
                       // Set adapter
                       quakesListView.setAdapter(quakeAdapter);
                   }
                   return null;
               }
           });

        // Assign variables
        swipeRefreshLayout = findViewById(R.id.swipe_refresh);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @SneakyThrows
            @Override
            public void onRefresh() {
                // When refresh, add value in array list

                serviceViewModel.fetchData(new ResponseCallback() {
                    @Override
                    public LinkedHashMap<String, Quake> onSuccess(LinkedHashMap<String, Quake> quakes) {

                        if (quakeAdapter == null) {
                            quakeAdapter = new QuakeListAdapter(context, R.layout.quakes_list, currentQuakesList);
                            quakesListView.setAdapter(quakeAdapter);

                        } else {
                            quakeAdapter.notifyDataSetChanged();
                        }

                        return null;
                    }
                });

                // Dismiss refreshing dialogue
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        quakesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Quake selected = currentQuakesList.get(position);
                Intent appInfo = new Intent(context, QuakeDetails.class);

                appInfo.putExtra("Longitude", selected.getLongitude());
                appInfo.putExtra("Latitude", selected.getLatitude());

                startActivity(appInfo);
            }
        });
    }
}