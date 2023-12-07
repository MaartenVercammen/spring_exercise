package com.example.demo.repository;


import com.example.demo.model.Book;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends ListCrudRepository<Book, Long> {

}
