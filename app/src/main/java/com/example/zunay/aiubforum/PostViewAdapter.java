package com.example.zunay.aiubforum;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class PostViewAdapter extends RecyclerView.Adapter<PostViewAdapter.PostCommentHolder> {
    private Context mCtx;

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
        Comment comment = postCommentList.get(postCommentList.size()-position-1);
        holder.textViewComment.setText(comment.CommentValue());
        holder.textViewCommentTime.setText(comment.getCommentTime());
        Picasso.with(mCtx).load(comment.getCommentImage()).into(holder.imageViewComment);
    }

    @Override
    public int getItemCount() {
        return postCommentList.size();
    }

    class PostCommentHolder extends RecyclerView.ViewHolder {
        TextView textViewComment, textViewCommentTime;
        ImageView imageViewComment;

        PostCommentHolder(View itemView) {
            super(itemView);

            textViewComment = itemView.findViewById(R.id.textViewComment);
            imageViewComment = itemView.findViewById(R.id.imageViewComment);
            textViewCommentTime = itemView.findViewById(R.id.commentTime);
        }

    }
}
