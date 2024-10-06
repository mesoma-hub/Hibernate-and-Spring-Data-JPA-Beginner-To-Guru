package com.example.springdatajpa_intro;

import com.example.springdatajpa_intro.repositories.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/*@SpringBootTest: makes sure that the context loads by bringing in the entire spring boot context. But
* right now we have a lightweight project, so it's not that much of a penalty to bring up everything.
* in a complex project with a lot of moving pieces with a lot more things being wired and configured.
* It can really slow down your test. Here come SpringBoot test splices which brings up minimal context
* for us to work with. For instance if we're only going to be testing the database layer then let's only
* bring up the database layer*/
@SpringBootTest
class SpringDataJpaIntroApplicationTests {

	@Autowired
	BookRepository bookRepository;
	/*This is sanity check test to ensure that springBoot context will run and load properly
	without any error*/
	@Test
	void contextLoads() {
	}

	@Test
	void testBookRepository() {
		long count = bookRepository.count();
		assertThat(count).isGreaterThan(0);
		/*Hibernate starts up, performs reflection on the JPA entity, creates everything in the database
		* for us, our test executes and it goes into shutdown mode where it cleans up the database.*/
	}
}
