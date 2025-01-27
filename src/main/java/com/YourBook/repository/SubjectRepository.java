package com.YourBook.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.YourBook.entity.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Long>{
	Optional<Subject> findBySubjectName(String subjectName);
	List<Subject> findByCategoryId(Long categoryId);

}
