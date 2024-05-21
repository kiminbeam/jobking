package com.example.jobking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.jobking.entity.InterviewList;

public interface IInterviewListRepository extends JpaRepository<InterviewList, Long> {
	
	@Query(value="select * from interview_list where cid= :cid", nativeQuery = true)
	Optional <InterviewList> findByCid(@Param("cid") String cid);
	
	@Query(value="update interview_list set interview = :interview where interviewno = :interviewno", nativeQuery = true)
	void updateInterview(@Param("interview") String interview, @Param("interviewno") Long interviewno);

	@Query(value="update interview_list set pass= :pass where interviewno = :interviewno", nativeQuery = true)
	void updatePass(@Param("pass") String pass, @Param("interviewno") Long interviewno);
}
