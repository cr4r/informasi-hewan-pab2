package com.ti5a.informasihewan;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DialogForm  extends DialogFragment {
    String kodeMK, namaMK, kelas, dosenPengampuh, key, pilih;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    public DialogForm(String kodeMK, String namaMK, String kelas, String dosenPengampuh, String key, String pilih) {
        this.kodeMK = kodeMK;
        this.namaMK = namaMK;
        this.kelas = kelas;
        this.dosenPengampuh = dosenPengampuh;
        this.key = key;
        this.pilih = pilih;
    }

    TextView et_kode_mk;
    TextView et_nama_mk;
    TextView et_kelas;
    TextView et_dosen_pengampuh;

    Button btn_simpan;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.input_form, container, false);

        et_kode_mk = view.findViewById(R.id.et_kode_mk);
        et_nama_mk = view.findViewById(R.id.et_nama_mk);
        et_kelas = view.findViewById(R.id.et_kelas);
        et_dosen_pengampuh = view.findViewById(R.id.et_dosen_pengampuh);
        btn_simpan = view.findViewById(R.id.btn_simpan);

        et_kode_mk.setText(kodeMK);
        et_nama_mk.setText(namaMK);
        et_kelas.setText(kelas);
        et_dosen_pengampuh.setText(dosenPengampuh);


        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String kodeMK = et_kode_mk.getText().toString();
                String namaMK = et_nama_mk.getText().toString();
                String kelas = et_kelas.getText().toString();
                String dosenPengampuh = et_dosen_pengampuh.getText().toString();

                if (TextUtils.isEmpty(kodeMK)) {
                    DialogForm.this.input((EditText) et_kode_mk, "Kode Mata Kuliah");
                } else if (TextUtils.isEmpty(namaMK)) {
                    DialogForm.this.input((EditText) et_nama_mk, "Nama Mata Kuliah");
                } else if (TextUtils.isEmpty(kelas)) {
                    DialogForm.this.input((EditText) et_kelas, "Kelas");
                } else if (TextUtils.isEmpty(dosenPengampuh)) {
                    DialogForm.this.input((EditText) et_dosen_pengampuh, "Dosen Pengampuh");
                } else {
                    if (pilih.equals("Tambah")) {
                        getDialog().dismiss();
                        database.child("Data1").push().setValue(new MataKuliah(kodeMK, namaMK, kelas, dosenPengampuh)).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(@NonNull Void aVoid) {
                                Toast.makeText(view.getContext(), "Data Tersimpan", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(view.getContext(), "Data Gagal Tersimpan", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else if (pilih.equals("Ubah")) {
                        getDialog().dismiss();
                        database.child("Data1").child(key).setValue(new MataKuliah(kodeMK, namaMK, kelas, dosenPengampuh)).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(@NonNull Void aVoid) {
                                Toast.makeText(view.getContext(), "Data Berhasil di Ubah", Toast.LENGTH_SHORT).show();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(view.getContext(), "Data Gagal di Ubah", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }

        });

        return view;

    }

    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null){
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }
    private void input(EditText txt, String s){
        txt.setError(s+ "Tidak Boleh Kosong");
        txt.requestFocus();
    }

}
