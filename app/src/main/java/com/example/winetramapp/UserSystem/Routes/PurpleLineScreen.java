package com.example.winetramapp.UserSystem.Routes;

import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.winetramapp.R;
import com.example.winetramapp.UserSystem.RecyclerAdapter;
import com.example.winetramapp.UserSystem.RecyclerItem;
import com.example.winetramapp.UserSystem.SelectRoute;

import java.util.ArrayList;

public class PurpleLineScreen extends AppCompatActivity {
    public static final String SHARED_PREFS = "sharedPrefs";
    private boolean switchOnOff;
    String TAG = PurpleLineScreen.class.getSimpleName();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_screen_activity);

        getSupportActionBar().setTitle("Time Table");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ArrayList<RecyclerItem> mList = new ArrayList<>();
        if(SelectRoute.selectedRoute == 4) {
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.purple)));
            mList.add(new RecyclerItem(R.drawable.bus, "Ticket Office", "9:30 | 10:05 | 10:40 | 11:15 | 11:50"));
            mList.add(new RecyclerItem(R.drawable.bus, "Allee Bleue", "9:54 | 10:29 | 11:04 | 11:39 | 12:14 | 12:49 | 13:24 | 13:59 | 14:34 | 15:09 | 15:44 | 16:19"));
            mList.add(new RecyclerItem(R.drawable.bus, "Solms Delta", "10:00| 10:35 | 11:10 | 11:45 | 12:20 | 12:55 | 13:30 | 14:05 | 14:40 | 15:15 | 15:50 | 16:25"));
            mList.add(new RecyclerItem(R.drawable.bus, "Boschendal", "10:12 | 10:47 | 11:22 | 11:57 | 12:32 | 13:07 | 13:42 | 14:17 | 14:52 | 15:27 | 16:02 | 16:37"));
            mList.add(new RecyclerItem(R.drawable.tram, "Plasir De Merle", "10:31 | 11:06 | 11:41 | 12:16 | 12:51 | 13:26 | 14:01 | 14:36 | 15:11 | 15:46 | 16:21 | 16:56"));
            mList.add(new RecyclerItem(R.drawable.tram, "Vrede en Lust", "10:35 | 11:10 | 11:45 | 12:20 | 12:55 | 13:30 | 14:05 | 14:40 | 15:50 | 16:25 | 17:00"));
            mList.add(new RecyclerItem(R.drawable.bus, "Nobile Hill", "--- | --- | --- | 12:31 | 13:06 | 13:41 | 14:16 | 14:51 | 15:26 | 16:01 | 16:36 | 17:11"));
            mList.add(new RecyclerItem(R.drawable.bus, "Babylonstoren", "--- | --- | --- | 12:39 | 13:14 | 13:49 | 14:24 | 14:59 | 15:34 | 16:09 | 16:44 | 17:19"));
            mList.add(new RecyclerItem(R.drawable.bus, "Backsberg", "--- | --- | --- | 12:46 | 13:21 | 13:56 | 14:31 | 15:06 | 15:34 | 16:16 | 16:51 | 17:26"));

        }
        RecyclerView mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        RecyclerView.Adapter mAdapter = new RecyclerAdapter(mList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        Switch blueNotificationSwitch = (Switch) findViewById(R.id.notification_switch);
        loadData("purple");
        Log.i(TAG,"Loaded purple");
        updateView(blueNotificationSwitch);
        blueNotificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                saveData("purple",b);
                Log.i(TAG,"saved purple");
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
}
