package com.example.earthquakesgreece.viewmodels;

import com.example.earthquakesgreece.model.Quake;

import java.util.ArrayList;

public interface ResponseCallback {
    ArrayList<Quake> onSuccess(ArrayList<Quake> quakes);
}
