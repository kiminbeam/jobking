package com.example.jobking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jobking.entity.UserReply;

public interface IUserReplyRepository extends JpaRepository<UserReply, Long> {

}
