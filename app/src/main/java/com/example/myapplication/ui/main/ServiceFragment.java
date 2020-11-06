package com.example.myapplication.ui.main;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.service.MyJobService;


public class ServiceFragment extends Fragment {

    private static Context context;
    private static final String TAG = "HomeScreenActivity";

    public ServiceFragment() {
        // Required empty public constructor
    }

    public static ServiceFragment newInstance(Context param1) {
        ServiceFragment fragment = new ServiceFragment();
        Bundle args = new Bundle();
        context = param1;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_service, container, false);
    }
}