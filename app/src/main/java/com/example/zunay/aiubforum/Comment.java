package com.example.zunay.aiubforum;

/**
 * Created by RaSeL on 02-Dec-17.
 */

public class Comment {
    private String CommentValue;
    private String imageComment;
    private String commentTime;

    Comment( String commentValue, String image,String commentTime) {
        this.CommentValue = commentValue;
        this.imageComment = image;
        this.commentTime = commentTime;
    }


    String CommentValue() {
        return CommentValue;
    }

    String getCommentImage() {
        return imageComment;
    }

    public String getCommentTime() {
        return commentTime;
    }
}

