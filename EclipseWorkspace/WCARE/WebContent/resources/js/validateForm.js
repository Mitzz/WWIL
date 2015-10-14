function validatefrmMaintainCompany (event)
{

	if(validateForm(

	  'Legal Name', document.frmMaintainCompany.txtLegalName.value, 'M', 'S'
	, 'FIEN Code', document.frmMaintainCompany.txtFEINCode.value, 'M', 'S'
	, 'Company Description', document.frmMaintainCompany.txtCompanyDesc.value, 'O', 'S'

	))
	{
		return true;
	}
}
function validatefrmCompany (event)
{

	if(validateForm(

	 'Company Code', document.frmNewCompany.txtCompanyCode.value, 'M', 'S'
	, 'Legal Name', document.frmNewCompany.txtLegalName.value, 'M', 'S'
	, 'FIEN Code', document.frmNewCompany.txtFEINCode.value, 'M', 'S'
	, 'Company Description', document.frmNewCompany.txtCompanyDesc.value, 'O', 'S'

	))
	{
		return true;
	}
}

function validatefrmCompanyAddress (event)
{

	if(validateForm(
	 'Address', document.frmNewCompany.txtCompanyAddress.value, 'M', 'S'
	, 'City', document.frmNewCompany.txtCompanyCity.value, 'M', 'S'
	, 'State Code',document.frmNewCompany.txtCompanyStateCode.value, 'M', 'S'
	, 'Country Code', document.frmNewCompany.txtCompanyCountryCode.value, 'M', 'S'
	, 'ZIP/Postal Code', document.frmNewCompany.txtCompanyZIPCode.value, 'M', 'S'
	, 'Work Phone', document.frmNewCompany.txtCompanyWorkPhone.value, 'O', 'S'
	, 'Other Phone', document.frmNewCompany.txtCompanyOtherPhone.value, 'O', 'S'
	, 'Fax', document.frmNewCompany.txtCompanyFax.value, 'O', 'S'
	, 'Email', document.frmNewCompany.txtCompanyEmail.value, 'O', 'E'
	, 'Comment', document.frmNewCompany.txtCompanyComment.value, 'O', 'S'
	))
	{
		return true;
	}
}

function validatefrmCompanyContactAddress (event)
{

	if(validateForm(
	  'Last Name', document.frmNewCompany.txtContactLastName.value, 'M', 'S'
	, 'First Name', document.frmNewCompany.txtContactFirstName.value, 'M', 'S'
	, 'Middle Name',document.frmNewCompany.txtContactMiddleName.value, 'O', 'S'
	, 'Title',document.frmNewCompany.txtContactTitle.value, 'O', 'S'
	, 'Address', document.frmNewCompany.txtContactStreetAddress.value, 'M', 'S'
	, 'City', document.frmNewCompany.txtContactCity.value, 'M', 'S'
	, 'State Code',document.frmNewCompany.txtContactStateCode.value, 'M', 'S'
	, 'Country Code', document.frmNewCompany.txtContactCountryCode.value, 'M', 'S'
	, 'ZIP/Postal Code', document.frmNewCompany.txtContactZIPCode.value, 'M', 'S'
	, 'Work Phone', document.frmNewCompany.txtContactWorkPhone.value, 'O', 'S'
	, 'Other Phone', document.frmNewCompany.txtContactOtherPhone.value, 'O', 'S'
	, 'Home Phone', document.frmNewCompany.txtContactHomePhone.value, 'O', 'S'
	, 'Cell Phone', document.frmNewCompany.txtContactCellPhone.value, 'O', 'S'
	, 'Fax', document.frmNewCompany.txtContactFax.value, 'O', 'S'
	, 'Email', document.frmNewCompany.txtContactEmail.value, 'O', 'E'
	, 'Computer Name', document.frmNewCompany.txtContactComputerName.value, 'O', 'S'
	, 'Comment', document.frmNewCompany.txtContactComment.value, 'O', 'S'
	))
	{
		return true;
	}
}



function validatefrmDivision (event)
{

	if(validateForm(
	 'Division Short Name', document.frmNewDivision.txtDivisionShortName.value, 'M', 'S'
	, 'Division Long Name', document.frmNewDivision.txtDivisionLongName.value, 'M', 'S'
	, 'Logo File Name', document.frmNewDivision.txtLogoFileName.value, 'O', 'S'
	, 'FIEN Code', document.frmNewDivision.txtFEINCode.value, 'O', 'S'
	))
	{
		return true;
	}
}
function validatefrmDivisionAddress (event)
{

	if(validateForm(
	 'Address', document.frmNewDivision.txtDivisionStreetAddress.value, 'M', 'S'
	, 'City', document.frmNewDivision.txtDivisionCity.value, 'M', 'S'
	, 'State Code',document.frmNewDivision.txtDivisionStateCode.value, 'M', 'S'
	, 'Country Code', document.frmNewDivision.txtDivisionCountryCode.value, 'M', 'S'
	, 'ZIP/Postal Code', document.frmNewDivision.txtDivisionZIPCode.value, 'M', 'S'
	, 'Work Phone', document.frmNewDivision.txtDivisionWorkPhone.value, 'O', 'S'
	, 'Other Phone', document.frmNewDivision.txtDivisionOtherPhone.value, 'O', 'S'
	, 'Fax', document.frmNewDivision.txtDivisionFax.value, 'O', 'S'
	, 'Email', document.frmNewDivision.txtDivisionEmail.value, 'O', 'E'
	, 'Comment', document.frmNewDivision.txtDivisionComment.value, 'O', 'S'
	))
	{
		return true;
	}
}
function validatefrmDivisionContactAddress (event)
{

	if(validateForm(
	  'Last Name', document.frmNewDivision.txtContactLastName.value, 'M', 'S'
	, 'First Name', document.frmNewDivision.txtContactFirstName.value, 'M', 'S'
	, 'Middle Name',document.frmNewDivision.txtContactMiddleName.value, 'O', 'S'
	, 'Title',document.frmNewDivision.txtContactTitle.value, 'O', 'S'
	, 'Address', document.frmNewDivision.txtContactStreetAddress.value, 'M', 'S'
	, 'City', document.frmNewDivision.txtContactCity.value, 'M', 'S'
	, 'State Code',document.frmNewDivision.txtContactStateCode.value, 'M', 'S'
	, 'Country Code', document.frmNewDivision.txtContactCountryCode.value, 'M', 'S'
	, 'ZIP/Postal Code', document.frmNewDivision.txtContactZIPCode.value, 'M', 'S'
	, 'Work Phone', document.frmNewDivision.txtContactWorkPhone.value, 'O', 'S'
	, 'Other Phone', document.frmNewDivision.txtContactOtherPhone.value, 'O', 'S'
	, 'Home Phone', document.frmNewDivision.txtContactHomePhone.value, 'O', 'S'
	, 'Cell Phone', document.frmNewDivision.txtContactCellPhone.value, 'O', 'S'
	, 'Fax', document.frmNewDivision.txtContactFax.value, 'O', 'S'
	, 'Email', document.frmNewDivision.txtContactEmail.value, 'O', 'E'
	, 'Computer Name', document.frmNewDivision.txtContactComputerName.value, 'O', 'S'
	, 'Comment', document.frmNewDivision.txtContactComment.value, 'O', 'S'
	))
	{
		return true;
	}
}

function validatefrmOfficeAddress (event)
{

	if(validateForm(
	 'Address', document.frmAddMoreAddress.txtStreetAddr.value, 'M', 'S'
	, 'City', document.frmAddMoreAddress.txtCity.value, 'M', 'S'
	, 'State Code',document.frmAddMoreAddress.txtStateCode.value, 'M', 'S'
	, 'Country Code', document.frmAddMoreAddress.txtCountryCode.value, 'M', 'S'
	, 'ZIP/Postal Code', document.frmAddMoreAddress.txtZIPCode.value, 'M', 'S'
	, 'Work Phone', document.frmAddMoreAddress.txtWorkPhone.value, 'O', 'S'
	, 'Other Phone', document.frmAddMoreAddress.txtOtherPhone.value, 'O', 'S'
	, 'Fax', document.frmAddMoreAddress.txtFax.value, 'O', 'S'
	, 'Email', document.frmAddMoreAddress.txtEmail.value, 'O', 'E'
	, 'Comment', document.frmAddMoreAddress.txtComment.value, 'O', 'S'
	))
	{
		return true;
	}
}


function validatefrmOfficeContacts (event)
{

	if(validateForm(
	  'Last Name', document.frmAddMoreContact.txtLastName.value, 'M', 'S'
	, 'First Name', document.frmAddMoreContact.txtFirstName.value, 'M', 'S'
	, 'Middle Name',document.frmAddMoreContact.txtMiddleName.value, 'O', 'S'
	, 'Title',document.frmAddMoreContact.txtTitle.value, 'O', 'S'
	, 'Address', document.frmAddMoreContact.txtStreetAddr.value, 'M', 'S'
	, 'City', document.frmAddMoreContact.txtCity.value, 'M', 'S'
	, 'State Code',document.frmAddMoreContact.txtStateCode.value, 'M', 'S'
	, 'Country Code', document.frmAddMoreContact.txtCountryCode.value, 'M', 'S'
	, 'ZIP/Postal Code', document.frmAddMoreContact.txtZIPCode.value, 'M', 'S'
	, 'Work Phone', document.frmAddMoreContact.txtWorkPhone.value, 'O', 'S'
	, 'Other Phone', document.frmAddMoreContact.txtOtherPhone.value, 'O', 'S'
	, 'Home Phone', document.frmAddMoreContact.txtHomePhone.value, 'O', 'S'
	, 'Cell Phone', document.frmAddMoreContact.txtCellPhone.value, 'O', 'S'
	, 'Fax', document.frmAddMoreContact.txtFax.value, 'O', 'S'
	, 'Email', document.frmAddMoreContact.txtEmail.value, 'O', 'E'
	, 'Computer Name', document.frmAddMoreContact.txtComputerName.value, 'O', 'S'
	, 'Comment', document.frmAddMoreContact.txtComment.value, 'O', 'S'
	))
	{
		return true;
	}
}



