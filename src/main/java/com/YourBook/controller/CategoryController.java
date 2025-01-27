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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import com.YourBook.dto.CategoryDto;
import com.YourBook.entity.Category;
import com.YourBook.service.CategoryService;

@RestController
@CrossOrigin("*")
@RequestMapping("api/v1")
public class CategoryController {
	@Autowired
	private CategoryService categoryService;
	
	@PostMapping("/add-category")
	public ResponseEntity<?> addCategory(CategoryDto categoryDto) throws IllegalStateException, IOException{
		String s = categoryService.saveCategory(categoryDto);
		return ResponseEntity.ok(s);
	}

	@GetMapping("/get-category")
	public ResponseEntity<?> getCategory(){
		List<Category> categories = categoryService.getcategory();
		return ResponseEntity.ok(categories);
	}
	
	@DeleteMapping("/delete-category/{id}")
	public ResponseEntity<?> deleteCategoryById(@PathVariable Long id) throws URISyntaxException{
		Category category = categoryService.deleteCategoryById(id);
		return ResponseEntity.ok(category);
	}
	
	@PutMapping("/update-category/{id}")
	public ResponseEntity<?> updateCategoryById(@PathVariable Long id, CategoryDto categoryDto) throws IllegalStateException, IOException, URISyntaxException{
		String s = categoryService.updateCategoryById(id, categoryDto);
		return ResponseEntity.ok(s);
	}
}
