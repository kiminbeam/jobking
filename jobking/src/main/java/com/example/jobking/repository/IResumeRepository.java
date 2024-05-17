package com.example.jobking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.jobking.entity.Resume;

public interface IResumeRepository extends JpaRepository<Resume, Long> {

	@Query(value="select * from resume where uid= :uid " , nativeQuery=true)
	public List<Resume> findByUid(@Param("uid") String uid);
}
