package com.example.demo;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class WebRestController {
	@Autowired
	ObjectiveDatabase objective;
	
	@GetMapping("/csv")
	public String csv() {
		return "";
	}
	
	@GetMapping("/json")
	public String json() throws JsonProcessingException {
		List<ObjectiveData> data = objective.findAll();
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(data);
	}
	
	@PostMapping(value="/sql",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> sql(@RequestParam("teamNum") int teamNum) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		String result = mapper.writeValueAsString(objective.findAllByTeamNumberOrderByMatchNumberAsc(teamNum));
		return ResponseEntity.of(Optional.of(result));
	}
	
	@PostMapping("/sqlRaw")
	public String sqlRaw(@RequestParam String cmd) {
		return "";
	}
}
