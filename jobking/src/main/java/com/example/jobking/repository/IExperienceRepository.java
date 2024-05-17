package com.example.jobking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jobking.entity.Experience;

public interface IExperienceRepository extends JpaRepository<Experience, Long> {

}
