package com.wtr.ui.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionSupport;


public class BaseAction extends ActionSupport {
	
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(BaseAction.class);
	
	private String toggleMenu;
	
	public BaseAction(){
		setToggleMenu("true");
   	}
	
	public HttpSession createNewSession(HttpServletRequest request) {
		if(logger.isDebugEnabled()){
			logger.debug("Inside BaseAction::createNewSession()");
		}
		// force a new session if one exists
	  	HttpSession session = request.getSession(false);	  	
	  	if (null != session) {
		  	logger.debug("Session already exists: SESSION ID: " + session.getId() + " - invalidating this one and creating new one.");
		  	try{
		  	//session.invalidate();
		  	}catch(Exception e){
		  		System.out.println("Exception in invalidatting session --->"+e.getMessage());
		  	}
		  	session = null;
	  	}	 
	  	try{
	  		session = request.getSession(true);  
	  		session.setAttribute("SessionID", session.getId());       
	  		session.setAttribute("RemoteAddr", request.getRemoteAddr());
	  	}catch(Exception e){
	  		System.out.println("Exception in creating new ssession ---> "+e.getMessage());
	  	}
		logger.debug("Created new session succesfully");
		return session;
  	}
	
	/**
	 * Verifies if a session is valid.
	 * 
	 * @param request HTTP request object
	 * 
	 * @return Session flag set to true if session is valid
	 */
   	public boolean isSessionValid(HttpServletRequest request){
   		if(logger.isDebugEnabled()){
   			logger.debug("Inside BaseAction::isSessionValid()");
   		}
   		long start = System.currentTimeMillis();
	  	HttpSession session = request.getSession(false);

		if (session != null) {
			String lS_reqSessionID = session.getId();        
			if(lS_reqSessionID == null) 
				return false;
			String lS_sesSessionID = (String) session.getAttribute("SessionID"); 
			if(lS_sesSessionID == null) 
				return false;        
			
			String lS_sesRemoteAddr = (String) session.getAttribute("RemoteAddr");
			if(lS_sesRemoteAddr == null) lS_sesRemoteAddr = "null";        
			String lS_reqRemoteAddr = request.getRemoteAddr();        
			if(lS_reqRemoteAddr == null) lS_reqRemoteAddr = "null";                
	
			if(!(lS_sesSessionID.equals(lS_reqSessionID)))
				return false;
	
			if(!(lS_sesRemoteAddr.equalsIgnoreCase(lS_reqRemoteAddr)))
				return false;
		}
		else
			return false;
			
		long end = System.currentTimeMillis();
		logger.info("BaseAction::execute-->Time for execution (ms)="
				+ (end - start));
		return true;		
   	}

	public String getToggleMenu() {
		return toggleMenu;
	}

	public void setToggleMenu(String toggleMenu) {
		this.toggleMenu = toggleMenu;
	}

}
