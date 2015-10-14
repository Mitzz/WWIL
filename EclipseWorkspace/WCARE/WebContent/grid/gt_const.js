/*****************************************************************

	SigmaGrid 2.0release  (trial version)
	Copyright (C) 2005-2008 Sigma Soft Ltd. All Rights Reserved. 
	http://www.sigmawidgets.com/

	WARNING: This software program is protected by copyright law 
	and international treaties. Unauthorized reproduction or
	distribution of this program, or any portion of it, may result
	in severe civil and criminal penalties, and will be prosecuted
	to the maximum extent possible under the law.

	FOR DEVELOPMENT AND TRIAL ONLY:
	This software is not free and is licensed to you for development 
	and evaluation only. You are not allowed to distribute 
	or use any parts of this software for any commercial purposes.

*****************************************************************/
var SigmaConst={};
SigmaConst.Grid={
	EMPTY_FUNCTION : function(){},
	DEFAULT_ENCODING : "UTF-8",
	DEFAULT_ECG_ID : "gt",
	ETI_ID : "eti",
	ETI_PAGE_FLAG : "eti_p",
	SORT_PREFIX : "_s_",
	FILTER_PREFIX : "_f_",
	ACTION : "a",
	FILTER_ACTION : "fa",
    CLEAR_ACTION  : "ca",

	PAGEFIELD_SUFFIX : "_p",
	EXPORT_IFRAME_SUFFIX : "_ecs_export_iframe",
	SHADOW_ROW : "_shadowRow",
	HIDE_HEADER_ROW : "_hideListRow",

	DEFALUT_ADD_TEMPLATE : "add_template",

	COL_T_CLASSNAME : "gt-col-",
	SKIN_CLASSNAME_PREFIX : "gt-skin-",

	SKIN_PATH  : "/skin/{SKING_NAME}/ecgrid.css",
	SPACE_CHAR : "&#160;",
	AJAX_ZONE_BEGIN : "_begin_ ",
	AJAX_ZONE_END : " _end_",
	AJAX_ZONE_PREFIX : "<!-- ECS_AJAX_ZONE_PREFIX_",
	AJAX_ZONE_SUFFIX : "_ECS_AJAX_ZONE_SUFFIX -->",
	CELL_PADDING_H : 4,
	SCROLLBAR_WIDTH :18,
	MIN_COLWIDTH : 40,
	AJAX_HEADER :['isAjaxRequest','true'],
	ROW_HIGHLIGHT_CLASS : "highlight",
	ROW_SELECTLIGHT_CLASS : "selectlight",
	FIX_LEFT : 1,
	ECGRID_PATH		:"/ecgrid"
	

};

SigmaConst.Key = {

	BACKSPACE : 8,
	TAB : 9,
	ENTER : 13,
	SHIFT : 16,
	CTRL : 17,
	PAUSE : 19,
	CAPSLOCK : 20,
	ESC : 27,

	SPACE : 33,
	PAGEUP : 33,
	PAGEDOWN : 34,
	END : 35,
	HOME : 36,
	LEFT : 37,
	UP : 38,
	RIGHT : 39,
	DOWN : 40,
	INSERT : 45,
	DELETE : 46,

	WIN : 91,
	WIN_R : 92,
	MENU : 93,

	F1 : 112,
	F2 : 113,
	F3 : 114,
	F4 : 115,
	F5 : 116,
	F6 : 117,
	F7 : 118,
	F8 : 119,
	F9 : 120,
	F10 : 121,
	F11 : 122,
	F12 : 123,
	NUMLOCK : 144,
	SCROLLLOCK : 145

};


//

