package com.example.jobking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.jobking.entity.Career;
import com.example.jobking.entity.Resume;

public interface ICareerRepository extends JpaRepository<Career, Long>{

	// 이력서를 기준으로 경력 목록 조회
    List<Career> findByResume(Resume resume);
	
    List<Career> findByResumeRno(Long rno);
    
    @Query(value="delete from career where cno = :cno" , nativeQuery=true)
	public void deleteCareerByCno(@Param("cno") Long cno);
    
    
}
