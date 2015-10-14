
function hideRow()
{
	if(document.forms[0].occupations.value=="28")
	{
		document.getElementById('divInd').style["visibility"]="hidden" ;
		document.getElementById('divInd').style["display"]="none" ;
	}
	else
	{
		document.getElementById('divInd').style["visibility"]="visible" ;
		document.getElementById('divInd').style["display"]="block" ;
	}
}

function Evt(evt) 
{
 this.evt = evt ? evt : window.event; 
 this.source = evt.target ? evt.target : evt.srcElement;
 this.x = evt.pageX ? evt.pageX : evt.clientX;
 this.y = evt.pageY ? evt.pageY : evt.clientY;
}

function wtg(e,n,dnm,xoff,yoff)
{
	var theTop;

	 if (navigator.userAgent.indexOf("MSIE")!=-1) 
	  theTop =  document.body.scrollTop; 
	 else
	  theTop = 0;

	
	if(!e) e = window.event;
	
	evt = new Evt(e);

	document.getElementById(dnm).style.left=evt.x + xoff;
	document.getElementById(dnm).style.top=evt.y + yoff + theTop;

	if(n==1)
	{
		document.getElementById(dnm).style["display"]="block" ;
		document.getElementById(dnm).style["visibility"]="visible" ;
	}
	else
	{
		document.getElementById(dnm).style["display"]="none" ;
		document.getElementById(dnm).style["visibility"]="hidden" ;
	}
}

function searchtag(searchval)
{
	document.location.href = "Main.php?do=search&txtsearch="+escape(searchval);
}


function hideAnydiv(divName1)
{
	document.getElementById(divName1).style.display='none';
	document.getElementById(divName1).style.visibility='hidden';
}

function showpostdiv(divName1)
{
	document.getElementById(divName1).style.display='block';
	document.getElementById(divName1).style.visibility='visible';
}


function validate_photofrm(frm)
{
	var ch;
	var flag=true;
	var error="";

	var fileName = frm.photo_filename.value;
	var fileTypes = ['','gif','jpg','jpeg','bmp'];
	var dots = fileName.split(".")
	var fileType = "." + dots[dots.length-1];
	if (fileTypes.join(".").indexOf(fileType.toLowerCase()) == -1) 
	{
		//alert("Please only upload files that end in types: \n" + (fileTypes.join(" .")) + "\nPlease select a new file and try again.");
		alert("Unsupported file format!. Please upload your photo in .gif, .jpg, .jpeg or .bmp format.");

		return (false);
	}	
	
	if(frm.photo_filename.value=="")
	{
		flag=false;
		error=error+"Please select a photo to upload\n";
		  if(focus==false){
			frm.photo_filename.focus();
			focus=true;
		  }
	}

	if(trim(frm.occupations.value)=="")
	{
		flag=false;
		error=error+"Please select a profession\n";
		  if(focus==false){
			frm.occupations.focus();
			focus=true;
		  }
	}

	if(frm.college1.value=="")
	{
		flag=false;
		error=error+"Please enter your college/university name\n";
		  if(focus==false){
			frm.college1.focus();
			focus=true;
		  }
	}

	if(trim(frm.occupations.value)!="" && trim(frm.occupations.value)!="28")
	{

		if(trim(frm.industry.value)=="")
		{
			flag=false;
			error=error+"Please select an industry\n";
			  if(focus==false){
				frm.industry.focus();
				focus=true;
			  }
		}
		if(trim(frm.orgname.value)=="")
		{
			flag=false;
			error=error+"Please enter your company's name\n";
			  if(focus==false){
				frm.orgname.focus();
				focus=true;
			  }
		}
	
	}

	if(frm.interests.value=="")
	{
		flag=false;
		error=error+"Please enter your interests\n";
		  if(focus==false){
			frm.interests.focus();
			focus=true;
		  }
	}

	if(flag==false)
	{
		alert(error);
		return false;
	}
	else{

		frm.submit.value  = 'Submitting..';
		frm.submit.disabled  = true;
		return true;
	}
	
}



function calcaddCharLeft(ansid) {

	clipped = false
	lenUSig = 0
	maxLength = 300

	if (document.getElementById("addcomment"+ansid).value.length > maxLength) {
		document.getElementById("addcomment"+ansid).value = document.getElementById("addcomment"+ansid).value.substring(0,maxLength)
		charleft = 0
		clipped = true
	} 
	else {
		charleft = maxLength - document.getElementById("addcomment"+ansid).value.length
	}

	document.getElementById("add"+ansid).innerHTML = charleft
	return clipped

}
function addKey(ansid) {
	supportsKeys = true
	calcaddCharLeft(ansid)
}

function calcRateCharLeft(ansid) {

	clipped = false
	lenUSig = 0
	maxLength = 300

	if (document.getElementById("ratecomment"+ansid).value.length > maxLength) {
		document.getElementById("ratecomment"+ansid).value = document.getElementById("ratecomment"+ansid).value.substring(0,maxLength)
		charleft = 0
		clipped = true
	} 
	else {
		charleft = maxLength - document.getElementById("ratecomment"+ansid).value.length
	}

	document.getElementById("rate1"+ansid).innerHTML = charleft
	return clipped

}
function rateKey(ansid) {
	supportsKeys = true
	calcRateCharLeft(ansid)
}

function calcmessCharLeft() {
	clipped = false
	lenUSig = 0
	maxLength = 1000
        if (document.getElementById("message").value.length > maxLength) { 
	        document.getElementById("message").value = document.getElementById("message").value.substring(0,maxLength)
		charleft = 0
		clipped = true
        } else {
		charleft = maxLength - document.getElementById("message").value.length
	}
		document.getElementById("messCL").innerHTML = charleft
        return clipped
}
function messKey() {
	supportsKeys = true
	calcmessCharLeft()
}

function hiderateDiv(ansid){
	document.getElementById("rate"+ansid).innerHTML="";
	document.getElementById("rate"+ansid).style.visibility="hidden";
	document.getElementById("rate"+ansid).style.display="none";
}

function sttab(t)
{
	for(i=1;i<=4;i++){
		if(i==3 && loggedin==0){
			continue;
		}
		if(i==3 && loggedin==1 && qnauserid!=userid){
			continue;
		}

		/*if(i==1)
		{
			document.getElementById('tt1').style.backgroundColor = "#FFB4BC";
		}
		if(i==2)
		{
			document.getElementById('tt2').style.backgroundColor = "#FBFD8F";
		}
		if(i==3)
		{
			document.getElementById('tt3').style.backgroundColor = "#E7BAFF";
		}
		if(i==4)
		{
			document.getElementById('tt4').style.backgroundColor = "#BBFFB6";
		}*/


		ts=document.getElementById('tt'+i);
		tr = document.getElementById('dd'+i);
		ta = document.getElementById('aa'+i);
		if(t==i){
			ts.className="tbmain2 qnaProfbord4";
			ta.className="srchlinksel2";
			ts.style.borderBottom="1px solid #FFFFFF";
			tr.style["display"]="block";
			tr.style["visibility"]="visible";
		}
		else{
			ts.className="stb2 border1";
			ta.className="srchlink2a";
			ts.style.borderBottom="1px solid #B5D6EF";
			ts.style.backgroundColor = "#E2E2E2";
			tr.style["display"]="none";
			tr.style["visibility"]="hidden";
		}
	}

	document.getElementById('tt'+t).style.backgroundColor = "#FFFFFF";
}


function st2(t,n)
{
	//	document.getElementById('ttq'+n).style.borderBottom="1px solid #b5d6ef";
	for(i=1;i<=3;i++)
	{
		ts=document.getElementById('tt'+ i + 'q' + n);
		tr = document.getElementById('dd'+ i + 'q' + n);
		ta = document.getElementById('aa'+ i + 'q' + n);
		if(t==i)
		{
			ts.className="linkoff";
			ta.className="srchlinksel2";
		//	ts.style.borderBottom="1px solid #d9eeff";
		//	ts.style.background="#d9eeff";
			tr.style["display"]="block";
			tr.style["visibility"]="visible";
		}
		else
		{
	//		document.getElementById('tt'+i + 'q' + n ).className="ttback"+i;			

			ts.className="linkon";
			ta.className="srchlink2a";
		//	ts.style.borderBottom="1px solid #B5D6EF";
		//	ts.style.background="#effbff";
			tr.style["display"]="none";
			tr.style["visibility"]="hidden";
		}
	}
}


