// General library of JavaScript functions

var undefined;

window.pilCommandQueue = [ ];

// create basic form elements for cloning
var baseA = document.createElement("a");
var baseDL = document.createElement("dl");
var baseDT = document.createElement("dt");
var baseDD = document.createElement("dd");
var baseDIV = document.createElement("div");
var baseUL = document.createElement("ul");
var baseOL = document.createElement("ol");
var baseLI = document.createElement("li");
var baseFORM = document.createElement("form");
var baseSELECT = document.createElement("select");
var baseINPUT = document.createElement("input");
var baseOPTION = document.createElement("option");
var baseTABLE = document.createElement("table");
var baseTHEAD = document.createElement("thead");
var baseTBODY = document.createElement("tbody");
var baseTH = document.createElement("th");
var baseTR = document.createElement("tr");
var baseTD = document.createElement("td");
var baseSPAN = document.createElement("span");
var baseBR = document.createElement("br");
var baseTEXTAREA = document.createElement("textarea");
var baseIMG = document.createElement('img');

// pull the value of a node from a DOM (xml) object
function getNodeValue(node, tag) {
	var returnValue = undefined;

	if (tag !== ".") {
		var foundAt = -1;

		try {
			for (var i = 0; i < node.childNodes.length; i++) {
				if (node.childNodes[i].nodeName === tag) {
					foundAt = i;

					break;
				}
			}
		} catch (err1) {
		}

		if (foundAt === -1) {
			try {
				if ( (node.length !== undefined) && (node.length > 0)) {
					node = node[0];

					for (var j = 0; j < node.childNodes.length; j++) {
						if (node.childNodes[j].nodeName === tag) {
							foundAt = j;

							break;
						}
					}
				}
			} catch (err2) {
			}
		}

		if (foundAt >= 0) {
			var set = false;

			try {
				returnValue = node.childNodes[foundAt].nodeValue;

				if ( (returnValue !== "") && (returnValue !== null) && (returnValue !== undefined)) {
					set = true;
				}
			} catch (err3) {
			}

			if ( !set) {
				try {
					returnValue = node.childNodes[foundAt].textContent;

					if ( (returnValue !== "") && (returnValue !== null) && (returnValue !== undefined)) {
						set = true;
					}
				} catch (err4) {
				}
			}

			if ( !set) {
				try {
					if (node.childNodes[foundAt].childNodes.length > 0) {
						returnValue = node.childNodes[foundAt].childNodes[0].textContent;
					}

					if ( (returnValue !== "") && (returnValue !== null) && (returnValue !== undefined)) {
						set = true;
					}
				} catch (err5) {
				}
			}
		}
	} else {
		returnValue = node.textContent;
	}

	if (returnValue === undefined) {
		returnValue = "";
	}

	returnValue = returnValue.replace(/&amp;/gi, "&");

	return returnValue;
}

// pull an attribute value from a DOM (xml) object
function getNodeAttribute(obj, tag) {
	var returnValue = undefined;
	var set = false;

	try {
		returnValue = obj.attributes.getNamedItem(tag).value;
		set = true;
	} catch (err) {
	}

	if ( !set) {
		try {
			returnValue = obj[0].attributes.getNamedItem(tag).value;
			set = true;
		} catch (err1) {
		}
	}

	if (returnValue === undefined) {
		returnValue = "";
	}

	returnValue = returnValue.replace(/&amp;/gi, "&");

	return returnValue;
}

function getChildrenByTagName(obj, tag) {
	var list = [ ];
	var child;

	for (var i = 0; i < obj.childNodes.length; i++) {
		child = obj.childNodes[i];

		if (child.tagName === tag) {
			list[list.length] = child;
		}
	}

	return list;
}

function setNodeAttribute(obj, tag, value) {
	var attributeList = null;
	var set = false;

	try {
		attributeList = obj.attributes;

		set = true;
	} catch (err) {
	}

	if ( !set) {
		try {
			attributeList = obj[0].attributes;

			set = true;
		} catch (err2) {
		}
	}

	if (set) {
		var newAttribute = document.createAttribute(tag);

		newAttribute.value = value;
		attributeList.setNamedItem(newAttribute);
	}
}

function loadSelectBox(selectBox, nodeType, nodeID, nodeDesc, xmlDoc, showIDs, zeroDesc) {
	if (showIDs === undefined) {
		showIDs = false;
	}

	var holdSelected = selectBox.value;

	pilKillChildren(selectBox);

	var data = xmlDoc.getElementsByTagName(nodeType);

	var workOption = baseOPTION.cloneNode(false);

	var id;
	var desc;

	if (zeroDesc !== undefined) {
		workOption = baseOPTION.cloneNode(false);
		workOption.value = 0;
		workOption.text = zeroDesc;

		selectBox.appendChild(workOption);
	}

	for (var i = 0; i < data.length; i++) {
		id = getNodeAttribute(data[i], nodeID);

		if ( ! ( (id === '0') && (zeroDesc !== undefined))) {
			desc = unEscapeString(getNodeValue(data[i], nodeDesc));

			if (showIDs) {
				desc += " (" + id + ")";
			}

			workOption = baseOPTION.cloneNode(false);
			workOption.value = id;
			workOption.text = desc;

			selectBox.appendChild(workOption);
		}
	}

	selectBox.value = holdSelected;
}

