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

public class PinkLineScreen extends AppCompatActivity {

    public static final String SHARED_PREFS = "sharedPrefs";
    private boolean switchOnOff;
    String TAG = PinkLineScreen.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_screen_activity);


        getSupportActionBar().setTitle("Time Table");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ArrayList<RecyclerItem> mList = new ArrayList<>();

        if(SelectRoute.selectedRoute == 7) {
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.pink)));

            mList.add(new RecyclerItem(R.drawable.bus, "Ticket Office", "10:07 | 11:07 | 12:07 | 13:07 | 14:07 | 15:07 | 16:07"));
            mList.add(new RecyclerItem(R.drawable.tram, "Tram Platform", "10:15 | 11:15 | 12:15 | 13:15 | 14:15 | 15:15 16:15"));
            mList.add(new RecyclerItem(R.drawable.bus, "Leopards Leap", "10:22 | 11:22 | 12:22 | 13:22 | 14:22 | 15:22 | 16:22"));
            mList.add(new RecyclerItem(R.drawable.bus, "Moreson", "10:35 | 11:35 | 12:35 | 13:35 | 14:35 | 15:35 | 16:35"));
            mList.add(new RecyclerItem(R.drawable.bus, "Eikehof", "10:46| 11:46 | 12:46 | 13:46 | 14:46 | 15:46 | 16:46"));
            mList.add(new RecyclerItem(R.drawable.bus, "Paserene", "-- | -- | 13:03 | 14:03 | 15:03 | 16:03 | 17:03"));
            mList.add(new RecyclerItem(R.drawable.bus, "Franschhoek Village", "-- | -- | 13:11 | 14:11 | 15:11 | 16:11 | 17:11"));
            mList.add(new RecyclerItem(R.drawable.bus, "Haute Cabriere", "-- | -- | 13:11 | 14:11 | 15:11 | 16:11 | 17:11"));
            mList.add(new RecyclerItem(R.drawable.bus, "Le Lude", "-- | -- | 13:11 | 14:11 | 15:11 | 16:11 | 17:11"));
            mList.add(new RecyclerItem(R.drawable.bus, "Franschhoek Celler", "-- | -- | 13:11 | 14:11 | 15:11 | 16:11 | 17:11"));

        }
        RecyclerView mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        RecyclerView.Adapter mAdapter = new RecyclerAdapter(mList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        Switch notificationSwitch = (Switch) findViewById(R.id.notification_switch);
        loadData("pink");
        Log.i(TAG,"Loaded pink");
        updateView(notificationSwitch);
        notificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                saveData("pink",b);
                Log.i(TAG,"saved pink");
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
