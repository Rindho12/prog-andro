package com.example.myapplication.mahasiswa;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.data.model.Mahasiswa;

import java.util.List;

public class MahasiswaListAdapter extends RecyclerView.Adapter<MahasiswaListAdapter.ViewHolder> {
    private Context context;
    private List<Mahasiswa> mahasiswaList;

    public MahasiswaListAdapter(Context context, List<Mahasiswa> mahasiswaList) {
        this.context = context;
        this.mahasiswaList = mahasiswaList;
    }

    @NonNull
    @Override
    public MahasiswaListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_list_mahasiswa,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MahasiswaListAdapter.ViewHolder holder, int position) {
        Mahasiswa m = mahasiswaList.get(position);

        holder.nim.setText(m.getNim());
        holder.nama.setText(m.getNama());
        holder.nohp.setText(m.getPhone());
    }

    @Override
    public int getItemCount() {
        return this.mahasiswaList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView nim, nama, nohp;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nim = itemView.findViewById(R.id.textViewNim);
            nama = itemView.findViewById(R.id.textViewNama);
            nohp = itemView.findViewById(R.id.textViewNoHp);
        }
    }
}
