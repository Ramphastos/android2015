package com.tom.maps;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements GoogleMap.OnMyLocationChangeListener, LocationListener {

    LatLng seven = new LatLng(25.0257431, 121.5385991);
    LatLng sce = new LatLng(25.0258420, 121.5380680);
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    Map<Marker, Friend> friends = new HashMap<>();
    private Friend f1;
    private Friend f2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        f1 = new Friend("湯老猴", R.drawable.monkey, "09999999");
        f2 = new Friend("小豬", R.drawable.pig, "091111111");
        setUpMapIfNeeded();
        String s = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?key=AIzaSyCBNQlgABwWOXT4iV7C3sjHOUijYtLtSrs&location=25.0258420,121.5380680&radius=500";
        new PlacesTask().execute(s);
    }

    class IconTask extends AsyncTask<String, Void, Bitmap>{
        Marker marker;
        public IconTask(Marker marker){
            this.marker = marker;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap bitmap = null;
            try {
                URL url = new URL(params[0]);
                bitmap = BitmapFactory.decodeStream(url.openStream());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            marker.setIcon(BitmapDescriptorFactory.fromBitmap(bitmap));
        }
    }

    class PlacesTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... params) {
            StringBuffer sb = new StringBuffer();
            try {
                URL url = new URL(params[0]);
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line = in.readLine();

                while(line!=null){
                    sb.append(line);
                    line = in.readLine();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return sb.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            Log.d("JSON", s);
            try {
                JSONObject obj = new JSONObject(s);
                JSONArray results = obj.getJSONArray("results");
                for (int i=0; i<results.length();i++){
                    JSONObject place = results.getJSONObject(i);
                    JSONObject geometry = place.getJSONObject("geometry");
                    JSONObject location = geometry.getJSONObject("location");
                    double lat = location.getDouble("lat");
                    double lng = location.getDouble("lng");
                    String name = place.getString("name");
                    String iconURL = place.getString("icon");
                    Log.d("PLACE", name + "/" + lat + "/" + lng);
                    Marker m = mMap.addMarker(new MarkerOptions()
                        .title(name)
                        .position(new LatLng(lat, lng)));
                    new IconTask(m).execute(iconURL);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        //mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        //一開始即移動地圖到文化推廣
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(25.0258420, 121.5380680), 18));
//        mMap.setOnMyLocationChangeListener(this);
        LocationManager locManager =
                (LocationManager)getSystemService(LOCATION_SERVICE);
        try {
            //locManager.requestLocationUpdates("gps", 5*1000, 0, this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                View view = getLayoutInflater().inflate(R.layout.friend, null);
                final Friend friend = friends.get(marker);
                TextView tv1 = (TextView) view.findViewById(R.id.info_title);
                tv1.setText(friend.getName());
                ImageView iv = (ImageView) view.findViewById(R.id.info_img);
                iv.setImageResource(friend.getAvatarId());
                TextView tv = (TextView) view.findViewById(R.id.info_phone);
                tv.setText(friend.getPhone());
                return view;
            }
        });

        Marker f1Marker = mMap.addMarker(
                new MarkerOptions()
                        .position(seven)
                        .title(f1.getName())
                        .icon(BitmapDescriptorFactory.fromResource(f1.getAvatarId()))
        );

        friends.put(f1Marker, f1);
        friends.put(mMap.addMarker(new MarkerOptions()
                .position(new LatLng(25.0258420, 121.5380680))
                .icon(BitmapDescriptorFactory.fromResource(f2.getAvatarId()))
                .title(f2.getName())), f2);
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Friend friend = friends.get(marker);
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+friend.getPhone()));
                startActivity(intent);
            }
        });
    }

    @Override
    public void onMyLocationChange(Location location) {
        mMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(
                location.getLatitude(),
                location.getLongitude()
        )));
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("LOCATION", "onLocationChanged");
        mMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(
                location.getLatitude(),
                location.getLongitude()
        )));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("LOCATION", "onStatusChanged");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("LOCATION", "onProviderEnabled");
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("LOCATION", "onProviderDisabled");
    }
}
