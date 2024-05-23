package com.example.jobking.test;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.jobking.entity.CompanyReply;
import com.example.jobking.repository.ICompanyReplyRepository;

@SpringBootTest
public class Test {
	
	@Autowired
	ICompanyReplyRepository comReplyRepos;
	
	@org.junit.jupiter.api.Test
	public void testquery() {
		String cid = "aaaa";
		List<CompanyReply> list = comReplyRepos.findAllByCid(cid);
		
		System.out.println(list);
	}
}
