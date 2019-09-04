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

public class YellowLineScreen extends AppCompatActivity {
    public static final String SHARED_PREFS = "sharedPrefs";
    private boolean switchOnOff;
    String TAG = YellowLineScreen.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_screen_activity);
        getSupportActionBar().setTitle("Time Table");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ArrayList<RecyclerItem> mList = new ArrayList<>();

        if(SelectRoute.selectedRoute == 3) {
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.yellow)));
            mList.add(new RecyclerItem(R.drawable.tram, "Ticket Office", "10:45 | 11:45 | 12:45"));
            mList.add(new RecyclerItem(R.drawable.tram, "Grande Provance", "11:03 | 12:03 | 13:03 | 14:03 | 15:03 | 16:03"));
            mList.add(new RecyclerItem(R.drawable.tram, "Rickety Bridge", "11:11 | 12:11 | 13:11 | 14:11 | 15:11 | 16:11"));
            mList.add(new RecyclerItem(R.drawable.bus, "Franschhoek Village", "-- | 12:30 | 13:30 | 14:30 | 15:30 | 16:30"));
            mList.add(new RecyclerItem(R.drawable.bus, "Maison", "-- | 12:39 | 13:39 | 14:39 | 15:39 | 16:39"));
            mList.add(new RecyclerItem(R.drawable.bus, "Mont Rochelle", "-- | 12:51 | 13:51 | 14:51 | 15:51 | 16:51 | 17:51"));
            mList.add(new RecyclerItem(R.drawable.bus, "Holden Manz", "-- | 13:02 | 14:02 | 15:02 | 16:02 | 17:02"));
            mList.add(new RecyclerItem(R.drawable.bus, "Charmonix", "-- | -- | 13:14 | 14:14 | 15:14 | 16:14 | 17:14"));
            mList.add(new RecyclerItem(R.drawable.bus, "Dieu Donne", "-- | -- | 13:17 | 14:17 | 15:17 | 16:17 | 17:17"));

        }
        RecyclerView mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        RecyclerView.Adapter mAdapter = new RecyclerAdapter(mList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        Switch notificationSwitch = (Switch) findViewById(R.id.notification_switch);
        loadData("yellow");
        Log.i(TAG,"Loaded yellow");
        updateView(notificationSwitch);
        notificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                saveData("yellow",b);
                Log.i(TAG,"saved yellow");
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
