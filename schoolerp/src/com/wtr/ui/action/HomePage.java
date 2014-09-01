package com.wtr.ui.action;

import org.apache.log4j.Logger;

public class HomePage extends BaseAction{
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger( HomePage.class);	
	
	public String displayHome(){
		logger.debug("HomePage: displayHome()");
		return "success";
	}
	
	public String displaypatientHome(){
		logger.debug("HomePage: displaypatientHome()");
		return "success";
	}
	
	public String displaydoctorHome() {
		logger.debug("HomePage: displaydoctorHome()");
		return "success";
	}
	
	
}

	

