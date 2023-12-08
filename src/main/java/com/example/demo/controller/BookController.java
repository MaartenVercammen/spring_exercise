package com.example.demo.controller;

import com.example.demo.exceptions.ResourceNoContentException;
import com.example.demo.model.Book;
import com.example.demo.service.BookService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<Book> getBooks() {
        return bookService.getAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Book getBook(@PathVariable long id) {
        var book = bookService.getBookById(id);
        return book.orElseThrow(ResourceNoContentException::new);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Book deleteBook(@PathVariable long id) {
        var book = bookService.deleteBookById(id);
        return book.orElseThrow(ResourceNoContentException::new);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Book update(@PathVariable long id, @RequestBody Book book) {
        var outBook = bookService.updateBook(id, book);
        return outBook.orElseThrow(ResourceNoContentException::new);
    }

}
