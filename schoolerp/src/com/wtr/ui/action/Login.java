package com.wtr.ui.action;

import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.wtr.bl.services.AuditInfoService;
import com.wtr.bl.services.DoctorService;
import com.wtr.bl.services.PatientService;
import com.wtr.bl.services.UserService;
import com.wtr.bl.vo.AuditInfoVO;
import com.wtr.bl.vo.DoctorVO;
import com.wtr.bl.vo.PatientVO;
import com.wtr.bl.vo.UserStatus;
import com.wtr.bl.vo.UserType;
import com.wtr.bl.vo.UserVO;
import com.wtr.ui.util.UIConstants;
import com.wtr.ui.util.Utilities;

public class Login extends BaseAction implements ServletRequestAware, ModelDriven<UserVO>, Preparable{
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger( Login.class);
	private HttpServletRequest request;	
	private UserVO user = new UserVO();
	private String successMessage;
	private String failureMessage;
	public UserService userService;
	public PatientService patientService;
	public DoctorService doctorService;
	public AuditInfoService auditInfoService;
	public String ipAddress = "";
			
	public UserVO getUser() {
		return user;
	}
	public void setUser(UserVO user) {
		this.user = user;
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
	public UserService getUserService() {
		return userService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public AuditInfoService getAuditInfoService() {
		return auditInfoService;
	}
	public void setAuditInfoService(AuditInfoService auditInfoService) {
		this.auditInfoService = auditInfoService;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public UserVO getModel() {		
		return user;
	}
	public void setServletRequest(HttpServletRequest request) {
		this.request=request;
	}
	
	public void prepare() throws Exception {		
		logger.debug("Inside Login:prepare()");
		WebApplicationContext context = 
			WebApplicationContextUtils.getRequiredWebApplicationContext(ServletActionContext.getServletContext());
		
		/* COMMENTED FOR FUTURE SERVICE IMPLEMENTATION
		userService = (UserService)context.getBean("userService");
		patientService = (PatientService)context.getBean("patientService");
		doctorService = (DoctorService)context.getBean("doctorService");
		
		auditInfoService = (AuditInfoService)context.getBean("auditInfoService");
		logger.debug("In prepare, userService ="+userService);
		
		*/
		//is client behind something?
		ipAddress = request.getHeader("X-FORWARDED-FOR");  
		if (ipAddress == null) {  
		   ipAddress = request.getRemoteAddr();  
		}
		logger.debug("client's ipAddress ="+ipAddress);
	}	
	
	public String displayLogin(){
		logger.debug("Login: displayLogin()");
		logger.debug(getText("loginpageup"));		
		//invalidate the current session if any
		HttpSession session = request.getSession(false);
		logger.debug("session is "+session);
		if(session!=null){			
			session.invalidate();
			session=null;
			logger.debug("session invalidate complete");
		}
		return "success";
	}
	
	public String authenticate(){
		String returnType;
		if(!isLicenseValid()){
			failureMessage = "License of the product has expired, please contact your System Administrator!";
			logger.error(failureMessage);
			return "error";
		}		
		logger.debug("Login: authenticate()");
		logger.debug("Input userName ="+user.getUserName());
		String encryptedPasswordFromUI = Utilities.encodePassword(user.getPassword());
		logger.debug("encryptedPasswordFromUI ="+encryptedPasswordFromUI);		
		
		/* COMMENTED FOR FUTURE SERVICE IMPLEMENTATION
		try{
			AuditInfoVO auditInfo = new AuditInfoVO();
			auditInfo.setEntity("USER");			
			UserVO searchedUser = userService.getUserByUserName(user.getUserName());			
			if(searchedUser==null){
				failureMessage = "User not found with username "+user.getUserName()+".";
				logger.error(failureMessage);
				return "error";
			}
			if(searchedUser.getUserStatus() == UserStatus.Inactive){
				failureMessage = "Invalid Username/Password or inactive account, please try again.";
				logger.error(failureMessage);
				return "error";
			}
			logger.debug("user is "+searchedUser.getUserType().name());
			if(searchedUser.getUserType() == UserType.Patient){
				PatientVO searchedPatient = patientService.getPatientById(searchedUser.getId());
				if(searchedPatient == null){
					failureMessage = "User  "+searchedUser.getUserName()+" has not been set as patient, please contact labtechnician or administrator.";
					logger.error(failureMessage);
					return "error";
				}else{
					logger.debug("Patient "+searchedUser.getUserName()+" found.");
				}
			}else if(searchedUser.getUserType() == UserType.Doctor){
				DoctorVO searchedDoctor = doctorService.getDoctorById(searchedUser.getId());
				if(searchedDoctor == null){
					failureMessage = "User  "+searchedUser.getUserName()+" has not been set as doctor, please contact labtechnician or administrator.";
					logger.error(failureMessage);
					return "error";
				}else{
					logger.debug("Doctor "+searchedUser.getUserName()+" found.");
				}
			}else{
				failureMessage = "Invalid user type, application is available for patients and doctors only. ";
				logger.error(failureMessage);
				return "error";
			}
			*/
		
			//returnType = (searchedUser.getUserType()).name().toLowerCase() + "home";				
			returnType = "patient"+"home";
			System.out.println("User Type :: " + returnType);
			
			/* COMMENTED FOR FUTURE SERVICE IMPLEMENTATION
			String encryptedPasswordInDB = searchedUser.getPassword();
			logger.debug("encryptedPasswordInDB ="+encryptedPasswordInDB);
			if(!StringUtils.equals(encryptedPasswordInDB,encryptedPasswordFromUI)){
				failureMessage = "Password not matching for user "+user.getUserName();
				logger.error(failureMessage);
				auditInfo.setActionName("USER_LOGIN_FAILURE");
				auditInfo.setActionOrigin(searchedUser.getId());			
				auditInfo.setMachineKey(ipAddress);				
				auditInfo.setActor("SYSTEM");
				auditInfo.setAuditNote(failureMessage);
				auditInfoService.addAuditInfo(auditInfo);
				logger.info("Added auditinfo for password failure for "+user.getUserName());
				return "error";
			}
			
			*/
			logger.info("Login successful for user "+user.getUserName());
			//Creating new session for user			
			HttpSession session =  createNewSession(request);			

			/* COMMENTED FOR FUTURE IMPLEMENTATION
			session.setAttribute("user", searchedUser);
			
			if(searchedUser.getPasswordLastChangeDate()!=null && searchedUser.getPasswordExpirationDays()>0){
				Date passwordLastChangeDate = searchedUser.getPasswordLastChangeDate();
				int passwordExpirationDays = searchedUser.getPasswordExpirationDays();
				Calendar cal = Calendar.getInstance();
				cal.setTime(passwordLastChangeDate);
				cal.add(Calendar.DATE, passwordExpirationDays);					
				Date passwordExpirationDate = cal.getTime();
				logger.debug("passwordLastChangeDate="+passwordLastChangeDate);
				logger.debug("passwordExpirationDays="+passwordExpirationDays);					
				logger.debug("passwordExpirationDate="+passwordExpirationDate);
				logger.debug("Current Date = "+new Date());
				if(new Date().compareTo(passwordExpirationDate) > 0){
					logger.debug("For "+ searchedUser.getUserName()+":password has expired! Enforcing password change.");
					//Redirecting user to change the password	
					return "gotochangepassword";
				}else{
					logger.debug("For "+ searchedUser.getUserName()+": password has not expired yet!");
				}					
			}			
			
			
			//Storing last login date in session
			String strLastLoginDate=Utilities.getFormattedDate(searchedUser.getLastLoginDate(),UIConstants.TIMESTAMP_DISPLAY_FORMAT);
			session.setAttribute("lastlogindate", strLastLoginDate);
			//Updating last login date in DB
			searchedUser.setLastLoginDate(new Date());
			userService.updateUser(searchedUser);
			//Auditing User Login success event
			auditInfo.setActionName("USER_LOGIN_SUCCESS");
			auditInfo.setActionOrigin(searchedUser.getId());			
			auditInfo.setMachineKey(ipAddress);				
			auditInfo.setActor("SYSTEM");
			auditInfo.setAuditNote("Login successful for user "+user.getUserName());
			auditInfoService.addAuditInfo(auditInfo);			
			if(searchedUser.isPasswordForceChange()){
				logger.debug("PasswordForceChange is TRUE, redirecting to changepassword action");
				return "gotochangepassword";
			}else{			
				return returnType;
			}
			
			
		}catch(Exception e){
			logger.error(e.getMessage());
			return "error";
		}	
		*/
		return returnType;
	}
	
	public boolean isLicenseValid(){
		//Add licensing verification logic here!!
		return true;
	}
	
	
}

	

