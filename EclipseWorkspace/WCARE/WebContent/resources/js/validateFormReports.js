
function validateForm() {
  //data format = array containing 'fieldName', 'data', 'mandatoryness', 'dataType'

  var errString="The following error(s) has occurred :-\n\n";

  var args = validateForm.arguments;
  for (var i=0; i<(args.length); i+=4) {
  	//alert ("label:"+args[i] + " value:" + args[i+1] + " mandatoryness:" + args[i+2] + " dataType:" + args[i+3]);

	if(args[i + 2]=='M') {
		if (args[i+1]=="") {
		   errString += "  - " + args[i] + " is mandatory.\n";
		} else {
		   if(args[i+3]=='D') {
			if (!isDate(args[i+1])) {
		   	  errString += "  - " + args[i] + " should be a date field of the format dd.mm.yy\n";
			}
		   } else if(args[i+3]=='I') {
			if (!isNumber(args[i+1])) {
		   	  errString += "  - " + args[i] + " should be an number value.\n";
			}
		   } else if(args[i+3]=='M') {
			if (!isDecimal(args[i+1])) {
		   	  errString += "  - " + args[i] + " should be a decimal field.\n";
			}'O'
		   } else if(args[i+3]=='P') {
			if (!isPhone(args[i+1])) {
		   	  errString += "  - " + args[i] + " should be a phone no.\n";
			}
		   }else if(args[i+3]=='Q') {
			if (!isSSN(args[i+1])) {
		   	  errString += "  - " + args[i] + "  should be a valid SSN.\n";
			}
		   }else if(args[i+3]=='E') {
			if (!isEmail(args[i+1])) {
		   	  errString += "  - " + args[i] + " should be an email address.\n";
			}
		   } else if(args[i+3]=='T') {
			if (!isTime(args[i+1])) {
		   	  errString += "  - " + args[i] + "- Valid values allowed are 00:00 thru 23:59.\n";
			}
		   }
		}
	}

	else {
		if (args[i+1]!="") {
		   if(args[i+3]=='D') {
			if (!isDate(args[i+1])) {
		   	  errString += "  - " + args[i] + " should be a date field.\n";
			}
		   } else if(args[i+3]=='I') {
			if (!isNumber(args[i+1])) {
		   	  errString += "  - " + args[i] + " should be an number value.\n";
			}
		   } else if(args[i+3]=='M') {
			if (!isDecimal(args[i+1])) {
		   	  errString += "  - " + args[i] + " should be a decimal field.\n";
			}
		   } else if(args[i+3]=='P') {
			if (!isPhone(args[i+1])) {
		   	  errString += "  - " + args[i] + " should be a phone no.\n";
			}
		   } else if(args[i+3]=='E') {
			if (!isEmail(args[i+1])) {
		   	  errString += "  - " + args[i] + " should be an email address.\n";
			}
		   } else if(args[i+3]=='T') {
			if (!isTime(args[i+1])) {
		   	  errString += "  - " + args[i] + "-Valid values allowed are 00:00 thru 23:59.\n";
			}
		   } else if(args[i+3]=='DC') {
		   	if (!DateComparison(args[(i-(2*4))+1],args[(i-(1*4))+1])) {
		   	  errString += "  - " + args[(i-(2*4))] + " " + "should be less than" +" " +  args[(i-(1*4))] + "\n";
			}
		   }
	}
  }
}
  if (errString=='The following error(s) has occurred :-\n\n') {
     	return true;
  } else {
  	alert (errString);
	return false;
  }
}







