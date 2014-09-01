package com.wtr.ui.action;

import java.util.Date;


import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.wtr.bl.services.AuditInfoService;
import com.wtr.bl.services.UserService;
import com.wtr.bl.vo.AuditInfoVO;
import com.wtr.bl.vo.UserVO;
import com.wtr.ui.util.Utilities;

public class ChangePassword extends BaseAction implements ServletRequestAware, ModelDriven<UserVO>, Preparable{
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger( ChangePassword.class);
	private HttpServletRequest request;	
	private UserVO user = new UserVO();	
	private String successMessage;
	private String failureMessage;
	public UserService userService;
	public AuditInfoService auditInfoService;	
	public String ipAddress = "";
	UserVO userInSession = new UserVO();
	
	public void prepare() throws Exception {
		logger.debug("Inside ChangePassword:prepare()");
		WebApplicationContext context =
			WebApplicationContextUtils.getRequiredWebApplicationContext(ServletActionContext.getServletContext());
		userService = (UserService)context.getBean("userService");
		auditInfoService = (AuditInfoService)context.getBean("auditInfoService");
		//logger.debug("In prepare userService ="+userService);
		//is client behind something?
		ipAddress = request.getHeader("X-FORWARDED-FOR");
		if (ipAddress == null) {
		   ipAddress = request.getRemoteAddr();
		}		
		//logger.debug("client's ipAddress ="+ipAddress);
		Object obj = request.getSession().getAttribute("user");		
		if(obj!=null){
			userInSession = (UserVO) obj;				
		}
	}
	
	public UserVO getModel() {
		return user;
	}
	
	public void setServletRequest(HttpServletRequest request) {
		this.request=request;
	}
	
	public String getSuccessMessage() {
		return successMessage;
	}

	public void setSuccessMessage(String successMessage) {
		this.successMessage = successMessage;
	}

	public String getFailureMessage() {
		return failureMessage;
	}

	public void setFailureMessage(String failureMessage) {
		this.failureMessage = failureMessage;
	}	
	
	public String displayChangePassword(){
		String retStr = "";
		logger.debug("UserAdmin: displayChangePassword()");
		if(userInSession.getUserType().name().equals("Patient")) {
			setToggleMenu("true");
			retStr = "patient";
		} else if (userInSession.getUserType().name().equals("Doctor")) {
			retStr = "doctor";
		}
		return retStr;
	}
	/*
	public UserVO getUser() {
		return user;
	}

	public void setUser(UserVO user) {
		this.user = user;
	}
	*/
	public String changePassword(){
		logger.debug("ChangePassword: changePassword()");		
		try{			
			logger.debug("User from form -->"+user.getAttributesAsString());
			userInSession = userService.getUserById(userInSession.getId());//Refreshing the user from database			
			String oldPasswordIn = Utilities.encodePassword(user.getOldPassword());
			logger.debug("oldPasswordIn="+oldPasswordIn);
			if(!StringUtils.equals(oldPasswordIn, userInSession.getPassword())){
				failureMessage = getText("oldpassworddoesnotmatch")+" for user "+userInSession.getUserName()+".";				
				logger.error(failureMessage);
				//Audit entry
				AuditInfoVO auditInfo = new AuditInfoVO();
				auditInfo.setEntity("USER");
				auditInfo.setActionName("USER_PASSWORD_CHANGE");
				auditInfo.setActionOrigin(userInSession.getId());
				auditInfo.setMachineKey(ipAddress);
				auditInfo.setActor(userInSession.getUserName());
				auditInfo.setAuditNote(failureMessage);
				auditInfoService.addAuditInfo(auditInfo);				
				return "error";
			}			
			String newPasswordIn = Utilities.encodePassword(StringUtils.trim(user.getPassword()));
			logger.debug("newPasswordIn="+newPasswordIn);
			userInSession.setOldPassword(userInSession.getPassword());//Copying current password into oldpassword
			userInSession.setPasswordForceChange(false);
			userInSession.setPassword(newPasswordIn);//Setting new password
			userInSession.setLastUpdatedBy(userInSession.getUserName());
			userInSession.setPasswordLastChangeDate(new Date());			
			userService.updateUser(userInSession);
			request.setAttribute("user",userInSession);
			successMessage = getText("changepasswordsuccess") +" for user "+userInSession.getUserName()+".";
			logger.info(successMessage);
			//Audit entry
			AuditInfoVO auditInfo = new AuditInfoVO();
			auditInfo.setEntity("USER");
			auditInfo.setActionName("USER_PASSWORD_CHANGE");
			auditInfo.setActionOrigin(userInSession.getId());
			auditInfo.setMachineKey(ipAddress);
			auditInfo.setActor(userInSession.getUserName());
			auditInfo.setAuditNote("Password changed for user "+userInSession.getUserName());
			auditInfoService.addAuditInfo(auditInfo);
			logger.info("Added auditinfo for changing password for user "+userInSession.getUserName());
			return "success";
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
			failureMessage = getText("changepasswordfailure")+" for user "+userInSession.getUserName();
			logger.error(failureMessage);
			return "error";
		}		
	}
	

}

	

