package com.nerzon.rest_api.repository;


import com.nerzon.rest_api.entity.Book;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class BookRepo {
    private final Map<String, Book> datasource = new ConcurrentHashMap<>();

    public BookRepo() {
        datasource.put("1", new Book("1", "1984", "George Orwell"));
        datasource.put("2", new Book("2", "Fahrenheit 451", "Ray Bradbury"));
        datasource.put("3", new Book("3", "Foundation", "Isaac Asimov"));
        datasource.put("4", new Book("4", "The Naked Sun", "Isaac Asimov"));
    }

    public Book save(Book book) {
        datasource.put(book.getId(), book);
        return book;
    }

    public Book findById(String id) {
        return datasource.get(id);
    }

    public Collection<Book> findAll() {
        return datasource.values();
    }

}
