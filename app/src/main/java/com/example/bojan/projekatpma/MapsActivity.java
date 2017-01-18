package com.example.bojan.projekatpma;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.bojan.projekatpma.db.MarkerDataSource;
import com.example.bojan.projekatpma.model.Marker;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapsActivity extends FragmentActivity implements LocationListener {

    private GoogleMap mMap;
    Context context = this;
    MarkerDataSource data;
    EditText titleInput;
    EditText descInput;
    Button showBtn;
    LinearLayout linearLayout;
    Marker m = new Marker();


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
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
        final List<Marker> markers = data.getAllMarkers();
        for (int i = 0; i < markers.size(); i++) {
            String[] slatlng = markers.get(i).getPosition().split(" ");
            LatLng latLng = new LatLng(Double.valueOf(slatlng[0]), Double.valueOf(slatlng[1]));
            mMap.addMarker(new MarkerOptions()
                    .title(markers.get(i).getTitle())
                    .snippet(markers.get(i).getDescription())
                    .position(latLng)
            );
        }


        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(final com.google.android.gms.maps.model.Marker marker) {
                DialogInterface.OnClickListener dialogOnClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case DialogInterface.BUTTON_NEUTRAL:
                                DialogInterface.OnClickListener dialogOnClickListener1 = new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        switch (i) {
                                            case DialogInterface.BUTTON_POSITIVE:
                                                marker.remove();
                                                data.deleteMarker(new Marker(marker.getTitle(), marker.getSnippet(),
                                                        marker.getPosition().latitude + " " + marker.getPosition().longitude));
                                                break;
                                            case DialogInterface.BUTTON_NEGATIVE:
                                                break;
                                        }
                                    }
                                };
                                AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                                builder1.setMessage("Are you sure?").setPositiveButton("Yes", dialogOnClickListener1)
                                        .setNegativeButton("No", dialogOnClickListener1).show();
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                LayoutInflater li = LayoutInflater.from(context);
                                final View v1 = li.inflate(R.layout.edit_marker,null);
                                AlertDialog.Builder builder2 = new AlertDialog.Builder(context);
                                builder2.setView(v1);
                                builder2.setCancelable(false);

                                builder2.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int which) {
                                        EditText etEditMarkerTitle = (EditText)v1.findViewById(R.id.etEditMarkerTitle);
                                        EditText etEditMarkerDesc = (EditText)v1.findViewById(R.id.etEditMarkerDesc);

                                        marker.setTitle(String.valueOf(etEditMarkerTitle.getText().toString()));
                                        marker.setSnippet(String.valueOf(etEditMarkerDesc.getText().toString()));
                                        m.setTitle(String.valueOf(etEditMarkerTitle.getText().toString()));
                                        m.setDescription(String.valueOf(etEditMarkerDesc.getText().toString()));
                                        data.updateMarker(new Marker(marker.getTitle(), marker.getSnippet(),
                                                marker.getPosition().latitude + " " + marker.getPosition().longitude));
                                        dialogInterface.dismiss();
                                    }
                                });
                                builder2.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                    }
                                });

                                AlertDialog alertDialog = builder2.create();
                                alertDialog.show();
                                break;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Title: " + " " + marker.getTitle() + "\n" +
                        "Description: " + " " + marker.getSnippet()).setNeutralButton("Delete", dialogOnClickListener)
                        .setNegativeButton("Edit", dialogOnClickListener).setTitle("Details").show();
            }
        });

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(final LatLng latLng) {
                LayoutInflater li = LayoutInflater.from(context);
                final View v = li.inflate(R.layout.add_marker,null);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setView(v);
                builder.setCancelable(false);

                builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        final Double lat = latLng.latitude;
                        Double lng = latLng.longitude;
                        final String coordLat = lat.toString();
                        final String coordLng = lng.toString();

                        EditText etMarkerTitle = (EditText)v.findViewById(R.id.etMarkerTitle);
                        EditText etMarkerDesc = (EditText)v.findViewById(R.id.etMarkerDesc);
                        m.setTitle(String.valueOf(etMarkerTitle.getText().toString()));
                        m.setDescription(String.valueOf(etMarkerDesc.getText().toString()));
                        m.setPosition(coordLat + " " + coordLng);
                        data.addMarker(m);
                        mMap.addMarker(new MarkerOptions()
                                .position(latLng)
                                .snippet(String.valueOf(etMarkerDesc.getText().toString()))
                                .title(String.valueOf(etMarkerTitle.getText().toString()))
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                        dialogInterface.dismiss();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();



            }
        });
    }


    /**
     * da
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
