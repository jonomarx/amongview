package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PitDatabase extends JpaRepository<PitData, Long> {
	public PitData findByTeamNumber(int teamNumber);
}
