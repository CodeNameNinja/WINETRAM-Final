package com.example.winetramapp.DriverSystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.PowerManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.winetramapp.Constants;
import com.example.winetramapp.LoginScreen;
import com.example.winetramapp.R;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;

import java.util.ArrayList;
import java.util.Map;

public class DriverMapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Switch mWorkingSwitch;
    private Boolean isLoggingOut = false;
    private BroadcastReceiver broadcastReceiver;
    private String TAG = DriverMapActivity.class.getSimpleName();
    private int DriverSelectedLine = DriverLoginActivity.DriverSelectedLine;
    final String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();


    @Override
    protected void onResume() {
        super.onResume();
        if(broadcastReceiver == null){
            broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    Log.i(TAG,"\n" +intent.getExtras().get("coordinates"));
                }
            };
        }
        registerReceiver(broadcastReceiver,new IntentFilter("location_update"));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        runtime_permission();


    }

    public void logoutBtn(View view)
    {
        isLoggingOut = true;
        Intent i = new Intent(getApplicationContext(),DriverLocationService.class);
        stopService(i);
        disconnectDriver();
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(DriverMapActivity.this, LoginScreen.class);
        startActivity(intent);
        finish();

    }
    private void getSelectedLineTopBarColor()
    {

        final DatabaseReference driversAvailableReference = FirebaseDatabase.getInstance().getReference().child("driversAvailable").child("drivers");
        driversAvailableReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.child("RedLine").hasChild(userId)){
                    getSupportActionBar().setTitle("Red Line");
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xffde4e4e));
                }
                if(dataSnapshot.child("BlueLine").hasChild(userId)){
                    getSupportActionBar().setTitle("Blue Line");
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff4e96de));
                }
                if(dataSnapshot.child("GreenLine").hasChild(userId)){
                    getSupportActionBar().setTitle("Green Line");
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff4ede58));
                }
                if(dataSnapshot.child("YellowLine").hasChild(userId)){
                    getSupportActionBar().setTitle("Yellow Line");
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xffded94e));
                }
                if(dataSnapshot.child("OrangeLine").hasChild(userId)){
                    getSupportActionBar().setTitle("Orange Line");
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xffde8c4e));
                }
                if(dataSnapshot.child("PurpleLine").hasChild(userId)){
                    getSupportActionBar().setTitle("Purple Line");
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff7c4ede));
                }
                if(dataSnapshot.child("PinkLine").hasChild(userId)){
                    getSupportActionBar().setTitle("Pink Line");
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xffde4eb3));
                }
                if(dataSnapshot.child("GreyLine").hasChild(userId)){
                    getSupportActionBar().setTitle("Grey Line");
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff858284));
                }
                if(dataSnapshot.child("Tram Franschhoek").hasChild(userId)){
                    getSupportActionBar().setTitle("Tram Franschhoek Line");
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff858284));
                }
                if(dataSnapshot.child("Tram Drakenstein").hasChild(userId)){
                    getSupportActionBar().setTitle("Drakenstein Tram Line");
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff7c4ede));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void disconnectDriver()
    {
        final DatabaseReference driversAvailableReference = FirebaseDatabase.getInstance().getReference().child("driversAvailable").child("drivers");
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("driversAvailable").child("drivers");

        driversAvailableReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("RedLine").hasChild(userId)){
                    DatabaseReference cRef = ref.child("RedLine");
                    GeoFire geoFire = new GeoFire(cRef);
                    geoFire.removeLocation(userId);
                }
                if(dataSnapshot.child("BlueLine").hasChild(userId)){
                    DatabaseReference cRef = ref.child("BlueLine");
                    GeoFire geoFire = new GeoFire(cRef);
                    geoFire.removeLocation(userId);
                }
                if(dataSnapshot.child("GreenLine").hasChild(userId)){
                    DatabaseReference cRef = ref.child("GreenLine");
                    GeoFire geoFire = new GeoFire(cRef);
                    geoFire.removeLocation(userId);
                }
                if(dataSnapshot.child("YellowLine").hasChild(userId)){
                    DatabaseReference cRef = ref.child("YellowLine");
                    GeoFire geoFire = new GeoFire(cRef);
                    geoFire.removeLocation(userId);
                }
                if(dataSnapshot.child("PurpleLine").hasChild(userId)){
                    DatabaseReference cRef = ref.child("PurpleLine");
                    GeoFire geoFire = new GeoFire(cRef);
                    geoFire.removeLocation(userId);
                }
                if(dataSnapshot.child("OrangeLine").hasChild(userId)){
                    DatabaseReference cRef = ref.child("OrangeLine");
                    GeoFire geoFire = new GeoFire(cRef);
                    geoFire.removeLocation(userId);
                }
                if(dataSnapshot.child("PinkLine").hasChild(userId)){
                    DatabaseReference cRef = ref.child("PinkLine");
                    GeoFire geoFire = new GeoFire(cRef);
                    geoFire.removeLocation(userId);
                }
                if(dataSnapshot.child("GreyLine").hasChild(userId)){
                    DatabaseReference cRef = ref.child("GreyLine");
                    GeoFire geoFire = new GeoFire(cRef);
                    geoFire.removeLocation(userId);
                }
                if(dataSnapshot.child("Tram Franschhoek").hasChild(userId)){
                    DatabaseReference cRef = ref.child("Tram Franschhoek");
                    GeoFire geoFire = new GeoFire(cRef);
                    geoFire.removeLocation(userId);
                }
                if(dataSnapshot.child("Tram Drakenstein").hasChild(userId)){
                    DatabaseReference cRef = ref.child("Tram Drakenstein");
                    GeoFire geoFire = new GeoFire(cRef);
                    geoFire.removeLocation(userId);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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
        LatLng franschhoek = new LatLng(-33.910979, 19.119677);
        mMap.addMarker(new MarkerOptions().position(franschhoek).title("Franschhoek Ticket Office"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(franschhoek));
        mMap.setMyLocationEnabled(true);
        final Handler handler = new Handler();

        handler.post(new Runnable() {
            @Override
            public void run() {
                CircleOptions circleOptions = new CircleOptions();
                Circle circle;
                for (Map.Entry<String, Map.Entry<LatLng,Integer>> entry : Constants.MAIN_GEOFENCES.entrySet()) {
                    LatLng latlng = new LatLng(entry.getValue().getKey().latitude, entry.getValue().getKey().longitude);
                    circleOptions.center(latlng)
                            .fillColor(Color.argb(64, 0, 255, 0))
                            .strokeColor(Color.GREEN)
                            .strokeWidth(1)
                            .radius(entry.getValue().getValue());
                    circle = mMap.addCircle(circleOptions);
                }
            }
        });
    }

    public void runtime_permission()
    {
        String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
        Permissions.check(this/*context*/, permissions, "Location permissions are required to get your location"/*rationale*/, null/*options*/, new PermissionHandler() {
            @Override
            public void onGranted() {
                mWorkingSwitch = (Switch) findViewById(R.id.workingSwitch);
                mWorkingSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            Intent i = new Intent(getApplicationContext(),DriverLocationService.class);
                            startService(i);
                            getSelectedLineTopBarColor();

                        } else {
                            Intent i = new Intent(getApplicationContext(),DriverLocationService.class);
                            stopService(i);
                            disconnectDriver();

                        }
                    }
                });
            }

            @Override
            public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                super.onDenied(context, deniedPermissions);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(broadcastReceiver != null){
            unregisterReceiver(broadcastReceiver);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        disconnectDriver();
    }
}
