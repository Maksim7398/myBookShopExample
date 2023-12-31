package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.BooksPageDto;
import com.example.MyBookShopApp.data.SearchWordDto;
import com.example.MyBookShopApp.entity.Book;
import com.example.MyBookShopApp.errors.EmptySearchException;
import com.example.MyBookShopApp.service.BookService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MainPageController {

    private final BookService bookService;

    @Autowired
    public MainPageController(BookService bookService
                           ) {
        this.bookService = bookService;

    }

    @GetMapping("/")
    public String mainPage(){
        return "index";
    }
    @ModelAttribute("recommendedBooks")
    public List<Book> recommendedBooks(){
        return bookService.getPageOfRecommendedBooks(0,6).getContent();
    }



    @GetMapping("books/recommended")
    @ResponseBody
    public BooksPageDto getBooksPage(@RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit){
        return new BooksPageDto(bookService.getPageOfRecommendedBooks(offset,limit).getContent());
    }

    @ModelAttribute("searchWordDto")
    public static SearchWordDto searchWordDto(){
        return new SearchWordDto();
    }

    @ModelAttribute("searchResults")
    public List<Book> searchResults(){
        return new ArrayList<>();
    }

    @GetMapping(value = {"/search", "/search/{searchWord}"})
    public String getSearchResult(@PathVariable(value = "searchWord", required = false) String searchWordDto,
                                  Model model) throws EmptySearchException {
        if(searchWordDto!=null){

            model.addAttribute("searchWordDto", searchWordDto );
            model.addAttribute("searchResults",
                    bookService.getPageOfSearchResultBooks(searchWordDto, 0, 5));
            return "/search/index";
        }else {
            throw new EmptySearchException("Поиск по null невозможен");
        }

    }

    @GetMapping("/search/page/{searchWord}")
    @ResponseBody
    public BooksPageDto getNextSearchPage(@RequestParam("offset") Integer offset,
                                          @RequestParam("limit") Integer limit,
                                          @PathVariable(value = "searchWord", required = false) SearchWordDto searchWordDto) {
        return new BooksPageDto(bookService.getPageOfGoogleBooksApiSearchResults(searchWordDto.getExample(), offset, limit));
    }











    @GetMapping("/recent")
    public String news(){
        return "books/recent";
    }
    @GetMapping("/popular")
    public String popular(){
        return "books/popular";
    }
    @GetMapping("/genres")
    public String genres(){
        return "genres/index";
    }



}
