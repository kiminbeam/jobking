package com.example.jobking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jobking.entity.Oa;

public interface IOaRepository extends JpaRepository<Oa, Long> {
	
	List<Oa> findByResumeRno(Long rno);
	
	void deleteByResumeRno(Long rno);
}
