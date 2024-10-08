package com.example.demo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;
import javax.swing.JFileChooser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class Startup {
	@Autowired
	private ObjectiveDatabase objective;
	@Autowired
	private SubjectiveDatabase subjective;
	@Autowired
	private PitDatabase pit;
	
	@Autowired
	private Environment env;

	@Bean
	CommandLineRunner init() {
		return args -> {
			run();
		};
	}
	
	private void processData(File f) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			JsonNode node = mapper.readTree(f);
			if(node.get("Park") != null) {
				ObjectiveData data = mapper.readValue(f, ObjectiveData.class);
				
				if(objective.findByTeamNumberAndMatchNumberAndMatchType(node.get("TeamNumber").intValue(), node.get("MatchNumber").intValue(), node.get("MatchType").textValue()) == null)
				objective.save(data);
			} else if(node.get("HPAtAmp") != null) {
				SubjectiveData data = mapper.readValue(f, SubjectiveData.class);
				
				if(subjective.findByTeamNumberAndMatchNumberAndMatchType(node.get("TeamNumber").intValue(), node.get("MatchNumber").intValue(), node.get("MatchType").textValue()) == null)
				subjective.save(data);
			} else {
				PitData data = mapper.readValue(f, PitData.class);
				pit.save(data);
			}
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void run() throws StreamReadException, DatabindException, IOException {
		Config cfg = GetConfig();
		 // Blame jonathan for these names
		File file; // Objective Directory
		File file2; // Subjective Directory
		File folder; // Pit Directory
		String unformattedFilePath;
		Scanner scanner = new Scanner(System.in);
		
		if(env.getProperty("oPath") == null) {
			System.out.print("Objective Directory (blank for last used): ");
			unformattedFilePath = scanner.nextLine();
		} else {
			unformattedFilePath = env.getProperty("oPath");
		}
		if (unformattedFilePath.equals("")) {
			file = new File(cfg.getObjPath());
			System.out.println(cfg.getObjPath());
		} else {
			file = new File(unformattedFilePath.replaceAll("[\"]", "").trim());
			cfg.setObjPath(unformattedFilePath.replaceAll("[\"]","").trim());
		}

		objective.deleteAll();
		subjective.deleteAll();

		ObjectMapper mapper = new ObjectMapper();
		for (File f : file.listFiles()) {
			if (f.getName().endsWith(".json")) {
				processData(f);
			}
		}
		
		if(env.getProperty("sPath") == null) {
			System.out.print("Subjective Directory (blank for last used): ");
			unformattedFilePath = scanner.nextLine();
		} else {
			unformattedFilePath = env.getProperty("sPath");
		}
		if (unformattedFilePath.equals("")) {
			file2 = new File(cfg.getSubjPath());
			System.out.println(cfg.getSubjPath());
		} else {
			file2 = new File(unformattedFilePath.replaceAll("[\"]", "").trim());
			cfg.setSubjPath(unformattedFilePath.replaceAll("[\"]","").trim());
		}
		for (File f : file2.listFiles()) {
			if (f.getName().endsWith(".json")) {
				processData(f);
			}
		}
		
		pit.deleteAll();
		
		if(env.getProperty("pPath") == null) {
			System.out.print("Pit Directory (blank for last used): ");
			unformattedFilePath = scanner.nextLine();
		} else {
			unformattedFilePath = env.getProperty("pPath");
		}
		if (unformattedFilePath.equals("")) {
			folder = new File(cfg.getPitPath());
			System.out.println(cfg.getPitPath());
		} else {
			folder = new File(unformattedFilePath.replaceAll("[\"]", "").trim());
			cfg.setPitPath(unformattedFilePath.replaceAll("[\"]","").trim());
		}
		for (File f : folder.listFiles()) {
			if (f.getName().endsWith(".json")) {
				processData(f);
			}
		}
		scanner.close();

		SetConfig(cfg);
		System.out.println("Done!");
		System.out.println("Open up http://localhost:8080 in a browser to continue.");
	}

	public Config GetConfig() {
		Gson gson = new Gson();
		try (FileReader reader = new FileReader(System.getProperty("user.home") + "/.amongview/config.json")) {
			// Convert JSON file to Java object
			return gson.fromJson(reader, Config.class);

		} catch (IOException e) {
			return new Config();
		}
	}
	public void SetConfig(Config config) {
		Gson gson = new Gson();
		Path configFilePath = Paths.get(System.getProperty("user.home") + "/.amongview");
		if (!Files.exists(configFilePath)) {
			try {Files.createDirectories(configFilePath);} catch (IOException e) {}
		}
		try (FileWriter writer = new FileWriter(System.getProperty("user.home") + "/.amongview/config.json")) {
			// Convert Java object to JSON file
			gson.toJson(config, writer);

		} catch (IOException e) {
			
		}
	}

	public class Config {
		public String objPath;
		public String subjPath;
		public String pitPath;

		public String getObjPath() {
			return objPath;
		}

		public void setObjPath(String objPath) {
			this.objPath = objPath;
		}

		public String getSubjPath() {
			return subjPath;
		}

		public void setSubjPath(String subjPath) {
			this.subjPath = subjPath;
		}

		public String getPitPath() {
			return pitPath;
		}

		public void setPitPath(String pitPath) {
			this.pitPath = pitPath;
		}

	}
}
