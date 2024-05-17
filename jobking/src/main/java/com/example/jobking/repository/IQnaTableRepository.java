package com.example.jobking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jobking.entity.QnaTable;

public interface IQnaTableRepository extends JpaRepository<QnaTable, Long> {

}
