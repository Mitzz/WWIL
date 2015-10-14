

if (!SigmaMsg){
	var SigmaMsg={ };
}
if (!SigmaMsg.Grid){
	SigmaMsg.Grid={ };
}

SigmaMsg.Grid.en={
	LOCAL	: "EN",
	ENCODING		: "UTF-8",
	SCROLL_TOP_TEXT	: "Scroll To Top",
	SCROLL_BOTTOM_TEXT	: "Scroll To Bottom",
	NO_DATA : "No Data",
	CHANGE_SKIN : "Change Skin",
	STYLE_NAME_0 : "Sigma Classic",
	STYLE_NAME_1 : "Vista Style",
	MENU_FREEZE_COL : "Lock/Unlock",
	MENU_SHOW_COL : "Hide Columns",
	MENU_GROUP_COL : "Group/Ungroup",

	GOTOPAGE_BUTTON_TEXT: 'Go to specified page',

	FILTERCLEAR_TEXT: "Remove All Filters",
	SORTASC_TEXT	: "Ascend",
	SORTDESC_TEXT	: "Descend",
	SORTDEFAULT_TEXT: "Original",
	ERR_PAGENO		: "Page number must be integer between 1 to #{1} ",
	EXPORT_CONFIRM	: "This operation will affect all records of the whole table.\n\n( Press \"Cancel\" to limit scope to current page.)",
	OVER_MAXEXPORT	: "Number of records exceeds #{1}, the maximum allowed.).",
	PAGE_STATE	: "#{1} - #{2} displayed,  #{3}pages #{4} records totally.",
	SHADOWROW_FAILED: "Failed to retrieve information needed.",
	
	NEARPAGE_TITLE	: "",
	WAITING_MSG : 'This will take a few seconds, please wait...',

	NO_RECORD_UPDATE: "No records need to be updated.",
	UPDATE_CONFIRM	: "Are you sure to save these changes?",
	NO_MODIFIED: "No records changed, operation cancelled.",

	PAGE_BEFORE : 'Page',
	PAGE_AFTER : '',

	PAGESIZE_BEFORE :   '',
	PAGESIZE_AFTER :   'per page',

	RECORD_UNIT : '',
	
	CHECK_ALL : 'Check All',

	COLUMNS_HEADER : 'Header Titles',

	DIAG_TITLE_FILTER : 'Filter Options',
	DIAG_TITLE_CHART  : 'Chart',
	DIAG_NO_FILTER : 'No Filter',

	TEXT_ADD_FILTER	: "Add Filter",
	TEXT_CLEAR_FILTER	: "Remove All",

	TEXT_OK	: "OK",
	TEXT_DEL : "Delete",
	TEXT_CANCEL	: "Cancel",
	TEXT_CLOSE	: "Close"
};

SigmaMsg.Grid['default']=SigmaMsg.Grid.en;


if (!SigmaMsg.Validator){
	SigmaMsg.Validator={ };
}

SigmaMsg.Validator.en={

		'required'	: '{0#This field} is required.',
		'date'		: '{0#This field} must be in proper format ({1#YYYY-MM-DD}).',
		'time'		: '{0#This field} must be in proper format ({1#HH:mm}).',
		'datetime'	: '{0#This field} must be in proper format ({1#YYYY-MM-DD HH:mm}).',
		'email'		: '{0#This field} must be in proper email format.',
		'telephone'	: '{0#This field} must be in proper phone no format.',
		'number'	: '{0} must be a number.',
		'integer'	: '{0} must be an integer.',
		'float'		: '{0} must be integer or decimal.',
		'money'		: '{0} must be integer or decimal with 2 fraction digits.',
		'range'		: '{0} must be between {1} and {2}.',
		'equals'	: '{0} must be same as {1}.',
		'lessthen'	: '{0} must be less than {1}.',
		'idcard'	: '{0} must be in proper ID format',

		'enchar'	: 'Letters, digits or underscore allowed only for {0}',
		'cnchar'	: '{0} must be Chinese charactors',
		'minlength'	: '{0} must contain more than {1} characters.',
		'maxlength'	: '{0} must contain less than {1} characters.'


}

SigmaMsg.Validator['default']=SigmaMsg.Validator.en;

//