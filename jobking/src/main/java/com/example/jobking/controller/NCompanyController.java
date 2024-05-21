package com.example.jobking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.jobking.repository.IApplyListRepository;
import com.example.jobking.repository.ICompanyRepository;
import com.example.jobking.repository.IJobAdRepository;

@RequestMapping("/company")
@Controller
public class NCompanyController {

	
	@Autowired
	IJobAdRepository repository;

	@Autowired
	ICompanyRepository comrepository;

	
	
	
}