/***************######################################*******************/
/***************######################################*******************/
/***************######################################*******************/
/***************######################################*******************/
/***************######################################*******************/
/***************######################################*******************/
/**********************************************************************************/
/****        Tihs Function clear the form without submiting.                   ****/
/****        Input parameter is HTML form name.                                ****/
/**********************************************************************************/
function formClear(frm) {
	vCount = frm.elements.length;

	for( i=0; i<vCount; i++) {
		if (( frm.elements[i].type == 'text') || ( frm.elements[i].type == 'textarea')) {
			frm.elements[i].value = "";
		}
		if ( frm.elements[i].type == 'checkbox') {
			frm.elements[i].checked = false;
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
/****	This function check whether a string is Integer number or not.         ****/
/****	It take string as input parameter and return boolean as output.        ****/
/**********************************************************************************/
function isNumber(str) {
	var isnum=true;
	if ((str==null) || (str=="")){
		isnum=true;
		return isnum;
   	}

	for(j=0;j<str.length;j++){

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
/****	This function validate the birth date. The Valid Birth Date			   ****/
/****	should not be less than 150 years from today's  date.                  ****/
/****	It takes two input parameter birth date and sysdate and return		   ****/
/****	output as boolean.	Here bDate is an object and sysDate is an Date	   ****/
/**********************************************************************************/


function validateBirthDate(bDate,sysDate)
{

	var year1,year2
	var month1, month2
	var day1, day2

	if (bDate.value == "") return true;
	if (validateDate(bDate))
	{
		bDate = bDate.value;
		year1 = bDate.substring(6,10)
		month1 = bDate.substring(0,2)
		day1 = bDate.substring(3,5)

		year2 = sysDate.substring(6,10)
		year2 = year2 - 150;
		month2 = sysDate.substring(0,2)
		day2 = sysDate.substring(3,5)

		if (year1+month1+day1 >= year2+month2+day2)
		{
			return true;
		}
		else
		{	alert("Birth Date should not be less than \n150 years from todays date.");
			return false;
		}
	}
}



/**********************************************************************************/
/****	This function validate the input in a field is a valid date or not.    ****/
/****	It take the field name and field prompt as input parameter.            ****/
/**********************************************************************************/
function validateDate(date) {

   	if((date.value=="")||(date.value == date.defaultValue)) return true;

	if(!isDate(date.value)){
	   alert("Invalid Date\nFormat : dd.mm.yyyy");
	   date.select();
	   date.focus();
	   return false;
	} else {

	   var f1 = date.value.indexOf(".");
     	   var f2 = date.value.indexOf(".", f1+1);

	   var mon = date.value.substring(0, f1);
	   var day = date.value.substring(f1+1, f2);
	   var year = date.value.substring(f2+1, date.value.length);

	   if(parseFloat(year) < 1000 )	year = 2000 + parseFloat(year);

	   	if ((parseFloat(day) < 10 )&&(day.length == 1)) day = '0'+day;
	   	if ((parseFloat(mon) < 10 )&&(mon.length == 1)) mon = '0'+mon;
	   	date.value = mon + "." + day + "." + year;

	   	return true;
	}
}


/**********************************************************************************/
/****	This function check whether a string has date format or not.           ****/
/****	It take string as input parameter and return boolean as output.        ****/
/**********************************************************************************/
function isDate(str){
	var f1 = str.indexOf(".");
	var f2=-1;
	if(f1!=-1){
		f2 = str.indexOf(".", f1+1);
	}

	if(f1==-1||f2==-1||f1==0){
		return false;
	}

   var str1 = str.substring(0, f1);
   if((!isNumber(str1)) || (str1>12||str1<=0)){return false;}

   else {
	var str2 = (str.substring(f1+1, f2));
	var dd = parseFloat(str2);
	if(dd > 31 || dd <= 0){
		return false;
	}

	else{
		var str3 = str.substring(f2+1, str.length);
		if(!isNumber(str3) || str3=="" || str3==null||parseFloat(str3)>9999) return false;

	}

   }

   if(!isValidDate(str)){
	return false;
   }

   return true;
}

function DateComparison(dateFrom, dateTo)
{

var year1,year2,curyear
var month1, month2,curmonth
var day1, day2,curday

year1 = dateFrom.substring(6,10)
year2 = dateTo.substring(6,10)
month1 = dateFrom.substring(0,2)
month2 = dateTo.substring(0,2)
day1 = dateFrom.substring(3,5)
day2 = dateTo.substring(3,5)
if (year1+month1+day1 > year2+month2+day2)
{
  	return false
}
else
{
	return true;
}
}


/**********************************************************************************/
/*****	This function check an input string is valid date or not.            ******/
/*****	It is Used to validate leap year.                                    ******/
/*****	It is Used by isDate function only.                                  ******/
/**********************************************************************************/
function isValidDate(str){
   var f1 = str.indexOf(".");
   var f2 = str.indexOf(".", f1+1);
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
/*****	This function check an input string is valid email id or not.            ******/
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


/*

function isPhone(str){

  var flag = true;

   var indx1 = str.indexOf("-", 0);
   var indx2 = str.indexOf("-", indx1+1);
   var indx3 = str.indexOf("E", indx2+1);
   var indx4 = str.indexOf("-", indx2+1);

   if((indx1!=3) || (indx2!=7) || (indx4!=-1))
   {
   	flag = false;

   	return flag;
   }
   if(str.length<15 || str.length>20 || str.length==16)
   {
   	flag = false;

   	return flag;
   }
   else if(str.length==15)
   {
   	for(var i=0; i<str.length; i++)
   	{
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
	   	{
	   		flag = false;

	   		return flag;
	   	}
   	}
   }
   else if(str.length>16 && str.length<=20)
   {
   	if(indx3!=15)
   	{
   		flag = false;

   		return flag;
   	}
   	if(indx3==15)
   	{

		for(var j=0; j<15; j++)
		{
			if(str.substring(j, j+1)!="0" &&
			   str.substring(j, j+1)!="1" &&
			   str.substring(j, j+1)!="2" &&
			   str.substring(j, j+1)!="3" &&
			   str.substring(j, j+1)!="4" &&
			   str.substring(j, j+1)!="5" &&
			   str.substring(j, j+1)!="6" &&
			   str.substring(j, j+1)!="7" &&
			   str.substring(j, j+1)!="8" &&
			   str.substring(j, j+1)!="9" &&
			   str.substring(j, j+1)!="-" )
			{
				flag = false;

				return flag;
			}
		}
		for(var k=16; k<str.length; k++)
		{
			if(str.substring(k, k+1)!="0" &&
			   str.substring(k, k+1)!="1" &&
			   str.substring(k, k+1)!="2" &&
			   str.substring(k, k+1)!="3" &&
			   str.substring(k, k+1)!="4" &&
			   str.substring(k, k+1)!="5" &&
			   str.substring(k, k+1)!="6" &&
			   str.substring(k, k+1)!="7" &&
			   str.substring(k, k+1)!="8" &&
			   str.substring(k, k+1)!="9" )
			{
				flag = false;

				return flag;
			}
		}
   	}
   }
   else
   {

   	return true;
   }

return flag;
}

*/

/**********************************************************************************/
/*****	This function capitalixe each and every character typed.             ******/
/*****	It Used on onKeyPress event.                                         ******/
/*****	USAGE : <INPUT TYPE="text" onKeyPress="makeUpper();">                ******/
/**********************************************************************************/

//function makeUpper() {
// if (event.keyCode>=97 && event.keyCode<=122) {
//	event.keyCode = event.keyCode - 32;
// }
//}


/**********************************************************************************/
/****	This function validates whether input string exceeds 240 chars or not. ****/
/****	It take the field value as input parameter.                            ****/
/****	Call this function on onKeyPress event in the textarea.                ****/
/**********************************************************************************/
function validateDescriptionOnKeyPress (descObj) {
	var count = 0;
	count  = descObj.value.length + 1;
	if (count > 239) {
	   alert ('\n\nDescription can not exceed 240 characters.\n\n');
      	   event.returnValue = false;
	}

}

/**********************************************************************************/
/****	This function validates whether input string exceeds 240 chars or not. ****/
/****	It take the field object as input parameter.                           ****/
/****	Call this function on onBlur event in the textarea.                    ****/
/**********************************************************************************/
function validateDescriptionOnBlur (descObj) {
	var count = 0;
	count  = descObj.value.length;
	if (count > 239) {
	   alert ('\n\nDescription can not exceed 240 characters.\n\n');
	   descObj.focus();
	   descObj.select();
	}

}


/**********************************************************************************/
/****	This function validates whether input string is decimal  or not.       ****/
/****	It take the field object as input parameter.                           ****/
/****	Call this function on onBlur event in the textarea.                    ****/
/**********************************************************************************/
function validateDecimal(decObj) {
	if (!isDecimal(decObj.value)) {
	   alert ('\n\nIt should be a proper decimal field.\n\n');
	   decObj.focus();
	   decObj.select();
	}

}

/**********************************************************************************/
/****	This function validates whether input string is integer or not.        ****/
/****	It take the field object as input parameter.                            ****/
/****	Call this function on onBlur event in the textarea.                    ****/
/**********************************************************************************/
function validateNumber(numObj) {
	if (!isNumber(numObj.value)) {
	   alert ('\n\nIt should be a proper number field.\n\n');
	   numObj.focus();
	   numObj.select();
	}
}

function validateMandatorynes(manObj) {
	if (manObj.value=="") {
	   alert ('\n\nThis field should not be blank.\n\n');
	   manObj.focus();
	   manObj.select();
	}

}


function validatePassword(pasObj) {
	if (pasObj.value.length <7 || pasObj.value.length >10) {
	   alert ('\n\nPassword should be atleast 7 characters long\nand atmost 10 characters long.\n');
	   pasObj.focus();
	   pasObj.select();
	}

}



function valueOfObject (frmObj) {

   //alert ('NAME :'+frmObj.name);
   //alert ('type :'+frmObj.type);
   //alert ('val :'+frmObj.value);

   if(frmObj.type == 'text') { //for textbox

	return frmObj.value;

   } else if(frmObj.type == 'password') { //for password

	return frmObj.value;

   } else if(frmObj.type == 'textarea') {//for textarea

	return frmObj.value;

   } else  if(frmObj.type == 'hidden') {//for hidden field

	return frmObj.value;

   } else  if(frmObj.type == 'select-one') {//for select one

	var sel;
	for (var i=0; i<frmObj.options.length; i++) {
		if (frmObj.options[i].selected) {
		   sel = frmObj.options[i].value;
		}
	}
	return sel;

   } else  if(frmObj.type == 'select-multiple') {//for select multiple
	return "";
   } else if(frmObj.type == 'radio') {//for radio button
	return "";
   } else  if(frmObj.type == 'checkbox') { //for checkbox
	return "";
   } else  if(frmObj.type == 'hidden') {//for hidden field
	return "";

   } else  if(frmObj.type == 'button') {
	return "";
   } else {

	return "";
   }

}


/**********************************************************************************/
/*****	This function allows to enter only numeric characters.               ******/
/*****	It Used on onKeyPress event.                                         ******/
/*****	USAGE : <INPUT TYPE="text" onKeyPress="onlyNumber();">                ******/
/**********************************************************************************/
function onlyNumber() {
  if (event.keyCode<48 || event.keyCode>57) {
      event.returnValue = false;
  }
}

function checkDecimal() {
  if (event.keyCode<46 || event.keyCode>57 || event.keyCode==47) {
      event.returnValue = false;
  }

}



function makeUpper()
{
  //if ((event.keyCode<48 || event.keyCode>57) && (event.keyCode<65 || event.keyCode>90) && (event.keyCode<97 || event.keyCode>122) && (event.keyCode!=32))
  if ((event.keyCode<40 || event.keyCode>58) && (event.keyCode<63 || event.keyCode>126) && (event.keyCode<32 || event.keyCode>33) && (event.keyCode<36 || event.keyCode>37))
  //if ((event.keyCode<40 || event.keyCode>58) && (event.keyCode<63 || event.keyCode>126) && (event.keyCode<32 || event.keyCode>33) && (event.keyCode<36 || event.keyCode>37) && (event.keyCode!=38) && (event.keyCode!=39) && (event.keyCode!=35) && (event.keyCode!=59) && (event.keyCode!=60)  && (event.keyCode!=62))
  {
      event.returnValue = false;
  }
  else if (event.keyCode>=97 && event.keyCode<=122)
  {
	event.keyCode = event.keyCode - 32;
  }
}

/************ This function is for the fields which allow Special Characters ************/
function makeUpperSpecial()
{
  //if ((event.keyCode<48 || event.keyCode>57) && (event.keyCode<65 || event.keyCode>90) && (event.keyCode<97 || event.keyCode>122) && (event.keyCode!=32))
  //***if ((event.keyCode<40 || event.keyCode>58) && (event.keyCode<63 || event.keyCode>126) && (event.keyCode<32 || event.keyCode>33) && (event.keyCode<36 || event.keyCode>37))
  if ((event.keyCode<40 || event.keyCode>58) && (event.keyCode<63 || event.keyCode>126) && (event.keyCode<32 || event.keyCode>33) && (event.keyCode<36 || event.keyCode>37) && (event.keyCode!=38) && (event.keyCode!=39) && (event.keyCode!=35) && (event.keyCode!=59) && (event.keyCode!=60)  && (event.keyCode!=62))
  {
      event.returnValue = false;
  }
  else if (event.keyCode>=97 && event.keyCode<=122)
  {
	event.keyCode = event.keyCode - 32;
  }
}
/************ This function is for Organizational Name fields only ************/
function makeUpperForOrgName()
{
  //if ((event.keyCode<48 || event.keyCode>57) && (event.keyCode<65 || event.keyCode>90) && (event.keyCode<97 || event.keyCode>122) && (event.keyCode!=32))
  //***if ((event.keyCode<40 || event.keyCode>58) && (event.keyCode<63 || event.keyCode>126) && (event.keyCode<32 || event.keyCode>33) && (event.keyCode<36 || event.keyCode>37))
  //if ((event.keyCode<40 || event.keyCode>58) && (event.keyCode<63 || event.keyCode>126) && (event.keyCode<32 || event.keyCode>33) && (event.keyCode<36 || event.keyCode>37) && (event.keyCode!=39)  && (event.keyCode!=35) && (event.keyCode!=60)  && (event.keyCode!=62))
  //**//if((event.keyCode<48 || event.keyCode>57) && (event.keyCode<65 || event.keyCode>90) && (event.keyCode<97 || event.keyCode>122) && (event.keyCode!=35) && (event.keyCode!=32))
  if ((event.keyCode<40 || event.keyCode>58) && (event.keyCode<63 || event.keyCode>126) && (event.keyCode<32 || event.keyCode>33) && (event.keyCode<36 || event.keyCode>37) && (event.keyCode!=38) && (event.keyCode!=39) && (event.keyCode!=35) && (event.keyCode!=59) && (event.keyCode!=60)  && (event.keyCode!=62))
  {
      event.returnValue = false;
  }
  else if (event.keyCode>=97 && event.keyCode<=122)
  {
	event.keyCode = event.keyCode - 32;
  }
}

/************ This function is for Name fields only ************/
function makeUpperForName()
{
  //if ((event.keyCode<48 || event.keyCode>57) && (event.keyCode<65 || event.keyCode>90) && (event.keyCode<97 || event.keyCode>122) && (event.keyCode!=32))
  //***if ((event.keyCode<40 || event.keyCode>58) && (event.keyCode<63 || event.keyCode>126) && (event.keyCode<32 || event.keyCode>33) && (event.keyCode<36 || event.keyCode>37))
  //if ((event.keyCode<40 || event.keyCode>58) && (event.keyCode<63 || event.keyCode>126) && (event.keyCode<32 || event.keyCode>33) && (event.keyCode<36 || event.keyCode>37) && (event.keyCode!=38) && (event.keyCode!=39) && (event.keyCode!=60)  && (event.keyCode!=62))
  //**//if((event.keyCode<48 || event.keyCode>57) && (event.keyCode<65 || event.keyCode>90) && (event.keyCode<97 || event.keyCode>122) && (event.keyCode!=38) && (event.keyCode!=39) && (event.keyCode!=44) && (event.keyCode!=32))
  if ((event.keyCode<40 || event.keyCode>58) && (event.keyCode<63 || event.keyCode>126) && (event.keyCode<32 || event.keyCode>33) && (event.keyCode<36 || event.keyCode>37) && (event.keyCode!=38) && (event.keyCode!=39) && (event.keyCode!=35) && (event.keyCode!=59) && (event.keyCode!=60)  && (event.keyCode!=62))
  {
      event.returnValue = false;
  }
  else if (event.keyCode>=97 && event.keyCode<=122)
  {
	event.keyCode = event.keyCode - 32;
  }
}

/************ This function is for Comment fields only ************/
function makeUpperComment()
{
  //if ((event.keyCode<48 || event.keyCode>57) && (event.keyCode<65 || event.keyCode>90) && (event.keyCode<97 || event.keyCode>122) && (event.keyCode!=32))
  //***if ((event.keyCode<40 || event.keyCode>58) && (event.keyCode<63 || event.keyCode>126) && (event.keyCode<32 || event.keyCode>33) && (event.keyCode<36 || event.keyCode>37))
  //if ((event.keyCode<40 || event.keyCode>58) && (event.keyCode<63 || event.keyCode>126) && (event.keyCode<32 || event.keyCode>33) && (event.keyCode<36 || event.keyCode>37) && (event.keyCode!=38) && (event.keyCode!=39) && (event.keyCode!=35) && (event.keyCode!=44) && (event.keyCode!=60)  && (event.keyCode!=62))
  //**//if((event.keyCode<48 || event.keyCode>57) && (event.keyCode<65 || event.keyCode>90) && (event.keyCode<97 || event.keyCode>122) && (event.keyCode!=38) && (event.keyCode!=44) && (event.keyCode!=32))
  if ((event.keyCode<40 || event.keyCode>58) && (event.keyCode<63 || event.keyCode>126) && (event.keyCode<32 || event.keyCode>33) && (event.keyCode<36 || event.keyCode>37) && (event.keyCode!=38) && (event.keyCode!=39) && (event.keyCode!=35) && (event.keyCode!=59) && (event.keyCode!=60)  && (event.keyCode!=62))
  {
      event.returnValue = false;
  }
  else if (event.keyCode>=97 && event.keyCode<=122)
  {
	event.keyCode = event.keyCode - 32;
  }
}

/************ This function is for Address fields only ************/
function makeUpperAddress()
{
  //***if ((event.keyCode<40 || event.keyCode>57) && (event.keyCode<63 || event.keyCode>126) && (event.keyCode<32 || event.keyCode>33) && (event.keyCode<35 || event.keyCode>37))
  if ((event.keyCode<40 || event.keyCode>57) && (event.keyCode<63 || event.keyCode>126) && (event.keyCode<32 || event.keyCode>33) && (event.keyCode<36 || event.keyCode>37) && (event.keyCode!=39) && (event.keyCode!=60) && (event.keyCode!=62))
  {
      event.returnValue = false;
  }
  else if (event.keyCode>=97 && event.keyCode<=122)
  {
	event.keyCode = event.keyCode - 32;
  }
}

/**********************************************************************************/
/*****	This function allows to enter only alphanumeric characters.          ******/
/*****	It Used on onKeyPress event.                                         ******/
/*****	USAGE : <INPUT TYPE="text" onKeyPress="onlyAlphaNum();">             ******/
/**********************************************************************************/
function makeAlphaNum()
{
  //***if ((event.keyCode<48 || event.keyCode>57) && (event.keyCode<65 || event.keyCode>90) && (event.keyCode<97 || event.keyCode>122) && (event.keyCode!=32))
  if ((event.keyCode<48 || event.keyCode>57) && (event.keyCode<65 || event.keyCode>90) && (event.keyCode<97 || event.keyCode>122) && (event.keyCode!=32) && (event.keyCode!=39) && (event.keyCode!=35) && (event.keyCode!=45) && (event.keyCode!=60) && (event.keyCode!=62))
  {
      event.returnValue = false;
  }
  else if (event.keyCode>=97 && event.keyCode<=122)
  {
	event.keyCode = event.keyCode - 32;
  }
}

/************ This function is for Code and Document Type fields only ************/
function makeAlphaNumCode()
{
  //***if ((event.keyCode<48 || event.keyCode>57) && (event.keyCode<65 || event.keyCode>90) && (event.keyCode<97 || event.keyCode>122) && (event.keyCode!=32))
  //if ((event.keyCode<48 || event.keyCode>57) && (event.keyCode<65 || event.keyCode>90) && (event.keyCode<97 || event.keyCode>122) && (event.keyCode!=32) && (event.keyCode!=39) && (event.keyCode!=35) && (event.keyCode!=45) && (event.keyCode!=60) && (event.keyCode!=62))
  if ((event.keyCode<40 || event.keyCode>58) && (event.keyCode<63 || event.keyCode>126) && (event.keyCode<32 || event.keyCode>33) && (event.keyCode<36 || event.keyCode>37) && (event.keyCode!=38) && (event.keyCode!=39) && (event.keyCode!=35) && (event.keyCode!=59) && (event.keyCode!=60)  && (event.keyCode!=62))
  {
      event.returnValue = false;
  }
  else if (event.keyCode>=97 && event.keyCode<=122)
  {
	event.keyCode = event.keyCode - 32;
  }
}

/***********************************************************************************************************/
/*****	This function allows to enter only alphanumeric characters specially for description          ******/
/*****	It Used on onKeyPress event.                                        			      ******/
/*****	USAGE : <INPUT TYPE="text" onKeyPress="makeAlphaNumDescription();">                           ******/
/***********************************************************************************************************/
function makeAlphaNumDescription()
{
  //***if ((event.keyCode<48 || event.keyCode>57) && (event.keyCode<65 || event.keyCode>90) && (event.keyCode<97 || event.keyCode>122) && (event.keyCode!=32))
  //if ((event.keyCode<48 || event.keyCode>57) && (event.keyCode<65 || event.keyCode>90) && (event.keyCode<97 || event.keyCode>122) && (event.keyCode!=32) && (event.keyCode!=39) && (event.keyCode!=35) && (event.keyCode!=38) && (event.keyCode!=44) && (event.keyCode!=60) && (event.keyCode!=62))
  //**//if((event.keyCode<48 || event.keyCode>57) && (event.keyCode<65 || event.keyCode>90) && (event.keyCode<97 || event.keyCode>122) && (event.keyCode!=38) && (event.keyCode!=44) && (event.keyCode!=32))
  if ((event.keyCode<40 || event.keyCode>58) && (event.keyCode<63 || event.keyCode>126) && (event.keyCode<32 || event.keyCode>33) && (event.keyCode<36 || event.keyCode>37) && (event.keyCode!=38) && (event.keyCode!=39) && (event.keyCode!=35) && (event.keyCode!=59) && (event.keyCode!=60)  && (event.keyCode!=62))
  {
      event.returnValue = false;
  }
  else if (event.keyCode>=97 && event.keyCode<=122)
  {
	event.keyCode = event.keyCode - 32;
  }
}


/**********************************************************************************/
/****	This function checks whether a SSN is valid or not.                    ****/
/****	It take string as input parameter and return boolean as output.        ****/
/**********************************************************************************/
function isSSN(str) {
	var isssn=true;
	if ((str==null) || (str=="")){
		isssn=true;
		return isssn;
   	}
	var matchArr = str.match(/^(\d{3})-?\d{2}-?\d{4}$/);
	var numDashes = str.split('-').length - 1;
	if (matchArr == null || numDashes == 1) {
		isssn=false;
		return isssn;
	}
	return isssn;
  }

/**********************************************************************************/
/*****	This function allows to enter only a valid SSN		               ******/
/*****	It Used on onBlur event.                                         ******/
/*****	USAGE : Call with SSN.value and the fieldname as parameter	*****/
/**********************************************************************************/

function SSNValidation(ssn,ssnfield)
{
	if (ssnfield.value=="") return true;
	var matchArr = ssn.match(/^(\d{3})-?\d{2}-?\d{4}$/);
	var numDashes = ssn.split('-').length - 1;
	if (matchArr == null || numDashes == 1)
	{
		alert('Invalid SSN. Must be 9 digits or in the form NNN-NN-NNNN.');
		msg = "does not appear to be valid";
		ssnfield.select();
		ssnfield.focus();
		return false;
	}
	else
	{
		return true;
	}

}




/**********************************************************************************/
/*****	This function is called on click of lookup button                    ******/
/*****	lkpLookUp(code,desc,token,coverage,listtitle) is for general lookup and        ******/
/*****	function lkpLookUpSevBased(code,desc,token,coverage,sev,listtitle) is          *****/
/*****  for lookup which populate data based on Coverage.		     *****/
/*****  Here Code is for Text field name for Code.			     *****/
/*****       desc is for Text field name for Description.                     *****/
/*****       type is for property id.                                         *****/
/*****       cover is for Coverage Type.                  		      *****/
/**********************************************************************************/


var strUrl;
var tmpWin;

function lkpLookUp(code,desc,token,coverage,listtitle)
{

var txtcode = code;
var txtdesc = desc;
var strtoken = token;
var strCov  = coverage;
var strTitle = listtitle;
//alert("COV "+coverage);
strUrl = 'MainController.jsp?transaction=no&event=LookUp&codeField=' + txtcode + '&descField=' + txtdesc + '&type=' +strtoken +'&code=' + document.all(txtcode).value + '&description=' + document.all(txtdesc).value +'&coverage=' + strCov + '&listtitle=' + strTitle
//alert(strUrl);
if(!tmpWin)
{
tmpWin = window.open(strUrl, "title", 'height=450,width=600,top=10,left=10,scrollbars=yes');
}
else
{
tmpWin.close();
tmpWin = window.open(strUrl, "title", 'height=450,width=600,top=10,left=10,scrollbars=yes');
}
return tmpWin;
}

function lkpLookUpSevBased(code,desc,token,coverage,sev,listtitle)
{
var txtcode = code;
var txtdesc = desc;
var strtoken = token;
var strCov = coverage;
var strSev = sev;
var strTitle = listtitle;
//alert("COV "+coverage);
strUrl = 'MainController.jsp?event=LookUp&codeField=' + txtcode + '&descField=' + txtdesc + '&type=' +strtoken +'&code=' + document.all(txtcode).value + '&description=' + document.all(txtdesc).value +'&coverage=' + strCov +'&severity=' + strSev + '&listtitle=' + strTitle
//alert(strUrl);
if(!tmpWin)
{
tmpWin = window.open(strUrl, "title", 'height=450,width=600,top=10,left=10,scrollbars=yes');
}
else
{
tmpWin.close();
tmpWin = window.open(strUrl, "title", 'height=450,width=600,top=10,left=10,scrollbars=yes');
}
return tmpWin;
}

function lkpLookUpRsrvBased(code,desc,token,coverage,rsrv,listtitle)
{
var txtcode = code;
var txtdesc = desc;
var strtoken = token;
var strCov = coverage;
var strSev = rsrv;
var strTitle = listtitle;
//alert("COV "+coverage);
strUrl = 'MainController.jsp?event=LookUp&codeField=' + txtcode + '&descField=' + txtdesc + '&type=' +strtoken +'&code=' + document.all(txtcode).value + '&description=' + document.all(txtdesc).value +'&coverage=' + strCov +'&reserve=' + strSev + '&listtitle=' + strTitle
//alert(strUrl);
if(!tmpWin)
{
tmpWin = window.open(strUrl, "title", 'height=450,width=600,top=10,left=10,scrollbars=yes');
}
else
{
tmpWin.close();
tmpWin = window.open(strUrl, "title", 'height=450,width=600,top=10,left=10,scrollbars=yes');
}
return tmpWin;
}

/*******************************************************************************************/
/***********   This function converts the number in decimal format to its text format  oken**/
/*******************************************************************************************/

	var n = "";

	function amountText(input)
	{
		var search11 = new String(input)
		var SearchString = search11.indexOf(".");

		//alert(SearchString);
		var Search1 = input;
		if (SearchString==-1)
		{
			var Return1 = convert(Search1) + "Dollars" + " and No Cents";
			return Return1;
		}

		else
		{
			var String1= Search1.split(".")
			var StringBeforedecimal = String1[0];
			var StringAfterdecimal = String1[1];

			if(Math.round(StringAfterdecimal) == 0)
			{
				if (StringBeforedecimal == ""){
					var Return1 = " Zero Dollars" + " and " + " No Cents";
				}
				else {
					var Return1 = convert(StringBeforedecimal) + " Dollars" + " and No Cents";
				}
				return Return1;
			}

			else
			{
				if (StringBeforedecimal == 0) {
				var Return1 = " Zero Dollars" + " and " + convert(StringAfterdecimal) + "Cent";
				} else {
					var Return1 = convert(StringBeforedecimal) + "Dollars" + " and " + convert(StringAfterdecimal) + "Cent";
				}

				//alert("return"+ Return1);
				return Return1;
			}
		}
	}


	function d1(x)
	{ // single digit terms
		switch(x)
		{
			case '0': n= ""; break;
			case '1': n= " One "; break;
			case '2': n= " Two "; break;
			case '3': n= " Three "; break;
			case '4': n= " Four "; break;
			case '5': n= " Five "; break;
			case '6': n= " Six "; break;
			case '7': n= " Seven "; break;
			case '8': n= " Eight "; break;
			case '9': n= " Nine "; break;
			default: n = "Not a Number";
		}
		return n;
	}

	function d2(x)
	{ // 10x digit terms
		switch(x)
		{
			case '0': n= ""; break;
			case '1': n= ""; break;
			case '2': n= " Twenty "; break;
			case '3': n= " Thirty "; break;
			case '4': n= " Forty "; break;
			case '5': n= " Fifty "; break;
			case '6': n= " Sixty "; break;
			case '7': n= " Seventy "; break;
			case '8': n= " Eighty "; break;
			case '9': n= " Ninety "; break;
			default: n = "Not a Number";
		}
		return n;
	}

	function d3(x)
	{ // teen digit terms
		switch(x)
		{
			case '0': n= " Ten "; break;
			case '1': n= " Eleven "; break;
			case '2': n= " Twelve "; break;
			case '3': n= " Thirteen "; break;
			case '4': n= " Fourteen "; break;
			case '5': n= " Fifteen "; break;
			case '6': n= " Sixteen "; break;
			case '7': n= " Seventeen "; break;
			case '8': n= " Eighteen "; break;
			case '9': n= " Nineteen "; break;
			default: n=  "Not a Number";
		}
		return n;
	}

	function convert(input)
	{
		var inputlength = input.length;
		var x = 0;
		var teen1 = "";
		var teen2 = "";
		var teen3 = "";
		var numName = "";
		var invalidNum = "";
		var a1 = ""; // for insertion of million, thousand, hundred
		var a2 = "";
		var a3 = "";
		var a4 = "";
		var a5 = "";
		digit = new Array(inputlength); // stores output

		for (i = 0; i < inputlength; i++)
		{
			// puts digits into array
			digit[inputlength - i] = input.charAt(i);
		}
		store = new Array(9); // store output
		for (i = 0; i < inputlength; i++)
		{
			x= inputlength - i;
			switch (x)
			{ // assign text to each digit
				case x=9: d1(digit[x]); store[x] = n; break;
				case x=8: if (digit[x] == "1")
							{teen3 = "yes"}
						  else
							{teen3 = ""};
						  d2(digit[x]); store[x] = n; break;
				case x=7: if (teen3 == "yes")
							{teen3 = ""; d3(digit[x])}
						  else {d1(digit[x])}; store[x] = n; break;
				case x=6: d1(digit[x]); store[x] = n; break;
				case x=5: if (digit[x] == "1")
							{teen2 = "yes"}
						  else
							{teen2 = ""};
						  d2(digit[x]); store[x] = n; break;
				case x=4: if (teen2 == "yes")
							{teen2 = ""; d3(digit[x])}
						  else
						    {d1(digit[x])};
						  store[x] = n; break;
				case x=3: d1(digit[x]); store[x] = n; break;
				case x=2: if (digit[x] == "1")
							{teen1 = "yes"}
						  else
							{teen1 = ""};
						  d2(digit[x]); store[x] = n; break;
				case x=1: if (teen1 == "yes")
							{teen1 = "";d3(digit[x])}
						  else
							{d1(digit[x])};
						  store[x] = n; break;
			}
			if (store[x] == "Not a Number")
				{invalidNum = "yes"};

			switch (inputlength)
			{
				case 1:   store[2] = "";
				case 2:   store[3] = "";
				case 3:   store[4] = "";
				case 4:   store[5] = "";
				case 5:   store[6] = "";
				case 6:   store[7] = "";
				case 7:   store[8] = "";
				case 8:   store[9] = "";
			}

			if (store[9] != "")
				{ a1 =" Hundred, "}
			else
				{a1 = ""};
			if ((store[9] != "")||(store[8] != "")||(store[7] != ""))
				{ a2 =" Million, "}
			else
				{a2 = ""};
			if (store[6] != "")
				{ a3 =" Hundred "}
			else
				{a3 = ""};
			if ((store[6] != "")||(store[5] != "")||(store[4] != ""))
				{ a4 =" Thousand, "}
			else
				{a4 = ""};
			if (store[3] != "")
				{ a5 =" Hundred "}
			else
				{a5 = ""};
		}
		// add up text, cancel if invalid input found
		if (invalidNum == "yes")
			{numName = "Invalid Input"}
		else
		{
			numName =  store[9] + a1 + store[8] + store[7]
			+ a2 + store[6] + a3 + store[5] + store[4]
			+ a4 + store[3] + a5 + store[2] + store[1];
		}
		store[1] = ""; store[2] = ""; store[3] = "";
		store[4] = ""; store[5] = ""; store[6] = "";
		store[7] = ""; store[8] = ""; store[9] = "";
		if (numName == "")
			{numName = "Zero"};
		//document.myform.textver.value = numName;
		//return true;
		return numName;
}

/*******************************************************************************************/
/***********   End of number to text conversion functions.                        **********/
/*******************************************************************************************/

/*******************************************************************************************/
/***********   This function only allows to enter decimal values.                        **********/
/*******************************************************************************************/

function onlyDecimal() {
  if (event.keyCode<48 || event.keyCode>57 ) {
      if (event.keyCode!=46) {
       	event.returnValue = false;
       }
  }
}

/*******************************************************************************************/
/***********   This function only allows to enter decimal values.                 **********/
/***********   This function is for Phone Number and Fax Number fields.           **********/
/*******************************************************************************************/

function onlyDecimalChanged() {
  if (event.keyCode<48 || event.keyCode>57 ) {
      if ((event.keyCode!=40) && (event.keyCode!=41) && (event.keyCode!=45)) {
       	event.returnValue = false;
       }
  }
}

// ===================================================
//		validatePercent() checks that percent should
//		not be greater than 100.
// ===================================================

function validatePercent(objfield)
{
    if ((objfield.value) > 100 )
    {
			alert("Please Enter Any value between 0 to 100");
			objfield.focus();
			objfield.select();
	}
    else
        return true;
}

//For Dial-A-Zip

var win_option1= "height=200,width=300,top=130,left=250"
var win_option2= "height=250,width=400,top=130,left=200"
function windowOpenAddressContactValidation(address, city, state, zip, contactAddress, contactCity,contactState, contactZip)
{
	alert("entered windowOpenAddressValidation()");

	window.open("MainController.jsp?event=AddressValidate" + "&Address=" + address + "&State=" + state +"&City=" + city + "&Zip=" + zip + "&ContactAddress=" + contactAddress + "&ContactCity=" + contactCity + "&ContactState=" + contactState + "&ContactZip=" + contactZip,"ValidateAddressContact", win_option1);
	return true;
}

function windowOpenAddressValidation(address, city, state, zip)
{
	alert("entered windowOpenAddressValidation()");

	window.open("MainController.jsp?event=AddressValidate" + "&Address=" + address + "&State=" + state +"&City=" + city + "&Zip=" + zip , "ValidateAddressContact", win_option1);
	return true;
}

function replaceSpecialCharacter(strToReplace)
{
//	re = /&/g;
//	reNext = /#/g;
//	strFirstString = strToReplace;
//	strTempString = strFirstString.replace(re,"%26");
//	strFinalString = strTempString.replace(reNext,"%23");
//  escape() is a standard java script function.
	return escape(strToReplace);
}

function ValidateAddress(address, city, state, zip, country,addressActiveStatus,
	contactAddress, contactCity, contactState, contactZip, contactCountry,  contactActiveStatus)
{
	//alert("Dial a Zip - newValidateAddress - before calling JSP country = " + country);
	
	address = replaceSpecialCharacter(address);
	city = replaceSpecialCharacter(city);
	zip = replaceSpecialCharacter(zip);
	contactAddress = replaceSpecialCharacter(contactAddress);
	contactCity = replaceSpecialCharacter(contactAddress);
	contactZip = replaceSpecialCharacter(contactZip);
	/*
	alert(address);
	alert(city);
	alert(contactAddress);
	alert(contactCity);
	*/
	var strUrl ;
	if ((addressActiveStatus == "Y") && (contactActiveStatus == "Y"))
	{
		strUrl ="AddressValidate.jsp?Address=" + address + "&State=" + state +"&City=" + city + "&Zip=" + zip
											+ "&Country=" + country + "&ContactAddress=" + contactAddress + "&ContactCity=" + contactCity +
									"&ContactState=" + contactState + "&ContactZip=" + contactZip + "&ContactCountry=" + contactCountry;
      	}
      	else if ((addressActiveStatus == "Y") && ( (contactActiveStatus == "") || (contactActiveStatus == "N"))	)							
	{
		strUrl = "AddressValidate.jsp?Address=" + address + "&State=" + state +"&City=" + city + "&Zip=" + zip + "&Country=" + country;
	}
	else if (((addressActiveStatus == "") || (addressActiveStatus == "N")) && (contactActiveStatus == "Y"))								
	{
		strUrl = "AddressValidate.jsp?Address=" + contactAddress + "&State=" + contactState +"&City=" + contactCity + "&Zip=" + contactZip + "&Country=" + contactCountry + "&contactInd=Y";
	}
	else
	{
		document.forms[0].hidAddressValidatedIndicator.value = "Y";
		document.forms[0].hidResultFromAddressValidation.value = "1";
		document.forms[0].hidMessageFromAddressValidation.value = "Address validation is not required";
		document.forms[0].hidResultFromContactValidation.value = "1";	
		
		return;
	}
	
	var tmpWIn = window.open(strUrl, "title", win_option2);

	while (!tmpWIn.closed)
	{
	}


}

//Start
/**********************************************************************************/
/*****	This function is called on click of lookup button                    ******/
/*****	lkpA49LookUp(code,desc,token,listtitle) is for general lookup and        ******/
/*****  Here Code is for Text field name for Code.			     *****/
/*****       desc is for Text field name for Description.                     *****/
/*****       type is for LKP_TYPE                                         *****/
/**********************************************************************************/


var strUrl;
var tmpWin;

function lkpA49LookUp(code,desc,token,listtitle)
{

var txtcode = code;
var txtdesc = desc;
var strtoken = token;

var strTitle = listtitle;
strUrl = 'MainController.jsp?transaction=no&event=A49LookUp&codeField=' + txtcode + '&descField=' + txtdesc + '&type=' +strtoken +'&code=' + document.all(txtcode).value + '&description=' + document.all(txtdesc).value +'&listtitle=' + strTitle

if(!tmpWin)
{
tmpWin = window.open(strUrl, "title", 'height=450,width=600,top=10,left=10,scrollbars=yes');
}
else
{
tmpWin.close();
tmpWin = window.open(strUrl, "title", 'height=450,width=600,top=10,left=10,scrollbars=yes');
}
return tmpWin;
}

var secondsPerMinute = 60;
var minutesPerHour = 60;

function convertSecondsToHHMMSS(intSecondsToConvert) {
	var hours = convertHours(intSecondsToConvert);
	var minutes = getRemainingMinutes(intSecondsToConvert);
	minutes = (minutes == 60) ? "00" : minutes;
	var seconds = getRemainingSeconds(intSecondsToConvert);
	return hours+"."+minutes;
}
	
function convertHours(intSeconds) {
	var minutes = convertMinutes(intSeconds);
	var hours = Math.floor(minutes/minutesPerHour);
	return hours;
}

function convertMinutes(intSeconds) {
	return Math.floor(intSeconds/secondsPerMinute);
}

function getRemainingSeconds(intTotalSeconds) {
	return (intTotalSeconds%secondsPerMinute);
}

function getRemainingMinutes(intSeconds) {
	var intTotalMinutes = convertMinutes(intSeconds);
	return (intTotalMinutes%minutesPerHour);
}

function HMStoSec1(T) { // h.m
  //var A = T.split(/\D+/) ; 
  var A = T.split(".") ;
//  return (A[0]*60 + +A[1])*60 + +A[2]
  return (A[0]*60 + +A[1])*60
}

//End


