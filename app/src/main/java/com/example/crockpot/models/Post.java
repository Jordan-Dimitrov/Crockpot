package com.example.crockpot.models;

import java.util.UUID;

public class Post {
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public Post(){

    }
    public Post(String content, String imageName) {
        this.content = content;
        this.imageName = imageName;
        this.postId = UUID.randomUUID().toString();
    }

    String content;
    String imageName;

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    String postId;
}
