package com.example.springdatajpa_intro.repository;

import com.example.springdatajpa_intro.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
