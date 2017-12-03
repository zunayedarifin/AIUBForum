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

/**
 * Created by RaSeL on 22-Nov-17.
 */


public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {


    //this context we will use to inflate the layout
    private Context mCtx;

    //we are storing all the products in a list
    private List<Post> postList;

    //getting the context and product list with constructor
    public PostAdapter(Context mCtx, List<Post> productList) {
        this.mCtx = mCtx;
        this.postList = productList;
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.single_row, null);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PostViewHolder holder, int position) {
        //getting the product of the specified position
        Post post = postList.get(position);

        //binding the data with the viewholder views
        holder.textViewTitle.setText(post.getTitle());
        holder.textViewShortDesc.setText(post.getShortdesc());
        // holder.imageView.setImageDrawable(mCtx.getResources().getDrawable(post.getImage()));
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
            //  Toast.makeText(mCtx," view is clicked -"+postion,Toast.LENGTH_LONG).show();
            Post post = postList.get(postion);
            Intent intent=new Intent(mCtx,PostView.class);
            intent.putExtra("Description",post.getShortdesc());
            intent.putExtra("ImageSrc",post.getImage());
            intent.putExtra("position",postion);
            mCtx.startActivity(intent);
        }
    }
}