function showAnswer(nm)
{
	document.getElementById('ans'+nm).style.visibility='visible';
	document.getElementById('ans'+nm).style.display='block';
}

function closeAnswer(nm)
{
	document.getElementById('ans'+nm).style.visibility='hidden';
	document.getElementById('ans'+nm).style.display='none';
}

function AddComment(qn)
{
	document.getElementById('AddCommentq'+qn).style.display='block';
	document.getElementById('AddCommentq'+qn).style.visibility='visible';
	document.getElementById('btnCommentq'+qn).style.display='none';
	document.getElementById('btnCommentq'+qn).style.visibility='hidden';
	document.getElementById('commentTxt'+qn).style.visibility='visible';	
	document.getElementById('commentTxt'+qn).style.display='block';	
}

function cancelComment(qn)
{
	document.getElementById("CommentAdded"+qn).style.display="none";
	document.getElementById("CommentAdded"+qn).style.visibility="hidden";
	document.getElementById('AddCommentq'+qn).style.display='none';
	document.getElementById('AddCommentq'+qn).style.visibility='hidden';
	document.getElementById('btnCommentq'+qn).style.display='block';
	document.getElementById('btnCommentq'+qn).style.visibility='visible';
	document.getElementById('commentTxt'+qn).style.display='none';	
	document.getElementById('commentTxt'+qn).style.visibility='hidden';	
}
function hideDiv(dn,qn)
{
	document.getElementById('dd'+dn+'q'+qn).style.display='none';
	document.getElementById('dd'+dn+'q'+qn).style.visibility='hidden';
	/*
	document.getElementById('ttq'+qn).style.borderBottom="0px solid #ffffff";
	document.getElementById('tt1q'+qn).style.borderBottom="1px solid #b5d6ef";
	document.getElementById('tt2q'+qn).style.borderBottom="1px solid #b5d6ef";
	document.getElementById('tt3q'+qn).style.borderBottom="1px solid #b5d6ef";
	document.getElementById('tt1q'+qn).style.background="#effbff";
	document.getElementById('tt2q'+qn).style.background="#effbff";
	document.getElementById('tt3q'+qn).style.background="#effbff"; 
	*/
}
function showDivAbuse(nm,ans)
{
	document.getElementById('divAbuse'+nm+ans).style.display='block';
	document.getElementById('divAbuse'+nm+ans).style.visibility='visible';
}

function hideDivAbuse(nm,ans)
{
	document.getElementById('divAbuse'+nm+ans).style.display='none';
	document.getElementById('divAbuse'+nm+ans).style.visibility='hidden';
}

//to close all messages, the message div's are named as ms1,ms2..ms7
function closeMsg()
{
	return;
	for(i=1;i<=7;i++)
	{
		hideAnydiv('ms'+i);
	}

	hideAnydiv('divReportAbu');
}


function st2(t,n)
{
	//document.getElementById('ttq'+n).style.borderBottom="1px solid #b5d6ef";
	for(i=1;i<=5;i++)
	{
		if(i==5){
			if(loggedin==0 || bestansselected==1 ||questposter==0){
				continue;
			}
		}

		ts=document.getElementById('tt'+ i + 'q' + n);
		tr = document.getElementById('dd'+ i + 'q' + n);
		ta = document.getElementById('aa'+ i + 'q' + n);
		if(t==i)
		{
			ts.className="tb2";
			ta.className="srchlinksel2";
		//	ts.style.borderBottom="0px solid #FFFFFF";
		//	ts.style.background="#d9eeff";
			tr.style["display"]="block";
			tr.style["visibility"]="visible";
		}
		else
		{
			document.getElementById('tt'+i + 'q' + n ).className="ttback"+i;			
			ta.className="srchlink2";
		//	ts.style.borderBottom="1px solid #B5D6EF";
			ts.style.background="#ffffff";
			tr.style["display"]="none";
			tr.style["visibility"]="hidden";
		}
	}
}

function hideDiv(dn,qn)
{
	document.getElementById('dd'+dn+'q'+qn).style.display='none';
	document.getElementById('dd'+dn+'q'+qn).style.visibility='hidden';
	//document.getElementById('ttq'+qn).style.borderBottom="0px solid #ffffff";
	
	for(i=1;i<=5;i++)
	{
		if(i==5)
		{
			if(loggedin==0 || bestansselected==1 ||questposter==0){
				continue;
			}
		}
		//document.getElementById('tt'+i+'q'+qn).style.borderBottom="1px solid #b5d6ef";
	}

	//document.getElementById('tt'+dn+'q'+qn).style.background="#ffffff";
}

function showMore(divExt,divMain)
{
	document.getElementById(divMain).style.display="none";
	document.getElementById(divMain).style.visibility="hidden";
	document.getElementById(divExt).style.display="block";
	document.getElementById(divExt).style.visibility="visible";
}

function closeMore(divExt,divMain)
{
	document.getElementById(divMain).style.display="block";
	document.getElementById(divMain).style.visibility="visible";
	document.getElementById(divExt).style.display="none";
	document.getElementById(divExt).style.visibility="hidden";
}

function showhideKM(dvnm,lk)
{
	var Ltext=document.getElementById(lk).innerHTML;
	if (Ltext=='Close')
	{
	document.getElementById(dvnm).style.display='none';
	document.getElementById(dvnm).style.visibility='hidden';
	document.getElementById(lk).innerHTML="more"
	}
	else
	{
	document.getElementById(dvnm).style.display='block';
	document.getElementById(dvnm).style.visibility='visible';
	document.getElementById(lk).innerHTML="Close"
	}
}

//-->


function showCancel(nm)
{

	if (nm==2)
	{
		document.getElementById('giveAns').style.display='block';
		document.getElementById('giveAns').style.visibility='visible';
		document.getElementById('AnsBtn').style.display='none';
		document.getElementById('AnsBtn').style.visibility='hidden';
		document.getElementById('AnsTxt').style.display='block';
		document.getElementById('AnsTxt').style.visibility='visible';
	}	
	else
	{
		document.getElementById('subAns').style.display='none';
		document.getElementById('subAns').style.visibility='hidden';
		document.getElementById('giveAns').style.display='none';
		document.getElementById('giveAns').style.visibility='hidden';
		document.getElementById('AnsBtn').style.display='block';
		document.getElementById('AnsBtn').style.visibility='visible';
		document.getElementById('AnsTxt').style.display='none';
		document.getElementById('AnsTxt').style.visibility='hidden';

	}

}

function AddComment(qn)
{
	document.getElementById('AddCommentq'+qn).style.display='block';
	document.getElementById('AddCommentq'+qn).style.visibility='visible';
	document.getElementById('btnCommentq'+qn).style.display='none';
	document.getElementById('btnCommentq'+qn).style.visibility='hidden';
	document.getElementById('commentTxt'+qn).style.visibility='visible';	
	document.getElementById('commentTxt'+qn).style.display='block';	
}

function cancelComment(qn)
{
	document.getElementById("CommentAdded"+qn).style.display="none";
	document.getElementById("CommentAdded"+qn).style.visibility="hidden";
	document.getElementById('AddCommentq'+qn).style.display='none';
	document.getElementById('AddCommentq'+qn).style.visibility='hidden';
	document.getElementById('btnCommentq'+qn).style.display='block';
	document.getElementById('btnCommentq'+qn).style.visibility='visible';
	document.getElementById('commentTxt'+qn).style.display='none';	
	document.getElementById('commentTxt'+qn).style.visibility='hidden';	
}

function calcansCharLeft() {

	clipped = false
	lenUSig = 0
	maxLength = 4000

	if (document.getElementById("answer").value.length > maxLength) {
		document.getElementById("answer").value = document.getElementById("answer").value.substring(0,maxLength)
		charleft = 0
		clipped = true
	} 
	else {
		charleft = maxLength - document.getElementById("answer").value.length
	}

	document.getElementById("countBody").innerHTML = charleft
	return clipped

}
function answerKey() {
	supportsKeys = true
	calcansCharLeft()
}


function calcRateCharLeft(ansid) {

	clipped = false
	lenUSig = 0
	maxLength = 300

	if (document.getElementById("ratecomment"+ansid).value.length > maxLength) {
		document.getElementById("ratecomment"+ansid).value = document.getElementById("ratecomment"+ansid).value.substring(0,maxLength)
		charleft = 0
		clipped = true
	} 
	else {
		charleft = maxLength - document.getElementById("ratecomment"+ansid).value.length
	}

	document.getElementById("rate1"+ansid).innerHTML = charleft
	return clipped

}
function rateKey(ansid) {
	supportsKeys = true
	calcRateCharLeft(ansid)
}