function validatefrmMaintainOfficeAddress (event)
{

	if(validateForm(
	 'Address', document.frmMaintainCompanyAddress.txtStreetAddr.value, 'M', 'S'
	, 'City', document.frmMaintainCompanyAddress.txtCity.value, 'M', 'S'
	, 'State Code',document.frmMaintainCompanyAddress.txtStateCode.value, 'M', 'S'
	, 'Country Code', document.frmMaintainCompanyAddress.txtCountryCode.value, 'M', 'S'
	, 'ZIP/Postal Code', document.frmMaintainCompanyAddress.txtZIPCode.value, 'M', 'S'
	, 'Work Phone', document.frmMaintainCompanyAddress.txtWorkPhone.value, 'O', 'S'
	, 'Other Phone', document.frmMaintainCompanyAddress.txtOtherPhone.value, 'O', 'S'
	, 'Fax', document.frmMaintainCompanyAddress.txtFax.value, 'O', 'S'
	, 'Email', document.frmMaintainCompanyAddress.txtEmail.value, 'O', 'E'
	, 'Comment', document.frmMaintainCompanyAddress.txtComment.value, 'O', 'S'
	))
	{
		return true;
	}
}

function validatefrmMaintainOfficeContact (event)
{

	if(validateForm(
	  'Last Name', document.frmMaintainCompanyContact.txtLastName.value, 'M', 'S'
	, 'First Name', document.frmMaintainCompanyContact.txtFirstName.value, 'M', 'S'
	, 'Middle Name',document.frmMaintainCompanyContact.txtMiddleName.value, 'O', 'S'
	, 'Title',document.frmMaintainCompanyContact.txtTitle.value, 'O', 'S'
	, 'Address', document.frmMaintainCompanyContact.txtStreetAddr.value, 'M', 'S'
	, 'City', document.frmMaintainCompanyContact.txtCity.value, 'M', 'S'
	, 'State Code',document.frmMaintainCompanyContact.txtStateCode.value, 'M', 'S'
	, 'Country Code', document.frmMaintainCompanyContact.txtCountryCode.value, 'M', 'S'
	, 'ZIP/Postal Code', document.frmMaintainCompanyContact.txtZIPCode.value, 'M', 'S'
	, 'Work Phone', document.frmMaintainCompanyContact.txtWorkPhone.value, 'O', 'S'
	, 'Other Phone', document.frmMaintainCompanyContact.txtOtherPhone.value, 'O', 'S'
	, 'Home Phone', document.frmMaintainCompanyContact.txtHomePhone.value, 'O', 'S'
	, 'Cell Phone', document.frmMaintainCompanyContact.txtCellPhone.value, 'O', 'S'
	, 'Fax', document.frmMaintainCompanyContact.txtFax.value, 'O', 'S'
	, 'Email', document.frmMaintainCompanyContact.txtEmail.value, 'O', 'E'
	, 'Computer Name', document.frmMaintainCompanyContact.txtComputerName.value, 'O', 'S'
	, 'Comment', document.frmMaintainCompanyContact.txtComment.value, 'O', 'S'
	))
	{
		return true;
	}
}


function validatefrmCostCenter (event)
{

	if(validateForm(
	 'Cost Center Code', document.frmCreateCostCenter.txtCostCenterCode.value, 'M', 'S'
	, 'Office Code', document.frmCreateCostCenter.txtOfficeCode.value, 'M', 'S'
	, 'Office Name',document.frmCreateCostCenter.txtOfficeName.value, 'M', 'S'
	, 'Company Name', document.frmCreateCostCenter.cboCompany.value, 'M', 'S'
	, 'Division Code', document.frmCreateCostCenter.txtDivisionCode.value, 'M', 'S'
	, 'Manager Code', document.frmCreateCostCenter.txtManagerCode.value, 'M', 'S'
	, 'Rate', document.frmCreateCostCenter.txtRate.value, 'O', 'M'
	, 'MO Rate', document.frmCreateCostCenter.txtMORate.value, 'O', 'M'
	, 'Aop Rate', document.frmCreateCostCenter.txtAopRate.value, 'O', 'M'
	, 'Currency Rate', document.frmCreateCostCenter.txtCurrencyRate.value, 'O', 'M'
	, 'Time Zone', document.frmCreateCostCenter.txtTimeZone.value, 'M', 'I'
	, 'Check Printing Cost Center Code', document.frmCreateCostCenter.txtPrintCostCenterCode.value, 'O', 'S'
	))
	{
		return true;
	}
}

function validatefrmCostCenterAddress (event)
{

	if(validateForm(
	 'Address', document.frmCreateCostCenter.txtAddressStreetAddr.value, 'M', 'S'
	, 'City', document.frmCreateCostCenter.txtAddressCity.value, 'M', 'S'
	, 'State Code',document.frmCreateCostCenter.txtStateCode1.value, 'M', 'S'
	, 'Country Code', document.frmCreateCostCenter.txtCountryCode1.value, 'M', 'S'
	, 'ZIP/Postal Code', document.frmCreateCostCenter.txtAddressZIPCode.value, 'M', 'S'
	, 'Work Phone', document.frmCreateCostCenter.txtAddressWorkPhone.value, 'O', 'S'
	, 'Other Phone', document.frmCreateCostCenter.txtAddressOtherPhone.value, 'O', 'S'
	, 'Fax', document.frmCreateCostCenter.txtAddressFax.value, 'O', 'S'
	, 'Email', document.frmCreateCostCenter.txtAddressEmail.value, 'O', 'E'
	, 'Comment', document.frmCreateCostCenter.txtAddressComment.value, 'O', 'S'
	))
	{
		return true;
	}
}

function validatefrmCostCenterContactAddress (event)
{

	if(validateForm(
	  'Last Name', document.frmCreateCostCenter.txtContactLastName.value, 'M', 'S'
	, 'First Name', document.frmCreateCostCenter.txtContactFirstName.value, 'M', 'S'
	, 'Middle Name',document.frmCreateCostCenter.txtContactMiddleName.value, 'O', 'S'
	, 'Address', document.frmCreateCostCenter.txtContactStreetAddr.value, 'M', 'S'
	, 'City', document.frmCreateCostCenter.txtContactCity.value, 'M', 'S'
	, 'State Code',document.frmCreateCostCenter.txtStateCode2.value, 'M', 'S'
	, 'Country Code', document.frmCreateCostCenter.txtCountryCode2.value, 'M', 'S'
	, 'ZIP/Postal Code', document.frmCreateCostCenter.txtContactZIPCode.value, 'M', 'S'
	, 'Work Phone', document.frmCreateCostCenter.txtContactWorkPhone.value, 'O', 'S'
	, 'Other Phone', document.frmCreateCostCenter.txtContactOtherPhone.value, 'O', 'S'
	, 'Home Phone', document.frmCreateCostCenter.txtContactHomePhone.value, 'O', 'S'
	, 'Cell Phone', document.frmCreateCostCenter.txtContactCellPhone.value, 'O', 'S'
	, 'Fax', document.frmCreateCostCenter.txtContactFax.value, 'O', 'S'
	, 'Email', document.frmCreateCostCenter.txtContactEmail.value, 'O', 'E'
	, 'Computer Name', document.frmCreateCostCenter.txtContactComputerName.value, 'O', 'S'
	, 'Comment', document.frmCreateCostCenter.txtContactComment.value, 'O', 'S'
	))
	{
		return true;
	}
}

function validatefrmModifyCostCenter(event)
{

	if(validateForm(
	 'Cost Center Code', document.frmMaintainCostCenter.txtCostCenterCode.value, 'M', 'S'
	, 'Office Code', document.frmMaintainCostCenter.txtOfficeCode, 'M', 'S'
	, 'Office Name',document.frmMaintainCostCenter.txtOfficeName.value, 'M', 'S'
	, 'Division Code', document.frmMaintainCostCenter.txtDivCode.value, 'M', 'S'
	, 'Manager Code', document.frmMaintainCostCenter.txtManagerCode.value, 'M', 'S'
	, 'Rate', document.frmMaintainCostCenter.txtRate.value, 'O', 'M'
	, 'MO Rate', document.frmMaintainCostCenter.txtMORate.value, 'O', 'M'
	, 'Aop Rate', document.frmMaintainCostCenter.txtAopRate.value, 'O', 'M'
	, 'Currency Rate', document.frmMaintainCostCenter.txtCurrencyRate.value, 'O', 'M'
	, 'Time Zone', document.frmMaintainCostCenter.txtTimeZone.value, 'M', 'I'
	, 'Check Printing Cost Center Code', document.frmMaintainCostCenter.txtPrintCostCenterCode.value, 'O', 'S'
	))
	{
		return true;
	}
}

function validatefrmBank (event)
{

	if(validateForm(
	 'Bank Name', document.frmCreateBank.txtBankName.value, 'M', 'S'
	, 'Depository Account Number', document.frmCreateBank.txtDepAcctNo.value, 'M', 'S'
	, 'On Behalf Of',document.frmCreateBank.txtBehalf.value, 'M', 'S'
	, 'Dispersement Account Number', document.frmCreateBank.txtDesAcctNo.value, 'M', 'S'
	, 'Routing Information', document.frmCreateBank.txtRoutingInfo.value, 'M', 'S'
	, 'Plan No', document.frmCreateBank.txtPlanNo.value, 'O', 'S'
	, 'Check Logo', document.frmCreateBank.txtLogo.value, 'M', 'S'
	, 'Signature 1', document.frmCreateBank.txtSign1.value, 'M', 'S'
	, 'Signature 2', document.frmCreateBank.txtSign2.value, 'O', 'S'
	, 'Symbol 1', document.frmCreateBank.txtSymbol1.value, 'O', 'S'
	, 'Symbol 2', document.frmCreateBank.txtSymbol2.value, 'O', 'S'
	, 'Remark 1', document.frmCreateBank.txtRemark1.value, 'O', 'S'
	, 'Remark 2', document.frmCreateBank.txtRemark2.value, 'O', 'S'
	, 'Remark 3', document.frmCreateBank.txtRemark3.value, 'O', 'S'
	, 'Remark 4', document.frmCreateBank.txtRemark4.value, 'O', 'S'
	, 'Maximum Signature Amount', document.frmCreateBank.txtMaxAmt.value, 'M', 'M'
	, 'Prefund Amount', document.frmCreateBank.txtPrefundAmt.value, 'M', 'M'
	, 'Check Code', document.frmCreateBank.txtCheckCode.value, 'O', 'S'
	, 'Signatory Title 1', document.frmCreateBank.txtFirstSign.value, 'O', 'S'
	, 'Signatory Title 2', document.frmCreateBank.txtSecondSign.value, 'O', 'S'
	, 'Description', document.frmCreateBank.txtDesc.value, 'O', 'S'
	))
	{
		return true;
	}
}

