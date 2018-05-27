package com.dull.bird.email.tool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


//@Controller
@SpringBootApplication()
public class ToolApplication {

//	@GetMapping("/test")
//	public String test(){
//		return "upload";
//	}
	public static void main(String[] args) {
		SpringApplication.run(ToolApplication.class, args);
	}
}
