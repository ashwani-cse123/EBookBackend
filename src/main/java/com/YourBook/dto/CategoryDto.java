package com.YourBook.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.YourBook.entity.Subject;

public class CategoryDto {
	private String categoryName;
	private MultipartFile imagePath;
	private String discription;
	
	private List<Subject> subjectList;

	public CategoryDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public MultipartFile getImagePath() {
		return imagePath;
	}

	public void setImagePath(MultipartFile imagePath) {
		this.imagePath = imagePath;
	}

	public String getDiscription() {
		return discription;
	}

	public void setDiscription(String discription) {
		this.discription = discription;
	}

	public List<Subject> getSubjectList() {
		return subjectList;
	}

	public void setSubjectList(List<Subject> subjectList) {
		this.subjectList = subjectList;
	}
	
	
}
