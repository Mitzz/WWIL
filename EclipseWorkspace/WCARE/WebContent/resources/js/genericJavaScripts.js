var windowId;
var calendar;

function maskNumber(frmObj) { //v1.0
  var strLengthCheck;
  for (var vCounter=0; vCounter < frmObj.value.length; vCounter+=1) {
	strLengthCheck = frmObj.value.replace(',','');
  }
  event.returnValue = false;
  if (((event.keyCode>=48 && event.keyCode<=57) || event.keyCode==46) && (strLengthCheck.length) <=15 && (strLengthCheck.length) > 0 ) {
	  event.returnValue = true;
	  var str='';
	  var strBeforeDecGen='';
	  var strBeforeDec='';
	  var strAfterDec='';
	  var indx = 0;
	  var indx1 = 0;
	  var strLenBeforeDec=0;
	  if (event.keyCode == 46) {
		str = frmObj.value +'.';
	  } else {
		str = frmObj.value +''+(event.keyCode-48);
	  }
	  for (var vCounter=0; vCounter<str.length; vCounter+=1) {
		str = str.replace(',','');
	  }
	  indx = str.indexOf('.');
	  if (indx != -1) {
		  var strCheck = str.substring(indx+1, str.length);
		  indx1 = strCheck.indexOf('.');
	  }
	  else {
	  	indx1 = -1;
	  }
	  if (indx1 == -1) {
		  if (indx!=-1) {
		        var str1 = str.substring(0, indx);
			strBeforeDec= str.substring(0, indx);
			strAfterDec= str.substring(indx+1, str.length);
		  	strLenBeforeDec = str1.length;
		  } else {
			strBeforeDec= str.substring(0, str.length);
			strAfterDec= '';
			strLenBeforeDec = str.length;
		  }
		  if (event.keyCode != 46) { // . not presed
			  if (strLenBeforeDec==0) {
				strBeforeDec = strBeforeDec;
			  }
			  else if (strLenBeforeDec==1) {
				strBeforeDec = strBeforeDec;
			  }
			  else if (strLenBeforeDec==2) {
				strBeforeDec = strBeforeDec;
			  }
			  else if (strLenBeforeDec==3) {
				strBeforeDec = strBeforeDec;
			  }

			  else {
			  	var str2 = strBeforeDec.substring(strLenBeforeDec-3,strLenBeforeDec);
			  	var str3 = strBeforeDec.substring(0, strLenBeforeDec-3);
			  	var vCounter1 = 2;
				if (str3.length%2 == 0) {
				  	for (var vCounter=0; vCounter<str3.length; vCounter+=2) {
						var str4 = str3.substring(vCounter, vCounter+2);
						strBeforeDecGen = strBeforeDecGen+str4+',';
				  	}
			  	} else {
				  	for (var vCounter=1; vCounter<str3.length; vCounter+=2) {

						var str4 = str3.substring(vCounter, vCounter+2);
						strBeforeDecGen = strBeforeDecGen+str4+',';
				  	}
				  	strBeforeDecGen = str3.substring(0, 1)+','+strBeforeDecGen;
			  	}
				strBeforeDec = strBeforeDecGen+str2;
			  }
			  if (strAfterDec=='') {
				  frmObj.value = strBeforeDec;
			  } else {
				  frmObj.value = strBeforeDec+'.'+strAfterDec;
			  }
		  }
		  else {
			  frmObj.value = frmObj.value+'.';
		  }
	  }
  }
  else if (((event.keyCode>=48 && event.keyCode<=57) || event.keyCode==46) && (strLengthCheck.length) == 0 ){
	  if (event.keyCode == 46)
	  {
		frmObj.value = '0' + '.';
	  }
	  else
	  {
		frmObj.value = frmObj.value+''+(event.keyCode-48);
	  }

  }
}


function validateAmount(frmObj) { //v1.0
  if (((event.keyCode < 48 || event.keyCode > 57) && event.keyCode !=46 && event.keyCode !=45) || frmObj.value.length > 15) {
	  event.returnValue = false;
  }
  if (event.keyCode ==46) {
  	  var indx = frmObj.value.indexOf('.');
	  if (indx != -1) {
		  var strCheck = frmObj.value.substring(indx, frmObj.value.length);
		  var indx1 = strCheck.indexOf('.');
		  if (indx1 != -1) {
		  	event.returnValue = false;
		  }
	  }
  }

  if (event.keyCode ==45) {
  	  var indx = frmObj.value.indexOf('-');
	  if (indx != -1 && indx == 0) {
		  var strCheck = frmObj.value.substring(indx, frmObj.value.length);
		  var indx1 = strCheck.indexOf('-');
		  if (indx1 != -1) {
		  	event.returnValue = false;
		  }
	  }
	  else if (indx != -1 && indx != 0) {
	  	event.returnValue = false;
	  }
 }
}

function validatePositiveAmt(frmObj) { //v1.0
  if (((event.keyCode < 48 || event.keyCode > 57) && event.keyCode !=46) || frmObj.value.length > 15) {
	  event.returnValue = false;
  }
  if (event.keyCode ==46) {
  	  var indx = frmObj.value.indexOf('.');
	  if (indx != -1) {
		  var strCheck = frmObj.value.substring(indx, frmObj.value.length);
		  var indx1 = strCheck.indexOf('.');
		  if (indx1 != -1) {
		  	event.returnValue = false;
		  }
	  }
  }
}


function validateInt(frmObj) { //v1.0
  if ((event.keyCode < 48 || event.keyCode > 57) && event.keyCode !=45) {
	  event.returnValue = false;
  }
}

function maskPositiveInt(frmObj) { //v1.0
  if (event.keyCode < 48 || event.keyCode > 57) {
	  event.returnValue = false;
  }
}

function maskNegativeInt(frmObj) { //v1.0
  if ((event.keyCode < 48 || event.keyCode > 57) && event.keyCode !=45) {
	  event.returnValue = false;
  }
  if (event.keyCode ==45) {
  	  var indx = frmObj.value.indexOf('-');
	  if (indx != -1 && indx == 0) {
		  var strCheck = frmObj.value.substring(indx, frmObj.value.length);
		  var indx1 = strCheck.indexOf('-');
		  if (indx1 != -1) {
		  	event.returnValue = false;
		  }
	  }
	  else if (indx != -1 && indx != 0) {
	  	event.returnValue = false;
	  }
  }
}

function maskNegativeAmt(frmObj) { //v1.0
  if (((event.keyCode < 48 || event.keyCode > 57) && event.keyCode !=46  && event.keyCode !=45) || frmObj.value.length > 15) {
	  event.returnValue = false;
  }
  if (event.keyCode ==46) {
  	  var indx = frmObj.value.indexOf('.');
	  if (indx != -1) {
		  var strCheck = frmObj.value.substring(indx, frmObj.value.length);
		  var indx1 = strCheck.indexOf('.');
		  if (indx1 != -1) {
		  	event.returnValue = false;
		  }
	  }
  }
  if (event.keyCode ==45) {
  	  var indx = frmObj.value.indexOf('-');
	  if (indx != -1 && indx == 0) {
		  var strCheck = frmObj.value.substring(indx, frmObj.value.length);
		  var indx1 = strCheck.indexOf('-');
		  if (indx1 != -1) {
		  	event.returnValue = false;
		  }
	  }
	  else if (indx != -1 && indx != 0) {
	  	event.returnValue = false;
	  }
  }
}

/**********************************************************************************/
/****	This function cross validate between from date and to date.            ****/
/****	It take from date and to date field name as input parameter            ****/
/****	and return boolean as output.                                          ****/
/**********************************************************************************/
function crossValidateDate(date1, date2){

	var strf = date1.value;
	var strt = date2.value;

	var f11 = strf.indexOf("/");
	var f21 =  strf.indexOf("/", f11+1);
              var monthf =  strf.substring(0, f11);
              var dayf =  strf.substring(f11+1, f21);
              var yearf = strf.substring(f21+1, strf.length);
              var monf = parseFloat(monthf) - 1;
             if(yearf=="0") yearf="2000";

	var f12 = strt.indexOf("/");
	var f22 = strt.indexOf("/", f12+1);
              var montht = strt.substring(0, f12);
              var dayt = strt.substring(f12+1, f22);
              var yeart = strt.substring(f22+1, strt.length);
              var mont = parseFloat(montht) - 1;
             if(yeart=="0") yeart="2000";

              var df = new Date(yearf, monf, dayf);
              var dt = new Date(yeart, mont, dayt);
	if (df > dt) {
		return false;
	}
	else {
		return true;
	}

 }

/**********************************************************************************/
/****			Added For Error Functioning			       ****/
/**********************************************************************************/

function MM_openBrErrorWindow(ErrCode,ErrMsg, fs ,winName,features1,features2) {

	//if (navigator.appName == 'Netscape'){

		var w = window.open("","errorWindow",features1);
		var d = w.document;
		d.open();
		d.write('<html>');
		d.write('<head>');
		d.write('<title>Message Window</title>');
		d.write('</head>');
		d.write('<body bgcolor="#E5E5DE" onBlur="this.focus();" onLoad="document.frmErr.CloseWin.focus();">');
		//d.write('<body bgcolor="#FFCC99">');
		d.write('<form method="post" name="frmErr">');

		d.write('<br>');
		d.write('<h2 align="center"><font face="Verdana, Arial, Helvetica, sans-serif" color="red"><u>');
		d.write('Error');
		d.write('</u></font></h2>');
		d.write('<table width="90%" align="center">');
		d.write('<tr valign="top">');
		d.write('<td>');
		d.write('<font face="Verdana, Arial, Helvetica, sans" size="2" color="black"><b>');
		d.write('Code		:-');
		d.write('</b></font>');
		d.write('</td>');
		d.write('<td>');
		d.write('<font face="Verdana, Arial, Helvetica, sans" size="2" color="black">');
		d.write(ErrCode);
		d.write('</font>');
		d.write('</td>');
		d.write('</tr>');
		d.write('<tr valign="top">');
		d.write('<td>');
		d.write('<font face="Verdana, Arial, Helvetica, sans" size="2" color="black"><b>');
		d.write('Message	:-');
		d.write('</b></font>');
		d.write('</td>');
		d.write('<td>');
		d.write('<font face="Verdana, Arial, Helvetica, sans" size="2" color="black">');
		d.write(ErrMsg);
		d.write('</font>');
		d.write('</td>');
		d.write('</tr>');
		d.write('</table>');

		d.write('<br>');

		//Button to close the window
		d.write('<table width="90%" align="center">');
		d.write('<tr>');
		d.write('<td align="center">');
		d.write('<input type="button" name="CloseWin" value="   Close   " onClick="self.close();">');
		d.write('</td>');
		d.write('</tr>');
		d.write('</table>');

		d.write('</form>');
		d.write('</body>');
		d.write('</html>');
		//d.CloseWin.focus();
		d.close();
		w.focus();
		w.moveTo(220, 275);

}

function MM_openBrMessageWindow(ErrCode,ErrMsg, fs ,winName,features1,features2) {
	//if (navigator.appName == 'Netscape'){
		var w = window.open("","errorWindow",features1);
		var d = w.document;
		d.open();
		d.write('<html>');
		d.write('<head>');
		d.write('<title>Message Window</title>');
		d.write('</head>');

		d.write('<body bgcolor="#E5E5DE" onBlur="this.focus();" onLoad="document.frmErr.CloseWin.focus();">');
		//d.write('<body bgcolor="#FFCC99">');
		d.write('<form method="post" name="frmErr">');

		d.write('<br>');
		d.write('<h2 align="center"><font face="Verdana, Arial, Helvetica, sans-serif" color="red"><u>');
		d.write('Error');
		d.write('</u></font></h2>');

		d.write('<table width="90%" align="center">');
		d.write('<tr valign="top">');
		d.write('<td>');
		d.write('<font face="Verdana, Arial, Helvetica, sans" size="2" color="black"><b>');
		d.write('Code		:-');
		d.write('</b></font>');
		d.write('</td>');
		d.write('<td>');
		d.write('<font face="Verdana, Arial, Helvetica, sans" size="2" color="black">');
		d.write(ErrCode);
		d.write('</font>');
		d.write('</td>');
		d.write('</tr>');
		d.write('<tr valign="top">');
		d.write('<td>');
		d.write('<font face="Verdana, Arial, Helvetica, sans" size="2" color="black"><b>');
		d.write('Message	:-');
		d.write('</b></font>');
		d.write('</td>');
		d.write('<td>');
		d.write('<font face="Verdana, Arial, Helvetica, sans" size="2" color="black">');
		d.write(ErrMsg);
		d.write('</font>');
		d.write('</td>');
		d.write('</tr>');
		d.write('</table>');

		d.write('<br>');

		//Button to close the window
		d.write('<table width="90%" align="center">');
		d.write('<tr>');
		d.write('<td align="center">');
		d.write('<input type="button" name="CloseWin" value="   Close   " onClick="self.close();">');
		d.write('</td>');
		d.write('</tr>');
		d.write('</table>');

		d.write('</form>');
		d.write('</body>');
		d.write('</html>');
		//d.CloseWin.focus();
		d.close();
		w.focus();
		w.moveTo(220, 275);

}
function loginErrorMessage(str,fs) {
	firstFieldFocus();
	var vStr, f1;
	if (str  != "") {
		if ((str.substring(0,1))=="1") {
			status = str.substring(2);
		}else {
			f1 = str.indexOf('******');
			var ErrCode = str.substring(2, f1);
			var ErrMsg  = str.substring(f1+6);
			MM_openBrErrorWindow(ErrCode,ErrMsg,fs,'ERRORWINDOW','resizable=no,scrollbars=no,status=no,width=600,height=300','status=no');
		}
	}else {
		status = '';
	}
}

function errorMessage(str,fs) {
	//firstFieldFocus();
	//exitOpenWindows();
	var vStr, f1;
	if (str  != "") {
		if ((str.substring(0,1))=="1") {
			status = str.substring(2);
		}else if((str.substring(0,1))=="0") {
			f1 = str.indexOf('******');
			vCode = str.substring(2, f1);
			vMsg = str.substring(f1+6);
			if (vCode != 0) {
				window.history.back();
			}
			showError(vCode, vMsg);
		}else if((str.substring(0,1))=="3") {
			f1 = str.length;
			vMsg = str.substring(2);
			showMessage(1, vMsg);
			window.history.back();
		}else {
			f1 = str.indexOf('******');
			vCode = str.substring(2, f1);
			vMsg = str.substring(f1+6);
			showMessage(vCode, vMsg);
		}
	}else {
		status = '';
	}
}

/**********************************************************************************/
/****        This Function clears the form except for the hidden 	       ****/
/****	     fields without submiting.                  		       ****/
/**********************************************************************************/
function formClear() {

	for( i=0; i<document.forms[0].elements.length; i++) {
		if (( document.forms[0].elements[i].type == 'text' ) || ( document.forms[0].elements[i].type == 'textarea' ) || ( document.forms[0].elements[i].type == 'password' )) {
			document.forms[0].elements[i].value = "";
		}
		if ( document.forms[0].elements[i].type == 'checkbox' ) {
			document.forms[0].elements[i].checked = false;
		}
		if ( document.forms[0].elements[i].type == 'radio' ) {
			document.forms[0].elements[i].checked = false;
		}
		if ( document.forms[0].elements[i].type == 'select-one' && ( document.forms[0].elements[i].length != 0 )) {
			document.forms[0].elements[i].options[0].selected = true;
		}

	}
	return;
}

/**********************************************************************************/
/****        This Function clears the form along with the hidden	       ****/
/****	     fields without submiting.                			       ****/
/**********************************************************************************/
function formClearHidden() {

	for( i=0; i<document.forms[0].elements.length; i++) {
		if (( document.forms[0].elements[i].type == 'text' ) ||
		    ( document.forms[0].elements[i].type == 'textarea' ) ||
		    ( document.forms[0].elements[i].type == 'password' ) ||
		    ( document.forms[0].elements[i].type == 'hidden' )) {
			document.forms[0].elements[i].value = "";
		}
		if ( document.forms[0].elements[i].type == 'checkbox' ) {
			document.forms[0].elements[i].checked = false;
		}
		// Added for reverting back to default checked vaue of radio button
		if ( document.forms[0].elements[i].type == 'radio' ) {
			if (document.forms[0].elements[i].defaultChecked==true) {
			document.forms[0].elements[i].checked=true
			}
		}
		//	document.forms[0].elements[i].checked = false;
		if ( document.forms[0].elements[i].type == 'select-one' && ( document.forms[0].elements[i].length != 0 )) {
			document.forms[0].elements[i].options[0].selected = true;
		}

	}
	return;
}

