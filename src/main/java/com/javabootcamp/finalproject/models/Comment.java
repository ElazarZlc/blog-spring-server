package com.javabootcamp.finalproject.models;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    String name;
    String data;
    @Column(name = "created_at")
    LocalDateTime createdAt;
    @Column(name = "post_id")
    int postId;

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Comment() {
        this.createdAt = LocalDateTime.now();
    }

    public Comment(String name, String data, int postId) {
        this.name = name;
        this.data = data;
        this.createdAt = LocalDateTime.now();
        this.postId = postId;
    }

    public int getId() {
        return id;
    }



    public String getName() {
        return name;
    }



    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }


    public int getPostId() {
        return postId;
    }


}
