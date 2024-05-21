package com.example.jobking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.jobking.entity.UserBoard;
import com.example.jobking.entity.UserReply;

public interface IUserReplyRepository extends JpaRepository<UserReply, Long> {

	@Query(value="Select * from user_reply where uid= :uid", nativeQuery=true)
	List<UserReply> findByUid(@Param("uid") String uid);
	
	@Query(value="delete from user_reply where ubno= :ubno", nativeQuery=true)
	void deleteAllByUbno(@Param("ubno") Long ubno);
	
	@Query(value="Select * from user_reply where ubno= :ubno", nativeQuery=true)
	List<UserReply> findAllByUbno(@Param("ubno") Long ubno);
}
