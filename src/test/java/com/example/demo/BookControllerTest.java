package com.example.demo;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.demo.controller.BookController;
import com.example.demo.model.Book;
import com.example.demo.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)
class BookControllerTest {

    private final List<Book> bookList = new ArrayList<Book>();
    @Autowired
    private MockMvc controller;
    @MockBean
    private BookService service;

    @BeforeEach
    void setup() {
        bookList.add(new Book("test 1", "Maarten"));
        bookList.add(new Book("test 2", "Maarten"));
        bookList.add(new Book("test 3", "Maarten"));
    }

    @Test
    void whenGetAll_ReturnListOfBooks() throws Exception {
        when(service.getAll()).thenReturn(bookList);

        this.controller.perform(
                        get("/book"))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(bookList)));
    }

    @Test
    void whenGetById_ReturnBookWithId() throws Exception {
        when(service.getBookById(1)).thenReturn(Optional.of(bookList.get(0)));

        this.controller.perform(
                        get("/book/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(bookList.get(0))));
    }

    @Test
    void whenGetById_ReturnNoContent() throws Exception {
        when(service.getBookById(1)).thenReturn(Optional.empty());

        this.controller.perform(
                        get("/book/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void whenCreate_ReturnCreatedResource() throws Exception {
        when(service.createBook(any())).thenReturn(bookList.get(0));

        this.controller.perform(
                        post("/book")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(bookList.get(0))))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(bookList.get(0))));
    }

    @Test
    void whenUpdate_ReturnUpdatedResource() throws Exception {
        when(service.updateBook(anyLong(), any())).thenReturn(Optional.of(bookList.get(0)));
        this.controller.perform(
                        put("/book/0")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(bookList.get(0))))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(bookList.get(0))));
    }

    @Test
    void whenDelete_ReturnDeletedResource() throws Exception {
        when(service.deleteBookById(anyLong())).thenReturn(Optional.of(bookList.get(0)));
        this.controller.perform(
                        delete("/book/0"))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(bookList.get(0))));
    }

}
