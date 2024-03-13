package com.example.demo;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class SubjectiveData {
	private @Id @GeneratedValue Long id;
	
	@JsonProperty("TeamNumber")
	private int teamNumber;
	
	@JsonProperty("MatchType")
	private String matchType;
	
	@JsonProperty("MatchNumber")
	private int matchNumber;
	
	@JsonProperty("DataQuality")
	private int dataQuality;
	
	@JsonProperty("Replay")
	private boolean replay;
	
	@JsonProperty("AllianceColor")
	private String allianceColor;
	
	@JsonProperty("DriverStation")
	private int driverStation;
	
	@JsonProperty("ScouterName")
	private String scouterName;
	
	@JsonProperty("HPAtAmp")
	private boolean hpAtAmp;
	
	@JsonProperty("AutoPickups")
	private String autoPickups;
	
	@JsonProperty("CanScoreSub")
	private boolean canScoreSub;
	
	@JsonProperty("CanScorePodium")
	private boolean canScorePodium;
	
	@JsonProperty("CanScoreOther")
	private boolean canScoreOther;
	
	@JsonProperty("Feeder")
	private boolean feeder;
	
	@JsonProperty("Coopertition")
	private boolean coopertition;
	
	@JsonProperty("HumanPlayerComments")
	@Column(columnDefinition="varchar(512)")
	private String humanPlayerComments;
	
	@JsonProperty("Comments")
	@Column(columnDefinition="varchar(512)")
	private String comments;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getTeamNumber() {
		return teamNumber;
	}

	public void setTeamNumber(int teamNumber) {
		this.teamNumber = teamNumber;
	}

	public String getMatchType() {
		return matchType;
	}

	public void setMatchType(String matchType) {
		this.matchType = matchType;
	}

	public int getMatchNumber() {
		return matchNumber;
	}

	public void setMatchNumber(int matchNumber) {
		this.matchNumber = matchNumber;
	}

	public int getDataQuality() {
		return dataQuality;
	}

	public void setDataQuality(int dataQuality) {
		this.dataQuality = dataQuality;
	}

	public boolean isReplay() {
		return replay;
	}

	public void setReplay(boolean replay) {
		this.replay = replay;
	}

	public String getAllianceColor() {
		return allianceColor;
	}

	public void setAllianceColor(String allianceColor) {
		this.allianceColor = allianceColor;
	}

	public int getDriverStation() {
		return driverStation;
	}

	public void setDriverStation(int driverStation) {
		this.driverStation = driverStation;
	}

	public String getScouterName() {
		return scouterName;
	}

	public void setScouterName(String scouterName) {
		this.scouterName = scouterName;
	}

	public boolean isHpAtAmp() {
		return hpAtAmp;
	}

	public void setHpAtAmp(boolean hpAtAmp) {
		this.hpAtAmp = hpAtAmp;
	}

	public String getAutoPickups() {
		return autoPickups;
	}

	public void setAutoPickups(String autoPickups) {
		this.autoPickups = autoPickups;
	}

	public boolean isCanScoreSub() {
		return canScoreSub;
	}

	public void setCanScoreSub(boolean canScoreSub) {
		this.canScoreSub = canScoreSub;
	}

	public boolean isCanScorePodium() {
		return canScorePodium;
	}

	public void setCanScorePodium(boolean canScorePodium) {
		this.canScorePodium = canScorePodium;
	}

	public boolean isCanScoreOther() {
		return canScoreOther;
	}

	public void setCanScoreOther(boolean canScoreOther) {
		this.canScoreOther = canScoreOther;
	}

	public boolean isFeeder() {
		return feeder;
	}

	public void setFeeder(boolean feeder) {
		this.feeder = feeder;
	}

	public boolean isCoopertition() {
		return coopertition;
	}

	public void setCoopertition(boolean coopertition) {
		this.coopertition = coopertition;
	}

	public String getHumanPlayerComments() {
		return humanPlayerComments;
	}

	public void setHumanPlayerComments(String humanPlayerComments) {
		this.humanPlayerComments = humanPlayerComments;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
	
}
