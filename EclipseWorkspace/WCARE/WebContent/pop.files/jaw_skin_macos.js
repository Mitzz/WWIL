/*
 name:macos
 theme:Mac OS
 */
 
var jaw_skin_macos = '<table width="100%" height="100%">'+
        '<tr>'+
          '<td class="border00"><div></div></td>'+
          '<td class="border10">'+
            '<div class="jaw_handle"><div class="jaw_header">&nbsp;</div>'+            
            '<div class="jaw_skin_macos_controls" onMouseOver="with(this.childNodes[0].childNodes[0].childNodes[0]){ for (var i = 0; i < 3; i++ ){ if(childNodes[i].childNodes[0]) childNodes[i].childNodes[0].onmouseover(); } };" onMouseOut="with(this.childNodes[0].childNodes[0].childNodes[0]){ for (var i = 0; i < 3; i++ ){ if(childNodes[i].childNodes[0]) childNodes[i].childNodes[0].onmouseout(); } };">'+
              '<table style="table-layout:auto;">'+
                '<tr>'+
                  '<td><div class="jaw_close" title="close"></div></td>'+
                  '<td><div class="jaw_minimize" title="minimize"></div></td>'+
                  '<td><div class="jaw_fullscreen" title="fullscreen"></div></td>'+
                '</tr>'+
               '</table></div>'+ 
            '</div>'+            
          '</td>'+
          '<td class="border20"><div></div></td>'+
        '</tr>'+
        '<tr>'+
          '<td colspan="3" height="100%">'+            
            '<div class="jaw_contentarea"></div>'+
            '<div class="jaw_statusarea"><div class="jaw_resizearea">&nbsp;</div></div>'+            
          '</td>'+          
        '</tr>'+
      '</table>';
      
var jaw_skin_macos_border_offset = 2;

var jaw_skin_macos_images = [
   'border-00.gif',
   'border-00-passive.gif',
   'border-10.gif',
   'border-10-passive.gif',
   'border-20.gif',
   'border-20-passive.gif',
   'bottom-bg.gif',
   'close.gif',
   'fullscreen.gif',
   'min.gif',
   'passivebuttons.gif',
   'resizer.gif'
];
