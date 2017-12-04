package com.example.zunay.aiubforum;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    List<Post> postList;
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    private FloatingActionButton postingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        recyclerView = findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        postingButton=findViewById(R.id.posting_widget);

        postList = new ArrayList<>();
        final PostAdapter adapter = new PostAdapter(this, postList);

        databaseReference = FirebaseDatabase.getInstance().getReference("Post");

        ValueEventListener roomsValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for ( DataSnapshot userDataSnapshot : dataSnapshot.getChildren() ) {
                    if (userDataSnapshot != null) {
                        String postname = userDataSnapshot.getKey();
                        //Toast.makeText(getApplicationContext(),postname,Toast.LENGTH_LONG).show();

                        String title = userDataSnapshot.child("Title").getValue().toString();
                        String postTime = userDataSnapshot.child("Time").getValue().toString();
                        String description = userDataSnapshot.child("Description").getValue().toString();
                        String imgSrc = userDataSnapshot.child("Image").getValue().toString();
                        if(imgSrc.isEmpty()){
                            imgSrc = getResources().getResourceName(R.drawable.no_image);
                        }
                        Post obj = new Post(title,description,imgSrc,postTime);
                        Post obj = new Post(postname,title,description,imgSrc,postTime);
                        postList.add(obj);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),databaseError.toString(),Toast.LENGTH_LONG).show();
            }
        };

        postingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this,PostingActivity.class);
                startActivity(intent);
            }
        });

        databaseReference.addListenerForSingleValueEvent(roomsValueEventListener);
        recyclerView.setAdapter(adapter);
    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser=mAuth.getCurrentUser();
        if (currentUser==null)
        {
            sendToStart();
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId()==R.id.main_logout_button)
        {
            FirebaseAuth.getInstance().signOut();
            sendToStart();
        }
        if (item.getItemId()==R.id.account_settings)
        {
            Intent intent= new Intent(MainActivity.this,SettingsActivity.class);
            startActivity(intent);
        }
        return true;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    private void sendToStart() {
        Intent intent= new Intent(MainActivity.this,StartActivity.class);
        startActivity(intent);
        finish();
    }
}
