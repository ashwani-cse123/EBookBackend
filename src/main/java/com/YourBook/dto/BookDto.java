package com.YourBook.dto;

import org.springframework.web.multipart.MultipartFile;

public class BookDto {
	
	private String bookName;
	private String bookPrice;
	private String discription;
	private String authorName;
	private MultipartFile bookUrl;
	private MultipartFile image;
	
	private String subjectName;
	
	public BookDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getBookPrice() {
		return bookPrice;
	}

	public void setBookPrice(String bookPrice) {
		this.bookPrice = bookPrice;
	}

	public String getDiscription() {
		return discription;
	}

	public void setDiscription(String discription) {
		this.discription = discription;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	

	public MultipartFile getBookUrl() {
		return bookUrl;
	}

	public void setBookUrl(MultipartFile bookUrl) {
		this.bookUrl = bookUrl;
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
	
	

}
