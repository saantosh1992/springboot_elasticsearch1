package com.company.springboot.controller;


import com.company.springboot.model.Book;
import com.company.springboot.service.BookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.Map;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookDao bookDao;

    @GetMapping(value = "/hello")
    public String sayHello(){

        return "Hi Santosh!";
    }

    @PostMapping
    public Book insertBook(@RequestBody Book book){
        return bookDao.insertBook(book);
    }

    @GetMapping("/{id}")
    public Map<String, Object> getBookById(@PathVariable String id){

        return bookDao.getBookById(id);
    }

    @PutMapping("/{id}")
    public Map<String, Object> updateBookById(@RequestBody Book book, @PathVariable String id){

        return bookDao.updateBookById(book, id);
    }

    @DeleteMapping("/{id}")
    public void deleteBookById(@PathVariable String id){
        bookDao.deleteBookById(id);

    }
}
