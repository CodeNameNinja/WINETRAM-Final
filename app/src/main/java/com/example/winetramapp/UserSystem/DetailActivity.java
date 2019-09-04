package com.example.winetramapp.UserSystem;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.winetramapp.R;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            setContentView(R.layout.activity_detail);
        }catch(Exception e)
        {
            Intent intent = new Intent(this,SelectRoute.class);
            startActivity(intent);
        }


        TextView textView = findViewById(R.id.recyclerTextView);
        textView.setText(getIntent().getStringExtra("param"));
    }
}