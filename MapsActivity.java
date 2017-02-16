package com.baimingze369gmail.uw_sublet;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;


import com.google.android.gms.games.multiplayer.realtime.Room;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Bundle i = getIntent().getExtras();
        final ArrayList<String> rms = i.getStringArrayList("addrs");

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        //mapFragment.getMapAsync(this);

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
                //ArrayList<String> rms = i.getStringArrayList("addrs");
                System.out.println("size: " + rms.size());
                for(int j=0; j<rms.size(); j++) {
//                    String ct1 = rms.get(j);
//                    LatLng testaddrs;
//                    System.out.println("1111111 " + ct1);
                    try {
                        final String ct1 = rms.get(j);
                        LatLng testaddrs;
                        System.out.println("1111111 " + ct1);
                        double ll[] = geoLocate(ct1);
                        //System.out.println("1111111 " + String.valueOf(ll[0]) + " " + String.valueOf(ll[1]));
                        if(ll != null) {
                            testaddrs = new LatLng(ll[0], ll[1]);
                            mMap.addMarker(new MarkerOptions().position(testaddrs).title(ct1));
                            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener()
                            {

                                @Override
                                public boolean onMarkerClick(Marker arg0) {
                                    System.out.println("----address: " + arg0.getTitle());
                                    for(int x = 0; x < MainActivity.fire.rooms.size(); ++x){
                                        System.out.println("in loop");
                                        System.out.println(MainActivity.fire.rooms.get(x).getAddress());
                                        if(MainActivity.fire.rooms.get(x).getAddress().equals(arg0.getTitle())){
                                            System.out.println("in if statement");
                                            Intent i = new Intent(MapsActivity.this, PostPageActivity.class);
                                            i.putExtra("Address", MainActivity.fire.rooms.get(x).getAddress());
                                            i.putExtra("Price", MainActivity.fire.rooms.get(x).getPrice());
                                            i.putExtra("from", MainActivity.fire.rooms.get(x).getStrat_Time());
                                            i.putExtra("to", MainActivity.fire.rooms.get(x).getEnd_Time());
                                            i.putExtra("type", MainActivity.fire.rooms.get(x).getType());
                                            i.putExtra("name", MainActivity.fire.rooms.get(x).getContact_Name());
                                            i.putExtra("email", MainActivity.fire.rooms.get(x).getEmail());
                                            i.putExtra("description", MainActivity.fire.rooms.get(x).getDescription());
                                            i.putExtra("number", MainActivity.fire.rooms.get(x).getPhone_Number());
                                            i.putExtra("furnish", MainActivity.fire.rooms.get(x).isFurnished());
                                            i.putExtra("parkAvail", MainActivity.fire.rooms.get(x).isParking_Available());
                                            i.putExtra("pet", MainActivity.fire.rooms.get(x).isPet_Friendly());
                                            i.putExtra("washroom", MainActivity.fire.rooms.get(x).isWashroom());
                                            i.putStringArrayListExtra("pics", MainActivity.fire.rooms.get(x).getPics());
                                            i.putExtra("post", MainActivity.fire.rooms.get(x).getPost_code());
                                            i.putExtra("roomid", MainActivity.fire.rooms.get(x).roomid);
                                            startActivity(i);
                                        }
                                    }
                                    return true;
                                }

                            });
                        }
                    } catch (IOException e) {
                        System.out.println("==========wrong addrs=============");
                        e.printStackTrace();
                    }
                }

                //LatLng uw = new LatLng(43.469433, -80.540342);
                //googleMap.addMarker(new MarkerOptions().title("Paris").position(uw));
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
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);

        LatLng newyork;

        try {
            double ll[] = geoLocate("201 lester street, waterloo, Ontario");
            newyork = new LatLng(ll[0], ll[1]);
            mMap.addMarker(new MarkerOptions().position(newyork).title("Marker in New York"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.addMarker(new MarkerOptions().position(newyork).title("Marker in New York"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(newyork));
    }


    public double[] geoLocate(String addr) throws IOException
    {
        Geocoder gc = new Geocoder(this);
        List<Address> list = gc.getFromLocationName(addr, 1);
        if(list.size() == 0) {
            System.out.println("1111111 " + String.valueOf(list.size()));
            return null;
        }
        Address add = list.get(0);
        double lat = add.getLatitude();
        double lng = add.getLongitude();
        return new double[]{lat, lng};
    }

}
