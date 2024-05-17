package com.example.jobking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jobking.entity.User;

public interface IUserRepository extends JpaRepository<User,String>  {

	
}
