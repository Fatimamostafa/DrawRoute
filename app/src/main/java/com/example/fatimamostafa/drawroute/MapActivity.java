package com.example.fatimamostafa.drawroute;

import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

public class MapActivity extends AppCompatActivity implements DrawRoute.onDrawRoute {
    GoogleMap googleMap;
    MapView mMapView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        mMapView = (MapView) findViewById(R.id.mapView);

        //mMapView.onCreate(null);

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {


                googleMap = mMap;

                if (Build.VERSION.SDK_INT >= 23 &&
                        ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                googleMap.setMyLocationEnabled(true);

                googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        String tag = marker.getTag().toString();
                        //Toast.makeText(getActivity(), userInformationModelArray.get(Integer.parseInt(tag)).getName(), Toast.LENGTH_SHORT).show();
                        marker.showInfoWindow();
                        return true;
                    }
                });


                // For dropping a marker at a point on the Map
                TrackGPS gps = new TrackGPS(getApplicationContext());
                LatLng myLoc = new LatLng(gps.getLatitude(),gps.getLongitude());
                //googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker Title").snippet("Marker Description"));

                // For zooming automatically to the location of the marker
                CameraPosition cameraPosition = new CameraPosition.Builder().target(myLoc).zoom(13).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


                googleMap.clear();
                LatLng latLng = new LatLng(23.721343, 90.428789);
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15);
                googleMap.animateCamera(cameraUpdate);
                String key = "AIzaSyAb-lenTW4kt2w_8Aa80mwBisD37eri2_U";//"AIzaSyCXu4kn4jzdLVp54AhkHNOrBMAyq4q4bXI";

                DrawRoute.getInstance(MapActivity.this, getApplicationContext())
                        .setFromLatLong(myLoc.latitude, myLoc.longitude)
                        .setToLatLong(23.803813, 90.426000)
                        .setGmapAndKey(key, googleMap).run();

            }
        });


    }

    @Override
    public void afterDraw(String result) {

    }
}
