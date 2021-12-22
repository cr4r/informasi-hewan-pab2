package com.ti5a.informasihewan;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
    private List<InformasiHewan> mList;
    private Activity activity;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    public RecyclerAdapter(List<InformasiHewan> mList, Activity activity) {
        this.mList = mList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public RecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.layout_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.MyViewHolder holder, int position) {
        InformasiHewan matkul = mList.get(position);

        holder.tv_kode_mk.setText("Kode MK : "+matkul.getKodeMK());
        holder.tv_nama_mk.setText("Nama MK : "+matkul.getNamaMK());
        holder.tv_kelas.setText  ("Kelas : "+matkul.getKelas());
        holder.tv_dosen_pengampuh.setText("Dosen Pengampuh : "+matkul.getDosenPengampuh());
        holder.hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        database.child("Data1").child(matkul.getKey()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(@NonNull Void aVoid) {
                                Toast.makeText(activity, "Berhasil dihapus", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(activity, "Gagal dihapus", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).setMessage("Apakah Anda Ingin Menghapus Data MK "+ matkul.getNamaMK()+" ?");
                builder.show();
            }
        });

        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                FragmentManager manager = ((AppCompatActivity)activity).getSupportFragmentManager();
                DialogForm dialogForm = new DialogForm(matkul.getKodeMK(),
                        matkul.getNamaMK(),
                        matkul.getKelas(),
                        matkul.getDosenPengampuh(),
                        matkul.getKey(),
                        "Ubah");

                dialogForm.show(manager, "form");
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_kode_mk;
        TextView tv_nama_mk;
        TextView tv_kelas;
        TextView tv_dosen_pengampuh;

        CardView cardView;
        Button hapus;

        public MyViewHolder(View itemView) {
            super(itemView);

            tv_kode_mk = itemView.findViewById(R.id.tv_kode_mk);
            tv_nama_mk = itemView.findViewById(R.id.tv_nama_mk);
            tv_kelas = itemView.findViewById(R.id.tv_kelas);
            tv_dosen_pengampuh = itemView.findViewById(R.id.tv_dosen_pengampuh);
            cardView = itemView.findViewById(R.id.card_view);
            hapus = itemView.findViewById(R.id.hapus);
        }
    }
}
