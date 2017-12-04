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

    public PostAdapter(Context mCtx, List<Post> productList) {
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

        Post post = postList.get(position);

        holder.textViewTitle.setText(post.getTitle());
        holder.textViewShortDesc.setText(post.getShortdesc());
        Picasso.with(mCtx).load(post.getImage()).into(holder.imageView);

    }


    @Override
    public int getItemCount() {
        return postList.size();
    }


    class PostViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView textViewTitle, textViewShortDesc;
        ImageView imageView;

        PostViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            textViewTitle = (TextView) itemView.findViewById(R.id.textViewTitle);
            textViewShortDesc = (TextView) itemView.findViewById(R.id.textViewShortDesc);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
        }

        @Override
        public void onClick(View view) {
            int postion = getAdapterPosition();

            Post post = postList.get(postion);
            Intent intent=new Intent(mCtx,PostView.class);
            intent.putExtra("Description",post.getShortdesc());
            intent.putExtra("ImageSrc",post.getImage());
            intent.putExtra("position",postion);
            mCtx.startActivity(intent);
        }
    }
}