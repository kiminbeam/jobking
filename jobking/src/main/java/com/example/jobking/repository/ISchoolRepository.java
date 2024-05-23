package com.example.jobking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.jobking.entity.School;

public interface ISchoolRepository extends JpaRepository<School, Long>{
	
	List<School> findByResumeRno(Long rno);

	@Query(value="delete from school where sno = :sno" , nativeQuery=true)
	public void deleteSchoolBySno(@Param("sno") Long sno);
    
	void deleteByResumeRno(Long rno);
}
