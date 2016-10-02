package com.enercon.model.table;

import org.apache.struts.action.ActionForm;

import com.enercon.model.master.LoginMasterVo;

public class LoginDetailVo extends ActionForm{

	private String id;
	private String loginMasterId;
	private String ownerName;
	private String ownerEmail;
	private String ownerPhoneNumber;
	private String ownerCellNumber;
	private String faxNumber;
	private String ownerDOB;
	private String ownerDOA;
	private String contactPerson;
	private String address;
	private String city;
	private String zip;
	private String email;
	private String phoneNo;
	private String cellNo;
	private String faxNo;
	private String dob;
	private String doa;
	private String activated;
	private String emailActivated;
	
	//LoginMasterVo can have '0..n' LoginDetailVo
	private LoginMasterVo login;
	
	
}
