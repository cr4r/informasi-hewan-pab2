package com.ti5a.informasihewan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton fab_add;
    RecyclerAdapter recyclerAdapter;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    ArrayList<InformasiHewan> listInformasiHewan;
    RecyclerView rv_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fab_add = findViewById(R.id.fb_add);
        rv_view = findViewById(R.id.rv_view);

        RecyclerView.LayoutManager mLayout = new LinearLayoutManager(this);
        rv_view.setLayoutManager(mLayout);
        rv_view.setItemAnimator(new DefaultItemAnimator());

        fab_add.setOnClickListener(v -> {
            DialogForm dialogForm = new DialogForm("", "", "", "", "Tambah");
            dialogForm.show(getSupportFragmentManager(),"form");
        });

        showData();
    }
    private void showData(){
        database.child("Data1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listInformasiHewan = new ArrayList<>();
                for (DataSnapshot item : snapshot.getChildren()){
                    InformasiHewan ih = item.getValue(InformasiHewan.class);
                    ih.setKey(item.getKey());
                    listInformasiHewan.add(ih);
                }

                recyclerAdapter = new RecyclerAdapter(listInformasiHewan, MainActivity.this);
                rv_view.setAdapter(recyclerAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}