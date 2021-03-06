package com.example.zunay.aiubforum;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PostView extends AppCompatActivity {

    private TextView postDescription;
    private ImageView postImage;
    private int position;
    List<Comment> commentList;

    //the recyclerview
    private Button mCommentBtn;
    private RecyclerView recyclerView;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_view);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        postDescription = (TextView) findViewById(R.id.postDescriptionId);
        postImage = (ImageView) findViewById(R.id.postImageId);
        postDescription.setText(getIntent().getStringExtra("Description"));
        Picasso.with(this).load(getIntent().getStringExtra("ImageSrc")).into(postImage);

        position = getIntent().getIntExtra("position",0);
        position +=1;
        mCommentBtn = findViewById(R.id.btnComment);
        mCommentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PostView.this,ReplyActivity.class);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recycleViewComment);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        commentList = new ArrayList<>();
        final PostViewAdapter adapter = new PostViewAdapter(this, commentList);

        databaseReference = FirebaseDatabase.getInstance().getReference("Data").child("Post "+String.valueOf(position)).child("Reply");

        ValueEventListener roomsValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for ( DataSnapshot userDataSnapshot : dataSnapshot.getChildren() ) {
                    if (userDataSnapshot != null) {
                        String description = userDataSnapshot.child("Comment").getValue(String.class);
                        String commentTime = userDataSnapshot.child("Time").getValue(String.class);

                        String imgSrc = "https://static.boredpanda.com/blog/wp-content/uploads/2017/01/badass-babies-thug-life-22-587ddcd0bbbbd__605.jpg";
                        Comment obj = new Comment(description,imgSrc,commentTime);
                        commentList.add(obj);
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        databaseReference.addListenerForSingleValueEvent(roomsValueEventListener);
        recyclerView.setAdapter(adapter);

    }
}
