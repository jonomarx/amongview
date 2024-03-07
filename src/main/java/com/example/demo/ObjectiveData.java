package com.example.demo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
public class ObjectiveData implements Serializable {
	/**
	 * internally stored id for SQL
	 */
	private @Id @GeneratedValue Long id;
	
	@JsonProperty("TeamNumber")
	private int teamNumber;
	
	@JsonProperty("MatchType")
	private String matchType;
	
	@JsonProperty("DataQuality")
	private int dataQuality;
	
	@JsonProperty("MatchNumber")
	private int matchNumber;
	
	@JsonProperty("Replay")
	private boolean replay;
	
	@JsonProperty("AllianceColor")
	private String allianceColor;
	
	@JsonProperty("DriverStation")
	private int driverStation;
	
	@JsonProperty("ScouterName")
	private String scouterName;
	
	@JsonProperty("Preload")
	private boolean preload;
	
	@JsonProperty("StartPos")
	private String startPos;
	
	@JsonProperty("LeftWing")
	private boolean leftWing;
	
	@JsonProperty("AutoSpeaker")
	private int autoSpeaker;
	
	@JsonProperty("AutoAmp")
	private int autoAmp;
	
	@JsonProperty("AutoPickUpWing")
	private int autoPickUpWing;
	
	@JsonProperty("AutoPickUpCenter")
	private int autoPickUpCenter;
	
	@JsonProperty("AStop")
	private boolean aStop;
	
	@JsonProperty("PickUpGround")
	private int pickUpGround;
	
	@JsonProperty("PickUpSource")
	private int pickUpSource;
	
	@JsonProperty("SpeakerNotesUnamped")
	private int speakerNotesUnamped;
	
	@JsonProperty("SpeakerNotesAmped")
	private int speakerNotesAmped;
	
	@JsonProperty("AmpNotes")
	private int ampNotes;
	
	@JsonProperty("Feeder")
	private boolean feeder;
	
	@JsonProperty("Coopertition")
	private boolean coopertition;
	
	@JsonProperty("Onstage")
	private boolean onstage;
	
	@JsonProperty("Park")
	private boolean park;
	
	@JsonProperty("Spotlight")
	private boolean spotlight;
	
	@JsonProperty("Trap")
	private boolean trap;
	
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

	public int getDataQuality() {
		return dataQuality;
	}

	public void setDataQuality(int dataQuality) {
		this.dataQuality = dataQuality;
	}

	public int getMatchNumber() {
		return matchNumber;
	}

	public void setMatchNumber(int matchNumber) {
		this.matchNumber = matchNumber;
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

	public boolean isPreload() {
		return preload;
	}

	public void setPreload(boolean preload) {
		this.preload = preload;
	}

	public String getStartPos() {
		return startPos;
	}

	public void setStartPos(String startPos) {
		this.startPos = startPos;
	}

	public boolean isLeftWing() {
		return leftWing;
	}

	public void setLeftWing(boolean leftWing) {
		this.leftWing = leftWing;
	}

	public int getAutoSpeaker() {
		return autoSpeaker;
	}

	public void setAutoSpeaker(int autoSpeaker) {
		this.autoSpeaker = autoSpeaker;
	}

	public int getAutoAmp() {
		return autoAmp;
	}

	public void setAutoAmp(int autoAmp) {
		this.autoAmp = autoAmp;
	}

	public int getAutoPickUpWing() {
		return autoPickUpWing;
	}

	public void setAutoPickUpWing(int autoPickUpWing) {
		this.autoPickUpWing = autoPickUpWing;
	}

	public int getAutoPickUpCenter() {
		return autoPickUpCenter;
	}

	public void setAutoPickUpCenter(int autoPickUpCenter) {
		this.autoPickUpCenter = autoPickUpCenter;
	}

	public boolean getaStop() {
		return aStop;
	}

	public void setaStop(boolean aStop) {
		this.aStop = aStop;
	}

	public int getPickUpGround() {
		return pickUpGround;
	}

	public void setPickUpGround(int pickupGround) {
		this.pickUpGround = pickupGround;
	}

	public int getPickUpSource() {
		return pickUpSource;
	}

	public void setPickUpSource(int pickupSource) {
		this.pickUpSource = pickupSource;
	}

	public int getSpeakerNotesUnamped() {
		return speakerNotesUnamped;
	}

	public void setSpeakerNotesUnamped(int speakerNotesUnamped) {
		this.speakerNotesUnamped = speakerNotesUnamped;
	}

	public int getSpeakerNotesAmped() {
		return speakerNotesAmped;
	}

	public void setSpeakerNotesAmped(int speakerNotesAmped) {
		this.speakerNotesAmped = speakerNotesAmped;
	}

	public int getAmpNotes() {
		return ampNotes;
	}

	public void setAmpNotes(int ampNotes) {
		this.ampNotes = ampNotes;
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

	public boolean isOnstage() {
		return onstage;
	}

	public void setOnstage(boolean onstage) {
		this.onstage = onstage;
	}

	public boolean isPark() {
		return park;
	}

	public void setPark(boolean park) {
		this.park = park;
	}

	public boolean isSpotlight() {
		return spotlight;
	}

	public void setSpotlight(boolean spotlight) {
		this.spotlight = spotlight;
	}

	public boolean isTrap() {
		return trap;
	}

	public void setTrap(boolean trap) {
		this.trap = trap;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
}
