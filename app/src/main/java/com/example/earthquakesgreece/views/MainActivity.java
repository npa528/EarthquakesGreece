package com.example.earthquakesgreece.views;

import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.earthquakesgreece.R;
import com.example.earthquakesgreece.model.Quake;
import com.example.earthquakesgreece.model.QuakeListAdapter;
import com.example.earthquakesgreece.viewmodels.ResponseCallback;
import com.example.earthquakesgreece.viewmodels.ServiceViewModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;

import lombok.SneakyThrows;

public class MainActivity extends AppCompatActivity {

    // Initialize variables
    SwipeRefreshLayout swipeRefreshLayout;
    ListView quakesListView;
    ServiceViewModel serviceViewModel;
    QuakeListAdapter quakeAdapter;

    LinkedHashMap<String, Quake> currentQuakesMap = new LinkedHashMap<>();

    ArrayList<Quake> currentQuakesList = new ArrayList<>();


    @SneakyThrows
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Context context = getApplicationContext();
        serviceViewModel = new ServiceViewModel(context);

        serviceViewModel.fetchData(new ResponseCallback() {
               @Override
               public LinkedHashMap<String, Quake> onSuccess(LinkedHashMap<String, Quake> quakes) {
//                   if (BuildConfig.DEBUG) Log.d("Quake","Response Listener Service");
//                   compareList(quakes);
//                   return null;

                   if (currentQuakesList.isEmpty()) {
                       currentQuakesList = new ArrayList<>(quakes.values());

                       quakesListView = findViewById(R.id.listQuakes);
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

                        //Union of keys from both maps
                        HashSet<String> unionKeys = new HashSet<>(currentQuakesMap.keySet());
                        unionKeys.addAll(quakes.keySet());

                        unionKeys.removeAll(currentQuakesMap.keySet());
                        quakes.keySet().retainAll(unionKeys);

                        ArrayList<Quake> values = new ArrayList<>(quakes.values());

                        for (int i = 0; i < values.size(); i++) {
                            currentQuakesList.add(i, values.get(i));
                        }

                        if (quakeAdapter == null) {
                            quakesListView = findViewById(R.id.listQuakes);
                            quakeAdapter = new QuakeListAdapter(context, R.layout.quakes_list, currentQuakesList);
                            quakesListView.setAdapter(quakeAdapter);

                        } else {
                            quakeAdapter.notifyDataSetChanged();
                        }

                        return null;
                    }
                });

                // Notify adapter
//                quakeAdapter.notifyDataSetChanged();

                // Dismiss refreshing dialogue
                swipeRefreshLayout.setRefreshing(false);

            }
        });

    }

}