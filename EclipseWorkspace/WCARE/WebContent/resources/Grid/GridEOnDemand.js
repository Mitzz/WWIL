// Include this file instead of GridE.js if you want to download the script only on demand
// When you will want to create and show TreeGrids on your page, load GridE.js by calling the LoadGridE() function
// The path is url of GridE.js file like "../Grid/GridE.js".
// The path can be relative to the main page url as usual or can be absolute
// Check the script path carefully, the most errors are caused because of wrong path
function LoadGridE(path){
if(LoadGridE.Loaded) return;
if(!path) { alert("Cannot download TreeGrid script!\r\n\r\nThe script path is empty"); return; }
try {
	var script = document.createElement("script");
	script.type = "text/javascript";
	script.src = path;
	document.documentElement.getElementsByTagName("head")[0].appendChild(script);
	LoadGridE.Loaded = true;
	}
catch(e){
	alert("Cannot download TreeGrid script!\r\n\r\nError message:\r\n"+(e.message ? e.message:e));
	}
}
if(!window.Grids){ 
	var Grids = new Array();
	Grids.OnDemand = true;
	}
if(!window.TCalc) { 
	var TCalc = function(){ };
	TCalc.OnDemand = true;
	}
