/************************ Java Scripts For Auctions Module   *************************/
/* 
 *         Author:         Ravi Kiran Gupta
 *         Version:        1.0
 *         Creation Date:  16/12/2005
 *
 */


    
   	function validateAuctionForm() {
   	  	//data format = array containing 'fieldName', 'data', 'mandatoryness', 'dataType'
        
   	    var errString="The following error(s) has occurred :-\n\n";
   	    var args = validateAuctionForm.arguments;
   	    for (var i=0; i<(args.length); i+=4) {
   	  	//alert ("label:"+args[i] + " value:" + args[i+1] + " mandatoryness:" + args[i+2] + " dataType:" + args[i+3]);

        
   		if(args[i + 2]=='M') {
   			if (args[i+1]=="") {
   			   errString += "  - " + args[i] + " is mandatory.\n";
   			} else {
   			   if(args[i+3]=='D') {
   				if (!isDate(args[i+1])) {
   			   	  errString += "  - " + args[i] + " should be a date field.\n";
   				}
   			   } else if(args[i+3]=='I') {
   				if (!isNumber(args[i+1])) {
   			   	  errString += "  - " + args[i] + " should be an integer value.\n";
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
   			   	  errString += "  - " + args[i] + " should be an integer value.\n";
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

	function crossValidateAuctionDate(date1, date2){

		var strf = date1.value;
		var strt = date2.value;
		var diff = 0;
    var systdate = new Date(); 

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
    var yeart = strt.substring(f22+1, strt.indexOf(' '));
    var mont = parseFloat(montht) - 1;

  	//alert("From Date: " + dayf + "-" + monthf + "-" + yearf);
		//alert("To Date: " + dayt + "-" + montht + "-" + yeart);
    //alert(systdate);

		if(yeart=="0") yeart="2000";

    	var df = new Date(yearf, monf, dayf);
	    var dt = new Date(yeart, mont, dayt);
		if (df > dt) {
			alert("Closing Date cannot be earlier than Start Date.");			
			return false;
		}
    else if(systdate.getDate()>df.getDate())
    {
      alert("Start Date cannot be earlier than Current Date.");	
      return false;
    }
		return true;
	}    


	function crossValidateSearchAuctionDate(date1, date2){

		var strf = date1.value;
		var strt = date2.value;
		var diff = 0;

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
    var yeart = strt.substring(f22+1, strt.indexOf(' '));
    var mont = parseFloat(montht) - 1;

  	//alert("From Date: " + dayf + "-" + monthf + "-" + yearf);
		//alert("To Date: " + dayt + "-" + montht + "-" + yeart);
    //alert(systdate);

		if(yeart=="0") yeart="2000";

    	var df = new Date(yearf, monf, dayf);
	    var dt = new Date(yeart, mont, dayt);
		if (df > dt) {
			alert("Closing Date cannot be earlier than Opening Date.");			
			return false;
		}
		return true;
	} 

	function crossValidateAuctionDateTime(date1, date2){

		var strf = date1.value;
		var strt = date2.value;
		var diff = 0;
    var systdate = new Date(); 

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
    var yeart = strt.substring(f22+1, strt.indexOf(' '));
    var mont = parseFloat(montht) - 1;

  	//alert("From Date: " + dayf + "-" + monthf + "-" + yearf);
		//alert("To Date: " + dayt + "-" + montht + "-" + yeart);
    //alert(systdate);

		if(yeart=="0") yeart="2000";

    	var df = new Date(yearf, monf, dayf);
	    var dt = new Date(yeart, mont, dayt);
		if (df > dt) {
			alert("Closing Date cannot be earlier than Opening Date.");			
			return false;
		}
    else if(systdate.getDate()>df.getDate())
    {
      alert("Start Date cannot be earlier than Current Date.");	
      return false;
    }	
    else {
			var one_day = 1000 * 60 * 60 * 24;
			diff = (Math.ceil((dt.getTime() - df.getTime()) / (one_day)));
			//alert("Difference : " + diff + " days ");
		}

		//var openingTimeHr = parseInt((document.forms[0].opentimehour.value == "00" ) ? "24" : document.forms[0].opentimehour.value);
		var openingTimeHr = parseInt(document.forms[0].opentimehour.value);
    var openingTimeMin = parseInt(document.forms[0].opentimemin.value);
		var openingTimeSec = parseInt(document.forms[0].opentimesec.value);		
			
		//var closingTimeHr = parseInt((document.forms[0].closetimehour.value == "00" ) ? "24" : document.forms[0].closetimehour.value);
		var closingTimeHr = parseInt(document.forms[0].closetimehour.value)
    var closingTimeMin = parseInt(document.forms[0].closetimemin.value);
		var closingTimeSec = parseInt(document.forms[0].closetimesec.value);		
			
		//alert("Opening Time: " + openingTimeHr + ":" + openingTimeMin + ":" + openingTimeSec );
		//alert("Closing Time: " + closingTimeHr + ":" + closingTimeMin + ":" + closingTimeSec );
						
		//alert("Difference : " + diff + " days ");
			
		if ( diff == 0 ) {
			if ( openingTimeHr > closingTimeHr ) {
				alert("Closing Hour is less than Opening Hour.");
				return false;
			}
				
			if ( openingTimeHr == closingTimeHr ) {
				if ( openingTimeMin > closingTimeMin ) {
					alert("Closing Minute is less than Opening Minute.");						
					return false;
				}
					
				if ( openingTimeMin == closingTimeMin ) {
					if ( openingTimeSec > closingTimeSec ) {
						alert("Closing Second is less than Opening Second.");
						return false;
					}
				
					if ( openingTimeSec == closingTimeSec ) {
						alert("Time Settings Identical. Please set valid time.");
						return false;
					}						
				}
			}	
		}
		return true;
	}
  
  
  /*  This function is used for checking discounts percentages of Discount - based Auction */
  
    function checkDiscount() {
      var startbiddiscount = parseFloat(document.forms[0].startbiddiscount.value);
      //var estimateddiscount = parseFloat(document.forms[0].estimateddiscount.value);
      var incrementdiscount = parseFloat(document.forms[0].incrementdiscount.value);

      if ( document.forms[0].quantity.value == 0 ) {
        alert("Quantity cannot be zero.");
        document.forms[0].quantity.select();
        document.forms[0].quantity.focus();
        return false;
      }

      if ( startbiddiscount > 100  ) {
        alert("Discount percentage cannot be more than 100. ");        
        document.forms[0].startbiddiscount.select();
        document.forms[0].startbiddiscount.focus();
        return false;
      }

      //if ( estimateddiscount > 100 ) {
      //  alert("Discount percentage cannot be more than 100. ");        
      //  document.forms[0].estimateddiscount.select();
      //  document.forms[0].estimateddiscount.focus();
      //  return false;
      //}

      if ( incrementdiscount > 100 ) {
        alert("Discount percentage cannot be more than 100. ");        
        document.forms[0].incrementdiscount.select();
        document.forms[0].incrementdiscount.focus();        
        return false;
      }
      
      //if ( estimateddiscount < startbiddiscount ) {
      //  alert("Estimated Discount should be greater than Start Bid Discount. ");
      //  document.forms[0].estimateddiscount.select();
      //  document.forms[0].estimateddiscount.focus();        
      //  return false;
      //}
      
      //if ( (estimateddiscount - startbiddiscount) < incrementdiscount ) {
      //  alert("Increment Value % should be less than % difference between \n Start Bid Discount % & Estimated Discount %. ");
      //  document.forms[0].incrementdiscount.select();
      //  document.forms[0].incrementdiscount.focus();  
      //  return false;
      //}      
      return true;
    }   
    
    
    /*  This function is used for checking prices of Reverse Auction */
    
    function checkPricing() {
      var startbidprice = parseFloat(document.forms[0].startbidprice.value);
      var estimatedprice = parseFloat(document.forms[0].estimatedprice.value);
      var decrementvalue = parseFloat(document.forms[0].decrementvalue.value);
      
      if (document.forms[0].quantity.value == 0) {
        alert("Quantity cannot be zero.");
        document.forms[0].quantity.select();
        document.forms[0].quantity.focus();          
        return false;
      }
      
      if ( estimatedprice > startbidprice ) {
        alert("Estimated Price should be less than Start Bid Price. ");
        document.forms[0].estimatedprice.select();
        document.forms[0].estimatedprice.focus();          
        return false;
      }
      
      if ( (startbidprice - estimatedprice) < decrementvalue ) {
        alert("Decrement value should be less than difference between \n Start Bid Price & Estimated Price. ");
        document.forms[0].decrementvalue.select();
        document.forms[0].decrementvalue.focus();          
        return false;
      }      
      return true;
    }    