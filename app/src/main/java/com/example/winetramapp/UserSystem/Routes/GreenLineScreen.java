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

public class GreenLineScreen extends AppCompatActivity {

    public static final String SHARED_PREFS = "sharedPrefs";
    private boolean switchOnOff;
    String TAG = GreenLineScreen.class.getSimpleName();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_screen_activity);

        getSupportActionBar().setTitle("Time Table");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ArrayList<RecyclerItem> mList = new ArrayList<>();

        if(SelectRoute.selectedRoute == 2) {
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.green)));
            mList.add(new RecyclerItem(R.drawable.tram, "Ticket Office", "10:15 | 11:15 | 12:15"));
            mList.add(new RecyclerItem(R.drawable.tram, "Grande Provance", "10:33 | 11:33 | 12:33 | 13:33 | 14:33 | 15:33 | 16:33"));
            mList.add(new RecyclerItem(R.drawable.tram, "Rickety Bridge", "10:41 | 11:41 | 12:41 | 13:41 | 14:41 | 15:41 16:41"));
            mList.add(new RecyclerItem(R.drawable.bus, "Franschhoek Village", "-- | 12:00 | 13:00 | 14:00 | 15:00 | 16:00 | 17:00"));
            mList.add(new RecyclerItem(R.drawable.bus, "La Bri", "-- | 12:07 | 13:07 | 14:07 | 15:07 | 16:07 | 17:07"));
            mList.add(new RecyclerItem(R.drawable.bus, "Holden Manz", "-- | 12:15 | 13:15 | 14:15 | 15:15 | 16:15 | 17:15"));
            mList.add(new RecyclerItem(R.drawable.bus, "La Bourgogne", "-- | 12:22 | 13:22 | 14:22 | 15:22 | 16:22 | 17:22"));
            mList.add(new RecyclerItem(R.drawable.bus, "Glenwood", "-- | -- | 12:35 | 13:35 | 14:35 | 15:35 | 16:35 | 17:35"));
            mList.add(new RecyclerItem(R.drawable.bus, "La Couronne", "-- | -- | 12:46 | 13:46 | 14:46 | 15:46 | 16:46 | 17:46"));

        }
        RecyclerView mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        RecyclerView.Adapter mAdapter = new RecyclerAdapter(mList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        Switch notificationSwitch = (Switch) findViewById(R.id.notification_switch);
        loadData("green");
        Log.i(TAG,"Loaded green");
        updateView(notificationSwitch);
        notificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                saveData("green",b);
                Log.i(TAG,"saved green");
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
