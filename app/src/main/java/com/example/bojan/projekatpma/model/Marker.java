package com.example.bojan.projekatpma.model;

/**
 * Created by Bojan on 12/11/2016.
 */

public class Marker {

    private Long id;
    private String title;
    private String snippet;
    private String position;

    public Marker() {
    }

    public Marker(Long id, String title, String snippet, String position) {
        this.id = id;
        this.title = title;
        this.snippet = snippet;
        this.position = position;
    }

    public Marker(String title, String snippet, String position) {
        this.title = title;
        this.snippet = snippet;
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

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
