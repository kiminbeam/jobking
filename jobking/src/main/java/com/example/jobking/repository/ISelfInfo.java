package com.example.jobking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jobking.entity.SelfInfo;

public interface ISelfInfo extends JpaRepository<SelfInfo, Integer> {

}
