package com.example.demo;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class PitData {
	private @Id @GeneratedValue Long id;
	
	@JsonProperty("TeamName")
	private String teamName;
	
	@JsonProperty("TeamNumber")
	private int teamNumber;
	
	@JsonProperty("Interviewer")
	private String interviewer;
	
	@JsonProperty("Interviewee")
	private String interviewee;
	
	@JsonProperty("RobotWeight")
	private String robotWeight;
	
	@JsonProperty("Vision")
	private boolean vision;
	
	@JsonProperty("VisionCapability")
	@Column(columnDefinition="varchar(1024)")
	private String visionCapability;
	
	@JsonProperty("DriveTrain")
	@Column(columnDefinition="varchar(1024)")
	private String driveTrain;
	
	@JsonProperty("RobotMechanism")
	@Column(columnDefinition="varchar(1024)")
	private String robotMechanism;
	
	@JsonProperty("AutoPieces")
	private String autoPieces;
	
	@JsonProperty("ScoresAmp")
	private boolean scoresAmp;
	
	@JsonProperty("ScoresSpeaker")
	private boolean scoresSpeaker;
	
	@JsonProperty("LeaveWing")
	private boolean leaveWing;
	
	@JsonProperty("ScoringPreference")
	private String scoringPreference;
	
	@JsonProperty("CanScoreAmp")
	private boolean canScoreAmp;
	
	@JsonProperty("CanScoreSpeaker")
	private boolean canScoreSpeaker;
	
	@JsonProperty("CycleTimeSpeaker")
	private String cycleTimeSpeaker;
	
	@JsonProperty("CycleTimeAmp")
	private String cycleTimeAmp;
	
	@JsonProperty("PickupFromGround")
	private boolean pickupFromGround;
	
	@JsonProperty("PickupFromSource")
	private boolean pickupFromSource;
	
	@JsonProperty("DriverExperience")
	@Column(columnDefinition="varchar(1024)")
	private String driverExperience;
	
	@JsonProperty("TeamComments")
	@Column(columnDefinition="varchar(1024)")
	private String teamComments;
	
	@JsonProperty("PersonalComments")
	@Column(columnDefinition="varchar(1024)")
	private String personalComments;
	
	@JsonProperty("FitsUnderStage")
	private boolean fitsUnderStage;
	
	@JsonProperty("CanFeedPass")
	private boolean canFeedPass;
	
	@JsonProperty("CanDefend")
	private boolean canDefend;
	
	@JsonProperty("Strategy")
	@Column(columnDefinition="varchar(1024)")
	private String strategy;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the teamName
	 */
	public String getTeamName() {
		return teamName;
	}

	/**
	 * @param teamName the teamName to set
	 */
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	/**
	 * @return the teamNumber
	 */
	public int getTeamNumber() {
		return teamNumber;
	}

	/**
	 * @param teamNumber the teamNumber to set
	 */
	public void setTeamNumber(int teamNumber) {
		this.teamNumber = teamNumber;
	}

	/**
	 * @return the interviewer
	 */
	public String getInterviewer() {
		return interviewer;
	}

	/**
	 * @param interviewer the interviewer to set
	 */
	public void setInterviewer(String interviewer) {
		this.interviewer = interviewer;
	}

	/**
	 * @return the interviewee
	 */
	public String getInterviewee() {
		return interviewee;
	}

	/**
	 * @param interviewee the interviewee to set
	 */
	public void setInterviewee(String interviewee) {
		this.interviewee = interviewee;
	}

	/**
	 * @return the robotWeight
	 */
	public String getRobotWeight() {
		return robotWeight;
	}

	/**
	 * @param robotWeight the robotWeight to set
	 */
	public void setRobotWeight(String robotWeight) {
		this.robotWeight = robotWeight;
	}

	/**
	 * @return the vision
	 */
	public boolean isVision() {
		return vision;
	}

	/**
	 * @param vision the vision to set
	 */
	public void setVision(boolean vision) {
		this.vision = vision;
	}

	/**
	 * @return the visionCapability
	 */
	public String getVisionCapability() {
		return visionCapability;
	}

	/**
	 * @param visionCapability the visionCapability to set
	 */
	public void setVisionCapability(String visionCapability) {
		this.visionCapability = visionCapability;
	}

	/**
	 * @return the driveTrain
	 */
	public String getDriveTrain() {
		return driveTrain;
	}

	/**
	 * @param driveTrain the driveTrain to set
	 */
	public void setDriveTrain(String driveTrain) {
		this.driveTrain = driveTrain;
	}

	/**
	 * @return the robotMechanism
	 */
	public String getRobotMechanism() {
		return robotMechanism;
	}

	/**
	 * @param robotMechanism the robotMechanism to set
	 */
	public void setRobotMechanism(String robotMechanism) {
		this.robotMechanism = robotMechanism;
	}

	/**
	 * @return the autoPieces
	 */
	public String getAutoPieces() {
		return autoPieces;
	}

	/**
	 * @param autoPieces the autoPieces to set
	 */
	public void setAutoPieces(String autoPieces) {
		this.autoPieces = autoPieces;
	}

	/**
	 * @return the scoresAmp
	 */
	public boolean isScoresAmp() {
		return scoresAmp;
	}

	/**
	 * @param scoresAmp the scoresAmp to set
	 */
	public void setScoresAmp(boolean scoresAmp) {
		this.scoresAmp = scoresAmp;
	}

	/**
	 * @return the scoresSpeaker
	 */
	public boolean isScoresSpeaker() {
		return scoresSpeaker;
	}

	/**
	 * @param scoresSpeaker the scoresSpeaker to set
	 */
	public void setScoresSpeaker(boolean scoresSpeaker) {
		this.scoresSpeaker = scoresSpeaker;
	}

	/**
	 * @return the leaveWing
	 */
	public boolean isLeaveWing() {
		return leaveWing;
	}

	/**
	 * @param leaveWing the leaveWing to set
	 */
	public void setLeaveWing(boolean leaveWing) {
		this.leaveWing = leaveWing;
	}

	/**
	 * @return the scoringPreference
	 */
	public String getScoringPreference() {
		return scoringPreference;
	}

	/**
	 * @param scoringPreference the scoringPreference to set
	 */
	public void setScoringPreference(String scoringPreference) {
		this.scoringPreference = scoringPreference;
	}

	/**
	 * @return the canScoreAmp
	 */
	public boolean isCanScoreAmp() {
		return canScoreAmp;
	}

	/**
	 * @param canScoreAmp the canScoreAmp to set
	 */
	public void setCanScoreAmp(boolean canScoreAmp) {
		this.canScoreAmp = canScoreAmp;
	}

	/**
	 * @return the canScoreSpeaker
	 */
	public boolean isCanScoreSpeaker() {
		return canScoreSpeaker;
	}

	/**
	 * @param canScoreSpeaker the canScoreSpeaker to set
	 */
	public void setCanScoreSpeaker(boolean canScoreSpeaker) {
		this.canScoreSpeaker = canScoreSpeaker;
	}

	/**
	 * @return the cycleTimeSpeaker
	 */
	public String getCycleTimeSpeaker() {
		return cycleTimeSpeaker;
	}

	/**
	 * @param cycleTimeSpeaker the cycleTimeSpeaker to set
	 */
	public void setCycleTimeSpeaker(String cycleTimeSpeaker) {
		this.cycleTimeSpeaker = cycleTimeSpeaker;
	}

	/**
	 * @return the cycleTimeAmp
	 */
	public String getCycleTimeAmp() {
		return cycleTimeAmp;
	}

	/**
	 * @param cycleTimeAmp the cycleTimeAmp to set
	 */
	public void setCycleTimeAmp(String cycleTimeAmp) {
		this.cycleTimeAmp = cycleTimeAmp;
	}

	/**
	 * @return the pickupFromGround
	 */
	public boolean isPickupFromGround() {
		return pickupFromGround;
	}

	/**
	 * @param pickupFromGround the pickupFromGround to set
	 */
	public void setPickupFromGround(boolean pickupFromGround) {
		this.pickupFromGround = pickupFromGround;
	}

	/**
	 * @return the pickupFromSource
	 */
	public boolean isPickupFromSource() {
		return pickupFromSource;
	}

	/**
	 * @param pickupFromSource the pickupFromSource to set
	 */
	public void setPickupFromSource(boolean pickupFromSource) {
		this.pickupFromSource = pickupFromSource;
	}

	/**
	 * @return the driverExperience
	 */
	public String getDriverExperience() {
		return driverExperience;
	}

	/**
	 * @param driverExperience the driverExperience to set
	 */
	public void setDriverExperience(String driverExperience) {
		this.driverExperience = driverExperience;
	}

	/**
	 * @return the teamComments
	 */
	public String getTeamComments() {
		return teamComments;
	}

	/**
	 * @param teamComments the teamComments to set
	 */
	public void setTeamComments(String teamComments) {
		this.teamComments = teamComments;
	}

	/**
	 * @return the personalComments
	 */
	public String getPersonalComments() {
		return personalComments;
	}

	/**
	 * @param personalComments the personalComments to set
	 */
	public void setPersonalComments(String personalComments) {
		this.personalComments = personalComments;
	}
}
