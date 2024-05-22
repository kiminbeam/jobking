package com.example.jobking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.jobking.entity.Hope;


public interface IHopeRepository extends JpaRepository<Hope, Long> {

	
}