function calcaddCharLeft(ansid) {

	clipped = false
	lenUSig = 0
	maxLength = 300

	if (document.getElementById("addcomment"+ansid).value.length > maxLength) {
		document.getElementById("addcomment"+ansid).value = document.getElementById("addcomment"+ansid).value.substring(0,maxLength)
		charleft = 0
		clipped = true
	} 
	else {
		charleft = maxLength - document.getElementById("addcomment"+ansid).value.length
	}

	document.getElementById("add"+ansid).innerHTML = charleft
	return clipped

}
function addKey(ansid) {
	supportsKeys = true
	calcaddCharLeft(ansid)
}


function hiderateDiv(ansid){
	document.getElementById("rate"+ansid).innerHTML="";
	document.getElementById("rate"+ansid).style.visibility="hidden";
	document.getElementById("rate"+ansid).style.display="none";
}

function showAnydiv(divName1,divName2,divName3,link1,link2,link3)
{
	document.getElementById(divName1).style.display='block';
	document.getElementById(divName1).style.visibility='visible';
	document.getElementById(divName2).style.display='none';
	document.getElementById(divName2).style.visibility='hidden';
	document.getElementById(divName3).style.display='none';
	document.getElementById(divName3).style.visibility='hidden';
	document.getElementById(link1).className="srchlinksel1";
	document.getElementById(link2).className="srchlink1";
	document.getElementById(link3).className="srchlink1";
}

function hideAnydiv(divName1)
{
	document.getElementById(divName1).style.display='none';
	document.getElementById(divName1).style.visibility='hidden';
}

function showDivAbuse(nm,ans)
{
	document.getElementById('divAbuse'+nm+ans).style.display='block';
	document.getElementById('divAbuse'+nm+ans).style.visibility='visible';
}

function hideDivAbuse(nm,ans)
{
	document.getElementById('divAbuse'+nm+ans).style.display='none';
	document.getElementById('divAbuse'+nm+ans).style.visibility='hidden';
}


var reqs;
var cc=window;
var ce=eval;
var u=true;
var gmessageid;
var messageid;
var gansid,gcomid,gquestid;

var gtimer;
var gtimeoutms = 15000;


function trim(strText) { 
    // this will get rid of leading spaces 
    while (strText.substring(0,1) == ' ') 
        strText = strText.substring(1, strText.length);

    // this will get rid of trailing spaces 
    while (strText.substring(strText.length-1,strText.length) == ' ')
        strText = strText.substring(0, strText.length-1);

   return strText;
} 

function getCookie(c_name)
{
if (document.cookie.length>0)
  {
  c_start=document.cookie.indexOf(c_name + "=")
  if (c_start!=-1)
    { 
    c_start=c_start + c_name.length+1 
    c_end=document.cookie.indexOf(";",c_start)
    if (c_end==-1) c_end=document.cookie.length
    return unescape(document.cookie.substring(c_start,c_end))
    } 
  }
return "";
}

function checkCookie()
{	
	username=getCookie('Rlo')
	quserid=getCookie('QNAuserid')
	imgpath='http://im.rediff.com/qna/pix';
	if (quserid!=null && quserid!="")
	{
		username = username.replace(/\+/g,' ');

		var loginstatusstr;
		loginstatusstr = '';
		loginstatusstr += '<table border="0" cellpadding="0" cellspacing="0">';
		loginstatusstr += '<tbody><tr class="ftr11">';
		loginstatusstr += '<td>&nbsp;  <b>Hi <font class="fntRed">'+username+' </font></b> &nbsp; | &nbsp;</td>';
		loginstatusstr += '<td valign="bottom">&nbsp;<a href="Main.php?do=qnaprofile&userid='+quserid+'">';
		loginstatusstr += '<img title="My Q&A" src="'+imgpath+'/MyQnAbtn.gif" border="0" hspace="0" vspace="0">&nbsp;</a></td>';
		loginstatusstr += '<td>&nbsp;  |  &nbsp;  <a href="Main.php">Home</a></td>';
		loginstatusstr += '<td>&nbsp;  |  &nbsp;  <a href="Main.php?do=feedback">Feedback</a>  &nbsp;  | &nbsp;  </td>';
		loginstatusstr += '<td><a href="faq.html">Help</a>  &nbsp;  | &nbsp; <a href="Main.php?do=logout"><b>Sign Out</b></a></td>';
		loginstatusstr += '</tr></tbody></table>';	

		gr("loginstatus").innerHTML = loginstatusstr;

	}
	return;

}

function callURL(req,requrl,method,processor)
{
    var senddata;
	if(method=="POST")
	{
		var ar = requrl.split("?", 2);
		senddata = ar[1];
		requrl = ar[0];
	}
    if(cc.XMLHttpRequest)
    {
        ce(req+" = new XMLHttpRequest();");
        ce(req).onreadystatechange=processor;
        ce(req).open(method,requrl,u);
        if(method=="POST")
        {
            ce(req).setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            ce(req).send(senddata);
        }
        else
            ce(req).send(null);
    }
    else if(cc.ActiveXObject)
    {
        isIE=u;
        ce(req+" = new ActiveXObject(\"Microsoft.XMLHTTP\");");
        if(ce(req))
        {
            ce(req).onreadystatechange=processor;
            ce(req).open(method,requrl,u);
            if(method=="POST")
            {
                ce(req).setRequestHeader("Content-type", "application/x-www-form-urlencoded");
                ce(req).send(senddata);
            }
            else
                ce(req).send();
        }
    }
    else
    {mull=0}
}

function postlogin(){

	var url = "";

	var rediff_id = gr("rediff_id").value;
	var rediff_pass = gr("rediff_pass").value;
	var questid = gr("questid").value;

	if(trim(rediff_id) == "" || trim(rediff_pass) == ""){
		alert('Rediff id or Password field blank');
		return false;
	}

	url += "rediff_id="+escape(rediff_id)+"&";
	url += "rediff_pass="+escape(rediff_pass)+"&";
	url += "questid="+questid;

	gr('div_postlogin').style.visibility='hidden';
	gr('div_postlogin').style.display='none';
	gr('div_postloginhid').style.visibility='visible';
	gr('div_postloginhid').style.display='block';
	gr('div_postloginhid').innerHTML = "<font class=sb2>Please wait...</font>";
	//alert(url);return false;

	callURL("reqs","Main.php?do=postlogin&"+url, "POST", processPostlogin);

}

function processPostlogin()
{
	if(reqs.readyState==4)
	{
		if(reqs.status ==200)
		{
			if(reqs.responseText)
			{
				//alert(reqs.responseText);

				if(reqs.responseText == "ERR1"){
					alert("Username or Password field cannot be left blank");
					gr('div_postloginhid').style.visibility='hidden';
					gr('div_postloginhid').style.display='none';
					gr('div_postlogin').style.visibility='visible';
					gr('div_postlogin').style.display='block';
				}
				else if(reqs.responseText == "ERR2"){
					alert("Sorry! You may have entered incorrect user Id or Password. Please try again.");
					gr('div_postloginhid').style.visibility='hidden';
					gr('div_postloginhid').style.display='none';
					gr('div_postlogin').style.visibility='visible';
					gr('div_postlogin').style.display='block';
				}
				else if(reqs.responseText == "ERR6"){
					alert("You have been Blocked by the moderator.");
					gr('div_postloginhid').style.visibility='hidden';
					gr('div_postloginhid').style.display='none';
					gr('div_postlogin').style.visibility='visible';
					gr('div_postlogin').style.display='block';
				}
				else if(reqs.responseText == "ERR3"){
					alert("You have reached the limit for asking questions in a day.");
					gr('div_postloginhid').style.visibility='hidden';
					gr('div_postloginhid').style.display='none';
					gr('div_postlogin').style.visibility='visible';
					gr('div_postlogin').style.display='block';
				}
				else{
					//alert(reqs.responseText);
					gr('div_postloginhid').style.visibility='hidden';
					gr('div_postloginhid').style.display='none';
					gr('div_postlogin').style.visibility='visible';
					gr('div_postlogin').style.display='block';
					gr('div_postlogin').innerHTML=reqs.responseText;
					
					checkCookie();
				}


			}
		}
	}

}



