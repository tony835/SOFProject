document.getElementById('titleDoc').innerHTML = document.title;

function relativeRedir(loc) {	
	var b = document.getElementsByTagName('base');
	if (b && b[0] && b[0].href) {
			if (b[0].href.substr(b[0].href.length - 1) == '/' && loc.charAt(0) == '/')
		loc = loc.substr(1);
			loc = b[0].href + loc;
	}
	window.location.replace(loc);
}


function ChanGeValue() {
	var x = document.getElementById("mySelect").selectedIndex;
	var y = document.getElementById("mySelect").options;
	window.location.replace("personne/list.htm?nomGroupe=" + y[x].text);
}

function askSubmission(msg, form) {
	if (confirm(msg))
		form.submit();
}