package com.YourBook.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import com.YourBook.dto.BookDto;
import com.YourBook.entity.Book;



public interface BookService {
	public String saveBook(BookDto bookDto) throws IllegalStateException, IOException;
	public List<Book> getBook();
	public List<Book> getBookBySubjectId(Long subjectId);
	public Book deleteBookById(Long id) throws URISyntaxException;
	public String updateBookById(Long id, BookDto bookDto) throws IllegalStateException, IOException, URISyntaxException;
}
