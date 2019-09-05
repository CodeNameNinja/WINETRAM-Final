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

public class OrangeLineScreen extends AppCompatActivity {
    public static final String SHARED_PREFS = "sharedPrefs";
    private boolean switchOnOff;
    String TAG = OrangeLineScreen.class.getSimpleName();
    Switch notificationSwitch;
    final DatabaseReference Ref = FirebaseDatabase.getInstance().getReference().child("driversAvailable").child("drivers");
    final DatabaseReference tramDrakensteinDatabaseReferenceTarget = FirebaseDatabase.getInstance().getReference().child("driversAvailable").child("drivers").child("Tram Drakenstein").child("Tram Drakenstein Location");
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

        if(SelectRoute.selectedRoute == 5) {
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.orange)));
            mList.add(new RecyclerItem(R.drawable.bus, "Ticket Office", "9:30 | 10:05 | 10:40 | 11:15 | 11:50"));
            mList.add(new RecyclerItem(R.drawable.bus, "Babylonstoren", "9:54 | 10:29 | 11:04 | 11:39 | 12:14 | 12:49 | 13:24 | 13:59 | 12:34 | 15:09 | 15:44 | 16:19"));
            mList.add(new RecyclerItem(R.drawable.bus, "Backsberg", "9:54 | 10:29 | 11:04 | 11:39 | 12:14 | 12:49 | 13:24 | 13:59 | 12:34 | 15:09 | 15:44 | 16:19"));
            mList.add(new RecyclerItem(R.drawable.bus, "Boschendal", "9:54 | 10:29 | 11:04 | 11:39 | 12:14 | 12:49 | 13:24 | 13:59 | 12:34 | 15:09 | 15:44 | 16:19"));
            mList.add(new RecyclerItem(R.drawable.tram, "Vrede en Lust", "9:54 | 10:29 | 11:04 | 11:39 | 12:14 | 12:49 | 13:24 | 13:59 | 12:34 | 15:09 | 15:44 | 16:19"));
            mList.add(new RecyclerItem(R.drawable.tram, "Plasir de Merle", "9:54 | 10:29 | 11:04 | 11:39 | 12:14 | 12:49 | 13:24 | 13:59 | 12:34 | 15:09 | 15:44 | 16:19"));
            mList.add(new RecyclerItem(R.drawable.bus, "Allee Blue", "10:35 | 11:35 | 12:35 | 13:35 | 14:35 | 15:35 | 16:35"));
            mList.add(new RecyclerItem(R.drawable.bus, "Solms Delta", "9:54 | 10:29 | 11:04 | 11:39 | 12:14 | 12:49 | 13:24 | 13:59 | 12:34 | 15:09 | 15:44 | 16:19"));
            mList.add(new RecyclerItem(R.drawable.bus, "Boschendal", "9:54 | 10:29 | 11:04 | 11:39 | 12:14 | 12:49 | 13:24 | 13:59 | 12:34 | 15:09 | 15:44 | 16:19"));

        }
        RecyclerView mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        RecyclerView.Adapter mAdapter = new RecyclerAdapter(mList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        notificationSwitch = (Switch) findViewById(R.id.notification_switch);
        loadData("orange");
        Log.i(TAG,"Loaded orange");
        updateView(notificationSwitch);
        notificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                saveData("orange",b);
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
        DatabaseReference mReference = Ref.child("OrangeLine").child("bus");
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
        tramDrakensteinDatabaseReferenceTarget.addValueEventListener(new ValueEventListener() {
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
        Notification notification = new NotificationCompat.Builder(OrangeLineScreen.this, CHANNEL_2_ID)
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

        Notification notification = new NotificationCompat.Builder(OrangeLineScreen.this, CHANNEL_2_ID)
                .setSmallIcon(R.drawable.bus)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();
        notificationManager.notify(2,notification);
    }
}
