package com.example.zunay.aiubforum;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by RaSeL on 02-Dec-17.
 */

public class PostViewAdapter extends RecyclerView.Adapter<PostViewAdapter.PostCommentHolder> {
    //this context we will use to inflate the layout
    private Context mCtx;

    //we are storing all the products in a list
    private List<Comment> postCommentList;

    public PostViewAdapter(Context mCtx, List<Comment> commentList) {
        this.mCtx = mCtx;
        this.postCommentList = commentList;
    }

    @Override
    public PostCommentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.single_comment, null);
        return new PostCommentHolder(view);
    }

    @Override
    public void onBindViewHolder(PostCommentHolder holder, int position) {
        //getting the product of the specified position
        Comment comment = postCommentList.get(position);

        //binding the data with the viewholder views
        holder.textViewComment.setText(comment.CommentValue());
        // holder.imageView.setImageDrawable(mCtx.getResources().getDrawable(post.getImage()));
        Picasso.with(mCtx).load(comment.getCommentImage()).into(holder.imageViewComment);
    }

    @Override
    public int getItemCount() {
        return postCommentList.size();
    }

    class PostCommentHolder extends RecyclerView.ViewHolder {
        TextView textViewComment;
        ImageView imageViewComment;

        PostCommentHolder(View itemView) {
            super(itemView);

            textViewComment = (TextView) itemView.findViewById(R.id.textViewComment);
            imageViewComment = (ImageView) itemView.findViewById(R.id.imageViewComment);
        }

    }
}
