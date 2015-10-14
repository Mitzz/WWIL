if (document.getElementById){
document.write('<style type="text/css">\n')
document.write('.submenu{display: none;}\n')
document.write('.subsubmenu{display: none;}\n')
document.write('</style>\n')
}

function SwitchMenu(obj,img){
	if(document.getElementById){
	var el = document.getElementById(obj);
	var im = document.getElementById(img);
	var imn = document.getElementById("masterdiv").getElementsByTagName("img");
	var ar = document.getElementById("masterdiv").getElementsByTagName("span");
		if(el.style.display != "block"){
			for (var i=0; i<ar.length; i++){
				if (ar[i].className=="submenu")
				ar[i].style.display = "none";
				if (ar[i].className=="subsubmenu")
				ar[i].style.display = "none";
				if (ar[i].className=="subsubsubmenu")
				ar[i].style.display = "none";
				if (imn[i].className=="img1") 
				{
				imn[i].src= "resources/images/arrow-side.gif";	
				imn[i].width = "4";
				imn[i].height = "6";
				}
				if (imn[i].className=="img2") 
				{
				imn[i].src= "resources/images/arrow-side.gif";	
				imn[i].width = "4";
				imn[i].height = "6";
				}
				if (imn[i].className=="img3") 
				{
				imn[i].src= "resources/images/arrow-side.gif";	
				imn[i].width = "4";
				imn[i].height = "6";
				}
			}
			el.style.display = "block";
			im.src = "resources/images/arrow-down.gif";
			im.width = "6";
			im.height = "4";
		}else{
			el.style.display = "none";
			im.src = "resources/images/arrow-side.gif";	
			im.width = "4";
			im.height = "6";
		}
	}
}

function SwitchMenu1(obj,img){
	if(document.getElementById){
	var el = document.getElementById(obj);
	var im = document.getElementById(img);
	var imn = document.getElementById("masterdiv").getElementsByTagName("img");
	var ar = document.getElementById("masterdiv").getElementsByTagName("span");
		if(el.style.display != "block"){
			for (var i=0; i<ar.length; i++){
				if (ar[i].className=="subsubmenu")
				ar[i].style.display = "none";
				if (ar[i].className=="subsubsubmenu")
				ar[i].style.display = "none";
				if (imn[i].className=="img2")
				{
				imn[i].src= "resources/images/arrow-side.gif";	
				imn[i].width = "4";
				imn[i].height = "6";
				}
				if (imn[i].className=="img3") 
				{
				imn[i].src= "resources/images/arrow-side.gif";	
				imn[i].width = "4";
				imn[i].height = "6";
				}
			}
			el.style.display = "block";
			im.src = "resources/images/arrow-down.gif";
			im.width = "6";
			im.height = "4";
		}else{
			el.style.display = "none";
			im.src = "resources/images/arrow-side.gif";
			im.width = "4";
			im.height = "6";
		}
	}
}

function SwitchMenu2(obj,img){
	if(document.getElementById){
	var el = document.getElementById(obj);
	var im = document.getElementById(img);	
	var imn = document.getElementById("masterdiv").getElementsByTagName("img");
	var ar = document.getElementById("masterdiv").getElementsByTagName("span");
		if(el.style.display != "block"){
			for (var i=0; i<ar.length; i++){
				if (ar[i].className=="subsubsubmenu")
				ar[i].style.display = "none";
				if (imn[i].className=="img3")
				{
				imn[i].src= "resources/images/arrow-side.gif";	
				imn[i].width = "4";
				imn[i].height = "6";
				}				
			}
			el.style.display = "block";
			im.src = "resources/images/arrow-down.gif";
			im.width = "6";
			im.height = "4";
		}else{
			el.style.display = "none";
			im.src = "resources/images/arrow-side.gif";	
			im.width = "4";
			im.height = "6";
		}
	}
}
