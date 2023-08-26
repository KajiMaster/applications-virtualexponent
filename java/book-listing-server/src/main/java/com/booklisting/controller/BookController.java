package com.booklisting.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory; // <-- Import the SLF4J classes

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;

import com.booklisting.model.Book;
import com.booklisting.service.BookService;

import java.util.List;

@RestController
public class BookController {

    private static final Logger logger = LoggerFactory.getLogger(BookController.class);  // <-- Create a logger instance

    // Inject the book service
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/")
    public ResponseEntity<String> rootEndpoint() {
        logger.info("Root endpoint accessed");  // <-- Add logging statement
        return new ResponseEntity<>("Book Listing Service is Up and Running!", HttpStatus.OK);
    }

    @RequestMapping("/search")
    public List<Book> search(@RequestParam String term, @RequestParam(required=false) String sort, @RequestParam(required=false) Integer page) {
        logger.info("Search endpoint accessed with term: {}, sort: {}, page: {}", term, sort, page);  // <-- Add logging statement
        return bookService.searchBooks(term, sort, page);
    }


}
