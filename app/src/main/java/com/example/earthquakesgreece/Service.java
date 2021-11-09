package com.example.earthquakesgreece;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class Service {

    public void getData(Context context ) {
        RequestQueue queue = Volley.newRequestQueue(context);
//        String url ="https://eida.gein.noa.gr/fdsnws/event/1/query?starttime=2021-09-28T00:00:00&endtime=2021-09-28T10:00:00 &minlatitude=34.245454&maxlatitude=41.744376&minlongitude=19.359372&maxlongitude=29.664913&format=xml";

        String url ="https://eida.gein.noa.gr/fdsnws/event/1/query?" +
                Constants.STARTTIME + "=2021-09-28T00:00:00&" +
                Constants.ENDTIME + "=2021-09-28T10:00:00&" +
                Constants.MINLAT + "=34.245454&" +
                Constants.MAXLAT + "=41.744376&" +
                Constants.MINLONG + "=19.359372&" +
                Constants.MAXLONG + "=29.664913&" +
                Constants.FORMAT + "=xml";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                (Response.Listener<String>) response -> {
                    // Display the first 500 characters of the response string.
                    Log.d("myTag","ResponseLog = " + response.toString() );

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("myTag","Failed");
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
