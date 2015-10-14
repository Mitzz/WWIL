//Add New Row in grid with rowId	
function addRows(gridObj, colNo) {
	return addGridRows(gridObj, "rowId", colNo);
}

//Delete Selected Row from grid	
function deleteRows(gridObj) {
	gridObj.deleteSelectedRows();
}

function addGridRows(gridObj, idType, lineNoColIndex)
{
	var newValue = 0;
	var lineNumber = 0;
	for(var i = 0;i< gridObj.getRowsNum();i++){
		var id = gridObj.getRowId(i);
		newValue = parseInt(gridObj.cells(id,lineNoColIndex).getValue());
		if(newValue > lineNumber){
			lineNumber = newValue;
		}
	}
	if(idType == "uniqueId"){
		var uniqueID = gridObj.uid();
		gridObj.addRow(uniqueID, [ ]);
		gridObj.cells(uniqueID,lineNoColIndex).setValue(lineNumber+1);
		return uniqueID;
	}else{
		gridObj.addRow(lineNumber+1, [ ]);
		gridObj.cells(lineNumber+1,lineNoColIndex).setValue(lineNumber+1);
		return lineNumber + 1;
	}
}

function includePaging(obj, rows) {
	obj.enablePaging(true, rows, 5, "pagingArea", true);
	obj.setPagingSkin("bricks");
}

//duplicate include paging method for different grids
function includePaging1(obj, rows) {
	obj.enablePaging(true, rows, 5, "pagingArea1", true);
	obj.setPagingSkin("bricks");
}


function gridValidMaxLen(){
	//Max length validation of 50 Character in grid Cell
	dhtmlxValidation.isMax50 = function(value) {
		return value.length <= 50;
	};
	//Max length validation of 200 Character in grid Cell
	dhtmlxValidation.isMax200 = function(value) {
		return value.length <= 200;
	};
	//Number Validation of less than 9999999999.99 in grid Cell
	dhtmlxValidation.isMaxWt12 = function(value) {
		return eval(value) <= 9999999999.99;
	};
	//Max length validation of 500 Character in grid Cell
	dhtmlxValidation.isMax500 = function(value) {
		return value.length <= 500;
	};
	dhtmlxValidation.isMax100 = function(value) {
		return value.length <= 100;
	};
}

//compare date between fromDate(small value) and toDate(greater value)
//check both value is available or not 
//if not form level validation should be there for both date
function compareDate(fromDate, toDate){
	// check both value is there or not
	// in else it will return true its due to date field validation is done on form level
	if (toDate != '' && fromDate != ''){
		// extract day month and year from both of date
		var fromDay = fromDate.substring(0, 2);
		var toDay = toDate.substring(0, 2);
		var fromMon = eval(fromDate.substring(3, 5)-1);
		var toMon = eval(toDate.substring(3, 5)-1);
		var fromYear = fromDate.substring(6, 10);
		var toYear = toDate.substring(6, 10);

		// convert both date in date object
		var fromDt = new Date(fromYear, fromMon, fromDay);
		var toDt = new Date(toYear, toMon, toDay); 

		// compare both date 
		// if fromDt is greater than toDt return false else true
		if (fromDt > toDt) {
			return false;
		}
		else{
			return true;
		}
	}else{
		return true;
	}
}