package com.example.jobking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jobking.entity.Company;

public interface ICompanyRepository extends JpaRepository<Company,String> {

}