var bansid;
function announcebestans(questid,ansid){

	var url = "";
	url += "questid="+questid+"&";
	url += "ansid="+ansid;

	gr('dd5q'+ansid).innerHTML = "<font class=sb2>Please wait...</font>";
	//alert(url);return false;

	callURL("reqs","Main.php?do=bestanswer&"+url, "POST", processBestans);
	bansid = ansid;

}

function processBestans()
{
	if(reqs.readyState==4)
	{
		if(reqs.status ==200)
		{
			if(reqs.responseText)
			{
				if(reqs.responseText == "ERR3"){
					alert("You are not logged in.");
				}
				else if(reqs.responseText == "ERR6"){
					alert("You have been Blocked by the moderator.");
				}
				else if(reqs.responseText == "ERR5"){
					alert("Best answer for this question has already been selected.");
				}
				else{
					//alert(reqs.responseText);
					gr('dd5q'+bansid).innerHTML=reqs.responseText;
					bansid = "";
					checkCookie();
				}

			}
		}
	}
}


var ansid = "";

function postRate(formid)
{
	var formelement = eval("document.sub_rate"+formid);
	var url = "";
	var rediff_id = formelement.rediff_id.value;
	var rediff_pass = formelement.rediff_pass.value;
	var comment = formelement.comment.value;
	var loggedin = formelement.loggedin.value;
	var ansid = formelement.ansid.value;
	var catid = formelement.catid.value;
	var questid = formelement.questid.value;

	var rate_checked	= gr("r1_"+ansid).checked;
	if(rate_checked){
		var rateid = 1;	
	}
	else{
		var rateid = 0;	
	}
	if(loggedin != 1){
		if(trim(rediff_id) == "" || trim(rediff_pass) == ""){
			alert('Rediff id or Password field blank');
			return false;
		}
	}

	url += "rediff_id="+escape(rediff_id)+"&";
	url += "rediff_pass="+escape(rediff_pass)+"&";
	url += "comment="+escape(comment)+"&";
	url += "loggedin="+loggedin+"&";
	url += "ansid="+ansid+"&";
	url += "catid="+catid+"&";
	url += "questid="+questid+"&";
	url += "rateid="+rateid;

	gr('dd1q'+ansid).style.visibility='hidden';
	gr('dd1q'+ansid).style.display='none';
	gr("rate"+ansid).style.visibility="visible";
	gr("rate"+ansid).style.display="block";

	gr("rate"+ansid).innerHTML="<font class=sb2>Please wait...</font>";

	//alert("Main.php?do=submitcomment&"+url);
	callURL("reqs","Main.php?do=submitcomment&"+url, "POST", processPostRate);
	gansid = ansid;
}

function processPostRate()
{
	if(reqs.readyState==4)
	{
		if(reqs.status ==200)
		{
			if(reqs.responseText)
			{
				gr('rate'+gansid).style.visibility='hidden';
				gr('rate'+gansid).style.display='none';

				if(reqs.responseText == "ERR1"){
					alert("Username or Password field cannot be left blank");
					gr('dd1q'+gansid).style.visibility='visible';
					gr('dd1q'+gansid).style.display='block';
				}
				else if(reqs.responseText == "ERR2"){
					alert("Sorry! You may have entered incorrect user Id or Password. Please try again.");
					gr('dd1q'+gansid).style.visibility='visible';
					gr('dd1q'+gansid).style.display='block';
				}
				else if(reqs.responseText == "ERR3"){
					alert("Comment field cannot be left blank");
					gr('dd1q'+gansid).style.visibility='visible';
					gr('dd1q'+gansid).style.display='block';
				}
				else if(reqs.responseText == "ERR4"){
					alert("Please select an option");
					gr('dd1q'+gansid).style.visibility='visible';
					gr('dd1q'+gansid).style.display='block';
				}
				else if(reqs.responseText == "ERR5"){
					alert("You can vote only once. Multiple votes for answers are not allowed.");
				}
				else if(reqs.responseText == "ERR6"){
					alert("You cannot vote on your own answers.");
				}
				else if(reqs.responseText == "ERR7"){
					alert("You have been Blocked by the moderator.");
				}
				else if(reqs.responseText == "ERR8"){
					alert("You have reached the limit for ratings in a day.");
				}
				else{
					//alert(reqs.responseText); 
					gr('dd1q'+gansid).style.display='none';
					gr('dd1q'+gansid).style.visibility='hidden';
					gr("rate"+gansid).style.visibility="visible";
					gr("rate"+gansid).style.display="block";
					gr("rate"+gansid).innerHTML=reqs.responseText;
					var cnt1 = trim(gr("votecnt"+gansid).innerHTML);
					var cnt;
					cnt = parseInt(cnt1);
					cnt = cnt+1;
					gr("votecnt"+gansid).innerHTML=cnt;
					gansid = "";
					//reposition();
					checkCookie();
				}
			}
		}
	}
}

function getrefererquestarray(referer,frm,pageno)
{
	gr("refererquestlist").innerHTML="<font class=sb2>Please wait...</font>";
	//alert("Main.php?do=getrefererquest&referer="+referer+"&frm="+frm+"&pageno="+pageno);
	callURL("reqs","Main.php?do=getrefererquest&referer="+referer+"&frm="+frm+"&pageno="+pageno, "POST", processGetrefererquestarray);
}

function processGetrefererquestarray()
{
	if(reqs.readyState==4)
	{
		if(reqs.status ==200)
		{
			if(reqs.responseText)
			{
				//alert(reqs.responseText);
				if(reqs.responseText == "NOQUES"){
					gr("refererquestlist").innerHTML="<table width='100%'><tr><td align='center'>No questions present</td></tr></table>";
				}
				else{
					gr("refererquestlist").innerHTML=reqs.responseText;
				}

			}
		}
	}
}


function getquesrecent()
{
	var val = gr("dd1").innerHTML;
	if(trim(val) != "") return;

	gr("dd1").innerHTML="<font class=sb2>Please wait...</font>";

	callURL("reqs","Main.php?do=recent_top_unans&type=recent&all=0", "POST", processGetquesrecent);

}


function processGetquesrecent()
{
	if(reqs.readyState==4)
	{
		if(reqs.status ==200)
		{
			if(reqs.responseText)
			{
				//alert(reqs.responseText);
				if(reqs.responseText == "NOQUES"){
					gr("dd1").innerHTML="<table width='100%'><tr><td align='center'>No questions present</td></tr></table>";
				}
				else{
					gr("dd1").innerHTML=reqs.responseText;
				}
				//reposition();

			}
		}
	}
}

function getquesunanswered()
{
	var val = gr("dd2").innerHTML;
	if(trim(val) != "") return;

	gr("dd2").innerHTML="<table height='100' width='100%'><tr><td align='center'><font class=sb2>Please wait...</font></td></tr></table>";

	callURL("reqs","Main.php?do=recent_top_unans&type=unanswered&all=0", "POST", processGetquesunanswered);

}


function processGetquesunanswered()
{
	if(reqs.readyState==4)
	{
		if(reqs.status ==200)
		{
			if(reqs.responseText)
			{
				//alert(reqs.responseText);
				if(reqs.responseText == "NOQUES"){
					gr("dd2").innerHTML="<table width='100%'><tr><td align='center'>No questions present</td></tr></table>";
				}
				else{
					gr("dd2").innerHTML=reqs.responseText;
				}

			}
		}
	}
}

function getquesbestanswered()
{
	var val = gr("dd3").innerHTML;
	if(trim(val) != "") return;

	gr("dd3").innerHTML="<table height='100' width='100%'><tr><td align='center'><font class=sb2>Please wait...</font></td></tr></table>";

	callURL("reqs","Main.php?do=recent_top_unans&type=best&all=0", "POST", processGetquesbestanswered);

}

function processGetquesbestanswered()
{
	if(reqs.readyState==4)
	{
		if(reqs.status ==200)
		{
			if(reqs.responseText)
			{
				//alert(reqs.responseText);
				if(reqs.responseText == "NOQUES"){
					gr("dd3").innerHTML="<table width='100%'><tr><td align='center'>No questions present</td></tr></table>";
				}
				else{
					gr("dd3").innerHTML=reqs.responseText;
				}

			}
		}
	}
}

var duserid;
var gpageno;
function deletepubmessages(userid,delmsgid,pageno)
{
	callURL("reqs","Main.php?do=qnamessages&userid="+userid+"&delmsgid="+delmsgid+"&process=2", "POST", processDeletepubmessages);
	duserid = userid;
	gpageno = pageno;

}

