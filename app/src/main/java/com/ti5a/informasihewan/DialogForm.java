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
    String nama, keluarga, habitat, key, pilih;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    public DialogForm(String nama, String keluarga, String habitat, String key, String pilih) {
        this.nama = nama;
        this.keluarga = keluarga;
        this.habitat = habitat;
        this.key = key;
        this.pilih = pilih;
    }

    TextView et_nama;
    TextView et_keluarga;
    TextView et_habitat;

    Button btn_simpan;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.input_form, container, false);

        et_nama = view.findViewById(R.id.et_nama);
        et_keluarga = view.findViewById(R.id.et_keluarga);
        et_habitat = view.findViewById(R.id.et_habitat);
        btn_simpan = view.findViewById(R.id.btn_simpan);

        et_nama.setText(nama);
        et_keluarga.setText(keluarga);
        et_habitat.setText(habitat);


        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nama = et_nama.getText().toString();
                String keluarga = et_keluarga.getText().toString();
                String habitat = et_habitat.getText().toString();

                if (TextUtils.isEmpty(nama)) {
                    DialogForm.this.input((EditText) et_nama, "Nama Hewan");
                } else if (TextUtils.isEmpty(keluarga)) {
                    DialogForm.this.input((EditText) et_keluarga, "Keluarga Hewan");
                } else if (TextUtils.isEmpty(habitat)) {
                    DialogForm.this.input((EditText) et_habitat, "Habitat");
                } else {
                    if (pilih.equals("Tambah")) {
                        getDialog().dismiss();
                        database.child("Data1").push().setValue(new InformasiHewan(nama, keluarga, habitat)).addOnSuccessListener(new OnSuccessListener<Void>() {
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
                        database.child("Data1").child(key).setValue(new InformasiHewan(nama, keluarga, habitat)).addOnSuccessListener(new OnSuccessListener<Void>() {
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
