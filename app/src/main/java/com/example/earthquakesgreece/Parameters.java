package com.example.earthquakesgreece;

public interface Parameters {

    public static final String MINLAT = "34.245454";
    public static final String MAXLAT  = "41.744376";
    public static final String MINLONG = "19.359372";
    public static final String MAXLONG = "29.664913";
    public static final String FORMATREPONSE = "xml";


    public static final String QUAKEML_TAG = "q:quakeml";
    public static final String EVENTPARAMETERS_TAG = "eventParameters";
    public static final String EVENT_TAG = "event";

    public static final String DESCRIPTION_TAG = "description";
    public static final String DESCRIPTION_TEXT_TAG = "text";

    public static final String CREATIONINFO_TAG = "creationInfo";
    public static final String CREATIONTIME_TAG = "creationTime";

    public static final String MAGNITUDE_TAG = "magnitude";
    public static final String MAGNITUDE_CREATION_INFO_TAG = "creationInfo";
    public static final String MAGNITUDE_CREATION_TIME_TAG = "creationTime";
    public static final String MAGNITUDE_MAG_TAG = "mag";
    public static final String VALUE_TAG = "value";

    public static final String ORIGIN_TAG = "origin";
    public static final String TIME_TAG = "time";
    public static final String LONGITUDE_TAG = "longitude";
    public static final String LATITUDE_TAG = "latitude";

    public static final String DEPTH_TAG = "depth";


}

// Use
//textView.setText(Constants.MINLAT);