function validatefrmAddress (event)
{

	if(validateForm(
	 'Address', document.frmCreateBank.txtAddressStreetAddr.value, 'M', 'S'
	, 'City', document.frmCreateBank.txtAddressCity.value, 'M', 'S'
	, 'State Code',document.frmCreateBank.txtStateCode1.value, 'M', 'S'
	, 'Country Code', document.frmCreateBank.txtCountryCode1.value, 'M', 'S'
	, 'ZIP/Postal Code', document.frmCreateBank.txtAddressZIPCode.value, 'M', 'S'
	, 'Work Phone', document.frmCreateBank.txtAddressWorkPhone.value, 'O', 'S'
	, 'Other Phone', document.frmCreateBank.txtAddressOtherPhone.value, 'O', 'S'
	, 'Fax', document.frmCreateBank.txtAddressFax.value, 'O', 'S'
	, 'Email', document.frmCreateBank.txtAddressEmail.value, 'O', 'E'
	, 'Comment', document.frmCreateBank.txtAddressComment.value, 'O', 'S'
	))
	{
		return true;
	}
}

function validatefrmContactAddress (event)
{

	if(validateForm(
	  'Last Name', document.frmCreateBank.txtContactLastName.value, 'M', 'S'
	, 'First Name', document.frmCreateBank.txtContactFirstName.value, 'M', 'S'
	, 'Middle Name',document.frmCreateBank.txtContactMiddleName.value, 'O', 'S'
	, 'Address', document.frmCreateBank.txtContactStreetAddr.value, 'M', 'S'
	, 'City', document.frmCreateBank.txtContactCity.value, 'M', 'S'
	, 'State Code',document.frmCreateBank.txtStateCode2.value, 'O', 'S'
	, 'Country Code', document.frmCreateBank.txtCountryCode2.value, 'M', 'S'
	, 'ZIP/Postal Code', document.frmCreateBank.txtContactZIPCode.value, 'M', 'S'
	, 'Work Phone', document.frmCreateBank.txtContactWorkPhone.value, 'O', 'S'
	, 'Other Phone', document.frmCreateBank.txtContactOtherPhone.value, 'O', 'S'
	, 'Home Phone', document.frmCreateBank.txtContactHomePhone.value, 'O', 'S'
	, 'Cell Phone', document.frmCreateBank.txtContactCellPhone.value, 'O', 'S'
	, 'Fax', document.frmCreateBank.txtContactFax.value, 'O', 'S'
	, 'Email', document.frmCreateBank.txtContactEmail.value, 'O', 'E'
	, 'Computer Name', document.frmCreateBank.txtContactComputerName.value, 'O', 'S'
	, 'Comment', document.frmCreateBank.txtContactComment.value, 'O', 'S'
	))
	{
		return true;
	}
}

function validatefrmBankAddress (event)
{

	if(validateForm(
	 'Address', document.frmBankAddress.txtAddressStreetAddr.value, 'M', 'S'
	, 'City', document.frmBankAddress.txtAddressCity.value, 'M', 'S'
	, 'State Code',document.frmBankAddress.txtStateCode.value, 'M', 'S'
	, 'Country Code', document.frmBankAddress.txtCountryCode.value, 'M', 'S'
	, 'ZIP/Postal Code', document.frmBankAddress.txtAddressZIPCode.value, 'M', 'S'
	, 'Work Phone', document.frmBankAddress.txtAddressWorkPhone.value, 'O', 'S'
	, 'Other Phone', document.frmBankAddress.txtAddressOtherPhone.value, 'O', 'S'
	, 'Fax', document.frmBankAddress.txtAddressFax.value, 'O', 'S'
	, 'Email', document.frmBankAddress.txtAddressEmail.value, 'O', 'E'
	, 'Comment', document.frmBankAddress.txtAddressComment.value, 'O', 'S'
	))
	{
		return true;
	}
}

function validatefrmBankContactAddress (event)
{

	if(validateForm(
	  'Last Name', document.frmBankContact.txtContactLastName.value, 'M', 'S'
	, 'First Name', document.frmBankContact.txtContactFirstName.value, 'M', 'S'
	, 'Middle Name',document.frmBankContact.txtContactMiddleName.value, 'O', 'S'
	, 'Title',document.frmBankContact.txtContactTitle.value, 'O', 'S'
	, 'Address', document.frmBankContact.txtContactStreetAddr.value, 'M', 'S'
	, 'City', document.frmBankContact.txtContactCity.value, 'M', 'S'
	, 'State Code',document.frmBankContact.txtStateCode.value, 'O', 'S'
	, 'Country Code', document.frmBankContact.txtCountryCode.value, 'M', 'S'
	, 'ZIP/Postal Code', document.frmBankContact.txtContactZIPCode.value, 'M', 'S'
	, 'Work Phone', document.frmBankContact.txtContactWorkPhone.value, 'O', 'S'
	, 'Other Phone', document.frmBankContact.txtContactOtherPhone.value, 'O', 'S'
	, 'Home Phone', document.frmBankContact.txtContactHomePhone.value, 'O', 'S'
	, 'Cell Phone', document.frmBankContact.txtContactCellPhone.value, 'O', 'S'
	, 'Fax', document.frmBankContact.txtContactFax.value, 'O', 'S'
	, 'Email', document.frmBankContact.txtContactEmail.value, 'O', 'E'
	, 'Computer Name', document.frmBankContact.txtContactComputerName.value, 'O', 'S'
	, 'Comment', document.frmBankContact.txtContactComment.value, 'O', 'S'
	))
	{
		return true;
	}
}


function validatefrmModifyBank (event)
{

	if(validateForm(
	 'Bank Name', document.frmMaintainBank.txtBankName.value, 'M', 'S'
	, 'Depository Account Number', document.frmMaintainBank.txtDepAcctNo.value, 'M', 'S'
	, 'On Behalf Of',document.frmMaintainBank.txtBehalf.value, 'M', 'S'
	, 'Dispersement Account Number', document.frmMaintainBank.txtDesAcctNo.value, 'M', 'S'
	, 'Routing Information', document.frmMaintainBank.txtRoutingInfo.value, 'M', 'S'
	, 'Plan No', document.frmMaintainBank.txtPlanNo.value, 'O', 'S'
	, 'Check Logo', document.frmMaintainBank.txtLogo.value, 'M', 'S'
	, 'Signature 1', document.frmMaintainBank.txtSign1.value, 'M', 'S'
	, 'Signature 2', document.frmMaintainBank.txtSign2.value, 'O', 'S'
	, 'Symbol 1', document.frmMaintainBank.txtSymbol1.value, 'O', 'S'
	, 'Symbol 2', document.frmMaintainBank.txtSymbol2.value, 'O', 'S'
	, 'Remark 1', document.frmMaintainBank.txtRemark1.value, 'O', 'S'
	, 'Remark 2', document.frmMaintainBank.txtRemark2.value, 'O', 'S'
	, 'Remark 3', document.frmMaintainBank.txtRemark3.value, 'O', 'S'
	, 'Remark 4', document.frmMaintainBank.txtRemark4.value, 'O', 'S'
	, 'Maximum Signature Amount', document.frmMaintainBank.txtMaxAmt.value, 'M', 'M'
	, 'Prefund Amount', document.frmMaintainBank.txtPrefundAmt.value, 'M', 'M'
	, 'Check Code', document.frmMaintainBank.txtCheckCode.value, 'O', 'S'
	, 'Signatory Title 1', document.frmMaintainBank.txtFirstSign.value, 'O', 'S'
	, 'Signatory Title 2', document.frmMaintainBank.txtSecondSign.value, 'O', 'S'
	, 'Description', document.frmMaintainBank.txtDesc.value, 'O', 'S'
	))
	{
		return true;
	}
}

function frmSubmit (event) {

	if (validateForm(
		      'User Id', document.createuser.userid.value, 'M', 'S'
		    , 'Password', document.createuser.paswd.value, 'M', 'S'
		    , 'Adjustor Code', document.createuser.adjcode.value, 'M', 'S'
		    , 'User Template', valueOfObject(document.createuser.usertemplate), 'M', 'S'
		    , 'First Name', document.createuser.txtFirstName.value, 'M', 'S'
		    , 'Middle Name', document.createuser.txtMiddleName.value, 'M', 'S'
		    , 'Last Name', document.createuser.txtLastName.value, 'M', 'S'
		    , 'User Level', valueOfObject(document.createuser.userlevel), 'M', 'S'
		    , 'Cost Center Field', document.createuser.txtCostCenterCode.value, 'M', 'S'
		    , 'User Category', valueOfObject(document.createuser.usercategory), 'M', 'S'
		    , 'User Type', valueOfObject(document.createuser.usertype), 'M', 'S'
		    , 'Password Expiry Days', document.createuser.pwdExpDays.value, 'M', 'S'
		    , 'Password Prompt Days', document.createuser.pwdExpPmtDays.value, 'M', 'S'
		    , 'Physical Street Address', document.createuser.textStreetAddr.value, 'M', 'S'
		    , 'Physical Address Field', document.createuser.textCity.value, 'M', 'S'
		    , 'Physical Address State Field', document.createuser.txtStateCode.value, 'M', 'S'
		    , 'Physical Address ZIP Field', document.createuser.textZIPCode.value, 'M', 'S'
		    , 'Physical Address Country Field', document.createuser.txtCountryCode.value, 'M', 'S'
		    , 'Physical Address Email Field', document.createuser.textAddrEmail.value, 'M', 'E'
		    , 'Mailing Street Address', document.createuser.textMailAddr.value, 'M', 'S'
		    , 'Mailing Address City Field', document.createuser.textMailCity.value, 'M', 'S'
		    , 'Mailing Address State Field', document.createuser.txtStateCode1.value, 'M', 'S'
		    , 'Mailing Address ZIP Field', document.createuser.textZIPCode1.value, 'M', 'S'
		    , 'Mailing Address Country Field', document.createuser.txtCountryCode1.value, 'M', 'S'
		    , 'Mailing Address Email Field', document.createuser.textEmail.value, 'M', 'E'



		   ) )

	{

		if(validateSupervisor())
		{
			var strUrl = "MainController.jsp?event=createUser&eventName=" + event+"&fromPage="+1 ;
			document.createuser.method = 'POST'
			document.createuser.action = strUrl
			document.createuser.submit();
		}
	}

}