function urlEncode(string) {
	var newString = "" + string; // force whatever is passed in to be a
	// string

	while (newString.indexOf(" ") !== -1) {
		newString = newString.replace(" ", "+");
	}

	return escape(newString);
}

function pilSwapNode(node1, node2) {
	var placeHolder1 = document.createTextNode("place holder 1");
	var placeHolder2 = document.createTextNode("place holder 2");

	var parentNode1 = node1.parentNode;
	var parentNode2 = node2.parentNode;

	parentNode1.insertBefore(placeHolder1, node1);
	parentNode2.insertBefore(placeHolder2, node2);

	parentNode1.replaceChild(node2, placeHolder1);
	parentNode2.replaceChild(node1, placeHolder2);

	return true;
}

function showError(response, param2, param3) {
	var xmlDoc = response.xhr.responseXML;
	var isError = false;

	try {
		var error = getNodeAttribute(xmlDoc, "isError");

		if (error != "") {
			var errorReason = getNodeAttribute(xmlDoc, "errorReason");
			var errorMessage = getNodeAttribute(xmlDoc, "errorMessage");

			alert(errorReason + "\n\n" + errorMessage);

			isError = true;
		}
	} catch (error) {

	}

	return isError;
}

function bitBucket(param1, param2, param3) {
	showError(response);

	return true;
}

function pilEncapsulate(command) {
	var commandXML = "<command ";
	var nodes = "";

	for (i in command) {
		commandXML += i + "='" + escapeString(command[i]) + "' ";
		nodes += "<" + i + ">" + escapeString(command[i]) + "</" + i + ">";
	}

	commandXML += ">" + nodes + "</command>";

	return commandXML;
}

function escapeString(inputData) {
	var outputData = inputData;

	if ( typeof inputData == 'string') {
		if (inputData != null && inputData != "") {
			outputData = inputData.replace(/&/gi, "&amp;");
			outputData = outputData.replace(/</gi, "&lt;");
			outputData = outputData.replace(/>/gi, "&gt;");
			outputData = outputData.replaceAll("\"", "&quot;");
			outputData = outputData.replaceAll("\\", "~1~");
			outputData = outputData.replaceAll("%", "~2~");
			outputData = outputData.replaceAll("\'", "~3~");
			outputData = outputData.replaceAll("\n", "~4~");
		}
	}

	return outputData;
}

function unEscapeString(inputData) {
	var outputData = inputData;

	if ( typeof inputData == 'string') {
		if (inputData != null && inputData != "") {
			outputData = inputData.replaceAll("&amp;", "&");
			outputData = outputData.replaceAll("&lt;", "<");
			outputData = outputData.replaceAll("&gt;", ">");
			outputData = outputData.replaceAll("&quot;", "\"");
			outputData = outputData.replaceAll("~1~", "\\");
			outputData = outputData.replaceAll("~2~", "%");
			outputData = outputData.replaceAll("~3~", "\'");
			outputData = outputData.replaceAll("~4~", "\n");
		}
	}

	return outputData;
}

function flushQueue() {
	window.pilCommandQueue = [ ];
	window.pilBusy = false;

	return true;
}

function pilQueueCommand(command) {
	var commandXML = pilEncapsulate(command);

	if (window.pilCommandQueue === undefined) {
		window.pilCommandQueue = [ ];
	}

	window.pilCommandQueue[window.pilCommandQueue.length] = commandXML;
	window.pilBusy = true;

	return true;
}

function pilPushSpecificQueue(url, onLoad, queue, onError) {
	if (onError == undefined) {
		onError = "bitBucket";
	}

	var xmlDoc = "";

	xmlDoc += "<?xml version='1.0' encoding='utf-8' ?>";
	xmlDoc += "<commands>";

	for (var i = 0; i < queue.length; i++) {
		if (queue[i] !== undefined) {
			xmlDoc += queue[i];
		}
	}

	xmlDoc += "</commands>";

	$.ajax({
		type: 'POST',
		url: url,
		data: xmlDoc,
		contentType: 'text/xml',
		dataType: 'xml',
		cache: false,
		error: showError,
		success: onLoad
	});

	return true;
}

function pilPushQueue(url, onLoad) {
	pilPushSpecificQueue(url, onLoad, window.pilCommandQueue);

	flushQueue();

	return true;
}

function cursorWait() {
	document.body.style.cursor = 'progress';

	return true;
}

function cursorReset() {
	try {
		document.body.style.cursor = 'default';
	} catch (err) {

	}

	return true;
}

String.prototype.trim = function() {
	return this.replace(/^\s+|\s+$/g, "");
};

String.prototype.ltrim = function() {
	return this.replace(/^\s+/, "");
};

String.prototype.rtrim = function() {
	return this.replace(/\s+$/, "");
};

String.prototype.replaceAll = function(find, replace) {
	var text = this;
	var index = text.indexOf(find);

	while (index != -1) {
		text = text.replace(find, replace);
		index = text.indexOf(find);
	}

	return text;
};