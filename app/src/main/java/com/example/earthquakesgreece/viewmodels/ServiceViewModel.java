package com.example.earthquakesgreece.viewmodels;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.XmlResourceParser;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.earthquakesgreece.Constants;
import com.example.earthquakesgreece.QuakemlParser;
import com.example.earthquakesgreece.R;
import com.example.earthquakesgreece.ResponseCallback;

import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Executor;

import lombok.SneakyThrows;

public class ServiceViewModel {

    public void readXML(Context context) throws IOException, XmlPullParserException {

        final InputStream inputStream = context.getResources().openRawResource(R.raw.quakeml);
        QuakemlParser qml = new QuakemlParser();
        qml.parse(inputStream);
    }

    public void getData(Context context, final ResponseCallback callBack) {
        RequestQueue queue = Volley.newRequestQueue(context);
//        String url ="https://eida.gein.noa.gr/fdsnws/event/1/query?starttime=2021-09-28T00:00:00&endtime=2021-09-28T10:00:00 &minlatitude=34.245454&maxlatitude=41.744376&minlongitude=19.359372&maxlongitude=29.664913&format=xml";

        String url ="https://eida.gein.noa.gr/fdsnws/event/1/query?" +
                Constants.STARTTIME + "=2021-11-09T00:00:00&" +
                Constants.ENDTIME + "=2021-11-09T10:00:00&" +
                Constants.MINLAT + "=34.245454&" +
                Constants.MAXLAT + "=41.744376&" +
                Constants.MINLONG + "=19.359372&" +
                Constants.MAXLONG + "=29.664913&";
//                Constants.FORMAT + "=xml";


        // Formulate the request and handle the response.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @SneakyThrows
                    @Override
                    public void onResponse(String response) {
                        // Do something with the response
                        Log.d("myTag", "ResponseLog = " + response);

                        QuakemlParser qml = new QuakemlParser();
                        qml.parse(new ByteArrayInputStream(response.getBytes()));
                        callBack.onSuccess();
                    }
                },
                error -> {
                    // Handle error
                    Log.d("myTag","Failed");
                });


        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
