package com.example.winetramapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.winetramapp.DriverSystem.DriverPasswordScreen;
import com.example.winetramapp.UserSystem.SelectRoute;

public class LoginScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



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