function validateSupervisor()
{
 if((document.createuser.usertype.value==165)||(document.createuser.usertype.value==163) || (document.createuser.usertype.value==164))
	{
		if(document.createuser.txtSuperviser.value == '')
			{
			alert("Please select Supervisor field");
			return false;
			}
		return true;
	}
else{
	return true;
}

}



function isTime(str)
{
   if(str=="") return true;
   var indx1 = str.indexOf(":", 0);
   var strHr ;
   var strMin ;

   if(indx1==-1){
	return false;
   }

   else if(indx1==0){
	return false;
   }

   else
   {
    if (indx1 == 2)
	   strHr = str.substring(0,2) ;
	else if(indx1 == 1)
	   strHr = str.substring(0,1) ;
	if (isNumber(strHr) && (eval(strHr)>=0) && (eval(strHr)<24))
	{
		if (indx1 == 2)
		   strMin = str.substring(3,5) ;
		else if(indx1 == 1)
		   strMin = str.substring(2,4) ;
	    if (isNumber(strMin) && (eval(strMin)>=0) && (eval(strMin)<60))
	       return true;
	    else
	       return false;
	}
	else
	    return false;
   }
}



function validateForm() {
  //data format = array containing 'fieldName', 'data', 'mandatoryness', 'dataType'

  var errString="The following error(s) has occurred :-\n\n";

  var args = validateForm.arguments;
  for (var i=0; i<(args.length); i+=4) {
  	//alert ("label:"+args[i] + " value:" + args[i+1] + " mandatoryness:" + args[i+2] + " dataType:" + args[i+3]);

	if(args[i + 2]=='M') {
		if (args[i+1]=="") {
		   errString += "  - " + args[i] + " is mandatory.\n";
		} else {
		   if(args[i+3]=='D') {
			if (!isDate(args[i+1])) {
		   	  errString += "  - " + args[i] + " should be a date field.\n";
			}
		   } else if(args[i+3]=='I') {
			if (!isNumber(args[i+1])) {
		   	  errString += "  - " + args[i] + " should be a number value.\n";
			}
		   } else if(args[i+3]=='M') {
			if (!isDecimal(args[i+1])) {
		   	  errString += "  - " + args[i] + " should be a decimal field.\n";
			}
		   } else if(args[i+3]=='P') {
			if (!isPhone(args[i+1])) {
		   	  errString += "  - " + args[i] + " should be a phone no.\n";
			}
		   }else if(args[i+3]=='Q') {
			if (!isSSN(args[i+1])) {
		   	  errString += "  - " + args[i] + "  should be a valid SSN.\n";
			}
		   }else if(args[i+3]=='E') {
			if (!isEmail(args[i+1])) {
		   	  errString += "  - " + args[i] + " should be an email address.\n";
			}
		   } else if(args[i+3]=='T') {
			if (!isTime(args[i+1])) {
		   	  errString += "  - " + args[i] + "- Valid values allowed are 00:00 thru 23:59.\n";
			}
		   }
		}
	}

	else {
		if (args[i+1]!="") {
		   if(args[i+3]=='D') {
			if (!isDate(args[i+1])) {
		   	  errString += "  - " + args[i] + " should be a date field.\n";
			}
		   } else if(args[i+3]=='I') {
			if (!isNumber(args[i+1])) {
		   	  errString += "  - " + args[i] + " should be a number value.\n";
			}
		   } else if(args[i+3]=='M') {
			if (!isDecimal(args[i+1])) {
		   	  errString += "  - " + args[i] + " should be a decimal field.\n";
			}
		   } else if(args[i+3]=='P') {
			if (!isPhone(args[i+1])) {
		   	  errString += "  - " + args[i] + " should be a phone no.\n";
			}
		   } else if(args[i+3]=='E') {
			if (!isEmail(args[i+1])) {
		   	  errString += "  - " + args[i] + " should be an email address.\n";
			}
		   } else if(args[i+3]=='T') {
			if (!isTime(args[i+1])) {
		   	  errString += "  - " + args[i] + "-Valid values allowed are 00:00 thru 23:59.\n";
			}
		   } else if(args[i+3]=='DC') {
		   	if (!DateComparison(args[(i-(2*4))+1],args[(i-(1*4))+1])) {
		   	  errString += "  - " + args[(i-(2*4))] + " " + "should be less than" +" " +  args[(i-(1*4))] + "\n";
			}
		   }
	}
  }
}
  if (errString=='The following error(s) has occurred :-\n\n') {
     	return true;
  } else {
  	alert (errString);
	return false;
  }
}







/***************######################################*******************/
/***************######################################*******************/
/***************######################################*******************/
/***************######################################*******************/
/***************######################################*******************/
/***************######################################*******************/
/**********************************************************************************/
/****        Tihs Function clear the form without submiting.                   ****/
/****        Input parameter is HTML form name.                                ****/
/**********************************************************************************/
function formClear(frm) {
	vCount = frm.elements.length;

	for( i=0; i<vCount; i++) {
		if (( frm.elements[i].type == 'text') || ( frm.elements[i].type == 'textarea')) {
			frm.elements[i].value = "";
		}
		if ( frm.elements[i].type == 'checkbox') {
			frm.elements[i].checked = false;
		}
	}
	return;
}


/**********************************************************************************/
/****        This function change the value of a field to upper case.          ****/
/****        It take the field name as input parameter.                        ****/
/**********************************************************************************/
function changeUpper(item) {
	item.value = item.value.toUpperCase();
}


/**********************************************************************************/
/****	This function check whether a string is Integer number or not.         ****/
/****	It take string as input parameter and return boolean as output.        ****/
/**********************************************************************************/
function isNumber(str) {
	var isnum=true;
	if ((str==null) || (str=="")){
		isnum=true;
		return isnum;
   	}

	for(j=0;j<str.length;j++){

		if((str.substring(j,j+1) !="0") &&
		   (str.substring(j,j+1) !="1") &&
		   (str.substring(j,j+1) !="2") &&
		   (str.substring(j,j+1) !="3") &&
		   (str.substring(j,j+1) !="4") &&
		   (str.substring(j,j+1) !="5") &&
		   (str.substring(j,j+1) !="6") &&
		   (str.substring(j,j+1) !="7") &&
		   (str.substring(j,j+1) !="8") &&
		   (str.substring(j,j+1) !="9"))   {
			isnum=false;
		}
	}
	return isnum;
}



/**********************************************************************************/
/****	This function check whether a string is decimal number or not.        *****/
/****	It take string as input parameter and return boolean as output.       *****/
/**********************************************************************************/
function isDecimal(str){
	var isnum=true;
	if ((str==null) || (str=="")){
		isnum=true;
		return isnum;
	}

	for(j=0;j<str.length;j++){
		if(j==0 && str.substring(j, j+1) =="-") { continue; } //if '-' is found at first position

		if((str.substring(j,j+1) !="0") &&
		   (str.substring(j,j+1) !="1") &&
		   (str.substring(j,j+1) !="2") &&
		   (str.substring(j,j+1) !="3") &&
		   (str.substring(j,j+1) !="4") &&
		   (str.substring(j,j+1) !="5") &&
		   (str.substring(j,j+1) !="6") &&
		   (str.substring(j,j+1) !="7") &&
		   (str.substring(j,j+1) !="8") &&
		   (str.substring(j,j+1) !="9") &&
		   (str.substring(j,j+1) !="."))   {
			isnum=false;
		}
	}
	var indx1 = str.indexOf(".", 0);
	if(indx1!=-1){
		var indx2=str.indexOf(".", indx1+1);
		if(indx2!=-1) isnum=false;
	}
	return isnum;
}

/**********************************************************************************/
/****	This function validate the birth date. The Valid Birth Date			   ****/
/****	should not be less than 150 years from today's  date.                  ****/
/****	It takes two input parameter birth date and sysdate and return		   ****/
/****	output as boolean.	Here bDate is an object and sysDate is an Date	   ****/
/**********************************************************************************/


function validateBirthDate(bDate,sysDate)
{

	var year1,year2
	var month1, month2
	var day1, day2

	if (bDate.value == "") return true;
	if (validateDate(bDate))
	{
		bDate = bDate.value;
		year1 = bDate.substring(6,10)
		month1 = bDate.substring(0,2)
		day1 = bDate.substring(3,5)

		year2 = sysDate.substring(6,10)
		year2 = year2 - 150;
		month2 = sysDate.substring(0,2)
		day2 = sysDate.substring(3,5)

		if (year1+month1+day1 >= year2+month2+day2)
		{
			return true;
		}
		else
		{	alert("Birth Date should not be less than \n150 years from todays date.");
			return false;
		}
	}
}



/**********************************************************************************/
/****	This function validate the input in a field is a valid date or not.    ****/
/****	It take the field name and field prompt as input parameter.            ****/
/**********************************************************************************/
function validateDate(date) {

   	if((date.value=="")||(date.value == date.defaultValue)) return true;

	if(!isDate(date.value)){
	   alert("Invalid Date\nFormat : mm/dd/yyyy");
	   date.select();
	   date.focus();
	   return false;
	} else {

	   var f1 = date.value.indexOf("/");
     	   var f2 = date.value.indexOf("/", f1+1);

	   var mon = date.value.substring(0, f1);
	   var day = date.value.substring(f1+1, f2);
	   var year = date.value.substring(f2+1, date.value.length);

	   if(parseFloat(year) < 1000 )	year = 2000 + parseFloat(year);

	   	if ((parseFloat(day) < 10 )&&(day.length == 1)) day = '0'+day;
	   	if ((parseFloat(mon) < 10 )&&(mon.length == 1)) mon = '0'+mon;
	   	date.value = mon + "/" + day + "/" + year;

	   	return true;
	}
}


