package com.YourBook.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.YourBook.entity.Book;

public class SubjectDto {
	private String categoryName;
	private String subjectName;
	private MultipartFile image;
	private String discription;
	
	private List<Book> bookList;

	public SubjectDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}



	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public MultipartFile getImage() {
		return image;
	}

	public void setImage(MultipartFile image) {
		this.image = image;
	}

	public String getDiscription() {
		return discription;
	}

	public void setDiscription(String discription) {
		this.discription = discription;
	}

	public List<Book> getBookList() {
		return bookList;
	}

	public void setBookList(List<Book> bookList) {
		this.bookList = bookList;
	}
	
	
}
