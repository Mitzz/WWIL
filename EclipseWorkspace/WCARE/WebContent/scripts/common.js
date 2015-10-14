// JavaScript Document
$(document).ready(function(){
    //$('.mwh').hover(function() {
//		$(".mwh-layer").stop(true,true).slideDown("fast");
//		$(this).css("background-position","0 -35px");
//	},function(){
//		
//		$(".mwh-layer").slideUp();		
//				$(this).css("background-position","0 0");
//		}); 

// Clearfix
	$('<style type="text/css">.clearfix:after {visibility: hidden;display: block;font-size: 0;content: " ";clear: both;height: 0;}* html .clearfix { zoom: 1; }*:first-child+html .clearfix { zoom: 1; }</style>').appendTo("head");
	
	var winHeight = $(window).height();
	var winWidth = $(window).width();	
	//$("body").css('min-height',winHeight);	
	function innerPage100(){
		$(".content").css('min-height',"");
		$(".container").css('min-height',"");
		$(".whitebox").css('padding-top',"");
		var winHeight = $(window).height();
		var contentHeight = winHeight - 240;
		var containerHeight = winHeight - 274;
		var whiteBoxPadTop = winHeight - 730;
		$(".content").css('min-height',contentHeight);
		$(".container").css('min-height',containerHeight);
		$(".whitebox").css('padding-top',whiteBoxPadTop);
	}
	innerPage100();
	$(window).resize(innerPage100);
	 var scrollid;
    //$(".subNav").hide();
    highlight();
	// Scroll Top

	$('a[href=#top]').click(function(){
        $('html, body').animate({scrollTop:0}, 'slow');
        return false;
		 });		
	var scrollid; 
			
	$(".subNav").hide();
    $('ul.navmenu li a').hover(function() {
        scrollid = $(this).attr("rel");
        if (! ($(this).hasClass('selected'))) {
            $(".subNav").stop(true,true).slideUp();
            $("ul.navmenu li a").removeClass('selected');			
            $(this).addClass('selected');
			if(!($(this).hasClass('selected') && $(this).hasClass('selectedpage')))
			$("ul.navmenu li a.selectedpage").css("background","none").css("color","#fff");
            $('#' + scrollid).stop(true,true).slideDown();
        }
    },	
	function() {
		if(scrollid != "contact")
		{
        $('div.subNav').hover(function() {
            $('#' + scrollid).show();
			if(!($('#' + (scrollid + '_menu')).hasClass('selected') && $('#' + (scrollid + '_menu')).hasClass('selectedpage')))
			$("ul.navmenu li a.selectedpage").css("background","none").css("color","#fff");
        },				
	function() {
		$("ul.navmenu li a.selectedpage").css("background-color","#fff").css("color","#000");
			$('.subNav').slideUp();
			$("ul.navmenu li a").removeClass('selected');  			
        });
		}
		else
		$("ul.navmenu li a").removeClass('selected');
		$("ul.navmenu li a.selectedpage").css("background-color","#fff").css("color","#000");
    });	
    $('div.header,.containerIn,.breadcrumb,.content').hover(function() {
		$("ul.navmenu li a.selectedpage").css("background-color","#fff").css("color","#000");
        $('.subNav').stop(true,true).slideUp();
        $("ul.navmenu li a").removeClass('selected');
    });
	
	$(".increae_font").click(function() {									  
	 var currentFontSize = $('.containerIn').css('font-size');
		if(currentFontSize != "14px")
		{
			var currentFontSizeNum = parseFloat(currentFontSize, 10);
			var newFontSize = currentFontSizeNum+2;
			$('.containerIn').css('font-size', newFontSize);
			$('.containerIn').css('line-height',"20px");
			$(this).addClass("inactivefont");
			$(".decrease_font").removeClass("inactivefont");
			return false;
		}	
	});	
	$(".decrease_font").click(function() {									  
		var currentFontSize = $('.containerIn').css('font-size');
		if(currentFontSize != "12px")
		{
    var currentFontSizeNum = parseFloat(currentFontSize, 10);
    var newFontSize = currentFontSizeNum-2;
    $('.containerIn').css('font-size', newFontSize);
	$('.containerIn').css('line-height',"18px");
	$(this).addClass("inactivefont");
	$(".increae_font").removeClass("inactivefont");
    return false;			
		}	
	});	
	$(".decrease_font").addClass("inactivefont");
	// contactus tab								  
	$(".contactTabCon").hide();
	$("ul.contactTab li:first").addClass("active").show();
	$(".contactTabCon:first").show();
	$("ul.contactTab li").click(function() {
		if($(this).hasClass("active")){
			return false;
		}else{
			$("ul.contactTab li").removeClass("active");
			$(this).addClass("active");
			$(".contactTabCon").slideUp();
	
			var activeTab = $(this).find("a").attr("href");
			$(activeTab).show();
			return false;
			}
	});
	
	$("div.uniqueTabCont:first").show();
	$("ul.uniqueTab li:first a.addclass").addClass("active");
	$("ul.uniqueTab li a").hover(function() {
	//$('html, body').animate({scrollTop:0}, 'slow');
	$("ul.uniqueTab li a.addclass").removeClass("active");
	$("div.uniqueTabCont").stop(true,true).hide(500);
	$(this).parent().find("div.uniqueTabCont").stop(true,true).show(500) //Drop down the subnav on click
	$(this).parent().find("a.addclass").addClass("active");	
	$(this).parent().hover(function() {
	}, function(){	
		$(this).parent().find("div.uniqueTabCont").hide(500); //When the mouse hovers out of the subnav, move it back up
		$(this).parent().find("a.addclass").removeClass("active");
	});
	})
	$(".uniqueTab").mouseleave(function(){
		$("div.uniqueTabCont").stop(true,true).hide(500);
		$("div.uniqueTabCont:first").stop(true,true).show(500);
		$("ul.uniqueTab li:first a.addclass").addClass("active");
	});
	 
	
	
	
});
function highlight() {
	if($('div').hasClass('who_we'))
	  scroll("who_we");    
	else if ($('div').hasClass('what_we'))
      scroll("what_we");
	else if ($('div').hasClass('why_we'))
      scroll("why_we");
	else if ($('div').hasClass('partner'))
      scroll("partner");	  
	else if ($('div').hasClass('careers'))
      scroll("careers");	
	else if ($('div').hasClass('contactus'))
      menuhigh("contact_menu");	
	else if ($('div').hasClass('homepage'))
      menuhigh("home_menu");
}
function scroll(scrollid) {	
    $('#' + scrollid + '_menu').addClass("selectedpage");
    var sPath = window.location.pathname;
    var sPage = sPath.substring(sPath.lastIndexOf('/') + 1);
   // $('#slide-whole .subNav').each(function() {
           // var menuid = $(this).attr('id');			
            $('#' + scrollid).find('a').each(function() {
                if (sPage == $(this).attr('href')) {
                    $(this).parent('li').addClass("selected");	
					$(this).removeAttr("href");
                }
            });
    //}); 
}
function menuhigh(menuid){
	$('#' + menuid ).addClass("selectedpage");
}


