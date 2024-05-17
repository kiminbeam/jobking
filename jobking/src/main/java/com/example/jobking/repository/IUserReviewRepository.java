package com.example.jobking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jobking.entity.UserReview;

public interface IUserReviewRepository extends JpaRepository<UserReview, Long> {

}
