package com.example.winetramapp.UserSystem.Routes;

import android.app.Notification;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.winetramapp.R;
import com.example.winetramapp.UserSystem.RecyclerAdapter;
import com.example.winetramapp.UserSystem.RecyclerItem;
import com.example.winetramapp.UserSystem.SelectRoute;
import com.example.winetramapp.UserSystem.UserLocationServices;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.winetramapp.NotificationChannel.CHANNEL_2_ID;

public class GreyLineScreen extends AppCompatActivity {

    public static final String SHARED_PREFS = "sharedPrefs";
    private boolean switchOnOff;
    String TAG = GreyLineScreen.class.getSimpleName();
    Switch notificationSwitch;

    final DatabaseReference Ref = FirebaseDatabase.getInstance().getReference().child("driversAvailable").child("drivers");
    final DatabaseReference tramFranschhoekDatabaseReference = FirebaseDatabase.getInstance().getReference().child("driversAvailable").child("drivers").child("Tram Franschhoek").child("Tram Franschhoek Location");
    private NotificationManagerCompat notificationManager;

    @Override
    protected void onResume() {
        super.onResume();
        if(notificationSwitch.isChecked())
        {
            Intent i = new Intent(getApplicationContext(), UserLocationServices.class);
            startService(i);
        }
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_screen_activity);

        getSupportActionBar().setTitle("Time Table");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ArrayList<RecyclerItem> mList = new ArrayList<>();
        if(SelectRoute.selectedRoute == 6) {
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.grey)));
            mList.add(new RecyclerItem(R.drawable.bus, "Ticket Office", "10:00 | 10:30 | 11:00 | 11:30 | 12:00"));
            mList.add(new RecyclerItem(R.drawable.bus, "Haute Cabriere", "10:06 | 10:36 | 11:06 | 11:36 | 12:06 | 12:36 | 13:06 | 13:36 | 14:06 | 14:36 | 15:06 | 15:36 | 16:06 | 16:36"));
            mList.add(new RecyclerItem(R.drawable.bus, "Le Lude", "10:11 | 10:41 | 11:11 | 11:41 | 12:11 | 12:41 | 13:11 | 13:41 | 14:11 | 14:41 | 15:11 | 15:41 | 16:11 | 16:41"));
            mList.add(new RecyclerItem(R.drawable.bus, "Franschhoek Celler", "10:18 | 10:48 | 11:18 | 11:48 | 12:18 | 12:48 | 13:18 | 13:48 | 14:18 | 14:48 | 15:18 | 15:48 | 16:18 | 16:48"));
            mList.add(new RecyclerItem(R.drawable.tram,"Tram Platform", "--- | --- | 11:23 | 11:53 | 12:23 | 12:53 | 13:23 | 13:53 | 14:23 | 14:54 | 15:23 | 15:54| 16:23 | 16:53"));
            mList.add(new RecyclerItem(R.drawable.bus, "Leapard's Leap", "--- | --- | 11:42 | 14:03 | 15:03 | 16:03 | 17:03"));
            mList.add(new RecyclerItem(R.drawable.bus, "Moreson", "--- | --- | 13:11 | 14:11 | 15:11 | 16:11 | 17:11"));
            mList.add(new RecyclerItem(R.drawable.bus, "Eikehof", "--- | --- | 13:11 | 14:11 | 15:11 | 16:11 | 17:11"));
            mList.add(new RecyclerItem(R.drawable.bus, "Paserene", "--- | --- | 13:11 | 14:11 | 15:11 | 16:11 | 17:11"));

        }
        RecyclerView mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        RecyclerView.Adapter mAdapter = new RecyclerAdapter(mList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        notificationSwitch = (Switch) findViewById(R.id.notification_switch);
        loadData("grey");
        Log.i(TAG,"Loaded grey");
        updateView(notificationSwitch);
        notificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                saveData("grey",b);
                if(b)
                {

                    Intent i = new Intent(getApplicationContext(), UserLocationServices.class);
                    startService(i);
                    conditions();
                }
                else{
                    Intent i = new Intent(getApplicationContext(),UserLocationServices.class);
                    stopService(i);
                }
            }
        });
    }

    private void saveData(String key , boolean value) {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key,value);
        editor.apply();
    }
    public void loadData(String key)
    {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        switchOnOff = sharedPreferences.getBoolean(key,false);
    }
    public void updateView(Switch selectedLineSwitch)
    {
        try{
            selectedLineSwitch.setChecked(switchOnOff);
        }catch (Exception e)
        {
            Log.e("Error", "did not update View");
        }
    }
    void conditions()
    {
        DatabaseReference mReference = Ref.child("GreyLine").child("bus");
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try{
                    String location = dataSnapshot.getValue().toString();
                    busNotificationsBody("The bus is at: " + location);
                }catch (Exception e)
                {
                    Log.e(TAG,"Could not retrieve bus location");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        tramFranschhoekDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try{
                    String location = dataSnapshot.getValue().toString();
                    tramNotificationsBody("The tram is currently at: " + location);
                }catch (Exception e)
                {
                    Log.e(TAG,"Could not Get Tram Location" + e);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    void busNotificationsBody(String BusLocation)
    {
        String title = BusLocation;
        String message = "";
        Notification notification = new NotificationCompat.Builder(GreyLineScreen.this, CHANNEL_2_ID)
                .setSmallIcon(R.drawable.bus)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notificationManager.notify(1,notification);
    }
    void tramNotificationsBody(String TramLocation )
    {
        String title = TramLocation;
        String message = "";

        Notification notification = new NotificationCompat.Builder(GreyLineScreen.this, CHANNEL_2_ID)
                .setSmallIcon(R.drawable.bus)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();
        notificationManager.notify(2,notification);
    }
}
