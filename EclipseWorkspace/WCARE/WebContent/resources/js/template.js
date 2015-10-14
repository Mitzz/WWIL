var SCROLL1_DATA = {
	// Use <blink> and </blink> tags to enclose blinking fragment, 
	// <pause time=ms> to pause scrolling for a time of ms miliseconds
	'message' : " <pause time=1>Scrolling <blink>Statusbar</blink> JavaScript <pause time=3>Scrolling Title <blink>JavaScript</blink>",
	// scrolling speed, milliseconds per char. use negative value to reverse direction
	'speed' : 200,
	// blinking parameters: [time to show for, time to hide for] or
	// if scalar both time to show for and time to hide for
	'blink' : [500, 500],
	// wheather the scrolling goes in window title bar
	'intitle' : true
}

var SCROLL2_DATA = {
	'message' : " Scrolling Statusbar JavaScript Scrolling Title <blink>JavaScript</blink>",
	'speed' : 200,
	'blink' : 500,
	'intitle' : false
}
