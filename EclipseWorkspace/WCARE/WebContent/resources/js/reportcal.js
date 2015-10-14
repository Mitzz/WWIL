
/************************ Java Scripts For SRM Module   *************************/
/* 
 *         Author:         Jithin 
 *         Version:        1.0
 *         Creation Date:  14/12/2005
 *
*/


/**********Global Variables ***************/
   var SelRFQValue;
  
/*****************************************/

/*
 *  Function to Show SearchResults For RFQ
 *
*/

 function showRFQSearchResults(){
	   var index = document.criteria.status.selectedIndex;
	   
	 /*** Block Criteria Div *********/
	   document.getElementById('cr1').style.display = "none";
	   document.getElementById('cr2').style.display = "none";
	   document.getElementById('cr3').style.display = "none";
	   document.getElementById('submit').style.display = "none";
	   document.getElementById('spacer').style.display = "none";

	 /******** Change Header **********/
	   if(index==1){
		   //document.getElementById('header').style.paddingLeft ='250px';
		   document.getElementById('header').innerHTML="<b>SEARCH RESULTS FOR CLOSED RFQ</b>";
		   document.getElementById('closedresults').style.display = "block";
	   }else{
		   //document.getElementById('header').style.paddingLeft ='250px';
		   document.getElementById('header').innerHTML="<b>SEARCH RESULTS FOR PENDING RFQ</b>";
		   document.getElementById('pendingresults').style.display = "block";
	   } 
	  
}


