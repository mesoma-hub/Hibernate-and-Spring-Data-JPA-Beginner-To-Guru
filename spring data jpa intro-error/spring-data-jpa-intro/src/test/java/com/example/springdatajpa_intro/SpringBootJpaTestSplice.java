package com.example.springdatajpa_intro;

import com.example.springdatajpa_intro.domain.Book;
import com.example.springdatajpa_intro.repositories.BookRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
/*@TestMethodOrder: This annotation helps us to run the test according to the specified order*/
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//@ActiveProfiles("local")
@DataJpaTest /*@DataJpaTest: is a SpringBoot test splice*/
@ComponentScan(basePackages = {"com.example.springdatajpa_intro.bootstrap"}) /*Configure the DataInitializer to be registered in the context*/
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SpringBootJpaTestSplice {
    @Autowired
    BookRepository bookRepository;

    /*Initialization test*/
    @Commit /*This test is Commiting the test data to the database in a transaction*/
    @Order(1)
    @Test
    void testJpaTestSplice() {
        long countBefore = this.bookRepository.count();
        /*Because we're using the @DataJpaTest annotation the DataInitializer component is not
        * initialized because the @DataJpaTest annotation doesn't register it in the context
        * as it's not related to a database activity and so the test fails.*/
        /*assertThat(countBefore).isEqualTo(2);*/

        assertThat(countBefore).isEqualTo(2); // initially 0 was passed to the method
        /*This persists a new entity to the database*/
        bookRepository.save(new Book("My Book", "1235555", "Self"));

        long countAfter = this.bookRepository.count();
        assertThat(countBefore).isLessThan(countAfter);
    }
    /*The tests may not run according to order, so we need make sure it does because the
    initialisation test has to run before this. After making sure the test runs in order it
    still fails because the default behaviour of Spring Boot on a test context is to run
    the first transactional context (ie the first method) and then roll it back. And then
    run the next transactional context (ie the second test method) and then roll it back.
    Which is typically the behaviour that we want. Instead of the test populating data into
    other tests which will lead to a lot of problems. There are 2 ways to resolve this.
    1) @Rollback(value = false): This is least preferred
    2) @Commit: This is preferred because it makes it cleaner and clearly states the
    intentions.
    The second test is testing for persistence. There's two things to take note of if we're writing
    tests like this:
    1) The sequence of the test becomes very important, so we need to make use of the
    @TestMethodOrder() of the JUNIT 5 Library*/
    @Order(2)
    @Test
    void testJpaTestSpliceTransaction() {
        long countBefore = this.bookRepository.count();
        assertThat(countBefore).isEqualTo(3); // initially 1 was passed to the method
    }
}

/* Hibernate Schema Generation Tool
* hbm2ddl.auto configuration property has the ability to reflect on JPA annotated classes to
* determine necessary database structure
* Hibernate can create DDL statements to file, execute DDL statements to create or update database
* tables. Spring Boot is auto configuring this property to automatically generate database tables
* the Hibernate DDL auto properties include:
* 1) none: Disables the schema generation tool.
* 2) create-only: Create database schema from JPA entities.
* 3) drop: drops database tables related to JPA entities.
* 4) create-drop: drops database schemas and recreates from JPA entities, then will drop when shutting down.
* 5) validate: validates schema, fatal error if wrong (preferred as it helps reflect on the data structure).
* 6) update: updates schema from database entities.
* */