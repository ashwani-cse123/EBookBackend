package com.YourBook.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import com.YourBook.dto.SubjectDto;
import com.YourBook.entity.Subject;

public interface SubjectService {
	public String saveSubject(SubjectDto subjectDto) throws IllegalStateException, IOException;
	public List<Subject> getSubject();
	public List<Subject> getSubjectByCategoryId(Long categoryId);
	public Subject deleteSubjectById(Long id) throws URISyntaxException;
	public String updateSubjectById(Long id, SubjectDto subjectDto) throws IllegalStateException, IOException, URISyntaxException;
}
