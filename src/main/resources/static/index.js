let individualChart = null;
let allChart = null;

function openTab(evt, tabname) {
	tabcontent = document.getElementsByClassName("tabcontent");
	for(i = 0; i < tabcontent.length; i++) {
		tabcontent[i].style.display = "none";
	}
	
	tablinks = document.getElementsByClassName("tablinks");
	for(i = 0; i < tablinks.length; i++) {
		tablinks[i].className = tablinks[i].classname.replaceAll(' active', "");
		console.log(tablinks[i].className);
	}
	
	document.getElementById(tabname).style.display = "block";
	evt.currentTarget.className += " active";
}

let dataFuncs = {};
dataFuncs["AutoSpeaker"] = function(item) {return item.AutoSpeaker};
dataFuncs["AutoAmp"] = function(item) {return item.AutoAmp};
dataFuncs["AutoScores"] = function(item) {return item.AutoSpeaker + item.AutoAmp};
dataFuncs["AutoWing"] = function(item) {return item.AutoPickUpWing};
dataFuncs["AutoCenter"] = function(item) {return item.AutoPickUpCenter};
dataFuncs["AutoPickups"] = function(item) {return item.AutoPickUpWing + item.AutoPickUpCenter};
dataFuncs["AutoOuttakeEfficiency"] = function(item) {return (item.AutoSpeaker+item.AutoAmp) / (item.AutoPickUpWing+item.AutoPickUpCenter+1)};

dataFuncs["TeleSpeakerAmped"] = function(item) {return item.SpeakerNotesAmped};
dataFuncs["TeleSpeakerUnamped"] = function(item) {return item.SpeakerNotesUnamped};
dataFuncs["TeleSpeaker"] = function(item) {return item.SpeakerNotesAmped + item.SpeakerNotesUnamped};
dataFuncs["TeleAmp"] = function(item) {return item.AmpNotes};
dataFuncs["TeleScores"] = function(item) {return item.SpeakerNotesAmped + item.SpeakerNotesUnamped + item.AmpNotes};
dataFuncs["TeleGround"] = function(item) {return item.PickUpGround};
dataFuncs["TeleSource"] = function(item) {return item.PickUpSource};
dataFuncs["TelePickups"] = function(item) {return item.PickUpGround + item.PickUpSource};
dataFuncs["TeleOuttakeEfficiency"] = function(item) {return (item.SpeakerNotesAmped + item.SpeakerNotesUnamped + item.AmpNotes) / (item.PickUpGround + item.PickUpSource)};

dataFuncs["EndOnstage"] = function(item) {return (item.Onstage ? 1 : 0)};
dataFuncs["EndPark"] = function(item) {return (item.Park ? 1 : 0)};
dataFuncs["EndTrap"] = function(item) {return (item.Trap ? 1 : 0)};
dataFuncs["EndSpotlight"] = function(item) {return (item.Spotlight ? 1 : 0)};
dataFuncs["EndStatus"] = function(item) {
	if(item.Onstage) return 2;
	if(item.Park) return 1;
	return 0;
};

dataFuncs["AutoPoints"] = function(item) {
	return (item.LeftWing ? 2 : 0) + item.AutoAmp * 2 + item.AutoSpeaker * 5;
};
dataFuncs["TelePoints"] = function(item) {
	return item.SpeakerNotesUnamped * 2 + item.SpeakerNotesAmped * 5 + item.AmpNotes;
}
dataFuncs["TelePointsUnamped"] = function(item) {
	return item.SpeakerNotesUnamped * 2 + item.SpeakerNotesAmped * 2 + item.AmpNotes;
}
dataFuncs["EndPoints"] = function(item) {
	return item.Park + (item.Onstage ? 1 : 0) * (item.Spotlight ? 4 : 3) + item.Trap * 5;
}

dataFuncs["TotalPoints"] = function(item) {
	return dataFuncs["AutoPoints"](item) + dataFuncs["TelePoints"](item) + dataFuncs["EndPoints"](item);
}


