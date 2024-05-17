package com.example.jobking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jobking.entity.CompanyReview;

public interface ICompanyReplyRepository extends JpaRepository<CompanyReview, Long> {

}