/**********************************************************************************/
/****        This function change the value of a field to upper case.          ****/
/****        It take the field name as input parameter.                        ****/
/**********************************************************************************/
function changeUpper(item) {
	item.value = item.value.toUpperCase();
}

/**********************************************************************************/
/****   statusMessage function give message in either status bur or as alert.  ****/
/****   It take character string as input parameter. Depending on the first    ****/
/****	characterof the string it display the message from third character to  ****/
/****   rest of the string. If first character is '1' then it display the      ****/
/****   message in status bur otherwise it display the message as alert.       ****/
/**********************************************************************************/
/*******
function statusMessage(str) {

	var vStr, f1;

	if (str  != "") {
		if ((str.substring(0,1))=="1") {
			status = str.substring(2);
		}else {
			f1 = str.indexOf('******');
			vStr = 'Error Code = ' + str.substring(2, f1)+'\n' + 'Erroe Message = ' + str.substring(f1+6);
		}
	}else {
		status = '';
	}
}
*****/

function statusMessage(str) {
	firstFieldFocus();
	var vStr, f1;

	if (str  != "") {
		if ((str.substring(0,1))=="1") {
			status = str.substring(2);
		}else if((str.substring(0,1))=="0") {
			f1 = str.indexOf('******');
			vCode = str.substring(2, f1);
			vMsg = str.substring(f1+6);
			showError(vCode, vMsg);
		}else {
			f1 = str.indexOf('******');
			vCode = str.substring(2, f1);
			vMsg = str.substring(f1+6);
			showMessage(vCode, vMsg);
		}
	}else {
		status = '';
	}
}


function showError (errCode, errMsg) {
	var w = window.open("", 							// URL (Not Specified)
			    "errorWiindow", 						//Window NAme
			    "resizable=no,scrollbars=yes,status=no,width=600,height=300" // Features
			    );
	//w.focus();
	var d = w.document;
	d.open();
	d.write('<HTML>');
	d.write('<head>');
	d.write('<TITLE>Error Window</TITLE>');
	d.write('</head>');
	d.write('<body bgcolor="#E5E5DE" onBlur="this.focus();" onLoad="document.frmErr.CloseWin.focus();">');
	//d.write('<body bgcolor="#E5E5DE">');
	d.write('<form  name="frmErr">');


	d.write('<br>');
	d.write('<h2 align="center"><font face="Verdana, Arial, Helvetica, sans-serif" color="red"><u>');
	d.write('Error');
	d.write('</u></font></h3>');

	d.write('<br>');
	d.write('<table width="100%" align="center">');
	// Error Code
	d.write('<tr valign="top">');
	d.write('<td>');
	d.write('<font face="Verdana, Arial, Helvetica, sans" size="2"><B>');
	d.write('Error Code:');
	d.write('</b></font>');
	d.write('</td>');
	d.write('<td>');
	d.write('<font face="Verdana, Arial, Helvetica, sans" size="2" color="black">');
	d.write(errCode);
	d.write('</font>');
	d.write('</td>');
	d.write('</tr>');

	// Error Message
	d.write('<tr valign="top">');
	d.write('<td>');
	d.write('<font face="Verdana, Arial, Helvetica, sans" size="2"><B>');
	d.write('Error Message:');
	d.write('</b></font>');
	d.write('</td>');
	d.write('<td>');
	d.write('<font face="Verdana, Arial, Helvetica, sans" size="2" color="black">');
	d.write(errMsg);
	d.write('</font>');
	d.write('</td>');
	d.write('</tr>');
	d.write('</table>');

	d.write('<br>');

	//Button to close the window
	d.write('<table width="90%" align="center">');
	d.write('<tr>');
	d.write('<td align="center">');
	d.write('<input type="button" name="CloseWin" value="   Close   " onClick="self.close();">');
	d.write('</td>');
	d.write('</tr>');
	d.write('</table>');

	d.write('</form>');
	d.write('</body>');
	d.write('</html>');
	//d.CloseWin.focus();
	d.close();
	w.focus();
	w.moveTo(350, 200);
}

function showMessage (errCode, errMsg) {
	var w = window.open("", 							// URL (Not Specified)
			    "messageWiindow", 						//Window NAme
			    "resizable=no,scrollbars=no,status=no,width=600,height=300" // Features
			    );
	//w.focus();
	var d = w.document;
	d.open();
	d.write('<HTML>');
	d.write('<head>');
	d.write('<TITLE>Message Window</TITLE>');
	d.write('</head>');

	d.write('<body bgcolor="#E5E5DE" onBlur="this.focus();" onLoad="document.frmErr.CloseWin.focus();">');
	//d.write('<body bgcolor="#FFCC99">');
	d.write('<form  name="frmErr">');


	d.write('<br>');
	d.write('<h2 align="center"><font face="Verdana, Arial, Helvetica, sans-serif" color="BROWN"><u>');
	d.write('Message');
	d.write('</u></font></h3>');

	d.write('<br>');
	d.write('<table width="100%" align="center">');
	// Error Message
	d.write('<tr valign="top">');
	d.write('<td align="center">');
	d.write('<font face="Verdana, Arial, Helvetica, sans" size="2" color="black">');
	d.write(errMsg);
	d.write('</font>');
	d.write('</td>');
	d.write('</tr>');
	d.write('</table>');

	d.write('<br>');

	//Button to close the window
	d.write('<table width="90%" align="center">');
	d.write('<tr>');
	d.write('<td align="center">');
	d.write('<input type="button" name="CloseWin" value="   Close   " onClick="self.close();">');
	d.write('</td>');
	d.write('</tr>');
	d.write('</table>');

	d.write('</form>');
	d.write('</body>');
	d.write('</html>');
	//d.CloseWin.focus();
	d.close();
	w.focus();
	w.moveTo(350, 200);
}
/***************************************************************************************/
/********This function checks the status message, throws error window if reqd.,*********/
/*****If errCode is anything other than 0, user is returned to the source screen********/
/***************************************************************************************/
function statusMessageWithErrChk(str) {

	firstFieldFocus();

	var vStr, f1;

	if (str  != "") {
		if ((str.substring(0,1))=="1") {
			status = str.substring(2);
		}else if((str.substring(0,1))=="0") {
			f1 = str.indexOf('******');
			vCode = str.substring(2, f1);
			vMsg = str.substring(f1+6);
			if (vCode != 0) {
				var backButton = window.history.back();
				if (typeof(backButton) != 'undefined')
				{
					window.history.back();
				}
				else
				{
					window.close();
				}
				//parent.display.history.back();
				//parent.Control.history.back();
			}
			/* Strst change by Raktim */
			if((vMsg == " invalid username/password; logon denied") || (vMsg.indexOf('is already logged in.') != -1))
			{
				//parent.display.history.back();
				//parent.head.history.back();
				window.history.back();
			}
			/* End change */
			showError(vCode, vMsg);
		}else if((str.substring(0,1))=="3") {
			f1 = str.length;
			vMsg = str.substring(2);
			showMessage(1, vMsg);
				window.history.back();
				//parent.display.history.back();
				//parent.Control.history.back();
		}else {
			f1 = str.indexOf('******');
			vCode = str.substring(2, f1);
			vMsg = str.substring(f1+6);
			showMessage(1, vMsg);
				window.history.back();
				//parent.display.history.back();
				//parent.Control.history.back();
		}
	}else {
		status = '';
	}

}
/**********************************************************************************/
/****        This function validate whether an inserted value is number or not.****/
/****        It take the field name and character string as input parameter.   ****/
/**********************************************************************************/
function validateNumber(item, str) {

	if(!isPositiveInteger(item.value)){

		alert(str+' should be a Number.');
		item.select();
		item.focus();
		return false;
	}
	return true;

}

/**********************************************************************************/
/****        This function validate whether an inserted value is number or not.****/
/****        It take the field name and character string as input parameter.   ****/
/**********************************************************************************/
function validatePositiveNumber(item, str) {

	if(!isPositiveInteger(item.value)){

		alert(str+' should be an Positive Number.');
		item.select();
		item.focus();
		return false;
	}
	return true;

}

/**********************************************************************************/
/****        This function validate whether an inserted value is number or not.****/
/****        It take the field name and character string as input parameter.   ****/
/**********************************************************************************/
function validateNegativeNumber(item, str) {

	if(!isNegativeInteger(item.value)){

		alert(str+' should be a number.');
		item.select();
		item.focus();
		return false;
	}
	return true;

}

/**********************************************************************************/
/****        This function validate whether an inserted value is number or not.****/
/****        It take the field name and character string as input parameter.   ****/
/**********************************************************************************/
function validateDecimal(item, str) {
	var str1;

	if ((item.value).indexOf('-') != -1) {
		str1 = item.value.substring(1,str.length);
	}
	else {
		str1 = item.value;
	}
	if(!isDecimal(item.value)){

		alert(str+' should be a Number.');
		item.select();
		item.focus();
		return false;
	} else if(str1 != null && str1 != ''){
			if (str1 > 999999999999.99 || str1 <= 0.00){
			alert (str + ' should be less than 999999999999.99\nand greater than 0.00');
			item.select();
			item.focus();
			return false;}
	} else {
		var temp = '' + parseFloat(Math.round(item.value*100.00))/100.00;
		var indx = temp.indexOf('.');
		if (indx == -1) {
			null;
		}
		else {
			var temp1 = temp.substr (indx+1, temp.length);
			if (temp1.length == 1) {
				item.value = temp + '0';
			} else {
				item.value = temp;
			}
		}
		return true;
	}


}

/**********************************************************************************/
/****        This function validate whether an inserted value is number or not.****/
/****        It take the field name and character string as input parameter.   ****/
/**********************************************************************************/
function validatePositiveDecimal(item, str) {
	if(!isDecimal(item.value)){

		alert(str+' should be a Number.');
		item.select();
		item.focus();
		return false;
	} else if ((item.value).indexOf('-') != -1) {
		//errors += '- '+theName+' must be positive number.\n';
		alert(item.value +' must be positive number.');
	} else if (item.value > 999999999999.99 || item.value < 0.00){
		alert (str + ' should be less than 999999999999.99\nand greater than 0.00');
		item.select();
		item.focus();
		return false;
	} else {
		var temp = '' + parseFloat(Math.round(item.value*100.00))/100.00;
		var indx = temp.indexOf('.');
		if (indx == -1) {
			null;
		}
		else {
			var temp1 = temp.substr (indx+1, temp.length);
			if (temp1.length == 1) {
				item.value = temp + '0';
			} else {
				item.value = temp;
			}
		}
		return true;
	}

}

/**********************************************************************************/
/****        This function validate whether an inserted value is number or not.****/
/****        It take the field name and character string as input parameter.   ****/
/**********************************************************************************/
function validateNegativeDecimal(item, str) {

	str = str.substring(1,str.length);
	if(!isDecimal(item.value)){
		alert(str+' should be a Number.');
		item.select();
		item.focus();
		return false;
	} else if ((item.value).indexOf('-') != 0) {
		//errors += '- '+theName+' must be negative number.\n';
		alert(item.value +' must be negative number.');
	} else if (str > 999999999999.99 || str < 0.00){
		alert (str + ' should be less than -999999999999.99\nand greater than 0.00');
		item.select();
		item.focus();
		return false;
	} else {
		var temp = '' + parseFloat(Math.round(item.value*100.00))/100.00;
		var indx = temp.indexOf('.');
		if (indx == -1) {
			null;
		}
		else {
			var temp1 = temp.substr (indx+1, temp.length);
			if (temp1.length == 1) {
				item.value = temp + '0';
			} else {
				item.value = temp;
			}
		}
		return true;
	}

}

/**********************************************************************************/
/****        This function compare the length of an inserted string in text    ****/
/****	     area with given length.                                           ****/
/****        It take the field name, allowable length and character string     ****/
/****	     (field prompt) as input parameter.                                ****/
/**********************************************************************************/
function validateLength(item, len, str) {

	if ( item.value.length > len )  {
		alert(str+' Length should not be greater than '+len+' Character.');
		item.focus();
	}
}

/**********************************************************************************/
/****	This function validate the input in a field is a valid date or not.    ****/
/****	It take the field name and field prompt as input parameter.            ****/
/**********************************************************************************/
function validateDate(date, context) {
	if (context == '1') {
	   	if((date.value=="")||(date.value == date.defaultValue)) return true;

		if(!isDate(date.value)){
		   alert("Invalid Date\nFormat : mm/dd/yyyy");
		   date.select();
		   date.focus();
		   return false;
		} else {

		   var f1 = date.value.indexOf("/");
	     	   var f2 = date.value.indexOf("/", f1+1);

		   var mon = date.value.substring(0, f1);
		   var day = date.value.substring(f1+1, f2);
		   var year = date.value.substring(f2+1, date.value.length);
		   if(parseFloat(year) < 1000 )	year = 2000 + parseFloat(year);
		   var toDay = new Date();
		   var vDay  = new Date(year, parseFloat(mon)-1, parseFloat(day)+1);
		   if ((parseFloat(day) < 10 )&&(day.length == 1)) day = '0'+day;
		   if ((parseFloat(mon) < 10 )&&(mon.length == 1)) mon = '0'+mon;
		   date.value =   mon + "/" + day + "/" + year;
		   return true;
		}
	}

	else {
	   	if((date.value=="")||(date.value == date.defaultValue)) return true;

		if(!isDate(date.value)){
		   alert("Invalid Date\nFormat : mm/dd/yyyy");
		   date.select();
		   date.focus();
		   return false;
		} else {

		   var f1 = date.value.indexOf("/");
	     	   var f2 = date.value.indexOf("/", f1+1);

		   var mon = date.value.substring(0, f1);
		   var day = date.value.substring(f1+1, f2);
		   var year = date.value.substring(f2+1, date.value.length);

		   if(parseFloat(year) < 1000 )	year = 2000 + parseFloat(year);

		   var toDay = new Date();
		   var vDay  = new Date(year, parseFloat(mon)-1, parseFloat(day)+1);

		   if ( vDay < toDay) {
			alert('Entered date can not be less than todays date');
			date.select();
			date.focus();
			return false;

		   }else {

		   	if ((parseFloat(day) < 10 )&&(day.length == 1)) day = '0'+day;
		   	if ((parseFloat(mon) < 10 )&&(mon.length == 1)) mon = '0'+mon;
		   	date.value =  mon + "/" + day + "/" + year;

		   	return true;
		   }
		}
	}
}

/**********************************************************************************/
/****	This function count the input string of a text field and give alert   *****/
/****	message as it reach the specified length.                             *****/
/****	It take text field name and specified length as input parameter.      *****/
/**********************************************************************************/
function countLength(item, len) {

      	var val = item.value;
	var count = val.length + 1;
	if (count > len) {
		alert("You have entered "+(count - 1)+" chars. Maximum "+len+" chars are allowable");
      		event.returnValue = false;
	}

}

/**********************************************************************************/
/****	This function check whether a string is Integer number or not.         ****/
/****	It take string as input parameter and return boolean as output.        ****/
/**********************************************************************************/
function isPositiveInteger(str) {
	var isnum=true;
	if ((str==null) || (str=="")){
		isnum=true;
		return isnum;
   	}

	for(var j=0;j<str.length;j++){

		if((str.substring(j,j+1) !="0") &&
		   (str.substring(j,j+1) !="1") &&
		   (str.substring(j,j+1) !="2") &&
		   (str.substring(j,j+1) !="3") &&
		   (str.substring(j,j+1) !="4") &&
		   (str.substring(j,j+1) !="5") &&
		   (str.substring(j,j+1) !="6") &&
		   (str.substring(j,j+1) !="7") &&
		   (str.substring(j,j+1) !="8") &&
		   (str.substring(j,j+1) !="9"))   {
			isnum=false;
		}
	}
	return isnum;
}
/************************************************************************************/
/****	This function check whether a string is a Negative Integer number or not.****/
/****	It take string as input parameter and return boolean as output.        	 ****/
/************************************************************************************/
function isNegativeInteger(str) {
	var isnum=true;
	if ((str==null) || (str=="")){
		isnum=true;
		return isnum;
   	}


	if (str.indexOf('-') != 0) {
		isnum=false;
		return isnum;
	}

	str = str.substring(1,str.length);

	for(var j=0;j<str.length;j++){

		if((str.substring(j,j+1) !="0") &&
		   (str.substring(j,j+1) !="1") &&
		   (str.substring(j,j+1) !="2") &&
		   (str.substring(j,j+1) !="3") &&
		   (str.substring(j,j+1) !="4") &&
		   (str.substring(j,j+1) !="5") &&
		   (str.substring(j,j+1) !="6") &&
		   (str.substring(j,j+1) !="7") &&
		   (str.substring(j,j+1) !="8") &&
		   (str.substring(j,j+1) !="9"))   {
			isnum=false;
		}
	}
	return isnum;
}

