package com.nerzon.webflux_demo.controller;

import com.nerzon.webflux_demo.entity.Book;
import com.nerzon.webflux_demo.repository.BookRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookRepo bookRepo;

    public BookController(BookRepo bookRepo) {
        this.bookRepo = bookRepo;
    }

    @GetMapping("/{id}")
    public Mono<Book> getBookById(@PathVariable String id) {
        System.out.println(System.currentTimeMillis());
        return bookRepo.findById(id);
    }

    @GetMapping
    public Flux<Book> getAllBooks() {
        return bookRepo.findAll();
    }

    @PostMapping
    public Mono<Book> addNewBook(@RequestBody Book book) {
        System.out.println(System.currentTimeMillis());
        return bookRepo.save(book);
    }
}
