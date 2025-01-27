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
import org.springframework.web.bind.annotation.RestController;

import com.YourBook.dto.SubjectDto;
import com.YourBook.entity.Subject;
import com.YourBook.service.SubjectService;

@RestController
@CrossOrigin("*")
@RequestMapping("api/v1")
public class SubjectController {
	@Autowired 
	private SubjectService subjectService;
	
	@PostMapping("/save-subject")
	public ResponseEntity<?> saveSubject(SubjectDto subjectDto) throws IllegalStateException, IOException{
		String s = subjectService.saveSubject(subjectDto);
		return ResponseEntity.ok(s);
	}
	
	@GetMapping("/get-subject")
	public ResponseEntity<?> getSubject(){
		List<Subject> subjects = subjectService.getSubject();
		return ResponseEntity.ok(subjects);
	}
	
	@DeleteMapping("/delete-subject/{id}")
	public ResponseEntity<?> deleteSubjectById(@PathVariable Long id) throws URISyntaxException{
		Subject subject = subjectService.deleteSubjectById(id);
		return ResponseEntity.ok(subject);
	}
	
	@PutMapping("/update-subject/{id}")
	public ResponseEntity<?> updateSubjectById(@PathVariable("id") Long id, SubjectDto subjectDto) throws IllegalStateException, IOException, URISyntaxException{
		String s = subjectService.updateSubjectById(id, subjectDto);
		return ResponseEntity.ok(s);
	}
	
	@GetMapping("/get-subject/{categoryId}")
	public ResponseEntity<?> getSubjectById(@PathVariable Long categoryId){
		List<Subject> subjects = subjectService.getSubjectByCategoryId(categoryId);
		return ResponseEntity.ok(subjects);
	}
}
