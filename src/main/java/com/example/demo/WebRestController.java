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
	@Autowired
	SubjectiveDatabase subjective;
	@Autowired
	PitDatabase pit;
	
	@GetMapping(value="/obj", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> obj(@RequestParam("teamNum") int teamNum) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		String result = mapper.writeValueAsString(objective.findAllByTeamNumberOrderByMatchTypeDescMatchNumberAsc(teamNum));
		return ResponseEntity.of(Optional.of(result));
	}
	
	@GetMapping(value="/sub", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> sub(@RequestParam("teamNum") int teamNum) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		String result = mapper.writeValueAsString(subjective.findAllByTeamNumberOrderByMatchTypeDescMatchNumberAsc(teamNum));
		return ResponseEntity.of(Optional.of(result));
	}
	
	@GetMapping(value="/match",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> qual(@RequestParam("teamNum") int teamNum, @RequestParam("match") int match, @RequestParam("type") String type, @RequestParam("data") String dataType) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		if(dataType.equals("Objective")) {
			String result = mapper.writeValueAsString(objective.findByTeamNumberAndMatchNumberAndMatchType(teamNum, match, type));
			return ResponseEntity.of(Optional.of(result));
		} else if(dataType.equals("Subjective")) {
			String result = mapper.writeValueAsString(subjective.findByTeamNumberAndMatchNumberAndMatchType(teamNum, match, type));
			return ResponseEntity.of(Optional.of(result));
		} else {
			return ResponseEntity.of(Optional.of(null));
		}
	}
	
	@GetMapping(value="/pit", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> pit(@RequestParam("teamNum") int teamNum) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return ResponseEntity.of(Optional.of(mapper.writeValueAsString(pit.findByTeamNumber(teamNum))));
	}
	
	@GetMapping(value="/teams", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> teams() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		String result = mapper.writeValueAsString(objective.findAllTeams());
		return ResponseEntity.of(Optional.of(result));
	}
}
