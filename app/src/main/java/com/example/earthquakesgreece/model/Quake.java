package com.example.earthquakesgreece.model;

import android.util.Log;

import androidx.annotation.NonNull;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class Quake {

    @Getter
    private final String regionName;

    @Getter
    private final LocalDateTime date;

    @Getter
    private final float magnitude;

    @Getter
    private final int depth;

    @Getter
    private final float longitude;

    @Getter
    private final float latitude;


    public Quake(String regionName, String date, float magnitude, int depth, float longitude, float latitude) {
        this.regionName = regionName;

        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        this.date = LocalDateTime.parse(date, formatter);
        this.magnitude = magnitude;
        this.depth = depth;
        this.longitude = longitude;
        this.latitude = latitude;
    }

//    @NonNull
//    @Override
//    public String toString() {
//        return " Region = "+regionName + "\n Mag= " + magnitude + "\n Depth= " + depth;
//    }
}
