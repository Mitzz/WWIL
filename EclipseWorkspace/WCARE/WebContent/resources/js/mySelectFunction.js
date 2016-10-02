$.fn.myFunction = function(selectableBoxId, selectedBoxId, allToLeft, allToRight, selectToLeft, selectToRight) {
	$('#' + selectToRight).click(function(e) {
		var selectedOpts = $('#' + selectableBoxId + ' option:selected');
		if (selectedOpts.length == 0) {
			e.preventDefault();
		}

		$('#' + selectedBoxId).append($(selectedOpts).clone());
		$(selectedOpts).remove();
		e.preventDefault();
	});

	$('#' + selectToLeft).click(function(e) {
		var selectedOpts = $('#' + selectedBoxId + ' option:selected');
		if (selectedOpts.length == 0) {
			e.preventDefault();
		}

		$('#' + selectableBoxId).append($(selectedOpts).clone());
		$(selectedOpts).remove();
		e.preventDefault();
	});

	$('#' + selectableBoxId).dblclick(function() {
		$('#' + selectableBoxId + ' option:selected').appendTo('#' + selectedBoxId);
		$("#" + selectedBoxId + " option:selected").removeAttr("selected");
	});

	$('#' + selectedBoxId).dblclick(function() {
		$('#' + selectedBoxId + ' option:selected').appendTo('#' + selectableBoxId);
		$("#" + selectableBoxId + " option:selected").removeAttr("selected");
	});

	$('#' + allToRight).click(function() {
		$("#" + selectableBoxId + " option").each(function() {
			$(this).appendTo('#' + selectedBoxId);
		});
	});

	$('#' + allToLeft).click(function() {
		$("#" + selectedBoxId + " option").each(function() {
			$(this).appendTo('#' + selectableBoxId);
		});
	});
};

$.fn.ajaxRequest = function(param, fn){
	$.ajax({
		url: 'AjaxFrontController.do',
		data: param,
		type: 'POST',
		dataType: 'json',
		success: function(data){
			fn(data);
		}
	});
}

$.fn.populateSelectionField = function(selector, data){
	var selectionField = $(selector);
	$.each(data, function (index, selectVo) {
		selectionField.append(new Option(selectVo.textValue, selectVo.optionValue));
	});
}

$.fn.selectAllFunction = function(selectedId) {
	$("#" + selectedId + " option").each(function(){
		$('#' + selectedId + ' option[value="' + $(this).val() + '"]').prop('selected', true);
	});
};

$.fn.getSelectOptionValueForQuery = function(selectElementId){
	
	var multipleIds = "";       		
	$("#" + selectElementId + " > option").each(function() {
		multipleIds = multipleIds + "'" + this.value + "'" + ",";
	});
	
	return multipleIds.slice(0,-1);
};

$.fn.isSelectBoxEmpty = function(selectBoxId){
	return (!$("#" + selectBoxId).has("option").length > 0 );
};

$.fn.displayMessageIfEmpty = function(isEmpty, message) {
	if(isEmpty){
		alert(message);
	}
}

$.fn.isSelectBoxEmptyWithErrorMessage = function(selectedField, errorMessage){
	var isEmpty = (!$(selectedField).has("option").length > 0 );
	$.fn.displayMessageIfEmpty(isEmpty, errorMessage);
	return isEmpty;
};

$.fn.isInputTextFieldEmpty = function(inputFieldId){
	return (!$.trim($("#" + inputFieldId).val()));
};

$.fn.isInputTextFieldEmptyWithErrorMessage = function(selectedField, errorMessage){
	var isEmpty = (!$.trim($(selectedField).val()));
	$.fn.displayMessageIfEmpty(isEmpty, errorMessage);
	return isEmpty;
};

$.fn.validateFromDateToDate = function(fromDate, toDate){
	if($.fn.calculateTimeDifferenceBetweenTwoDateInMinutes($.fn.getDateObject(fromDate),$.fn.getDateObject(toDate)) < 0){
		return false;
	}
	return true;
}

