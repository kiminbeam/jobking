package com.example.jobking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jobking.entity.CompanyBoard;

public interface ICompanyBoardRepository extends JpaRepository<CompanyBoard, Long> {

}