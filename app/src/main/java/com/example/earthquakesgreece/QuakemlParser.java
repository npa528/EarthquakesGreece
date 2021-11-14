package com.example.earthquakesgreece;

import android.util.Log;
import android.util.Xml;

import com.example.earthquakesgreece.model.Quake;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

public class QuakemlParser implements Runnable {

    @Getter
    private final List<Quake> quakes= new ArrayList<Quake>();

    @Setter
    private InputStream inputStream;


    @SneakyThrows
    @Override
    public void run() {

        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(inputStream, null);
            parser.nextTag();
            readFeed(parser);

        } finally {
            inputStream.close();
        }
    }

    private void readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {

        while (parser.next() != XmlPullParser.END_DOCUMENT) {
            if (parser.getEventType() != XmlPullParser.START_TAG || parser.getName().equals(Parameters.EVENTPARAMETERS_TAG)) {
                continue;
            }

            String name = parser.getName();
            // Starts by looking for the entry tag
            if (name.equals(Parameters.EVENT_TAG)) {
                quakes.add(readEvent(parser));
            }
        }
    }


    // Parses contents of Quake ML ('event'). If it encounters a 'description'(region name), 'magnitude'(nested 'mag'), 'creationInfo' etc,
    // hands them off to their respective "read" methods for processing. Otherwise, skips the tag.
    public Quake readEvent (XmlPullParser parser) throws IOException, XmlPullParserException  {

        String region = "";
        String date = "";
        float mag = 0;
        int depth = 0;
        float longitude = 0;
        float latitude = 0;

        int eventType = parser.next();

        while (eventType != XmlPullParser.END_DOCUMENT ) {

            // check for the parent tag
            while (eventType == XmlPullParser.START_TAG) {

                if (parser.getName().equals(Parameters.DESCRIPTION_TAG)) {
                    region = getValue(parser, Parameters.DESCRIPTION_TEXT_TAG);
                    if (BuildConfig.DEBUG) Log.d("Quake","Region = " +region);

                } else if (parser.getName().equals(Parameters.MAGNITUDE_TAG)) {
                    mag = Float.parseFloat(getValue(parser, Parameters.VALUE_TAG));
                    if (BuildConfig.DEBUG) Log.d("Quake","Mag = " +mag);

                } else if (parser.getName().equals(Parameters.ORIGIN_TAG)) {
                    date = getValue(parser, Parameters.VALUE_TAG);
                    if (BuildConfig.DEBUG) Log.d("Quake","Time = " +date);

                } else if (parser.getName().equals(Parameters.LONGITUDE_TAG)) {
                    longitude = Float.parseFloat(getValue(parser, Parameters.VALUE_TAG));
                    if (BuildConfig.DEBUG) Log.d("Quake","Longitude = " +longitude);

                } else if (parser.getName().equals(Parameters.LATITUDE_TAG)) {
                    latitude = Float.parseFloat(getValue(parser, Parameters.VALUE_TAG));

                    if (BuildConfig.DEBUG) Log.d("Quake","Latitude = " +latitude);

                } else if (parser.getName().equals(Parameters.DEPTH_TAG)) {
                    String temp = getValue(parser, Parameters.VALUE_TAG);

                    int indexValue = temp.indexOf('.');
                    if (indexValue != -1) {
                        temp = temp.substring(0, temp.indexOf('.'));
                    }

                    depth = Integer.parseInt(temp);
                    depth /= 1000;
                    if (BuildConfig.DEBUG) Log.d("Quake","Depth = " +depth);
                }

                eventType = parser.next();

            }

            eventType = parser.next();
        }
        return new Quake(region, date, mag, depth, longitude, latitude);
    }


    // Collects Latitude of Quake
    public String getValue(XmlPullParser parser, String tag) throws XmlPullParserException, IOException {

        String value = "";

        while (parser.getEventType() != XmlPullParser.END_DOCUMENT && value.isEmpty()) {
            // check for the parent tag
            while (parser.getEventType() == XmlPullParser.START_TAG) {

                if (parser.getName().equals(tag)) {
                    value = parser.nextText();
                    break;
                }

                parser.next();
            }

            parser.next();
        }

        return value;
    }
}
