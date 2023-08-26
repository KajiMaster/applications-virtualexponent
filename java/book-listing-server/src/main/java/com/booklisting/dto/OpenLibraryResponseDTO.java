package com.booklisting.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class OpenLibraryResponseDTO {

    @JsonProperty("docs")
    private List<OpenLibraryBookDTO> books;

    public List<OpenLibraryBookDTO> getBooks() {
        return books;
    }

    public void setBooks(List<OpenLibraryBookDTO> books) {
        this.books = books;
    }
}
