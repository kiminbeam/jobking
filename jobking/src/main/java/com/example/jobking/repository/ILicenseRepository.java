package com.example.jobking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jobking.entity.License;

public interface ILicenseRepository extends JpaRepository<License, Long> {

}