/**********************************************************************************/
/****	This function check whether a string is Integer number or not.         ****/
/****	It take string as input parameter and return boolean as output.        ****/
/**********************************************************************************/
function isInteger(str) {
	var isnum=true;
	if ((str==null) || (str=="")){
		isnum=true;
		return isnum;
   	}

	for(var j=0;j<str.length;j++){
		if((j == 0) && (str.substring(0,1) == '-')) {
			isnum=true;
		}
		else if((str.substring(j,j+1) !="0") &&
			(str.substring(j,j+1) !="1") &&
			(str.substring(j,j+1) !="2") &&
			(str.substring(j,j+1) !="3") &&
			(str.substring(j,j+1) !="4") &&
			(str.substring(j,j+1) !="5") &&
			(str.substring(j,j+1) !="6") &&
			(str.substring(j,j+1) !="7") &&
			(str.substring(j,j+1) !="8") &&
			(str.substring(j,j+1) !="9"))   {
				isnum=false;
		}
	}
	return isnum;
}

/**********************************************************************************/
/****	This function check whether a string is Character or not.	       ****/
/****	It take string as input parameter and return boolean as output.        ****/
/**********************************************************************************/
function isChar(str) {
	var isCharacter=true;
	if ((str==null) || (str=="")){
		isCharacter=true;
		return isCharacter;
   	}

	for(j=0;j<str.length;j++){

		if( (!((str.substring(j,j+1).charCodeAt(0) >= "A".charCodeAt(0)) &&
		       (str.substring(j,j+1).charCodeAt(0) <= "Z".charCodeAt(0)))) &&
		    (!((str.substring(j,j+1).charCodeAt(0) >= "a".charCodeAt(0)) &&
		       (str.substring(j,j+1).charCodeAt(0) <= "z".charCodeAt(0)))) )
		{
			isCharacter=false;
		}

	}
	return isCharacter;
}

/**********************************************************************************/
/****	This function check whether a string is Character or not.	       ****/
/****	It take string as input parameter and return boolean as output.        ****/
/**********************************************************************************/
function isAlphanumeric(str) {
	var isAlpha=true;
	if ((str==null) || (str=="")){
		isAlpha=true;
   	}

/*
	//I am told that '_' is Alphanumeric !! - Anjan
	//if ((!isPositiveInteger(str)) && (!isChar(str))) {
	//else if (isPositiveInteger(str)) {
	//	isAlpha=false;
	//}

	var strToCheck;

	for(var j=0;j<str.length;j++){
		strToCheck = str.substring(j,j+1);
		if ((!isPositiveInteger(strToCheck)) && (!isChar(strToCheck))) {
			if (	(!(strToCheck == "_"))
				&& (!(strToCheck == "-"))
				&& (!(strToCheck == "/"))
				&& (!(strToCheck == "."))
				&& (!(strToCheck == " "))
			) {
				isAlpha=false;
			}
		}
	}
*/
	return isAlpha;
}
/**********************************************************************************/
/****	This function check whether a string is decimal number or not.        *****/
/****	It take string as input parameter and return boolean as output.       *****/
/**********************************************************************************/
function isAmount(item,str) {
	var isnum=true;
	if ((str==null) || (str=="")){
		isnum=true;
		return isnum;
	}

	for(j=0;j<str.length;j++){
		if(j==0 && str.substring(j, j+1) =="-") { continue; } //if '-' is found at first position

		if((str.substring(j,j+1) !="0") &&
		   (str.substring(j,j+1) !="1") &&
		   (str.substring(j,j+1) !="2") &&
		   (str.substring(j,j+1) !="3") &&
		   (str.substring(j,j+1) !="4") &&
		   (str.substring(j,j+1) !="5") &&
		   (str.substring(j,j+1) !="6") &&
		   (str.substring(j,j+1) !="7") &&
		   (str.substring(j,j+1) !="8") &&
		   (str.substring(j,j+1) !="9") &&
		   (str.substring(j,j+1) !="."))   {
			return false;
		}
	}
	var indx1 = str.indexOf(".", 0);
	if(indx1!=-1){
		var indx2=str.indexOf(".", indx1+1);
		if(indx2!=-1)  return false;
	}
       /****************************************************************************/
       /* Commented as per RICB requirement.					   */
       /* Spectacle power needs to be a number field and property. To cater to its */
       /* negative value, following change has been done			   */
       /****************************************************************************/
       /*if (item.value > 999999999999.99 || item.value < 0.00){
		alert (str + ' should be less than 999999999999.99\nand greater than 0.00');
		item.select();
		item.focus();
		return false;
	} else {*/
		var temp = '' + parseFloat(Math.round(item.value*100.00))/100.00;
		var indx = temp.indexOf('.');
		if (indx == -1) {
			item.value = temp;
			//null;
		}
		else {
			var temp1 = temp.substr (indx+1, temp.length);
			if (temp1.length == 1) {
				item.value = temp + '0';
			} else {
				item.value = temp;
			}
		}
		return true;
	//}
}
/**********************************************************************************/
/****	This function check whether a string is decimal number or not.        *****/
/****	It take string as input parameter and return boolean as output.       *****/
/**********************************************************************************/
function isNonZeroAmount(item,str) {
	var isnum=true;
	if ((str==null) || (str=="")){
		isnum=true;
		return isnum;
	}

	for(j=0;j<str.length;j++){
		if(j==0 && str.substring(j, j+1) =="-") { continue; } //if '-' is found at first position

		if((str.substring(j,j+1) !="0") &&
		   (str.substring(j,j+1) !="1") &&
		   (str.substring(j,j+1) !="2") &&
		   (str.substring(j,j+1) !="3") &&
		   (str.substring(j,j+1) !="4") &&
		   (str.substring(j,j+1) !="5") &&
		   (str.substring(j,j+1) !="6") &&
		   (str.substring(j,j+1) !="7") &&
		   (str.substring(j,j+1) !="8") &&
		   (str.substring(j,j+1) !="9") &&
		   (str.substring(j,j+1) !="."))   {
			return false;
		}
	}
	var indx1 = str.indexOf(".", 0);
	if(indx1!=-1){
		var indx2=str.indexOf(".", indx1+1);
		if(indx2!=-1)  return false;
	}
       if (item.value > 999999999999.99 || item.value <= 0.00){
		//alert (item.name + ' should be less than 999999999999.99\nand greater than 0.00');
		item.select();
		item.focus();
		return false;
	} else {
		var temp = '' + parseFloat(Math.round(item.value*100.00))/100.00;
		var indx = temp.indexOf('.');
		if (indx == -1) {
			null;
		}
		else {
			var temp1 = temp.substr (indx+1, temp.length);
			if (temp1.length == 1) {
				item.value = temp + '0';
			} else {
				item.value = temp;
			}
		}
		return true;
	}
}


/**********************************************************************************/
/****	This function check whether a string is decimal number or not.        *****/
/****	It take string as input parameter and return boolean as output.       *****/
/**********************************************************************************/
function isMaskedAmount(item,str) {
	var isnum=true;
	if ((str==null) || (str=="")){
		isnum=true;
		return isnum;
	}

	for (var vCounter=0; vCounter<str.length; vCounter+=1) {
		str = str.replace(',','');
	}

	for(j=0;j<str.length;j++){
		if(j==0 && str.substring(j, j+1) =="-") { continue; } //if '-' is found at first position

		if((str.substring(j,j+1) !="0") &&
		   (str.substring(j,j+1) !="1") &&
		   (str.substring(j,j+1) !="2") &&
		   (str.substring(j,j+1) !="3") &&
		   (str.substring(j,j+1) !="4") &&
		   (str.substring(j,j+1) !="5") &&
		   (str.substring(j,j+1) !="6") &&
		   (str.substring(j,j+1) !="7") &&
		   (str.substring(j,j+1) !="8") &&
		   (str.substring(j,j+1) !="9") &&
		   (str.substring(j,j+1) !="."))   {
			return false;
		}
	}
	var indx1 = str.indexOf(".", 0);
	if(indx1!=-1){
		var indx2=str.indexOf(".", indx1+1);
		if(indx2!=-1)  return false;
	}
       if (item.value > 999999999999.99 || item.value <= 0.00){
		//alert (item.name + ' should be less than 999999999999.99\nand greater than 0.00');
		item.select();
		item.focus();
		return false;
	} else {
		var temp = '' + parseFloat(Math.round(item.value*100.00))/100.00;
		var indx = temp.indexOf('.');
		if (indx == -1) {
			null;
		}
		else {
			var temp1 = temp.substr (indx+1, temp.length);
			if (temp1.length == 1) {
				item.value = temp + '0';
			} else {
				item.value = temp;
			}
		}
		return true;
	}
}
/**********************************************************************************/
/****	This function check whether a string is decimal number or not.        *****/
/****	It take string as input parameter and return boolean as output.       *****/
/**********************************************************************************/
function isDecimal(str){

	var isnum=true;
	if ((str==null) || (str=="")){
		isnum=true;
		return isnum;
	}

	for(j=0;j<str.length;j++){
		if(j==0 && str.substring(j, j+1) =="-") { continue; } //if '-' is found at first position

		if((str.substring(j,j+1) !="0") &&
		   (str.substring(j,j+1) !="1") &&
		   (str.substring(j,j+1) !="2") &&
		   (str.substring(j,j+1) !="3") &&
		   (str.substring(j,j+1) !="4") &&
		   (str.substring(j,j+1) !="5") &&
		   (str.substring(j,j+1) !="6") &&
		   (str.substring(j,j+1) !="7") &&
		   (str.substring(j,j+1) !="8") &&
		   (str.substring(j,j+1) !="9") &&
		   (str.substring(j,j+1) !="."))   {
			isnum=false;
		}
	}
	var indx1 = str.indexOf(".", 0);
	if(indx1!=-1){
		var indx2=str.indexOf(".", indx1+1);
		if(indx2!=-1) isnum=false;
	}
	return isnum;
}

/**********************************************************************************/
/****	This function check whether a string has date format or not.           ****/
/****	It take string as input parameter and return boolean as output.        ****/
/**********************************************************************************/
function isDate(str){
	var f1 = str.indexOf("/");
	var f2=-1;
	if(f1!=-1){
		f2 = str.indexOf("/", f1+1);
	}

	if(f1==-1||f2==-1||f1==0){
		return false;
	}

   var str1 = str.substring(f1+1, f2);
   if((!isPositiveInteger(str1)) || (str1>31||str1==0)){return false;}

   else {
	var str2 = (str.substring(0, f1));

	var mm = parseFloat(str2);

	if(mm > 12 || mm < 1){
		return false;
	}

	else{
		var str3 = str.substring(f2+1, str.length);
		if(!isPositiveInteger(str3) || str3=="" || str3==null||parseFloat(str3)>9999) return false;

	}

   }

   if(!isValidDate(str)){
	return false;
   }

   return true;
}

/**********************************************************************************/
/*****	This function check an input string is valid date or not.            ******/
/*****	It Used by isDate function only.                                     ******/
/**********************************************************************************/
function isValidDate(str){
   var f1 = str.indexOf("/");
   var f2 = str.indexOf("/", f1+1);
   var month = str.substring(0, f1);
   var day = str.substring(f1+1, f2);
   var year = str.substring(f2+1, str.length);
   var mon = parseFloat(month) - 1;
   if(year=="0") year="2000";

   var d = new Date(year, mon, day);
   if(mon==d.getMonth()){
	return true;
   }
   else return false;
}

/**********************************************************************************/
/****	This function check whether a string has time format or not.           ****/
/****	It take string as input parameter and return boolean as output.        ****/
/**********************************************************************************/
function isTime(str){

	var hourIndex = str.indexOf(":");
	var minuteIndex=-1;

	if(hourIndex!=-1){
		minuteIndex = str.indexOf(":", hourIndex+1);
	}

	if(hourIndex==-1 || minuteIndex==-1 || hourIndex==0){
		return false;
	}

        // The function isPositiveInteger checks for null so we don't have to do it here.

        var hour = str.substring(0, hourIndex);
   	if((!isPositiveInteger(hour)) || (hour>23 || hour<0)) {
   		return false;
   	}
   	else {
		var minute = (str.substring(hourIndex+1, minuteIndex));
		if(!isPositiveInteger(minute) || (parseFloat(minute)<0 || parseFloat(minute)>= 60)) {
			return false;
		}
		else {
			var second = str.substring(minuteIndex+1, str.length);
			if(!isPositiveInteger(second) || (parseFloat(second)<0 || parseFloat(second)>= 60)) {
				return false;
			}
		}
  	}

   	return true;
}

/**********************************************************************************/
/*****	This function allows to enter only numeric characters.               ******/
/*****	It Used on onKeyPress event.                                         ******/
/*****	USAGE : <INPUT TYPE="text" onKeyPress="onlyNumber();">               ******/
/**********************************************************************************/
function onlyNumber() {
  if (event.keyCode<48 || event.keyCode>57) {
      event.returnValue = false;
  }
}

/**********************************************************************************/
/*****	This function capitalixe each and every character typed.             ******/
/*****	It Used on onKeyPress event.                                         ******/
/*****	USAGE : <INPUT TYPE="text" onKeyPress="makeUpper();">                ******/
/**********************************************************************************/
function makeUpper() {
  if (event.keyCode>=97 && event.keyCode<=122) {
	event.keyCode = event.keyCode - 32;
  }
}

/**********************************************************************************/
/*****	This function check an input string is valid email id or not.        ******/
/*****	It Used by isDate function only.                                     ******/
/**********************************************************************************/
function isEmail(str){
   if(str=="") return true;
   var indx1 = str.indexOf("@", 0);
   var indx2 = str.indexOf(".", indx1+1);
   var indx3 = str.indexOf(" ");
   var indx4 = str.indexOf(",");
   var indx5 = str.indexOf(";");
   var indx6 = str.lastIndexOf(".");
   

  if ( indx3 != -1 ) {
    return false;
  }
  
  if ( indx4 != -1 ) {
    return false;
  }
  
  if ( indx5 != -1 ) {
    return false;
  }  

   if(indx1==-1||indx2==-1)
	return false;

   else if(indx1==0){
	return false;
   }

   else if(indx2-indx1<=1) {
	return false;
   }

   else if(indx2 == (str.length-1)) {
	return false;
   }
   
   else if(((parseInt(str.length)-1)-parseInt(indx6))>3)
   {    
   return false;
   }

   else return true;
}


/**********************************************************************************/
/*****	This function check an input string is valid phone no or not.        ******/
/**********************************************************************************/
function isPhone(str){

  var flag = true;
  for(var i=0; i<str.length; i++){
	if(str.substring(i, i+1)!="0" &&
           str.substring(i, i+1)!="1" &&
           str.substring(i, i+1)!="2" &&
           str.substring(i, i+1)!="3" &&
           str.substring(i, i+1)!="4" &&
           str.substring(i, i+1)!="5" &&
           str.substring(i, i+1)!="6" &&
           str.substring(i, i+1)!="7" &&
           str.substring(i, i+1)!="8" &&
           str.substring(i, i+1)!="9" &&
           str.substring(i, i+1)!="-" )
		flag = false;
  }
  return flag;
}

