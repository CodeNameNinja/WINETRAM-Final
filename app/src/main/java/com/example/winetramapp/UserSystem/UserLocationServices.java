package com.example.winetramapp.UserSystem;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.winetramapp.DriverSystem.DriverLocationService;
import com.example.winetramapp.LoginScreen;
import com.example.winetramapp.R;
import com.firebase.geofire.GeoFire;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import static com.example.winetramapp.NotificationChannel.CHANNEL_1_ID;
import static com.example.winetramapp.NotificationChannel.CHANNEL_2_ID;

public class UserLocationServices extends Service  {
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationRequest locationRequest;
    public static Location mLastLocation;
    private String TAG = DriverLocationService.class.getSimpleName();
    DatabaseReference geoRef;
    GeoFire geoFire;
    private NotificationManagerCompat notificationManager;
    String enteredFence;

    @Override
    public void onCreate() {
        super.onCreate();
        fusedLocationProviderClient = new FusedLocationProviderClient(UserLocationServices.this);
        locationRequest = new LocationRequest();

        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(1000);

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
//                saveGeoFire();

            }
        }
    };
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
