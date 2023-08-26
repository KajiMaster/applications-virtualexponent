package com.booklisting.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.booklisting.dto.OpenLibraryResponseDTO;

import com.booklisting.model.Book;
import com.booklisting.service.BookService;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class BookService {
    
    private static final String OPEN_LIBRARY_API = "https://openlibrary.org";
    private final WebClient webClient;

    @Autowired
    public BookService(WebClient WebClientConfig) {
        this.webClient = WebClientConfig;
    }

    private static final Logger logger = LoggerFactory.getLogger(BookService.class);  // <-- Create a logger instance

    public BookService() {
        this.webClient = WebClient.builder().baseUrl(OPEN_LIBRARY_API).build();
    }

    public List<Book> searchBooks(String term, String sort, Integer page) {
        logger.info("Searching books with term: {}, sort: {}, page: {}", term, sort, page);  // Logging for debugging

        String searchEndpoint = "/search.json";  // This can be changed based on what you want to search (books, authors, etc.)
        StringBuilder queryParams = new StringBuilder();
        queryParams.append("?q=").append(term);  // Basic search query

        if (sort != null) {
            queryParams.append("&sort=").append(sort);
        }
    
        if (page != null) {
            queryParams.append("&page=").append(page);
        }
    
        String constructedUrl = searchEndpoint + queryParams.toString(); // A basic example, you'd need to add more parameters here

        List<Book> books = Collections.emptyList(); // Default to an empty list

        try {
            OpenLibraryResponseDTO response = webClient.get()
                .uri(constructedUrl)
                .retrieve()
                .bodyToMono(OpenLibraryResponseDTO.class)
                .block();
        
            books = response.getBooks().stream()
                .map(Book::new)
                .collect(Collectors.toList());
        
        } catch (WebClientResponseException e) {
            logger.error("Error fetching books from OpenLibrary API. Status: {}, Body: {}", e.getStatusCode(), e.getResponseBodyAsString());
        }
    
        return books;
    }

    public Book findBookById(Long id) {
        logger.info("Searching for book with ID: {}", id);  // <-- Add logging statement
        //... other code
        return null; // Return null for now. Remember to replace this with the actual logic.
    }
}
  