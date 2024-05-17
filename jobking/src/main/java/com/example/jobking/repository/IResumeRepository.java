package com.example.jobking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jobking.entity.Resume;

public interface IResumeRepository extends JpaRepository<Resume, Long> {

}
