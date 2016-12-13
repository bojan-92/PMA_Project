package com.example.bojan.projekatpma;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.example.bojan.projekatpma.db.MarkerDataSource;
import com.example.bojan.projekatpma.model.Marker;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapsActivity extends FragmentActivity implements LocationListener {

    private GoogleMap mMap;
    Context context = this;
    MarkerDataSource data;
    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        initMap();
        data = new MarkerDataSource(context);
        try {
            data.open();
        } catch (Exception e) {
            Log.i("Connection unsuccessful", "Connection unsuccessful");
        }
        List<Marker> markers = data.getAllMarkers();
        for (int i = 0; i < markers.size(); i++) {
            String[] slatlng = markers.get(i).getPosition().split(" ");
            LatLng latLng = new LatLng(Double.valueOf(slatlng[0]), Double.valueOf(slatlng[1]));
            mMap.addMarker(new MarkerOptions()
                    .title(markers.get(i).getTitle())
                    .snippet(markers.get(i).getDescription())
                    .position(latLng)
            );
        }
        //data.addMarker(new Marker("test1","snippet test1","40.7769904 -122.4169725"));


        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                if (mMap != null) {
                    mMap.addMarker(new MarkerOptions()
                            .position(latLng)
                            .title("You are here")
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                    Double lat = latLng.latitude;
                    Double lng = latLng.longitude;
                    String coordLat = lat.toString();
                    String coordLng = lng.toString();


                    data.addMarker(new Marker("Your location","Your description",coordLat + " " + coordLng));
                }
            }
        });
//        data.close();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mMap = mapFragment.getMap();

        mMap.setMyLocationEnabled(true);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

    @Override
    public void onLocationChanged(Location location) {

    }
}
