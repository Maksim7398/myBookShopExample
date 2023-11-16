package com.example.MyBookShopApp.service;

import com.example.MyBookShopApp.data.BookRepository;
import com.example.MyBookShopApp.data.google.api.books.Item;
import com.example.MyBookShopApp.data.google.api.books.Root;
import com.example.MyBookShopApp.entity.Author;
import com.example.MyBookShopApp.entity.Book;
import com.example.MyBookShopApp.errors.BookstoreApiWrongParmeterException;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {


   private final BookRepository bookRepository;
   private final RestTemplate restTemplate;

    @Autowired
    public BookService(BookRepository bookRepository, RestTemplate restTemplate) {
        this.bookRepository = bookRepository;
        this.restTemplate = restTemplate;
    }

//    public List<Book> getBooksData(){
//        return bookRepository.findAll();
//    }
    public List<Book> bookList(){
        return bookRepository.findAll();
    }

    public List<Book> getBooksByAuthor(String authorName){
      return   bookRepository.findBooksByAuthorFirstNameContaining(authorName);
    }
    @SneakyThrows
    public List<Book> getBooksByTitleContaining(String bookTitle)  {
        if (bookTitle.equals("") || bookTitle.length() <=1){
            throw  new BookstoreApiWrongParmeterException("Wrong values passed to one or more parameters");
        }else {
            List<Book> data =  bookRepository.findBooksByTitleContaining(bookTitle);
            if (data.size()>0){
                return data;
            }else {
                throw  new BookstoreApiWrongParmeterException("No data found with specified parameters...");
            }
        }
    }
    public List<Book> findBookBySlugsIn(String[] slug){
       return bookRepository.findBookBySlugIn(slug);
    }

    public List<Book> byBooksPriceOldBetween(Integer min, Integer max){
       return bookRepository.findBooksByPriceOldBetween(min,max);
    }

    public List<Book> findBookPriceOldIs(Integer price){
     return bookRepository.findBooksByPriceOldIs(price);
    }

    public List<Book> getBestsellers(){
        return bookRepository.getBestsellers();
    }

    public List<Book> getBooksWithMaxDiscount(){
        return bookRepository.getBooksWithMaxDiscount();
    }

    public Page<Book> getPageOfRecommendedBooks(Integer offset,Integer limit){
        Pageable nextPage = PageRequest.of(offset,limit);
        return bookRepository.findAll(nextPage);
    }
    public Page<Book> getPageOfSearchResultBooks(String searchWord,Integer offset,Integer limit){
        Pageable nextPage = PageRequest.of(offset,limit);
        return bookRepository.findBookByTitleContaining(searchWord,nextPage);
    }

    public Book findBookBySlugContains(String slug){
        return bookRepository.findBookBySlug(slug);
    }


    public void save(Book book){
        bookRepository.save(book);
    }

    @Value("${google.books.api.key}")
    private String apiKey;

    public List<Book> getPageOfGoogleBooksApiSearchResults(String searchWord,Integer offset,Integer limit){
        String REQUEST_URL = "https://www.googleapis.com/books/v1/volumes?" +
                "q=" + searchWord +
                "&key=" + apiKey +
                "&filter=paid-ebooks" +
                "&startIndex=" + offset +
                "&maxResults=" + limit;

        Root root = restTemplate.getForEntity(REQUEST_URL,Root.class).getBody();
        ArrayList<Book> list = new ArrayList<>();
        if (root != null){
            for (Item item : root.getItems()){
                Book book = new Book();
                if (item.getVolumeInfo() != null){
                    book.setAuthor(new Author(item.getVolumeInfo().getAuthors()));
                    book.setTitle(item.getVolumeInfo().getTitle());
                    book.setImage(item.getVolumeInfo().getImageLinks().getThumbnail());

                }
                if (item.getSaleInfo() != null){
                    book.setPrice(item.getSaleInfo().getRetailPrice().getAmount());
                    Integer oldPrice = item.getSaleInfo().getListPrice().getAmount();
                    book.setPriceOld(oldPrice);
                }
                list.add(book);
            }
        }
        return list;

    }
}
