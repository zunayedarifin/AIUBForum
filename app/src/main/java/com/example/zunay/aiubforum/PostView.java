package com.example.zunay.aiubforum;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

    List<Comment> commentList;

    private RecyclerView recyclerView;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_view);
        postDescription = (TextView) findViewById(R.id.postDescriptionId);
        postImage = (ImageView) findViewById(R.id.postImageId);
        postDescription.setText(getIntent().getStringExtra("Description"));
        Picasso.with(this).load(getIntent().getStringExtra("ImageSrc")).into(postImage);

        int position = getIntent().getIntExtra("position",0);
        position +=1;

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

                        String imgSrc = "https://static.boredpanda.com/blog/wp-content/uploads/2017/01/badass-babies-thug-life-22-587ddcd0bbbbd__605.jpg";
                        Comment obj = new Comment(description,imgSrc);
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
