package com.example.jobking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.jobking.entity.CompanyReply;
import com.example.jobking.repository.ICompanyReplyRepository;

@Controller
public class TestController {

	@Autowired
	ICompanyReplyRepository r;
	
	@RequestMapping("/testtest")
	public void test() {
		List<CompanyReply> list = r.findAllByCid("aaaa");
		System.out.println(list);
	}
	
	
}
