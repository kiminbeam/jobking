package com.example.jobking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jobking.entity.Career;

public interface ICareerRepository extends JpaRepository<Career, Long>{

}
