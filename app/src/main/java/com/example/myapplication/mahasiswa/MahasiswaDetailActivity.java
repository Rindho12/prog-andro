package com.example.myapplication.mahasiswa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.data.model.Mahasiswa;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MahasiswaDetailActivity extends AppCompatActivity {

    private TextView nim;
    private TextView nama;
    private TextView nohp;
    private Button edit;
    private Button hapus;
    private Mahasiswa mhs;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mahasiswa_detail);

        Intent intent = getIntent();
        mhs = intent.getParcelableExtra("Mahasiswa");

        db = FirebaseFirestore.getInstance();

        nim = findViewById(R.id.textViewNimDetail);
        nama = findViewById(R.id.textViewNamaDetail);
        nohp = findViewById(R.id.textViewNoHpDetail);
        edit = findViewById(R.id.buttonEditDetail);
        hapus = findViewById(R.id.buttonHapusDetail);

        nim.setText(mhs.getNim());
        nama.setText(mhs.getNama());
        nohp.setText(mhs.getPhone());

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(MahasiswaDetailActivity.this, MahasiswaEditActivity.class);
                intent.putExtra("Mahasiswa", mhs);
                startActivity(intent);
            }
        });

        hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                    db.collection("DaftarMhs")
                            .whereEqualTo("nim", mhs.getNim())
                            .whereEqualTo("nama", mhs.getNama())
                            .whereEqualTo("phone", mhs.getPhone())
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            document.getReference().delete();
                                        }
                                        Toast.makeText(MahasiswaDetailActivity.this, "Mahasiswa berhasil dihapuskan",
                                                Toast.LENGTH_SHORT).show();
                                        finish();
                                    } else {
                                    }
                                }
                            });
            }
        });
    }
}