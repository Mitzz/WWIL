package com.enercon.model.master;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

public class CustomerMasterVo {
	private final static Logger logger = Logger.getLogger(CustomerMasterVo.class);

	private String id;
	private String name;
	private List<WecMasterVo> wecs = new ArrayList<WecMasterVo>();
	private LoginMasterVo login;
	private List<StateMasterVo> states;

	public CustomerMasterVo() {
	}

	public CustomerMasterVo(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<WecMasterVo> getWecs() {
		return wecs;
	}

	public void setWecs(List<WecMasterVo> wecs) {
		this.wecs = wecs;
	}

	public void addWec(WecMasterVo wec){
		wecs.add(wec);
	}
	
	public List<StateMasterVo> getStateMasterVo() {
		if(states == null || states.size() == 0){
			Set<StateMasterVo> statesSet = new HashSet<StateMasterVo>();
			List<WecMasterVo> wecs = getWecs();
			
			for(WecMasterVo wec : wecs){
				StateMasterVo state = wec.getEb().getSite().getArea().getState();
				statesSet.add(state);
			}
			
			logger.debug(statesSet);
			states = new ArrayList<StateMasterVo>(statesSet);
		}
		return states;
	}

	public LoginMasterVo getLogin() {
		return login;
	}

	public void setLogin(LoginMasterVo login) {
		login.addCustomer(this);
		this.login = login;
	}
}
