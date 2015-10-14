// *****************************************************************************
//                  Cross-Browser Javascript pop-up calendar.
//
// Author  : Anthony Garrett
//
// Credits : I wrote this from scratch myself but I couldn't have done it
//           without the superb "JavaScript The Definitive Guide" by David
//           Flanagan (Pub. O'Reilly ISBN 0-596-00048-0).  I also recognise
//           a contribution from my experience with PopCalendar 4.1 by
//           Liming(Victor) Weng.
//
// Rights  : Feel free to copy and change this as you like except that I
//           regard it as polite to leave the first twenty-one lines as is.
//
// Contact : Sorry, I can't offer support for this but if you find a problem
//           (or just want to tell me how useful you find it), please send
//           me an email at scwfeedback@tarrget.info (Note the two Rs in
//           tarrget).  I will try to fix problems quickly but this is a
//           spare time thing for me.
//
// *****************************************************************************
//
// Features: Easily customised
//                  (output date format, colours, language, year range and
//                   week start day)
//           Accepts a date as input
//                  (see comments below for formats).
//           Cross-browser code tested against;
//                  Internet Explorer 6.0.28     Mozilla  1.7.1
//                  Opera             7.52+      Firefox  0.9.1+
//                                               Flock    0.4.9
//
// How to add the Calendar to your page:
//           This script needs to be defined for your page so, immediately after the
//           BODY tag add the following line;
//
//                  <script type='Text/JavaScript' src='scw.js'></script>
//
// How to use the Calendar once it is defined for your page:
//           Simply choose an event to trigger the calendar (like an onClick or
//           an onMouseOver) and an element to work on (for the calendar to take
//           its initial date from and write its output date to) then write it like
//           this;
//                      <<event>>="scwShow(<<element>>,this);"
//
//              e.g.    onClick="scwShow(document.getElementById('myElement'),this);"
//              or      onMouseOver="scwShow(this,this);"
//
//
// *****************************************************************************
//
// Version   Date        By               Description
// =======   ====        ===============  ===========
//   1.0     2004-08-02  Anthony Garrett  Initial release
//   1.1     2005-10-17  Anthony Garrett  Added requested feature to allow
//                                        a click anywhere on the calling page
//                                        to cancel the calendar.
//                                        Added "How to" paragraphs to
//                                        documentation (above).
//                                        Corrected bug that misread numeric
//                                        seed months as one less than entered.
//   1.2     2005-10-26  Anthony Garrett  Allow start of week to be any day.
//   2.0     2005-11-03  Anthony Garrett  Add an IFRAME behind the calendar to
//                                        deal with IE SELECT boxes.
//                                        Renamed all exposed variables and
//                                        functions but kept showCal as entry
//                                        point for backward compatibility.
//                                        Added classes to all HTML elements
//                                        and moved all style attributes to
//                                        inline stlye sheet in customisation
//                                        section.
//   2.1     2005-11-10  Anthony Garrett  Fixed a bug which causes the calendar
//                                        not to display in Firefox when the
//                                        event trigger element's parent was
//                                        not the data element's parent.
//                                        NOTE: This has forced me to add a
//                                              second interface parameter!
//   2.2     2005-11-17  Anthony Garrett  Added input date validation.
//                                        Added input date highlighting (thanks
//                                        to Brad Allan for that suggestion).
//                                        Added optional strict date processing
//                                        (e.g. making 31-Sep an error instead
//                                        of returning 1-Oct). Improved the
//                                        calendar positioning so that CSS
//                                        positioning using DIVs is handled
//                                        correctly.
//   2.3     2005-11-23  Anthony Garrett  Corrected input validation for US
//                                        and other date formats.  Added 
//                                        examples for US date processing.
//
// Note    : The year range is honoured by all the ways of changing between
//           months however I have decided not to prevent a user from selecting
//           a display day that is outside the range (it can happen if the
//           day falls in the week that starts or ends the range).  You may
//           wish to add further validation to your page in order to trap those
//           days.
//
// *****************************************************************************

// This date is used throughout to determine today's date.

    var scwDateNow = new Date();

