package com.example.earthquakesgreece;

import android.util.Log;
import android.util.Xml;

import com.example.earthquakesgreece.model.Quake;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

public class QuakemlParser {

    @Getter
    private final ArrayList<Quake> quakes= new ArrayList<>();


    @Setter
    private InputStream inputStream;

//    @Override
//    public ArrayList<Quake> getListQuakes() {
//        return quakes;
//    }



    public void startParser() throws XmlPullParserException, IOException {

        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(inputStream, null);
            parser.nextTag();
            readQuakeML(parser);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            inputStream.close();
        }
    }

    private void readQuakeML(XmlPullParser parser) throws XmlPullParserException, IOException {

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

        parser.require(XmlPullParser.START_TAG, null, Parameters.EVENT_TAG);
        while (parser.next() != XmlPullParser.END_DOCUMENT) {

            if (parser.getName() == null) {
                continue;
            }

            if (parser.getEventType() == XmlPullParser.END_TAG && parser.getName().equals(Parameters.EVENT_TAG)) {
                if (BuildConfig.DEBUG) Log.d("Quake","Event tag reached - create Quake ");
                break;
            }

            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

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
