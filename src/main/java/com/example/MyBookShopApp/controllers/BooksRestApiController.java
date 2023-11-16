package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.ApiResponse;
import com.example.MyBookShopApp.errors.BookstoreApiWrongParmeterException;
import com.example.MyBookShopApp.service.BookService;
import com.example.MyBookShopApp.entity.Book;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
@Api("book data api")
public class BooksRestApiController {

    private final BookService bookService;
    @Autowired
    public BooksRestApiController(BookService bookService) {
        this.bookService = bookService;
    }
    @GetMapping("books/by-author")
    @ApiOperation("operation to get list of bookshop by passed author first name")
    public ResponseEntity<List<Book>> booksByAuthor(@RequestParam("author") String authorName){
        return ResponseEntity.ok(bookService.getBooksByAuthor(authorName));
    }
    @GetMapping("books/by-title")
    @ApiOperation("get books by book title")
    public ResponseEntity<ApiResponse<Book>> booksByTitle(@RequestParam("title") String title){
        ApiResponse<Book> response = new ApiResponse<>();
        List<Book> data = bookService.getBooksByTitleContaining(title);
        response.setDebugMessage("successful request");
        response.setMessages("data size: " + data.size() + " elements");
        response.setHttpStatus(HttpStatus.OK);
        response.setTimeStamp(LocalDateTime.now());
        response.setData(data);
        return ResponseEntity.ok(response);
    }
    @GetMapping("books/by-price-range")
    @ApiOperation("get books by price from min to max price")
    public ResponseEntity<List<Book>> booksByPriceRange(@RequestParam("min") Integer min,
                                                        @RequestParam("max") Integer max){
        return ResponseEntity.ok(bookService.byBooksPriceOldBetween(min,max));
    }
    @GetMapping("books/with-max-price")
    @ApiOperation("get books with price is max discount")
    public ResponseEntity<List<Book>> booksByPriceRange(){
        return ResponseEntity.ok(bookService.getBooksWithMaxDiscount());
    }

    @GetMapping("books/bestsellers")
    @ApiOperation("get books which  is bestseller = 1")
    public ResponseEntity<List<Book>> bestsellersBooks(){
        return ResponseEntity.ok(bookService.getBestsellers());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiResponse<Book>> handlerMissingServletRequestException(Exception exception){

        return new ResponseEntity<>(new ApiResponse<Book>(HttpStatus.BAD_REQUEST,
                "Missing required parameters",exception),HttpStatus.BAD_REQUEST);

    }
    @ExceptionHandler(BookstoreApiWrongParmeterException.class)
    public ResponseEntity<ApiResponse<Book>> handlerBookstoreParameterException(Exception exception){
        return new ResponseEntity<>(new ApiResponse<Book>(HttpStatus.BAD_REQUEST,
                "Bad parameter value",exception),HttpStatus.BAD_REQUEST);
    }
}
