package com.example.springdatajpa_intro.bootstrap;

import com.example.springdatajpa_intro.domain.Book;
import com.example.springdatajpa_intro.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    private final BookRepository bookRepository;

    public DataInitializer(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        /*We need to empty the repository */

        Book bookDDD = new Book("Domain Driven Design", "123", "Wrox");
        Book savedBook = bookRepository.save(bookDDD);

        Book bookSIA = new Book("Spring in Action", "456", "O'reily");
        Book savedBookSIA = bookRepository.save(bookSIA);

        bookRepository.findAll().forEach(book -> {
            System.out.println("Book Id: " + book.getId());
            System.out.println("Book Title: " + book.getTitle());
        });
    }
}
