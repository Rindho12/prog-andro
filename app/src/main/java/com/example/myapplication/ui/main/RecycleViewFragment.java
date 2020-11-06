package com.example.myapplication.ui.main;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.data.model.Film;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecycleViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecycleViewFragment extends Fragment {

    private static Context context;
    private static final String TAG = "HomeScreenActivity";

    public RecycleViewFragment() {
        // Required empty public constructor
    }

    public static RecycleViewFragment newInstance(Context param1) {
        RecycleViewFragment fragment = new RecycleViewFragment();
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
        View view = inflater.inflate(R.layout.fragment_recycle_view, container, false);

        RecyclerView rvFilm = (RecyclerView) view.findViewById(R.id.rvFilm);
        List<Film> filmList = new ArrayList<>();
        filmList.add(new Film(R.drawable.planes, "planes", "Ini adalah film planes"));
        filmList.add(new Film(R.drawable.conjuring, "Conjuring", "Ini adalah film conjuring"));
        filmList.add(new Film(R.drawable.thor, "Thor", "Ini adalah film thor"));
        FilmRecyclerAdapter adapter = new FilmRecyclerAdapter(context, filmList);
        rvFilm.setLayoutManager(new LinearLayoutManager(context));
        rvFilm.setAdapter(adapter);

        return view;
    }
}