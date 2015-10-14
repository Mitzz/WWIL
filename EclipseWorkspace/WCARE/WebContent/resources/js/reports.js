var prodCatg;
var reportType;

function checkProductCategory()
{
   
    prodCatg =   document.frmSearchreport.productCategory.value;
    
    if( document.frmSearchreport.txtEffectiveDate.value=="")alert('Please Select Date From');
    if( document.frmSearchreport.txtEndDate.value=="")alert('Please Select Date To');
    if(prodCatg=="Project Spares" && document.frmSearchreport.txtEffectiveDate.value!="" && document.frmSearchreport.txtEndDate.value!="" ) {
    	location.href='ProjectSummary.html';
     }
     if(prodCatg=="Spares" && document.frmSearchreport.txtEffectiveDate.value!="" && document.frmSearchreport.txtEndDate.value!="" ) {
       	location.href='sparesDetails.html';
     }
     return true;
}

function init(){
    alert(prodCatg);
}