function processDeletepubmessages()
{
	if(reqs.readyState==4)
	{
		if(reqs.status ==200)
		{
			if(reqs.responseText)
			{

				if(reqs.responseText == "ERR1"){
					alert("Username or Password field cannot be left blank");
				}
				else if(reqs.responseText == "ERR2"){
					alert("Blocked or Invalid User");
				}
				else{
					//alert(reqs.responseText);
					gr("div_delmess").style.visibility="visible";
					gr("div_delmess").style.display="block";
					gr("div_delmess").innerHTML=reqs.responseText;
					//reposition();
					var cnt1 = trim(gr("scrapcount").innerHTML);
					var cnt;
					cnt = parseInt(cnt1);
					cnt = cnt-1;
					gr("scrapcount").innerHTML=cnt;

					getmessages(duserid,gpageno)

				}
			}
		}
	}
}


function deletealert(userid,questid)
{
	callURL("reqs","Main.php?do=qnadeletealert&userid="+userid+"&questid="+questid, "POST", processDeletealert);
}

function processDeletealert()
{
	if(reqs.readyState==4)
	{
		if(reqs.status ==200)
		{
			if(reqs.responseText)
			{
				if(reqs.responseText == "ERR1"){
					alert("Username or Password field cannot be left blank");
				}
				else if(reqs.responseText == "ERR2"){
					alert("Blocked or Invalid User");
				}
				else{
					//alert(reqs.responseText);
					//gr("dd3").innerHTML=reqs.responseText;
				}
			}
		}
	}
}

function getmybestanswers(userid,pageno)
{
	gr("dd1").innerHTML="<font class=sb2>Please wait...</font>";

	callURL("reqs","Main.php?do=getmyanswers&userid="+userid+"&pageno="+pageno+"&bestanswer=1", "POST", processMyanswers);
}

function getmyanswers(userid,pageno)
{
	gr("dd1").innerHTML="<font class=sb2>Please wait...</font>";

	callURL("reqs","Main.php?do=getmyanswers&userid="+userid+"&pageno="+pageno, "POST", processMyanswers);
}

function processMyanswers()
{
	if(reqs.readyState==4)
	{
		if(reqs.status ==200)
		{
			if(reqs.responseText)
			{
				//alert(reqs.responseText);
				if(reqs.responseText == "ERR1"){
					alert("Username or Password field cannot be left blank");
				}
				else if(reqs.responseText == "ERR2"){
					alert("Blocked or Invalid User");
				}
				else{
					//alert(reqs.responseText);
					gr("dd1").innerHTML=reqs.responseText;
				}
			}
		}
	}
}


function getmessages(userid,pageno)
{
	callURL("reqs","Main.php?do=qnamessages&userid="+userid+"&pageno="+pageno, "POST", processQnamessages);
}

function processQnamessages()
{
	if(reqs.readyState==4)
	{
		if(reqs.status ==200)
		{
			if(reqs.responseText)
			{
				gr("div_below").innerHTML=reqs.responseText;
			}
		}
	}
}



function qnashowalert(userid,pageno)
{
	callURL("reqs","Main.php?do=qnaalert&userid="+userid+"&pageno="+pageno, "POST", processGetalert);
}

function processGetalert()
{
	if(reqs.readyState==4)
	{
		if(reqs.status ==200)
		{
			if(reqs.responseText)
			{

				if(reqs.responseText == "ERR1"){
					alert("Username or Password field cannot be left blank");
				}
				else if(reqs.responseText == "ERR2"){
					alert("Blocked or Invalid User");
				}
				else{
					//alert(reqs.responseText);
					gr("dd3").innerHTML=reqs.responseText;
				}
			}
		}
	}
}


var div_bansid;
function displaydivBestans(questid,ansid){

	var url = "";
	url += "questid="+questid;
	url += "&ansid="+ansid;
	url += "&showdiv=1";

	gr("dd5q"+ansid).innerHTML="<font class=sb2>Please wait...</font>";
	callURL("reqs","Main.php?do=bestanswer&"+url, "POST", processdisplaydivBestans);
	div_bansid = ansid;
}

function processdisplaydivBestans()
{
	if(reqs.readyState==4)
	{
		if(reqs.status ==200)
		{
			if(reqs.responseText)
			{
				//alert(reqs.responseText);
				gr("dd5q"+div_bansid).innerHTML=reqs.responseText;

			}
		}
	}
}



function displaydivAns(catid,questid){

	var url = "";
	url += "catid="+catid;
	url += "&questid="+questid;
	url += "&showdiv=1";

	gr("giveAns").innerHTML="<font class=sb2>Please wait...</font>";
	callURL("reqs","Main.php?do=submitanswer&"+url, "POST", processdisplaydivAns);
}

function processdisplaydivAns()
{
	if(reqs.readyState==4)
	{
		if(reqs.status ==200)
		{
			if(reqs.responseText)
			{
				//alert(reqs.responseText);
				gr("giveAns").innerHTML=reqs.responseText;

			}
		}
	}
}


function displaydivalert(questid){

	var url = "";
	url += "questid="+questid;
	url += "&showdiv=1";

	divcontent = gr("divAlertMe").innerHTML;
	gr("divAlertMe").innerHTML="<font class=sb2>Please wait...</font>";
	//alert("Main.php?do=setalert&"+url);
	callURL("reqs","Main.php?do=setalert&"+url, "POST", processdisplaydivalert);
}

function processdisplaydivalert()
{
	if(reqs.readyState==4)
	{
		if(reqs.status ==200)
		{
			if(reqs.responseText)
			{
				//alert(reqs.responseText);
				gr("divAlertMe").innerHTML=reqs.responseText;

			}
		}
	}
}


var gdivcontent;

function setalert()
{

	var url = "";
	var rediff_id = gr("rediff_id").value;
	var rediff_pass = gr("rediff_pass").value;
	var loggedin = gr("loggedin").value;
	var questid = gr("questid").value;

	if(loggedin != 1){
		if(trim(rediff_id) == "" || trim(rediff_pass) == ""){
			alert('Rediff id or Password field blank');
			return false;
		}
	}

	url += "rediff_id="+escape(rediff_id)+"&";
	url += "rediff_pass="+escape(rediff_pass)+"&";
	url += "loggedin="+loggedin+"&";
	url += "questid="+questid;

	divcontent = gr("divAlertMe").innerHTML;
	gr("divAlertMe").innerHTML="<font class=sb2>Please wait...</font>";
	//alert("Main.php?do=setalert&"+url);
	callURL("reqs","Main.php?do=setalert&"+url, "POST", processsetalert);
	//gansid = ansid;
	gdivcontent = divcontent;

}

function processsetalert()
{
	if(reqs.readyState==4)
	{
		if(reqs.status ==200)
		{
			if(reqs.responseText)
			{
				gr("divAlertMe").innerHTML="";
				if(reqs.responseText == "ERR1"){
					alert("Username or Password field cannot be left blank");
					gr("divAlertMe").innerHTML=gdivcontent;
				}
				else if(reqs.responseText == "ERR2"){
					alert("Blocked or Invalid User");
					gr("divAlertMe").innerHTML=gdivcontent;
				}
				else{
					//alert(reqs.responseText);
					gr("divAlertMe").innerHTML=reqs.responseText;

					checkCookie();
				}
			}
		}
	}
}

var divcansid;
function displaydivAddcomment(questid,ansid,catid){

	var url = "";
	url += "questid="+questid+"&";
	url += "ansid="+ansid+"&";
	url += "catid="+catid+"&";
	url += "showdiv=2";

	gr("AddCommentq"+ansid).innerHTML="<font class=sb2>Please wait...</font>";
	callURL("reqs","Main.php?do=getcomments&"+url, "POST", processdisplaydivAddcomment);
	divcansid = ansid;
}

function processdisplaydivAddcomment()
{
	if(reqs.readyState==4)
	{
		if(reqs.status ==200)
		{
			if(reqs.responseText)
			{
				//alert(reqs.responseText);
				gr("AddCommentq"+divcansid).innerHTML=reqs.responseText;

			}
		}
	}
}

var divransid;
function displaydivRate(questid,ansid,catid){

	var url = "";
	url += "questid="+questid+"&";
	url += "ansid="+ansid+"&";
	url += "catid="+catid+"&";
	url += "showdiv=1";

	gr("dd1q"+ansid).innerHTML="<font class=sb2>Please wait...</font>";
	callURL("reqs","Main.php?do=getcomments&"+url, "POST", processdisplaydivRate);
	divransid = ansid;
}


