package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class WebController {
	/*private ObjectiveRepository objective;
	private SubjectiveRepository subjective;
	private PitsRepository pits;*/
	
	@GetMapping("/")
	public String index(Model model) {
		return "index.html";
	}
	
	@GetMapping("/Chart.js") 
	public String chart() {
		return "Chart.js";
	}
}
