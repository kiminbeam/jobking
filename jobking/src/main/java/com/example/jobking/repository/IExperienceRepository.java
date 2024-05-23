package com.example.jobking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jobking.entity.Experience;

public interface IExperienceRepository extends JpaRepository<Experience, Long> {

	List<Experience> findByResumeRno(Long rno);
	
	
}