function processdisplaydivRate()
{
	if(reqs.readyState==4)
	{
		if(reqs.status ==200)
		{
			if(reqs.responseText)
			{
				//alert(reqs.responseText);
				gr("dd1q"+divransid).innerHTML=reqs.responseText;

			}
		}
	}
}

var divfansid;
function displaydivForward(questid,ansid){

	var url = "";
	url += "questid="+questid+"&";
	url += "ansid="+ansid+"&";
	url += "showdiv=1";

	gr("dd4q"+ansid).innerHTML="<font class=sb2>Please wait...</font>";
	callURL("reqs","Main.php?do=forwardmsg&"+url, "POST", processdisplaydivForward);
	divfansid = ansid;
}

function processdisplaydivForward()
{
	if(reqs.readyState==4)
	{
		if(reqs.status ==200)
		{
			if(reqs.responseText)
			{
				//alert(reqs.responseText);
				gr("dd4q"+divfansid).innerHTML=reqs.responseText;

			}
		}
	}
}

function displaycomAbuse(questid,ansid,comid)
{
	gr("divAbuse"+ansid+comid).innerHTML= "<font class=sb2>Please wait...</font>";
	var url = "";
	url += "qid="+questid+"&";
	url += "aid="+ansid+"&";
	url += "cid="+comid;
//	alert("Main.php?do=reportabuse&new=1&"+url);
	callURL("reqs","Main.php?do=reportabuse&new=1&"+url, "POST", processdisplaycomAbuse);
	gansid = ansid;
	gcomid = comid;

}

function processdisplaycomAbuse()
{
	if(reqs.readyState==4)
	{
		if(reqs.status ==200)
		{
			if(reqs.responseText)
			{
//				alert(reqs.responseText);
				document.getElementById("divAbuse"+gansid+gcomid).innerHTML=reqs.responseText;
				//reposition();
			}
		}
	}
}


function displayquesAbuse(questid)
{
	gr("divReportAbu").innerHTML = "<font class=sb2>Please wait...</font>";
	var url = "";
	url += "qid="+questid;
//	alert("Main.php?do=reportabuse&new=1&"+url);
	callURL("reqs","Main.php?do=reportabuse&new=1&"+url, "POST", processdisplayquesAbuse);

}

function processdisplayquesAbuse()
{
	if(reqs.readyState==4)
	{
		if(reqs.status ==200)
		{
			if(reqs.responseText)
			{
				//alert(reqs.responseText);
				document.getElementById("divReportAbu").innerHTML=reqs.responseText;
				//reposition();
			}
		}
	}
}

var ab_ansid; 
function displayAbuse2(questid,ansid)
{

	var url = "";
	url += "qid="+questid+"&";
	url += "aid="+ansid;
//	alert("Main.php?do=reportabuse&new=1&"+url);
	callURL("reqs","Main.php?do=reportabuse&new=1&"+url, "POST", processdisplayAbuse2);
	ab_ansid = ansid;
}

function processdisplayAbuse2()
{
	if(reqs.readyState==4)
	{
		if(reqs.status ==200)
		{
			if(reqs.responseText)
			{
				//alert(reqs.responseText + "\n" + "dd3q"+ansid);
				document.getElementById("dd3q"+ab_ansid).innerHTML=reqs.responseText;
				ab_ansid = "";
				//showLoginName();
				//reposition();
			}
		}
	}
}

function displayAbuse(questid,aid)
{
	gr("dd3q"+aid).innerHTML= "<font class=sb2>Please wait...</font>";

	var url = "";
	url += "qid="+questid+"&";
	url += "aid="+aid;
	callURL("reqs","Main.php?do=reportabuse&new=1&"+url, "POST", processdisplayAbuse);
	ansid = aid;
}

function processdisplayAbuse()
{
	if(reqs.readyState==4)
	{
		if(reqs.status ==200)
		{
			if(reqs.responseText)
			{
				document.getElementById("dd3q"+ansid).innerHTML=reqs.responseText;
				//ab_ansid = "";
				checkCookie();
			}
		}
	}
}

function postComment(ansid)
{
	var url = "";

	var rediff_id = gr("rediff_id_sub_com"+ansid).value;
	var rediff_pass = gr("rediff_pass_sub_com"+ansid).value;
	var comment = gr("addcomment"+ansid).value;
	var loggedin = gr("loggedin_sub_com"+ansid).value;
	var ansid = gr("ansid_sub_com"+ansid).value;
	var catid = gr("catid_sub_com"+ansid).value;
	var questid = gr("questid_sub_com"+ansid).value;

	if(loggedin != 1){
		if(trim(rediff_id) == "" || trim(rediff_pass) == ""){
			alert('Rediff id or Password field blank');
			return false;
		}
	}
	if(trim(comment) == ""){
		alert('Comment field cannot be left blank');
		return false;
	}

	gr('AddCommentq'+ansid).style.visibility='hidden';
	gr('AddCommentq'+ansid).style.display='none';
	gr("CommentAdded"+ansid).style.visibility="visible";
	gr("CommentAdded"+ansid).style.display="block";

	gr("CommentAdded"+ansid).innerHTML="<font class=sb2>Please wait...</font>";

	url += "rediff_id="+escape(rediff_id)+"&";
	url += "rediff_pass="+escape(rediff_pass)+"&";
	url += "comment="+encodeURIComponent(comment)+"&";
	url += "loggedin="+loggedin+"&";
	url += "ansid="+ansid+"&";
	url += "questid="+questid+"&";
	url += "catid="+catid;

	//alert("Main.php?do=submitcomment&"+url);
	callURL("reqs","Main.php?do=submitcomment&"+url, "POST", processPostComment);
	gansid = ansid;
	gquestid = questid;
}

function processPostComment()
{
	if(reqs.readyState==4)
	{
		if(reqs.status ==200)
		{
			if(reqs.responseText)
			{
				gr("CommentAdded"+gansid).style.visibility="hidden";
				gr("CommentAdded"+gansid).style.display="none";

				if(reqs.responseText == "ERR1"){
					alert("Username or Password field cannot be left blank");
					gr("AddCommentq"+gansid).style.visibility="visible";
					gr("AddCommentq"+gansid).style.display="block";
				}
				else if(reqs.responseText == "ERR2"){
					alert("Sorry! You may have entered incorrect user Id or Password. Please try again.");
					gr("AddCommentq"+gansid).style.visibility="visible";
					gr("AddCommentq"+gansid).style.display="block";
				}
				else if(reqs.responseText == "ERR3"){
					alert("Comment field cannot be left blank");
					gr("AddCommentq"+gansid).style.visibility="visible";
					gr("AddCommentq"+gansid).style.display="block";
				}
				else if(reqs.responseText == "ERR7"){
					alert("You have been Blocked by the moderator.");
				}
				else if(reqs.responseText == "ERR8"){
					alert("You have reached the limit for comments in a day.");
				}
				else{
					//alert(reqs.responseText);
					gr('AddCommentq'+gansid).style.display='none';
					gr('AddCommentq'+gansid).style.visibility='hidden';
					gr("CommentAdded"+gansid).style.visibility="visible";
					gr("CommentAdded"+gansid).style.display="block";
					gr("CommentAdded"+gansid).innerHTML=reqs.responseText;
					var cnt1 = trim(gr("comcnt"+gansid).innerHTML);
					var cnt;
					cnt = parseInt(cnt1);
					cnt = cnt+1;
					gr("comcnt"+gansid).innerHTML=cnt;
					getcomments(gquestid,gansid,"addCom");
					gansid = "";
					gquestid = "";
					//reposition();
					checkCookie();
				}
			}
		}
	}
}

var guserid;

