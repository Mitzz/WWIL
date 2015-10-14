// Tigra Status Bar Scroller
// URL: http://www.softcomplex.com/products/tigra_status_bar_scroller/
// Version: 1.0
// Date: 10/30/2004 (mm/dd/yyyy)
// Note: Permission given to use this script in ANY kind of applications if
//    header lines are left unchanged.

var TSA = [];

function TSBScroller(data) {
	if (!data) return;
	var len_buf = 0, i = 0, arr = [], pauses = [];
	var re = /<pause time=([0-9]+)>/g;
	var re2 = /<blink>([^<]+)<\/blink>/g;
	this.str = '';
	this.clear_str = ''
	this.place = Boolean(data.intitle);
	this.speed = data.speed ? Math.abs(data.speed) : 200;
	if (!data.blink) data.blink = 500;
	this.blink_show = data.blink[0] ? data.blink[0] : data.blink;
	this.blink_hide = data.blink[1] ? data.blink[1] : data.blink;
	this.left2right = data.speed > 0;
	this.is_show = true;
	this.msg = data.message ? data.message : 'Tigra Status Bar Scroller from SoftComplex';
	this.dropScroller = TSBSDropScroller;
	this.pauses = [];
	this.blink = [];
	while ((arr = re.exec(this.msg)) != null) {
		this.pauses[arr.index - len_buf - (this.left2right ? 0 : 1)] = arr;
		len_buf = arr[0].length;
	}
	for (i in this.pauses) this.msg = this.msg.replace(this.pauses[i][0], "");
	len_buf = 0;
	while ((arr = re2.exec(this.msg)) != null) {
		this.blink[arr.index - len_buf - (this.left2right ? 0 : 1)] = [arr[0], arr[1], arr[1].length];
		len_buf = arr[0].length - arr[1].length;
		for (i in this.pauses) {
			if (i > arr.index) {
				pauses[i - len_buf] = [this.pauses[i][0], this.pauses[i][1]];
				break;
			}
			else 
				pauses[i] = [this.pauses[i][0], this.pauses[i][1]];
		}
		this.pauses = pauses;
	}
	for (i in this.blink)
		this.msg = this.msg.replace(this.blink[i][0], this.blink[i][1]);
	this.pos = this.left2right ? 0 : this.msg.length;
	this.ScrollStart = TSBSScrollStart;
	this.is_scroll = true;
	this.id = TSA.length;
	TSA[TSA.length] = this;
	this.is_pause = false;
	this.time = new Date();
	this.blink_start = this.time * 1;
	this.scroll_start = this.time * 1;
	this.ScrollStart();
}

function TSBSDropScroller() {
	clearTimeout(this.ScrollInterval);
	if (this.place) document.title = '';
	else status = '';
	TSA[this.id] = null;
}

function TSBSScrollStart(blink) {
	var n, blink_text = str = '', re, blink_txt_start = false, blink_txt_pos = 0;
	this.time = new Date();
	if (typeof(this.pauses[this.pos]) == 'object' && this.pauses[this.pos].length == 2 && !this.is_pause) {
		this.pause = this.pauses[this.pos][1] * 1000;
		this.is_pause = true;
		this.scroll_start = this.time * 1;
	}
	if (this.is_show) {
		if ((this.time * 1 - this.blink_start) < this.blink_show)
			blink = 0
		else {
			blink = 1
			this.is_show = false;
			this.blink_start = this.time * 1;
		}
	}
	else {
		if ((this.time * 1 - this.blink_start) < this.blink_hide)
			blink = 1;
		else {
			blink = 0;
			this.is_show = true;
			this.blink_start = this.time * 1;
		}
	}
	this.str = this.msg.substring(this.pos, this.msg.length) + this.msg.substring(0, this.pos);
	if ((this.time * 1 - this.scroll_start) < this.pause) {
		if (blink) {
			str = '';
			for (i = 0; i < this.msg.length; i ++) {
				if (typeof(this.blink[i]) == 'object') {
					blink_txt_start = true;
					blink_txt_pos = i;
				}
				if (blink_txt_start && (i - blink_txt_pos) > this.blink[blink_txt_pos][2])
					blink_txt_start = false;
					str += blink_txt_start ? "_" : this.msg.charAt(i);
			}
			this.str = str.substring(this.pos, this.msg.length) + str.substring(0, this.pos);
		}
		if (this.place)
			document.title = this.str;
		else status = this.str;
		this.ScrollInterval = setTimeout("TSA[" + this.id + "].ScrollStart()", this.speed);
		return;
	}
	this.is_pause = false;
	if (blink) {
		str = '';
		for (i = 0; i < this.msg.length; i ++) {
			if (typeof(this.blink[i]) == 'object') {
				blink_txt_start = true;
				blink_txt_pos = i;
			}
			if (blink_txt_start && (i - blink_txt_pos) > this.blink[blink_txt_pos][2])
				blink_txt_start = false;
				str += blink_txt_start ? "_" : this.msg.charAt(i);
		}
		this.str = str.substring(this.pos, this.msg.length) + str.substring(0, this.pos);
	}
	if (this.left2right) this.pos ++;
	else this.pos --;
	if (this.pos > this.msg.length) this.pos = 0;
	if (this.pos < 0) this.pos = this.msg.length;
	this.ScrollInterval = setTimeout("TSA[" + this.id + "].ScrollStart()", this.speed);
	if (this.place) document.title = this.str;
	else status = this.str;
}