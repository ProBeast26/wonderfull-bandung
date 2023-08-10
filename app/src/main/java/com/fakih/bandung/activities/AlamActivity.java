package com.fakih.bandung.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.fakih.bandung.R;
import com.fakih.bandung.Tools;
import com.fakih.bandung.adapter.AlamAdapter;
import com.fakih.bandung.layout.LayoutMarginDecoration;
import com.fakih.bandung.model.ModelAlam;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AlamActivity extends AppCompatActivity implements AlamAdapter.onSelectData {

    RecyclerView rvWisata;
    AlamAdapter alamAdapter;
    LayoutMarginDecoration gridMargin;
    List<ModelAlam> lsAlam = new ArrayList<>();
    DatabaseReference reference;
    Toolbar tbWisata;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alam);

        tbWisata = findViewById(R.id.toolbar_wisata);
        tbWisata.setTitle("Daftar Wisata");
        setSupportActionBar(tbWisata);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Mohon Tunggu");
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Sedang menampilkan data...");

        rvWisata = findViewById(R.id.wisataList);
        GridLayoutManager mLayoutManager = new GridLayoutManager(this,
                2, RecyclerView.VERTICAL, false);
        rvWisata.setLayoutManager(mLayoutManager);
        gridMargin = new LayoutMarginDecoration(2, Tools.dp2px(this, 4));
        rvWisata.addItemDecoration(gridMargin);
        rvWisata.setHasFixedSize(true);

        reference = FirebaseDatabase.getInstance("https://sykaris-4d64f-default-rtdb.firebaseio.com").getReference().child("Alam");
        reference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                Log.d("firebase databse", String.valueOf(task.getResult().getValue()));

            }
        });
        alamAdapter = new AlamAdapter(this, lsAlam, this);
        rvWisata.setAdapter(alamAdapter);

        progressDialog.show();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ModelAlam modelAlam = dataSnapshot.getValue(ModelAlam.class);
                    lsAlam.add(modelAlam);
                }
                alamAdapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onSelected(ModelAlam modelAlam) {
         Intent intent = new Intent(AlamActivity.this, DetailAlamActivity.class);
            intent.putExtra("detailData", modelAlam);
            startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}