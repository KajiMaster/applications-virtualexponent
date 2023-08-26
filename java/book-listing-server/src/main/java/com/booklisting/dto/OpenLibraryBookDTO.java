package com.booklisting.dto;  // Moved it to the dto package

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class OpenLibraryBookDTO {

    @JsonProperty("title_suggest")
    private String title;

    @JsonProperty("author_name")
    private List<String> authors;

    @JsonProperty("cover_i")
    private Integer coverId;

    //Getters
    public String getTitle() {
        return title;
    }
    
    public List<String> getAuthors() {
        return authors;
    }
    
    public Integer getCoverId() {
        return coverId;
    }

    //Setters
    public void setTitle(String title) {
        this.title = title;
    }
    
    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }
    
    public void setCoverId(Integer coverId) {
        this.coverId = coverId;
    }
}
