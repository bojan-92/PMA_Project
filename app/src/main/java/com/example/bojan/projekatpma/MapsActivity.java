package com.example.bojan.projekatpma;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.Toast;

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


        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(final com.google.android.gms.maps.model.Marker marker) {
                DialogInterface.OnClickListener dialogOnClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case DialogInterface.BUTTON_POSITIVE:
                                marker.remove();
                                data.deleteMarker(new Marker(marker.getTitle(),marker.getSnippet(),
                                        marker.getPosition().latitude + " " + marker.getPosition().longitude));
                                break;
                            case DialogInterface.BUTTON_NEUTRAL:
                                DialogInterface.OnClickListener dialogOnClickListener1 = new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        switch (i){
                                            case DialogInterface.BUTTON_POSITIVE:
                                                marker.remove();
                                                data.deleteMarker(new Marker(marker.getTitle(),marker.getSnippet(),
                                                        marker.getPosition().latitude + " " + marker.getPosition().longitude));
                                                break;
                                            case DialogInterface.BUTTON_NEGATIVE:
                                                break;
                                        }
                                    }
                                };
                                AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                                builder1.setMessage("Are you sure?").setPositiveButton("Yes",dialogOnClickListener1)
                                        .setNegativeButton("No", dialogOnClickListener1).show();
                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Title: " + " " + marker.getTitle() + "\n" +
                "Description: " + " " + marker.getSnippet()).setNeutralButton("Delete",dialogOnClickListener)
                        .setNegativeButton("Edit", dialogOnClickListener).setTitle("Details").show();
            }
        });

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
                    final String coordLat = lat.toString();
                    final String coordLng = lng.toString();



                    AlertDialog.Builder builder = new AlertDialog.Builder(context);

                    LinearLayout layout = new LinearLayout(context);
                    layout.setOrientation(LinearLayout.VERTICAL);

                    final TextView title = new TextView(context);
                    title.setText("Add new marker");
                    title.setPadding(10,30,10,10);
                    title.setGravity(Gravity.CENTER);
                    title.setTextSize(20);
                    builder.setCustomTitle(title);

                    final EditText titleBox = new EditText(context);
                    titleBox.setHint("Title");
                    titleBox.setHintTextColor(Color.GRAY);
                    layout.addView(titleBox);

                    final EditText descriptionBox = new EditText(context);
                    descriptionBox.setHint("Description...");
                    descriptionBox.setHintTextColor(Color.GRAY);
                    descriptionBox.setHeight(200);
                    descriptionBox.setGravity(Gravity.TOP);
                    layout.addView(descriptionBox);


                    builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            m.setTitle(String.valueOf(titleBox.getText().toString()));
                            m.setDescription(String.valueOf(descriptionBox.getText().toString()));
                            m.setPosition(coordLat + " " + coordLng);
                            data.addMarker(m);
                            dialogInterface.dismiss();
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });

                    builder.setView(layout);
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            }
        });
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
