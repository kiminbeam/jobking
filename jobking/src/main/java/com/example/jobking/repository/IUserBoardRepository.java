package com.example.jobking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.jobking.entity.UserBoard;

public interface IUserBoardRepository extends JpaRepository<UserBoard, Long> {

	@Query(value="Select * from user_board where uid= :uid", nativeQuery=true)
	List<UserBoard> findByUid(@Param("uid") String uid);
}