/**********************************************************************************/
/*****	This function is used to delete one or more records.	             ******/
/**********************************************************************************/
function setDeleteValues() {
	exitOpenWindows();
        var dataString = "";

        var args = setDeleteValues.arguments;
	var vCount =0;
	var i;

        var screenId            = args[0];
        var eventCode           = args[1];
        var strDelimiter        = args[2];
        var recDelimiter        = args[3];
        var typDelimiter        = args[4];
        var noFields	        = args[5];

        for (i=0; i<noFields; i++) {
		if (document.forms[0].elements[i].type == 'checkbox') {
			if (document.forms[0].elements[i].checked) {
				dataString = dataString + document.forms[0].elements[i].value + strDelimiter;
			}
		}
		else if (document.forms[0].elements[i].type == 'radio') {
			if (document.forms[0].elements[i].checked) {
				dataString = dataString + document.forms[0].elements[i].value + strDelimiter;
			}
		}
		else if (document.forms[0].elements[i].type == 'select-one') {
			var sel = "";

			for (var j=0; j<document.forms[0].elements[i].options.length; j++) {
				if (document.forms[0].elements[i].options[j].selected) {
					sel = document.forms[0].elements[i].options[j].value;
				}
			}
			dataString = dataString + sel + strDelimiter;
		}
		else if (document.forms[0].elements[i].type == 'text' || document.forms[0].elements[i].type == 'textarea' || document.forms[0].elements[i].type == 'hidden' || document.forms[0].elements[i].type == 'password' || document.forms[0].elements[i].type == 'file') {

			dataString = dataString + document.forms[0].elements[i].value + strDelimiter;
		}
	}
        dataString = dataString + recDelimiter + typDelimiter;

	for (i=noFields; i<document.forms[0].elements.length; i++) {
		if (document.forms[0].elements[i].checked) {
			vCount =1;
			dataString = dataString + document.forms[0].elements[i].value + strDelimiter + recDelimiter;
		}
	}

	dataString = dataString  + typDelimiter;

	if ( vCount == 0 ) {
        	alert('You need to check atleast one check box to delete a record');
	}
	else {
	        document.forms[1].pScreenId.value = screenId;
	        document.forms[1].pEventCode.value = eventCode;
	        document.forms[1].pDataString.value = dataString;

		document.forms[1].submit();
	}

}

/**********************************************************************************/
/*****	This function forms the data string and goes to the relevent screen  ******/
/*****  when the hyperlink in the screen is clicked. 	                     ******/
/**********************************************************************************/
function setLinkValues() {
	exitOpenWindows();
        var dataString = "";
        var args = setLinkValues.arguments;

        var screenId            = args[0];
        var eventCode           = args[1];
        var strDelimiter        = args[2];
        var recDelimiter        = args[3];
        var typDelimiter        = args[4];

        for (var i=5; i<args.length; i++) {
        	dataString = dataString + args[i] + strDelimiter;
        }
        document.forms[1].pScreenId.value = screenId;
        document.forms[1].pEventCode.value = eventCode;
        document.forms[1].pDataString.value = dataString + recDelimiter + typDelimiter;
	document.forms[1].submit();

}

/**********************************************************************************/
/*****	This function forms the data string and goes to the relevent screen  ******/
/*****  when the hyperlink in the screen is clicked along with the values of ******/
/*****  the fields on the screen.			 	             ******/
/**********************************************************************************/
function setFormLinkValues() {
	exitOpenWindows();
        var dataString = "";
        var args = setFormLinkValues.arguments;

        var screenId            = args[0];
        var eventCode           = args[1];
        var strDelimiter        = args[2];
        var recDelimiter        = args[3];
        var typDelimiter        = args[4];
        var noFields		= args[5];

        for (var i=0; i<document.forms[0].elements.length; i++) {

                if (document.forms[0].elements[i].type == 'checkbox') {
                        if (document.forms[0].elements[i].checked) {
                                dataString = dataString + document.forms[0].elements[i].value + strDelimiter;
			}
                }
                else if (document.forms[0].elements[i].type == 'radio') {
                        if (document.forms[0].elements[i].checked) {
                                dataString = dataString + document.forms[0].elements[i].value + strDelimiter;
			}
                }
                else if (document.forms[0].elements[i].type == 'select-one') {
                        var sel = "";
                        for (var j=0; j<document.forms[0].elements[i].options.length; j++) {
                        	if (document.forms[0].elements[i].options[j].selected) {
                                	sel = document.forms[0].elements[i].options[j].value;
                           	}
                        }
                        dataString = dataString + sel + strDelimiter;
		}
                else if (document.forms[0].elements[i].type == 'text' || document.forms[0].elements[i].type == 'textarea' || document.forms[0].elements[i].type == 'hidden' || document.forms[0].elements[i].type == 'password' || document.forms[0].elements[i].type == 'file') {

                        dataString = dataString + document.forms[0].elements[i].value + strDelimiter;
		}
 		if ( i == noFields - 1 ) {
                        dataString = dataString + recDelimiter + typDelimiter;
                }

        }

        if (i > noFields) {
        	dataString = dataString + recDelimiter + typDelimiter;
        }

        for (var i=6; i < args.length; i++) {
                dataString = dataString + args[i] + strDelimiter;
        }

        document.forms[1].pScreenId.value = screenId;
        document.forms[1].pEventCode.value = eventCode;
     //   document.forms[1].pDataString.value = escape(dataString) + recDelimiter + typDelimiter;
	  document.forms[1].pDataString.value = dataString + recDelimiter + typDelimiter;
	 document.forms[1].submit();
}

/**********************************************************************************/
/*****	This function forms the data string and goes to the relevent screen  ******/
/*****  when the hyperlink in the screen is clicked along with the values of ******/
/*****  the fields on the screen.			 	             ******/
/**********************************************************************************/
function setFormRiskLinkValues() {
	exitOpenWindows();
        var dataString = "";
        var args = setFormRiskLinkValues.arguments;

        var screenId            = args[0];
        var eventCode           = args[1];
        var strDelimiter        = args[2];
        var recDelimiter        = args[3];
        var typDelimiter        = args[4];
        var noFields		= args[5];

        for (var i=0; i<document.forms[0].elements.length; i++) {

                if (document.forms[0].elements[i].type == 'checkbox') {
                        if (document.forms[0].elements[i].checked) {
                                dataString = dataString + document.forms[0].elements[i].value + strDelimiter;
			}
                }
                else if (document.forms[0].elements[i].type == 'radio') {
                        if (document.forms[0].elements[i].checked) {
                                dataString = dataString + document.forms[0].elements[i].value + strDelimiter;
			}
                }
                else if (document.forms[0].elements[i].type == 'select-one') {
                        var sel = "";
                        for (var j=0; j<document.forms[0].elements[i].options.length; j++) {
                        	if (document.forms[0].elements[i].options[j].selected) {
                                	sel = document.forms[0].elements[i].options[j].value;
                           	}
                        }
                        dataString = dataString + sel + strDelimiter;
		}
                else if (document.forms[0].elements[i].type == 'text' || document.forms[0].elements[i].type == 'textarea' || document.forms[0].elements[i].type == 'hidden' || document.forms[0].elements[i].type == 'password' || document.forms[0].elements[i].type == 'file') {

                        dataString = dataString + document.forms[0].elements[i].value + strDelimiter;
		}
 		if ( i == noFields - 1 ) {
                        dataString = dataString + recDelimiter + typDelimiter;
                }

        }

        if (i > noFields) {
        	dataString = dataString + recDelimiter + typDelimiter;
        }

        for (var i=6; i < args.length; i++) {
                dataString = dataString + args[i] + strDelimiter;
        }

        document.forms[1].pScreenId.value = screenId;
        document.forms[1].pEventCode.value = eventCode;
        document.forms[1].pDataString.value = dataString + recDelimiter + typDelimiter;
	if (typeof(parent.parent.Control)!= 'undefined') {
		if (typeof(parent.parent.Control.document.FormUnderwritignControl.pPolicyTabMode) != 'undefined') {
			parent.parent.Control.document.FormUnderwritignControl.pPolicyTabMode.value = document.forms[1].pScreenMode.value;
			document.forms[1].pScreenMode.value = 1;
		}
	}
	document.forms[1].submit();
}

/**********************************************************************************/
/*****	This function forms the data string and goes to the relevent screen  ******/
/*****  when the hyperlink in the screen is clicked along with the values of ******/
/*****  the fields on the screen.			 	             ******/
/**********************************************************************************/
function setFormRiskCoverLinkValues() {
	exitOpenWindows();
        var dataString = "";
        var args = setFormRiskCoverLinkValues.arguments;

        var screenId            = args[0];
        var eventCode           = args[1];
        var strDelimiter        = args[2];
        var recDelimiter        = args[3];
        var typDelimiter        = args[4];
        var noFields		= args[5];

        for (var i=0; i<document.forms[0].elements.length; i++) {

                if (document.forms[0].elements[i].type == 'checkbox') {
                        if (document.forms[0].elements[i].checked) {
                                dataString = dataString + document.forms[0].elements[i].value + strDelimiter;
			}
                }
                else if (document.forms[0].elements[i].type == 'radio') {
                        if (document.forms[0].elements[i].checked) {
                                dataString = dataString + document.forms[0].elements[i].value + strDelimiter;
			}
                }
                else if (document.forms[0].elements[i].type == 'select-one') {
                        var sel = "";
                        for (var j=0; j<document.forms[0].elements[i].options.length; j++) {
                        	if (document.forms[0].elements[i].options[j].selected) {
                                	sel = document.forms[0].elements[i].options[j].value;
                           	}
                        }
                        dataString = dataString + sel + strDelimiter;
		}
                else if (document.forms[0].elements[i].type == 'text' || document.forms[0].elements[i].type == 'textarea' || document.forms[0].elements[i].type == 'hidden' || document.forms[0].elements[i].type == 'password' || document.forms[0].elements[i].type == 'file') {

                        dataString = dataString + document.forms[0].elements[i].value + strDelimiter;
		}
 		if ( i == noFields - 1 ) {
                        dataString = dataString + recDelimiter + typDelimiter;
                }

        }

        if (i > noFields) {
        	dataString = dataString + recDelimiter + typDelimiter;
        }

        for (var i=6; i < args.length; i++) {
                dataString = dataString + args[i] + strDelimiter;
        }

        document.forms[1].pScreenId.value = screenId;
        document.forms[1].pEventCode.value = eventCode;
        document.forms[1].pDataString.value = dataString + recDelimiter + typDelimiter;
	if (typeof(parent.parent.Control)!= 'undefined') {
		if (typeof(parent.parent.Control.document.FormUnderwritignControl.pRiskTabMode) != 'undefined') {
			parent.parent.Control.document.FormUnderwritignControl.pRiskTabMode.value = document.forms[1].pScreenMode.value;
			document.forms[1].pScreenMode.value = 1;
		}
	}
	document.forms[1].submit();
}

/**********************************************************************************/
/*****	This function checks whether changes have taken place in the 	     ******/
/*****  screen, validates the changes and then submits the form.             ******/
/**********************************************************************************/
function chkChangeValidateSubmit() {

	//Validate and Submit after checking whether changes were made on the screen.

	if (checkChange()) {

		var args = chkChangeValidateSubmit.arguments;
		var argList = "'" + args[0] + "'";
		for ( i=1; i < (args.length-5); i++ ) {
			argList = argList + "," + "'" + args[i] + "'";
		}

		// 	Alternatively instead of calling the leaf functions could have been written thus :
		//
		//	for ( i=1; i < (args.length-1); i++ ) {
		//		argList = argList + "," + "'" + args[i] + "'";
		//	}
		//	eval("validateAndSubmit(" + argList + ")");
		//

		if ( eval("validateScreen(" + argList + ")") ) {

			setValuesAndSubmit(args[args.length-6]
					  ,args[args.length-5]
				  	  ,args[args.length-4]
					  ,args[args.length-3]
					  ,args[args.length-2]
					  ,args[args.length-1]);
		}

	}
	else {
		alert('\n\nNo field updated.\n\n');
	}
}

/**********************************************************************************/
/*****	This function checks whether changes have taken place in the 	     ******/
/*****  screen, validates the changes and then submits the form.             ******/
/**********************************************************************************/
function tabValidateSubmit() {

	//Validate and Submit after checking whether changes were made on the screen.

	if (!checkChange()) {

		var args = tabValidateSubmit.arguments;
		var argList = "'" + args[0] + "'";

		for ( i=1; i < (args.length-5); i++ ) {
			argList = argList + "," + "'" + args[i] + "'";
		}

		// 	Alternatively instead of calling the leaf functions could have been written thus :
		//
		//	for ( i=1; i < (args.length-1); i++ ) {
		//		argList = argList + "," + "'" + args[i] + "'";
		//	}
		//	eval("validateAndSubmit(" + argList + ")");
		//

		if ( eval("validateScreen(" + argList + ")") ) {

			setValuesAndSubmit(args[args.length-6]
					  ,args[args.length-5]
				  	  ,args[args.length-4]
					  ,args[args.length-3]
					  ,args[args.length-2]
					  ,args[args.length-1]);
		}

	}
	else {
		alert('\n\nPlease first save the changes that were made on the screen\n\n');
	}
}

/**********************************************************************************/
/****	This function check whether any field value changed by user or not.    ****/
/****	It compare the existing value with its default value.                  ****/
/****	It take HTML form name as input parameter and return boolean as output.****/
/**********************************************************************************/
function checkChange() {

	var i;
	flag = 0;

	for( i=0; i<document.forms[0].elements.length; i++) {
		if (( document.forms[0].elements[i].type == 'text') || ( document.forms[0].elements[i].type == 'textarea') || ( document.forms[0].elements[i].type == 'password')) {
			if (document.forms[0].elements[i].defaultValue != document.forms[0].elements[i].value) flag = 1;
		}
		if ( document.forms[0].elements[i].type == 'select-one' && ( document.forms[0].elements[i].length != 0 )) {
			if (navigator.appName == 'Netscape') {
				if (! document.forms[0].elements[i].options[document.forms[0].elements[i].selectedIndex].defaultSelected) flag = 1;
			}
			else {
				for( j=0; j < document.forms[0].elements[i].length; j++ ) {
					if ( document.forms[0].elements[i].options[j].selected != document.forms[0].elements[i].options[j].defaultSelected ) {
						flag = 1;
					}
				}
			}
		}
		if ( document.forms[0].elements[i].type == 'radio' ) {
			if (document.forms[0].elements[i].defaultChecked != document.forms[0].elements[i].checked) flag = 1;
		}
		if ( document.forms[0].elements[i].type == 'checkbox' ) {
			if (document.forms[0].elements[i].defaultChecked != document.forms[0].elements[i].checked) flag = 1;
		}
	}

	if (flag == 1) {
		return true;
	} else {
		return false;
	}
}

/**********************************************************************************/
/*****	This function validates and submits the form.                        ******/
/**********************************************************************************/
function validateAndSubmit() {
	//Blank Search Disabling.
	var isOneFieldPopulated = false;

	for (i=0; i<document.forms[0].elements.length; i++)
	{


		if ((document.forms[0].elements[i].type == "text") && (document.forms[0].elements[i].value != ""))
			isOneFieldPopulated = true;
		else if ((document.forms[0].elements[i].type == 'checkbox') && (document.forms[0].elements[i].checked))
		{
			isOneFieldPopulated = true;

		}
		else if ((document.forms[0].elements[i].type == 'radio') && (document.forms[0].elements[i].checked))
			isOneFieldPopulated = true;
		else if (document.forms[0].elements[i].type == 'select-one')
		{
	   		var sel = "";
	 		for (var j=0; j<document.forms[0].elements[i].options.length; j++)
	 		{
		    	if (document.forms[0].elements[i].options[j].selected)
		    	{
	     			sel = document.forms[0].elements[i].options[j].value;
	    		}
	   		}
	   		if((sel!= "") && (sel!=null) && (sel != "0"))
	   			isOneFieldPopulated = true;
	  }

	}


	if (!isOneFieldPopulated)
		alert("Blank Search is not allowed.");
	else
	{

		//Validate and Submit without checking change.
		var args = validateAndSubmit.arguments;
		var argList = "'" + args[0] + "'";



		for ( i=1; i < (args.length-5); i++ ) {
			argList = argList + "," + "'" + args[i] + "'";
		}

		if ( eval("validateScreen(" + argList + ")") ) {



			setValuesAndSubmit(args[args.length-6]
					  ,args[args.length-5]
					  ,args[args.length-4]
					  ,args[args.length-3]
					  ,args[args.length-2]
					  ,args[args.length-1]);
		}
	}
}

