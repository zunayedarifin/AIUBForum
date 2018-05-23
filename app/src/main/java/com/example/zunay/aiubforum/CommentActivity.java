package com.example.zunay.aiubforum;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class CommentActivity extends AppCompatActivity {
    private TextView textView,textView2;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textView = findViewById(R.id.textView3);
        textView2 = findViewById(R.id.textView2);

        imageView = findViewById(R.id.postSrc);
        textView.setText(getIntent().getStringExtra("Description"));
        textView2.setText(getIntent().getStringExtra("title"));
        Picasso.with(getApplicationContext()).load(getIntent().getStringExtra("ImageSrc").trim()).into(imageView);
    }

    public void btnReplyClicked(View view) {
        Intent intent = new Intent(getApplicationContext(),ReplyActivity.class);
        startActivity(intent);
    }
}
