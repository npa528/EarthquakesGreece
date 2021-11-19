package com.example.earthquakesgreece.viewmodels;

import android.content.Context;

import com.example.earthquakesgreece.QuakemlParser;
import com.example.earthquakesgreece.R;
import com.example.earthquakesgreece.model.Quake;

import java.io.InputStream;
import java.util.ArrayList;

import lombok.Getter;
import lombok.SneakyThrows;

public class ServiceViewModel implements Runnable {

    @Getter
    private ArrayList<Quake> quakes = new ArrayList<>();

    private Context context;

    public ServiceViewModel (Context context) {
        this.context = context;
    }

    @SneakyThrows
    @Override
    public void run() {

        final InputStream inputStream = context.getResources().openRawResource(R.raw.quakeml);
        QuakemlParser qml = new QuakemlParser();
        qml.setInputStream(inputStream);
        qml.startParser();

        quakes.addAll(qml.getQuakes());
    }


    public void readXML(Context context) {


    }



//    public void getData(Context context, final ResponseCallback callBack) {
//        RequestQueue queue = Volley.newRequestQueue(context);
////        String url ="https://eida.gein.noa.gr/fdsnws/event/1/query?starttime=2021-09-28T00:00:00&endtime=2021-09-28T10:00:00 &minlatitude=34.245454&maxlatitude=41.744376&minlongitude=19.359372&maxlongitude=29.664913&format=xml";
//
//        String url ="https://eida.gein.noa.gr/fdsnws/event/1/query?" +
//                Constants.STARTTIME + "=2021-11-09T00:00:00&" +
//                Constants.ENDTIME + "=2021-11-09T10:00:00&" +
//                Constants.MINLAT + "=34.245454&" +
//                Constants.MAXLAT + "=41.744376&" +
//                Constants.MINLONG + "=19.359372&" +
//                Constants.MAXLONG + "=29.664913&";
////                Constants.FORMAT + "=xml";
//
//
//        // Formulate the request and handle the response.
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                response -> {
//                    // Do something with the response
//                    Log.d("myTag", "ResponseLog = " + response);
//
////                        QuakemlParser qml = new QuakemlParser();
////                        qml.parse(new ByteArrayInputStream(response.getBytes()));
//                    callBack.onSuccess();
//                },
//                error -> {
//                    // Handle error
//                    Log.d("myTag","Failed");
//                });
//
//
//        // Add the request to the RequestQueue.
//        queue.add(stringRequest);
//    }


}