/**********************************************************************************/
/*****	This function validates the elements in the screen.                  ******/
/**********************************************************************************/
function validateScreen() {

	var i,objStr,field,theString,theName,theCheck,frm,errors='',vCount=0,vNewCount = 0 ;
	var vCheckedFlag=0;
	var j;

	for (i=0; i<(validateScreen.arguments.length-1); i+=2) {

		objStr = validateScreen.arguments[i];
		field = eval(objStr);
		theString = validateScreen.arguments[i+1];
		theCheck = theString.substring(0, theString.indexOf('*'));
		theName =  theString.substring(theString.indexOf('*')+1, theString.length);

		if (field.value) {

			if ((theCheck.indexOf('Length') != -1)&&(field.value.length) > (theCheck.substring((theCheck.indexOf('Length')+6),theCheck.length)))
				errors += '- '+theName+' must less than '+theCheck.substring((theCheck.indexOf('Length')+6),theCheck.length)+' character(s).\n';

			if ((theCheck.indexOf('minLen') != -1)&&(field.value.length) < (theCheck.substring((theCheck.indexOf('minLen')+6),theCheck.length)))
				errors += '- '+theName+' must greater than '+theCheck.substring((theCheck.indexOf('minLen')+6),theCheck.length)+' character(s).\n';

			if ((theCheck.indexOf('isNum') != -1)&&(!isPositiveInteger(field.value)))
				errors += '- '+theName+' must be an Positive number.\n';

			if ((theCheck.indexOf('isInteger') != -1)&&(!isInteger(field.value)))
				errors += '- '+theName+' must be an number.\n';

			if ((theCheck.indexOf('isDecimal') != -1)&&((field.value == '.') ||(!isDecimal(field.value))))
				errors += '- '+theName+' must be a decimal.\n';

			if ((theCheck.indexOf('isAmount') != -1)&&((field.value == '.') || (!isAmount(field,field.value))))
				errors += '- '+theName+' should be a valid amount field.\n';

			if ((theCheck.indexOf('isMaskedAmount') != -1)&&(!isMaskedAmount(field,field.value)))
				errors += '- '+theName+' should be a valid amount field.\n';

			if ((theCheck.indexOf('isNonZeroAmount') != -1)&& ((field.value == '.')||(!isNonZeroAmount(field,field.value))))
				errors += '- '+theName+' should be a valid amount field.\n';

			if ((theCheck.indexOf('isEmail') != -1)&&(!isEmail(field.value)))
				errors += '- '+theName+' must be a valid email.\n';

			if ((theCheck.indexOf('isDate') != -1)&&(!isDate(field.value)))
				errors += '- '+theName+' must be a date.\n';

			if ((theCheck.indexOf('isPastDate') != -1)&&(!isPastDate(field)))
				errors += '- '+theName+' can only be a past or todays date.\n';
			//This part is specific to Accounting Transaction details screen
			if ((theCheck.indexOf('isVoucherDate') != -1)&&(!isVoucherDate(field)))
				errors += '- '+theName+' can only be a past or todays date.\n';
			if ((theCheck.indexOf('isTime') != -1)&&(!isTime(field.value)))
				errors += '- '+theName+' must be a valid time.\n';

			if ((theCheck.indexOf('isChar') != -1)&&(!isChar(field.value)))
				errors += '- '+theName+' must be alphabetic.\n';

			if ((theCheck.indexOf('isAlpha') != -1)&&(!isAlphanumeric(field.value)))
				errors += '- '+theName+' must be alphanumeric.\n';

			if (theCheck.indexOf('isPositiveDecimal') != -1) {
				if (!isDecimal(field.value)) {
					errors += '- '+theName+' must be a decimal.\n';
				}
				else {
					if ((field.value).indexOf('-') != -1) {
						errors += '- '+theName+' must be positive.\n';
					}
					else if (field.value == '.') {
						errors += '- '+theName+' must be decimal.\n';
					}
					else if (field.value> 999999999999.99 || field.value<= 0.00){
						alert (theName + ' should be less than 999999999999.99\nand greater than 0.00');
						field.select();
						field.focus();
						return false;
					}
				}
			}
			if (theCheck.indexOf('isNegativeDecimal') != -1) {
				if (!isDecimal(field.value)) {
					errors += '- '+theName+' must be a decimal.\n';
				}
				else if (field.value == '.') {
					errors += '- '+theName+' must be decimal.\n';
				}
				else {
					if ((field.value).indexOf('-') != 0) {
						errors += '- '+theName+' must be negative.\n';
					}
				}
			}

			if ((theCheck.indexOf('isNegativeInt') != -1)&&(!isNegativeInteger(field.value)))
				errors += '- '+theName+' must be a Negative integer.\n';

			if (theCheck.indexOf('isPercent') != -1) {
				if (!isDecimal(field.value)) {
					errors += '- '+theName+' must be a decimal.\n';
				}
				else {
					if ( field.value > 100 ) {
						errors += '- '+theName+' must be less than 100 being a percentage.\n';
					}
				}
			}

			if (theCheck.indexOf('validateDate') != -1) {
				if (!isDate(field.value)) {
					errors += '- '+theName+' must be a date.\n';
				}
				else {
					if (!validateDate(field)) {
						errors += '- '+theName+' must be greater than SYSDATE.\n';
					}
				}
			}

		}else if ((theCheck.charAt(0) == 'R') && (theCheck.indexOf('isChecked') == -1)) errors += '- '+theName+' is required.\n';

		if (theCheck.indexOf('isChecked') != -1) {

			var vCount = field.length;
			if (typeof(vCount) == "undefined"){
				if (field.type == 'checkbox') {
					if (!field.checked) errors += '- '+theName+' is required.\n';
				}
				else {
					for (j=0; j<field.length; j++) {
						if (field[j].checked) vCheckedFlag = 1;
					}
					if (vCheckedFlag != 1) errors += '- '+theName+' is required.\n';
				}
			}
			else
			{
				var vNoofChecked = 0;
				for (vCounter=0; vCounter < vCount; vCounter++) {
					if ((field[vCounter].checked == true) && (field[vCounter].disabled != true)) {
						vNoofChecked = 1;
						break;
					}
				}
				if (vNoofChecked == 0) {
					if (event != null){
						errors += '- '+theName+' is required.\n';
						event.returnValue = false;
					}
					else {
						errors += '- '+theName+' is required.\n';
					}
				}
			}
		}

		if ( field.name == 'pEffectiveStartDate') vCount = vCount+1;
		if ( field.name == 'pEffectiveEndDate') vCount = vCount+1;
		if ( field.name == 'pEventDate') vNewCount = vNewCount + 1;

		if ( field.name == 'pEvent') field.value = theString;

	}
	if ( vCount == 2 && vNewCount == 0) {
		if (! crossValidateDate(document.forms[0].pEffectiveStartDate, document.forms[0].pEffectiveEndDate))
			errors += '- From date can not be greater than to date.\n';
	}

	if (errors) {
		alert('The following error(s) occurred:\n\n'+errors);
	}
	else {
		return true;
	}


}

/**********************************************************************************/
/*****	This function forms the string and submits the second form.          ******/
/**********************************************************************************/
function setValuesAndSubmit() {
	exitOpenWindows();
	// Setting the values and submitting the second form.

	var dataString = "";
	var args = setValuesAndSubmit.arguments;

	var screenId            = args[args.length-6];
	var eventCode           = args[args.length-5];
	var strDelimiter        = args[args.length-4];
	var recDelimiter        = args[args.length-3];
	var typDelimiter        = args[args.length-2];
	var noFields	        = args[args.length-1];

	var newPassword		=args[args.length-7];


	for (var i=0; i<document.forms[0].elements.length; i++) {

		if ( i == noFields ) {
                        dataString = dataString + recDelimiter + typDelimiter;

                }

		if (document.forms[0].elements[i].type == 'checkbox') {
			if (document.forms[0].elements[i].checked) {
				dataString = dataString + document.forms[0].elements[i].value + strDelimiter;
			}
		}
		else if (document.forms[0].elements[i].type == 'radio') {
			if (document.forms[0].elements[i].checked) {
		                dataString = dataString + document.forms[0].elements[i].value + strDelimiter;
			}
	        }
		else if (document.forms[0].elements[i].type == 'select-one') {
			var sel = "";

		        for (var j=0; j<document.forms[0].elements[i].options.length; j++) {
		        	if (document.forms[0].elements[i].options[j].selected) {
		                	sel = document.forms[0].elements[i].options[j].value;
		                }
		        }
		        dataString = dataString + sel + strDelimiter;
		}
		else if (document.forms[0].elements[i].type == 'text' || document.forms[0].elements[i].type == 'textarea' || document.forms[0].elements[i].type == 'hidden' || document.forms[0].elements[i].type == 'password' || document.forms[0].elements[i].type == 'file') {
                        document.forms[0].elements[i].value = trim(document.forms[0].elements[i].value);
			dataString = dataString + document.forms[0].elements[i].value + strDelimiter;
		}

	}
	if (eventCode=='report' && (screenId=='S0021' || screenId=='S0022'))
	{
		dataString = dataString + recDelimiter + typDelimiter;
		//var theURL = callingProcess +'?pScreenId='+ screenId +'&pEventCode=eventCode&pDataString=' + escape(dataString);
		var theURL = 'PCSEC_PROCESS.jsp' + '?pScreenId='+ screenId + '&pEventCode=' + eventCode + '&pUserId=' + document.forms[1].pUserId.value + '&pRoleId=' + document.forms[1].pRoleId.value +  '&pBranchId=' + document.forms[1].pBranchId.value + '&pDataString=' + escape(dataString);
		openWindowForAuditTrail(theURL);
	}
	else if (eventCode=='PartyReActivateList' && (screenId=='PTYS0110' || screenId=='PTYS0120'))
	{

		dataString = dataString + recDelimiter + typDelimiter;
		//var theURL = callingProcess +'?pScreenId='+ screenId +'&pEventCode=eventCode&pDataString=' + escape(dataString);
		var theURL = 'PCPTY.process' + '?pScreenId='+ screenId + '&pEventCode=' + eventCode + '&pUserId=' + document.forms[1].pUserId.value + '&pRoleId=' + document.forms[1].pRoleId.value +  '&pBranchId=' + document.forms[1].pBranchId.value +  '&pVersionDate=' + document.forms[1].pVersionDate.value + '&pDataString=' + escape(dataString);
		openWindowForAuditTrail(theURL);
	}
	else if (eventCode=='DeleteWithReason' && (screenId=='PTYS0110' || screenId=='PTYS0120'))
	{

		dataString = dataString + recDelimiter + typDelimiter;
		//var theURL = callingProcess +'?pScreenId='+ screenId +'&pEventCode=eventCode&pDataString=' + escape(dataString);
		var theURL = 'PCPTY.process' + '?pScreenId='+ screenId + '&pEventCode=' + eventCode + '&pUserId=' + document.forms[1].pUserId.value + '&pRoleId=' + document.forms[1].pRoleId.value +  '&pBranchId=' + document.forms[1].pBranchId.value +  '&pVersionDate=' + document.forms[1].pVersionDate.value + '&pDataString=' + escape(dataString);
		openWindowForAuditTrail(theURL);
	}


	else
	{
		if (eventCode=='save' && screenId=='PTYS0030')
		{
			dataString = dataString + document.forms[1].pUserId.value;
		}
		document.forms[1].pScreenId.value = screenId;
		document.forms[1].pEventCode.value = eventCode;
		document.forms[1].pDataString.value = dataString + recDelimiter + typDelimiter;
		if (eventCode=='CLONE' && screenId=='SECS0030')
		{
			var UserCode = document.forms[1].pUserCode.value;
			dataString = dataString + document.forms[1].pUserId.value + recDelimiter + typDelimiter;
			window.close();
			var theURL = 'PCSEC_PROCESS.jsp' + '?pScreenId='+ screenId + '&pEventCode=' + eventCode + '&pUserCode=' + UserCode + '&pUserId=' + document.forms[1].pUserId.value + '&pRoleId=' + document.forms[1].pRoleId.value +  '&pBranchId=' + document.forms[1].pBranchId.value +  '&pDataString=' + escape(dataString);
			//alert(theURL);
			opener.location = theURL;
		}
		/* changed by raktim start*/
		else if (eventCode=='save' && (screenId=='ChangePassword'))
		{
			document.forms[1].pPassword.value = newPassword;
			document.forms[1].submit();
		}
		/* end */
		else
		{
			document.forms[1].submit();
		}
	}



}


function openWindowForAuditTrail(theURL)
{
	var winOption = "height=400,width=600,top=10,left=10,scrollbars=yes";
	if (!windowId) {
		var win = window.open(theURL,"title",winOption);
		windowId = win
		win.moveTo(200, 200);
	}
	else
	{
		windowId.close();
		var win = window.open(theURL,"title",winOption);
		windowId = win
		win.moveTo(200, 200);
	}

}

/**********************************************************************************/
/*****	These functions delete spaces at start and end of onputs  	     ******/
/**********************************************************************************/
function Ltrim(fieldValue)
{
var trimmedString="";
	for(var j=0; j<fieldValue.length; j++)
	{
		var eachChar = fieldValue.substring(j,j+1);
		if(j>0 && j<(fieldValue.length-1))
		{
			var leftChar = fieldValue.substring(j-1,j);
			var rightChar = fieldValue.substring(j+1,j+2);
			if(eachChar==" ")
			{
				if(leftChar!=" ")
				{
					trimmedString=trimmedString+eachChar;
				}
				else
				{

				}

			}
			else
			{
				trimmedString=trimmedString+eachChar;
			}
		}
		else
		{
			if(eachChar!=" ")
			{
				trimmedString=trimmedString+eachChar;
			}
		}
	}
	return 	trimmedString;

}
function Rtrim(fieldValue)
{
var trimmedString="";
	for(var j=0; j<fieldValue.length; j++)
	{
		var eachChar = fieldValue.substring(j,j+1);
		if(j>0 && j<(fieldValue.length-1))
		{
			var leftChar = fieldValue.substring(j-1,j);
			var rightChar = fieldValue.substring(j+1,j+2);
			if(eachChar==" ")
			{
				if(rightChar!=" ")
				{
					trimmedString=trimmedString+eachChar;
				}
				else
				{

				}

			}
			else
			{
				trimmedString=trimmedString+eachChar;
			}
		}
		else
		{
			if(eachChar!=" ")
			{
				trimmedString=trimmedString+eachChar;
			}
		}
	}
	return 	trimmedString;

}
function trim(fieldValue)
{
return Ltrim(Rtrim(fieldValue));
}

/**********************************************************************************/
/*****	This function clears the checked fields. It takes as arguments 	     ******/
/*****	the name of the checkbox and whatever horizontal fields that 	     ******/
/*****	have to be cleared  						     ******/
/**********************************************************************************/
function clearChecked() {

	var field;
	var argCheckbox;
	var len;
	var args = clearChecked.arguments;

	argCheckbox = eval(args[0]);
	len = argCheckbox.length;

	if (argCheckbox.type == 'checkbox') {
		for ( i=1; i<args.length; i++)	{

			field = eval(args[i]);

			if (typeof(len) == 'undefined') {
				if (argCheckbox.checked == true) {
					field.value = "";
				}
			}
			else {
				for(j=0; j<len; j++) {

					if ( argCheckbox[j].checked == true ) {
						field[j].value = "";
					}
				}
			}
		}
	}
}

/**********************************************************************************/
/*****	This function clears all the checkboxes. It takes as arguments 	     ******/
/*****	the name of the checkboxes that have to be cleared	 	     ******/
/**********************************************************************************/
function clearCheckBox() {

	var argCheckbox;
	var len;
	var args = clearCheckBox.arguments;

	for (i=0; i<args.length; i++)	{

		argCheckbox = eval(args[i]);
		len = argCheckbox.length;

		if (argCheckbox.type == 'checkbox') {
			if (typeof(len) == 'undefined') {
				argCheckbox.checked = false;
			}
			else {
				for(j=0; j<len; j++) {
					argCheckbox[j].checked = false;
				}
			}
		}
	}
}

/**********************************************************************************/
/*****	This function checks all the checkboxes. It takes as arguments 	     ******/
/*****	the name of the checkboxes that have to be checked	 	     ******/
/**********************************************************************************/
function checkAll() {

	var argCheckbox;
	var len;
	var args = checkAll.arguments;

	for (i=0; i<args.length; i++)	{

		argCheckbox = eval(args[i]);
		len = argCheckbox.length;

		if (typeof(len) == 'undefined') {
			if (argCheckbox.type == 'checkbox') {
				argCheckbox.checked = true;
			}
		}
		else {
			for(j=0; j<len; j++) {
				if (argCheckbox[j].type == 'checkbox') {
					argCheckbox[j].checked = true;
				}
			}
		}
	}
}

/**********************************************************************************/
/*****	This function checks all the checkboxes. It takes as arguments 	     ******/
/*****	the name of the checkboxes that have to be checked	 	     ******/
/**********************************************************************************/
function checkUncheckAll() {

	var argCheckbox;
	var len;
	var args = checkUncheckAll.arguments;
	var initArgCheckbox = eval(args[0]);
	for (i=1; i<args.length; i++)	{

	   argCheckbox = eval(args[i]);
	   if (typeof(argCheckbox) != 'undefined') {

		len = argCheckbox.length;

		if (typeof(len) == 'undefined') {
			if (argCheckbox.type == 'checkbox') {
				if (initArgCheckbox.checked == true) {
					argCheckbox.checked = true;
				}
				else {
					argCheckbox.checked = false;
				}
			}
		}
		else {
			for(j=0; j<len; j++) {
				if (argCheckbox[j].type == 'checkbox') {
					if (initArgCheckbox.checked == true) {
						argCheckbox[j].checked = true;
					}
					else {
						argCheckbox[j].checked = false;
					}
				}
			}
		}
	}
	}//for
}


