package com.booklistingclient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Main {
    
    private static final String DEFAULT_HOST = "127.0.0.1";

    public static void main(String[] args) {
        System.out.println("Client application started!");

        String host = DEFAULT_HOST;
        String searchTerm = null;
        String sortField = "title";
        Integer page = null;

        // Parse command line arguments
        
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "--help":
                    printHelp();
                    return;
                case "-s":
                case "--search":
                    searchTerm = args[++i];
                    break;
                case "--sort":
                    sortField = args[++i];
                    break;
                case "-p":
                    page = Integer.parseInt(args[++i]);
                    break;
                case "-h":
                case "--host":
                    host = args[++i];
                    break;
            }
        }
        
        // Now use the parsed data to call the server's REST API
        
        if (searchTerm != null) {
            List<Book> books = callServerSearchAPI(host, searchTerm, sortField, page);
            displayBooks(books, sortField);
        }
    }

    private static void printHelp() {
        System.out.println("Usage help:\n"
        + "-s, --search <term>      : Search for a book by term\n"
        + "-p <page>                : Specify the page number\n"
        + "-h, --host <hostname>    : Specify the server hostname\n"
        + "--help                   : Display this help message\n"
        + "\n"
        + "Example usage: ~/applications-virtualexponent/java/book-listing-client/target/book-listing-client-1.0-SNAPSHOT.jar -s \"harry potter\" -p 1");
    }

    private static List<Book> callServerSearchAPI(String host, String term, String sort, Integer page) {
        String baseUrl = "http://" + host + ":8080/search";

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("term", term));
        params.add(new BasicNameValuePair("sort", sort));
        if (page != null) {
            params.add(new BasicNameValuePair("page", String.valueOf(page)));
        }

        String paramString = URLEncodedUtils.format(params, "utf-8");
        String url = baseUrl + "?" + paramString;  

        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet(url);


        List<Book> books = new ArrayList<>();
        try {
            CloseableHttpResponse response = client.execute(request);

            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                System.out.println("Error: " + response.getStatusLine().getStatusCode() + " - " + EntityUtils.toString(response.getEntity()));
            } else {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String result = EntityUtils.toString(entity);
                    ObjectMapper mapper = new ObjectMapper();
                    Book[] bookArray = mapper.readValue(result, Book[].class); // assuming server sends array of books
                    books = Arrays.asList(bookArray);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return books;
    }

    private static void displayBooks(List<Book> books, String sortField) {
        // Sort the list of books
        Collections.sort(books, (book1, book2) -> {
            if ("author".equalsIgnoreCase(sortField)) {
                return book1.getAuthor().compareTo(book2.getAuthor());
            } else {  // Default sorting is by title
                return book1.getTitle().compareTo(book2.getTitle());
            }
        });

        // Display the books
        for(Book book : books) {
            System.out.println(book.getTitle() + " by " + book.getAuthor() + " (CoverUrl: " + book.getImageUrl() + ")");  // Modify as per your Book class structure
        }
    }
}
