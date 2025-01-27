package com.YourBook.serviceimpl;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.YourBook.dto.BookDto;
import com.YourBook.entity.Book;
import com.YourBook.entity.Subject;
import com.YourBook.repository.BookRepository;
import com.YourBook.repository.SubjectRepository;
import com.YourBook.service.BookService;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;

import jakarta.persistence.EntityNotFoundException;

@Component
public class BookServiceImpl implements BookService{
	@Value("${upload.directory}")
	private String folderPath;
	@Autowired 
	private SubjectRepository subjectRepository;
	
	@Autowired 
	private BookRepository bookRepository;
	
	@Autowired
	private AmazonS3 amazonS3;
	
	@Value("${aws.s3.bucket.bookimage.name}")
	private String bookImageFolder;
	
	@Value("${aws.s3.bucket.bookpdf.name}")
	private String bookPdfFolder;

	@Override
	public String saveBook(BookDto bookDto) throws IllegalStateException, IOException {
		String bookImageName = System.currentTimeMillis()+bookDto.getImage().getOriginalFilename();
		String bookPdfName = System.currentTimeMillis()+bookDto.getBookUrl().getOriginalFilename();
		
		amazonS3.putObject(bookImageFolder, bookImageName, bookDto.getImage().getInputStream(), new ObjectMetadata());
		String bookImageUrl = amazonS3.getUrl(bookImageFolder, bookImageName).toString();
		
		amazonS3.putObject(bookPdfFolder, bookPdfName, bookDto.getBookUrl().getInputStream(), new ObjectMetadata());
		String bookPdfUrl = amazonS3.getUrl(bookPdfFolder, bookPdfName).toString();
		
		Subject subject = subjectRepository.findBySubjectName(bookDto.getSubjectName()).get();
		Book book = new Book();
		book.setBookName(bookDto.getBookName());
		book.setAuthorName(bookDto.getAuthorName());
		book.setBookPrice(bookDto.getBookPrice());
		book.setDiscription(bookDto.getDiscription());
		book.setSubject(subject);
		book.setImageUrl(bookImageUrl);
		book.setBookUrl(bookPdfUrl);
		
		bookRepository.save(book);
		return "Book save successfully in " + bookDto.getSubjectName() + " subject";
	}

	@Override
	public List<Book> getBook() {
		// TODO Auto-generated method stub
		return bookRepository.findAll();
	}

	@Override
	public Book deleteBookById(Long id) throws URISyntaxException {
		Optional<Book> book = bookRepository.findById(id);
		if (book.isPresent()) {
	        Book bookEntity = book.get();

	        String imageUrl = bookEntity.getImageUrl();
	        String pdfUrl = bookEntity.getBookUrl();

	        // Extract bucket name and object key from the URL
	        String imageBucketName = bookImageFolder;
	        URI imageUri = new URI(imageUrl);
	        String imageObjectKey = imageUri.getPath().substring(1); // Remove leading slash
	        amazonS3.deleteObject(imageBucketName, imageObjectKey);
	        
	     // Extract bucket name and object key from the URL
	        String pdfBucketName = bookPdfFolder;
	        URI pdfUri = new URI(pdfUrl);
	        String pdfObjectKey = pdfUri.getPath().substring(1); // Remove leading slash
	        amazonS3.deleteObject(pdfBucketName, pdfObjectKey);

	        // Delete the category from the database
	        subjectRepository.deleteById(id);

	        return bookEntity;
	    } else {
	        throw new EntityNotFoundException("Book not found with id: " + id);
	    }
	}

	@Override
	public String updateBookById(Long id, BookDto bookDto) throws IllegalStateException, IOException, URISyntaxException {
		// TODO Auto-generated method stub
		Optional<Book> bookData = bookRepository.findById(id);
		Book book = bookData.get();
		if(bookDto.getBookName() != null)
			book.setBookName(bookDto.getBookName());
		if(bookDto.getAuthorName() != null)
			book.setAuthorName(bookDto.getAuthorName());
		if(bookDto.getBookPrice() != null)
			book.setBookPrice(bookDto.getBookPrice());
		if(bookDto.getDiscription() != null)
			book.setDiscription(bookDto.getDiscription());
		if(bookDto.getImage() != null) {
			String imageUrl = book.getImageUrl();
	        String imageBucketName = bookImageFolder;
	        URI imageUri = new URI(imageUrl);
	        String imageObjectKey = imageUri.getPath().substring(1); // Remove leading slash
	        amazonS3.deleteObject(imageBucketName, imageObjectKey);
			
			String imageFileName = System.currentTimeMillis()+bookDto.getImage().getOriginalFilename();
			amazonS3.putObject(bookImageFolder, imageFileName, bookDto.getImage().getInputStream(), new ObjectMetadata());

	        String imageFileUrl = amazonS3.getUrl(bookImageFolder, imageFileName).toString();
			System.out.println(imageFileUrl);
			book.setImageUrl(imageFileUrl);
		}
		if(bookDto.getBookUrl() != null) {
			String pdfUrl = book.getBookUrl();
			
		    String pdfBucketName = bookPdfFolder;
	        URI pdfUri = new URI(pdfUrl);
	        String pdfObjectKey = pdfUri.getPath().substring(1); // Remove leading slash
	        amazonS3.deleteObject(pdfBucketName, pdfObjectKey);
			
			String pdfFileName = System.currentTimeMillis()+bookDto.getBookUrl().getOriginalFilename();
			amazonS3.putObject(bookImageFolder, pdfFileName, bookDto.getBookUrl().getInputStream(), new ObjectMetadata());

	        String pdfFileUrl = amazonS3.getUrl(bookImageFolder, pdfFileName).toString();
			System.out.println(pdfFileUrl);
			book.setBookUrl(pdfFileUrl);
		}
		bookRepository.save(book);
		return "Book is upadated successfully!";
	}

	@Override
	public List<Book> getBookBySubjectId(Long subjectId) {
		// TODO Auto-generated method stub
		List<Book> books = bookRepository.findBySubjectId(subjectId);
		return books;
	}
	

}
