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

public class TramRegistrationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Button mBusReg;
    private EditText mEmail, mPassword;
    private Button mLogin, mRegistration, mTramReg;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;

    private String text;
    private final String[] setSpinner = new String[9];

    public void btnLeft(View view)
    {
        Intent intent = new Intent(TramRegistrationActivity.this, BusRegistrationActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tram_registration_activity);

        getSupportActionBar().setTitle("Register Tram Driver");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Spinner spinner = findViewById(R.id.select_bus_line_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.TramLines, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        mAuth = FirebaseAuth.getInstance();

        firebaseAuthListener = firebaseAuth -> {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if(user != null){
                Intent intent = new Intent(TramRegistrationActivity.this, DriverMapActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        };

        mEmail = (EditText) findViewById(R.id.bus_email);
        mPassword = (EditText) findViewById(R.id.tram_password);

        mRegistration = (Button) findViewById(R.id.bus_registration);
        mRegistration.setOnClickListener(v -> {
            String email = mEmail.getText().toString();
            String password = mPassword.getText().toString();

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(TramRegistrationActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        Toast.makeText(TramRegistrationActivity.this, "Sign up Error", Toast.LENGTH_SHORT).show();
                    } if(setSpinner[0] =="Franschhoek Tram") {
                        String user_id = mAuth.getCurrentUser().getUid();
                        DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child("Drivers").child("Tram Franschhoek").child(user_id);
                        current_user_db.setValue(true);
                    }
                    if (setSpinner[1]== "Drakenstein Tram") {
                        String user_id = mAuth.getCurrentUser().getUid();
                        DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child("Drivers").child("Tram Drakenstein").child(user_id);
                        current_user_db.setValue(true);
                    }
                    if (setSpinner[2] =="GreyPink Tram" ) {
                        String user_id = mAuth.getCurrentUser().getUid();
                        DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child("Drivers").child("GreyPinkTram").child(user_id);
                        current_user_db.setValue(true);
                    }

                }
            });

        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                text = parent.getItemAtPosition(position).toString();
                switch(text)
                {
                    case "Franschhoek Tram":
                        setSpinner[0] = "Franschhoek Tram";
                        break;
                    case "Drakenstein Tram":
                        setSpinner[1] = "Drakenstein Tram";
                        break;
                    case "GreyPinkTram":
                        setSpinner[2] = "GreyPinkTram";
                        break;


                }
//                Toast.makeText(parent.getContext(), text+" "+ setSpinner[0], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(firebaseAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(firebaseAuthListener);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