// indexBg Img
function setButterfly(){
	var windowHeight = $(window).height();
	var windowWidth = $(window).width();
	var orgImageWidth = 1190;
	var orgImageHeight = 750;
	var resizedImageHeight = Math.round(orgImageHeight*(windowWidth/orgImageWidth));
	var resizedImageWidth = Math.round(orgImageWidth*(windowHeight/orgImageHeight));
	var bgImage = $("div#bgWrapper img.bgImage");
	$(bgImage).css({
		left : '50%',
		top : '50%'
	});
	if (resizedImageHeight<windowHeight){
		var bgImageMarginTop = Math.round(windowHeight/2);
		var bgImageMarginLeft = Math.round(resizedImageWidth/2);
		$(bgImage).css({
			width : resizedImageWidth,
			height : windowHeight,
			marginLeft : '-'+bgImageMarginLeft+'px',
			marginTop : '-'+bgImageMarginTop+'px'
		});
	}else{
		var bgImageMarginTop = Math.round(resizedImageHeight/2);
		var bgImageMarginLeft = Math.round(windowWidth/2);
		$(bgImage).css({
			width: windowWidth,
			height : resizedImageHeight,
			marginLeft : '-'+bgImageMarginLeft+'px',
			marginTop : '-'+bgImageMarginTop+'px'
		});
	}
}
var i = 1;
function fadeImage() {
	var fadeImageCount = $('div#bgWrapper img.bgImage').size();
	if (i == fadeImageCount) {
		i = 0;
	}
	var currentImage = $('div#bgWrapper img.bgImage').eq(i);
	var currentText = $('div.categoryLink .whiteboxBg div').eq(i);
	$('div#bgWrapper img.bgImage:visible').fadeOut(1000);
	$('div.categoryLink .whiteboxBg div:visible').fadeOut(0);
	$(currentImage).fadeIn(1000);
	$(currentText).fadeIn(1000);
	i++;
}
$(document).ready(function(){
	setButterfly();
	$("div#bgWrapper img.bgImage:first").show();
	$("div.categoryLink .whiteboxBg div:first").show();
	setInterval("fadeImage(i)",10000);
});
$(window).resize(function(){
	setButterfly();
});
	
