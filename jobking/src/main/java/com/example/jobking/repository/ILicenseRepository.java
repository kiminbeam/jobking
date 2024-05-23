package com.example.jobking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jobking.entity.License;

public interface ILicenseRepository extends JpaRepository<License, Long> {

	List<License> findByResumeRno(Long rno);
	
	void deleteByResumeRno(Long rno);
}
