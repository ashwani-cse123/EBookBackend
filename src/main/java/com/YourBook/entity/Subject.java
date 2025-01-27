package com.YourBook.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "\"subject\"")
public class Subject {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String subjectName;
	private String imagePath;
	private String discription;
	
//	@Column(name = "category_id",  insertable=false, updatable=false)
//	private Long categoryId;
	
	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "category_id", referencedColumnName = "id")
	private Category category;
	
	@OneToMany(mappedBy = "subject", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<Book> bookList;

	public Subject() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Subject(String subjectName, String imagePath, String discription, Category category, List<Book> bookList) {
		super();
		this.subjectName = subjectName;
		this.imagePath = imagePath;
		this.discription = discription;
		this.category = category;
		this.bookList = bookList;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getDiscription() {
		return discription;
	}

	public void setDiscription(String discription) {
		this.discription = discription;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<Book> getBookList() {
		return bookList;
	}

	public void setBookList(List<Book> bookList) {
		this.bookList = bookList;
	}
	
	

}
