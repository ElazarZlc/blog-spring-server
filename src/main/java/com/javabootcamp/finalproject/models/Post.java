package com.javabootcamp.finalproject.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int Id;

    private String title;
    private String description;
    private String data;



    @Column(name = "created_at")
    private LocalDateTime createdAt;
    private String author;
    private boolean published;


    public Post() {

        this.createdAt = LocalDateTime.now();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Post(String title, String description, String data, String author , boolean published) {
        this.title = title;
        this.description = description;
        this.data = data;
        this.createdAt = LocalDateTime.now();
        this.author = author;
        this.published = published;
    }

    public int getId() {
        return Id;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public boolean isPublished() {
        return published;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDescription() {
        return description;
    }

    public String getData() {
        return data;
    }
}

