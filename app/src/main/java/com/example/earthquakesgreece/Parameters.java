package com.example.earthquakesgreece;

public interface Parameters {

    public static final String MINLAT = "34.245454";
    public static final String MAXLAT  = "41.744376";
    public static final String MINLONG = "19.359372";
    public static final String MAXLONG = "29.664913";
    public static final String FORMATREPONSE = "xml";


    public static final String QUAKEML_TAG = "q:quakeml";
    public static final String EVENT_TAG = "event";

    public static final String DESCRIPTION_TAG = "description";
    public static final String DESCRIPTION_TEXT_TAG = "text";

}

// Use
//textView.setText(Constants.MINLAT);