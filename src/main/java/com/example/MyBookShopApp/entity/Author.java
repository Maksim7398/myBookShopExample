package com.example.MyBookShopApp.entity;

import com.example.MyBookShopApp.entity.Book;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "authors")
@ApiModel(description = "data model authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "id generated by db",position = 1)
    private Integer id;

    @OneToMany(mappedBy = "author")
    @JsonIgnore
    private List<Book> bookList = new ArrayList<>();

    public Author(ArrayList<String> authors) {
        if (authors != null){
            this.firstName = authors.toString();
        }
    }

    public Author() {
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }
    @Column(name = "first_name")
    @ApiModelProperty(value = "firstname of author",example = "Bob",position = 2)
    private String firstName;
    @Column(name = "last_name")
    @ApiModelProperty(value = "lastname of author",example = "Bobovithz",position = 3)
    private String lastName;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return firstName + ' ' + lastName;
    }
}
