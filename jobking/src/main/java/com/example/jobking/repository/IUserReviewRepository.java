package com.example.jobking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.jobking.entity.UserReview;

public interface IUserReviewRepository extends JpaRepository<UserReview, Long> {
	
	@Query(value="select * from user_review where cid = :cid", nativeQuery = true)
	Optional<UserReview> findUserReview(@Param("cid") String cid);
	
	//평균 구하는 쿼리
	@Query(value="select ((sum(q1) + sum(q2) + sum(q3))/3) / count(distinct cid) from company_review where uid= :uid", nativeQuery = true)
	Double findAverageByUid(@Param("uid") String uid);

}