//------------------------------------------------------------------------------
// Customisation section
//------------------------------------------------------------------------------

    // Set the bounds for the calendar here...
    // If you want the year to roll forward you can use something like this...
    //      var scwBaseYear = scwDateNow.getFullYear()-5;
    // alternatively, hard code a date like this...
    //      var scwBaseYear = 1990;

    var scwBaseYear	       = scwDateNow.getFullYear()-10;

    // How many years do want to be valid and to show in the drop-down list?

    var scwDropDownYears   = 20;

    // All language dependent changes can be made here...

    var scwToday           = 'Today:',
        scwInvalidDateMsg  = 'The entered date is invalid.\n',
        scwOutOfRangeMsg   = 'The entered date is out of range.\n',
        scwInvalidAlert    = ['Invalid date (',') ignored.'],
        scwArrMonthNames   = ['Jan','Feb','Mar','Apr','May','Jun',
                              'Jul','Aug','Sep','Oct','Nov','Dec'],
        scwArrWeekInits    = ['S','M','T','W','T','F','S'];

    // Note:  Always start the scwArrWeekInits array with your string for
    //        Sunday whatever scwWeekStart (below) is set to.

    // scwWeekStart determines the start of the week in the display
    // Set it to: 0 (Zero) for Sunday, 1 (One) for Monday etc..

    var scwWeekStart       =    0;

    // Set the allowed date delimiters here...
    // E.g. To set the rising slash, hyphen, full-stop (aka stop or point) 
    //      and comma as delimiters use 
    //              var scwArrDelimiters   = ['/','-','.',','];

    var scwArrDelimiters   = ['/','-','.',',']; 

    // scwZindex controls how the pop-up calendar interacts with the rest
    // of the page.  It is usually adequate to leave it as 1 (One) but I
    // have made it available here to help anyone who needs to alter the
    // level in order to ensure that the calendar displays correctly in
    // relation to all other elements on the page.

    var scwZindex          = 1;

    // Personally I like the fact that entering 31-Sep-2005 displays
    // 1-Oct-2005, however you may want that to be an error.  If so,
    // set scwBlnStrict = true.  That will cause an error message to
    // display and the selected month is displayed without a selected
    // day. Thanks to Brad Allan for his feedback prompting this feature.

    var scwBlnStrict       = false;

    // Blend the colours into your page here...

    var scwBackground           = '#6666CC';    // Calendar background
    var scwHeadText             = '#CCCCCC';    // Colour of week headings

    // If you want to "turn off" any of the highlighting then just
    // set the highlight colours to the same as the un-higlighted colours.

    // Today string
    var scwTodayText            = '#FFFFFF',
        scwTodayHighlight       = '#FFFF00';

    // Active Cell
    var scwHighlightText        = '#000000',
        scwHighlightBackground  = '#FFFF00';

    // Weekend Days
    var scwWeekendText          = '#CC6666',
        scwWeekendBackground    = '#CCCCCC';

    // Days out of current month
    var scwExMonthText          = '#999999',
        scwExMonthBackground    = '#CCCCCC';

    // Current month's weekdays
    var scwCellText             = '#000000',
        scwCellBackground       = '#CCCCCC';

    // Input date
    var scwInDateText           = '#FF0000',
        scwInDateBackground     = '#FFCCCC';


    // I have made every effort to isolate the pop-up script from any
    // CSS defined on the main page but if you have anything set that
    // affects the pop-up (or you may want to change the way it looks)
    // then you can address it here.
    //
    // The classes are;
    //      scw         Overall
    //      scwHead     The Selection buttons/drop-downs
    //      scwWeek     The Day Initials (Column Headings)
    //      scwCells    The Individual days
    //      scwFoot     The "Today" selector

    document.writeln("<style>");
    document.writeln(   '.scw       {padding:1px;vertical-align:middle;}');
    document.writeln(   'iframe.scw {position:absolute;z-index:' + scwZindex    +
                                    ';top:0px;left:0px;visibility:hidden;'		+
									'width:1px;height:1px;}');
    document.writeln(   'table.scw  {padding:0px;visibility:hidden;'            +
                                    'position:absolute;width:200px;'            +
                                    'top:0px;left:0px;z-index:' + (scwZindex+1) +
                                    ';text-align:center;'                       +
                                    'padding:1px;vertical-align:middle;'        +
                                    'background-color:' + scwBackground         +
                                    ';border:ridge 2px;font-size:10pt;'         +
                                    'font-family:Arial,Helvetica,Sans-Serif;'   +
                                    'font-weight:bold;}');
    document.writeln(   'td.scwHead     {padding:0px 0px;text-align:center;}');
    document.writeln(   'select.scwHead {margin:3px 1px;}');
    document.writeln(   'input.scwHead  {height:22px;width:22px;'               +
                                        'vertical-align:middle;'                +
                                        'text-align:center;margin:2px 1px;'     +
                                        'font-size:10pt;font-family:fixedSys;'  +
                                        'font-weight:bold;}');
    document.writeln(   'tr.scwWeek     {text-align:center;font-weight:bold;'   +
                                        'color:' + scwHeadText + ';}');
    document.writeln(   'td.scwWeek     {padding:0px;}');
    document.writeln(   'table.scwCells {text-align:right;font-size:8pt;'       +
                                        'width:96%;font-family:'                +
                                        'Arial,Helvetica,Sans-Serif;}');
    document.writeln(   'td.scwCells {padding:3px;vertical-align:middle;'       +
                                     'width:16px;height:16px;font-weight:bold;' +
                                     'color:' + scwCellText                     +
                                     ';background-color:' + scwCellBackground   +
                                     '}');
    document.writeln(   'td.scwFoot  {padding:0px;text-align:center;'           +
                                     'font-weight:normal;color:'                +
                                      scwTodayText + ';}');
    document.writeln("</style>");

    // You can modify the input, display and output date formats in the
    // following three functions;

    function scwInputFormat(scwArrInput,scwEleValue)
        {var scwArrSeed = new Array();

         scwBlnFullInputDate = false;

         switch (scwArrInput.length)
            {case 1:
                {// Year only entry
                 scwArrSeed[0] = parseInt(scwArrInput[0],10);   // Year
                 scwArrSeed[1] = '6';                           // Month
                 scwArrSeed[2] = 1;                             // Day
                 break;
                }
             case 2:
                {// Year and Month entry
                 scwArrSeed[0] = parseInt(scwArrInput[1],10);   // Year
                 scwArrSeed[1] = scwArrInput[0];                // Month
                 scwArrSeed[2] = 1;                             // Day
                 break;
                }
             case 3:
                {// Day Month and Year entry
                 scwArrSeed[0] = parseInt(scwArrInput[2],10);   // Year
                 scwArrSeed[1] = scwArrInput[1];                // Month
                 scwArrSeed[2] = parseInt(scwArrInput[0],10);   // Day

                 // for Month, Day and Year entry use...
                 //   scwArrSeed[0] = parseInt(scwArrInput[2],10);  // Year
                 //   scwArrSeed[1] = scwArrInput[0];               // Month
                 //   scwArrSeed[2] = parseInt(scwArrInput[1],10);  // Day

                 scwBlnFullInputDate = true;
                 break;
                }
             default:
                {// A stuff-up has led to more than three elements in the date.
                 scwArrSeed[0] = 0;     // Year
                 scwArrSeed[1] = 0;     // Month
                 scwArrSeed[2] = 0;     // Day
                }
            }

         // Apply validation and report failures

         if (scwExpValYear.exec(scwArrSeed[0])  == null ||
             scwExpValMonth.exec(scwArrSeed[1]) == null ||
             scwExpValDay.exec(scwArrSeed[2])   == null)
             {alert(scwInvalidDateMsg  + 
                    scwInvalidAlert[0] + scwEleValue + scwInvalidAlert[1]);
              scwBlnFullInputDate = false;
              scwArrSeed[0] = scwBaseYear + Math.floor(scwDropDownYears/2); // Year
              scwArrSeed[1] = '6';     // Month
              scwArrSeed[2] = 1;       // Day
             }

         // Return the  Year    in scwArrSeed[0]
         //             Month   in scwArrSeed[1]
         //             Day     in scwArrSeed[2]

         return scwArrSeed;
        }

    function scwDisplayFormat(scwDisplayDate)
        {// The format of the display of today's date at the foot of the
         // calendar...
         // Day Month and Year display
         document.write(scwDisplayDate.getDate()                  + "-" +
                     scwArrMonthNames[scwDisplayDate.getMonth()]  + "-" +
                     scwDisplayDate.getFullYear());

         // for Month, Day and Year output use...
         //document.write(scwArrMonthNames[scwDisplayDate.getMonth()] + "-" +
         //            scwDisplayDate.getDate()                       + "-" +
         //            scwDisplayDate.getFullYear());
        }

    function scwSetOutput(scwOutputDate)
        {// Numeric months are held internally as 0 to 11 in this script so
         // the correct numeric month output should be in the form
         //                         (scwOutputDate.getMonth()+1)
         // e.g.
         //    scwTargetEle.value = ((scwOutputDate.getDate()<10)?'0':'')  +
         //                         scwOutputDate.getDate()             + '-' +
         //                         ((scwOutputDate.getMonth()<9)?'0':'')  +
         //                         (scwOutputDate.getMonth()+1)        + '-' +
         //                         scwOutputDate.getFullYear();

         // Day Month and Year output
           scwTargetEle.value =   ((scwOutputDate.getDate()<10)?'0':'')  +
                                  scwOutputDate.getDate()                + '-' +
                                  scwArrMonthNames[scwOutputDate.getMonth()] + '-' +
                                  scwOutputDate.getFullYear();

         // for Month, Day and Year output use...
         //scwTargetEle.value =   scwArrMonthNames[scwOutputDate.getMonth()] + '-' +
         //                     ((scwOutputDate.getDate()<10)?'0':'')  +
         //                       scwOutputDate.getDate()                + '-' +
         //                       scwOutputDate.getFullYear();


         if (!scwIsOpera)
            {document.getElementById('scwIframe').style.visibility='hidden';}
        }

