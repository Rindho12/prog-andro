package com.example.myapplication;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.service.MyJobService;
import com.example.myapplication.ui.login.LoginActivity;
import com.example.myapplication.utils.MyBroadcastReceiver;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.RequiresApi;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.ui.main.SectionsPagerAdapter;

public class HomeScreenActivity extends AppCompatActivity {

    private static final String TAG = "HomeScreenActivity";
    SharedPreferences session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        session = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if(session.getString("displayName", "").isEmpty() && session.getString("username", "").isEmpty()) {
            finish();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            return;
        }

        BroadcastReceiver newBroadcastReceiver = new MyBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.EXTRA_NO_CONNECTIVITY);
        this.registerReceiver(newBroadcastReceiver, intentFilter);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        Button logoutButton = findViewById(R.id.logout);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = session.edit();
                editor.clear();
                editor.apply();
                finish();
                Intent Intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(Intent);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void scheduleJob(View view) {
        ComponentName componentName = new ComponentName(getApplicationContext(), MyJobService.class);
        JobInfo info  = new JobInfo.Builder(123, componentName)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .setMinimumLatency(1*1000)
                .setOverrideDeadline(3*1000)
                .build();

        JobScheduler scheduler = (JobScheduler) getSystemService(JobScheduler.class);
        int resultCode = scheduler.schedule(info);
        if(resultCode == JobScheduler.RESULT_SUCCESS) {
            Log.i(TAG, "scheduleJob: Success");
        } else {
            Log.i(TAG, "scheduleJob: Fail");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void cancelJob(View view) {
        JobScheduler scheduler = (JobScheduler) getSystemService(JobScheduler.class);
        scheduler.cancel(123);
        Log.i(TAG, "scheduleJob: Cancel");

    }
}