package com.example.demo;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFileChooser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class Startup {
	@Autowired
	private ObjectiveDatabase objective;
	@Autowired
	private SubjectiveDatabase subjective;
	@Autowired
	private PitDatabase pit;
	
	@Bean
	CommandLineRunner init() {
		return args -> {
			run();
		};
	}
	
	private void run() throws StreamReadException, DatabindException, IOException {
		Scanner scanner = new Scanner(System.in);
		System.out.print("directory holding all objective data: ");
		File file = new File(scanner.nextLine());
		
		objective.deleteAll();
		subjective.deleteAll();
		
		ObjectMapper mapper = new ObjectMapper();
		for(File f : file.listFiles()) {
			if(f.getName().endsWith(".json")) {
				ObjectiveData data = mapper.readValue(f, ObjectiveData.class);
				objective.save(data);
				
				SubjectiveData sub = new SubjectiveData();
				sub.setTeamNumber(data.getTeamNumber());
				sub.setMatchType(data.getMatchType());
				sub.setMatchNumber(data.getMatchNumber());
				sub.setDataQuality(data.getDataQuality());
				sub.setReplay(data.isReplay());
				sub.setAllianceColor(data.getAllianceColor());
				sub.setDriverStation(data.getDriverStation());
				sub.setScouterName(data.getScouterName());
				sub.setHpAtAmp(Math.random() > 0.5 ? true : false);
				
				List<String> pickups = new LinkedList<>();
				pickups.add("0");
				pickups.add("1");
				pickups.add("2");
				pickups.add("3");
				pickups.add("4");
				pickups.add("5");
				pickups.add("6");
				pickups.add("7");
				Collections.shuffle(pickups);
				pickups.remove(0);
				pickups.remove(0);
				pickups.remove(0);
				
				String str = Arrays.toString(pickups.toArray(new String[0]));
				str = str.substring(1, str.length()-1);
				
				sub.setAutoPickups(str);
				sub.setScouterName("john doe");
				sub.setCanScoreSub(Math.random() > 0.5 ? true : false);
				sub.setCanScorePodium(Math.random() > 0.5 ? true : false);
				sub.setCanScoreOther(Math.random() > 0.5 ? true : false);
				sub.setFeeder(data.isFeeder());
				sub.setHumanPlayerComments("asj;df lak;dfdkjsdflkjd");
				sub.setComments("jawuy6hijbgfkn sjdwurgsdbp8934irwed  j;ks asjdf  as;dflj k");
				sub.setCoopertition(data.isCoopertition());
				
				subjective.save(sub);
			}
		}
		
		System.out.print("directory holding all pit data: ");
		pit.deleteAll();
		File folder = new File(scanner.nextLine());
		for(File f : folder.listFiles()) {
			if(f.getName().endsWith(".json")) {
				PitData data = mapper.readValue(f, PitData.class);
				pit.save(data);
			}
		}
		
		System.out.println("Done!");
	}
}
