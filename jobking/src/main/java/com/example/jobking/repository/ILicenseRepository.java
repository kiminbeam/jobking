package com.example.jobking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.jobking.entity.License;

public interface ILicenseRepository extends JpaRepository<License, Long> {

	List<License> findByResumeRno(Long rno);
	
	@Query(value="delete from license where lno = :lno" , nativeQuery=true)
	public void deleteLicenseByLno(@Param("lno") Long lno);
    
	void deleteByResumeRno(Long rno);
}
