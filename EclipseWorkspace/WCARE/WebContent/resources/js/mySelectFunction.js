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

$.fn.getMonthName = function(month){
	var monthNames = ["","January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" ]; 
    return monthNames[month];
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

/*$(".check-character-length").keyup(function(){
	
	var characters = $(this).attr('data-maxlength');
	
	if($(this).val().length > characters){
        $(this).val($(this).val().substr(0, characters));
	}
});*/