package com.example.zunay.aiubforum;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private Context mCtx;

    private List<Post> postList;

    PostAdapter(Context mCtx, List<Post> productList) {
        this.mCtx = mCtx;
        this.postList = productList;
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.single_row, null);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PostViewHolder holder, int position) {

        Post post = postList.get(postList.size()-position-1);

        holder.textViewTitle.setText(post.getTitle());
        holder.textViewShortDesc.setText(post.getShortdesc());
        holder.postTime.setText(post.getTimePost());
        Picasso.with(mCtx).load(post.getImage()).into(holder.imageView);

    }


    @Override
    public int getItemCount() {
        return postList.size();
    }


    class PostViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView textViewTitle, textViewShortDesc, postTime;
        ImageView imageView;

        PostViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewShortDesc = itemView.findViewById(R.id.textViewShortDesc);
            imageView = itemView.findViewById(R.id.imageView);
            postTime = itemView.findViewById(R.id.PostTime);
        }

        @Override
        public void onClick(View view) {
            int postion = postList.size()-getAdapterPosition()-1;

            Post post = postList.get(postion);

            Intent intent=new Intent(mCtx,PostView.class);
            intent.putExtra("Description",post.getShortdesc());
            intent.putExtra("ImageSrc",post.getImage());
            intent.putExtra("postName",post.getPostName());
            mCtx.startActivity(intent);
        }
    }
}