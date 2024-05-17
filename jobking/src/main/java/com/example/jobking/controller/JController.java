package com.example.jobking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class JController {

	
	@RequestMapping("/")
	public String root() {
		return "index";
	}
}
