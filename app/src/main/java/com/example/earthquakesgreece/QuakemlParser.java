package com.example.earthquakesgreece;

import android.util.Log;
import android.util.Xml;

import com.example.earthquakesgreece.model.Quake;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Getter;

public class QuakemlParser {

    @Getter
    private final List<Quake> quakes= new ArrayList<Quake>();

    private static final String ns = null;

    public List<Quake> parse(InputStream in) throws XmlPullParserException, IOException {

        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readFeed(parser);

        } finally {
            in.close();
        }
    }

    private List<Quake> readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        List<Quake> events = new ArrayList();

        parser.require(XmlPullParser.START_TAG, ns, Parameters.QUAKEML_TAG);
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the entry tag
            if (name.equals(Parameters.EVENT_TAG)) {
//                events.add(readEntry(parser));
                events.add(readQuakeML(parser));

            } else {
                skip(parser);
            }
        }
        return events;
    }


    // Parses contents of Quake ML ('event'). If it encounters a 'description'(region name), 'magnitude'(nested 'mag'), 'creationInfo' etc,
    // hands them off to their respective "read" methods for processing. Otherwise, skips the tag.
    public Quake readQuakeML(XmlPullParser parser) throws XmlPullParserException, IOException {

        parser.require(XmlPullParser.START_TAG, null, Parameters.EVENT_TAG);

        String region = null;
        Date date = null;
        int mag = 0;
        int depth = 0;
        float longitude = 0;
        float latitude = 0;

        while (parser.next() != XmlPullParser.END_TAG ) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            String name = parser.getName();

            if (name.equals(Parameters.DESCRIPTION_TAG)) {
                region = readDescription(parser);

            } else if (name.equals("summary")) {

            } else if (name.equals("link")) {

            } else {

            }

        }
        return new Quake(region, date, mag, depth, longitude, latitude);

    }


    // Processes Description tags in the event.
    private String readDescription(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, null, Parameters.DESCRIPTION_TAG);
        String title = readDescriptionextTag(parser);
        parser.require(XmlPullParser.END_TAG, null, Parameters.DESCRIPTION_TAG);
        return title;
    }

    // For the tags TEXT nested in DESCRIPTION.
    private String readDescriptionextTag(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();

            Log.d("Quake","Result = " + result );

            parser.nextTag();
        }
        return result;
    }


    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }

}
