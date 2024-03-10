package com.example.demo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ObjectiveDatabase extends JpaRepository<ObjectiveData, Long> {
	public List<ObjectiveData> findAllByTeamNumberOrderByMatchTypeDescMatchNumberAsc(int teamNumber);
	public ObjectiveData findByTeamNumberAndMatchNumberAndMatchType(int teamNumber, int matchNumber, String matchType);
	
	@Query("SELECT DISTINCT od.teamNumber FROM ObjectiveData od ORDER BY od.teamNumber ASC")
	public List<Integer> findAllTeams();
}
