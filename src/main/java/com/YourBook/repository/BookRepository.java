package com.YourBook.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.YourBook.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long>{
	List<Book> findBySubjectId(Long subjectId);

}
