package com.example.rohitranjan.vehiclepayment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;


public class GetDirectionsData extends AsyncTask<Object,String,String>{

    GoogleMap mMap;
    String url;
    String googleDirectionsData;
    String duration, distance,distanceValue;
    LatLng latLng;

    @Override
    protected String doInBackground(Object... objects) {
        mMap = (GoogleMap) objects[0];
        url = (String)objects[1];
        latLng = (LatLng)objects[2];


        DownloadUrl downloadUrl = new DownloadUrl();
        try{
            googleDirectionsData = downloadUrl.readUrl(url);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return googleDirectionsData;
    }

    @Override
    protected void onPostExecute(String result) {
        mMap.clear();
        String[] directionsList;
        DataParser parser = new DataParser();
        directionsList = parser.parseDirections(result);
        displayDirection(directionsList);

        HashMap<String,String> directionsList1 = null;
       DataParser parser1 = new DataParser();
       directionsList1 = parser1.parseDirections1(result);
       duration = directionsList1.get("duration");
       distance = directionsList1.get("distance");
       distanceValue = directionsList1.get("distanceValue");
       MarkerOptions markerOptions = new MarkerOptions();
       markerOptions.position(latLng);
       markerOptions.draggable(true);
       markerOptions.title("Duration="+duration);
       markerOptions.snippet("Distance= "+distance);

       if (Integer.parseInt(distanceValue) <= 5000){
           Log.d("hello","this is success");

       }

       mMap.addMarker(markerOptions);
    }

    public void displayDirection(String[] directionList){

        int count = directionList.length;
        for (int i = 0; i<count;i++){
            PolylineOptions options = new PolylineOptions();
            options.color(Color.RED);
            options.width(10);
            options.addAll(PolyUtil.decode(directionList[i]));

            mMap.addPolyline(options);
        }
    }
}
