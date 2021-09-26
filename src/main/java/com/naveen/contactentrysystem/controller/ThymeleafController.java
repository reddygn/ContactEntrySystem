package com.naveen.contactentrysystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ThymeleafController {

	@RequestMapping(value = "/home")
	public String home() {
		return "home";
	}
}
