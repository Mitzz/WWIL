
function validateForm() {
  //data format = array containing 'fieldName', 'data', 'mandatoryness', 'dataType'

  var errString="The following error(s) has occurred :-\n\n";

  var args = validateForm.arguments;
  for (var i=0; i<(args.length); i+=4) {
//  	alert ("label:"+args[i] + " value:" + args[i+1] + " mandatoryness:" + args[i+2] + " dataType:" + args[i+3]);

	if(args[i + 2]=='M') {
		if (args[i+1]=="") {
		   errString += "  - " + args[i] + " is mandatory.\n";
		} else {
		   if(args[i+3]=='D') {
			if (!dateChk(args[i+1])) {
		   	  errString += "  - " + args[i] + " should be a date field of the format dd/mm/yyyy\n";
			}
		   } else if(args[i+3]=='I') {
			if (!numberChk(args[i+1])) {
		   	  errString += "  - " + args[i] + " should be an number value.\n";
			}
		   } else if(args[i+3]=='M') {
			if (!decimalChk(args[i+1])) {
		   	  errString += "  - " + args[i] + " should be a decimal field.\n";
			}'O'
		   } else if(args[i+3]=='P') {
			if (!phoneChk(args[i+1])) {
		   	  errString += "  - " + args[i] + " should be a phone no.\n";
			}
		   }else if(args[i+3]=='Q') {
			if (!isSSN(args[i+1])) {
		   	  errString += "  - " + args[i] + "  should be a valid SSN.\n";
			}
		   }else if(args[i+3]=='E') {
			if (!emailChk(args[i+1])) {
		   	  errString += "  - " + args[i] + " should be valid email address.\n";
			}
		   } else if(args[i+3]=='T') {
			if (!timeChk(args[i+1])) {
		   	  errString += "  - " + args[i] + "- Valid values allowed are 00:00 thru 23:59.\n";
			}
		   }
		}
	}

	else {
		if (args[i+1]!="") {
		   if(args[i+3]=='D') {
			if (!dateChk(args[i+1])) {
		   	  errString += "  - " + args[i] + " should be a date field.\n";
			}
		   } else if(args[i+3]=='I') {
			if (!numberChk(args[i+1])) {
		   	  errString += "  - " + args[i] + " should be an number value.\n";
			}
		   } else if(args[i+3]=='M') {
			if (!decimalChk(args[i+1])) {
		   	  errString += "  - " + args[i] + " should be a decimal field.\n";
			}
		   } else if(args[i+3]=='P') {
			if (!phoneChk(args[i+1])) {
		   	  errString += "  - " + args[i] + " should be a phone no.\n";
			}
		   } else if(args[i+3]=='E') {
			if (!emailChk(args[i+1])) {
		   	  errString += "  - " + args[i] + " should be valid email address.\n";
			}
		   } else if(args[i+3]=='T') {
			if (!timeChk(args[i+1])) {
		   	  errString += "  - " + args[i] + "-Valid values allowed are 00:00 thru 23:59.\n";
			}
		   } else if(args[i+3]=='DC') {
		   	if (!dateComparison(args[(i-(2*4))+1],args[(i-(1*4))+1])) {
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
function numberChk(str) {
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
function decimalChk(str){
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

function validateDOB(bDate,sysDate)
{
	var year1,year2
	var month1, month2
	var day1, day2
	if (bDate.value == "") return true;
	if (validateDate(bDate))
	{
		bDate = bDate.value;
		year1 = bDate.substring(6,10)
		day1 = bDate.substring(0,2)
		month1 = bDate.substring(3,5)

		year2 = sysDate.substring(6,10)
		year2 = year2 - 150;
		day2 = sysDate.substring(0,2)
		month2 = sysDate.substring(3,5)

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
function validateDD(date) {

   	if((date.value=="")||(date.value == date.defaultValue)) return true;

	if(!dateChk(date.value)){
	   alert("Invalid Date\nFormat : dd/mm/yyyy");
	   date.select();
	   date.focus();
	   return false;
	} else {

	   var f1 = date.value.indexOf("/");
     	   var f2 = date.value.indexOf("/", f1+1);

	   var day = date.value.substring(0, f1);
	   var mon = date.value.substring(f1+1, f2);
	   var year = date.value.substring(f2+1, date.value.length);

	   if(parseFloat(year) < 1000 )	year = 2000 + parseFloat(year);

	   	if ((parseFloat(day) < 10 )&&(day.length == 1)) day = '0'+day;
	   	if ((parseFloat(mon) < 10 )&&(mon.length == 1)) mon = '0'+mon;
	   	date.value = day + "/" + mon + "/" + year;

	   	return true;
	}
}


/**********************************************************************************/
/****	This function check whether a string has date format or not.           ****/
/****	It take string as input parameter and return boolean as output.        ****/
/**********************************************************************************/
function dateChk(str){
	var f1 = str.indexOf("/");
	var f2=-1;
	if(f1!=-1){
		f2 = str.indexOf("/", f1+1);
	}

	if(f1==-1||f2==-1||f1==0){
		return false;
	}

   var str1 = str.substring(f1+1, f2);
   if((!numberChk(str1)) || (str1>12||str1<=0)){return false;}

   else {
	var str2 = (str.substring(0, f1));
	var dd = parseFloat(str2);
	if(dd > 31 || dd <= 0){
		return false;
	}

	else{
		var str3 = str.substring(f2+1, str.length);
		if(!numberChk(str3) || str3=="" || str3==null||parseFloat(str3)>9999) return false;

	}

   }
   if(!validDateChk(str)){
	return false;
   }

   return true;
}

function dateComparison(dateFrom, dateTo)
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
function validDateChk(str){
   var f1 = str.indexOf("/");
   var f2 = str.indexOf("/", f1+1);
   var day = str.substring(0, f1);
   var month = str.substring(f1+1, f2);
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
function emailChk(str){
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

function phoneChk(str){

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