let autoNoteBlue = {};
autoNoteBlue["0"] = {x:20,y:28};
autoNoteBlue["1"] = {x:20,y:97};
autoNoteBlue["2"] = {x:20,y:165};
autoNoteBlue["3"] = {x:20,y:235};
autoNoteBlue["4"] = {x:20,y:304};
autoNoteBlue["5"] = {x:239,y:47};
autoNoteBlue["6"] = {x:239,y:106};
autoNoteBlue["7"] = {x:239,y:165};

let autoNoteRed = {};
autoNoteRed["0"] = {x:336,y:28};
autoNoteRed["1"] = {x:336,y:97};
autoNoteRed["2"] = {x:336,y:165};
autoNoteRed["3"] = {x:336,y:235};
autoNoteRed["4"] = {x:336,y:304};
autoNoteRed["5"] = {x:116,y:47};
autoNoteRed["6"] = {x:116,y:106};
autoNoteRed["7"] = {x:116,y:165};

async function initAll(operation, sort) {
	if(dataFuncs[operation] == null) return;
	const response = await fetch("/teams", {
		method: 'GET',
		headers: {
			'Content-Type': 'application/x-www-form-urlencoded',
		},
		credentials: 'include',
	});
	var text = await response.json();
	
	if(allChart == null) {
		allChart = new Chart("allChart", {
			type: "bar",
			options: {
				onClick: clickOnAllChart
			}
		});
	}
	
	data = [];
	color = "rgba("+Math.random()*255+","+Math.random()*255+","+Math.random()*255+")";
	
	for(i = 0; i < text.length; i++) {
		team = text[i];
		params = new URLSearchParams();
		params.append("teamNum", team);
		const reply = await fetch("/obj?"+params.toString(), {
			method: 'GET',
			headers: {
				'Content-Type': 'application/x-www-form-urlencoded',
			},
			credentials: 'include',
		});
		teamData = await reply.json();
		
		total = 0;
		zeroes = 0;
		teamData.forEach(item => {
			num = dataFuncs[operation](item);
			total += num;
			if(num == 0 && document.getElementById("no0s").checked) zeroes++;
		});
		
		average = total / (teamData.length - zeroes);
		data[i] = {x:team,y:average};
	}
	
	allChart.data.labels = text;
	
	if(sort) {
		altLabels = [];
		data.sort((a,b) => {
			return b.y - a.y;
		});
		for(i = 0; i < data.length; i++) altLabels[i] = data[i].x;
		allChart.data.labels = altLabels;
	}
	
	if(document.getElementById("no0s").checked) operation += " (No 0s)";
	
	allChart.data.datasets[allChart.data.datasets.length] = {
		pointRadius: 0,
		backgroundColor: color,
		data: data,
		label: "Average " + operation,
		order: 1,
	};
	allChart.update();
}

