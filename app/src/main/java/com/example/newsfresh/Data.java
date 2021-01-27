package com.example.newsfresh;

public class Data {
    private String title;
    private String author;
    private String url;
    private  String imageUrl;

    public Data(String title, String author, String url, String imageUrl) {
        this.title = title;
        this.author = author;
        this.url = url;
        this.imageUrl = imageUrl;
    }

    public String getUrl() {
        return url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

//    public void setTitle(String text) {
//        this.title = text;
//    }
}
