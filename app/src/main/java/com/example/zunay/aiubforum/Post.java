package com.example.zunay.aiubforum;

/**
 * Created by RaSeL on 22-Nov-17.
 */

public class Post {
    private String postName;
    private String title;
    private String shortdesc;
    private String image;
    private String timePost;

    Post(String postName,String title, String shortdesc, String image, String timeComment) {
        this.postName = postName;
        this.title = title;
        this.shortdesc = shortdesc;
        this.image = image;
        this.timePost = timeComment;
    }

    String getTitle() {
        return title;
    }

    String getShortdesc() {
        return shortdesc;
    }

    String getImage() {
        return image;
    }

    String getTimePost() {
        return timePost;
    }

    public String getPostName() {
        return postName;
    }
}
