package com.example.MyBookShopApp.data;

import com.example.MyBookShopApp.entity.Book;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BookRepository extends JpaRepository<Book,Integer> {

    List<Book>  findBookByAuthor_FirstName(String name);
    @Query("from Book")
    List<Book> customFindAllBooks();

    List<Book> findBooksByAuthorFirstNameContaining(String authorFirstName);

    List<Book> findBooksByTitleContaining(String bookTitle);

    List<Book> findBooksByPriceOldBetween(Integer min, Integer max);

    List<Book> findBooksByPriceOldIs(Integer price);
    @Query("from Book where isBestseller=1")
    List<Book> getBestsellers();
    @Query(value = "SELECT * FROM books WHERE discount = (SELECT MAX(discount) FROM books)",nativeQuery = true)
    List<Book> getBooksWithMaxDiscount();

    Page<Book> findBookByTitleContaining(String title, Pageable nextPage);


    List<Book> findBookBySlugIn(String[] slugs);

    Book findBookBySlug(String slug);







}
