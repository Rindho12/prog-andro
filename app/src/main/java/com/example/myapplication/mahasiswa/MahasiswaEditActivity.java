package com.example.myapplication.mahasiswa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.data.model.Mahasiswa;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MahasiswaEditActivity extends AppCompatActivity {

    private EditText nim;
    private EditText nama;
    private EditText nohp;
    private Button simpan;
    private Mahasiswa mhs;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mahasiswa_edit);
        Intent intent = getIntent();
        mhs = intent.getParcelableExtra("Mahasiswa");

        db = FirebaseFirestore.getInstance();

        nim = findViewById(R.id.noMhsEdit);
        nama = findViewById(R.id.namaMhsEdit);
        nohp = findViewById(R.id.phoneMhsEdit);
        simpan = findViewById(R.id.simpanButtonEdit);

        nim.setText(mhs.getNim());
        nama.setText(mhs.getNama());
        nohp.setText(mhs.getPhone());

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!nim.getText().toString().isEmpty() && !nama.getText().toString().isEmpty()) {

                    final Mahasiswa mhsEdit = new Mahasiswa(nim.getText().toString(),
                            nama.getText().toString(),
                            nohp.getText().toString());

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
                                            document.getReference().update("nim", mhsEdit.getNim());
                                            document.getReference().update("nama", mhsEdit.getNama());
                                            document.getReference().update("phone", mhsEdit.getPhone());
                                        }
                                        Toast.makeText(MahasiswaEditActivity.this, "Mahasiswa berhasil diedit",
                                                Toast.LENGTH_SHORT).show();
                                        finish();
                                    } else {
                                    }
                                }
                            });
                    finish();
                } else {
                    Toast.makeText(MahasiswaEditActivity.this, "No dan Nama Mhs tidak boleh kosong",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}