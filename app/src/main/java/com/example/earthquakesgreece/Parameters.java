package com.example.earthquakesgreece;

public interface Parameters {

    /* QUAKEML PARAMETERS */
    String EVENTPARAMETERS_TAG = "eventParameters";
    String EVENT_TAG = "event";
    String EVENT_PUBLICID_TAG = "publicID";
    String DESCRIPTION_TAG = "description";
    String DESCRIPTION_TEXT_TAG = "text";
    String MAGNITUDE_TAG = "magnitude";
    String VALUE_TAG = "value";
    String ORIGIN_TAG = "origin";
    String LONGITUDE_TAG = "longitude";
    String LATITUDE_TAG = "latitude";
    String DEPTH_TAG = "depth";
    String PREFERREDMAG_TAG = "preferredMagnitudeID";
    String TYPE_TAG = "type";
    String EARTHQUAKE_VALUE = "earthquake";


    /* URL PARAMETERS */
    String STARTTIME = "starttime";
    String ENDTIME = "endtime";
    String MINLAT = "minlatitude";
    String MAXLAT = "maxlatitude";
    String MINLONG = "minlongitude";
    String MAXLONG = "maxlongitude";
    int last6Hours = 6;

    String HTTPS = "https";
    String BASEURL = "eida.gein.noa.gr";
    String PATH_FDSNWS = "fdsnws";
    String PATH_EVENT = "event";
    String PATH_1 = "1";
    String PATH_QUERY = "query";

    /* Params Coordinates for Greece */
    String MINLAT_GREECE = "32.959351844878924";
    String MAXLAT_GREECE = "41.978012322308516";
    String MINLONG_GREECE = "18.966053991350208";
    String MAXLONG_GREECE = "30.497690731896405";
}