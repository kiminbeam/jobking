package com.example.jobking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jobking.entity.SelfInfo;

public interface ISelfInfo extends JpaRepository<SelfInfo, Integer> {

	List<SelfInfo> findByUser_uid(String uid);
}