/**********************************************************************************/
/****	This function check whether a string has date format or not.           ****/
/****	It take string as input parameter and return boolean as output.        ****/
/**********************************************************************************/
function isDate(str){
	var f1 = str.indexOf("/");
	var f2=-1;
	if(f1!=-1){
		f2 = str.indexOf("/", f1+1);
	}

	if(f1==-1||f2==-1||f1==0){
		return false;
	}

   var str1 = str.substring(0, f1);
   if((!isNumber(str1)) || (str1>12||str1<=0)){return false;}

   else {
	var str2 = (str.substring(f1+1, f2));
	var dd = parseFloat(str2);
	if(dd > 31 || dd <= 0){
		return false;
	}

	else{
		var str3 = str.substring(f2+1, str.length);
		if(!isNumber(str3) || str3=="" || str3==null||parseFloat(str3)>9999) return false;

	}

   }

   if(!isValidDate(str)){
	return false;
   }

   return true;
}

function DateComparison(dateFrom, dateTo)
{

var year1,year2,curyear
var month1, month2,curmonth
var day1, day2,curday

year1 = dateFrom.substring(6,10)
year2 = dateTo.substring(6,10)
month1 = dateFrom.substring(0,2)
month2 = dateTo.substring(0,2)
day1 = dateFrom.substring(3,5)
day2 = dateTo.substring(3,5)
if (year1+month1+day1 > year2+month2+day2)
{
  	return false
}
else
{
	return true;
}
}


/**********************************************************************************/
/*****	This function check an input string is valid date or not.            ******/
/*****	It is Used to validate leap year.                                    ******/
/*****	It is Used by isDate function only.                                  ******/
/**********************************************************************************/
function isValidDate(str){
   var f1 = str.indexOf("/");
   var f2 = str.indexOf("/", f1+1);
   var month = str.substring(0, f1);
   var day = str.substring(f1+1, f2);
   var year = str.substring(f2+1, str.length);
   var mon = parseFloat(month) - 1;
   if(year=="0") year="2000";

   var d = new Date(year, mon, day);
   if(mon==d.getMonth()){
	return true;
   }
   else return false;
}



/**********************************************************************************/
/*****	This function check an input string is valid email id or not.            ******/
/*****	It Used by isDate function only.                                     ******/
/**********************************************************************************/
function isEmail(str){
   if(str=="") return true;
   var indx1 = str.indexOf("@", 0);
   var indx2 = str.indexOf(".", indx1+1);
   var indx3 = str.indexOf(" ");
   var indx4 = str.indexOf(",");
   var indx5 = str.indexOf(";");
   var indx6 = str.lastIndexOf(".");
   

  if ( indx3 != -1 ) {
    return false;
  }
  
  if ( indx4 != -1 ) {
    return false;
  }
  
  if ( indx5 != -1 ) {
    return false;
  }  

   if(indx1==-1||indx2==-1)
	return false;

   else if(indx1==0){
	return false;
   }

   else if(indx2-indx1<=1) {
	return false;
   }

   else if(indx2 == (str.length-1)) {
	return false;
   }
   
   else if(((parseInt(str.length)-1)-parseInt(indx6))>3)
   {    
   return false;
   }

   else return true;
}


/**********************************************************************************/
/*****	This function check an input string is valid phone no or not.        ******/
/**********************************************************************************/

function isPhone(str){

  var flag = true;
  for(var i=0; i<str.length; i++){
	if(str.substring(i, i+1)!="0" &&
           str.substring(i, i+1)!="1" &&
           str.substring(i, i+1)!="2" &&
           str.substring(i, i+1)!="3" &&
           str.substring(i, i+1)!="4" &&
           str.substring(i, i+1)!="5" &&
           str.substring(i, i+1)!="6" &&
           str.substring(i, i+1)!="7" &&
           str.substring(i, i+1)!="8" &&
           str.substring(i, i+1)!="9" &&
           str.substring(i, i+1)!="-" )
		flag = false;
  }
  return flag;
}


/*

function isPhone(str){

  var flag = true;

   var indx1 = str.indexOf("-", 0);
   var indx2 = str.indexOf("-", indx1+1);
   var indx3 = str.indexOf("E", indx2+1);
   var indx4 = str.indexOf("-", indx2+1);

   if((indx1!=3) || (indx2!=7) || (indx4!=-1))
   {
   	flag = false;

   	return flag;
   }
   if(str.length<15 || str.length>20 || str.length==16)
   {
   	flag = false;

   	return flag;
   }
   else if(str.length==15)
   {
   	for(var i=0; i<str.length; i++)
   	{
		if(str.substring(i, i+1)!="0" &&
		   str.substring(i, i+1)!="1" &&
		   str.substring(i, i+1)!="2" &&
		   str.substring(i, i+1)!="3" &&
		   str.substring(i, i+1)!="4" &&
		   str.substring(i, i+1)!="5" &&
		   str.substring(i, i+1)!="6" &&
		   str.substring(i, i+1)!="7" &&
		   str.substring(i, i+1)!="8" &&
		   str.substring(i, i+1)!="9" &&
		   str.substring(i, i+1)!="-" )
	   	{
	   		flag = false;

	   		return flag;
	   	}
   	}
   }
   else if(str.length>16 && str.length<=20)
   {
   	if(indx3!=15)
   	{
   		flag = false;

   		return flag;
   	}
   	if(indx3==15)
   	{

		for(var j=0; j<15; j++)
		{
			if(str.substring(j, j+1)!="0" &&
			   str.substring(j, j+1)!="1" &&
			   str.substring(j, j+1)!="2" &&
			   str.substring(j, j+1)!="3" &&
			   str.substring(j, j+1)!="4" &&
			   str.substring(j, j+1)!="5" &&
			   str.substring(j, j+1)!="6" &&
			   str.substring(j, j+1)!="7" &&
			   str.substring(j, j+1)!="8" &&
			   str.substring(j, j+1)!="9" &&
			   str.substring(j, j+1)!="-" )
			{
				flag = false;

				return flag;
			}
		}
		for(var k=16; k<str.length; k++)
		{
			if(str.substring(k, k+1)!="0" &&
			   str.substring(k, k+1)!="1" &&
			   str.substring(k, k+1)!="2" &&
			   str.substring(k, k+1)!="3" &&
			   str.substring(k, k+1)!="4" &&
			   str.substring(k, k+1)!="5" &&
			   str.substring(k, k+1)!="6" &&
			   str.substring(k, k+1)!="7" &&
			   str.substring(k, k+1)!="8" &&
			   str.substring(k, k+1)!="9" )
			{
				flag = false;

				return flag;
			}
		}
   	}
   }
   else
   {

   	return true;
   }

return flag;
}

*/

/**********************************************************************************/
/*****	This function capitalixe each and every character typed.             ******/
/*****	It Used on onKeyPress event.                                         ******/
/*****	USAGE : <INPUT TYPE="text" onKeyPress="makeUpper();">                ******/
/**********************************************************************************/

//function makeUpper() {
// if (event.keyCode>=97 && event.keyCode<=122) {
//	event.keyCode = event.keyCode - 32;
// }
//}


/**********************************************************************************/
/****	This function validates whether input string exceeds 240 chars or not. ****/
/****	It take the field value as input parameter.                            ****/
/****	Call this function on onKeyPress event in the textarea.                ****/
/**********************************************************************************/
function validateDescriptionOnKeyPress (descObj) {
	var count = 0;
	count  = descObj.value.length + 1;
	if (count > 239) {
	   alert ('\n\nDescription can not exceed 240 characters.\n\n');
      	   event.returnValue = false;
	}

}

/**********************************************************************************/
/****	This function validates whether input string exceeds 240 chars or not. ****/
/****	It take the field object as input parameter.                           ****/
/****	Call this function on onBlur event in the textarea.                    ****/
/**********************************************************************************/
function validateDescriptionOnBlur (descObj) {
	var count = 0;
	count  = descObj.value.length;
	if (count > 239) {
	   alert ('\n\nDescription can not exceed 240 characters.\n\n');
	   descObj.focus();
	   descObj.select();
	}

}


/**********************************************************************************/
/****	This function validates whether input string is decimal  or not.       ****/
/****	It take the field object as input parameter.                           ****/
/****	Call this function on onBlur event in the textarea.                    ****/
/**********************************************************************************/
function validateDecimal(decObj) {
	if (!isDecimal(decObj.value)) {
	   alert ('\n\nIt should be a proper decimal field.\n\n');
	   decObj.focus();
	   decObj.select();
	}

}

/**********************************************************************************/
/****	This function validates whether input string is integer or not.        ****/
/****	It take the field object as input parameter.                            ****/
/****	Call this function on onBlur event in the textarea.                    ****/
/**********************************************************************************/
function validateNumber(numObj) {
	if (!isNumber(numObj.value)) {
	   alert ('\n\nIt should be a proper number field.\n\n');
	   numObj.focus();
	   numObj.select();
	}

}

function validateMandatorynes(manObj) {
	if (manObj.value=="") {
	   alert ('\n\nThis field should not be blank.\n\n');
	   manObj.focus();
	   manObj.select();
	}

}


function validatePassword(pasObj) {
	if (pasObj.value.length <7 || pasObj.value.length >10) {
	   alert ('\n\nPassword should be atleast 7 characters long\nand atmost 10 characters long.\n');
	   pasObj.focus();
	   pasObj.select();
	}

}



