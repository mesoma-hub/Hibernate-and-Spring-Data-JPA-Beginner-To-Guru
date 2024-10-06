package com.example.springdatajpa_intro.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Objects;


@Entity
public class Book {
    /*GenerationType.AUTO: Allows the persistence provider to automatically assign this
    * GenerationType.IDENTITY: Delegates the database to manage assigning it
    * GenerationType.SEQUENCE: For a database sequence
    * GenerationType.TABLE: Having a table to maintain the next available Id value*/
    /*One very important thing about Entities, Hibernate specifically is going to be looking
    for a no argument constructor. If we add a constructor to initialise fields for the class
    like below Hibernate will throw an error because now that we have added constructor for
    the class the no argument constructor is going to go away. Adding the no-arg constructor
    solves the issue*/
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String isbn;
    private String publisher;

    public Book() {
    }

    public Book(String title, String isbn, String publisher) {
        this.title = title;
        this.isbn = isbn;
        this.publisher = publisher;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String pulisher) {
        this.publisher = pulisher;
    }
    /*Hibernate equality: This becomes important if we're going to be dealing with sets of objects
    * or entities that are persisted. Setting up equality allows Hibernate and Java to determine
    * equality. So technically we are looking at it from a persistence operation. The Id value
    * is used for comparison since it doesn't change*/

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book book)) return false;

        return Objects.equals(id, book.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public Long getId() {
        return id;
    }
}
