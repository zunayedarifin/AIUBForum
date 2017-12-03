package com.example.zunay.aiubforum;

/**
 * Created by RaSeL on 02-Dec-17.
 */

public class Comment {
    private String CommentValue;
    private String imageComment;

    Comment( String commentValue, String image) {
        this.CommentValue = commentValue;
        this.imageComment = image;
    }


    String CommentValue() {
        return CommentValue;
    }

    String getCommentImage() {
        return imageComment;
    }
}