function valueOfObject (frmObj) {

   //alert ('NAME :'+frmObj.name);
   //alert ('type :'+frmObj.type);
   //alert ('val :'+frmObj.value);

   if(frmObj.type == 'text') { //for textbox

	return frmObj.value;

   } else if(frmObj.type == 'password') { //for password

	return frmObj.value;

   } else if(frmObj.type == 'textarea') {//for textarea

	return frmObj.value;

   } else  if(frmObj.type == 'hidden') {//for hidden field

	return frmObj.value;

   } else  if(frmObj.type == 'select-one') {//for select one

	var sel;
	for (var i=0; i<frmObj.options.length; i++) {
		if (frmObj.options[i].selected) {
		   sel = frmObj.options[i].value;
		}
	}
	return sel;

   } else  if(frmObj.type == 'select-multiple') {//for select multiple
	return "";
   } else if(frmObj.type == 'radio') {//for radio button
	return "";
   } else  if(frmObj.type == 'checkbox') { //for checkbox
	return "";
   } else  if(frmObj.type == 'hidden') {//for hidden field
	return "";

   } else  if(frmObj.type == 'button') {
	return "";
   } else {

	return "";
   }

}


/**********************************************************************************/
/*****	This function allows to enter only numeric characters.               ******/
/*****	It Used on onKeyPress event.                                         ******/
/*****	USAGE : <INPUT TYPE="text" onKeyPress="onlyNumber();">                ******/
/**********************************************************************************/
function onlyNumber() {
  if (event.keyCode<48 || event.keyCode>57) {
      event.returnValue = false;
  }
}

function checkDecimal() {
  if (event.keyCode<46 || event.keyCode>57 || event.keyCode==47) {
      event.returnValue = false;
  }

}



function makeUpper()
{
  //if ((event.keyCode<48 || event.keyCode>57) && (event.keyCode<65 || event.keyCode>90) && (event.keyCode<97 || event.keyCode>122) && (event.keyCode!=32))
  if ((event.keyCode<40 || event.keyCode>58) && (event.keyCode<63 || event.keyCode>126) && (event.keyCode<32 || event.keyCode>33) && (event.keyCode<36 || event.keyCode>37))
  //if ((event.keyCode<40 || event.keyCode>58) && (event.keyCode<63 || event.keyCode>126) && (event.keyCode<32 || event.keyCode>33) && (event.keyCode<36 || event.keyCode>37) && (event.keyCode!=38) && (event.keyCode!=39) && (event.keyCode!=35) && (event.keyCode!=59) && (event.keyCode!=60)  && (event.keyCode!=62))
  {
      event.returnValue = false;
  }
  else if (event.keyCode>=97 && event.keyCode<=122)
  {
	event.keyCode = event.keyCode - 32;
  }
}

/************ This function is for the fields which allow Special Characters ************/
function makeUpperSpecial()
{
  //if ((event.keyCode<48 || event.keyCode>57) && (event.keyCode<65 || event.keyCode>90) && (event.keyCode<97 || event.keyCode>122) && (event.keyCode!=32))
  //***if ((event.keyCode<40 || event.keyCode>58) && (event.keyCode<63 || event.keyCode>126) && (event.keyCode<32 || event.keyCode>33) && (event.keyCode<36 || event.keyCode>37))
  if ((event.keyCode<40 || event.keyCode>58) && (event.keyCode<63 || event.keyCode>126) && (event.keyCode<32 || event.keyCode>33) && (event.keyCode<36 || event.keyCode>37) && (event.keyCode!=38) && (event.keyCode!=39) && (event.keyCode!=35) && (event.keyCode!=59) && (event.keyCode!=60)  && (event.keyCode!=62))
  {
      event.returnValue = false;
  }
  else if (event.keyCode>=97 && event.keyCode<=122)
  {
	event.keyCode = event.keyCode - 32;
  }
}
/************ This function is for Organizational Name fields only ************/
function makeUpperForOrgName()
{
  //if ((event.keyCode<48 || event.keyCode>57) && (event.keyCode<65 || event.keyCode>90) && (event.keyCode<97 || event.keyCode>122) && (event.keyCode!=32))
  //***if ((event.keyCode<40 || event.keyCode>58) && (event.keyCode<63 || event.keyCode>126) && (event.keyCode<32 || event.keyCode>33) && (event.keyCode<36 || event.keyCode>37))
  //if ((event.keyCode<40 || event.keyCode>58) && (event.keyCode<63 || event.keyCode>126) && (event.keyCode<32 || event.keyCode>33) && (event.keyCode<36 || event.keyCode>37) && (event.keyCode!=39)  && (event.keyCode!=35) && (event.keyCode!=60)  && (event.keyCode!=62))
  //**//if((event.keyCode<48 || event.keyCode>57) && (event.keyCode<65 || event.keyCode>90) && (event.keyCode<97 || event.keyCode>122) && (event.keyCode!=35) && (event.keyCode!=32))
  if ((event.keyCode<40 || event.keyCode>58) && (event.keyCode<63 || event.keyCode>126) && (event.keyCode<32 || event.keyCode>33) && (event.keyCode<36 || event.keyCode>37) && (event.keyCode!=38) && (event.keyCode!=39) && (event.keyCode!=35) && (event.keyCode!=59) && (event.keyCode!=60)  && (event.keyCode!=62))
  {
      event.returnValue = false;
  }
  else if (event.keyCode>=97 && event.keyCode<=122)
  {
	event.keyCode = event.keyCode - 32;
  }
}

/************ This function is for Name fields only ************/
function makeUpperForName()
{
  //if ((event.keyCode<48 || event.keyCode>57) && (event.keyCode<65 || event.keyCode>90) && (event.keyCode<97 || event.keyCode>122) && (event.keyCode!=32))
  //***if ((event.keyCode<40 || event.keyCode>58) && (event.keyCode<63 || event.keyCode>126) && (event.keyCode<32 || event.keyCode>33) && (event.keyCode<36 || event.keyCode>37))
  //if ((event.keyCode<40 || event.keyCode>58) && (event.keyCode<63 || event.keyCode>126) && (event.keyCode<32 || event.keyCode>33) && (event.keyCode<36 || event.keyCode>37) && (event.keyCode!=38) && (event.keyCode!=39) && (event.keyCode!=60)  && (event.keyCode!=62))
  //**//if((event.keyCode<48 || event.keyCode>57) && (event.keyCode<65 || event.keyCode>90) && (event.keyCode<97 || event.keyCode>122) && (event.keyCode!=38) && (event.keyCode!=39) && (event.keyCode!=44) && (event.keyCode!=32))
  if ((event.keyCode<40 || event.keyCode>58) && (event.keyCode<63 || event.keyCode>126) && (event.keyCode<32 || event.keyCode>33) && (event.keyCode<36 || event.keyCode>37) && (event.keyCode!=38) && (event.keyCode!=39) && (event.keyCode!=35) && (event.keyCode!=59) && (event.keyCode!=60)  && (event.keyCode!=62))
  {
      event.returnValue = false;
  }
  else if (event.keyCode>=97 && event.keyCode<=122)
  {
	event.keyCode = event.keyCode - 32;
  }
}

/************ This function is for Comment fields only ************/
function makeUpperComment()
{
  //if ((event.keyCode<48 || event.keyCode>57) && (event.keyCode<65 || event.keyCode>90) && (event.keyCode<97 || event.keyCode>122) && (event.keyCode!=32))
  //***if ((event.keyCode<40 || event.keyCode>58) && (event.keyCode<63 || event.keyCode>126) && (event.keyCode<32 || event.keyCode>33) && (event.keyCode<36 || event.keyCode>37))
  //if ((event.keyCode<40 || event.keyCode>58) && (event.keyCode<63 || event.keyCode>126) && (event.keyCode<32 || event.keyCode>33) && (event.keyCode<36 || event.keyCode>37) && (event.keyCode!=38) && (event.keyCode!=39) && (event.keyCode!=35) && (event.keyCode!=44) && (event.keyCode!=60)  && (event.keyCode!=62))
  //**//if((event.keyCode<48 || event.keyCode>57) && (event.keyCode<65 || event.keyCode>90) && (event.keyCode<97 || event.keyCode>122) && (event.keyCode!=38) && (event.keyCode!=44) && (event.keyCode!=32))
  if ((event.keyCode<40 || event.keyCode>58) && (event.keyCode<63 || event.keyCode>126) && (event.keyCode<32 || event.keyCode>33) && (event.keyCode<36 || event.keyCode>37) && (event.keyCode!=38) && (event.keyCode!=39) && (event.keyCode!=35) && (event.keyCode!=59) && (event.keyCode!=60)  && (event.keyCode!=62))
  {
      event.returnValue = false;
  }
  else if (event.keyCode>=97 && event.keyCode<=122)
  {
	event.keyCode = event.keyCode - 32;
  }
}

/************ This function is for Address fields only ************/
function makeUpperAddress()
{
  //***if ((event.keyCode<40 || event.keyCode>57) && (event.keyCode<63 || event.keyCode>126) && (event.keyCode<32 || event.keyCode>33) && (event.keyCode<35 || event.keyCode>37))
  if ((event.keyCode<40 || event.keyCode>57) && (event.keyCode<63 || event.keyCode>126) && (event.keyCode<32 || event.keyCode>33) && (event.keyCode<36 || event.keyCode>37) && (event.keyCode!=39) && (event.keyCode!=60) && (event.keyCode!=62))
  {
      event.returnValue = false;
  }
  else if (event.keyCode>=97 && event.keyCode<=122)
  {
	event.keyCode = event.keyCode - 32;
  }
}

/**********************************************************************************/
/*****	This function allows to enter only alphanumeric characters.          ******/
/*****	It Used on onKeyPress event.                                         ******/
/*****	USAGE : <INPUT TYPE="text" onKeyPress="onlyAlphaNum();">             ******/
/**********************************************************************************/
function makeAlphaNum()
{
  //***if ((event.keyCode<48 || event.keyCode>57) && (event.keyCode<65 || event.keyCode>90) && (event.keyCode<97 || event.keyCode>122) && (event.keyCode!=32))
  if ((event.keyCode<48 || event.keyCode>57) && (event.keyCode<65 || event.keyCode>90) && (event.keyCode<97 || event.keyCode>122) && (event.keyCode!=32) && (event.keyCode!=39) && (event.keyCode!=35) && (event.keyCode!=45) && (event.keyCode!=60) && (event.keyCode!=62) && (event.keyCode!=47) && (event.keyCode!=92) )
  {
      event.returnValue = false;
  }
  else if (event.keyCode>=97 && event.keyCode<=122)
  {
	event.keyCode = event.keyCode - 32;
  }
}

