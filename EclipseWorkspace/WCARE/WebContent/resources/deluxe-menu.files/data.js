/*
   Deluxe Menu Data File
   Created by Deluxe Tuner v4.1
   http://deluxe-menu.com
*/


// -- Deluxe Tuner Style Names
var itemStylesNames=["Top Item",];
var menuStylesNames=["Top Menu",];
// -- End of Deluxe Tuner Style Names

//--- Common
var menuIdentifier="deluxeMenu";
var isHorizontal=1;
var smColumns=1;
var smOrientation=0;
var dmRTL=0;
var pressedItem=-2;

//--- Dimensions
var menuWidth="1024";
var menuHeight="";
var smWidth="";
var smHeight="";

//--- Positioning
var absolutePos=0;
var posX="";
var posY="";
var subMenuAlign="left";
var subMenuVAlign="top";
var topDX=2;
var topDY=0;
var DX=0;
var DY=0;

//--- Font
var fontStyle=["normal 11px Arial, Helvetica","normal 11px Arial, Helvetica"];
var fontColor=["#082FA5","#082FA5"];
var fontDecoration=["none","none"];
var fontColorDisabled="#AAAAAA";

//--- Appearance
var menuBackColor=" #FFFFFF";
var menuBackImage="";
var menuBackRepeat="repeat";
var menuBorderColor="#999999";
var menuBorderWidth="1px";
var menuBorderStyle="solid";
var smFrameImage="";
var smFrameWidth=0;

//--- Item Appearance
var itemBackColor=["#FFFFFF","#A5BCFA"];
var itemBackImage=["",""];
var itemSlideBack=0;
var itemBorderWidth="0px";
var itemBorderColor=["#FFFFFF","#4C99AB"];
var itemBorderStyle=["solid","solid"];
var itemSpacing=1;
var itemPadding="3px 7px";
var itemAlignTop="left";
var itemAlign="left";
var itemCursor="pointer";

//--- Item Icons
var iconTopWidth="";
var iconTopHeight="";
var iconWidth="";
var iconHeight="";
var arrowImageMain=["resources/deluxe-menu.files/arrow.gif","deluxe-menu.files/arrow_o.gif"];
var arrowWidth=7;
var arrowHeight=7;
var arrowImageSub=["resources/deluxe-menu.files/arrow.gif","deluxe-menu.files/arrow_o.gif"];
var arrowWidthSub=7;
var arrowHeightSub=7;

//--- Separators
var separatorImage="resources/deluxe-menu.files/sep-sub.gif";
var separatorColor="";
var separatorWidth="90%";
var separatorHeight="1px";
var separatorAlignment="center";
var separatorVImage="resources/deluxe-menu.files/sep.gif";
var separatorVColor="";
var separatorVWidth="2px";
var separatorVHeight="35px";
var separatorPadding="0px";

//--- Transitional Effects & Sound
var transparency="100";
var transition=-1;
var transOptions="";
var transDuration=350;
var transDuration2=200;
var shadowLen=0;
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
var blankImage="resources/deluxe-menu.files/blank.gif";

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
    ["itemHeight=35px","itemBackColor=#E4E4E4,#FDFDFD","itemBackImage=resources/deluxe-menu.files/back.gif,resources/deluxe-menu.files/back_o.gif","itemSlideBack=0","fontStyle='bold 13px Arial, MS Sans Serif','bold 13px Arial, MS Sans Serif'","fontColor=#0033CC,#0033CC","fontDecoration=none,none"],
];
var menuStyles = [
    ["menuBorderWidth=0px","menuBorderStyle=solid","menuBorderColor=#999999","itemSpacing=0","itemPadding=9px 7px 10px"],
];
dm_init();