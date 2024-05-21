package com.example.jobking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.jobking.entity.UserReview;

public interface IUserReviewRepository extends JpaRepository<UserReview, Long> {
	
	@Query(value="select * from user_review where cid = :cid", nativeQuery = true)
	Optional<UserReview> findUserReview(@Param("cid") String cid);

}
