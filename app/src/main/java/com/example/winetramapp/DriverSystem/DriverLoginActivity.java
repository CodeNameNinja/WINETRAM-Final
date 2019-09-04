package com.example.winetramapp.DriverSystem;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;

import com.example.winetramapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DriverLoginActivity extends AppCompatActivity implements View.OnClickListener{
    private String TAG = DriverLoginActivity.class.getSimpleName();
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    public static int DriverSelectedLine;

    private ViewPager mSlideViewPager;
    private LinearLayout mDotsLayout;

    private TextView[] mDots;

    private SliderAdapter sliderAdapter;
    private static int mCurrentPage;

    private Button mLogin;

    public void registerBtn(View view)
    {

        Intent intent = new Intent(DriverLoginActivity.this,BusRegistrationActivity.class);
        startActivity(intent);
    }
    public void loginBtn(View view)
    {
        Toast.makeText(this, "" + mCurrentPage, Toast.LENGTH_SHORT).show();
        switch(mCurrentPage)
        {
            case 0:
                DriverSelectedLine = 0;
                authenticateUser("redlinedriver@bus.com", "123456");
                Log.d(TAG, "authenticated as REDLINE Driver");
                break;
            case 1:
                DriverSelectedLine = 1;
                authenticateUser("bluelinedriver@bus.com", "123456");
                Log.d(TAG, "authenticated as BLUE Driver");
                break;
            case 2:
                DriverSelectedLine = 2;
                authenticateUser("greenlinedriver@bus.com", "123456");
                Log.d(TAG, "authenticated as GREEN Driver");
                break;
            case 3:
                DriverSelectedLine = 3;
                authenticateUser("yellowlinedriver@bus.com", "123456");
                Log.d(TAG, "authenticated as YELLOW Driver");
                break;
            case 4:
                DriverSelectedLine = 4;
                authenticateUser("orangelinedriver@bus.com", "123456");
                Log.d(TAG, "authenticated as ORANGE Driver");
                break;
            case 5:
                DriverSelectedLine = 5;
                authenticateUser("purplelinedriver@bus.com", "123456");
                Log.d(TAG, "authenticated as PURPLE Driver");
                break;
            case 6:
                DriverSelectedLine = 6;
                authenticateUser("pinklinedriver@bus.com", "123456");
                Log.d(TAG, "authenticated as PINK Driver");
                break;
            case 7:
                DriverSelectedLine = 7;
                authenticateUser("greylinedriver@bus.com", "123456");
                Log.d(TAG, "authenticated as GREY Driver");
                break;
            case 8:
                DriverSelectedLine = 7;
                authenticateUser("franschhoekdriver@tram.com","123456");
                Log.d(TAG, "authenticated as Franschhoek Tram Driver");
                break;
            case 9:
                DriverSelectedLine = 9;
                authenticateUser("drakensteindriver@tram.com","123456");
                Log.d(TAG, "authenticated as Drakenstein tram Driver");
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + mCurrentPage);
        }
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_login_activity);

        getSupportActionBar().setTitle("Login");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mSlideViewPager = (ViewPager)findViewById(R.id.slideViewPager);
        mDotsLayout = (LinearLayout) findViewById(R.id.dotsLayout);

        sliderAdapter = new SliderAdapter(this);
        mSlideViewPager.setAdapter(sliderAdapter);

        addDotsIndicator(0);

        mSlideViewPager.addOnPageChangeListener(viewListener);


        mAuth = FirebaseAuth.getInstance();

        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user != null){
                    Intent intent = new Intent(DriverLoginActivity.this, DriverMapActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                }
            }
        };
    }

    public void addDotsIndicator(int position)
    {
        mDots = new TextView[10];
        mDotsLayout.removeAllViews();
        for(int i = 0; i < mDots.length; i++)
        {
            mDots[i] = new TextView(DriverLoginActivity.this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.colorTransparentWhite));
            mDotsLayout.addView(mDots[i]);
        }

        if(mDots.length > 0)
        {
            mDots[position].setTextColor(getResources().getColor(R.color.colorPrimary));
        }
    }

    final ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int i) {
            addDotsIndicator(i);
            mCurrentPage = i;
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };


    public void authenticateUser(final String mEmail, final String mPassword){

        final String email = mEmail.replace(" ","");
        final String password = mPassword.replace(" ","");

        if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(DriverLoginActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(Task<AuthResult> task) {
                    if (!task.isSuccessful()) {

                        Toast.makeText(DriverLoginActivity.this, "sign in error", Toast.LENGTH_SHORT).show();
                    } else
                        mAuth.addAuthStateListener(firebaseAuthListener);
                }
            });
        }
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
    public void onClick(View view) {

    }

}
