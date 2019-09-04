package com.example.winetramapp.DriverSystem;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.winetramapp.R;

public class DriverPasswordScreen extends AppCompatActivity implements View.OnClickListener {
    int counter;
    String masterCode = "5555";
    int iCode1,iCode2,iCode3,iCode4;
    int buttonClicked;
    String TAG = DriverPasswordScreen.class.getSimpleName();
    ImageView code1,code2,code3,code4;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_password_layout);

        getSupportActionBar().setTitle("Login");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button buttonZero = (Button)findViewById(R.id.btnZero);
        Button buttonOne = (Button)findViewById(R.id.btnOne);
        Button buttonTwo = (Button)findViewById(R.id.btnTwo);
        Button buttonThree = (Button)findViewById(R.id.btnThree);
        Button buttonFour = (Button)findViewById(R.id.btnFour);
        Button buttonFive = (Button)findViewById(R.id.btnFive);
        Button buttonSix = (Button)findViewById(R.id.btnSix);
        Button buttonSeven = (Button)findViewById(R.id.btnSeven);
        Button buttonEight = (Button)findViewById(R.id.btnEight);
        Button buttonNine = (Button)findViewById(R.id.btnNine);


        code1 = (ImageView)findViewById(R.id.code1);
        code2 = (ImageView)findViewById(R.id.code2);
        code3 = (ImageView)findViewById(R.id.code3);
        code4 = (ImageView)findViewById(R.id.code4);

        buttonZero.setOnClickListener(this);
        buttonOne.setOnClickListener(this);
        buttonTwo.setOnClickListener(this);
        buttonThree.setOnClickListener(this);
        buttonFour.setOnClickListener(this);
        buttonFive.setOnClickListener(this);
        buttonSix.setOnClickListener(this);
        buttonSeven.setOnClickListener(this);
        buttonEight.setOnClickListener(this);
        buttonNine.setOnClickListener(this);


        if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
            buttonZero.setBackgroundResource(R.drawable.ic_btn_circle_l);
            buttonOne.setBackgroundResource(R.drawable.ic_btn_circle_l);
            buttonTwo.setBackgroundResource(R.drawable.ic_btn_circle_l);
            buttonThree.setBackgroundResource(R.drawable.ic_btn_circle_l);
            buttonFour.setBackgroundResource(R.drawable.ic_btn_circle_l);
            buttonFive.setBackgroundResource(R.drawable.ic_btn_circle_l);
            buttonSix.setBackgroundResource(R.drawable.ic_btn_circle_l);
            buttonSeven.setBackgroundResource(R.drawable.ic_btn_circle_l);
            buttonEight.setBackgroundResource(R.drawable.ic_btn_circle_l);
            buttonNine.setBackgroundResource(R.drawable.ic_btn_circle_l);

        }


    }

    private void circleControl()
    {
        counter++;
        switch (counter)
        {
            case 1:
                code1.setImageResource(R.drawable.ic_circle_filled);
                iCode1 = buttonClicked;
                break;
            case 2:
                code2.setImageResource(R.drawable.ic_circle_filled);
                iCode2 = buttonClicked;
                break;
            case 3:
                code3.setImageResource(R.drawable.ic_circle_filled);
                iCode3 = buttonClicked;
                break;
            case 4:
                code4.setImageResource(R.drawable.ic_circle_filled);
                iCode4 = buttonClicked;
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        String masterTest = iCode1 + "" + iCode2 + "" + iCode3 + "" + iCode4;

                        if (masterTest.equals(masterCode)){
                            Intent intent = new Intent(DriverPasswordScreen.this,DriverLoginActivity.class);
                            startActivity(intent);

                        }else{
                            AlertDialog alertDialog = new AlertDialog.Builder(DriverPasswordScreen.this).create();
                            alertDialog.setTitle("Password Incorrect");
                            alertDialog.setMessage("Please re-enter password");
                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            alertDialog.show();
                        }
                        code1.setImageResource(R.drawable.ic_circle_unfilled);
                        code2.setImageResource(R.drawable.ic_circle_unfilled);
                        code3.setImageResource(R.drawable.ic_circle_unfilled);
                        code4.setImageResource(R.drawable.ic_circle_unfilled);
                    }
                }, 500);


                counter = 0;
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.btnZero:
                Log.i(TAG,"btnZero PRESSED");
                buttonClicked = 0;
                circleControl();
                break;
            case R.id.btnOne:
                Log.i(TAG,"btnOne PRESSED");
                buttonClicked = 1;
                circleControl();
                break;
            case R.id.btnTwo:
                Log.i(TAG,"btnTwo PRESSED");
                buttonClicked = 2;
                circleControl();
                break;
            case R.id.btnThree:
                Log.i(TAG,"btnThree PRESSED");
                buttonClicked = 3;
                circleControl();
                break;
            case R.id.btnFour:
                Log.i(TAG,"btnFour PRESSED");
                buttonClicked = 4;
                circleControl();
                break;
            case R.id.btnFive:
                Log.i(TAG,"btnFive PRESSED");
                buttonClicked = 5;
                circleControl();
                break;
            case R.id.btnSix:
                Log.i(TAG,"btnSix PRESSED");
                buttonClicked = 6;
                circleControl();
                break;
            case R.id.btnSeven:
                Log.i(TAG,"btnSeven PRESSED");
                buttonClicked = 7;
                circleControl();
                break;
            case R.id.btnEight:
                Log.i(TAG,"btnEight PRESSED");
                buttonClicked = 8;
                circleControl();
                break;
            case R.id.btnNine:
                Log.i(TAG,"btnNine PRESSED");
                buttonClicked = 9;
                circleControl();
                break;

        }
    }


}
