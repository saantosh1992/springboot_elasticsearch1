package com.company.springboot.service;

import com.company.springboot.model.Book;

import java.util.Map;

public interface BookDao {

    public Map<String, Object> getBookById(String id);

    public Book insertBook(Book book);

    public Map<String, Object> updateBookById(Book book, String id);

    public void deleteBookById(String id);
}
