package com.example.winetramapp;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.winetramapp.DriverSystem.DriverLocationService;
import com.example.winetramapp.DriverSystem.DriverPasswordScreen;
import com.example.winetramapp.UserSystem.SelectRoute;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;

import java.util.ArrayList;

public class LoginScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        runtime_permission();


    }
    public void runtime_permission()
    {
        String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
        Permissions.check(this/*context*/, permissions, "Location permissions are required to get your location"/*rationale*/, null/*options*/, new PermissionHandler() {
            @Override
            public void onGranted() {

            }

            @Override
            public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                super.onDenied(context, deniedPermissions);
            }
        });

    }
   public void guestBtn(View view)
   {
       Intent intent = new Intent(this, SelectRoute.class);
       startActivity(intent);
   }

    public void driverBtn(View view) {
        Intent intent = new Intent(this, DriverPasswordScreen.class);
        startActivity(intent);
    }
}
