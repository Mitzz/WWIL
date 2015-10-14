
/************************ Java Scripts For Common Usage   *************************/
/* 
 *         Author:         Ravi Kiran Gupta
 *         Version:        1.0
 *         Creation Date:  16/12/2005
 *
 */

  function submitToPage(page) {
    document.forms[0].action=page;
    document.forms[0].submit();
  }

  function linkToPage(page) {
    location.href=page;
  }

 function openPopup(path,width,height,name){
    var left = ((screen.availWidth - width)/2).toString();
	var top = ((screen.availHeight - height)/2).toString();
 	_popup(path,left,top,width,height,0,1,name);
 } 

 function _fullscreenPopup(path){
	var width = (screen.availWidth - 10).toString();
	var height = (screen.availHeight - 122).toString();
	wOPen =_popup(path,0,0,width,height,0,0)
	wOpen.moveTo( 0, 0 );
	wOpen.resizeTo( screen.availWidth, screen.availHeight );
	wOPen.opener.close();
	return wOpen;
 }

 function _popup(path,left,top,width,height,toolbar,statusbar,resize){
      var features = "left="+left+",top="+top+",width="+width+",height="+height+",toolbar="+toolbar+",status="+statusbar+"resizable="+resize;
	  if(width > 700){
	   features +=",scrollbars=1";
	  }else{
	     features +=",scrollbars=0";
	  }
	  wOpen = window.open(path,'',features);
	  return wOpen;
 }

