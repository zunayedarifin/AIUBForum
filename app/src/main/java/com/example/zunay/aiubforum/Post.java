package com.example.zunay.aiubforum;

/**
 * Created by RaSeL on 22-Nov-17.
 */

public class Post {
    private String title;
    private String shortdesc;
    private String image;

    Post(String title, String shortdesc, String image) {
        this.title = title;
        this.shortdesc = shortdesc;
        this.image = image;
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
}
