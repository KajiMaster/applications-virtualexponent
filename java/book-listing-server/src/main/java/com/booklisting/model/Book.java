package com.booklisting.model;

import com.booklisting.dto.OpenLibraryBookDTO;

public class Book {

    private String author;
    private String title;
    private String imageUrl;

    public Book() {
    }

    public Book(String author, String title, String imageUrl) {
        this.author = author;
        this.title = title;
        this.imageUrl = imageUrl;
    }
    
    public Book(OpenLibraryBookDTO olBook) {
        if (olBook.getAuthors() != null && !olBook.getAuthors().isEmpty()) {
            this.author = olBook.getAuthors().get(0);  // taking the first author for simplicity
        }
        this.title = olBook.getTitle();
        if (olBook.getCoverId() != null) {
            // System.out.println("Cover ID: " + olBook.getCoverId()); <-- logged the details of my getCoverID for testing
            this.imageUrl = "https://covers.openlibrary.org/b/id/" + olBook.getCoverId() + "-L.jpg";
        }
    }
    
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "Book{" +
                "author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}