package com.messed.ircmbs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.messed.ircmbs.View.Adapters.RestLiveOrderAdapter;

import java.util.ArrayList;

public class RestLiveTable extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference reference;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase database;
    ArrayList<String> tables;
    Toolbar toolbar;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_live_table);
        recyclerView=findViewById(R.id.livetablerecycler);
        tables=new ArrayList<>();
        toolbar=findViewById(R.id.toolbar_all);
        progressBar=new ProgressBar(this);
        progressBar=findViewById(R.id.prgbar);
        progressBar.setVisibility(View.VISIBLE);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });
        database = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        getTables();
    }

    private void getTables()
    {
        reference = database.getReference(firebaseAuth.getUid()).child("Table");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    tables.add(dataSnapshot1.getKey());
                }
                RestLiveOrderAdapter nob=new RestLiveOrderAdapter(getBaseContext(),tables);
                recyclerView.setAdapter(nob);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(RestLiveTable.this, "Some Error Occured", Toast.LENGTH_SHORT).show();
            }
        });
    }
}