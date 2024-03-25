package com.example.demo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import jakarta.transaction.Transactional;

public interface SubjectiveDatabase extends JpaRepository<SubjectiveData, Long> {
	public List<SubjectiveData> findAllByTeamNumberOrderByMatchTypeDescMatchNumberAsc(int teamNumber);
	public SubjectiveData findByTeamNumberAndMatchNumberAndMatchType(int teamNumber, int matchNumber, String matchType);
	
	@Query("SELECT DISTINCT od.teamNumber FROM ObjectiveData od ORDER BY od.teamNumber ASC")
	public List<Integer> findAllTeams();
}
