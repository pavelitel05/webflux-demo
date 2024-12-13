package com.nerzon.rest_api.controller;

import com.nerzon.rest_api.entity.Book;
import com.nerzon.rest_api.repository.BookRepo;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookRepo bookRepo;

    public BookController(BookRepo bookRepo) {
        this.bookRepo = bookRepo;
    }

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable String id) {
        System.out.println(System.currentTimeMillis());
        return bookRepo.findById(id);
    }

    @GetMapping
    public Collection<Book> getAllBooks() {
        return bookRepo.findAll();
    }

    @PostMapping
    public Book addNewBook(@RequestBody Book book) {
        System.out.println(System.currentTimeMillis());
        return bookRepo.save(book);
    }
}
