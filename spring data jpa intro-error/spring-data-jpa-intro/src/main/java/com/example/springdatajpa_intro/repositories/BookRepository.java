package com.example.springdatajpa_intro.repositories;

import com.example.springdatajpa_intro.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

/*Extending the JPARepository will make springboot to pick this up*/
public interface BookRepository extends JpaRepository<Book, Long> {
}
