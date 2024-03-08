package com.example.demo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ObjectiveDatabase extends JpaRepository<ObjectiveData, Long> {
	public List<ObjectiveData> findAllByTeamNumberOrderByMatchNumberAsc(int teamNumber);
	public ObjectiveData findByTeamNumberAndMatchNumber(int teamNumber, int matchNumber);
	
	@Query("SELECT DISTINCT od.teamNumber FROM ObjectiveData od ORDER BY od.teamNumber ASC")
	public List<Integer> findAllTeams();
}
