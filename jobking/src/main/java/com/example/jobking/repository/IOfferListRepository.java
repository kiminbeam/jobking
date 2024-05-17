package com.example.jobking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jobking.entity.OfferList;

public interface IOfferListRepository extends JpaRepository<OfferList, Long> {

}
