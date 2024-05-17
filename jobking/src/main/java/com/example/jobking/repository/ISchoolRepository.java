package com.example.jobking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jobking.entity.School;

public interface ISchoolRepository extends JpaRepository<School, Long>{

}