/************ This function is for Code and Document Type fields only ************/
function makeAlphaNumCode()
{
  //***if ((event.keyCode<48 || event.keyCode>57) && (event.keyCode<65 || event.keyCode>90) && (event.keyCode<97 || event.keyCode>122) && (event.keyCode!=32))
  //if ((event.keyCode<48 || event.keyCode>57) && (event.keyCode<65 || event.keyCode>90) && (event.keyCode<97 || event.keyCode>122) && (event.keyCode!=32) && (event.keyCode!=39) && (event.keyCode!=35) && (event.keyCode!=45) && (event.keyCode!=60) && (event.keyCode!=62))
  if ((event.keyCode<40 || event.keyCode>58) && (event.keyCode<63 || event.keyCode>126) && (event.keyCode<32 || event.keyCode>33) && (event.keyCode<36 || event.keyCode>37) && (event.keyCode!=38) && (event.keyCode!=39) && (event.keyCode!=35) && (event.keyCode!=59) && (event.keyCode!=60)  && (event.keyCode!=62))
  {
      event.returnValue = false;
  }
  else if (event.keyCode>=97 && event.keyCode<=122)
  {
	event.keyCode = event.keyCode - 32;
  }
}

/***********************************************************************************************************/
/*****	This function allows to enter only alphanumeric characters specially for description          ******/
/*****	It Used on onKeyPress event.                                        			      ******/
/*****	USAGE : <INPUT TYPE="text" onKeyPress="makeAlphaNumDescription();">                           ******/
/***********************************************************************************************************/
function makeAlphaNumDescription()
{
  //***if ((event.keyCode<48 || event.keyCode>57) && (event.keyCode<65 || event.keyCode>90) && (event.keyCode<97 || event.keyCode>122) && (event.keyCode!=32))
  //if ((event.keyCode<48 || event.keyCode>57) && (event.keyCode<65 || event.keyCode>90) && (event.keyCode<97 || event.keyCode>122) && (event.keyCode!=32) && (event.keyCode!=39) && (event.keyCode!=35) && (event.keyCode!=38) && (event.keyCode!=44) && (event.keyCode!=60) && (event.keyCode!=62))
  //**//if((event.keyCode<48 || event.keyCode>57) && (event.keyCode<65 || event.keyCode>90) && (event.keyCode<97 || event.keyCode>122) && (event.keyCode!=38) && (event.keyCode!=44) && (event.keyCode!=32))
  if ((event.keyCode<40 || event.keyCode>58) && (event.keyCode<63 || event.keyCode>126) && (event.keyCode<32 || event.keyCode>33) && (event.keyCode<36 || event.keyCode>37) && (event.keyCode!=38) && (event.keyCode!=39) && (event.keyCode!=35) && (event.keyCode!=59) && (event.keyCode!=60)  && (event.keyCode!=62))
  {
      event.returnValue = false;
  }
  else if (event.keyCode>=97 && event.keyCode<=122)
  {
	event.keyCode = event.keyCode - 32;
  }
}


/**********************************************************************************/
/****	This function checks whether a SSN is valid or not.                    ****/
/****	It take string as input parameter and return boolean as output.        ****/
/**********************************************************************************/
function isSSN(str) {
	var isssn=true;
	if ((str==null) || (str=="")){
		isssn=true;
		return isssn;
   	}
	var matchArr = str.match(/^(\d{3})-?\d{2}-?\d{4}$/);
	var numDashes = str.split('-').length - 1;
	if (matchArr == null || numDashes == 1) {
		isssn=false;
		return isssn;
	}
	return isssn;
  }

/**********************************************************************************/
/*****	This function allows to enter only a valid SSN		               ******/
/*****	It Used on onBlur event.                                         ******/
/*****	USAGE : Call with SSN.value and the fieldname as parameter	*****/
/**********************************************************************************/

function SSNValidation(ssn,ssnfield)
{
	if (ssnfield.value=="") return true;
	var matchArr = ssn.match(/^(\d{3})-?\d{2}-?\d{4}$/);
	var numDashes = ssn.split('-').length - 1;
	if (matchArr == null || numDashes == 1)
	{
		alert('Invalid SSN. Must be 9 digits or in the form NNN-NN-NNNN.');
		msg = "does not appear to be valid";
		ssnfield.select();
		ssnfield.focus();
		return false;
	}
	else
	{
		return true;
	}

}




/**********************************************************************************/
/*****	This function is called on click of lookup button                    ******/
/*****	lkpLookUp(code,desc,token,coverage,listtitle) is for general lookup and        ******/
/*****	function lkpLookUpSevBased(code,desc,token,coverage,sev,listtitle) is          *****/
/*****  for lookup which populate data based on Coverage.		     *****/
/*****  Here Code is for Text field name for Code.			     *****/
/*****       desc is for Text field name for Description.                     *****/
/*****       type is for property id.                                         *****/
/*****       cover is for Coverage Type.                  		      *****/
/**********************************************************************************/


var strUrl;
var tmpWin;

function lkpLookUp(code,desc,token,coverage,listtitle)
{

var txtcode = code;
var txtdesc = desc;
var strtoken = token;
var strCov  = coverage;
var strTitle = listtitle;
//alert("COV "+coverage);
strUrl = 'MainController.jsp?transaction=no&event=LookUp&codeField=' + txtcode + '&descField=' + txtdesc + '&type=' +strtoken +'&code=' + document.all(txtcode).value + '&description=' + document.all(txtdesc).value +'&coverage=' + strCov + '&listtitle=' + strTitle
//alert(strUrl);
if(!tmpWin)
{
tmpWin = window.open(strUrl, "title", 'height=450,width=600,top=10,left=10,scrollbars=yes');
}
else
{
tmpWin.close();
tmpWin = window.open(strUrl, "title", 'height=450,width=600,top=10,left=10,scrollbars=yes');
}
return tmpWin;
}

function lkpLookUpSevBased(code,desc,token,coverage,sev,listtitle)
{
var txtcode = code;
var txtdesc = desc;
var strtoken = token;
var strCov = coverage;
var strSev = sev;
var strTitle = listtitle;
//alert("COV "+coverage);
strUrl = 'MainController.jsp?event=LookUp&codeField=' + txtcode + '&descField=' + txtdesc + '&type=' +strtoken +'&code=' + document.all(txtcode).value + '&description=' + document.all(txtdesc).value +'&coverage=' + strCov +'&severity=' + strSev + '&listtitle=' + strTitle
//alert(strUrl);
if(!tmpWin)
{
tmpWin = window.open(strUrl, "title", 'height=450,width=600,top=10,left=10,scrollbars=yes');
}
else
{
tmpWin.close();
tmpWin = window.open(strUrl, "title", 'height=450,width=600,top=10,left=10,scrollbars=yes');
}
return tmpWin;
}

function lkpLookUpRsrvBased(code,desc,token,coverage,rsrv,listtitle)
{
var txtcode = code;
var txtdesc = desc;
var strtoken = token;
var strCov = coverage;
var strSev = rsrv;
var strTitle = listtitle;
//alert("COV "+coverage);
strUrl = 'MainController.jsp?event=LookUp&codeField=' + txtcode + '&descField=' + txtdesc + '&type=' +strtoken +'&code=' + document.all(txtcode).value + '&description=' + document.all(txtdesc).value +'&coverage=' + strCov +'&reserve=' + strSev + '&listtitle=' + strTitle
//alert(strUrl);
if(!tmpWin)
{
tmpWin = window.open(strUrl, "title", 'height=450,width=600,top=10,left=10,scrollbars=yes');
}
else
{
tmpWin.close();
tmpWin = window.open(strUrl, "title", 'height=450,width=600,top=10,left=10,scrollbars=yes');
}
return tmpWin;
}

