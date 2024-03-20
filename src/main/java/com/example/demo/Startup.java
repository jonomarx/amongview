package com.example.demo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
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
		File file = new File(scanner.nextLine().replaceAll("[\"]", ""));
		
		objective.deleteAll();
		subjective.deleteAll();
		
		ObjectMapper mapper = new ObjectMapper();
		for(File f : file.listFiles()) {
			if(f.getName().endsWith(".json")) {
				ObjectiveData data = mapper.readValue(f, ObjectiveData.class);
				objective.save(data);
			}
		}
		
		System.out.print("directory holding all subjective data: ");
		File file2 = new File(scanner.nextLine().replaceAll("[\"]", ""));
		for(File f : file2.listFiles()) {
			if(f.getName().endsWith(".json")) {
				SubjectiveData data = mapper.readValue(f, SubjectiveData.class);
				subjective.save(data);
			}
		}
		
		System.out.print("directory holding all pit data: ");
		pit.deleteAll();
		File folder = new File(scanner.nextLine().replaceAll("[\"]", ""));
		for(File f : folder.listFiles()) {
			if(f.getName().endsWith(".json")) {
				PitData data = mapper.readValue(f, PitData.class);
				pit.save(data);
			}
		}
		
		System.out.println("Done!");
	}
}
