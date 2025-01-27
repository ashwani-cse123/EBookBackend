package com.YourBook.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.YourBook.dto.BookDto;
import com.YourBook.entity.Book;
import com.YourBook.service.BookService;

@RestController
@CrossOrigin("*")
@RequestMapping("api/v1")
public class BookController {
	@Autowired
	private BookService bookService;
	
	@PostMapping("/save-book")
	public ResponseEntity<?> saveBook(BookDto bookDto) throws IllegalStateException, IOException{
		String s = bookService.saveBook(bookDto);
		return ResponseEntity.ok(s);
	}
	
	@GetMapping("/get-book")
	public ResponseEntity<?> getBook(){
		List<Book> books = bookService.getBook();
		return ResponseEntity.ok(books);
	}
	
	@DeleteMapping("/delete-book/{id}")
	public ResponseEntity<?> deleteBookById(@PathVariable Long id) throws URISyntaxException{
		Book book = bookService.deleteBookById(id);
		return ResponseEntity.ok(book);
	}
	
	@PutMapping("/update-book/{id}")
	public ResponseEntity<?> updateBookById(@PathVariable("id") Long id, BookDto bookDto) throws IllegalStateException, IOException, URISyntaxException{
		String s = bookService.updateBookById(id, bookDto);
		return ResponseEntity.ok(s);
	}
	
	@GetMapping("/get-book/{subjectId}")
	public ResponseEntity<?> getBookBySubjectId(@PathVariable Long subjectId){
		List<Book> books = bookService.getBookBySubjectId(subjectId);
		return ResponseEntity.ok(books);
	}

}