function postpubMessage()
{
	var url = "";
	var loggedin = gr('loggedin').value;
	var rediff_id = gr('rediff_id').value;
	var rediff_pass = gr('rediff_pass').value;
	var msgreceiverid = gr('msgreceiverid').value;
	var msgsenderid = gr('msgsenderid').value;
	var message = gr('message').value;

	if(loggedin != 1){
		if(trim(rediff_id) == "" || trim(rediff_pass) == ""){
			alert('Rediff id or Password field blank');
			return false;
		}
	}
	if(trim(message) == ""){
		alert('Message field cannot be left blank');
		return false;
	}

	gr("div_above").style.visibility="hidden";
	gr("div_above").style.display="none";
	gr("div_above_afterpost").innerHTML="<font class=sb2>Please wait...</font>";

	url += "rediff_id="+escape(rediff_id)+"&";
	url += "rediff_pass="+escape(rediff_pass)+"&";
	url += "message="+encodeURIComponent(message)+"&";
	url += "loggedin="+loggedin+"&";
	url += "msgreceiverid="+msgreceiverid+"&";
	url += "msgsenderid="+msgsenderid+"&";
	url += "process=1";
	//alert(url);return false;
	callURL("reqs","Main.php?do=qnamessages&"+url, "POST", processPostpubMessage);
	guserid = msgreceiverid;
}

function processPostpubMessage()
{
	if(reqs.readyState==4)
	{
		if(reqs.status ==200)
		{
			if(reqs.responseText)
			{
				gr("div_above_afterpost").style.visibility="hidden";
				gr("div_above_afterpost").style.display="none";

				if(reqs.responseText == "ERR1"){
					alert("Username or Password field cannot be left blank");
					gr("div_above").style.visibility="visible";
					gr("div_above").style.display="block";
				}
				else if(reqs.responseText == "ERR2"){
					alert("Sorry! You may have entered incorrect user Id or Password. Please try again.");
					gr("div_above").style.visibility="visible";
					gr("div_above").style.display="block";
				}
				else if(reqs.responseText == "ERR3"){
					alert("Message field cannot be left blank");
					gr("div_above").style.visibility="visible";
					gr("div_above").style.display="block";
				}
				else if(reqs.responseText == "ERR6"){
					alert("You have been Blocked by the moderator.");
					gr("div_above").style.visibility="visible";
					gr("div_above").style.display="block";
				}
				else if(reqs.responseText == "ERR7"){
					alert("You text contains abusive content.");
					gr("div_above").style.visibility="visible";
					gr("div_above").style.display="block";
				}
				else{
					//alert(reqs.responseText);
					gr("div_above_afterpost").style.visibility="visible";
					gr("div_above_afterpost").style.display="block";
					gr("div_above_afterpost").innerHTML= reqs.responseText;

					var cnt1 = trim(gr("scrapcount").innerHTML);
					var cnt;
					cnt = parseInt(cnt1);
					cnt = cnt+1;
					gr("scrapcount").innerHTML=cnt;

					checkCookie();
					getmessages(guserid,1)
				}

			}
		}
	}
}



var fansid;

function Forwardmsg(questid,ansid)
{
	var url = "";

	var loggedin = gr('fques_rediff_loggedin'+ansid).value;
	var rediff_id = gr('fques_rediff_id'+ansid).value;
	var rediff_pass = gr('fques_rediff_pass'+ansid).value;
	var fques_mailids = gr('fques_mailids'+ansid).value;

	if(loggedin != 1){
		if(trim(rediff_id) == "" || trim(rediff_pass) == ""){
			alert('Rediff id or Password field blank');
			return false;
		}
	}
	if(trim(fques_mailids) == ""){
		alert('Emailid field cannot be left blank');
		return false;
	}

	gr('dd4q'+ansid).style.visibility='hidden';
	gr('dd4q'+ansid).style.display='none';
	gr("dd4q"+ansid+"_success").style.visibility="visible";
	gr("dd4q"+ansid+"_success").style.display="block";
	gr("dd4q"+ansid+"_success").innerHTML="<font class=sb2>Please wait...</font>";

	url += "rediff_id="+escape(rediff_id)+"&";
	url += "rediff_pass="+escape(rediff_pass)+"&";
	url += "fques_mailids="+encodeURIComponent(fques_mailids)+"&";
	url += "loggedin="+loggedin+"&";
	url += "questid="+questid+"&";
	url += "ansid="+ansid;
	callURL("reqs","Main.php?do=forwardmsg&"+url, "POST", processForwardmsg);
	fansid = ansid;
}


function processForwardmsg()
{
	if(reqs.readyState==4)
	{
		if(reqs.status ==200)
		{
			if(reqs.responseText)
			{
				gr("dd4q"+fansid+"_success").style.visibility="hidden";
				gr("dd4q"+fansid+"_success").style.display="none";

				if(reqs.responseText == "ERR1"){
					alert("Username or Password field cannot be left blank");
					gr('dd4q'+fansid).style.visibility='visible';
					gr('dd4q'+fansid).style.display='block';
				}
				else if(reqs.responseText == "ERR2"){
					alert("Sorry! You may have entered incorrect user Id or Password. Please try again.");
					gr('dd4q'+fansid).style.visibility='visible';
					gr('dd4q'+fansid).style.display='block';
				}
				else if(reqs.responseText == "ERR3"){
					alert("Emailid field cannot be left blank");
					gr('dd4q'+fansid).style.visibility='visible';
					gr('dd4q'+fansid).style.display='block';
				}
				else if(reqs.responseText == "ERR4"){
					alert("Improper emailid format");
					gr('dd4q'+fansid).style.visibility='visible';
					gr('dd4q'+fansid).style.display='block';
				}
				else if(reqs.responseText == "ERR6"){
					alert("You have been Blocked by the moderator.");
				}
				else if(reqs.responseText == "ERR7"){
					alert("You can forward up to 10 persons at once.");
				}
				else if(reqs.responseText == "ERR8"){
					alert("You have exceeded the daily limit of 50 email forwards for the day.");
				}
				else{
					//alert(reqs.responseText);
			
					gr('dd4q'+fansid).style.visibility='hidden';
					gr('dd4q'+fansid).style.display='none';
					gr("dd4q"+fansid+"_success").style.visibility="visible";
					gr("dd4q"+fansid+"_success").style.display="block";
					gr("dd4q"+fansid+"_success").innerHTML=reqs.responseText;

					checkCookie();
				}

			}
		}
	}
}

function postMessage()
{

	//if(gtimer) clearTimeout(gtimer);

	//gtimer = setTimeout("handleError('postmessage')", gtimeoutms);
	//gtimer = setTimeout("postMessage()", gtimeoutms);

	var url = "";
	var rediff_id = document.submit_ans.rediff_id.value;
	var rediff_pass = document.submit_ans.rediff_pass.value;
	var answer = document.submit_ans.answer.value;
	var loggedin = document.submit_ans.loggedin.value;
	var questid = document.submit_ans.questid.value;
	var catid = document.submit_ans.catid.value;

	if(loggedin != 1){
		if(trim(rediff_id) == "" || trim(rediff_pass) == ""){
			alert('Rediff id or Password field blank');
			return false;
		}
	}
	if(trim(answer) == ""){
		alert('Answer field cannot be left blank');
		return false;
	}

	gr('giveAns').style.visibility='hidden';
	gr('giveAns').style.display='none';
	gr("subAns").style.visibility="visible";
	gr("subAns").style.display="block";
	gr("subAns").innerHTML="<font class=sb2>Please wait...</font>";

	url += "rediff_id="+escape(rediff_id)+"&";
	url += "rediff_pass="+escape(rediff_pass)+"&";
	url += "answer="+encodeURIComponent(answer)+"&";
	url += "loggedin="+loggedin+"&";
	url += "questid="+questid+"&";
	url += "catid="+catid;
	//alert("Main.php?do=submitanswer&"+url);return false;
	callURL("reqs","Main.php?do=submitanswer&"+url, "POST", processPostMessage);
}

function processPostMessage()
{
	if(reqs.readyState==4)
	{
		if(reqs.status ==200)
		{
			//if(gtimer) clearTimeout(gtimer);
			if(reqs.responseText)
			{
				gr("subAns").style.visibility="hidden";
				gr("subAns").style.display="none";

				if(reqs.responseText == "ERR1"){
					alert("Username or Password field cannot be left blank");
					gr('giveAns').style.visibility='visible';
					gr('giveAns').style.display='block';
				}
				else if(reqs.responseText == "ERR2"){
					alert("Sorry! You may have entered incorrect user Id or Password. Please try again.");
					gr('giveAns').style.visibility='visible';
					gr('giveAns').style.display='block';
				}
				else if(reqs.responseText == "ERR3"){
					alert("Answer field cannot be left blank");
					gr('giveAns').style.visibility='visible';
					gr('giveAns').style.display='block';
				}
				else if(reqs.responseText == "ERR4"){
					alert("You cannot answer your own questions.");
				}
				else if(reqs.responseText == "ERR5"){
					alert("You cannot answer a question more than once.");
				}
				else if(reqs.responseText == "ERR6"){
					alert("You have been Blocked by the moderator.");
				}
				else if(reqs.responseText == "ERR7"){
					alert("You have reached the limit for answering in a day.");
				}
				else if(reqs.responseText == "FASTTRACK"){
					document.location.href = "Main.php?do=fasttrack&answer=1";
				}
				else{
					//alert(reqs.responseText);
					gr('giveAns').style.visibility='hidden';
					gr('giveAns').style.display='none';
					gr("subAns").style.visibility="visible";
					gr("subAns").style.display="block";
					gr("subAns").innerHTML=reqs.responseText;

					checkCookie();
				}

			}
		}
	}
}


