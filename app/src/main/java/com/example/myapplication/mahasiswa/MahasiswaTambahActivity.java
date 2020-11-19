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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class MahasiswaTambahActivity extends AppCompatActivity {

    private EditText nim;
    private EditText nama;
    private EditText nohp;
    private Button simpan;
    private FirebaseFirestore firebaseFirestoreDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mahasiswa_tambah);

        firebaseFirestoreDb = FirebaseFirestore.getInstance();

        nim = findViewById(R.id.noMhs);
        nama = findViewById(R.id.namaMhs);
        nohp = findViewById(R.id.phoneMhs);
        simpan = findViewById(R.id.simpanButton);

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!nim.getText().toString().isEmpty() && !nama.getText().toString().isEmpty()) {

                    Mahasiswa mhs = new Mahasiswa(nim.getText().toString(),
                            nama.getText().toString(),
                            nohp.getText().toString());

                    firebaseFirestoreDb.collection("DaftarMhs").document().set(mhs)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(MahasiswaTambahActivity.this, "Mahasiswa berhasil didaftarkan",
                                            Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(MahasiswaTambahActivity.this, "ERROR" + e.toString(),
                                            Toast.LENGTH_SHORT).show();
                                    Log.d("TAG", e.toString());
                                }
                            });
                    finish();
                } else {
                    Toast.makeText(MahasiswaTambahActivity.this, "No dan Nama Mhs tidak boleh kosong",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}