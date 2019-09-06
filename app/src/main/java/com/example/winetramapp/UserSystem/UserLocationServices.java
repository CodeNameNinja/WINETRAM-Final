package com.example.winetramapp.UserSystem;

import android.app.Notification;
import android.app.NotificationManager;
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
import com.example.winetramapp.DriverSystem.DriverLocationService;
import com.example.winetramapp.LoginScreen;
import com.example.winetramapp.R;
import com.example.winetramapp.UserSystem.Routes.RedLineScreen;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;
import java.util.UUID;

import static com.example.winetramapp.NotificationChannel.CHANNEL_1_ID;
import static com.example.winetramapp.NotificationChannel.CHANNEL_2_ID;
import static com.example.winetramapp.NotificationChannel.CHANNEL_3_ID;

public class UserLocationServices extends Service  {
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationRequest locationRequest;
    public static Location mLastLocation;
    private String TAG = DriverLocationService.class.getSimpleName();
    DatabaseReference geoRef;
    GeoFire geoFire;
    private NotificationManagerCompat notificationManager;
    String enteredFence;
    final DatabaseReference UserRef = FirebaseDatabase.getInstance().getReference().child("usersAvailable").child("Users");
    Common common = new Common();
    static String uniqueID = UUID.randomUUID().toString();

    @Override
    public void onCreate() {
        super.onCreate();
        fusedLocationProviderClient = new FusedLocationProviderClient(UserLocationServices.this);
        locationRequest = new LocationRequest();

        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(3000);

        fusedLocationProviderClient.requestLocationUpdates(locationRequest,mLocationRequestCallback, Looper.myLooper());
        notificationManager = NotificationManagerCompat.from(this);


    }

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
                saveGeoFence();
                checkNotify();
            }
        }
    };
    private void GeoFence()
    {
        Handler handler = new Handler();
        handler.post(() -> {
            for (Map.Entry<String, Map.Entry<LatLng,Integer>> entry : Constants.MAIN_GEOFENCES.entrySet()) {
                double entryRadius = entry.getValue().getValue();
                entryRadius = entryRadius/1000;
                Log.i(TAG, "Geofence:  "+entry.getKey()+"Radius: " + entryRadius);
                GeoQuery geoQuery = geoFire.queryAtLocation(new GeoLocation(entry.getValue().getKey().latitude,entry.getValue().getKey().longitude),entryRadius);
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
    private void saveGeoFence()
    {
        geoFire = new GeoFire(UserRef);
        geoFire.setLocation(uniqueID, new GeoLocation(mLastLocation.getLatitude(), mLastLocation.getLongitude()), new GeoFire.CompletionListener() {
            @Override
            public void onComplete(String key, DatabaseError error) {
                Log.d(TAG,"Succesfully added "+uniqueID);
                DatabaseReference currentEstate = UserRef.child(uniqueID).child("Current Estate:");
                currentEstate.setValue(enteredFence);
                GeoFence();
            }
        });
    }
    private void checkNotify()
    {
        DatabaseReference checkNotify = FirebaseDatabase.getInstance().getReference().child("notify");
        checkNotify.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    if(dataSnapshot.child("value:").getValue().equals("Bus"))
                    {
                      busNotificationsBody("Get Ready the Bus is on its way");
                        Handler handler1 = new Handler();
                        handler1.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                checkNotify.child("value:").setValue(false);
                                notificationManager.cancel(6);
                            }
                        },1000*60);
                    }
                    if(dataSnapshot.child("value:").getValue().equals("Tram"))
                    {

                    tramNotificationsBody("Get Ready Tram is on its way");
                        Handler handler1 = new Handler();
                        handler1.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                checkNotify.child("value:").setValue(false);
                                notificationManager.cancel(3);
                            }
                        },1000*60);
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
        Notification notification = new NotificationCompat.Builder(UserLocationServices.this, CHANNEL_2_ID)
                .setSmallIcon(R.drawable.bus)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notificationManager.notify(1,notification);
    }
    void busNotificationsBody(String BusLocation)
    {
        String title = BusLocation;
        String message = "";
        Notification notification = new NotificationCompat.Builder(UserLocationServices.this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.bus)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notificationManager.notify(6,notification);
    }
    void tramNotificationsBody(String TramLocation)
    {
        String title = TramLocation;
        String message = "";
        Notification notification = new NotificationCompat.Builder(UserLocationServices.this, CHANNEL_3_ID)
                .setSmallIcon(R.drawable.bus)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notificationManager.notify(3,notification);
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

        common.removeUniqueId();
        try {
            fusedLocationProviderClient.removeLocationUpdates(mLocationRequestCallback);
        }catch (Exception e){
            Log.e(TAG,"FAILED TO REMOVE LOCATION: " + "\n"+e);
        }
    }
}
