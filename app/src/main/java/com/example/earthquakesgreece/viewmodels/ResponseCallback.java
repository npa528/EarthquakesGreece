package com.example.earthquakesgreece.viewmodels;

import com.example.earthquakesgreece.model.Quake;

import java.util.LinkedHashMap;

public interface ResponseCallback {

//    ArrayList<Quake> onSuccess(ArrayList<Quake> quakes);

    LinkedHashMap<String, Quake> onSuccess(LinkedHashMap<String, Quake> quakes);

}
