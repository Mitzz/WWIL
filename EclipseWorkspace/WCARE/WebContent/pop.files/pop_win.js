/*
   Deluxe Menu Data File
   Created by Deluxe Tuner v3.2
   http://deluxe-menu.com
*/



	deluxePopupWindow.addSkin('pop.files/jaw_skin_macos');
	deluxePopupWindow.addEvent(window, function(){
		var win=deluxePopupWindow.make(
				'win',
				'<div style=\"text-align:left;\"><b><span class="jaw_span">&nbsp;Dear Sir,<span><br><br><div>&nbsp;With effect from April 01 2010, the Internal Grid is not the part of Machine Availability.Any query in this connection may please be sent to manoj.tiwari@windworldindia.com',
				'',
				'width=250,height=200,scrollbars=yes,resizable=yes,minimizable=no,fullscreen=no,closable=yes,middle,center,,fade-effect,opacity=1,floatable=yes',
				'macos',
				'text');
		deluxePopupWindow.attachToEvent(win,'openAfter=,,,,,')
	}, 'load')