package com.example.winetramapp.DriverSystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.winetramapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BusRegistrationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private EditText mEmail, mPassword;
    private Button mLogin, mRegistration, mTramReg;

    private FirebaseAuth mBusAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private DatabaseReference mDatabase;
    private String text;
    private final String[] setSpinner = new String[9];

    public void btnRight(View v)
    {
        Intent intent = new Intent(BusRegistrationActivity.this,TramRegistrationActivity.class);
        startActivity(intent);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bus_registration_activity);

        getSupportActionBar().setTitle("Register Bus Driver");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Spinner spinner = findViewById(R.id.select_bus_line_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.lines, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        mBusAuth = FirebaseAuth.getInstance();

        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user != null){
                    Intent intent = new Intent(BusRegistrationActivity.this, DriverMapActivity.class);
                    startActivity(intent);
                    finish();

                }
            }
        };

        mRegistration = (Button) findViewById(R.id.bus_registration);

        mEmail = (EditText) findViewById(R.id.bus_email);
        mPassword = (EditText) findViewById(R.id.bus_password);

        mRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 String email = mEmail.getText().toString();
                 String password = mPassword.getText().toString();

                mBusAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(BusRegistrationActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(BusRegistrationActivity.this, "Sign up Error", Toast.LENGTH_SHORT).show();
                        } if(setSpinner[0] == "Red Line") {
                            String user_id = mBusAuth.getCurrentUser().getUid();
                            DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child("Drivers").child("RedLine").child("bus").child(user_id+"RedLinebus");
                            current_user_db.setValue(true);
                        }
                        if (setSpinner[1]== "Blue Line") {
                            String user_id = mBusAuth.getCurrentUser().getUid();
                            DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child("Drivers").child("BlueLine").child("bus").child(user_id);
                            current_user_db.setValue(true);
                        }
                        if (setSpinner[2] =="Green Line" ) {
                            String user_id = mBusAuth.getCurrentUser().getUid();
                            DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child("Drivers").child("GreenLine").child("bus").child(user_id);
                            current_user_db.setValue(true);
                        }
                        if (setSpinner[3]== "Yellow Line") {
                            String user_id = mBusAuth.getCurrentUser().getUid();
                            DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child("Drivers").child("YellowLine").child("bus").child(user_id);
                            current_user_db.setValue(true);
                        }
                        if (setSpinner[4]== "Purple Line") {
                            String user_id = mBusAuth.getCurrentUser().getUid();
                            DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child("Drivers").child("PurpleLine").child("bus").child(user_id);
                            current_user_db.setValue(true);
                        }
                        if (setSpinner[5]=="Orange Line") {
                            String user_id = mBusAuth.getCurrentUser().getUid();
                            DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child("Drivers").child("OrangeLine").child("bus").child(user_id);
                            current_user_db.setValue(true);
                        }
                        if (setSpinner[6]=="Grey Line") {
                            String user_id = mBusAuth.getCurrentUser().getUid();
                            DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child("Drivers").child("GreyLine").child("bus").child(user_id);
                            current_user_db.setValue(true);
                        }
                        if (setSpinner[7]=="Pink Line") {
                            String user_id = mBusAuth.getCurrentUser().getUid();
                            DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child("Drivers").child("PinkLine").child("bus").child(user_id);
                            current_user_db.setValue(true);
                        }
                    }
                });

            }
        });


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                text = parent.getItemAtPosition(position).toString();
                switch(text)
                {
                    case "Red Line":
                        setSpinner[0] = "Red Line";
                        break;
                    case "Blue Line":
                        setSpinner[1] = "Blue Line";
                        break;
                    case "Green Line":
                        setSpinner[2] = "Green Line";
                        break;
                    case "Yellow Line":
                        setSpinner[3] = "Yellow Line";
                        break;
                    case "Purple Line":
                        setSpinner[4] = "Purple Line";
                        break;
                    case "Orange Line":
                        setSpinner[5] = "Orange Line";
                        break;
                    case "Grey Line":
                        setSpinner[6] = "Grey Line";
                        break;
                    case "Pink Line":
                        setSpinner[7] = "Pink Line";
                        break;

                }
                Toast.makeText(parent.getContext(), text+" "+ setSpinner[0], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }
    @Override
    protected void onStart() {
        super.onStart();
        mBusAuth.addAuthStateListener(firebaseAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mBusAuth.removeAuthStateListener(firebaseAuthListener);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
