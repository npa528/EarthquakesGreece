package com.example.earthquakesgreece;

import com.example.earthquakesgreece.model.Quake;

import java.util.ArrayList;

public interface BackgroundTask extends Runnable {

    ArrayList<Quake> getListQuakes();
}
