package com.example.winetramapp.UserSystem;

import android.app.Notification;
import android.os.Handler;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.winetramapp.Constants;
import com.example.winetramapp.DriverSystem.DriverLocationService;
import com.example.winetramapp.R;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;
import java.util.UUID;

import static com.example.winetramapp.NotificationChannel.CHANNEL_2_ID;

public class Common {
    String TAG = Common.class.getSimpleName();
    StringBuilder builder = new StringBuilder();
    GeoFire geoFire;
    String enteredFence;
    public static String uniqueID = UUID.randomUUID().toString();
    public String mHoursMinutes(String Minutes,String...Hours)
    {
        for(String myTimes: Hours)
        {
            builder.append(myTimes+Minutes);
        }
        String text;
        return  text = builder.toString();
    }

    public void GeoFence()
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
                        enteredFence = entry.getKey();

//                        TriggeredGeoFence("You have entered into: " +entry.getKey());
//                        enteredFence = entry.getKey();
                    }

                    @Override
                    public void onKeyExited(String key) {
                        Log.i(TAG,"Exited");
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

public void removeUniqueId()
{
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("usersAvailable").child("Users");


    GeoFire geoFire = new GeoFire(ref);
    geoFire.removeLocation(uniqueID, new GeoFire.CompletionListener() {
        @Override
        public void onComplete(String key, DatabaseError error) {
            Log.d(TAG, "Removed Location Successfully: "+uniqueID);
            Log.e(TAG,key+"\n"+error);
        }
    });
}
}
