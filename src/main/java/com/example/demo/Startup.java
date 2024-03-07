package com.example.demo;

import java.io.File;
import java.io.IOException;
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
		scanner.close();
		
		objective.deleteAll();
		
		ObjectMapper mapper = new ObjectMapper();
		for(File f : file.listFiles()) {
			if(f.getName().endsWith(".json")) {
				ObjectiveData data = mapper.readValue(f, ObjectiveData.class);
				objective.save(data);
			}
		}
		System.out.println("Done!");
	}
}
