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
		teamData.forEach(item => total += dataFuncs[operation](item));
		average = total / teamData.length;
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
	text2.forEach(item => {
		if(item.CanScoreSub) subTotal++;
		if(item.CanScorePodium) podiumTotal++;
		if(item.CanScoreOther) otherTotal++;
	});

	document.getElementById("avgSubwoofer").textContent = "Subwoofer %: " + subTotal / text2.length * 100;
	document.getElementById("avgPodium").textContent = "Podium %: " + podiumTotal / text2.length * 100;
	document.getElementById("avgOther").textContent = "Other %: " + otherTotal / text2.length * 100;
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


		document.getElementById("sQualNum").textContent = data2.TeamNumber + "'s " + (matchType.substring(0,matchType.length-1)) + " " + data2.MatchNumber;
		document.getElementById("sAlliance").textContent = data2.AllianceColor + " " + data2.DriverStation;
		document.getElementById("sHP").textContent = "HP at Amp: " + data2.HPAtAmp;
		document.getElementById("sReplay").textContent = "Replay: " + data2.Replay;
		document.getElementById("sScouter").textContent = "Scouter: " + data2.ScouterName;
		document.getElementById("sQuality").textContent = "Data Quality: " + data2.DataQuality;

		document.getElementById("sAutoPickups").textContent = "Auto Pickups: " + data2.AutoPickups;

		document.getElementById("sSub").textContent = "Can Subwoofer: " + data2.CanScoreSub;
		document.getElementById("sPodium").textContent = "Can Podium: " + data2.CanScorePodium;
		document.getElementById("sOther").textContent = "Can Other: " + data2.CanScoreOther;
		document.getElementById("sFeeder").textContent = "Feeder: " + data2.Feeder;

		document.getElementById("sHPComments").textContent = data2.HumanPlayerComments;

		document.getElementById("sComments").textContent = data2.Comments;
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
