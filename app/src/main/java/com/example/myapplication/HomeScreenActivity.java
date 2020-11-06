package com.example.myapplication;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.data.model.Film;
import com.example.myapplication.service.MyJobService;
import com.example.myapplication.ui.main.FilmRecyclerAdapter;
import com.example.myapplication.utils.MyBroadcastReceiver;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.ui.main.SectionsPagerAdapter;

import java.util.List;

public class HomeScreenActivity extends AppCompatActivity {

    private static final String TAG = "HomeScreenActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        BroadcastReceiver newBroadcastReceiver = new MyBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.EXTRA_NO_CONNECTIVITY);
        this.registerReceiver(newBroadcastReceiver, intentFilter);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);


        RecyclerView rvFilm = (RecyclerView) findViewById(R.id.rvFilm);
        List<Film> filmList = null;
        filmList.add(new Film(R.drawable.planes, "planes", "Ini adalah film planes"));
        filmList.add(new Film(R.drawable.conjuring, "Conjuring", "Ini adalah film conjuring"));
        filmList.add(new Film(R.drawable.thor, "Thor", "Ini adalah film thor"));
        FilmRecyclerAdapter adapter = new FilmRecyclerAdapter(getApplicationContext(), filmList);
        rvFilm.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvFilm.setAdapter(adapter);
    }

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

    public void cancelJob(View view) {
        JobScheduler scheduler = (JobScheduler) getSystemService(JobScheduler.class);
        scheduler.cancel(123);
        Log.i(TAG, "scheduleJob: Cancel");

    }

}