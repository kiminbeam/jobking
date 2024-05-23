package com.example.jobking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.jobking.entity.SelfInfo;

public interface ISelfInfo extends JpaRepository<SelfInfo, Long> {

	List<SelfInfo> findByUser_uid(String uid);
	
	List<SelfInfo> findByResumeRno(Long rno);
	
	@Query(value="delete from selfInfo where sino = :sino" , nativeQuery=true)
	public void deleteSelfInfoBySino(@Param("sino") Long sino);
	
	void deleteByResumeRno(Long rno);
}
