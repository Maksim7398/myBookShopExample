package com.example.MyBookShopApp.data;

import com.example.MyBookShopApp.entity.Book;
import lombok.Getter;

import java.util.List;

@Getter
public class BooksPageDto {

    private Integer count;

    private List<Book> books;
    public BooksPageDto(List<Book> books) {
        this.books = books;
        this.count = books.size();
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
