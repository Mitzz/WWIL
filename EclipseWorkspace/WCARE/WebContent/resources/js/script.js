<!--
//rollovers...

if(document.images) {
     
		
		homeoff = new Image();
		homeoff.src = "resources/images/homeoff.gif";
		homeover = new Image();
		homeover.src = "resources/images/homeover.gif";
		
    feedbackoff = new Image();
		feedbackoff.src = "resources/images/feedbackoff.gif";
		feedbackover = new Image();
	  feedbackover.src = "resources/images/feedbackover.gif";
		
		tendersoff = new Image();
		tendersoff.src = "resources/images/tendersoff.gif";
		tendersover = new Image();
	  tendersover.src = "resources/images/tendersover.gif";
		
		contactusoff = new Image();
		contactusoff.src = "resources/images/contactusoff.gif";
		contactusover = new Image();
	       contactusover.src = "resources/images/contactusover.gif";
          
    faqoff = new Image();
		faqoff.src = "resources/images/faqoff.gif";
		faqover = new Image();
	       faqover.src = "resources/images/faqover.gif";
    
          
		registernowoff = new Image();
		registernowoff.src = "resources/images/registernowoff.gif";
		registernowover = new Image();
		registernowover.src = "resources/images/registernowover.gif";
		
    termsofuseoff = new Image();
		termsofuseoff.src = "resources/images/termsofuseoff.gif";
		termsofuseover = new Image();
		termsofuseover.src = "resources/images/termsofuseover.gif";
		  		
     

}

function onoff(imgName,state) {
        if(document.images) {               
		document.images[imgName].src = eval(imgName+state+".src");
        }
}         
//-->