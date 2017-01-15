package com.example.bojan.projekatpma.model;

/**
 * Created by Bojan on 1/6/2017.
 */

public class Category {

    private String id;
    private String title;

    public Category(){}

    public Category(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
