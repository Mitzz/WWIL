//JavaScript Document

$('#gallery_1').dcThumbnailGallery({
        'source':'../../_images/gallery.xml',
		'settings':{
        'gallery':{             
        'titleEnabled':true,          // Enable/disable the title
        'titlePosition':'top',        // The position of the title
        
        'thumbnailEnabled':true,      // Enable/disable the slide controls              
        'thumbnailSize':20,               // The size of the thumbnail.
        
        'navigationEnabled':false,        // Enable/disable the slide controls
        'controlThemeColor':'#000000',// The color of the controls
        'controlThemeAlpha':40,       // The alpha of the controls
        
        'transitionType':'random',    // Set the transition type
                                      // 'slide' : sliding out left/right
                                      // 'flip'  : 3d flip
                                      // 'slideup' : sliding up/down
                                      // 'swap'  : 3d swap
                                      // 'cube'  : 3d cube
                                      // 'pop'   : pop up
                                      // 'fade'  : fade
                                      // 'random' : random
                                      // *3D transition only supported by Safari
                
        'resizeMode':0                // Set the resize mode of main image
                                      //        AUTO            :0
                                      //        AUTO_WIDTH      :1
                                      //        AUTO_HEIGHT     :2
                                      //        STRETCH         :3
                                      //        AUTO_FILL       :5      
        },
        'basic':{                                                       
        'backgroundColor':'#333333',  // Set main background color
        'themeColor':'#000000',       // Set the overall theme color
        'themeTextColor':'#FFFFFF',   // Set the overall theme text color
        'themeAlpha':100,             // Set the overall theme background alpha
        
        'autoSlideShowEnabled':false, // Enable/disable auto start slide show
        'slideShowEnabled':true,      // Enable/disable slide show
        'slideShowInterval':5,        // Time between slide (sec);
        }
}
		
});
