package com.tom.freshchat;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

/**
 * Created by Administrator on 2015/11/5.
 */
public class MyService extends Service implements GoogleApiClient.ConnectionCallbacks, LocationListener {
    private GoogleApiClient api;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("MYSERVICE", "onCreate");
        api = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .build();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("MYSERVICE", "onStartCommand");
        api.connect();

        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onConnected(Bundle bundle) {
        LocationRequest req = new LocationRequest();
        req.setInterval(1000);
        req.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        LocationServices.FusedLocationApi.requestLocationUpdates(api, req, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("LOCATION", location.getLatitude()+"/"+location.getLongitude());
    }
}
