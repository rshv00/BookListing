package com.example.booklisting;

/**
 * Created by Рома on 2/6/2018.
 */

public class Book {
    private String title;
    private String authors;
    private String year;
    private String description;
    private String imageLink;
    private String infoLink;

    public Book(String title, String authors, String year, String description, String imageLink, String infoLink) {
        this.title = title;
        this.authors = authors;
        this.year = year;
        this.description = description;
        this.imageLink = imageLink;
        this.infoLink = infoLink;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getInfoLink() {
        return infoLink;
    }

    public void setInfoLink(String infoLink) {
        this.infoLink = infoLink;
    }
}
