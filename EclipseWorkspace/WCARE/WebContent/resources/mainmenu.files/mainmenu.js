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
var menuIdentifier="cssMenu";
var ie6Support=1;
var isHorizontal=1;
var smColumns=1;
var smOrientation=1;
var dmRTL=0;
var pressedItem=-2;

//--- Dimensions
var menuWidth="1024px";
var menuHeight="";
var smWidth="";
var smHeight="";

//--- Positioning
var absolutePos=1;
var posX="100px";
var posY="100px";
var subMenuAlign="left";
var subMenuVAlign="top";

//--- Font
var fontStyle=["normal 11px Trebuchet MS, Tahoma","normal 11px Trebuchet MS, Tahoma"];
var fontColor=["#191919","#191919"];
var fontDecoration=["none","none"];
var fontColorDisabled="#AAAAAA";

//--- Appearance
var menuBackColor="#89C6E7";
var menuBackImage="";
var menuBackRepeat="repeat-x";
var menuBorderColor="#13445E #FFFFFF";
var menuBorderWidth="0px";
var menuBorderStyle="solid";
var menuBorderRadius="";
var smFrameImage="";
var smFrameWidth=0;

//--- Item Appearance
var itemBackColor=["#89C6E7","#8080FF"];
var itemBackImage=["",""];
var itemSlideBack=1;
var itemShadow="";
var itemBorderWidth="1px";
var itemBorderColor=["#89C6E7","#15546C  #FFFFFF"];
var itemBorderStyle=["solid","solid"];
var itemBorderRadius="";
var itemSpacing=0;
var itemPadding="3px 10px 3px 5px";
var itemAlignTop="left";
var itemAlign="left";
var itemCursor="pointer";

//--- Item Icons
var iconTopWidth="";
var iconTopHeight="";
var iconWidth="";
var iconHeight="";
var arrowImageMain=["resources/mainmenu.files/arrowmain.gif","resources/mainmenu.files/arrowmaino.gif"];
var arrowWidth=8;
var arrowImageSub=["resources/mainmenu.files/arrowsub.gif","resources/mainmenu.files/arrowsubo.gif"];
var arrowWidthSub=0;

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

//--- Transitional Effects & Filters
var transparency="80";
var shadowLen=0;
var shadowColor="#B1B1B1";
var shadowTop=0;

//--- CSS Support (CSS-based Menu)
var cssSubmenu="";
var cssItem=["",""];
var cssItemText=["",""];

//--- Floatable Menu
var floatable=0;

//--- Advanced Features
var saveNavigationPath=1;
var itemTarget="";
var pathPrefix_img="";
var pathPrefix_link="";

var itemStyles = [
    ["itemHeight=31px","itemBackColor=#FFFFFF,#FFFFFF","itemBackImage=resources/mainmenu.files/styleIE7_normal.gif,resources/mainmenu.files/styleIE7_over.gif","itemSlideBack=5","itemBorderWidth=0px","fontStyle='normal 11px Trebuchet MS, Tahoma','normal 11px Trebuchet MS, Tahoma'"],
];
var menuStyles = [
    ["menuBorderWidth=1px","menuBorderStyle=solid","menuBorderColor=#13445E #FFFFFF","itemSpacing=0","itemPadding=3px"],
];

var menuItems = [

    ["Home","testlink.html", "", "", "", "", "0", "", "", "", "", ],
    ["Product Info","", "", "", "", "", "0", "", "", "", "", ],
        ["|Features","testlink.html", "", "", "", "", "", "0", "", "", "", ],
        ["|Installation","", "", "", "", "", "", "", "", "", "", ],
            ["||Description of Files","testlink.html", "", "", "", "", "", "0", "", "", "", ],
            ["||How To Setup","testlink.html", "", "", "", "", "", "", "", "", "", ],
        ["|Parameters Info","testlink.html", "", "", "", "", "", "", "", "", "", ],
        ["|Dynamic Functions","testlink.html", "", "", "", "", "", "", "", "", "", ],
        ["|Supported Browsers","", "", "", "", "", "", "", "", "", "", ],
            ["||Windows OS","", "", "", "", "", "", "0", "", "", "", ],
            ["||Internet Explorer","", "", "", "", "", "", "", "", "", "", ],
            ["||Firefox","", "", "", "", "", "", "", "", "", "", ],
            ["||Mozilla","", "", "", "", "", "", "", "", "", "", ],
            ["||Netscape","", "", "", "", "", "", "", "", "", "", ],
            ["||Opera","", "", "", "", "", "", "", "", "", "", ],
            ["||MAC OS","", "", "", "", "", "", "", "", "", "", ],
            ["||Firefox","", "", "", "", "", "", "", "", "", "", ],
            ["||Safari","", "", "", "", "", "", "", "", "", "", ],
            ["||Internet Explorer","", "", "", "", "", "", "", "", "", "", ],
            ["||Unix/Linux OS","", "", "", "", "", "", "", "", "", "", ],
            ["||Firefox","", "", "", "", "", "", "", "", "", "", ],
            ["||Konqueror","", "", "", "", "", "", "", "", "", "", ],
    ["Samples","", "", "", "", "", "0", "", "", "", "", ],
        ["|Sample 1","testlink.html", "", "", "", "", "", "0", "", "", "", ],
        ["|Sample 2 is Disabled","testlink.html", "", "", "", "_", "", "", "", "", "", ],
        ["|Sample 3","testlink.html", "", "", "", "", "", "", "", "", "", ],
        ["|Sample 4","testlink.html", "", "", "", "", "", "", "", "", "", ],
        ["|Sample 5","testlink.html", "", "", "", "", "", "", "", "", "", ],
        ["|Sample 6","testlink.html", "", "", "", "", "", "", "", "", "", ],
        ["|Sample 7","testlink.html", "", "", "", "", "", "", "", "", "", ],
        ["|Sample 8","testlink.html", "", "", "", "", "", "", "", "", "", ],
        ["|Sample 9","testlink.html", "", "", "", "", "", "", "", "", "", ],
    ["Purchase","http://deluxe-menu.com/order-purchase.html", "", "", "", "_blank", "0", "", "", "", "", ],
    ["Contact Us","testlink.htm", "", "", "", "", "0", "", "", "", "", ],
];

