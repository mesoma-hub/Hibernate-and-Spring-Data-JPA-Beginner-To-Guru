package com.example.springdatajpa_intro.bootstrap;

import com.example.springdatajpa_intro.domain.Book;
import com.example.springdatajpa_intro.repositories.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/*We need to add the @Component annotation or else springboot won't find it.*/
@Profile({"local", "default"})
@Component
public class DataInitializer implements CommandLineRunner {
    private final BookRepository bookRepository;

    public DataInitializer(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        /*Delete all records in the database and then reinitialize them to avoid duplicating records
        * in the database every time the application restarts.*/
        bookRepository.deleteAll();

        Book bookDDD = new Book("Domain Driven Design", "123", "Wrox");
        System.out.println("ID: " + bookDDD.getId());
        /*The savedBook will have the ID property initialised on it*/
        Book savedBookDDD = bookRepository.save(bookDDD);
        System.out.println("ID: " + savedBookDDD.getId());

        Book bookSIA = new Book("Spring in Action", "234234","O'reily");
        Book savedBookSIA = bookRepository.save(bookSIA);
        System.out.println("=".repeat(30));
        bookRepository.findAll().forEach(book -> {
            System.out.println("ID: " + book.getId());
            System.out.println("ID: " + book.getTitle());
        });

        /*Adding the following to the application.properties file "spring.jpa.show-sql=true"
        will show the sql statements executed by Hibernate.
        Hibernate: insert into book (isbn,publisher,title,id) values (?,?,?,?): The question marks
        (ie ?) are called bind parameters (used in JDBC) You can use bind parameters which is a
        good thing because you can bind parameters to prevent sql injection attacks because if you're
        just building the sql string from raw strings and concatenated strings together that's where
        you open the door to sql injection attacks. Bind parameters is a technique to avoid that
        vulnerability.*/
    }
}

/* TRANSACTIONS & ACID - What does it mean?
* Atomicity: All statements must be able to complete
* Consistency: Changes do not violate constraints
* Isolation: Reads inside transaction 'see' changed data. Reads outside transaction see original
* data (until commit)
* Durability: Once committed, changes are permanent
* Easy with one user, becomes very complex with many transacting users.

    Lost Updates
* ACID can lead to lost updates
    Preventing Lost Updates
* Locking is one technique which can be used to prevent lost updates
* Pessimistic Locking: Uses a database lock to prevent inflight transactions and will allow transactions
* to complete sequentially. ie select for update will wait for an exclusive lock
* Optimistic Locking: Uses a version property which is checked in the update
*
*
* */

