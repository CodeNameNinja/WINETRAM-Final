package com.example.winetramapp.UserSystem.Routes;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.winetramapp.DriverSystem.DriverPasswordScreen;
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

public class RedLineScreen extends AppCompatActivity {


    public static final String SHARED_PREFS = "sharedPrefs";
    private boolean switchOnOff;
    public Switch notificationSwitch;
    final DatabaseReference Ref = FirebaseDatabase.getInstance().getReference().child("driversAvailable").child("drivers");
    final DatabaseReference tramFranschhoekDatabaseReference = FirebaseDatabase.getInstance().getReference().child("driversAvailable").child("drivers").child("Tram Franschhoek").child("Tram Franschhoek Location");
    private NotificationManagerCompat notificationManager;
    String TAG = RedLineScreen.class.getSimpleName();


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
//        installListener();
        ArrayList<RecyclerItem> mList = new ArrayList<>();
        if(SelectRoute.selectedRoute == 0) {
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.red)));
                mList.add(new RecyclerItem(R.drawable.bus, "Ticket Office", "10:30 | 11:30"));
                mList.add(new RecyclerItem(R.drawable.bus, "Maison", "10:39 | 11:39 | 12:39 | 13:39 | 14:39 | 15:39 16:39"));
                mList.add(new RecyclerItem(R.drawable.bus, "Mont Rochelle", "10:51 | 11:51 | 12:51 | 13:51 | 14:51 | 15:51 | 16:51"));
                mList.add(new RecyclerItem(R.drawable.bus, "Holden Manz", "11:02 | 12:02 | 13:02 | 14:02 | 15:02 | 16:02 | 17:02"));
                mList.add(new RecyclerItem(R.drawable.bus, "Charmonix", "11:14 | 12:14 | 13:14 | 14:14 | 15:14 | 16:14 | 17:14"));
                mList.add(new RecyclerItem(R.drawable.bus, "Dieu Donne", "11:17 | 12:17 | 13:17 | 14:17 | 15:17 | 16:17 | 17:17"));
                mList.add(new RecyclerItem(R.drawable.tram, "Grande Provance Platform", "-- | -- | 13:33 | 14:33 | 15:33 | 16:33 | 17:33"));
                mList.add(new RecyclerItem(R.drawable.tram, "Rickety Bridge", "-- | -- | 13:41 | 14:41 | 15:41 | 16:41 | 17:41"));
        }
        RecyclerView mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        RecyclerView.Adapter mAdapter = new RecyclerAdapter(mList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        notificationManager = NotificationManagerCompat.from(this);
        notificationSwitch = (Switch) findViewById(R.id.notification_switch);
        loadData("red");
        Log.i(TAG,"Loaded red");
        updateView(notificationSwitch);
        notificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                saveData("red",b);
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
        editor.clear();
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
        DatabaseReference mReference = Ref.child("RedLine").child("bus");
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
                    Log.e(TAG,"Couldent Get Tram Location" + e);
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
        Notification notification = new NotificationCompat.Builder(RedLineScreen.this, CHANNEL_2_ID)
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

        Notification notification = new NotificationCompat.Builder(RedLineScreen.this, CHANNEL_2_ID)
                .setSmallIcon(R.drawable.bus)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notificationManager.notify(2,notification);
    }

    BroadcastReceiver broadcastReceiver;
    private void installListener() {

        if (broadcastReceiver == null) {

            broadcastReceiver = new BroadcastReceiver() {

                @Override
                public void onReceive(Context context, Intent intent) {

                    Bundle extras = intent.getExtras();

                    NetworkInfo info = (NetworkInfo) extras
                            .getParcelable("networkInfo");

                    NetworkInfo.State state = info.getState();
                    Log.d(TAG, info.toString() + " "
                            + state.toString());

                    if (state == NetworkInfo.State.CONNECTED) {

                        onNetworkUp();

                    } else {

                        onNetworkDown();

                    }

                }
            };

            final IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
            registerReceiver(broadcastReceiver, intentFilter);
        }
    }

    private void onNetworkDown() {
        AlertDialog alertDialog = new AlertDialog.Builder(RedLineScreen.this).create();
        alertDialog.setTitle("Network is Down");
        alertDialog.setMessage("The Network is not Working");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    private void onNetworkUp() {
        AlertDialog alertDialog = new AlertDialog.Builder(RedLineScreen.this).create();
        alertDialog.setTitle("Network is Up");
        alertDialog.setMessage("The Network is working");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
