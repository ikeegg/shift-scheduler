/**
 * JavaScript library for project.jsp
 */

function loadProjectList() {
	pilQueueCommand({
		action: "getCookbookList"
	});

	pilPushQueue("./Project", fillProjectList);
}

function fillProjectList(xmlDoc, param2, param3, param4) {
	var projectList = $("#projectID");

	projectList.empty();

	$(xmlDoc).find("project").each(function() {
		var projectID = $(this).find("projectID").text();
		var isbn13 = $(this).find("isbn13").text();

		var showText = projectID + " (" + isbn13 + ")";

		projectList.append($("<option>").val(projectID).text(showText));
	});

	$("#projectID").change(selectProject);
}

function selectProject(target) {
	clearScreen();

	pilQueueCommand({
		action: "getXmlForProjectID",
		projectID: $(this).val()
	});

	pilPushQueue("./Project", showProject);

	pilQueueCommand({
		action: "getKsProjectXML",
		projectID: $(this).val()
	});

	pilPushQueue("./Project", showTitle);
}

function showTitle(xmlDoc, param2, param3, param4) {
	$("#title").val($(xmlDoc).find("title").text());
}

function showProject(xmlDoc, param2, param3, param4) {
	$("#pageCount").val($(xmlDoc).find("pageCount").text());
	$("#itemNumber").val($(xmlDoc).find("itemNumber").text());
	$("#editorID").val($(xmlDoc).find("editor").text());
	$("#trimSize").val($(xmlDoc).find("trimSize").text());
	$("#isbn13").val($(xmlDoc).find("isbn13").text());
	$("#digitalProcessing").val($(xmlDoc).find("digitalServices").text());
	$("#colors").val($(xmlDoc).find("color").text());
	$("#developedFor").val($(xmlDoc).find("developedFor").text());
	$("#artDirector").val($(xmlDoc).find("artDirector").text());
	$("#binding").val($(xmlDoc).find("binding").text());
	$("#exclusiveFor").val($(xmlDoc).find("exclusiveFor").text());
}

function clearScreen() {
	$("#title").empty();
	$("#pageCount").empty();
	$("#itemNumber").empty();
	$("#editorID").empty();
	$("#trimSize").empty();
	$("#isbn13").empty();
	$("#digitalProcessing").empty();
	$("#colors").empty();
	$("#developedFor").empty();
	$("#artDirector").empty();
	$("#binding").empty();
	$("#exclusiveFor").empty();
}

function init() {
	$(document).foundation();

	loadProjectList();
}

$(document).ready(init);