async function updateIndividual(teamNum, operation) {
	if(dataFuncs[operation] == null) return;
	const params = new URLSearchParams();
	params.append("teamNum", teamNum);
	const response = await fetch("/obj?"+params.toString(), {
		method: 'GET',
		headers: {
			'Content-Type': 'application/x-www-form-urlencoded',
		},
		credentials: 'include',
	});
	var text = await response.json();
	
	data = [];
	x = [];
	var i = 0;
	
	total = 0;
	text.forEach(item => {
		letter = item.MatchType.substring(0,1);
		data[i] = {x:letter+item.MatchNumber,y:dataFuncs[operation](item)};
		x[i] = letter+item.MatchNumber;
		total += dataFuncs[operation](item);
		i++;
	});
	
	average = total / data.length;
	avg = [];
	for(i = 0; i < data.length; i++) {
		avg[i] = average;
	}
	
	if(individualChart == null) {
		individualChart = new Chart("individualChart", {
			type: "bar",
			options: {
				onClick: clickOnIndividualChart,
			},
		});
	}
	
	color = "rgba("+Math.random()*255+","+Math.random()*255+","+Math.random()*255+")";
	individualChart.data.datasets[individualChart.data.datasets.length] = {
		pointRadius: 4,
		/*pointBackgroundColor: color,
		borderColor: color,*/
		backgroundColor: color,
		data: data,
		label: teamNum + " " + operation,
		order: 2
	};
	individualChart.data.datasets[individualChart.data.datasets.length] = {
		pointRadius: 0,
		data: avg,
		pointBackgroundColor: color,
		borderColor: color,
		label: teamNum + " " + operation + " average",
		type: "line",
		order: 1
	}
	
	individualChart.data.labels = x;
	individualChart.update();

	const params2 = new URLSearchParams();
	params2.append("teamNum", teamNum);
	const response2 = await fetch("/sub?"+params2.toString(), {
		method: 'GET',
		headers: {
			'Content-Type': 'application/x-www-form-urlencoded',
		},
		credentials: 'include',
	});
	var text2 = await response2.json();

	subTotal = 0;
	podiumTotal = 0;
	otherTotal = 0;
	
	document.getElementById("commentsPaste1").replaceChildren();
	text.forEach(item => {
		theDiv = document.createElement("div");
		theMatch = document.createElement("div");
		theComments = document.createElement("div");
		
		theDiv.classList.add("bloc");
		theMatch.classList.add("column");
		theComments.classList.add("column");
		
		matchData = document.createElement("h3");
		matchData.textContent = item.MatchType.charAt(0) + item.MatchNumber;
		theMatch.appendChild(matchData);
		
		commentData = document.createElement("p");
		commentData.textContent = item.Comments;
		theComments.appendChild(commentData);
		
		theDiv.appendChild(theMatch);
		theDiv.appendChild(theComments);
		
		document.getElementById("commentsPaste1").appendChild(theDiv);
	});
	
	
	document.getElementById("commentsPaste2").replaceChildren();
	text2.forEach(item => {
		if(item.CanScoreSub) subTotal++;
		if(item.CanScorePodium) podiumTotal++;
		if(item.CanScoreOther) otherTotal++;
		
		theDiv = document.createElement("div");
		theMatch = document.createElement("div");
		theComments = document.createElement("div");
		
		theDiv.classList.add("bloc");
		theMatch.classList.add("column");
		theComments.classList.add("column");
		
		matchData = document.createElement("h3");
		matchData.textContent = item.MatchType.charAt(0) + item.MatchNumber;
		theMatch.appendChild(matchData);
		
		commentData = document.createElement("p");
		commentData.textContent = item.Comments;
		theComments.appendChild(commentData);
		
		theDiv.appendChild(theMatch);
		theDiv.appendChild(theComments);
		
		document.getElementById("commentsPaste2").appendChild(theDiv);
	});

	document.getElementById("avgSubwoofer").textContent = "Subwoofer %: " + Math.round(subTotal / text2.length * 100);
	document.getElementById("avgPodium").textContent = "Podium %: " + Math.round(podiumTotal / text2.length * 100);
	document.getElementById("avgOther").textContent = "Other %: " + Math.round(otherTotal / text2.length * 100);
	
	const params3 = new URLSearchParams();
	params3.append("teamNum", teamNum);
	const response3 = await fetch("/pit?" + params3.toString(), {
		method: 'GET',
		headers: {
			'Content-Type': 'application/x-www-form-urlencoded',
		},
		credentials: 'include',
	});
	
	pit = await response3.json();
	
	if(pit != null) {
		document.getElementById("pitDiv").style.display = "";
		
		document.getElementById("pitTeam").textContent = "Team: " + pit.TeamName;
		document.getElementById("pitNumber").textContent = pit.TeamNumber;
		document.getElementById("pitInterviewer").textContent = "Interviewer: " + pit.Interviewer;
		document.getElementById("pitInterviewee").textContent = "Interviewee: " + pit.Interviewee;
		document.getElementById("pitRHeight").textContent = "Robot Height: " + pit.RobotHeight;
		document.getElementById("pitRLW").textContent = "Robot Dims: " + pit.RobotLengthWidth;
		document.getElementById("pitRW").textContent = "Robot Weight: " + pit.RobotWeight;
		
		document.getElementById("pitVision").textContent = "Can Vision: " + pit.Vision;
		document.getElementById("pitVComments").textContent = "Vision comments: " + pit.VisionCapability;
		document.getElementById("pitTrain").textContent = "Drive Train: " + pit.DriveTrain;
		document.getElementById("pitMech").textContent = "Mechanisms: " + pit.RobotMechanism;
		
		document.getElementById("pitPieces").textContent = "Notes: " + pit.AutoPieces;
		document.getElementById("pitAAmp").textContent = "Scores Amp: " + pit.ScoresAmp;
		document.getElementById("pitASpeaker").textContent = "Scores Speaker: " + pit.ScoresSpeaker;
		document.getElementById("pitWing").textContent = "Leaves Wing: " + pit.LeaveWing;
		
		document.getElementById("pitPreference").textContent = "Prefers: " + pit.ScoringPreference;
		document.getElementById("pitTAmp").textContent = "Can Amp: " + pit.CanScoreAmp;
		document.getElementById("pitTAmpCT").textContent = "Amp Cycle Time: " + pit.CycleTimeAmp;
		document.getElementById("pitTSpeaker").textContent = "Can Speaker: " + pit.CanScoreSpeaker;
		document.getElementById("pitTSpeakerCT").textContent = "Speaker Cycle Time: " + pit.CycleTimeSpeaker;
		document.getElementById("pitGround").textContent = "Ground Pickup: " + pit.PickupFromGround;
		document.getElementById("pitSource").textContent = "Source Pickup: " + pit.PickupFromSource;
		
		document.getElementById("pitDriveXP").textContent = "Drive EXP: " + pit.DriverExperience;
		document.getElementById("pitTComments").textContent = "Team Comments: " + pit.TeamComments;
		document.getElementById("pitPComments").textContent = "Personal Comments: " + pit.PersonalComments;
	} else {
		document.getElementById("pitDiv").style.display = "none";
	}
}