function showfullmessage(messageid)
{
	var val = gr("ext"+messageid).innerHTML;
	if(trim(val) != "") return;
	//if(gr("ext"+messageid).style.visibility == "visible") return;
	gr("ext"+messageid).innerHTML="<tr><td><font class=sb2>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Fetching more...</font></td></tr>";
	callURL("reqs","Main.php?do=getdata&fullmess=1&messageid="+messageid,"POST",processReqGetMessage);
	gmessageid = messageid;
}

function processReqGetMessage()
{
	var data1;
	if(reqs.readyState==4)
	{
		if(reqs.status==200)
		{
			if(reqs.responseText == "")
				gr("ext"+gmessageid).innerHTML="No message present";
			else
				//alert(reqs.responseText)
				gr("ext"+gmessageid).innerHTML=reqs.responseText;
		}
	}
}

function getcomments(questid,ansid,str)
{
	if(str != "addCom"){
		var val = gr("com"+ansid).innerHTML;
		if(trim(val) != "") return;
	}
	gr("com"+ansid).innerHTML="<font class=sb2>Please wait...</font>";
	callURL("reqs","Main.php?do=getcomments&getcom=1&questid="+questid+"&ansid="+ansid,"POST",processReqGetComments);
	gmessageid = ansid;
}

function processReqGetComments()
{
	var data1;
	if(reqs.readyState==4)
	{
		if(reqs.status==200)
		{
			if(reqs.responseText == "")
				gr("com"+gmessageid).innerHTML="No comments present";
			else
				//alert(reqs.responseText)
				gr("com"+gmessageid).innerHTML=reqs.responseText;

			if(self != parent) parent.resizer(document.body.scrollHeight);
		}
	}
}


function getprofpageques(userid,pageno)
{
	//alert(pageno);
	//if(trim(gr("dd2").innerHTML) != "") return;
	gr("dd2").innerHTML="<font class=sb2>Please wait...</font>";
	callURL("reqs","Main.php?do=getprofpageques&userid="+userid+"&pageno="+pageno, "POST", processProfpageques);
}


function processProfpageques()
{
	if(reqs.readyState==4)
	{
		if(reqs.status ==200)
		{
			if(reqs.responseText)
			{
				//alert(reqs.responseText);

				if(reqs.responseText == "ERR1"){
					alert("Username or Password field cannot be left blank");
				}
				else if(reqs.responseText == "ERR2"){
					alert("Blocked or Invalid User");
				}
				else{
					//alert(reqs.responseText);
					gr("dd2").innerHTML=reqs.responseText;
					//reposition();
				}
			}
		}
	}
}


var gprofquestid;
function getprofpgans(profquestid,userid)
{
	if(gr("ans"+profquestid).style.visibility == "visible") return;
	var val = gr("ans"+profquestid).innerHTML;
	if(trim(val) != "") return;
	gr("ans"+profquestid).innerHTML="<font class=sb2>Fetching replies...</font>";
	callURL("reqs","Main.php?do=getprofpgans&questid="+profquestid+"&userid="+userid,"POST",processProfquestrep);
	gprofquestid = profquestid;
}


function processProfquestrep()
{
	var data1;
	if(reqs.readyState==4)
	{
		if(reqs.status==200)
		{
			//alert(reqs.responseText);
			if(reqs.responseText == "")
				gr("ans"+gprofquestid).innerHTML="No answer present";
			else
				gr("ans"+gprofquestid).innerHTML=reqs.responseText;

		}
	}
}

function getdata(messageid)
{
	//if(gr(messageid).style.visibility == "visible") return;
	gr(messageid).style.visibility='visible';
	gr(messageid).style.display='block';
	gr('readlinkdiv_'+messageid).innerHTML = "<a href=\"javascript:closereplydiv('"+messageid+"');\">Hide</a>";
	var val = gr(messageid).innerHTML;
	if(trim(val) != "") return;
	gr(messageid).innerHTML="<font class=sb2>Please wait...</font>";
	callURL("reqs","Main.php?do=getdata&messageid="+messageid,"POST",processReqGetReplies);
	gmessageid = messageid;
}

function processReqGetReplies()
{
	var data1;
	if(reqs.readyState==4)
	{
		if(reqs.status==200)
		{
			if(reqs.responseText != ""){
				gr(gmessageid).innerHTML=reqs.responseText;
			}
		}
	}
}

function closereplydiv(id)
{
	gr(id).style.visibility = 'hidden';
	gr(id).style.display = 'none';
	gr('readlinkdiv_'+id).innerHTML = "<a href=\"javascript:getdata('"+id+"')\">Read</a>";
}

function gr(id)
{
	return document.getElementById(id);
}


function getRef(name)
{
	return document.getElementById(name);
}


var g_int_aid	= "";
var g_int_qid	= "";
var g_int_cid	= "";

function submitabuse(qid,aid,cid)
{


	txtusername = "";
	txtpassword	= "";
	ansid= aid;
	url = "qid="+qid+"&aid="+aid+"&cid="+cid;
	g_int_qid = qid;
	g_int_aid = aid;
	g_int_cid = cid;
	loggedin		= getRef("loggedin_"+qid+"_"+aid+"_"+cid).value;
	if(loggedin == 0)
	{
		txtusername		= getRef("txtusername_"+qid+"_"+aid+"_"+cid).value;
		txtpassword		= getRef("txtpassword_"+qid+"_"+aid+"_"+cid).value;
		if(trim(txtusername) == "" || trim(txtpassword) == ""){
			alert('Rediff id or Password field blank');
			return false;
		}
	}
	cboabusetype	= getRef("cboabusetype_"+qid+"_"+aid+"_"+cid).value;

	if(trim(cboabusetype) == ""){
		alert('Please choose an abuse type.');
		return false;
	}

	if (cid != "")
	{
		document.getElementById("divAbuse"+aid+cid).innerHTML="<font class=sb2>Please wait...</font>";
	} 
	else if (aid != "")
	{
		document.getElementById("dd3q"+aid).innerHTML="<font class=sb2>Please wait...</font>";
	}
	else
	{
		document.getElementById("divReportAbu").innerHTML="<font class=sb2>Please wait...</font>";
	}	
	url += "&txtusername="+txtusername+"&txtpassword="+txtpassword+"&cboabusetype="+cboabusetype+"&loggedin="+loggedin;
	if (cid != "")
	{
		callURL("reqs","Main.php?do=reportabuse&submit=Submit&"+url, "POST", processdisplayCommentAbuse);
	} 
	else if (aid != "")
	{

		callURL("reqs","Main.php?do=reportabuse&submit=Submit&"+url, "POST", processdisplayAbuse);
	}
	else
	{
		callURL("reqs","Main.php?do=reportabuse&submit=Submit&"+url, "POST", processdisplayQuestAbuse);
	}
}
function processdisplayCommentAbuse()
{
	if(reqs.readyState==4)
	{
		if(reqs.status ==200)
		{
			if(reqs.responseText)
			{
				document.getElementById("divAbuse"+g_int_aid+g_int_cid).innerHTML=reqs.responseText;
				g_int_cid = "";
				g_int_aid = "";
				//showLoginName();
				//reposition();
				checkCookie();
			}
		}
	}
}
function processdisplayQuestAbuse()
{
	if(reqs.readyState==4)
	{
		if(reqs.status ==200)
		{
			if(reqs.responseText)
			{
				document.getElementById("divReportAbu").innerHTML=reqs.responseText;
				//showLoginName();
				//reposition();
				checkCookie();
			}
		}
	}
}


function refreshAd()
{
	//rbanner.document.location.reload();
	//tbanner.document.location.reload();
	document.getElementById("rbanner").src = document.getElementById("rbanner").src;
	document.getElementById("tbanner").src = document.getElementById("tbanner").src;
}