/**********************************************************************************/
/*****	This function adds the fields. It takes as arguments 	     	     ******/
/*****	the name of the totalfield and whatever horizontal fields that 	     ******/
/*****	have to be added  						     ******/
/**********************************************************************************/
function addTotal() {

	var field;
	var argTotal;
	var args = addTotal.arguments;
	var total;
	var prevTotal;
	for ( i=0; i<(args.length-1); i=i+2) {

		argTotal = eval(args[i]);
		field = eval(args[i+1]);
		var len = field.length;

		if (typeof(len) == 'undefined') {
			if (field.value != '' && (!isNaN(field.value))) {
				prevTotal = argTotal.value;
				total = field.value;
			}
			if (isNaN(field.value)) {
				alert('Should be a valid No');
				field.value = prevTotal;
				total = prevTotal;
				field.select();
				field.focus();
				return;
			}
			argTotal.value = total;
		}
		else {
			total = 0;
			for(j=0; j<len; j++) {
				if (field[j].value != '' && (!isNaN(field[j].value))) {
					prevTotal = argTotal.value;
					total = total + parseFloat(field[j].value);
				}
				if (isNaN(field[j].value)) {
					alert('Should be a valid No');

					// These gimmicks are being done as I am not sure

					total = 0;
					for(k=0; k<len; k++) {
						if ((field[k].value != '') && (j != k)) {
							total = total + parseFloat(field[k].value);
						}
					}
					field[j].value = ( prevTotal - total );

					// if the value before change can be captured.....Anjan

					total = prevTotal;
					field[j].select();
					field[j].focus();
					return;
				}
			}
			argTotal.value = total;
		}
	}
}

/**********************************************************************************/
/*****	This function adds the fields. It takes as arguments 	     	     ******/
/*****	the name of the totalfield and whatever horizontal fields that 	     ******/
/*****	have to be added given some condition. This is incomplete in 	     ******/
/*****	the sense that it is hardcoded				 	     ******/
/**********************************************************************************/
function addTotalGivenCondition() {

	var field;
	var conditionField;
	var conditionValue;
	var argTotal;
	var args = addTotalGivenCondition.arguments;
	var total;
	var prevTotal;
	for ( i=0; i<(args.length-3); i=i+4)	{

		argTotal = eval(args[i]);

		field = eval(args[i+1]);
		conditionField = eval(args[i+2]);
		conditionValue = args[i+3];

		var len = field.length;
		if (typeof(len) == 'undefined') {
			if ((field.value != '') && (conditionField.value == conditionValue) && (!isNaN(field.value))) {
				prevTotal = argTotal.value;
				total = field.value;
			}
			if (isNaN(field.value)) {
				alert('Should be a valid No');
				field.value = prevTotal;
				total = prevTotal;
				field.select();
				field.focus();
				return;
			}
			argTotal.value = total;
		}
		else {
			total = 0;

			for(j=0; j<len; j++) {

				if ((field[j].value != '') && (conditionField[j].value == conditionValue) && (!isNaN(field[j].value))) {
					prevTotal = argTotal.value;
					total = total + parseFloat(field[j].value);
				}
				if (isNaN(field[j].value)) {
					alert('Should be a valid No');

					// These gimmicks are being done as I am not sure

					total = 0;
					for(k=0; k<len; k++) {
						if ((field[k].value != '') && (j != k) &&
						    (conditionField[k].value == conditionValue)) {
							total = total + parseFloat(field[k].value);
						}
					}
					field[j].value = ( prevTotal - total );

					// if the value before change can be captured.....Anjan

					total = prevTotal;
					field[j].select();
					field[j].focus();
					return;
				}
			}
			argTotal.value = total;
		}
	}
}

/**********************************************************************************/
/*****	This function is used for opening a window for the searches.	     ******/
/**********************************************************************************/
function openWindow(callingProcess,screenId,fieldSep, recSep, typSep, winName, features) {

	var dataString = '';
	var pUserId = document.forms[1].pUserId.value;
	var pRoleId = document.forms[1].pRoleId.value;
	for (i=7; i < openWindow.arguments.length; i++) {

		if (typeof(openWindow.arguments[i]) == 'string') {
			dataString = dataString + openWindow.arguments[i];
		}
		else {
			dataString = dataString + eval(openWindow.arguments[i]).value;
		}
		dataString = dataString + fieldSep;
	}

	dataString = dataString + recSep + typSep;

        var theURL = callingProcess +'?pScreenId='+ screenId +'&pEventCode=Find&pDataString=' + escape(dataString)+'&pUserId='+pUserId+'&pRoleId='+pRoleId;
	if (!windowId) {
		var win = window.open(theURL,winName,features);
		windowId = win
		win.moveTo(200, 100);
	}
	else
	{
		windowId.close();
		var win = window.open(theURL,winName,features);
		windowId = win
		win.moveTo(300, 200);
	}
}

/**********************************************************************************/
/*****	This function is used for opening a window for the searches.	     ******/
/**********************************************************************************/
function openGenericWindow(callingProcess, screenId, eventCode, fieldSep, recSep, typSep, winName, features) {

	var dataString = '';

	var pUserId = document.forms[1].pUserId.value;
	var pRoleId = document.forms[1].pRoleId.value;

	for (i=8; i < openGenericWindow.arguments.length; i++) {
		if (typeof(openGenericWindow.arguments[i]) == 'string') {
			dataString = dataString + openGenericWindow.arguments[i];
		}
		else {
			dataString = dataString + eval(openGenericWindow.arguments[i]).value;
		}
		dataString = dataString + fieldSep;
	}
	dataString = dataString + recSep + typSep;
        var theURL = callingProcess +'?pScreenId='+ screenId +'&pEventCode=' + eventCode + '&pDataString=' + escape(dataString) +'&pUserId='+pUserId+'&pRoleId='+pRoleId;
	if (!windowId) {
		var win = window.open(theURL,winName,features);
		windowId = win
		win.moveTo(100, 100);
	}
	else
	{
		windowId.close();
		var win = window.open(theURL,winName,features);
		windowId = win
		win.moveTo(100, 100);
	}
}

/**********************************************************************************/
/*****	This function puts the parameters for the selected row		     ******/
/*****	to the corresponding placeholders in the screen.		     ******/
/**********************************************************************************/
function selectFields(str, fieldSep) {


  	var replacePosition;
  	while(1) {
      		replacePosition =   str.indexOf("‡");
      		if (replacePosition==-1) break;
      		str = str.substring(0,replacePosition)+"'"+str.substring(replacePosition+1, str.length);
  	}


  	var windowName = window.name;


  	var indxLast = windowName.lastIndexOf("XYZ");

  	var sourceForm = windowName.substring(indxLast+3, windowName.length);

        var indxVal = new Array();
  	var textValue = new Array();
  	var indx = new Array();
  	var textField = new Array();
  	var strText = new Array();
  	var strItem;
  	var indxZYX;

  	indxVal[0] = str.indexOf(fieldSep);
  	textValue[0] = str.substring(0, indxVal[0]);
  	indx[0] = windowName.indexOf("XYZ");
  	textField[0] = windowName.substring(0, indx[0]);
  	if ( indx[0] < indxLast ) {

		for (i=0; (indx[i] < indxLast); i++) {

         		indxVal[i+1] = str.indexOf(fieldSep, indxVal[i]+1);


         		if ( indxVal[i+1] > (indxVal[i]+1) ) {
            			textValue[i+1] = str.substring(indxVal[i]+1, indxVal[i+1]);
            			         		}
         		else {

      	    			textValue[i+1] = '';
         		}

         		indx[i+1] = windowName.indexOf("XYZ", indx[i]+1);
         		textField[i+1] = windowName.substring(indx[i]+3, indx[i+1]);
	 		strText[i+1] = "window.opener.document." + sourceForm + "." + textField[i+1];

			indxZYX = strText[i+1].indexOf('ZYX');
			if (indxZYX != -1 ) {
				strText[i+1] = strText[i+1].replace('ZYX','[');
				strText[i+1] = strText[i+1] + ']';
			}
			strItem = eval(strText[i+1]);

     	 		strItem.value = textValue[i+1];

     		}

	        textValue[i] = str.substring(indxVal[i-1]+1, str.length);
	        strText[i] = "window.opener.document." + sourceForm + "." + textField[i];

		indxZYX = strText[i].indexOf('ZYX');
		if (indxZYX != -1 ) {
			strText[i] = strText[i].replace('ZYX','[');
			strText[i] = strText[i] + ']';
		}

	        strItem = eval(strText[i]);

	        strItem.value = textValue[i];

  	}
  	else if ( indx[0] == indxLast ) {


		textValue[0] = str;


  	}

  	strText[0] = "window.opener.document." + sourceForm + "." + textField[0];

	indxZYX = strText[0].indexOf('ZYX');
	if (indxZYX != -1 ) {
		strText[0] = strText[0].replace('ZYX','[');
		strText[0] = strText[0] + ']';
	}
	strItem = eval(strText[0]);
  	strItem.value = textValue[0];


  	window.close();

}

/**********************************************************************************/
/*****	This function when invoked transforms the screen to read-only.	     ******/
/**********************************************************************************/
function viewOnly()
{

        firstFieldFocus();
/**	if ((event.button==1) || (event.button==2) || (event.button==0) || (event.button==4)) {
		str = "Beware!!! You are trying to be smart like Prasanta!!\n Remember this screen is read-only";
	}  **/
	if (navigator.appName == 'Netscape') {
		for ( i=0; i<document.forms[0].elements.length; i++) {
				document.forms[0].elements[i].onfocus = document.forms[0].elements[i].blur;
				document.forms[0].elements[i].style.background="#E1E1E1";
				document.forms[0].elements[i].style.color="#000000";
		}
	}
	else {
		for ( i=0; i<document.forms[0].elements.length; i++) {
			if (( document.forms[0].elements[i].type == 'text')
			|| ( document.forms[0].elements[i].type == 'textarea')
			|| ( document.forms[0].elements[i].type == 'hidden')) {
				document.forms[0].elements[i].readOnly = true;
				document.forms[0].elements[i].style.background="#E1E1E1";
				document.forms[0].elements[i].style.color="#000000";
			}
			else {
				document.forms[0].elements[i].disabled = true;
				document.forms[0].elements[i].style.background="#E1E1E1";
				//document.forms[0].elements[i].style.color="#000000";
			}
		}
	}

}

/*********************************************************************************/
/******************** Opening of a Document **************************************/
/*********************************************************************************/
function openDocument() {
        var dataString = "";
	var pUserId = document.forms[1].pUserId.value;
	var pRoleId = document.forms[1].pRoleId.value;

        var args = openDocument.arguments;
	var vCount =0;
	var i;

        var screenId            = args[0];
        var eventCode           = args[1];
        var strDelimiter        = args[2];
        var recDelimiter        = args[3];
        var typDelimiter        = args[4];
        var noFields	        = args[5];
        var callingProcess	= args[6];
        var winName	        = args[7];
	if (noFields != 0) {
	        for (i=0; i<noFields; i++) {
			if (document.forms[0].elements[i].type == 'checkbox') {
				if (document.forms[0].elements[i].checked) {
					dataString = dataString + document.forms[0].elements[i].value + strDelimiter;
				}
			}
			else if (document.forms[0].elements[i].type == 'radio') {
				if (document.forms[0].elements[i].checked) {
			                dataString = dataString + document.forms[0].elements[i].value + strDelimiter;
				}
		        }
			else if (document.forms[0].elements[i].type == 'select-one') {
				var sel = "";

			        for (var j=0; j<document.forms[0].elements[i].options.length; j++) {
			        	if (document.forms[0].elements[i].options[j].selected) {
			                	sel = document.forms[0].elements[i].options[j].value;
			                }
			        }
			        dataString = dataString + sel + strDelimiter;
			}
			else if (document.forms[0].elements[i].type == 'text' || document.forms[0].elements[i].type == 'textarea' || document.forms[0].elements[i].type == 'hidden' || document.forms[0].elements[i].type == 'password' || document.forms[0].elements[i].type == 'file') {

				dataString = dataString + document.forms[0].elements[i].value + strDelimiter;
			}
	        }
	        dataString = dataString + recDelimiter + typDelimiter;
        }
	for (i=noFields; i<document.forms[0].elements.length; i++) {
        	if (document.forms[0].elements[i].checked) {
			vCount =1;
			dataString = dataString + document.forms[0].elements[i].value + strDelimiter + recDelimiter;
		}
	}

	dataString = dataString  + typDelimiter;

	if ( vCount == 0 && noFields > 0 && noFields < document.forms[0].elements.length) {
        	alert('You need to check atleast one check box to see the report');
	}
	else {

	       // var theURL = callingProcess +'?pScreenId='+ screenId +'&pEventCode=' + eventCode + '&pBranchId=' + document.forms[1].pBranchId.value + '&pDataString=' + escape(dataString)  +'&pUserId='+pUserId+'&pRoleId='+pRoleId;
		//var theURL = callingProcess +'?pScreenId='+ screenId +'&pEventCode=' + eventCode + '&pBranchId=' + document.forms[1].pBranchId.value + '&pDataString=' + escape(dataString)  +'&pUserId='+pUserId+'&pRoleId='+pRoleId;
		var theURL = callingProcess +'?pScreenId='+ screenId +'&pEventCode=' + eventCode + '&pBranchId=' + document.forms[1].pBranchId.value + '&pDataString=' + escape(dataString)  +'&pUserId='+pUserId+'&pRoleId='+pRoleId;
		//var win = window.open(theURL,winName,'scrollbars=yes,resizable=yes,toolbar=yes,menubar=yes,width=600,height=800');
	 	//win.moveTo(200, 0);
		if (!windowId) {
			var win = window.open(theURL,winName,'scrollbars=yes,resizable=yes,toolbar=yes,menubar=yes,width=600,height=800');
			windowId = win
			win.moveTo(300, 200);
		}
		else
		{
			windowId.close();
			var win = window.open(theURL,winName,'scrollbars=yes,resizable=yes,toolbar=yes,menubar=yes,width=600,height=800');
			windowId = win
			win.moveTo(300, 200);
		}
	}

}

/**********************************************************************************/
/****   This Function opens a child window with a data string.	               ****/
/**********************************************************************************/
function openWindowWithDataString() {
	if (!checkChange()) {
	        var dataString = "";
		var pUserId = document.forms[1].pUserId.value;
		var pRoleId = document.forms[1].pRoleId.value;

	        var args = openWindowWithDataString.arguments;
		var i;

	        var screenId            = args[0];
	        var eventCode           = args[1];
	        var strDelimiter        = args[2];
	        var recDelimiter        = args[3];
	        var typDelimiter        = args[4];
	        var noFields	        = args[5];
	        var callingProcess	= args[6];
	        var winName	        = args[9];

		for (i=0; i<document.forms[0].elements.length; i++) {
			dataString = dataString + document.forms[0].elements[i].value + strDelimiter;
		}

		dataString = dataString + recDelimiter;
		dataString = dataString + args[7] + strDelimiter + args[8] + strDelimiter + recDelimiter + typDelimiter;

		var theURL = callingProcess +'?pScreenId='+ screenId +'&pEventCode=' + eventCode + '&pDataString=' + escape(dataString)  +'&pUserId='+pUserId+'&pRoleId='+pRoleId;

		//var win = window.open(theURL,winName,'scrollbars=yes,resizable=yes,toolbar=no,menubar=no,width=800,height=600');
		//win.moveTo(200,100);
		if (!windowId) {
			var win = window.open(theURL,winName,'scrollbars=yes,resizable=yes,toolbar=no,menubar=no,width=800,height=600');
			windowId = win
			win.moveTo(300, 200);
		}
		else
		{
			windowId.close();
			var win = window.open(theURL,winName,'scrollbars=yes,resizable=yes,toolbar=no,menubar=no,width=800,height=600');
			windowId = win
			win.moveTo(300, 200);
		}
	}
	else {
		alert('Please first save the changes that were made on the screen.');
	}

}

