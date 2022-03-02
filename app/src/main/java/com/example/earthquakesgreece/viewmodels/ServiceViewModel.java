package com.example.earthquakesgreece.viewmodels;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.earthquakesgreece.BuildConfig;
import com.example.earthquakesgreece.Parameters;
import com.example.earthquakesgreece.QuakemlParser;
import com.example.earthquakesgreece.model.Quake;

import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;

import lombok.SneakyThrows;

public class ServiceViewModel implements Runnable, ResponseCallback {

    private final LinkedHashMap<String, Quake> quakes = new LinkedHashMap<>();
    private final Context context;
    private ResponseCallback callback;

    public ServiceViewModel (Context context) {
        this.context = context;
    }

    public void fetchData(ResponseCallback callback) {
        this.callback = callback;
        this.run();
    }

    @SneakyThrows
    @Override
    public void run() {

        RequestQueue queue = Volley.newRequestQueue(context);
//        String url ="https://eida.gein.noa.gr/fdsnws/event/1/query?starttime=2021-09-28T00:00:00&endtime=2021-09-28T10:00:00 &minlatitude=34.245454&maxlatitude=41.744376&minlongitude=19.359372&maxlongitude=29.664913&format=xml";
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

        String timeNow = formatter.format(LocalDateTime.now(Clock.systemUTC())); // endtime
        String xHoursAgo = formatter.format(LocalDateTime.now(Clock.systemUTC()).minusHours(Parameters.last6Hours)); // starttime

        Uri.Builder builder = new Uri.Builder();
        builder.scheme(Parameters.HTTPS)
                .authority(Parameters.BASEURL)
                .appendPath(Parameters.PATH_FDSNWS)
                .appendPath(Parameters.PATH_EVENT)
                .appendPath(Parameters.PATH_1)
                .appendPath(Parameters.PATH_QUERY)
                .appendQueryParameter(Parameters.STARTTIME, xHoursAgo)
                .appendQueryParameter(Parameters.ENDTIME, timeNow)
                .appendQueryParameter(Parameters.MINLAT, Parameters.MINLAT_GREECE)
                .appendQueryParameter(Parameters.MAXLAT, Parameters.MAXLAT_GREECE)
                .appendQueryParameter(Parameters.MINLONG, Parameters.MINLONG_GREECE)
                .appendQueryParameter(Parameters.MAXLONG, Parameters.MAXLONG_GREECE);

        String finalULR = builder.build().toString();

        if (BuildConfig.DEBUG) Log.d("Quake","finalURL = " +finalULR);


        // Formulate the request and handle the response.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, finalULR,
                response -> {
                    // Do something with the response
                    Log.d("myTag", "ResponseLog = " + response);

                    QuakemlParser qml = new QuakemlParser();

                    try {
                        if (response.isEmpty()) {
                            throw new IOException();
                        }

                        InputStream input = new ByteArrayInputStream(response.getBytes(StandardCharsets.UTF_8));
                        qml.startParser(input);

                        quakes.putAll(qml.getQuakes());
                        callback.onSuccess(quakes);

                    } catch (XmlPullParserException | IOException e) {
                        Toast.makeText(context, "Failed: " +e.toString(), Toast.LENGTH_SHORT).show();

                        e.printStackTrace();
                    }
                },
                error -> {
                    // Handle error
                    Log.d("myTag","Failed: " +error);
                });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy( 50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    @Override
    public LinkedHashMap<String, Quake> onSuccess(LinkedHashMap<String, Quake> quakesToRet) {
        quakesToRet = quakes;
        return quakesToRet;
    }
}
