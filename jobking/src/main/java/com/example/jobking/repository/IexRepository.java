package com.example.jobking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jobking.entity.User;

public interface IexRepository extends JpaRepository<User, Long>{

}
