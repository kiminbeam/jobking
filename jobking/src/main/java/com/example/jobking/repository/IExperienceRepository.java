package com.example.jobking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.jobking.entity.Experience;

public interface IExperienceRepository extends JpaRepository<Experience, Long> {

	List<Experience> findByResumeRno(Long rno);
	
	@Query(value="delete from experience where eno = :eno" , nativeQuery=true)
	public void deleteExperienceByEno(@Param("eno") Long eno);
    
	void deleteByResumeRno(Long rno);
}
