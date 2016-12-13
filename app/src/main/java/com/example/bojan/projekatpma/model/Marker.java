package com.example.bojan.projekatpma.model;

/**
 * Created by Bojan on 12/11/2016.
 */

public class Marker {

    private Long id;
    private String title;
    private String description;
    private String position;

    public Marker() {
    }

    public Marker(Long id, String title, String description, String position) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.position = position;
    }

    public Marker(String title, String description, String position) {
        this.title = title;
        this.description = description;
        this.position = position;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
}
