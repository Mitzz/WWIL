  
  //	***************	Additional Scripts incorporated by Ritesh on 14-12-2005	**********	//
  
  
  
  function validateForm() {
  	//data format = array containing 'fieldName', 'data', 'mandatoryness', 'dataType'

    var errString="The following error(s) has occurred :-\n\n";

    var args = validateForm.arguments;
    for (var i=0; i<(args.length); i+=4) {
  	//alert ("label:"+args[i] + " value:" + args[i+1] + " mandatoryness:" + args[i+2] + " dataType:" + args[i+3]);

	if(args[i + 2]=='M') {
		if (Trim(args[i+1])=="") {
		   errString += "  - " + args[i] + " is mandatory.\n";
		} else {
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
		   }else if(args[i+3]=='Q') {
			if (!isSSN(args[i+1])) {
		   	  errString += "  - " + args[i] + "  should be a valid SSN.\n";
			}
		   }else if(args[i+3]=='E') {
			if (!isEmail(args[i+1])) {
		   	  errString += "  - " + args[i] + " should be a valid email address.\n";
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
		   	  errString += "  - " + args[i] + " should be a valid email address.\n";
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




function gotoSave() {

			var blnSave=true;
					if(!(validateForm('Company Name',document.custregn.compname.value,'M','',
								  'Company Address',document.custregn.compadd.value,'M','',
								  'City',document.custregn.city.value,'M','',
								  'State',document.custregn.state.value,'M','',
								  'Country',document.custregn.country.value,'M','',
								  'Pin',document.custregn.pin.value,'0','',

								  'Country Code',document.custregn.concode.value,'M','',
								  'Area Code',document.custregn.areacode.value,'M','',
								  'Phone Code',document.custregn.phone.value,'M','',
								  'Fax',document.custregn.fax.value,'O','P',
								  'Contact Person',document.custregn.conperson.value,'M','',
								  'Tel. No.',document.custregn.telno.value,'O','P',
								  'Fax No.',document.custregn.faxno.value,'O','',
								  'Email',document.custregn.email.value,'M','E',
								  'Mobile No.',document.custregn.mobileno.value,'O','P',
								  'Turnover of the Company',document.custregn.turnover.value,'O','',
								  'Annual Tonnage',document.custregn.anltonnage.value,'O',''

							)))
							{
						blnSave=false;
					} else {
						alert("Your data has been successfully saved!!");
						location="../Admin/AdminHome.html";
			}

	}





	function checkItems() {

	  var flag=0;
	  var args = checkItems.arguments;

	  for (var i=0; i<(args.length); i+=2) {

		if (args[i+1]!="") {
		   flag++;
			}
		}

		if(flag!=0) {
			location="EnquireSearchResults.html";
		}
		else
			alert("Please enter data in one or more fields in order to Enquire");
	}


	function gotoCheck() {
		checkItems('Steel Grade',document.enq.steelgrd.value,
						  'Size',document.enq.sz.value,
						  'Quantity',document.enq.qty.value,
						  'Chemistry',document.enq.spchem.value,
						  'Delivery Requirements',document.enq.delyreq.value)
	}



	function checkItems2() {

	  var flag=0;
	  var args = checkItems2.arguments;

	  for (var i=0; i<(args.length); i+=2) {

		if (args[i+1]!="") {
		   flag++;
			}
		}

		if(flag!=0) {
			alert("Your data has been successfully saved!!");
			location="../Admin/AdminHome.html";
			//location="EnquireSearchResults.html";
		}
		else
			alert("Please enter data in one or more fields in order to Register.");
	}


	function gotoCheck2() {
		checkItems2('Steel Grade',document.regnreq.steelgrd.value,
						  'Size',document.regnreq.sz.value,
						  'Quantity',document.regnreq.qty.value,
						  'Chemistry',document.regnreq.spchem.value,
						  'Delivery Requirements',document.regnreq.delyreq.value)
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



/**********************************************************************************/
/*****	This function check an input string is valid date or not.            ******/
/*****	It is Used to validate leap year.                                    ******/
/*****	It is Used by isDate function only.                                  ******/
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

/**TRIM function repeated from common.jsp, this was necessary as this page uses TRIM function now**/
function Trim(TRIM_VALUE){
    if(TRIM_VALUE.length < 1){
      return"";
    }
  
    TRIM_VALUE = RTrim(TRIM_VALUE);
    TRIM_VALUE = LTrim(TRIM_VALUE);
    if(TRIM_VALUE==""){
      return "";
    } else{
      return TRIM_VALUE;
    }
  } //End Function

  function RTrim(VALUE){
    var w_space = String.fromCharCode(32);
    var v_length = VALUE.length;
    var strTemp = "";
    if(v_length < 0){
      return"";
    }
    var iTemp = v_length -1;

    while(iTemp > -1){
      if(VALUE.charAt(iTemp) == w_space){
      } else{
        strTemp = VALUE.substring(0,iTemp +1);
        break;
      }
      iTemp = iTemp-1;

    } //End While
    return strTemp;

  } //End Function

  function LTrim(VALUE){
    var w_space = String.fromCharCode(32);
    if(v_length < 1){
      return"";
    }
    
    var v_length = VALUE.length;
    var strTemp = "";
    var iTemp = 0;

    while(iTemp < v_length){
      if(VALUE.charAt(iTemp) == w_space){
      } else{
        strTemp = VALUE.substring(iTemp,v_length);
        break;
      }
      iTemp = iTemp + 1;
    } //End While
    return strTemp;
  } //End Function
  