$.fn.getDateObject = function(dateString){
    var year = -1;
    var month = -1;
    var day = -1;
    
    var dateStringSplit = dateString.split("/");
    
    year = dateStringSplit[2];
    month = dateStringSplit[1] - 1;
    day = dateStringSplit[0];
    
/*        console.log(day + ":" + month + ":" + year);*/
    return new Date(year, month, day);
}

$.fn.calculateTimeDifferenceBetweenTwoDateInMinutes = function(fromDate, toDate){
    return (toDate.getTime() - fromDate.getTime()) / (1000 * 60);
}

$.fn.getMonthNo = function(monthName){
	monthName = monthName.toLowerCase();
	var monthNames = ["jan", "feb", "mar", "apr", "may", "jun", "jul", "aug", "sep", "oct", "nov", "dec"
	                    		/*"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"*/
	                    	]
	for(var index in monthNames){
		if(monthNames[index] == monthName)
			return parseInt(index) + 1;
	}
}

$.fn.getMonthName = function(month){
	var monthNames = ["","January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" ]; 
    return monthNames[month];
}

$.fn.zeroFill = function( number, width, char )
{
  width -= number.toString().length;
  if ( width > 0 )
  {
    return new Array( width + (/\./.test( number ) ? 2 : 1) ).join( char ) + number;
  }
  return number + ""; // always return a string
}

//getHHMMFormat
$.fn.formatTime = function(minutes){
	minutes = parseInt(minutes,10);
	var mmhharray = [minutes % 60 , parseInt(minutes / 60)];

	var min = '' + mmhharray[0];
	var hr = '' + mmhharray[1];

	if(hr.length === 1){
	    hr = "0" + hr;
	}
	
	if(min.length === 1){
	    min = "0" + min;
	}
	
	return hr + " : " + min;
}

//numberWithCommas
$.fn.formatNumber = function(x) {
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

/*$.fn.restrictCharacter = function(selector){
	console.log("function called");
	var c = $(selector);
	var characters = c.attr('data-maxlength');
	
	if(c.val().length > characters){
        c.val(c.val().substr(0, characters));
	}	
}*/

function rc(selector){
	var c = $(selector);
	var characters = c.attr('data-maxlength');
	
	if(c.val().length > characters){
        c.val(c.val().substr(0, characters));
	}
}

$.fn.charactersCheck = function(){ 
	
	$(".check-character-length").keyup(function(){
		
		//Check if attribute 'data-maxlength' present
		if ($(this).is('[data-maxlength]')) {
			var characters = $(this).attr('data-maxlength');
			
			if($(this).val().length > characters){
				$(this).val($(this).val().substr(0, characters));
			}
		} else if ($(this).is('[maxlength]')) {		//used especially for struts tag which don't allowed used defined attributes. 
			var characters = $(this).attr('maxlength') - 10;//(maxlength - 10) -> Important
			
			if($(this).val().length > characters){
				$(this).val($(this).val().substr(0, characters));
			}
		}
		
	});
};

$.fn.getCalendarFormat = function(date){
	var arr = date.split("-");
	logger(arr);
	var day = $.fn.zeroFill(arr[0], 2, '0');
	var month = $.fn.zeroFill($.fn.getMonthNo(arr[1]), 2, '0');
	var year = arr[2];
	return day + "/" + month + "/" + year;
};

/*<option value="">--Make a Selection--</option>*/
$.fn.initializeSelectionField = function(selectionField, vos, value, text){
	for(var vo of vos){
		selectionField.append(new Option(vo[text], vo[value]));
	}
};

$.fn.disableSelection = function(selectors){
	$.each(selectors, function(index, selector){
		$(selector).prop("disabled", true);
	});
}

$.fn.enableSelection = function(selectors){
	$.each(selectors, function(index, selector){
		$(selector).prop("disabled", false);
	});
}

$.fn.emptySelection = function(selectors){
	$.each(selectors, function(index, selector){
		$(selector).empty().append(new Option("--Make a Selection--", "ns"));
	});
}

$.fn.getVo = function(propertyValue, vos, propertyName){
	for(var vo of vos){
		if(vo[propertyName] == propertyValue)
			return vo;
	}
};