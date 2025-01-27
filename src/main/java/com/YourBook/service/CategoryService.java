package com.YourBook.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import com.YourBook.dto.CategoryDto;
import com.YourBook.entity.Category;

public interface CategoryService {
	public String saveCategory(CategoryDto categoryDto) throws IllegalStateException, IOException;
	public List<Category> getcategory();
	public Category deleteCategoryById(Long id) throws URISyntaxException;
	public String updateCategoryById(Long id, CategoryDto categoryDto) throws IllegalStateException, IOException, URISyntaxException;
}