//------------------------------------------------------------------------------
// End of customisation section
//------------------------------------------------------------------------------

    // I try to avoid browser sniffing but the IE SELECT/z-index "feature" means
    // that I have had to place an IFRAME behind the pop-up.  This currently
    // renders incorrectly in Opera (the IFRAME renders in front of the
    // calendar) but as I write (2005-Nov-01) the rendering is fixed in Opera 9
    // which is in Beta.

    var scwIsOpera  = (navigator.userAgent.toLowerCase().indexOf("opera")!=-1);

    // Browsers handle positioning differently Mozilla (Firefox & Flock) needs
    // to exclude DIVs while IE/Opera must include them so unfortunately I have
    // to sniff this too.

    var scwIsFirefox= (navigator.userAgent.toLowerCase().indexOf("firefox")!=-1);

    var scwTargetEle,
        scwSaveText,
        scwSaveBackground,
        scwMonthSum         = 0,
        scwBlnFullInputDate = false,
        scwStartDate        = new Date(),
        scwSeedDate         = new Date(),
        scwWeekStart        = scwWeekStart%7;

    // "Escape" all the user defined date delimiters - 
    // several delimiters will need it and it does no harm for the others.

    var scwExpDelimiters    = new RegExp('[\\'+scwArrDelimiters.join('\\')+']','g');

    // These regular expression validate the input date format to the 
    // following rules;
    //
    // Format:              Day   1-31 (optional zero on single digits)
    //                      Month 1-12 (optional zero on single digits) 
    //                            or case insensitive name
    //                      Year  Two or four digits

    // Months names and Delimiters are as defined above

    var scwExpValDay    = /^(0?[1-9]|[1-2]\d|3[0-1])$/,
        scwExpValMonth  = new RegExp("^(0?[1-9]|1[0-2]|"        + 
                                     scwArrMonthNames.join("|") + 
                                     ")$","i"),
        scwExpValYear   = /^(\d{2}|\d{4})$/;

    function showCal(scwEle,scwSourceEle)    {scwShow(scwEle,scwSourceEle);}
    function scwShow(scwEle,scwSourceEle)
        {//*********************************************************************
         //   If no value is preset then the seed date is
         //      Today (when today is in range) OR
         //      The middle of the date range.
        
         scwSeedDate = scwDateNow;

         // Strip space characters from start and end of date input
         scwEle.value = scwEle.value.replace(/^\s+/,"").replace(/\s+$/,"");

         if (scwEle.value.length==0)
            {// If no value is entered and today is within the range,
             // use today's date, otherwise use the middle of the valid range.

             scwBlnFullInputDate=false;

             if ((new Date(scwBaseYear+scwDropDownYears-1,11,31))<scwSeedDate ||
                 (new Date(scwBaseYear,0,1))                     >scwSeedDate
				)
                {scwSeedDate = new Date(scwBaseYear +
                                        Math.floor(scwDropDownYears / 2), 5, 1);
                }
            }
         else
            {// Parse the string into an array using the allowed delimiters
             scwArrSeedDate = scwInputFormat(scwEle.value.split(scwExpDelimiters),
                                             scwEle.value);

             // So now we have the Year, Month and Day in an array.

			 //   If the year is two digits then the routine assumes a year
			 //   belongs in the 21st Century unless it is less than 50 in which
			 //   case it assumes the 20th Century is intended.

             if (scwArrSeedDate[0]<100)
                {scwArrSeedDate[0]= scwArrSeedDate[0] +
                                    parseInt((scwArrSeedDate[0]>50)?1900:2000,10);}

             // Check whether the month is in digits or an abbreviation

             if (scwArrSeedDate[1].search(/\d+/)!=0)
                {month = scwArrMonthNames.join('|').toUpperCase().
                            search(scwArrSeedDate[1].substr(0,3).toUpperCase());
                 scwArrSeedDate[1] = Math.floor(month/4)+1;
                }

             scwSeedDate = new Date(scwArrSeedDate[0],
                                    scwArrSeedDate[1]-1,
                                    scwArrSeedDate[2]);
            }

         // Test that we have arrived at a valid date

         if (isNaN(scwSeedDate))
            {alert( scwInvalidDateMsg +
                    scwInvalidAlert[0] + scwEle.value +
                    scwInvalidAlert[1]);
             scwSeedDate = new Date(scwBaseYear +
                    Math.floor(scwDropDownYears/2),5,1);
             scwBlnFullInputDate=false;
            }
         else
            {// Test that the date is within range,
             // if not then set date to a sensible date in range.

             if ((new Date(scwBaseYear,0,1)) > scwSeedDate)
                {if (scwBlnStrict) alert(scwOutOfRangeMsg);
                 scwSeedDate = new Date(scwBaseYear,0,1);
                 scwBlnFullInputDate=false;
                }
             else
                {if ((new Date(scwBaseYear+scwDropDownYears-1,11,31))<scwSeedDate)
                    {if (scwBlnStrict) alert(scwOutOfRangeMsg);
                     scwSeedDate = new Date(scwBaseYear +
                                            Math.floor(scwDropDownYears)-1,11,1);
                     scwBlnFullInputDate=false;
                    }
                 else
                    {if (scwBlnStrict && scwBlnFullInputDate &&
                          (scwSeedDate.getDate()      != scwArrSeedDate[2] ||
                           (scwSeedDate.getMonth()+1) != scwArrSeedDate[1] ||
                           scwSeedDate.getFullYear()  != scwArrSeedDate[0]
                          )
                        )
                        {alert(scwOutOfRangeMsg);
                         scwSeedDate = new Date(scwSeedDate.getFullYear(), 
                                                scwSeedDate.getMonth()-1,1);
                         scwBlnFullInputDate=false;
                        }
                    }
                }
            }

         // Calculate the number of months that the entered (or
         // defaulted) month is after the start of the allowed
         // date range.

         scwMonthSum =  12*(scwSeedDate.getFullYear()-scwBaseYear)+
                            scwSeedDate.getMonth();

         // Set the drop down boxes.

         document.getElementById('scwYears').options.selectedIndex =
            Math.floor(scwMonthSum/12);
         document.getElementById('scwMonths').options.selectedIndex=
            (scwMonthSum%12);

         // Position the calendar box

         var offsetTop =parseInt(scwEle.offsetTop,10)+
                        parseInt(scwEle.offsetHeight,10),
             offsetLeft=parseInt(scwEle.offsetLeft,10);

         scwTargetEle=scwEle;

         do {scwEle=scwEle.parentNode;
             if (scwEle.tagName!='FORM'  &&
                 scwEle.tagName!='TBODY' &&
                 scwEle.tagName!='TR'    &&
                 ((scwIsFirefox && scwEle.tagName!='DIV') || !scwIsFirefox) &&
                 scwEle.nodeType==1)
                {offsetTop +=parseInt(scwEle.offsetTop,10);
                 offsetLeft+=parseInt(scwEle.offsetLeft,10);
                }
            }
         while (scwEle.tagName!='BODY');

         document.getElementById('scw').style.top =offsetTop +'px';
         document.getElementById('scw').style.left=offsetLeft+'px';

         if (!scwIsOpera)
            {document.getElementById('scwIframe').style.top =offsetTop +'px';
             document.getElementById('scwIframe').style.left=offsetLeft+'px';
             document.getElementById('scwIframe').style.width  =
                (document.getElementById('scw').offsetWidth-2)+'px';
             document.getElementById('scwIframe').style.height =
                (document.getElementById('scw').offsetHeight-2)+'px';
             document.getElementById('scwIframe').style.visibility='visible';
            }

         // Display the month

         scwShowMonth(0);

         // Show it on the page

         document.getElementById('scw').style.visibility='visible';

         // Stop this event from bubbling up

         scwCancelPropagation(scwSourceEle);
        }

    function scwCancelPropagation(scwSourceEle)
        {if (typeof event=='undefined')         //Firefox
                {scwSourceEle.parentNode.
                    addEventListener("click",scwExecuteStop,false);
                }
         else   {event.cancelBubble = true;}    //IE, Opera
        }

    function scwExecuteStop(scwEvt)  {scwEvt.stopPropagation();}

    function scwHide(scwEle)
        {scwStartDate.setDate(scwStartDate.getDate() +
            parseInt(scwEle.id.substr(8),10));
         scwSetOutput(scwStartDate);
        }

    function scwShowMonth(scwBias)
        {// Set the selectable Month and Year
         // May be called: from the left and right arrows
         //                  (shift month -1 and +1 respectively)
         //                from the month selection list
         //                from the year selection list
         //                from the showCal routine
         //                  (which initiates the display).

         var scwShowDate  = new Date();

         scwSelYears  = document.getElementById('scwYears');
         scwSelMonths = document.getElementById('scwMonths');

         if (scwSelYears.options.selectedIndex>-1)
            {scwMonthSum=12*(scwSelYears.options.selectedIndex)+scwBias;
             if (scwSelMonths.options.selectedIndex>-1)
                {scwMonthSum+=scwSelMonths.options.selectedIndex;}
            }
         else
            {if (scwSelMonths.options.selectedIndex>-1)
                {scwMonthSum+=scwSelMonths.options.selectedIndex;}
            }

         scwShowDate.setFullYear(scwBaseYear + Math.floor(scwMonthSum/12),
                                 (scwMonthSum%12),
                                 1);

         if ((12*(parseInt(scwShowDate.getFullYear()) - scwBaseYear),10) +
            parseInt(scwShowDate.getMonth(),10) < (12*scwDropDownYears)    &&
            (12*(parseInt(scwShowDate.getFullYear(),10) - scwBaseYear)) +
            parseInt(scwShowDate.getMonth(),10) > -1)
            {scwSelYears.options.selectedIndex=Math.floor(scwMonthSum/12);
             scwSelMonths.options.selectedIndex=(scwMonthSum%12);

             scwCurMonth = scwShowDate.getMonth();

             scwShowDate.setDate(-(scwShowDate.getDay()-scwWeekStart)%7+1);

             scwStartDate = new Date(scwShowDate);

             // Treewalk to display the dates.
             // I tried to use getElementsByName but IE refused to cooperate
             // so I resorted to this method which works for all tested
             // browsers.

            var cells = document.getElementById('scwCells');
             for (i=0;i<cells.childNodes.length;i++)
                {var scwRows = cells.childNodes[i];
                 if (scwRows.nodeType==1 && scwRows.tagName=='TR')
                    {for (j=0;j<scwRows.childNodes.length;j++)
                        {var scwCols = scwRows.childNodes[j];
                         if (scwCols.nodeType==1 && scwCols.tagName=='TD')
                            {scwRows.childNodes[j].innerHTML=
                                    scwShowDate.getDate();

                             var scwBlnInDate = 
                                    (scwBlnFullInputDate &&
                                     scwShowDate.getFullYear()==scwSeedDate.getFullYear()  &&
                                     scwShowDate.getMonth()   ==scwSeedDate.getMonth()     &&
                                     scwShowDate.getDate()    ==scwSeedDate.getDate());

                             scwRows.childNodes[j].style.color=
                                (scwShowDate.getMonth()!=scwCurMonth)
                                ?scwExMonthText
                                :scwBlnInDate
                                 ?scwInDateText
                                 :scwShowDate.getDay()%6==0
                                    ?scwWeekendText
                                    :scwCellText;

                             scwRows.childNodes[j].style.backgroundColor=
                                (scwShowDate.getMonth()!=scwCurMonth)
                                ?scwExMonthBackground
                                :scwBlnInDate
                                 ?scwInDateBackground
                                 :scwShowDate.getDay()%6==0
                                    ?scwWeekendBackground
                                    :scwCellBackground;

                             scwShowDate.setDate(scwShowDate.getDate()+1);
                            }
                        }
                    }
                }
            }
        }

    document.onclick = scwCancel;

    function scwCancel()
        {document.getElementById('scw').style.visibility='hidden';
         if (!scwIsOpera)
            {document.getElementById('scwIframe').style.visibility='hidden';}
        }

    if (!scwIsOpera)
        {document.write("<iframe class='scw' " +
                                "id='scwIframe' name='scwIframe' "   +
                                "frameborder='0'>"                   +
                        "</iframe>");
        }

    document.write(
     "<table id='scw' class='scw' " +
            "onclick='scwCancelPropagation(this.childNodes[0]);'>" +
       "<tr class='scw'>" +
         "<td class='scw'>" +
           "<table class='scwHead' cellspacing='0' cellpadding='0' width='100%'>" +
            "<tr class='scwHead'>" +
                "<td class='scwHead'>" +
                    "<input class='scwHead' type='button' value='<' " +
                            "onclick='scwShowMonth(-1);'  /></td>" +
                 "<td class='scwHead'>" +
                    "<select id='scwMonths' class='scwHead' " +
                            "onChange='scwShowMonth(0);'>");

    for (i=0;i<scwArrMonthNames.length;i++)
        document.write(   "<option>" + scwArrMonthNames[i] + "</option>");

    document.write("   </select>" +
                 "</td>" +
                 "<td class='scwHead'>" +
                    "<select id='scwYears' class='scwHead' " +
                            "onChange='scwShowMonth(0);'>");

    for (i=0;i<scwDropDownYears;i++)
        document.write(   "<option>" + (scwBaseYear+i) + "</option>");

    document.write(   "</select>" +
                 "</td>" +
                 "<td class='scwHead'>" +
                    "<input class='scwHead' type='button' value='>' " +
                            "onclick='scwShowMonth(1);' /></td>" +
                "</tr>" +
              "</table>" +
            "</td>" +
          "</tr>" +
          "<tr class='scw'>" +
            "<td class='scw'>" +
              "<table class='scwCells' align='center' " +
                    "onclick=\"document.getElementById('scw').style." +
                                        "visibility='hidden';\">" +
                "<thead class='scwWeek'>" +
                  "<tr  class='scwWeek'>");

    for (i=0;i<scwArrWeekInits.length;i++)
        document.write( "<td class='scwWeek'>" +
                          scwArrWeekInits[(i+scwWeekStart)%scwArrWeekInits.length] +
                        "</td>");

    document.write("</tr>" +
                "</thead>" +
                "<tbody class='scwCells' id='scwCells'>");

    for (i=0;i<6;i++)
        {document.write(
                    "<tr class='scwCells'>");
         for (j=0;j<7;j++)
            {document.write(
                        "<td  class='scwCells' id='scwCell_" + (j+(i*7))+ "' " +
                             "onmouseover=\"scwSaveBackground=this.style.backgroundColor;" +
                                            "this.style.backgroundColor='" +
                                            scwHighlightBackground + "';" +
                                            "scwSaveText=this.style.color;" +
                                            "this.style.color='" +
                                            scwHighlightText + "';\" " +
                             "onmouseout =\"this.style.backgroundColor=scwSaveBackground;" +
                                            "this.style.color=scwSaveText;\" " +
                             "onclick='scwHide(this);'></td> ");
            }

         document.write(
                    "</tr>");
        }

    document.write(
                "</tbody>");

    if ((new Date(scwBaseYear + scwDropDownYears, 11, 32)) > scwDateNow &&
        (new Date(scwBaseYear, 0, 0))                      < scwDateNow)
        {document.write(
                  "<tfoot class='scwFoot'>" +
                    "<tr class='scwFoot'>" +
                      "<td class='scwFoot' colspan='7' " +
                          "onmouseover=\"this.style.color='" +
                                                        scwTodayHighlight + "';" +
                                        "this.style.fontWeight='bold';\" " +
                          "onmouseout =\"this.style.color='" +
                                                        scwTodayText + "';" +
                                        "this.style.fontWeight='normal';\" " +
                            "onclick='scwSetOutput(scwDateNow);'>" +
                                                        scwToday + " ");

         scwDisplayFormat(scwDateNow);

         document.write(
                        "</td>" +
                     "</tr>" +
                   "</tfoot>");
        }

    document.write(
              "</table>" +
            "</td>" +
          "</tr>" +
        "</table>");

// End of Calendar
