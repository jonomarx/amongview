package com.example.demo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ObjectiveDatabase extends JpaRepository<ObjectiveData, Long> {
	public List<ObjectiveData> findAllByTeamNumberOrderByMatchNumberAsc(int teamNumber);
}
