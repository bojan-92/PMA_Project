package com.example.bojan.projekatpma.model;

/**
 * Created by Bojan on 12/11/2016.
 */

public class Marker {

    private String id;
    private String title;
    private String description;
    private String position;
    private String category;

    public Marker() {
    }

    public Marker(String id, String title, String description, String position,String category) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.position = position;
        this.category = category;
    }

    public Marker(String title, String description, String position,String category) {
        this.title = title;
        this.description = description;
        this.position = position;
        this.category = category;
    }

    public Marker(String title, String description, String position) {
        this.title = title;
        this.description = description;
        this.position = position;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