/**********************************************************************************/
/****   This Function opens a child window with a data string.	               ****/
/**********************************************************************************/
function openRiskAttachmentWindow() {
	if (!checkChange()) {
	        var dataString = "";
		var pUserId = document.forms[1].pUserId.value;
		var pRoleId = document.forms[1].pRoleId.value;

	        var args = openRiskAttachmentWindow.arguments;
		var i;

	        var features            = args[0];
	        var screenId            = args[1];
	        var eventCode           = args[2];
	        var strDelimiter        = args[3];
	        var recDelimiter        = args[4];
	        var typDelimiter        = args[5];
	        var noFields	        = args[6];
	        var callingProcess	= args[7];
	        var winName	        = args[10];

		for (i=0; i<noFields; i++) {
			dataString = dataString + document.forms[0].elements[i].value + strDelimiter;
		}

		dataString = dataString + recDelimiter;
		dataString = dataString + args[8] + strDelimiter + args[9] + strDelimiter + recDelimiter + typDelimiter;

		var theURL = callingProcess +'?pScreenId='+ screenId +'&pEventCode=' + eventCode + '&pDataString=' + escape(dataString)  +'&pUserId='+pUserId+'&pRoleId='+pRoleId;

		//var win = window.open(theURL,winName,'scrollbars=yes,resizable=yes,toolbar=no,menubar=no,width=900,height=670');
		//win.moveTo(100,20);
		if (!windowId) {
			var win = window.open(theURL,winName,features);
			windowId = win
			win.moveTo(300, 200);
		}
		else
		{
			windowId.close();
			var win = window.open(theURL,winName,features);
			windowId = win
			win.moveTo(300, 200);
		}
	}
	else {
		alert('Please first save the changes that were made on the screen.');
	}

}



/**********************************************************************************/
/****   This Function forms the string and submits the parent form             ****/
/****	while closing the child window.					       ****/
/**********************************************************************************/
function formStringAndSubmitParent() {

	var args = formStringAndSubmitParent.arguments;
        var strDelimiter        = args[0];
        var recDelimiter        = args[1];
        var typDelimiter        = args[2];
        var screenId	        = args[3];
        var eventCode	        = args[4];
	var dataString 		= args[5];


	for (i=6; i < args.length; i++) {
		dataString = dataString + eval(args[i]).value + strDelimiter;
	}

	dataString = dataString + recDelimiter + typDelimiter;

	eval("window.opener.document." + window.name + "." + "pDataString").value = dataString;
	eval("window.opener.document." + window.name + "." + "pScreenId").value = screenId;
	eval("window.opener.document." + window.name + "." + "pEventCode").value = eventCode;
  	eval("window.opener.document." + window.name + "." + "submit()");

	window.close();


}
/************************ added by Subhasis *******************************/

/**********************************************************************************/
/****        This Function clears the fiels except for the hidden 	       ****/
/****	     fields without submiting.                  		       ****/
/**********************************************************************************/
function fieldClear() {
        var args = fieldClear.arguments;
	var field;
	for( i=0; i<args.length; i++) {
		field = eval(args[i]);
		if (( field.type == 'text' ) || ( field.type == 'textarea' ) || ( field.type == 'hidden' )) {
			field.value = "";
		}
		if ( field.type == 'checkbox' ) {
			field.checked = false;
		}
		if ( field.type == 'radio' ) {
			field.checked = false;
		}
		if ( field.type == 'select-one' && ( field.length != 0 )) {
			field.selected = true;
		}

	}
	return;
}

/**********************************************************************************/
/*****	This function validates and submits the form.                        ******/
/**********************************************************************************/
function checkSequencialAndSubmit() {

	var args = checkSequencialAndSubmit.arguments;
	var argCheckBox	= eval(args[0]);
	var len = argCheckBox.length;
	var submitFlag = false;
	if (typeof(len) == 'undefined') {
		if ( argCheckBox.checked == true ) {
			submitFlag = true;
		}
		else {
			alert('Check Box nust be checked before submiting');
			submitFlag = false;
			return;
		}
	}
	else {
		submitFlag = true;
		for(j=0; j<len; j++) {
			if ( argCheckBox[j].checked == false && argCheckBox[j].disabled == false) {
				for(k=j+1; k<len; k++) {
					if ( argCheckBox[k].checked == true ) {
						alert('The intermediate checkbox(es) must be checked before submiting\n or the last checkbox(es) unchecked.');
						submitFlag = false;
						return;
					}
				}
			}
		}
	}

	if ( submitFlag ) {

		openDocument(args[args.length-8]
			    ,args[args.length-7]
			    ,args[args.length-6]
			    ,args[args.length-5]
			    ,args[args.length-4]
		  	    ,args[args.length-3]
			    ,args[args.length-2]
		    	    ,args[args.length-1]);
	}
}

function gotoEnter() {
	if (event.keyCode == 13) {
		getAgreementNo();
	}

}
/**********************************************************************************/
/*****	This function traps enter button and prevents the submission of form ******/
/**********************************************************************************/
function trapEnter() {
	if (event.keyCode == 13) {
		event.returnValue=false;
	}
}



function validateInteger(item, str) {
	if(!isPositiveInteger(item.value)){

		alert(str+' should be a Number.');
		item.focus();
		item.select();
		return false;
	} else if (item.value > 999999999999 || item.value < 0){
		alert (str + ' should be less than 999999999999.99\nor greater than 0.00');
		item.focus();
		item.select();
		return false;
	} else {
		return true;
	}
}

/***************************************************************************************/
/********This function checks whether a date is less than equal SYSDATE*****************/
/***************************************************************************************/

function isPastDate(date) {
	if(!isDate(date.value)){

		   date.select();
		   date.focus();
		   return false;
		} else {

		   var f1 = date.value.indexOf("/");
	     	   var f2 = date.value.indexOf("/", f1+1);

		   var mon = date.value.substring(0, f1);
		   var day = date.value.substring(f1+1, f2);
		   var year = date.value.substring(f2+1, date.value.length);

		   if(parseFloat(year) < 1000 )	year = 2000 + parseFloat(year);

		   var toDay = new Date();

		   var vDay  = new Date(year, parseFloat(mon)-1, parseFloat(day));
		   if ( vDay > toDay) {

			date.select();
			date.focus();
			return false;

		   }else {

		   	if ((parseFloat(day) < 10 )&&(day.length == 1)) day = '0'+day;
		   	if ((parseFloat(mon) < 10 )&&(mon.length == 1)) mon = '0'+mon;
		   	date.value = mon +"/"+ day + "/"  + year;

		   	return true;
		   }
		}
}

/***************************************************************************************/
/********This function checks whether a date is less than equal servers SYSDATE*****************/
/***************************************************************************************/

function isVoucherDate(date) {
	if(!isDate(date.value)){

		   date.select();
		   date.focus();
		   return false;
		} else {

		/**To be shifted down**
		   var f1 = date.value.indexOf("/");
	     	   var f2 = date.value.indexOf("/", f1+1);
		   var day = date.value.substring(f1+1, f2);
		   var mon = date.value.substring(0, f1);
		   var year = date.value.substring(f2+1, date.value.length);

		   if(parseFloat(year) < 1000 )	year = 2000 + parseFloat(year);
		   var vDay  = new Date(year, parseFloat(mon)-1, parseFloat(day));
		***********************/

		   var str = document.forms[0].pSystemDate.value;
		   f1 = str.indexOf("/");
	     	   f2 = str.indexOf("/", f1+1);
		   day = str.substring(f1+1, f2);
		   mon = str.substring(0, f1);
		   year = str.substring(f2+1, date.value.length);

		   if(parseFloat(year) < 1000 )	year = 2000 + parseFloat(year);
		   toDay  = new Date(year, parseFloat(mon)-1, parseFloat(day));

		   var f1 = date.value.indexOf("/");
	     	   var f2 = date.value.indexOf("/", f1+1);
		   var mon = date.value.substring(0, f1);
		   var day = date.value.substring(f1+1, f2);
		   var year = date.value.substring(f2+1, date.value.length);

		   if(parseFloat(year) < 1000 )	year = 2000 + parseFloat(year);
		   var vDay  = new Date(year, parseFloat(mon)-1, parseFloat(day));

		   if ( vDay > toDay) {

			date.select();
			date.focus();
			return false;

		   }else {

		   	if ((parseFloat(day) < 10 )&&(day.length == 1)) day = '0'+day;
		   	if ((parseFloat(mon) < 10 )&&(mon.length == 1)) mon = '0'+mon;
		   	date.value =   mon + "/" + day + "/" +  year;

		   	return true;
		   }
		}
}


/**********************************************************************************/
/*****	This function puts the parameters for the selected row		     ******/
/*****	to the corresponding placeholders in the screen.		     ******/
/**********************************************************************************/
function selectFieldsAcc(str, fieldSep) {

  	var replacePosition;
  	while(1) {
      		replacePosition =   str.indexOf("¤");
      		if (replacePosition==-1) break;
      		str = str.substring(0,replacePosition)+"'"+str.substring(replacePosition+1, str.length);
  	}

  	var windowName = window.name;
  	var indxLast = windowName.lastIndexOf("XYZ");
  	var sourceForm = windowName.substring(indxLast+3, windowName.length);

        var indxVal = new Array();
  	var textValue = new Array();
  	var indx = new Array();
  	var textField = new Array();
  	var strText = new Array();
  	var strItem;

  	indxVal[0] = str.indexOf(fieldSep);
  	textValue[0] = str.substring(0, indxVal[0]);
  	indx[0] = windowName.indexOf("XYZ");
  	textField[0] = windowName.substring(0, indx[0]);

  	if ( indx[0] < indxLast ) {

     		for (i=0; (indx[i] < indxLast); i++) {

         		indxVal[i+1] = str.indexOf(fieldSep, indxVal[i]+1);

         		if ( indxVal[i+1] > (indxVal[i]+1) ) {
            			textValue[i+1] = str.substring(indxVal[i]+1, indxVal[i+1]);
         		}
         		else {
      	    			textValue[i+1] = '';
         		}

         		indx[i+1] = windowName.indexOf("XYZ", indx[i]+1);
         		textField[i+1] = windowName.substring(indx[i]+3, indx[i+1]);
	 		strText[i+1] = "window.opener.opener.document." + sourceForm + "." + textField[i+1];
	 		strItem = eval(strText[i+1]);
     	 		strItem.value = textValue[i+1];

     		}

	        textValue[i] = str.substring(indxVal[i-1]+1, str.length);
	        strText[i] = "window.opener.opener.document." + sourceForm + "." + textField[i];
	        strItem = eval(strText[i]);
	        strItem.value = textValue[i];

  	}
  	else if ( indx[0] == indxLast ) {

		textValue[0] = str;

  	}

  	strText[0] = "window.opener.opener.document." + sourceForm + "." + textField[0];
  	strItem = eval(strText[0]);
  	strItem.value = textValue[0];

  	window.close();
  	window.opener.close();

}
/*********************************************************************************************************/
/*	Chnaged in document manager*/
/*********************************************************************************************************/
/**********************************************************************************/
/*****	This function puts the parameters for the selected row		     ******/
/*****	to the corresponding placeholders in the screen.		     ******/
/**********************************************************************************/
function selectDocumentFields(str, fieldSep) {

  	var replacePosition;
  	while(1) {
      		replacePosition =   str.indexOf("‡");
      		if (replacePosition==-1) break;
      		str = str.substring(0,replacePosition)+"'"+str.substring(replacePosition+1, str.length);
  	}

  	var windowName = window.name;
  	var indxLast = windowName.lastIndexOf("XYZ");
  	var sourceForm = windowName.substring(indxLast+3, windowName.length);

        var indxVal = new Array();
  	var textValue = new Array();
  	var indx = new Array();
  	var textField = new Array();
  	var strText = new Array();
  	var strItem;
	var stTmpltInd = eval("window.opener.document." + sourceForm + ".TemplateRqrdInd");
  	indxVal[0] = str.indexOf(fieldSep);
  	textValue[0] = str.substring(0, indxVal[0]);
  	indx[0] = windowName.indexOf("XYZ");
  	textField[0] = windowName.substring(0, indx[0]);

  	if ( indx[0] < indxLast ) {

     		for (i=0; (indx[i] < indxLast); i++) {

         		indxVal[i+1] = str.indexOf(fieldSep, indxVal[i]+1);

         		if ( indxVal[i+1] > (indxVal[i]+1) ) {
            			textValue[i+1] = str.substring(indxVal[i]+1, indxVal[i+1]);
         		}
         		else {
      	    			textValue[i+1] = '';
         		}

         		indx[i+1] = windowName.indexOf("XYZ", indx[i]+1);
         		textField[i+1] = windowName.substring(indx[i]+3, indx[i+1]);
	 		strText[i+1] = "window.opener.document." + sourceForm + "." + textField[i+1];
	 		strItem = eval(strText[i+1]);
	 		if (strItem.type == 'select-one') {
			        for (var j=0; j<strItem.options.length; j++) {
			        	if (strItem.options[j].value == textValue[i+1]) {
			        		if ( textValue[i+1] == '4005') {
			        			stTmpltInd.value = 'Y';
			        		}
			        		else {
			        			stTmpltInd.value = 'N';
						}
			                	strItem.options[j].selected = true;
			                }
			        }

			}
			else {
     	 			strItem.value = textValue[i+1];
			}
     		}

	        textValue[i] = str.substring(indxVal[i-1]+1, str.length);
	        strText[i] = "window.opener.document." + sourceForm + "." + textField[i];
	        strItem = eval(strText[i]);

 		if (strItem.type == 'select-one') {
		        for (var j=0; j<strItem.options.length; j++) {
		        	if (strItem.options[j].value == textValue[i]) {
		        		if ( textValue[i] == '4005') {
		        			stTmpltInd.value = 'Y';
		        		}
		        		else {
		        			stTmpltInd.value = 'N';
					}
		                	strItem.options[j].selected = true;
		                }
		        }

		}
		else {
 			strItem.value = textValue[i];
		}

  	}
  	else if ( indx[0] == indxLast ) {

		textValue[0] = str;

  	}

  	strText[0] = "window.opener.document." + sourceForm + "." + textField[0];
  	strItem = eval(strText[0]);
  	strItem.value = textValue[0];

  	window.close();

}

/**********************************************************************************/
/*****	This function checks whether changes have taken place in the 	     ******/
/*****  screen, validates the changes and then submits the form.             ******/
/**********************************************************************************/
function chkChangePwdValidateSubmit() {

	//Validate and Submit after checking whether changes were made on the screen.

	if (checkChange()) {

		var args = chkChangePwdValidateSubmit.arguments;
		var strDelimiter = args[args.length-5];
		var recDelimiter = args[args.length-4];
		var typDelimiter = args[args.length-3];
		var callingProcess = args[args.length-2];
		var dataString = '';
		var argList = "'" + args[0] + "'";

		for ( i=1; i < (args.length-7); i++ ) {
			argList = argList + "," + "'" + args[i] + "'";
		}
		if ( eval("validateScreen(" + argList + ")") ) {
			if (document.forms[0].pUserPassword.defaultValue == document.forms[0].pUserPassword.value)
			{
				setValuesAndSubmit(args[args.length-7]
						  ,args[args.length-6]
					  	  ,args[args.length-5]
						  ,args[args.length-4]
						  ,args[args.length-3]
						  ,args[args.length-1]);
			}
			else {
				dataString = dataString + document.forms[0].pRoleId.value + strDelimiter;
				dataString = dataString + document.forms[0].pPartyId.value + strDelimiter;
				dataString = dataString + document.forms[0].pPartyFunctionId.value + strDelimiter;
				dataString = dataString + document.forms[0].pUserRoleId.value + strDelimiter;
				dataString = dataString + document.forms[0].pLastUpdatedDate.value + strDelimiter;
				dataString = dataString + document.forms[0].pUserCode.value + strDelimiter;
				dataString = dataString + document.forms[0].pUserFirstName.value + strDelimiter;
				dataString = dataString + document.forms[0].pUserSurName.value + strDelimiter;
				dataString = dataString + document.forms[0].pUserPassword.value + strDelimiter;
				dataString = dataString + document.forms[0].pRoleCode.value + strDelimiter;
				dataString = dataString + document.forms[0].pRoleName.value + strDelimiter;
				dataString = dataString + document.forms[0].pEffectiveStartDate.value + strDelimiter;
				dataString = dataString + document.forms[0].pEffectiveEndDate.value + strDelimiter + recDelimiter + typDelimiter;

				var theURL = callingProcess +'?pScreenId='+ args[args.length-7] +'&pEventCode=' + 'checkPassword' + '&pDataString=' + escape(dataString);

				var win = window.open(theURL,'processUserDetail','status=no,resize=no,toolbar=no,scrollbars=no,resizable=yes,menubar=no,width=400,height=110');
				win.moveTo(400,400);
			}
		}

	}
	else {
		alert('\n\nNo field updated.\n\n');
	}
}

