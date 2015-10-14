/* author: Philippe Corbes */
/* email: philippe.corbes@laposte.net */
/* creation date: 9/25/00 */
/* 18/09/2005 Ajout d'une zone blanche avant et apres le code */

<!-- // 
function PrintPage()
{
window.print();
}
function Code39() {
// Parameters: type:       barecode type ['CODE39','CODE39_CHECKSUM',CODE39_EXTENDED,CODE39_EXTENDED_CHECKSUM]
//             barcode:    value to translate
//             withtext:   boolean, True to add text
//             xsize:      size of a line
//             ysize:      the Y size
//             blackImage: a GIF image to build the barecode
//             whiteImage: a GIF image to build the barecode
//             xratio:     the ration between large and small bar
//             xinter:     the space between two digits 
  var argv = Code39.arguments;
  var argc = Code39.arguments.length;
  // Parameters
  this.colors   = new Array('../resources/images/white.gif','../resources/images/black.gif');
  this.type     = (argc > 0) ? argv[0] : 'CODE39';
  this.code     = (argc > 1) ? argv[1] : 'CODE39';
  this.withtext = (argc > 2) ? argv[2] : true;
  this.xsize    = (argc > 3) ? argv[3] : 1;
  this.ysize    = (argc > 4) ? argv[4] : 50;
  if (argc > 5) { this.colors[1] = argv[5]; } 
  if (argc > 6) { this.colors[0] = argv[6]; } 
  this.xratio   = (argc > 7) ? argv[7] : 3;
  this.xinter   = (argc > 8) ? argv[8] : 1;
  // Constants to build the image
  this.value  = new String('0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. $/+%*')
  this.digit  = new Array('nbnBNbNbnX', 'NbnBnbnbNX', 'nbNBnbnbNX', 'NbNBnbnbnX', 'nbnBNbnbNX', 
                          'NbnBNbnbnX', 'nbNBNbnbnX', 'nbnBnbNbNX', 'NbnBnbNbnX', 'nbNBnbNbnX', 
                          'NbnbnBnbNX', 'nbNbnBnbNX', 'NbNbnBnbnX', 'nbnbNBnbNX', 'NbnbNBnbnX', 
                          'nbNbNBnbnX', 'nbnbnBNbNX', 'NbnbnBNbnX', 'nbNbnBNbnX', 'nbnbNBNbnX', 
                          'NbnbnbnBNX', 'nbNbnbnBNX', 'NbNbnbnBnX', 'nbnbNbnBNX', 'NbnbNbnBnX', 
                          'nbNbNbnBnX', 'nbnbnbNBNX', 'NbnbnbNBnX', 'nbNbnbNBnX', 'nbnbNbNBnX', 
                          'NBnbnbnbNX', 'nBNbnbnbNX', 'NBNbnbnbnX', 'nBnbNbnbNX', 'NBnbNbnbnX', 
                          'nBNbNbnbnX', 'nBnbnbNbNX', 'NBnbnbNbnX', 'nBNbnbNbnX', 'nBnBnBnbnX', 
                          'nBnBnbnBnX', 'nBnbnBnBnX', 'nbnBnBnBnX', 'nBnbNbNbnX' )
  this.valueX = new String('\x00\x01\x02\x03\x04\x05\x06\x07\x08\x09\x0a\x0b\x0c\x0d\x0e\x0f'
                          +'\x10\x11\x12\x13\x14\x15\x16\x17\x18\x19\x1a\x1b\x1c\x1d\x1e\x1f'
                          +'\x20\x21\x22\x23\x24\x25\x26\x27\x28\x29\x2a\x2b\x2c\x2d\x2e\x2f'
                          +'0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\x5c]^_'
                          +'`abcdefghijklmnopqrstuvwxyz{|}~\x7f')
  this.codeX = Array('%U','$A','$B','$C','$D','$E','$F','$G','$H','$I','$J','$K','$L','$M','$N','$O',
                     '$P','$Q','$R','$S','$T','$U','$V','$W','$X','$Y','$Z','%A','%B','%C','%D','%E',
                     ' ', '/A','/B','/C','$', '%', '/F','/G','/H','/I','/J','+', '/L','-', '.', '/O',
                     '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '/Z','%F','%G','%H','%I','%J',
                     '%V','A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O',
                     'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '%K','%L','%M','%N','%O',
                     '%W','+A','+B','+C','+D','+E','+F','+G','+H','+I','+J','+K','+L','+M','+N','+O',
                     '+P','+Q','+R','+S','+T','+U','+V','+W','+X','+Y','+Z','%P','%Q','%R','%S','%T')
  // Function(s)
  this.draw   = DrawCode39;
}

