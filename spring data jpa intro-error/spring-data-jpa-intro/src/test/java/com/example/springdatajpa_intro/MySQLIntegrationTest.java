package com.example.springdatajpa_intro;

import com.example.springdatajpa_intro.repositories.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
/*After adding the @ActiveProfiles("local") the test fails because the H2 database is still
being wired in by the Spring boot test splice @DataJpaTest. As a result hibernate is issuing
sql commands and h2 is failing. Spring boot is overriding our configuration even though the local
profile is set already. Spring boot is setting the datasource to h2 instead of mysql*/
@ActiveProfiles("local")
@DataJpaTest
@ComponentScan(basePackages = {"com.example.springdatajpa_intro.bootstrap"})
/*This annotation tells spring boot not to do the autoconfiguration of the datasource*/
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MySQLIntegrationTest {
    @Autowired
    BookRepository bookRepository;
    /*When we run this test it passes because we're testing the h2 embedded database instead of MySQL
    * we need to set the profile to bring in mysql */
    @Test
    void testMySQL() {
        long count = bookRepository.count();
        assertThat(count).isEqualTo(2);
    }
}