/*	This function should be called on onFocus event of the login id textbox */

  function setBlank() {
    if(this.value && this.value == 'loginid') { 
      this.value = ''; 
      this.form.password.value = ''; 
    } else { 
  		this.select(); 
    }
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
	  if((!isPositiveInteger(str1)) || (str1>31||str1==0)){
      return false;
    } else {
      var str2 = (str.substring(0, f1));
      var mm = parseFloat(str2);
      if(mm > 12 || mm < 1){
        return false;
      }	else {   
        var str3 = str.substring(f2+1, str.length);
        if(!isPositiveInteger(str3) || str3=="" || str3==null||parseFloat(str3)>9999) 
          return false;
      }
    }
    if(!isValidDate(str)){
      return false;
    }
    return true;
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

	function isTime(str)
	{
	   if(str=="") return true;
	   var indx1 = str.indexOf(":", 0);
	   var strHr ;
	   var strMin ;

	   if(indx1==-1){
		return false;
	   }

	   else if(indx1==0){
		return false;
	   }

	   else
	   {
		if (indx1 == 2)
		   strHr = str.substring(0,2) ;
		else if(indx1 == 1)
		   strHr = str.substring(0,1) ;
		if (isNumber(strHr) && (eval(strHr)>=0) && (eval(strHr)<24))
		{
			if (indx1 == 2)
			   strMin = str.substring(3,5) ;
			else if(indx1 == 1)
			   strMin = str.substring(2,4) ;
			if (isNumber(strMin) && (eval(strMin)>=0) && (eval(strMin)<60))
			   return true;
			else
			   return false;
		}
		else
			return false;
	   }
	}
  
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
  
/**********************************************************************************/
/*****	This function is called onblur event of TEXT AREA fields          ******/
/**********************************************************************************/


  function chkLength(ctrl,  size) {
    var prmData = Trim(ctrl.value);
    
    var  prmAllowedSize = parseInt(size);

    if (prmData.length >  prmAllowedSize) {
      alert("Data length cannot  be greater than " +  size);
      ctrl.focus();
    }
        
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
    return (!isError);
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
  
  
   /***************Calender Routines ****************************/
  
function show_calendar(str_target, str_datetime) { 
 var arr_months = ["January", "February", "March", "April", "May", "June", 
  "July", "August", "September", "October", "November", "December"]; 
 var week_days = ["Su", "Mo", "Tu", "We", "Th", "Fr", "Sa"]; 
 var n_weekstart = 1; // day week starts from (normally 0 or 1) 
 var dt_datetime = (str_datetime == null || str_datetime =="" ?  new Date() : str2dt(str_datetime)); 
 var dt_prev_month = new Date(dt_datetime); 
 dt_prev_month.setMonth(dt_datetime.getMonth()-1); 
 var dt_next_month = new Date(dt_datetime); 
 dt_next_month.setMonth(dt_datetime.getMonth()+1); 
 var dt_firstday = new Date(dt_datetime); 
 dt_firstday.setDate(1); 
 dt_firstday.setDate(1-(7+dt_firstday.getDay()-n_weekstart)%7); 
 var dt_lastday = new Date(dt_next_month); 
 dt_lastday.setDate(0); 
  
 // html generation (feel free to tune it for your particular application) 
 // print calendar header 
 var str_buffer = new String ( 
  "<html>\n"+ 
  "<head>\n"+ 
  " <title>Calendar</title>\n"+ 
  "</head>\n"+ 
  "<body bgcolor=\"#000000\">\n"+ 
  "<table class=\"clsOTable\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n"+ 
  "<tr><td bgcolor=\"#700000\">\n"+ 
  "<table cellspacing=\"1\" cellpadding=\"3\" border=\"0\" width=\"100%\">\n"+ 
  "<tr>\n <td bgcolor=\"#700000\"><a href=\"javascript:window.opener.show_calendar('"+ 
  str_target+"', '"+ dt2dtstr(dt_prev_month)+"'+document.cal.time.value);\">"+ 
  "<img src=\"../resources/images/leftarrow.jpg\" width=\"12\" height=\"12\" border=\"0\""+ 
  " alt=\"previous month\"></a></td>\n"+ 
  " <td bgcolor=\"#700000\" colspan=\"5\">"+ 
  "<font color=\"white\" face=\"tahoma, verdana\" size=\"2\">" 
  +arr_months[dt_datetime.getMonth()]+" "+dt_datetime.getFullYear()+"</font></td>\n"+ 
  " <td bgcolor=\"#700000\" align=\"right\"><a href=\"javascript:window.opener.show_calendar('" 
  +str_target+"', '"+dt2dtstr(dt_next_month)+"'+document.cal.time.value);\">"+ 
  "<img src=\"../resources/images/rightarrow.jpg\" width=\"12\" height=\"12\" border=\"0\""+
  " alt=\"next month\"></a></td>\n</tr>\n" 
 ); 
 var dt_current_day = new Date(dt_firstday); 
 // print weekdays titles 
 str_buffer += "<tr>\n"; 
 for (var n=0; n<7; n++) 
  str_buffer += " <td bgcolor=\"#FFCCCC\">"+ 
  "<font color=\"#700000\" face=\"tahoma, verdana\" size=\"2\">"+ 
  week_days[(n_weekstart+n)%7]+"</font></td>\n"; 
 // print calendar table 
 str_buffer += "</tr>\n"; 
 while (dt_current_day.getMonth() == dt_datetime.getMonth() || 
  dt_current_day.getMonth() == dt_firstday.getMonth()) { 
  // print row heder 
  str_buffer += "<tr>\n"; 
  for (var n_current_wday=0; n_current_wday<7; n_current_wday++) { 
    if (dt_current_day.getDate() == dt_datetime.getDate() && 
     dt_current_day.getMonth() == dt_datetime.getMonth()) 
     // print current date 
     str_buffer += " <td bgcolor=\"red\" align=\"right\">"; 
    else if (dt_current_day.getDay() == 0 || dt_current_day.getDay() == 6) 
     // weekend days 
     str_buffer += " <td bgcolor=\"#E8E8E8\" align=\"right\">"; 
    else 
     // print working days of current month 
     str_buffer += " <td bgcolor=\"#E8E8E8\" align=\"right\">"; 
    if (dt_current_day.getMonth() == dt_datetime.getMonth()) 
     // print days of current month 
     str_buffer += "<a href=\"javascript:window.opener."+str_target+ 
     ".value='"+dt2dtstr(dt_current_day)+"'+document.cal.time.value; window.close();\">"+ 
     "<font color=\"black\" face=\"tahoma, verdana\" size=\"2\">"; 
    else 
     // print days of other months 
     str_buffer += "<a href=\"javascript:window.opener."+str_target+ 
     ".value='"+dt2dtstr(dt_current_day)+"'+document.cal.time.value; window.close();\">"+ 
     "<font color=\"gray\" face=\"tahoma, verdana\" size=\"2\">"; 
    str_buffer += dt_current_day.getDate()+"</font></a></td>\n"; 
    dt_current_day.setDate(dt_current_day.getDate()+1); 
  } 
  // print row footer 
  str_buffer += "</tr>\n"; 
 } 
 // print calendar footer 
 str_buffer += 
  "<form name=\"cal\">\n<tr><td colspan=\"7\" bgcolor=\"#FFCCCC\">"+ 
  "<font color=\"#700000\" face=\"tahoma, verdana\" size=\"2\">"+ 
  "<input type=\"hidden\" name=\"time\" value=\""+dt2tmstr(dt_datetime)+ 
  "\" size=\"8\" maxlength=\"8\"></font></td></tr>\n</form>\n" + 
  "</table>\n" + 
  "</tr>\n</td>\n</table>\n" + 
  "</body>\n" + 
  "</html>\n"; 
 var vWinCal = window.open("", "Calendar","width=200,height=250,status=no,left=500,top=100,menubar=0,toolbar=0"); 
 vWinCal.opener = self; 
 var calc_doc = vWinCal.document; 
 calc_doc.write (str_buffer); 
 calc_doc.close(); 
} 
// datetime parsing and formatting routimes. modify them if you wish other datetime format 
function str2dt (str_datetime) 
{ 
 str_datetime +='00:00:00';
 var re_date = /^(\d+)\-(\d+)\-(\d+)\s+(\d+)\:(\d+)\:(\d+)$/; 
 if (!re_date.exec(str_datetime)) 
 return alert("Invalid Datetime format: "+ str_datetime); 
 return (new Date (RegExp.$3, RegExp.$2-1, RegExp.$1, RegExp.$4, RegExp.$5, RegExp.$6)); 
        } 
function dt2dtstr (dt_datetime) 
{ 
 return (new  
                String(dt_datetime.getDate()+"-"+(dt_datetime.getMonth()+1)+"-"+dt_datetime.getFullYear()+" ")); 
        } 
function dt2tmstr (dt_datetime) 
{ 
   //return (new  
   //            String(dt_datetime.getHours()+":"+dt_datetime.getMinutes()+":"+dt_datetime.getSeconds())); 
   return '';
 } 
  
/**************End Of Calender Routines **********************/  

/************* Method for checking whether blank data is put into textarea *********/
function chkEnterKey(data,FieldName)
      {        
        var vdata=Trim(data.value);        
        var e_keyFlag=0;        
        for(i=0;i<vdata.length;i++)
        {    
          if(vdata.charCodeAt(i)!=10&&vdata.charCodeAt(i)!=13&&vdata.charCodeAt(i)!=32)
          { 
            return;
          }
        }
        if(vdata.length!=0)
        {
          alert("Enter proper data in "+FieldName); 
          data.select();
          data.focus();
          data.value="";
          return;
        }
      }