async function initTeamDropdown() {
	const response = await fetch("/teams", {
		method: 'GET',
		headers: {
			'Content-Type': 'application/x-www-form-urlencoded',
		},
		credentials: 'include',
	});
	var text = await response.json();
	
	dataList = document.createElement("datalist");
	dataList.id = "selectDataTeam";
	text.forEach(option => {
		optionElement = document.createElement("option");
		optionElement.value = option;
		dataList.appendChild(optionElement);
	});
	
	document.body.appendChild(dataList);
}

function loadIndividual() {
	updateIndividual(document.getElementById("team").value, document.getElementById("individualSelector").value);
}

function loadAll() {
	initAll(document.getElementById("allSelector").value, document.getElementById("sortTeams").checked);
}

function jumpToIndividual(teamNum, operation) {
	individualChart.destroy();
	individualChart = null;
	document.getElementById("team").value = teamNum;
	document.getElementById("individualSelector").value = operation;
	updateIndividual(teamNum, operation);
}

async function clickOnIndividualChart(event) {
	var points = individualChart.getElementsAtEventForMode(event, 'nearest', {
		intersect: true
	}, true);
	if(points.length) {
		firstPoint = points[0];
		label = individualChart.data.labels[firstPoint._index];
		value = individualChart.data.datasets[firstPoint._datasetIndex].data[firstPoint._index];
		
		latestTeam = individualChart.data.datasets[firstPoint._datasetIndex].label.split(' ')[0];
		
		matchType = (label.charAt(0) == 'Q' ? "Qualifications" : "Playoffs");
		match = label.substring(1);
		
		params = new URLSearchParams();
		params.append("teamNum", latestTeam);
		params.append("match", match);
		params.append("type", matchType);
		params.append("data", "Objective");
		
		const response = await fetch("/match?"+params.toString(), {
			method: 'GET',
			headers: {
				'Content-Type': 'application/x-www-form-urlencoded',
			},
			credentials: 'include',
		});
		data = await response.json();

		params2 = new URLSearchParams();
		params2.append("teamNum", latestTeam);
		params2.append("match", match);
		params2.append("type", matchType);
		params2.append("data", "Subjective");
		const response2 = await fetch("/match?"+params2.toString(), {
			method: 'GET',
			headers: {
				'Content-Type': 'application/x-www-form-urlencoded'
			},
			credentials: 'include',
		});
		data2 = await response2.json();
		
		document.getElementById("qualNum").textContent = data.TeamNumber + "'s " + (matchType.substring(0,matchType.length-1)) + " " + data.MatchNumber;
		document.getElementById("alliance").textContent = data.AllianceColor + " " + data.DriverStation;
		document.getElementById("replay").textContent = "Replay: " + data.Replay;
		document.getElementById("scouter").textContent = "Scouter: " + data.ScouterName
		document.getElementById("quality").textContent = "Data Quality: " + data.DataQuality;
		
		document.getElementById("coords").textContent = data.StartPos;
		document.getElementById("preload").textContent = "Preloaded: " + data.Preload;
		document.getElementById("eStop").textContent = "Auto Stop: " + data.AStop;
		document.getElementById("leftWing").textContent = "Left Wing: " + data.LeftWing;
		document.getElementById("notesCenter").textContent = "Center Notes: " + data.AutoPickUpCenter;
		document.getElementById("notesWing").textContent = "Wing Notes " + data.AutoPickUpWing;
		document.getElementById("autoSpeaker").textContent = "Speaker: " + data.AutoSpeaker;
		document.getElementById("autoAmp").textContent = "Amp: " + data.AutoAmp;
		
		document.getElementById("coop").textContent = "Coopertition: " + data.Coopertition;
		document.getElementById("feeder").textContent = "Feeder: " + data.Feeder;
		document.getElementById("groundPickup").textContent = "Ground Intake: " + data.PickUpGround;
		document.getElementById("sourcePickup").textContent = "Source Intake: " + data.PickUpSource;
		document.getElementById("unampedSpeaker").textContent = "Unamped Speaker: " + data.SpeakerNotesUnamped;
		document.getElementById("ampedSpeaker").textContent = "Amped Speaker: " + data.SpeakerNotesAmped;
		document.getElementById("amps").textContent = "Amps: " + data.AmpNotes;
		
		document.getElementById("onstage").textContent = "Onstage: " + data.Onstage;
		document.getElementById("park").textContent = "Parked: " + data.Park;
		document.getElementById("spotlight").textContent = "Spotlit: " + data.Spotlight; 
		document.getElementById("trap").textContent = "Trap: " + data.Trap;
		
		document.getElementById("comments").textContent = data.Comments;
		
		document.getElementById("totalPoints").textContent = "Sum Score: " + dataFuncs["TotalPoints"](data);
		document.getElementById("autoPoints").textContent = "Auto Score: " + dataFuncs["AutoPoints"](data);
		document.getElementById("telePoints").textContent = "Tele-Op Score: " + dataFuncs["TelePoints"](data);
		document.getElementById("endPoints").textContent = "End Score: " + dataFuncs["EndPoints"](data);
		
		document.getElementById("objectiveDiv").scrollIntoView({behavior: 'smooth'});
		
		canvas = document.getElementById("startImg");
		ctx = canvas.getContext("2d");
		
		if(data.AllianceColor == "Red") {
			image = document.getElementById("RedStart");
			ctx.fillStyle = "red";
		} else {
			image = document.getElementById("BlueStart");
			ctx.fillStyle = "blue";
		}
		
		ctx.fillRect(0,0,120,600);
		ctx.drawImage(image, 0, 0, 120, 600);
		
		coords = data.StartPos.split(", ")
		ctx.fillStyle="black";
		ctx.beginPath();
		ctx.arc(coords[1]*10, coords[0]*10, 10, 0, 2 * Math.PI);
		ctx.fill();
		
		if(data2 != null) {
			document.getElementById("subjectiveDiv").style.display = "";
			
			document.getElementById("sQualNum").textContent = data2.TeamNumber + "'s " + (matchType.substring(0,matchType.length-1)) + " " + data2.MatchNumber;
			document.getElementById("sAlliance").textContent = data2.AllianceColor + " " + data2.DriverStation;
			document.getElementById("sHP").textContent = "HP at Amp: " + data2.HPAtAmp;
			document.getElementById("sReplay").textContent = "Replay: " + data2.Replay;
			document.getElementById("sScouter").textContent = "Scouter: " + data2.ScouterName;
			document.getElementById("sQuality").textContent = "Data Quality: " + data2.DataQuality;
	
			//document.getElementById("sAutoPickups").textContent = "Auto Pickups: " + data2.AutoPickups;
	
			document.getElementById("sSub").textContent = "Can Subwoofer: " + data2.CanScoreSub;
			document.getElementById("sPodium").textContent = "Can Podium: " + data2.CanScorePodium;
			document.getElementById("sOther").textContent = "Can Other: " + data2.CanScoreOther;
			document.getElementById("sFeeder").textContent = "Feeder: " + data2.Feeder;
	
			document.getElementById("sHPComments").textContent = data2.HumanPlayerComments;
	
			document.getElementById("sComments").textContent = data2.Comments;
			
			document.getElementById("sCoop").textContent = "Coopertition: " + data2.Coopertition;
			
			canvas2 = document.getElementById("sAutoPickups");
			
			ctx2 = canvas2.getContext("2d");
			
			// THE PERSPECTIVE IS FLIPPED, DONT PANIC.
			if(data.AllianceColor == "Blue") {
				image = document.getElementById("RedNotes");
				//color = "red";
				color = "blue";
				coordTable = autoNoteRed;
			} else {
				image = document.getElementById("BlueNotes");
				//color = "blue";
				color = "red"
				coordTable = autoNoteBlue;
			}
			
			ctx2.fillStyle = color;
			ctx2.fillRect(0,0,356,333);
			ctx2.drawImage(image, 0, 0, 356, 333);
			
			dataPoints = data2.AutoPickups.split(", ");
			dataPoints.forEach(item => {
				ctx2.fillStyle="black";
				ctx2.beginPath();
				coords = coordTable[item];
				ctx2.arc(coords["x"], coords["y"], 12, 0, 2*Math.PI);
				ctx2.fill();
			});
			
			started = false;
			ctx2.lineWidth = 4;
			ctx2.beginPath();
			dataPoints.forEach(item => {
				coords = coordTable[item];
				if(!started) {
					ctx2.moveTo(coords["x"], coords["y"]);
					started = true;
				} else {
					ctx2.lineTo(coords["x"], coords["y"]);
				}
			})
			ctx2.stroke();
			
			ctx2.fillStyle = color;
			ctx2.font = "bold 20px Sans Serif";
			i = 0;
			iTable = ["A","B","C","D","E","F","G","H"];
			dataPoints.forEach(item => {
				coords = coordTable[item];
				ctx2.fillText(iTable[i], coords["x"]-5, coords["y"]+5);
				i++;
			});
		} else {
			document.getElementById("subjectiveDiv").style.display = "none";
		}
	}
}

async function clickOnAllChart(event) {
	var points = allChart.getElementsAtEventForMode(event, 'nearest', {
		intersect: true
	}, true);
	if(points.length) {
		firstPoint = points[0];
		label = allChart.data.labels[firstPoint._index];
		value = allChart.data.datasets[firstPoint._datasetIndex].data[firstPoint._index];
		operation = allChart.data.datasets[firstPoint._datasetIndex].label.split(' ')[1];
		jumpToIndividual(value.x, operation);
		document.getElementById("clickIndividual").click();
	}
}

updateIndividual(948, "TeleSpeaker");
initAll("TeleSpeaker", false);
initTeamDropdown();
