package com.nerzon.webflux_demo.repository;

import com.nerzon.webflux_demo.entity.Book;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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

    public Mono<Book> save(Book book) {
        datasource.put(book.getId(), book);
        return Mono.just(book);
    }

    public Mono<Book> findById(String id) {
        return Mono.just(datasource.get(id));
    }

    public Flux<Book> findAll() {
        return Flux.fromIterable(datasource.values());
    }

}
