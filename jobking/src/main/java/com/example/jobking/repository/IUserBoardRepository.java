package com.example.jobking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jobking.entity.UserBoard;

public interface IUserBoardRepository extends JpaRepository<UserBoard, Long> {

}
