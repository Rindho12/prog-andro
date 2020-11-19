package com.example.myapplication.mahasiswa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.data.model.Mahasiswa;
import com.example.myapplication.utils.RecyclerItemClickListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MahasiswaActivity extends AppCompatActivity {

    private static final String TAG = "MahasiswaActivity";
    private RecyclerView rvMahasiswa;
    private List<Mahasiswa> mahasiswaList = new ArrayList<>();
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mahasiswa);
        db = FirebaseFirestore.getInstance();

        rvMahasiswa = (RecyclerView) findViewById(R.id.rvMahasiswa);
        Button buttonMhs = (Button) findViewById(R.id.keTambahMahasiswa);

        rvMahasiswa.addOnItemTouchListener(
                new RecyclerItemClickListener(MahasiswaActivity.this, rvMahasiswa, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Log.d(TAG, "Position item : "+position);

                        TextView nim = view.findViewById(R.id.textViewNim);
                        TextView nama = view.findViewById(R.id.textViewNama);
                        TextView nohp = view.findViewById(R.id.textViewNoHp);

                        Log.d(TAG, "NIM : "+nim.getText());
                        Log.d(TAG, "Nama : "+nama.getText());
                        Log.d(TAG, "No HP : "+nohp.getText());

                        Mahasiswa mhs = new Mahasiswa(nim.getText().toString(), nama.getText().toString(), nohp.getText().toString());

                        Intent intent = new Intent(MahasiswaActivity.this, MahasiswaDetailActivity.class);
                        intent.putExtra("Mahasiswa", mhs);
                        startActivity(intent);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                    }
                })
        );

        buttonMhs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Intent = new Intent(MahasiswaActivity.this, MahasiswaTambahActivity.class);
                startActivity(Intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mahasiswaList.clear();
        db.collection("DaftarMhs")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData().get("nim").toString());
                                mahasiswaList.add(new Mahasiswa(document.getData().get("nim").toString(), document.getData().get("nama").toString(), document.getData().get("phone").toString()));
                            }
                            MahasiswaListAdapter adapter = new MahasiswaListAdapter(MahasiswaActivity.this, mahasiswaList);
                            rvMahasiswa.setLayoutManager(new LinearLayoutManager(MahasiswaActivity.this));
                            rvMahasiswa.setAdapter(adapter);
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }
}