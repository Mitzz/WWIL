/*
   Deluxe Menu Data File
   Created by Deluxe Tuner v4.1
   http://deluxe-menu.com
*/


// -- Deluxe Tuner Style Names
var itemStylesNames=["Top Item",];
var menuStylesNames=["Submenu",];
// -- End of Deluxe Tuner Style Names

//--- Common
var menuIdentifier="deluxeMenu";
var isHorizontal=1;
var smColumns=1;
var smOrientation=0;
var dmRTL=0;
var pressedItem=-2;

//--- Dimensions
var menuWidth="";
var menuHeight="";
var smWidth="";
var smHeight="";

//--- Positioning
var absolutePos=0;
var posX="";
var posY="";
var subMenuAlign="left";
var subMenuVAlign="top";
var topDX=0;
var topDY=0;
var DX=0;
var DY=0;

//--- Font
var fontStyle=["normal 11px Trebuchet MS, Tahoma","normal 11px Trebuchet MS, Tahoma"];
var fontColor=["#112D6F","#9DF8FF"];
var fontDecoration=["none","none"];
var fontColorDisabled="#AAAAAA";

//--- Appearance
var menuBackColor="#FFFFFF";
var menuBackImage="";
var menuBackRepeat="repeat-x";
var menuBorderColor="";
var menuBorderWidth="0px";
var menuBorderStyle="solid";
var smFrameImage="";
var smFrameWidth=0;

//--- Item Appearance
var itemBackColor=["#B4CDE5","#437CBC"];
var itemBackImage=["",""];
var itemSlideBack=0;
var itemBorderWidth="1px";
var itemBorderColor=["#8DAEFA","#84A7F9"];
var itemBorderStyle=["solid","ridge"];
var itemSpacing=0;
var itemPadding="0px 15px 0px 10px";
var itemAlignTop="left";
var itemAlign="left";
var itemCursor="pointer";

//--- Item Icons
var iconTopWidth="";
var iconTopHeight="";
var iconWidth="";
var iconHeight="";
var arrowImageMain=["resources/tabmenu.files/arr_white.gif","resources/tabmenu.files/arr_white.gif"];
var arrowWidth=7;
var arrowHeight=7;
var arrowImageSub=["resources/tabmenu.files/arr_black.gif","resources/tabmenu.files/arrv_white.gif"];
var arrowWidthSub=0;
var arrowHeightSub=0;

//--- Separators
var separatorImage="";
var separatorColor="";
var separatorWidth="100%";
var separatorHeight="3px";
var separatorAlignment="left";
var separatorVImage="";
var separatorVColor="";
var separatorVWidth="3px";
var separatorVHeight="100%";
var separatorPadding="0px";

//--- Transitional Effects & Sound
var transparency="85";
var transition=102;
var transOptions="";
var transDuration=300;
var transDuration2=200;
var shadowLen=3;
var shadowColor="#B1B1B1";
var shadowTop=0;
var onOverSnd="";
var onClickSnd="";

//--- CSS Support (CSS-based Menu)
var cssSubmenu="";
var cssItem=["",""];
var cssItemText=["",""];

//--- Floatable Menu
var floatable=0;
var floatIterations=6;
var floatableX=1;
var floatableY=1;
var floatableDX=15;
var floatableDY=15;

//--- AJAX-like Technology
var dmAJAX=0;
var ajaxReload=0;

//--- Advanced Features
var saveNavigationPath=1;
var noWrap=1;
var showByClick=0;
var smShowPause=200;
var smHideOnClick=1;
var smHidePause=1000;
var smSmartScroll=1;
var topSmartScroll=0;
var dm_writeAll=1;
var dmObjectsCheck=0;
var useIFRAME=0;
var popupMode=0;
var dynamic=0;
var keystrokes=0;
var dm_focus=1;
var dm_actKey=113;
var pathPrefix_img="";
var pathPrefix_link="";

//--- Movable Menu
var movable=0;
var moveWidth=12;
var moveHeight=20;
var moveColor="#DECA9A";
var moveImage="";
var moveCursor="move";
var smMovable=0;
var closeBtnW=15;
var closeBtnH=15;
var closeBtn="";
var blankImage="resources/tabmenu.files/blank.gif";

//--- Deprecated Features
var beforeItemImage=["",""];
var afterItemImage=["",""];
var beforeItemImageW="";
var afterItemImageW="";
var beforeItemImageH="";
var afterItemImageH="";
var statusString="";
var itemTarget="";
var dmSearch=0;

var itemStyles = [
    ["itemHeight=25px","itemBackColor=transparent,transparent","itemBackImage=resources/tabmenu.files/styleOp_normal.gif,resources/tabmenu.files/styleOp_over.gif","itemSlideBack=5","itemBorderWidth=0px","fontStyle='normal 11px Tahoma','normal 11px Tahoma'","fontColor=#FFFFFF,#B7FAFF"],
];
var menuStyles = [
    ["menuBackColor=#FFFFFF","menuBorderWidth=1px","menuBorderStyle=solid","menuBorderColor=#1C49B3","itemSpacing=4","itemPadding=1px 5px 1px 10px"],
];
dm_init();