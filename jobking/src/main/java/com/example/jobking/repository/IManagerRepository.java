package com.example.jobking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jobking.entity.Manager;

public interface IManagerRepository extends JpaRepository<Manager, Integer> {

}
