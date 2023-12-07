package com.example.demo.service;

import com.example.demo.model.Book;
import com.example.demo.repository.BookRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    public Optional<Book> getBookById(long id) {
        return bookRepository.findById(id);
    }

    public Optional<Book> deleteBookById(long id) {
        Optional<Book> old = bookRepository.findById(id);
        bookRepository.deleteById(id);
        return old;
    }

    public Optional<Book> updateBook(long id, Book book) {
        if (book.getId() != id) {
            return Optional.empty();
        }
        return Optional.of(bookRepository.save(book));
    }

    public Book createBook(Book book) {
        return bookRepository.save(book);
    }
}