/**********************************************************************************/
/*****	This function checks whether changes have taken place in the 	     ******/
/*****  screen, validates the changes and then submits the form.             ******/
/**********************************************************************************/
function ConfirmPwdAndSubmitParent() {
	if (document.forms[0].pConFirmPassword.value != '' )
	{
		if (document.forms[0].pPassword.value == document.forms[0].pConFirmPassword.value)
		{
			eval("window.opener.document." + window.name + "." + "pDataString").value = document.forms[0].pDataString.value;
			eval("window.opener.document." + window.name + "." + "pScreenId").value = document.forms[0].pScreenId.value;
			eval("window.opener.document." + window.name + "." + "pEventCode").value = document.forms[0].pEventCode.value;
		  	eval("window.opener.document." + window.name + "." + "submit()");
			window.close();
		}
		else
		{
			alert('Password doesnot match your original Password');
		}
	}
	else {
		alert('You need to retype the changed password');
	}
}

function openScheduleWindow() {
        var dataString = "";

        var args = openScheduleWindow.arguments;
	var vCount =0;
	var i;

        var screenId            = args[0];
        var eventCode           = args[1];
        var strDelimiter        = args[2];
        var recDelimiter        = args[3];
        var typDelimiter        = args[4];
        var callingProcess	= args[5];
        var winName	        = 'Declaration_Statement';
	for (var i=0; i<document.forms[0].elements.length; i++) {
		if (document.forms[0].elements[i].type == 'checkbox') {
			if (document.forms[0].elements[i].checked) {
				dataString = dataString + document.forms[0].elements[i].value + strDelimiter;
			}
		}
		else if (document.forms[0].elements[i].type == 'radio') {
			if (document.forms[0].elements[i].checked) {
		                dataString = dataString + document.forms[0].elements[i].value + strDelimiter;
			}
	        }
		else if (document.forms[0].elements[i].type == 'select-one') {
			var sel = "";

		        for (var j=0; j<document.forms[0].elements[i].options.length; j++) {
		        	if (document.forms[0].elements[i].options[j].selected) {
		                	sel = document.forms[0].elements[i].options[j].text;
		                }
		        }
		        dataString = dataString + sel + strDelimiter;
		}
		else if (document.forms[0].elements[i].type == 'text' || document.forms[0].elements[i].type == 'textarea' || document.forms[0].elements[i].type == 'hidden' || document.forms[0].elements[i].type == 'password' || document.forms[0].elements[i].type == 'file') {

			dataString = dataString + document.forms[0].elements[i].value + strDelimiter;
		}
	}

	dataString = dataString  + recDelimiter + typDelimiter;
        var theURL = callingProcess +'?pScreenId='+ screenId +'&pEventCode=' + eventCode + '&pDataString=' + escape(dataString);
	var win = window.open(theURL,winName,'scrollbars=yes,resizable=yes,toolbar=yes,menubar=yes,width=800,height=500');
  	win.moveTo(200, 0);
}
/**********************************************************************************/
/*****	This function checks the number of windows which are open and closes it****/
/**********************************************************************************/
function exitOpenWindows() {
	if (windowId) {
		if (!(windowId.closed)) {
			windowId.close();
		}
	}
	if (calendar) {
		if (!(calendar.closed)) {
			calendar.close();
		}
	}
}

/**********************************************************************************/
/*****	This function validates and submits the form.                        ******/
/**********************************************************************************/
function validateLoginSubmit() {

	//Validate and Submit without checking change.
	var args = validateLoginSubmit.arguments;
	var argList = "'" + args[0] + "'";

	for ( i=1; i < (args.length-5); i++ ) {
		argList = argList + "," + "'" + args[i] + "'";
	}

	if ( eval("validateScreen(" + argList + ")") ) {
		setLoginSubmit(	   parseInt(parent.parent.Control.document.maxlogon.noOfAttempts.value)
				  ,args[args.length-6]
				  ,args[args.length-5]
				  ,args[args.length-4]
				  ,args[args.length-3]
				  ,args[args.length-2]
				  ,args[args.length-1]);
	}
}

/**********************************************************************************/
/*****	This function forms the string and submits the second form.          ******/
/**********************************************************************************/
function setLoginSubmit() {

	exitOpenWindows();
	// Setting the values and submitting the second form.

	var dataString = "";
	var args = setLoginSubmit.arguments;

	var noOfAttempts        = args[args.length-7];
	var screenId            = args[args.length-6];
	var eventCode           = args[args.length-5];
	var strDelimiter        = args[args.length-4];
	var recDelimiter        = args[args.length-3];
	var typDelimiter        = args[args.length-2];
	var noFields	        = args[args.length-1];

	for (var i=0; i<document.forms[0].elements.length; i++) {

		if ( i == noFields ) {
                        dataString = dataString + recDelimiter + typDelimiter;

                }

		if (document.forms[0].elements[i].type == 'checkbox') {
			if (document.forms[0].elements[i].checked) {
				dataString = dataString + document.forms[0].elements[i].value + strDelimiter;
			}
		}
		else if (document.forms[0].elements[i].type == 'radio') {
			if (document.forms[0].elements[i].checked) {
		                dataString = dataString + document.forms[0].elements[i].value + strDelimiter;
			}
	        }
		else if (document.forms[0].elements[i].type == 'select-one') {
			var sel = "";

		        for (var j=0; j<document.forms[0].elements[i].options.length; j++) {
		        	if (document.forms[0].elements[i].options[j].selected) {
		                	sel = document.forms[0].elements[i].options[j].value;
		                }
		        }
		        dataString = dataString + sel + strDelimiter;
		}
		else if (document.forms[0].elements[i].type == 'text' || document.forms[0].elements[i].type == 'textarea' || document.forms[0].elements[i].type == 'hidden' || document.forms[0].elements[i].type == 'password' || document.forms[0].elements[i].type == 'file') {
                        document.forms[0].elements[i].value = trim(document.forms[0].elements[i].value);
			dataString = dataString + document.forms[0].elements[i].value + strDelimiter;
		}

	}

	document.forms[1].pScreenId.value = screenId;
	document.forms[1].pEventCode.value = eventCode;
  document.forms[1].pDataString.value = dataString + noOfAttempts + strDelimiter + recDelimiter + typDelimiter;


	document.forms[1].submit();

}
function statusMessageWithErrChk_1(str, userName) {
	var vStr, f1;
	var userCode = userName;
	var noOfAttempts;

	if (userCode == parent.parent.Control.document.maxlogon.userCode.value )
	{
		   parent.parent.Control.document.maxlogon.noOfAttempts.value = parseInt(parent.parent.Control.document.maxlogon.noOfAttempts.value) + 1;

	}else {

		parent.parent.Control.document.maxlogon.userCode.value = userName;
		parent.parent.Control.document.maxlogon.noOfAttempts.value = 1;
	}

	if (parseInt(parent.parent.Control.document.maxlogon.noOfAttempts.value) > 3)
	   {
		document.write('<br><br>');
		document.write('<br><br>');
		document.write('<div align="center"><br><br><b><font face = verdana color=red>'+userName+' is locked. Please contact system administrator.</font></b></div>');
	   }
	   else
	   {
		if (str  != "")
		{
				if ((str.substring(0,1))=="1")
				{
					status = str.substring(2);
				}
				else if((str.substring(0,1))=="0")
				{
					f1 = str.indexOf('******');
					vCode = str.substring(2, f1);
					vMsg = str.substring(f1+6);

					if ((vCode=="") || (vCode != 0))
					{
						window.history.back();
					}
					showError(vCode, vMsg);
				}
				else if((str.substring(0,1))=="3")
				{
					f1 = str.length;
					vMsg = str.substring(2);
					showMessage(1, vMsg);
					window.history.back();
				}
				else
				{
					f1 = str.indexOf('******');
					vCode = str.substring(2, f1);
					vMsg = str.substring(f1+6);
					showMessage(vCode, vMsg);
				}
		}else
		{
			status = '';
		}
   }

}
function checkReferenceTypeForLOV()
{
	if((document.forms[0].pReferenceType.value == '5')){
		alert('Please select the Reference Type');
		return false;
	}

	else{
		return true;
	}
}

function disableEnablePersonal(frm) {
	var opt;
  	if((document.forms[0].pReferenceType.value == '5' || document.forms[0].pReferenceType.value == '0'))
  	{
      		event.returnValue = false;
	}
}

function LOVreferenceTypeForDiary(callingProcess1, callingProcess2, screenId1, screenId2, eventCode2, fieldSep, recSep, typSep, winName1, winName2, features)
{
		if((document.forms[0].pReferenceType.value == '5' || document.forms[0].pReferenceType.value == '0')){
			alert('Please select the Reference Type');
		}
		else if(document.forms[0].pReferenceType.value == '4')//Party Find
		{
			var dataString = '';

			var pUserId = document.forms[1].pUserId.value;
			var pRoleId = document.forms[1].pRoleId.value;

			for (i=12; i < LOVreferenceTypeForDiary.arguments.length; i++) {
					if (typeof(LOVreferenceTypeForDiary.arguments[i]) == 'string') {
						dataString = dataString + LOVreferenceTypeForDiary.arguments[i];
					}
					else {
						dataString = dataString + eval(LOVreferenceTypeForDiary.arguments[i]).value;
					}
					dataString = dataString + fieldSep;
			}
				dataString = dataString + recSep + typSep;
					var theURL = callingProcess2 +'?pScreenId='+ screenId2 +'&pEventCode=' + eventCode2 + '&pDataString=' + escape(dataString) +'&pUserId='+pUserId+'&pRoleId='+pRoleId;
				if (!windowId) {
					var win = window.open(theURL,winName2,features);
					windowId = win
					win.moveTo(100, 100);
				}
				else
				{
					windowId.close();
					var win = window.open(theURL,winName2,features);
					windowId = win
					win.moveTo(100, 100);
				}
		}
		else	//Policy/Claim Find
		{
			var dataString = '';
			var pUserId = document.forms[1].pUserId.value;
			var pRoleId = document.forms[1].pRoleId.value;
			for (i=11; i < LOVreferenceTypeForDiary.arguments.length; i++) {

				if (typeof(LOVreferenceTypeForDiary.arguments[i]) == 'string') {
					dataString = dataString + LOVreferenceTypeForDiary.arguments[i];
				}
				else {
					dataString = dataString + eval(LOVreferenceTypeForDiary.arguments[i]).value;
				}
				dataString = dataString + fieldSep;
				}
				dataString = dataString + recSep + typSep;
				var theURL = callingProcess1 +'?pScreenId='+ screenId1 +'&pEventCode=Find&pDataString=' + escape(dataString)+'&pUserId='+pUserId+'&pRoleId='+pRoleId;
				if (!windowId) {
					var win = window.open(theURL,winName1,features);
					windowId = win
					win.moveTo(300, 200);
				}
				else
				{
					windowId.close();
					var win = window.open(theURL,winName1,features);
					windowId = win
					win.moveTo(300, 200);
				}
		}
}

/************************************************************************************/
/** This function places the focus on the first enabled form element ofthe page ****/
/************************************************************************************/

function firstFieldFocus()
{
	var isFocused = false;
    if( document.forms[0] != null )
    {
		for(i=0;i<document.forms[0].elements.length;i++)
		{
			if ((document.forms[0].elements[i].type == 'select-one') && (document.forms[0].elements[i].length != 0 )&& ( document.forms[0].elements[i].disabled== false))
			{
				document.forms[0].elements[i].focus();
				isFocused = true;
				break;
			}
			else if ((document.forms[0].elements[i].type == 'checkbox' )&& ( document.forms[0].elements[i].disabled== false))
			{
				document.forms[0].elements[i].focus();
				isFocused = true;
				break;
			}
			else if ((document.forms[0].elements[i].type == 'radio' )&& ( document.forms[0].elements[i].disabled== false))
			{
				document.forms[0].elements[i].focus();
				isFocused = true;
				break;
			}
			else if ((document.forms[0].elements[i].type != 'hidden' ) && (document.forms[0].elements[i].type != 'checkbox' ) && (document.forms[0].elements[i].type != 'select-one') && (document.forms[0].elements[i].type != 'radio') && (document.forms[0].elements[i].readOnly == false))
			{
				document.forms[0].elements[i].focus();
				isFocused = true;
			  	break;
			}
		}
    }
	if ((!isFocused) && (document.links.length > 0))
	{
		for(i=0; i<	document.links.length; i++)
		{
			if (document.links[i].name=="firstFocus")
			{
				isFocused = true;
				document.links[i].focus();
				break;
			}
		}
		if (!isFocused)
			document.links[0].focus();
	}
}

/**********************************************************************************/
/****	This function validate the time in xx.x format.    		       ****/
/****	It take the field name as input parameter.            		       ****/
/**********************************************************************************/
function validateTime(time) 
{
	if((time.value=="")||(time.value == time.defaultValue)) return true;

	if(!isOnePlaceAfterDec(time.value)){
	   alert("Invalid Time\nshould be a positive number in xx.x format");
	   time.select();
	   time.focus();
	   return false;
	}else{
		return true;
	}
}

function isOnePlaceAfterDec(str)
{
	if(!isDecimal(str)) return false;
	if(parseFloat(str)> 99.9 || parseFloat(str)<0) return false ;
	var indx1 = str.indexOf(".", 0);
	
	if(indx1==-1){
		if(str.length>1) return false;
		else return true ;
	}
	else
	{
		var lastpart = str.substring(indx1+1,str.length);

		if(lastpart.length >1)
		{
			return false;
		}
		else return true ;
	}

}

/**********************************************************************************/
/*****	This function is called onblur event of TEXT AREA fields          ******/
/**********************************************************************************/


function chkLength(ctrl,  size)
{
var prmData = ctrl.value;
var  prmAllowedSize = parseInt(size);

if (prmData.length >  prmAllowedSize)
{
alert("Data length cannot  be greater than " +  size);
ctrl.focus();
}
}


/**********************************************************************************/
/*****	This function is called onblur event of NUMBER(15,2) fields          ******/
/**********************************************************************************/

function decimalCheck152(this1){
	var errString ="The following error(s) has occurred :-\n\n";
	var isError = false;
	if (decimalLengthCheck152(this1.value) == 1) {
		errString += "  - " + " Number entered should have only two digits after decimal.\n";
		isError = true;
	}
	if (decimalLengthCheck152(this1.value) == 2) {
		errString += "  - " + " Number entered should have only thirteen digits before decimal.\n";
		isError = true;
	}
	if (decimalLengthCheck152(this1.value) == 3) {
		errString += "  - " + " A valid number should be entered.\n";
		isError = true;
	}	
	if (isError)
	{
		alert(errString);
		this1.focus();
		this1.select();
	}
}
/**********************************************************************************/
/*****	This function is called onblur event of NUMBER(5,2) fields           ******/
/**********************************************************************************/

function decimalCheck52(this1){
	var errString ="The following error(s) has occurred :-\n\n";
	var isError = false;
	if (decimalLengthCheck52(this1.value) == 1) {
		errString += "  - " + " Number entered should have only two digits after decimal.\n";
		isError = true;
	}
	if (decimalLengthCheck52(this1.value) == 2) {
		errString += "  - " + " Number entered should have only two digits before decimal.\n";
		isError = true;
	}
	if (decimalLengthCheck52(this1.value) == 3) {
		errString += "  - " + " A valid number should be entered.\n";
		isError = true;
	}	
	if (isError)
	{
		alert(errString);
		this1.focus();
		this1.select();
	}
}

function decimalLengthCheck52(input)
{
//alert("AVIJIt"+input);
	if(isNaN(input)) {
		return 3; //should be a number
	}else {
		for(var intCounter=0;intCounter<input.length;intCounter++)
		{
			if(input.substring(intCounter,intCounter+1) == ".")
			{
				var intDiff=(input.length-(intCounter+1));
				if (intDiff>2)
				{
					return 1;
				}
				if (intCounter>5)
				{
					return 2;
				}
			}
		}
		if ( input > 100)
			return 2;
	}
	return 0;
}

function decimalLengthCheck152(input)
{
	if(isNaN(input)){
		return 3; //should be a number
	}
	else {
		for(var intCounter=0;intCounter<input.length;intCounter++)
		{
			if(input.substring(intCounter,intCounter+1) == ".")
			{
				var intDiff=(input.length-(intCounter+1));
				if (intDiff>2)
				{
					return 1;
				}
				if (intCounter>13)
				{
					return 2;
				}
			}
		}
		if ( input >= 10000000000000)
			return 2;
	}
	return 0;
}