function DrawCode39(){
	// Parameters: barcode:    the code to translate
	var argv = this.draw.arguments;
	var argc = this.draw.arguments.length;
	if (argc > 0) { this.code = argv[0]; }

	var astr;
	var codeok = (this.code != ''); 
	if (codeok == true) codeok = ((this.type == 'CODE39') || (this.type == 'CODE39_CHECKSUM')
			|| (this.type == 'CODE39_EXTENDED') || (this.type == 'CODE39_EXTENDED_CHECKSUM'))
	if (codeok == false) {
		astr = this.type+' ??';
	} else {
		var i;
		var thecode = '';
		var codestr = '';
		// Transform Extended Code39
		if ((this.type == 'CODE39_EXTENDED') || (this.type == 'CODE39_EXTENDED_CHECKSUM')) {
			for (i = 0; i < this.code.length; i++) {
				codestr += this.code.charAt(i); 
				thecode += this.codeX[this.valueX.indexOf(this.code.charAt(i))];
			} 
		} else {
			for (i = 0; i < this.code.length; i++) { 
				if (this.value.indexOf(this.code.charAt(i)) != -1) thecode += this.code.charAt(i);
			}
			codestr = thecode;
		}
		this.code = codestr;

		// Checksum
		var checkstr = ''; 
		if ((this.type == 'CODE39_CHECKSUM') || (this.type == 'CODE39_EXTENDED_CHECKSUM')){
			var check = 0;
			for (i = 0; i < thecode.length; i++) { 
				check += this.value.indexOf(thecode.charAt(i)); 
			}
			checkstr = this.value.charAt(check % 43);
		}
		// Add Begin and End
		thecode = '*' + thecode + checkstr +'*';
		codestr = '*' + codestr + checkstr +'*';

		var bcstr = "";
		for (i = 0; i < thecode.length; i++) bcstr += this.digit[this.value.indexOf(thecode.charAt(i))];

		// Drawing table
		astr = '<table border=0 cellspacing=0 cellpadding=0 bordercolor=white backcolor=blue>\n';
		if (this.withtext) { astr += '<caption align=bottom>' + codestr + '</caption>\n'; }
		astr += '<tr>';
		astr += '<td><img src="'+this.colors[0]+'" width="'+10*this.xsize+'" height="'+this.ysize+'"></td>\n';
		for (i = 0; i < bcstr.length; i++) {
			if (bcstr.charAt(i)== "X") { astr += '<td><img src="' + this.colors[0] + '" height="' + this.ysize + '" width="' + this.xinter + '"></td>\n'; }
      			if (bcstr.charAt(i)== "b") { astr += '<td><img src="' + this.colors[0] + '" height="' + this.ysize + '" width="' + this.xsize + '"></td>\n'; }
	  		if (bcstr.charAt(i)== "B") { astr += '<td><img src="' + this.colors[0] + '" height="' + this.ysize + '" width="' + this.xsize*this.xratio + '"></td>\n'; }
      			if (bcstr.charAt(i)== "n") { astr += '<td><img src="' + this.colors[1] + '" height="' + this.ysize + '" width="' + this.xsize + '"></td>\n'; }
	  		if (bcstr.charAt(i)== "N") { astr += '<td><img src="' + this.colors[1] + '" height="' + this.ysize + '" width="' + this.xsize*this.xratio + '"></td>\n'; }
		}
		astr += '<td><img src="'+this.colors[0]+'" width="'+10*this.xsize+'" height="'+this.ysize+'"></td>\n';
		astr += '</tr></table>';
//   return '<xmp>#' +astr+'#</xmp>';    // debug
		return(astr);
	}
}
// -->
