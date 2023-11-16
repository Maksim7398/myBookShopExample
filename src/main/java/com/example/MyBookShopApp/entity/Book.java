package com.example.MyBookShopApp.entity;

import com.example.MyBookShopApp.data.BookFileType;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import org.springframework.boot.jackson.JsonComponent;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "books")
@ApiModel(description = "entity representing a book ")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("id auto generated db")
    private Integer id;
    @Column(name = "pub_date")
    @ApiModelProperty("date of book publication")
    private Date pubDate;

//    @Column(name = "author_id")
//    private Integer authorId;
    @ManyToOne
    @JoinColumn(name = "author_id",referencedColumnName = "id")
    @JsonIgnore
    private Author author;
    @Column(name = "is_bestseller")
    @ApiModelProperty("book which is bestseller if bestseller = 1, if 0 is to not best seller")
    private Integer isBestseller;
    @ApiModelProperty("mnemonically identify sequence of characters")
    private String slug;
    @ApiModelProperty("book title")
    private String title;
    @ApiModelProperty("book image url")
    private String image;



    @OneToMany(mappedBy = "book")
    private List<BookFile> bookFileList = new ArrayList<>();

    public List<BookFile> getBookFileList() {
        return bookFileList;
    }

    public void setBookFileList(List<BookFile> bookFileList) {
        this.bookFileList = bookFileList;
    }

    @Column(columnDefinition = "TEXT")
    private String description;
    @Column(name = "price")
    @JsonProperty("price")
    @ApiModelProperty("price without is discount")
    private Integer priceOld;
    @Column(name = "discount")
    @JsonProperty("discount")
    @ApiModelProperty("discount value to book")
    private Double price;

    @ApiModelProperty("rating to books")
    private Double rating;



    @JsonProperty
    public Integer discountPrice(){
        return priceOld - Math.toIntExact(Math.round(price * priceOld));
    }
    @JsonGetter("authors")
    public String authorsFullName(){
        return author.toString();
    }
    @JsonGetter("get_discount")
    public Integer getDiscount(){
        return Math.toIntExact(Math.round(price) * 100);
    }

    public Date getPubDate() {
        return pubDate;
    }

    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
    }

    public Integer getIsBestseller() {
        return isBestseller;
    }

    public void setIsBestseller(Integer isBestseller) {
        this.isBestseller = isBestseller;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPriceOld() {
        return priceOld;
    }

    public void setPriceOld(Integer priceOld) {
        this.priceOld = priceOld;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Author getAuthor() {
        return author;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double stars_count) {
        this.rating = stars_count;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", author=" + author +
                ", title='" + title + '\'' +
                ", priceOld=" + priceOld +
                ", price=" + price +
                '}';
    }
}