/*******************************************************************************************/
/***********   This function converts the number in decimal format to its text format  oken**/
/*******************************************************************************************/

	var n = "";

	function amountText(input)
	{
		var search11 = new String(input)
		var SearchString = search11.indexOf(".");

		//alert(SearchString);
		var Search1 = input;
		if (SearchString==-1)
		{
			var Return1 = convert(Search1) + "Dollars" + " and No Cents";
			return Return1;
		}

		else
		{
			var String1= Search1.split(".")
			var StringBeforedecimal = String1[0];
			var StringAfterdecimal = String1[1];

			if(Math.round(StringAfterdecimal) == 0)
			{
				if (StringBeforedecimal == ""){
					var Return1 = " Zero Dollars" + " and " + " No Cents";
				}
				else {
					var Return1 = convert(StringBeforedecimal) + " Dollars" + " and No Cents";
				}
				return Return1;
			}

			else
			{
				if (StringBeforedecimal == 0) {
				var Return1 = " Zero Dollars" + " and " + convert(StringAfterdecimal) + "Cent";
				} else {
					var Return1 = convert(StringBeforedecimal) + "Dollars" + " and " + convert(StringAfterdecimal) + "Cent";
				}

				//alert("return"+ Return1);
				return Return1;
			}
		}
	}


	function d1(x)
	{ // single digit terms
		switch(x)
		{
			case '0': n= ""; break;
			case '1': n= " One "; break;
			case '2': n= " Two "; break;
			case '3': n= " Three "; break;
			case '4': n= " Four "; break;
			case '5': n= " Five "; break;
			case '6': n= " Six "; break;
			case '7': n= " Seven "; break;
			case '8': n= " Eight "; break;
			case '9': n= " Nine "; break;
			default: n = "Not a Number";
		}
		return n;
	}

	function d2(x)
	{ // 10x digit terms
		switch(x)
		{
			case '0': n= ""; break;
			case '1': n= ""; break;
			case '2': n= " Twenty "; break;
			case '3': n= " Thirty "; break;
			case '4': n= " Forty "; break;
			case '5': n= " Fifty "; break;
			case '6': n= " Sixty "; break;
			case '7': n= " Seventy "; break;
			case '8': n= " Eighty "; break;
			case '9': n= " Ninety "; break;
			default: n = "Not a Number";
		}
		return n;
	}

	function d3(x)
	{ // teen digit terms
		switch(x)
		{
			case '0': n= " Ten "; break;
			case '1': n= " Eleven "; break;
			case '2': n= " Twelve "; break;
			case '3': n= " Thirteen "; break;
			case '4': n= " Fourteen "; break;
			case '5': n= " Fifteen "; break;
			case '6': n= " Sixteen "; break;
			case '7': n= " Seventeen "; break;
			case '8': n= " Eighteen "; break;
			case '9': n= " Nineteen "; break;
			default: n=  "Not a Number";
		}
		return n;
	}

	function convert(input)
	{
		var inputlength = input.length;
		var x = 0;
		var teen1 = "";
		var teen2 = "";
		var teen3 = "";
		var numName = "";
		var invalidNum = "";
		var a1 = ""; // for insertion of million, thousand, hundred
		var a2 = "";
		var a3 = "";
		var a4 = "";
		var a5 = "";
		digit = new Array(inputlength); // stores output

		for (i = 0; i < inputlength; i++)
		{
			// puts digits into array
			digit[inputlength - i] = input.charAt(i);
		}
		store = new Array(9); // store output
		for (i = 0; i < inputlength; i++)
		{
			x= inputlength - i;
			switch (x)
			{ // assign text to each digit
				case x=9: d1(digit[x]); store[x] = n; break;
				case x=8: if (digit[x] == "1")
							{teen3 = "yes"}
						  else
							{teen3 = ""};
						  d2(digit[x]); store[x] = n; break;
				case x=7: if (teen3 == "yes")
							{teen3 = ""; d3(digit[x])}
						  else {d1(digit[x])}; store[x] = n; break;
				case x=6: d1(digit[x]); store[x] = n; break;
				case x=5: if (digit[x] == "1")
							{teen2 = "yes"}
						  else
							{teen2 = ""};
						  d2(digit[x]); store[x] = n; break;
				case x=4: if (teen2 == "yes")
							{teen2 = ""; d3(digit[x])}
						  else
						    {d1(digit[x])};
						  store[x] = n; break;
				case x=3: d1(digit[x]); store[x] = n; break;
				case x=2: if (digit[x] == "1")
							{teen1 = "yes"}
						  else
							{teen1 = ""};
						  d2(digit[x]); store[x] = n; break;
				case x=1: if (teen1 == "yes")
							{teen1 = "";d3(digit[x])}
						  else
							{d1(digit[x])};
						  store[x] = n; break;
			}
			if (store[x] == "Not a Number")
				{invalidNum = "yes"};

			switch (inputlength)
			{
				case 1:   store[2] = "";
				case 2:   store[3] = "";
				case 3:   store[4] = "";
				case 4:   store[5] = "";
				case 5:   store[6] = "";
				case 6:   store[7] = "";
				case 7:   store[8] = "";
				case 8:   store[9] = "";
			}

			if (store[9] != "")
				{ a1 =" Hundred, "}
			else
				{a1 = ""};
			if ((store[9] != "")||(store[8] != "")||(store[7] != ""))
				{ a2 =" Million, "}
			else
				{a2 = ""};
			if (store[6] != "")
				{ a3 =" Hundred "}
			else
				{a3 = ""};
			if ((store[6] != "")||(store[5] != "")||(store[4] != ""))
				{ a4 =" Thousand, "}
			else
				{a4 = ""};
			if (store[3] != "")
				{ a5 =" Hundred "}
			else
				{a5 = ""};
		}
		// add up text, cancel if invalid input found
		if (invalidNum == "yes")
			{numName = "Invalid Input"}
		else
		{
			numName =  store[9] + a1 + store[8] + store[7]
			+ a2 + store[6] + a3 + store[5] + store[4]
			+ a4 + store[3] + a5 + store[2] + store[1];
		}
		store[1] = ""; store[2] = ""; store[3] = "";
		store[4] = ""; store[5] = ""; store[6] = "";
		store[7] = ""; store[8] = ""; store[9] = "";
		if (numName == "")
			{numName = "Zero"};
		//document.myform.textver.value = numName;
		//return true;
		return numName;
}

/*******************************************************************************************/
/***********   End of number to text conversion functions.                        **********/
/*******************************************************************************************/

/*******************************************************************************************/
/***********   This function only allows to enter decimal values.                        **********/
/*******************************************************************************************/

function onlyDecimal() {
  if (event.keyCode<48 || event.keyCode>57 ) {
      if (event.keyCode!=46) {
       	event.returnValue = false;
       }
  }
}

/*******************************************************************************************/
/***********   This function only allows to enter decimal values.                 **********/
/***********   This function is for Phone Number and Fax Number fields.           **********/
/*******************************************************************************************/

function onlyDecimalChanged() {
  if (event.keyCode<48 || event.keyCode>57 ) {
      if ((event.keyCode!=40) && (event.keyCode!=41) && (event.keyCode!=45)) {
       	event.returnValue = false;
       }
  }
}

// ===================================================
//		validatePercent() checks that percent should
//		not be greater than 100.
// ===================================================

function validatePercent(objfield)
{
    if ((objfield.value) > 100 )
    {
			alert("Please Enter Any value between 0 to 100");
			objfield.focus();
			objfield.select();
	}
    else
        return true;
}

//For Dial-A-Zip

var win_option1= "height=200,width=300,top=130,left=250"
var win_option2= "height=250,width=400,top=130,left=200"
function windowOpenAddressContactValidation(address, city, state, zip, contactAddress, contactCity,contactState, contactZip)
{
	alert("entered windowOpenAddressValidation()");

	window.open("MainController.jsp?event=AddressValidate" + "&Address=" + address + "&State=" + state +"&City=" + city + "&Zip=" + zip + "&ContactAddress=" + contactAddress + "&ContactCity=" + contactCity + "&ContactState=" + contactState + "&ContactZip=" + contactZip,"ValidateAddressContact", win_option1);
	return true;
}

function windowOpenAddressValidation(address, city, state, zip)
{
	alert("entered windowOpenAddressValidation()");

	window.open("MainController.jsp?event=AddressValidate" + "&Address=" + address + "&State=" + state +"&City=" + city + "&Zip=" + zip , "ValidateAddressContact", win_option1);
	return true;
}

function replaceSpecialCharacter(strToReplace)
{
//	re = /&/g;
//	reNext = /#/g;
//	strFirstString = strToReplace;
//	strTempString = strFirstString.replace(re,"%26");
//	strFinalString = strTempString.replace(reNext,"%23");
//  escape() is a standard java script function.
	return escape(strToReplace);
}

function ValidateAddress(address, city, state, zip, country,addressActiveStatus,
	contactAddress, contactCity, contactState, contactZip, contactCountry,  contactActiveStatus)
{
	//alert("Dial a Zip - newValidateAddress - before calling JSP country = " + country);
	
	address = replaceSpecialCharacter(address);
	city = replaceSpecialCharacter(city);
	zip = replaceSpecialCharacter(zip);
	contactAddress = replaceSpecialCharacter(contactAddress);
	contactCity = replaceSpecialCharacter(contactAddress);
	contactZip = replaceSpecialCharacter(contactZip);
	/*
	alert(address);
	alert(city);
	alert(contactAddress);
	alert(contactCity);
	*/
	var strUrl ;
	if ((addressActiveStatus == "Y") && (contactActiveStatus == "Y"))
	{
		strUrl ="AddressValidate.jsp?Address=" + address + "&State=" + state +"&City=" + city + "&Zip=" + zip
											+ "&Country=" + country + "&ContactAddress=" + contactAddress + "&ContactCity=" + contactCity +
									"&ContactState=" + contactState + "&ContactZip=" + contactZip + "&ContactCountry=" + contactCountry;
      	}
      	else if ((addressActiveStatus == "Y") && ( (contactActiveStatus == "") || (contactActiveStatus == "N"))	)							
	{
		strUrl = "AddressValidate.jsp?Address=" + address + "&State=" + state +"&City=" + city + "&Zip=" + zip + "&Country=" + country;
	}
	else if (((addressActiveStatus == "") || (addressActiveStatus == "N")) && (contactActiveStatus == "Y"))								
	{
		strUrl = "AddressValidate.jsp?Address=" + contactAddress + "&State=" + contactState +"&City=" + contactCity + "&Zip=" + contactZip + "&Country=" + contactCountry + "&contactInd=Y";
	}
	else
	{
		document.forms[0].hidAddressValidatedIndicator.value = "Y";
		document.forms[0].hidResultFromAddressValidation.value = "1";
		document.forms[0].hidMessageFromAddressValidation.value = "Address validation is not required";
		document.forms[0].hidResultFromContactValidation.value = "1";	
		
		return;
	}
	
	var tmpWIn = window.open(strUrl, "title", win_option2);

	while (!tmpWIn.closed)
	{
	}


}

//Start
/**********************************************************************************/
/*****	This function is called on click of lookup button                    ******/
/*****	lkpA49LookUp(code,desc,token,listtitle) is for general lookup and        ******/
/*****  Here Code is for Text field name for Code.			     *****/
/*****       desc is for Text field name for Description.                     *****/
/*****       type is for LKP_TYPE                                         *****/
/**********************************************************************************/


var strUrl;
var tmpWin;

function lkpA49LookUp(code,desc,token,listtitle)
{

var txtcode = code;
var txtdesc = desc;
var strtoken = token;

var strTitle = listtitle;
strUrl = 'MainController.jsp?transaction=no&event=A49LookUp&codeField=' + txtcode + '&descField=' + txtdesc + '&type=' +strtoken +'&code=' + document.all(txtcode).value + '&description=' + document.all(txtdesc).value +'&listtitle=' + strTitle

if(!tmpWin)
{
tmpWin = window.open(strUrl, "title", 'height=450,width=600,top=10,left=10,scrollbars=yes');
}
else
{
tmpWin.close();
tmpWin = window.open(strUrl, "title", 'height=450,width=600,top=10,left=10,scrollbars=yes');
}
return tmpWin;
}

// Removes leading whitespaces
function LTrim( value ) {
	
	var re = /\s*((\S+\s*)*)/;
	return value.replace(re, "");
	
}

// Removes ending whitespaces
function RTrim( value ) {
	
	var re = /((\s*\S+)*)\s*/;
	return value.replace(re, "");
	
}

// Removes leading and ending whitespaces
function trim( value ) {
	
	return LTrim(RTrim(value));
	
}




//End


