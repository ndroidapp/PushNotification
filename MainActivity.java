package com.example.noor.pushnotification;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private TextView mProfileLabel, mUsersLabel, mNotificationLabel;
    private ViewPager mMainPager;
    private PagerViewAdapter mPagerViewAdapter;
    private FirebaseAuth mAuth;

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            sendToLogin();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        mProfileLabel = findViewById(R.id.profileLabel);
        mUsersLabel = findViewById(R.id.usersLabel);
        mNotificationLabel = findViewById(R.id.notificationLabel);
        mMainPager = findViewById(R.id.mainPager);

        mMainPager.setOffscreenPageLimit(2);

        mPagerViewAdapter = new PagerViewAdapter(getSupportFragmentManager());
        mMainPager.setAdapter(mPagerViewAdapter);

        mMainPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                changeTabs(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mProfileLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMainPager.setCurrentItem(0);
            }
        });
        mUsersLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMainPager.setCurrentItem(1);
            }
        });
        mNotificationLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMainPager.setCurrentItem(2);
            }
        });

    }

    private void sendToLogin() {
        Intent intentLogin = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intentLogin);
        finish();
    }

    private void changeTabs(int position) {
        if (position == 0) {
            mProfileLabel.setTextSize(24);
            mProfileLabel.setTextColor(getResources().getColor(R.color.colorWhite));

            mUsersLabel.setTextSize(18);
            mUsersLabel.setTextColor(getResources().getColor(R.color.colorLightGrey));

            mNotificationLabel.setTextSize(18);
            mNotificationLabel.setTextColor(getResources().getColor(R.color.colorLightGrey));
        }

        if (position == 1) {
            mProfileLabel.setTextSize(18);
            mProfileLabel.setTextColor(getResources().getColor(R.color.colorLightGrey));

            mUsersLabel.setTextSize(24);
            mUsersLabel.setTextColor(getResources().getColor(R.color.colorWhite));

            mNotificationLabel.setTextSize(18);
            mNotificationLabel.setTextColor(getResources().getColor(R.color.colorLightGrey));
        }
        if (position == 2) {
            mProfileLabel.setTextSize(18);
            mProfileLabel.setTextColor(getResources().getColor(R.color.colorLightGrey));

            mUsersLabel.setTextSize(18);
            mUsersLabel.setTextColor(getResources().getColor(R.color.colorLightGrey));

            mNotificationLabel.setTextSize(24);
            mNotificationLabel.setTextColor(getResources().getColor(R.color.colorWhite));
        }
    }
}
