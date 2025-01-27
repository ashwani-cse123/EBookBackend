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

import com.YourBook.dto.CategoryDto;
import com.YourBook.entity.Book;
import com.YourBook.entity.Category;
import com.YourBook.entity.Subject;
import com.YourBook.repository.CategoryRepository;
import com.YourBook.service.CategoryService;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;

import jakarta.persistence.EntityNotFoundException;


@Component
public class CategoryServiceImpl implements CategoryService{
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private AmazonS3 amazonS3;
	
	@Value("${aws.s3.bucket.category.name}")
	private String categoryImageFolder;
	
	@Value("${upload.directory}")
	private String folderPath;
	
//	@Override
//	public String saveCategory(CategoryDto categoryDto) throws IllegalStateException, IOException {
//		// TODO Auto-generated method stub
//        String uploadDir = folderPath + File.separator + "categoryImage"; // Set a writable folder path
//        File directory = new File(uploadDir);
//        
//        // Make sure the directory exists or create it
//        if (!directory.exists()) {
//            directory.mkdirs();
//        }
//		
//		Category category = new Category();
//		String imagePath = uploadDir + File.separator + categoryDto.getImagePath().getOriginalFilename();
////		String path = folderPath+categoryDto.getImagePath().getOriginalFilename();
//		category.setCategoryName(categoryDto.getCategoryName());
//		category.setDiscription(categoryDto.getDiscription());
//		category.setImagePath("/categoryImage/" + categoryDto.getImagePath().getOriginalFilename()); 
////		category.setImagePath(imagePath);
//		categoryDto.getImagePath().transferTo(new File(imagePath));
//		
//		categoryRepository.save(category);
//		return "Category save successful!";
//	}
	@Override
	public String saveCategory(CategoryDto categoryDto) throws IllegalStateException, IOException {
		
		String fileName = System.currentTimeMillis()+categoryDto.getImagePath().getOriginalFilename();
        // Upload the file to S3
        amazonS3.putObject(categoryImageFolder, fileName, categoryDto.getImagePath().getInputStream(), new ObjectMetadata());

        // Generate presigned URL (use presigned URL for publicly accessible file)
        String fileUrl = amazonS3.getUrl(categoryImageFolder, fileName).toString();
		System.out.println(fileUrl);
		
		Category category = new Category();
		category.setCategoryName(categoryDto.getCategoryName());
		category.setDiscription(categoryDto.getDiscription());
		category.setImagePath(fileUrl);
		
		categoryRepository.save(category);
		return "Category save successful!";
	}

//	@Override
//	public Category deleteCategoryById(Long id) {
//		// TODO Auto-generated method stub
//		Optional<Category> category = categoryRepository.findById(id);
//		categoryRepository.deleteById(id);
//		return category.get();
//	}
	
	@Override
	public Category deleteCategoryById(Long id) throws URISyntaxException {
	    Optional<Category> category = categoryRepository.findById(id);
	    if (category.isPresent()) {
	        Category categoryEntity = category.get();
	        String imageUrl = categoryEntity.getImagePath();
	        String bucketName = categoryImageFolder;
	        URI uri = new URI(imageUrl);
	        String objectKey = uri.getPath().substring(1); // Remove leading slash
	        System.out.println(objectKey);

	        // Delete the image from S3
	        amazonS3.deleteObject(bucketName, objectKey);

	        // Delete the category from the database
	        categoryRepository.deleteById(id);

	        return categoryEntity;
	    } else {
	        throw new EntityNotFoundException("Category not found with id: " + id);
	    }
	}


	@Override
	public String updateCategoryById(Long id, CategoryDto categoryDto) throws IllegalStateException, IOException, URISyntaxException {
		// TODO Auto-generated method stub
		Optional<Category> categoryData = categoryRepository.findById(id);
		Category category = categoryData.get();
		if(categoryDto.getCategoryName() != null)
			category.setCategoryName(categoryDto.getCategoryName());
		if(categoryDto.getDiscription() != null)
			category.setDiscription(categoryDto.getDiscription());
		if(categoryDto.getImagePath() != null && !categoryDto.getImagePath().isEmpty()) {
//			String uploadDir = folderPath + File.separator + "categoryImage"; 
//			File directory = new File(uploadDir);
//	        if (!directory.exists()) {
//	            directory.mkdirs();
//	        }
//			String imagePath = uploadDir + File.separator + categoryDto.getImagePath().getOriginalFilename();
//			category.setImagePath("/categoryImage/" + categoryDto.getImagePath().getOriginalFilename());
//			categoryDto.getImagePath().transferTo(new File(imagePath));
			
			String imageUrl = category.getImagePath();
			String bucketName = categoryImageFolder;
	        URI uri = new URI(imageUrl);
	        String objectKey = uri.getPath().substring(1); // Remove leading slash
	        System.out.println(objectKey);

	        // Delete the image from S3
	        amazonS3.deleteObject(bucketName, objectKey);
			
			String fileName = System.currentTimeMillis()+categoryDto.getImagePath().getOriginalFilename();
			amazonS3.putObject(categoryImageFolder, fileName, categoryDto.getImagePath().getInputStream(), new ObjectMetadata());

	        // Generate presigned URL (use presigned URL for publicly accessible file)
	        String fileUrl = amazonS3.getUrl(categoryImageFolder, fileName).toString();
			System.out.println(fileUrl);
			category.setImagePath(fileUrl);
		}
		
		categoryRepository.save(category);
		return "Category update successful!";
	}

	@Override
	public List<Category> getcategory() {
		// TODO Auto-generated method stub
		List<Category> categories = categoryRepository.findAll();
		return categories;
	}

}
