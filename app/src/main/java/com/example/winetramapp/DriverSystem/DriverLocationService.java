package com.example.winetramapp.DriverSystem;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.winetramapp.Constants;
import com.example.winetramapp.LoginScreen;
import com.example.winetramapp.R;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

import static com.example.winetramapp.NotificationChannel.CHANNEL_1_ID;
import static com.example.winetramapp.NotificationChannel.CHANNEL_2_ID;

public class DriverLocationService extends Service {

    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationRequest locationRequest;
    public static Location mLastLocation;
    private String TAG = DriverLocationService.class.getSimpleName();
    final String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    DatabaseReference geoRef;
    GeoFire geoFire;
    private NotificationManagerCompat notificationManager;
    String enteredFence;

    final DatabaseReference DriversRef = FirebaseDatabase.getInstance().getReference().child("Users").child("Drivers");

    final DatabaseReference Ref = FirebaseDatabase.getInstance().getReference().child("driversAvailable").child("drivers");

    final DatabaseReference YellowRef = Ref.child("YellowLine");
    final DatabaseReference RedRef = Ref.child("RedLine");
    final DatabaseReference BlueRef = Ref.child("BlueLine");
    final DatabaseReference GreenRef = Ref.child("GreenLine");
    final DatabaseReference PurpleRef = Ref.child("PurpleLine");
    final DatabaseReference OrangeRef = Ref.child("OrangeLine");
    final DatabaseReference PinkRef = Ref.child("PinkLine");
    final DatabaseReference GreyRef = Ref.child("GreyLine");

    final DatabaseReference tramFranchhoekRef = Ref.child("Tram Franschhoek");
    final DatabaseReference tramDrakensteinRef = Ref.child("Tram Drakenstein");

    final DatabaseReference busReference = FirebaseDatabase.getInstance().getReference().child("driversAvailable").child("drivers");
    final DatabaseReference redBusReference = RedRef.child(userId).child("bus");
    final DatabaseReference blueBusReference =  BlueRef.child("bus");
    final DatabaseReference yellowBusReference =  YellowRef.child("bus");
    final DatabaseReference greenBusReference =  GreenRef.child("bus");
    final DatabaseReference purpleBusReference =  PurpleRef.child("bus");
    final DatabaseReference orangeBusReference =  OrangeRef.child("bus");
    final DatabaseReference pinkBusReference =  PinkRef.child("bus");
    final DatabaseReference greyBusReference =  GreyRef.child("bus");

    final DatabaseReference tramFranschhoekDatabaseReference = FirebaseDatabase.getInstance().getReference().child("driversAvailable").child("drivers").child("Tram Franschhoek").child("Tram Franschhoek Location");
    final DatabaseReference tramDrakensteinDatabaseReferenceTarget = FirebaseDatabase.getInstance().getReference().child("driversAvailable").child("drivers").child("Tram Drakenstein").child("Tram Drakenstein Location");


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    LocationCallback mLocationRequestCallback = new LocationCallback(){
        @Override
        public void onLocationResult(LocationResult locationResult) {
            for(Location location : locationResult.getLocations()){
                mLastLocation = location;
                Intent i = new Intent("location_update");
                i.putExtra("coordinates",location.getLongitude()+" "+location.getLatitude());
                sendBroadcast(i);
                saveGeoFire();

            }
        }
    };

    private void GeoFence()
    {
        Handler handler = new Handler();
        handler.post(() -> {
            for (Map.Entry<String, Map.Entry<LatLng,Integer>> entry : Constants.MAIN_GEOFENCES.entrySet()) {
                GeoQuery geoQuery = geoFire.queryAtLocation(new GeoLocation(entry.getValue().getKey().latitude,entry.getValue().getKey().longitude),0.5f);
                geoQuery.addGeoQueryEventListener(new GeoQueryEventListener() {
                    @Override
                    public void onKeyEntered(String key, GeoLocation location) {
                        Log.i("triggered for "+entry.getKey(),"lat:"+entry.getValue().getKey().latitude+",lng:"+entry.getValue().getKey().longitude);
                        TriggeredGeoFence("You have entered into: " +entry.getKey());
                        enteredFence = entry.getKey();
                    }

                    @Override
                    public void onKeyExited(String key) {
                        TriggeredGeoFence("Exited");
                    }

                    @Override
                    public void onKeyMoved(String key, GeoLocation location) {

                    }

                    @Override
                    public void onGeoQueryReady() {

                    }

                    @Override
                    public void onGeoQueryError(DatabaseError error) {

                    }
                });
            }
        });
    }

