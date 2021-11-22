package com.example.earthquakesgreece.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.Getter;

public class Quake {

    @Getter
    private final String eventId;

    @Getter
    private final String regionName;

    @Getter
    private final LocalDateTime date;

    @Getter
    private final String formatedDate;

    @Getter
    private final double magnitude;

    @Getter
    private final double depth;

    @Getter
    private final float longitude;

    @Getter
    private final float latitude;


    public Quake(String eventId, String regionName, String date, double magnitude, double depth, float longitude, float latitude) {
        this.eventId = eventId;
        this.regionName = regionName;

        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MM dd HH:mm:ss");
        this.date = LocalDateTime.parse(date, formatter);

        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        this.formatedDate = this.date.format(formatter2);

        this.magnitude = round(magnitude, 1);
        this.depth = round(depth, 1);

        this.longitude = longitude;
        this.latitude = latitude;
    }

    private static double round (double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }
}
