package com.example.jobking.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.jobking.entity.Hope;
import com.example.jobking.entity.Resume;


public interface IHopeRepository extends JpaRepository<Hope, Long> {
	
	@Query(value="select * from hope where uid= :uid and rno= :rno " , nativeQuery=true)
	public Hope findByUidAndRno(@Param("uid") String uid, @Param("rno") Long rno);
	
}