    private void saveGeoFire() {
        DriversRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    if (dataSnapshot.hasChild("RedLine")) {
                        String userIdRef = dataSnapshot.child("RedLine").getValue().toString();

                        if (!userIdRef.isEmpty() && userIdRef.contains(userId)) {
                            geoFire = new GeoFire(RedRef);
                            geoFire.setLocation(userId, new GeoLocation(DriverLocationService.mLastLocation.getLatitude(), DriverLocationService.mLastLocation.getLongitude()));
                            redBusReference.setValue(enteredFence);
                            GeoFence();
                        }
                    }
                    if (dataSnapshot.hasChild("BlueLine")) {
                        String userIdRef = dataSnapshot.child("BlueLine").getValue().toString();

                        if (!userIdRef.isEmpty() && userIdRef.contains(userId)) {
                            geoFire = new GeoFire(BlueRef);
                            geoFire.setLocation(userId, new GeoLocation(DriverLocationService.mLastLocation.getLatitude(), DriverLocationService.mLastLocation.getLongitude()));
                            blueBusReference.setValue(enteredFence);
                            GeoFence();
                        }
                    }
                    if (dataSnapshot.hasChild("GreenLine")) {
                        String userIdRef = dataSnapshot.child("GreenLine").getValue().toString();

                        if (!userIdRef.isEmpty() && userIdRef.contains(userId)) {
                            geoFire = new GeoFire(GreenRef);
                            geoFire.setLocation(userId, new GeoLocation(DriverLocationService.mLastLocation.getLatitude(), DriverLocationService.mLastLocation.getLongitude()));
                            GeoFence();
                        }
                    }
                    if (dataSnapshot.hasChild("YellowLine")) {
                        String userIdRef = dataSnapshot.child("YellowLine").getValue().toString();

                        if (!userIdRef.isEmpty() && userIdRef.contains(userId)) {
                            geoFire = new GeoFire(YellowRef);
                            geoFire.setLocation(userId, new GeoLocation(DriverLocationService.mLastLocation.getLatitude(), DriverLocationService.mLastLocation.getLongitude()));
                            GeoFence();
                        }
                    }
                    if (dataSnapshot.hasChild("PurpleLine")) {
                        String userIdRef = dataSnapshot.child("PurpleLine").getValue().toString();

                        if (!userIdRef.isEmpty() && userIdRef.contains(userId)) {
                            geoFire = new GeoFire(PurpleRef);
                            geoFire.setLocation(userId, new GeoLocation(DriverLocationService.mLastLocation.getLatitude(), DriverLocationService.mLastLocation.getLongitude()));
                            GeoFence();
                        }
                    }
                    if (dataSnapshot.hasChild("OrangeLine")) {
                        String userIdRef = dataSnapshot.child("OrangeLine").getValue().toString();

                        if (!userIdRef.isEmpty() && userIdRef.contains(userId)) {
                            geoFire = new GeoFire(OrangeRef);
                            geoFire.setLocation(userId, new GeoLocation(DriverLocationService.mLastLocation.getLatitude(), DriverLocationService.mLastLocation.getLongitude()));
                            GeoFence();
                        }
                    }
                    if (dataSnapshot.hasChild("PinkLine")) {
                        String userIdRef = dataSnapshot.child("PinkLine").getValue().toString();

                        if (!userIdRef.isEmpty() && userIdRef.contains(userId)) {
                            geoFire = new GeoFire(PinkRef);
                            geoFire.setLocation(userId, new GeoLocation(DriverLocationService.mLastLocation.getLatitude(), DriverLocationService.mLastLocation.getLongitude()));
                            GeoFence();
                        }
                    }
                    if (dataSnapshot.hasChild("GreyLine")) {
                        String userIdRef = dataSnapshot.child("GreyLine").getValue().toString();

                        if (!userIdRef.isEmpty() && userIdRef.contains(userId)) {
                            geoFire = new GeoFire(GreyRef);
                            geoFire.setLocation(userId, new GeoLocation(DriverLocationService.mLastLocation.getLatitude(), DriverLocationService.mLastLocation.getLongitude()));
                            GeoFence();
                        }
                    }
                    if (dataSnapshot.hasChild("Tram Franschhoek")) {
                        String userIdRef = dataSnapshot.child("Tram Franschhoek").getValue().toString();

                        if (!userIdRef.isEmpty() && userIdRef.contains(userId)) {
                            geoFire = new GeoFire(tramFranchhoekRef);
                            geoFire.setLocation(userId, new GeoLocation(DriverLocationService.mLastLocation.getLatitude(), DriverLocationService.mLastLocation.getLongitude()));
                            GeoFence();
                        }
                    }
                    if (dataSnapshot.hasChild("Tram Drakenstein")) {
                        String userIdRef = dataSnapshot.child("Tram Drakenstein").getValue().toString();

                        if (!userIdRef.isEmpty() && userIdRef.contains(userId)) {
                            geoFire = new GeoFire(tramDrakensteinRef);
                            geoFire.setLocation(userId, new GeoLocation(DriverLocationService.mLastLocation.getLatitude(), DriverLocationService.mLastLocation.getLongitude()));
                            GeoFence();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    void TriggeredGeoFence(String flag)
    {
        String title = flag;
        String message = "You have entered an estate";
        Notification notification = new NotificationCompat.Builder(DriverLocationService.this, CHANNEL_2_ID)
                .setSmallIcon(R.drawable.bus)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notificationManager.notify(1,notification);
    }
    @Override
    public void onCreate() {
        super.onCreate();

        fusedLocationProviderClient = new FusedLocationProviderClient(DriverLocationService.this);
        locationRequest = new LocationRequest();

        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(1000);

        fusedLocationProviderClient.requestLocationUpdates(locationRequest,mLocationRequestCallback, Looper.myLooper());
        notificationManager = NotificationManagerCompat.from(this);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String input = intent.getStringExtra("inputExtra");
        Intent notificationIntent = new Intent(this, LoginScreen.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setContentTitle("Wine Tram Notification Service")
                .setContentText(input)
                .setSmallIcon(R.drawable.bus)
                .setContentIntent(pendingIntent)
                .build();

        startForeground(1, notification);
        //do heavy work on a background thread
        //stopSelf();

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            fusedLocationProviderClient.removeLocationUpdates(mLocationRequestCallback);
        }catch (Exception e){
         Log.e(TAG,"FAILED TO REMOVE LOCATION: " + "\n"+e);
        }
    }


}