/*** Various PopUp Functions **********/

 function popup(path,width,height,name){
    var left = ((screen.availWidth - width)/2).toString();
	var top = ((screen.availHeight - height)/2).toString();
 	var wOpen=_popup(path,left,top,width,height,0,0,name);
 } 
 function openPopup(path,width,height){
     var left = ((screen.availWidth - width)/2).toString();
	 var top = ((screen.availHeight - height)/2).toString();
	 var features = "left="+left+",top="+top+",width="+width+",height="+height+",toolbar="+0+",resizable="+0;
	 window.open(path,'',features);
 }

 function _popup(path,left,top,width,height,toolbar,resize){
      var features = "left="+left+",top="+top+",width="+width+",height="+height+",toolbar="+toolbar+",resizable="+resize;
	  if(width > 700){
	   features +=",scrollbars=1";
	  }else{
	     features +=",scrollbars=0";
	  }
	  wOpen = window.open(path,'',features);
	  return wOpen;
	
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

/******* End of Popup Functions **************/

/******* Function to Navigate to QuotePage **************/

function displayQuotePage(){
   if(SelRFQValue=='3')
	   location.href="SRMServlet?srmActionId=getSpecifiedRFQForQuotationCreation";
   else if(SelRFQValue=='2')
	   location.href="SRMServlet?srmActionId=getSpecifiedRFQForQuotationCreation"; 
   else
	   location.href="SRMServlet?srmActionId=getSpecifiedRFQForQuotationCreation";  
} 


function enableQuoteButton(rdbBtn){
  
    SelRFQValue=rdbBtn.value;
	document.getElementById('submitquote').style.display = "block";
}

function submitF(){

//sSelRFQValue==rdbBtn.value;
document.forms[SelRFQValue].submit();
}

function changePage(pageid){
   location.href = pageid;
}


/*** Table Rows Addition & Deletion Functions ***********/

function addRow(){
              var int_Length = document.getElementById('DynaTable').rows.length
              obj_Row=document.getElementById('DynaTable').insertRow(int_Length)
              obj_Row.id = int_Length
              obj_Cell=obj_Row.insertCell(0)
			  obj_Cell.innerHTML="<input type='radio'  class='tablecell' name='radio' id='radio_1"+String(int_Length)+"' value = "+ String(int_Length)+" onclick='changeStatus(this)'>"
	          for(i=0;i<2;i++){
			     obj_Cell=obj_Row.insertCell(1)
                 obj_Cell.innerHTML="<input type='text' class='tablecell' name='techparam' id='text1"+String(int_Length)+"'>"
			  }
 }
 
 function removeRow(el){
     var obj = document.getElementsByName('radio');
     for(var i=0;i<obj.length;i++)
     {
	     if(obj.length==1) {
		   var status = obj[i].value
		   if(status==-1) {
			document.getElementById('DynaTable').deleteRow(i+1);
			return;
		    } 		 
	      }else {  
		if (obj[i].checked == true) {
			var status = obj[i].value
			if(status==-1) {
				document.getElementById('DynaTable').deleteRow(i+1);
				return;
			 }
		 }
		 
	     }	   
      }
		  
   }
   
   
   function addRow2(){
              var int_Length = document.getElementById('DynaTable2').rows.length
              obj_Row=document.getElementById('DynaTable2').insertRow(int_Length)
              obj_Row.id = int_Length
              obj_Cell=obj_Row.insertCell(0)
			  obj_Cell.innerHTML="<input type='radio'  class='tablecell' name='radio' id='radio_1"+String(int_Length)+"' value = "+ String(int_Length)+" onclick='changeStatus(this)'>"
	          for(i=0;i<13;i++){
			     obj_Cell=obj_Row.insertCell(1)
                 if(i==0)
                 {
                 obj_Cell.innerHTML="<input type='text' class='tablecell' name='itemno"+String(int_Length)+"' id='text1"+String(int_Length)+"' readonly='readonly' />"
                 }
                  if(i==1)
                 {
                 obj_Cell.innerHTML="<input type='text' class='tablecell' name='priceparam' id='text1"+String(int_Length)+"'  maxlength='240' />" //description"+String(int_Length)+"
                 }
                  if(i==2)
                 {
                 obj_Cell.innerHTML="<input type='text' class='tablecell' name='priceparam' id='text1"+String(int_Length)+"'   maxlength='30'  />" //unit"+String(int_Length)+"
                 }
                  if(i==3)
                 {
                 obj_Cell.innerHTML="<input type='text' class='tablecell' name='priceparam' id='text1"+String(int_Length)+"'  maxlength='10'  />" //qtyoffered"+String(int_Length)+"
                 }
                  if(i==4)
                 {
                 obj_Cell.innerHTML="<input type='text' class='tablecell' name='priceparam' id='text1"+String(int_Length)+"' readonly='readonly' maxlength='15'  />" //basicprice"+String(int_Length)+"
                 }
                  if(i==5)
                 {
                 obj_Cell.innerHTML="<input type='text' class='tablecell' name='priceparam' id='text1"+String(int_Length)+"' readonly='readonly'  maxlength='15'  />" //pkgfwd"+String(int_Length)+"
                 }
                  if(i==6)
                 {
                 obj_Cell.innerHTML="<input type='text' class='tablecell' name='priceparam' id='text1"+String(int_Length)+"'  maxlength='5'   />" //excise"+String(int_Length)+"
                 }
                  if(i==7)
                 {
                 obj_Cell.innerHTML="<input type='text' class='tablecell' name='priceparam' id='text1"+String(int_Length)+"' readonly='readonly' maxlength='15'  />" //subtotal"+String(int_Length)+"
                 }
                  if(i==8)
                 {
                 obj_Cell.innerHTML="<input type='text' class='tablecell' name='priceparam' id='text1"+String(int_Length)+"'  maxlength='5'  />" //vatcst"+String(int_Length)+"
                 }
                  if(i==9)
                 {
                 obj_Cell.innerHTML="<input type='text' class='tablecell' name='priceparam' id='text1"+String(int_Length)+"' readonly='readonly' maxlength='15'  />" //freight"+String(int_Length)+"
                 }
                  if(i==10)
                 {
                 obj_Cell.innerHTML="<input type='text' class='tablecell' name='priceparam' id='text1"+String(int_Length)+"' readonly='readonly' maxlength='15' />" //totalcost"+String(int_Length)+"
                 }
                  if(i==11)
                 {
                 obj_Cell.innerHTML="<input type='text' class='tablecell' name='priceparam' id='text1"+String(int_Length)+"' readonly='readonly'  maxlength='15'  />" //cenvat"+String(int_Length)+"
                 }
                  if(i==12)
                 {
                 obj_Cell.innerHTML="<input type='text' class='tablecell' name='priceparam' id='text1"+String(int_Length)+"' readonly='readonly'  maxlength='15'  />" //lcostnetofcenvatvat"+String(int_Length)+"
                 }
			  }
 }
 
  function addRow3(){
              var int_Length = document.getElementById('DynaTable2').rows.length
              obj_Row=document.getElementById('DynaTable2').insertRow(int_Length)
              obj_Row.id = int_Length
              obj_Cell=obj_Row.insertCell(0)
			  obj_Cell.innerHTML="<input type='radio'  class='tablecell' name='radio' id='radio_1"+String(int_Length)+"' value = "+ String(int_Length)+" onclick='changeStatus(this)'>"
	          for(i=0;i<13;i++){
			     obj_Cell=obj_Row.insertCell(1)
                 if(i==0)
                 {
                 obj_Cell.innerHTML="<input type='text' class='tablecell' name='itemno"+String(int_Length)+"' id='text1"+String(int_Length)+"'>"
                 }
                  if(i==1)
                 {
                 obj_Cell.innerHTML="<input type='text' class='tablecell' name='priceparam' id='text1"+String(int_Length)+"' maxlength='240' >" //description"+String(int_Length)+"
                 }
                  if(i==2)
                 {
                 obj_Cell.innerHTML="<input type='text' class='tablecell' name='priceparam' id='text1"+String(int_Length)+"' maxlength='30'  >" //unit"+String(int_Length)+"
                 }
                  if(i==3)
                 {
                 obj_Cell.innerHTML="<input type='text' class='tablecell' name='priceparam' id='text1"+String(int_Length)+"' maxlength='10'  >" //qtyoffered"+String(int_Length)+"
                 }
                  if(i==4)
                 {
                 obj_Cell.innerHTML="<input type='text' class='tablecell' name='priceparam' id='text1"+String(int_Length)+"' maxlength='15'   >" //basicprice"+String(int_Length)+"
                 }
                  if(i==5)
                 {
                 obj_Cell.innerHTML="<input type='text' class='tablecell' name='priceparam' id='text1"+String(int_Length)+"' maxlength='15'  >" //pkgfwd"+String(int_Length)+"
                 }
                  if(i==6)
                 {
                 obj_Cell.innerHTML="<input type='text' class='tablecell' name='priceparam' id='text1"+String(int_Length)+"' maxlength='15'  >" //excise"+String(int_Length)+"
                 }
                  if(i==7)
                 {
                 obj_Cell.innerHTML="<input type='text' class='tablecell' name='priceparam' id='text1"+String(int_Length)+"' readonly='readonly' maxlength='15'   >" //subtotal"+String(int_Length)+"
                 }
                  if(i==8)
                 {
                 obj_Cell.innerHTML="<input type='text' class='tablecell' name='priceparam' id='text1"+String(int_Length)+"' maxlength='15'   >" //vatcst"+String(int_Length)+"
                 }
                  if(i==9)
                 {
                 obj_Cell.innerHTML="<input type='text' class='tablecell' name='priceparam' id='text1"+String(int_Length)+"' maxlength='15'   >" //freight"+String(int_Length)+"
                 }
                  if(i==10)
                 {
                 obj_Cell.innerHTML="<input type='text' class='tablecell' name='priceparam' id='text1"+String(int_Length)+"' readonly='readonly' maxlength='15'   >" //totalcost"+String(int_Length)+"
                 }
                  if(i==11)
                 {
                 obj_Cell.innerHTML="<input type='text' class='tablecell' name='priceparam' id='text1"+String(int_Length)+"' readonly='readonly' maxlength='15'   >" //cenvat"+String(int_Length)+"
                 }
                  if(i==12)
                 {
                 obj_Cell.innerHTML="<input type='text' class='tablecell' name='priceparam' id='text1"+String(int_Length)+"' readonly='readonly' maxlength='15'   >" //lcostnetofcenvatvat"+String(int_Length)+"
                 }
			  }
 }
 
 
 function removeRow2(el){
     var obj = document.getElementsByName('radio');
     for(var i=0;i<obj.length;i++)
     {
	     if(obj.length==1) {
		   var status = obj[i].value
		   if(status==-1) {
			document.getElementById('DynaTable2').deleteRow(i+1);
			return;
		    } 		 
	      }else {  
		if (obj[i].checked == true) {
			var status = obj[i].value
			if(status==-1) {
				document.getElementById('DynaTable2').deleteRow(i+1);
				return;
			 }
		 }
		 
	     }	   
      }
		  
   }
   
  function removeRow3(el){
     var obj = document.getElementsByName('radio');
     for(var i=0;i<obj.length;i++)
     {
	     if(obj.length==1) {
		   var status = obj[i].value
		   if(status==-1) {
			document.getElementById('DynaTable2').deleteRow(i+1);
			return;
		    } 		 
	      }else {  
		if (obj[i].checked == true) {
			var status = obj[i].value
			if(status==-1) {
				document.getElementById('DynaTable2').deleteRow(i+1);
				return;
			 }
		 }
		 
	     }	   
      }
		  
   } 
   
   function changeStatus(rbtn)  {
         rbtn.value=-1;
   }

   function addInspectionRow(){
              var int_Length = document.getElementById('DynaTable').rows.length
              obj_Row=document.getElementById('DynaTable').insertRow(int_Length)
              obj_Row.id = int_Length
              obj_Cell=obj_Row.insertCell(0)
			  obj_Cell.innerHTML="<input type='radio'   name='radio' id='radio_1"+String(int_Length)+"' value = "+ String(int_Length)+" onclick='changeStatus(this)'>"
	          for(i=0;i<8;i++){
			     obj_Cell=obj_Row.insertCell(1)
                 obj_Cell.innerHTML="<input type='text' class='reportText' name='textTptName"+"' id='text1"+String(int_Length)+"'>"
			  }
 }
 
 function removeInspectionRow(el){
     var obj = document.getElementsByName('radio');
     for(var i=0;i<obj.length;i++)
     {
	     if(obj.length==1) {
		   var status = obj[i].value
		   if(status==-1) {
			document.getElementById('DynaTable').deleteRow(i+1);
			return;
		    } 		 
	      }else {  
		if (obj[i].checked == true) {
			var status = obj[i].value
			if(status==-1) {
				document.getElementById('DynaTable').deleteRow(i+1);
				return;
			 }
		 }
		 
	     }	   
      }
		  
   }
    function addInspectionCertRow(){
              var int_Length = document.getElementById('DynaTable').rows.length
              obj_Row=document.getElementById('DynaTable').insertRow(int_Length)
              obj_Row.id = int_Length
              obj_Cell=obj_Row.insertCell(0)
			  obj_Cell.innerHTML="<input type='radio'   name='radio' id='radio_1"+String(int_Length)+"' value = "+ String(int_Length)+" onclick='changeStatus(this)'>"
	          for(i=0;i<9;i++){
			     obj_Cell=obj_Row.insertCell(1)
                 obj_Cell.innerHTML="<input type='text' class='reportText' name='textTptName"+String(int_Length)+"' id='text1"+String(int_Length)+"'>"
			  }
 }
 
 function removeInspectionCertRow(el){
     var obj = document.getElementsByName('radio');
     for(var i=0;i<obj.length;i++)
     {
	     if(obj.length==1) {
		   var status = obj[i].value
		   if(status==-1) {
			document.getElementById('DynaTable').deleteRow(i+1);
			return;
		    } 		 
	      }else {  
		if (obj[i].checked == true) {
			var status = obj[i].value
			if(status==-1) {
				document.getElementById('DynaTable').deleteRow(i+1);
				return;
			 }
		 }
		 
	     }	   
      }
		  
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
 var re_date = /^(\d+)\.(\d+)\.(\d+)\s+(\d+)\:(\d+)\:(\d+)$/; 
 if (!re_date.exec(str_datetime)) 
 return alert("Invalid Datetime format: "+ str_datetime); 
 return (new Date (RegExp.$3, RegExp.$2-1, RegExp.$1, RegExp.$4, RegExp.$5, RegExp.$6)); 
        } 
function dt2dtstr (dt_datetime) 
{ 
 return (new  
                String(dt_datetime.getDate()+"."+(dt_datetime.getMonth()+1)+"."+dt_datetime.getFullYear()+" ")); 
        } 
function dt2tmstr (dt_datetime) 
{ 
   //return (new  
   //            String(dt_datetime.getHours()+":"+dt_datetime.getMinutes()+":"+dt_datetime.getSeconds())); 
   return '';
 } 
  
/**************End Of Calender Routines **********************/



function closePopUpWindow(id,txtid){
   window.opener.document.getElementById(txtid).value=id;
   window.close();
}

function openPO(path){
  /*
		_fullscreenPopup(path);
   }else{
      
   }*/
}



function crossValidateDate(date1, date2){

	var strf = date1.value;
	var strt = date2.value;

	var f11 = strf.indexOf("-");
	var f21 =  strf.indexOf("-", f11+1);
    var dayf =  strf.substring(0, f11);
    var monthf =  strf.substring(f11+1, f21);
    var yearf = strf.substring(f21+1, strf.indexOf(' '));
    var monf = parseFloat(monthf)-1;
    if(yearf=="0") yearf="2000";

	var f12 = strt.indexOf("-");
	var f22 = strt.indexOf("-", f12+1);
    var dayt = strt.substring(0, f12);
    var montht = strt.substring(f12+1, f22);
    var yeart = strt.substring(f22+1, strf.indexOf(' '));
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

function validate(){
  if(document.getElementById('RFQNO').value==''){
     alert('Please Specify RFQ Number to Proceed');
	 return false;
  }
  changePage('InspectionList.html');
}

function activateView(){
  document.getElementById('submit').style.display = "block"; 
  
}

function validateSearch(id,id1){
   if(document.getElementById(id).value=='' || document.getElementById(id1).value==''){
       alert('Please Specify Both PO Number & Vendor Code to Proceed');
	   return false;
   }
   document.cookie ="OPENIC=FALSE;path=/"
   _fullscreenPopup('inspectioncrtficte.html');
}

function Upload(){
   document.getElementById('msg').style.display = "block";
   document.getElementById('textRFQ').style.display = "none";
   document.getElementById('textPO').style.display = "none";
   document.getElementById('fileTDC').style.display = "none";
   document.getElementById('fileEDGP').style.display = "none";
   document.getElementById('submit').style.display = "none";
}

function showICList(){
   if(document.getElementById('PONO').value==''){
      alert('Please Provide PO Number To Proceed ');
	  return false;
   }
   document.getElementById('cr1').style.display = "none";
   document.getElementById('submit').style.display = "none";
   document.getElementById('result').style.display = "block";
}

function openIC(){
   document.cookie ="OPENIC=TRUE;path=/"
   _fullscreenPopup('inspectioncrtficte.html');

}

function get_cookie ( cookie_name )
{
		  var results = document.cookie.match ( cookie_name + '=(.*?)(;|$)' );

		  if ( results )
			return ( unescape ( results[1] ) );
		  else
			return null;
}






function isNumeric(txtVal){
  var pattern = "0123456789";
  var length = txtVal.length;
  var _char,_string="";
  for(i=0;i<length;i++){
       _char=txtVal.charAt(i);
       if(pattern.indexOf(_char) != -1){
                  _string +=_char;
       }
   }
   if(_string=="") {
      return false; 
   }
   return true;
}