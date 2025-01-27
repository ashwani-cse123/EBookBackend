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

import com.YourBook.dto.SubjectDto;
import com.YourBook.entity.Category;
import com.YourBook.entity.Subject;
import com.YourBook.repository.CategoryRepository;
import com.YourBook.repository.SubjectRepository;
import com.YourBook.service.SubjectService;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;

import jakarta.persistence.EntityNotFoundException;

@Component
public class SubjectServiceImpl implements SubjectService{
	@Autowired 
	private SubjectRepository subjectRepository;
	
	@Autowired 
	private CategoryRepository categoryRepository;
	
	@Autowired
	private AmazonS3 amazonS3;
	
	@Value("${aws.s3.bucket.subject.name}")
	private String subjectImageFolder;
	
	@Value("${upload.directory}")
	private String folderPath;
	@Override
	public String saveSubject(SubjectDto subjectDto) throws IllegalStateException, IOException {
		
		String fileName = System.currentTimeMillis()+subjectDto.getImage().getOriginalFilename();
		amazonS3.putObject(subjectImageFolder, fileName, subjectDto.getImage().getInputStream(), new ObjectMetadata());
		String fileUrl = amazonS3.getUrl(subjectImageFolder, fileName).toString();
		
		Category category = categoryRepository.findByCategoryName(subjectDto.getCategoryName()).get();
		Subject subject = new Subject();
		
		subject.setSubjectName(subjectDto.getSubjectName());
		subject.setDiscription(subjectDto.getDiscription());
		subject.setCategory(category);
		subject.setImagePath(fileUrl);

		subjectRepository.save(subject);
		return "Subject save successfully in " + subjectDto.getCategoryName() + " category";
	}

	@Override
	public List<Subject> getSubject() {
		// TODO Auto-generated method stub
		return subjectRepository.findAll();
	}

	@Override
	public Subject deleteSubjectById(Long id) throws URISyntaxException {
		Optional<Subject> subject = subjectRepository.findById(id);
		if (subject.isPresent()) {
	        Subject subjectEntity = subject.get();
	        String imageUrl = subjectEntity.getImagePath();
	        String bucketName = subjectImageFolder;
	        URI uri = new URI(imageUrl);
	        String objectKey = uri.getPath().substring(1); // Remove leading slash
	        System.out.println(objectKey);

	        amazonS3.deleteObject(bucketName, objectKey);

	        // Delete the category from the database
	        subjectRepository.deleteById(id);

	        return subjectEntity;
	    } else {
	        throw new EntityNotFoundException("Subject not found with id: " + id);
	    }
	}

	@Override
	public String updateSubjectById(Long id, SubjectDto subjectDto) throws IllegalStateException, IOException, URISyntaxException {
		// TODO Auto-generated method stub
		Optional<Subject> subjectData = subjectRepository.findById(id);
		if(subjectData.isPresent()) {
			Subject subject = subjectData.get();
			if(subjectDto.getSubjectName() != null)
				subject.setSubjectName(subjectDto.getSubjectName());
			if(subjectDto.getDiscription() != null)
				subject.setDiscription(subjectDto.getDiscription());
			if(subjectDto.getImage() != null) {
				
				String imageUrl = subject.getImagePath();
		        String bucketName = subjectImageFolder;
		        URI uri = new URI(imageUrl);
		        String objectKey = uri.getPath().substring(1);
		        amazonS3.deleteObject(bucketName, objectKey);
	
				String fileName = System.currentTimeMillis()+subjectDto.getImage().getOriginalFilename();
				amazonS3.putObject(subjectImageFolder, fileName, subjectDto.getImage().getInputStream(), new ObjectMetadata());
		        String fileUrl = amazonS3.getUrl(subjectImageFolder, fileName).toString();
				System.out.println(fileUrl);
				subject.setImagePath(fileUrl);
			}
			subjectRepository.save(subject);
			return "Subject is upadated successfully!";
		}else {
			return "Subject is not Present...";
		}
		
	}

	@Override
	public List<Subject> getSubjectByCategoryId(Long categoryId) {
		// TODO Auto-generated method stub
		List<Subject> subjects =  subjectRepository.findByCategoryId(categoryId);
		return subjects;
	}

}
