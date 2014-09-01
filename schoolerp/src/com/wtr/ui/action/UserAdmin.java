package com.wtr.ui.action;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.google.gson.Gson;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.wtr.bl.services.AuditInfoService;
import com.wtr.bl.services.GroupUserService;
import com.wtr.bl.services.UserService;
import com.wtr.bl.vo.AuditInfoVO;
import com.wtr.bl.vo.GroupCollectionVO;
import com.wtr.bl.vo.UserCollectionVO;
import com.wtr.bl.vo.UserStatus;
import com.wtr.bl.vo.UserType;
import com.wtr.bl.vo.UserVO;
import com.wtr.ui.util.UIConstants;
import com.wtr.ui.util.Utilities;

public class UserAdmin extends BaseAction implements ServletResponseAware, ServletRequestAware, ModelDriven<UserVO>, Preparable{
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger( UserAdmin.class);
	private HttpServletRequest request;
	private HttpServletResponse response;
	private UserVO user = new UserVO();
	private UserCollectionVO searchedUsers = new UserCollectionVO();
	private String successMessage;
	private String failureMessage;
	public UserService userService;
	public AuditInfoService auditInfoService;	
	public GroupUserService groupUserService;
	public String ipAddress = "";
	UserVO userInSession = new UserVO();
	
	public void prepare() throws Exception {
		logger.debug("Inside UserAdmin:prepare()");
		WebApplicationContext context =
			WebApplicationContextUtils.getRequiredWebApplicationContext(ServletActionContext.getServletContext());
		userService = (UserService)context.getBean("userService");
		auditInfoService = (AuditInfoService)context.getBean("auditInfoService");
		groupUserService = (GroupUserService)context.getBean("groupUserService");
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

	public UserCollectionVO getSearchedUsers() {
		return searchedUsers;
	}

	public void setSearchedUsers(UserCollectionVO searchedUsers) {
		this.searchedUsers = searchedUsers;
	}
	
	public void setServletRequest(HttpServletRequest request) {
		this.request=request;
	}

	public void setServletResponse(HttpServletResponse arg0) {
		this.response=arg0;
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

	@SkipValidation
	public String displayUserAdmin(){
		logger.debug("Inside UserAdmin: displayUserAdmin()");

		try{
			searchedUsers = userService.getAllUsers();
			if(searchedUsers!=null){
				logger.debug("No. of users searched = "+searchedUsers.getSize());
			}
		}catch(Exception e){
			logger.error(e.getMessage());
			return "error";
		}
		return "success";
	}

	public String addUser(){
		logger.debug("Inside UserAdmin: addUser()");
		try{
			logger.debug("userService="+userService);
			user.setPassword(Utilities.encodePassword(user.getPassword()));
			logger.debug("User from form -->"+user.getAttributesAsString());
			//user.setUserType(UserType.INTERNAL);
			String note = user.getNote();
			logger.debug("note = "+note);
			String formatted_note = StringUtils.trim(note.replaceAll("\\s*[\\r\\n]+\\s*", " "));
			logger.debug("formatted_note "+formatted_note);
			user.setNote(formatted_note);
			if(StringUtils.isNotEmpty(userInSession.getUserName())){
				user.setLastUpdatedBy(userInSession.getUserName());
			}else{
				user.setLastUpdatedBy("System");				
			}
			user.setPasswordLastChangeDate(new Date());
			String id = userService.addUser(user);
			successMessage = getText("addusersuccess");
			logger.info(successMessage);

			AuditInfoVO auditInfo = new AuditInfoVO();
			auditInfo.setEntity("USER");
			auditInfo.setActionName("USER_ADD");
			auditInfo.setActionOrigin(id);
			auditInfo.setMachineKey(ipAddress);
			
			if(StringUtils.isNotEmpty(userInSession.getUserName())){
				auditInfo.setActor(userInSession.getUserName());
			}else{
				auditInfo.setActor("System");
			}
			auditInfo.setAuditNote("Added user "+user.getUserName());
			auditInfoService.addAuditInfo(auditInfo);
			logger.info("Added auditinfo for adding user "+user.getUserName());
			return "success";
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
			failureMessage = getText("adduserfailure");
			logger.error(failureMessage);
			return "error";
		}
	}

	@SkipValidation
	public String updateUser(){
		logger.debug("Inside UserAdmin: updateUser()");
		try{
			logger.debug("userService="+userService);
			logger.debug("User from form -->"+user.getAttributesAsString());
			String id = StringUtils.trim(request.getParameter("selectedId"));
			logger.debug("Input id="+id);			
			UserVO userSearched = userService.getUserById(id);
			userSearched.setPrefix(user.getPrefix());
			userSearched.setFirstName(user.getFirstName());
			userSearched.setMiddleName(user.getMiddleName());
			userSearched.setLastName(user.getLastName());
			userSearched.setUserStatus(user.getUserStatus());
			userSearched.setUserType(user.getUserType());
						
			String desc = user.getNote();
			logger.debug("desc = "+desc);
			String formatted_note ="";
			if(StringUtils.isNotEmpty(desc)){
				formatted_note = StringUtils.trim(desc.replaceAll("\\s*[\\r\\n]+\\s*", " "));
			}
			logger.debug("formatted_note "+formatted_note);
			userSearched.setNote(formatted_note);			
			userSearched.setLastUpdatedBy(userInSession.getUserName());
			userSearched.setLastUpdatedDate(new Date());
			userService.updateUser(userSearched);
			successMessage = getText("updateusersuccess");
			logger.info(successMessage);

			AuditInfoVO auditInfo = new AuditInfoVO();
			auditInfo.setEntity("USER");
			auditInfo.setActionName("USER_UPDATE");
			auditInfo.setActionOrigin(id);
			auditInfo.setMachineKey(ipAddress);			
			auditInfo.setActor(userInSession.getUserName());			
			auditInfo.setAuditNote("Updated user "+userSearched.getUserName());
			auditInfoService.addAuditInfo(auditInfo);
			logger.info("Added auditinfo for updating user "+userSearched.getUserName());
			return "success";
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
			failureMessage = getText("updateuserfailure");
			logger.error(failureMessage);
			return "error";
		}
	}

	@SkipValidation
	public String deleteUser(){
		logger.debug("Inside UserAdmin: deleteUser()");
		try{
			String id = StringUtils.trim(request.getParameter("selectedId"));
			logger.debug("Input id="+id);
			//Obtaining User record before deleting for capturing audit details
			user = userService.getUserById(id);
			userService.deleteUser(id);
			successMessage = getText("deleteusersuccess");
			logger.info(successMessage);

			AuditInfoVO auditInfo = new AuditInfoVO();
			auditInfo.setEntity("USER");
			auditInfo.setActionName("USER_DELETE");
			auditInfo.setActionOrigin(id);
			auditInfo.setMachineKey(ipAddress);
			auditInfo.setActor(userInSession.getUserName());
			auditInfo.setAuditNote("Deleted user "+user.getUserName());
			auditInfoService.addAuditInfo(auditInfo);
			logger.info("Added auditinfo for deleting user "+user.getUserName());

			return "success";
		}catch(Exception e){
			logger.error(e.getMessage());
			failureMessage = getText("deletefailure");
			logger.error(failureMessage);
			return "error";
		}
	}

	public void validate(){
		logger.debug("Inside userAdmin::validate()");
		if(StringUtils.isEmpty(user.getUserName())){
			addFieldError( "userName", "userName is required.");
		}else{
			try{
				if(!userService.isUserNameAvailableForNewUser((user.getUserName()))){
					addFieldError( "userName", "user "+user.getUserName()+" already exists.");
				}
			}catch(Exception e){
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		}
		if(StringUtils.isEmpty(user.getFirstName())){
			addFieldError( "firstName", "firstName is required.");
		}
		if(StringUtils.isEmpty(user.getLastName())){
			addFieldError( "lastName", "lastName is required.");
		}		
	}

	@SkipValidation
	public void isUserNameAvailable() throws Exception {
		String userName = StringUtils.trim(request.getParameter("userName"));
		String userNameAvailable = "";
		logger.debug("Input userName="+userName);
		if(StringUtils.isNotBlank(userName)){
			if(userService.isUserNameAvailableForNewUser(userName)){
				//userNameAvailable = "userName is available";
			}else{
				userNameAvailable = "User name is not available, please use different User name.";
			}
			logger.debug("userNameAvailable="+userNameAvailable);
			System.out.println("response="+response);
			response.getWriter().write(userNameAvailable);
		}
	}

	@SkipValidation
	public void populateUserDetails() throws Exception {
		String id = StringUtils.trim(request.getParameter("selectedId"));
		logger.debug("Input Id="+id);
		GroupCollectionVO usersGroup = groupUserService.getGroupsOfUser(id);
		List<Object> userDetailsList = new ArrayList<Object>();
		UserVO user=userService.getUserById(id);
		String prefix = " ";
		if(null!=user&& StringUtils.isNotBlank(user.getPrefix())){
			prefix= StringUtils.trim(user.getPrefix());
		}
		logger.debug("prefix = "+prefix);
		String middleName = " ";
		if(null!=user&& StringUtils.isNotBlank(user.getMiddleName())){
			middleName= StringUtils.trim(user.getMiddleName());
		}
		logger.debug("middleName = "+middleName);		
		UserStatus userStatus = user.getUserStatus();
		String strUserStatus =userStatus.toString();
		logger.debug("strUserStatus = "+strUserStatus);		
		UserType userType = user.getUserType();
		String strUserType =userType.toString();
		logger.debug("strUserType = "+strUserType);		
		String note = " ";
		if(null!=user && StringUtils.isNotBlank(user.getNote())){
			note= StringUtils.trim(user.getNote());
		}
		logger.debug("note = "+note);
		String strLastLoginDate = " ";
		logger.debug("lastLoginDate = "+user.getLastLoginDate());
		if(null!=user && user.getLastLoginDate()!=null){
			strLastLoginDate = Utilities.getFormattedDate(user.getLastLoginDate(), UIConstants.TIMESTAMP_DISPLAY_FORMAT);			
		}else{
			strLastLoginDate = " ";
		}
		logger.debug("strLastLoginDate = "+strLastLoginDate);
		String strPasswordExpiresOn = " ";
		if(null!=user && user.getPasswordExpirationDays()>0 && user.getPasswordLastChangeDate()!=null){
			Date passwordLastChangeDate = user.getPasswordLastChangeDate();
			int passwordExpirationDays = user.getPasswordExpirationDays();
			Calendar cal = Calendar.getInstance();
			cal.setTime(passwordLastChangeDate);
			cal.add(Calendar.DATE, passwordExpirationDays);					
			Date passwordExpirationDate = cal.getTime();
			logger.debug("passwordLastChangeDate="+passwordLastChangeDate);
			logger.debug("passwordExpirationDays="+passwordExpirationDays);					
			logger.debug("passwordExpirationDate="+passwordExpirationDate);
			strPasswordExpiresOn = Utilities.getFormattedDate(passwordExpirationDate, UIConstants.TIMESTAMP_DISPLAY_FORMAT);
			logger.debug("strPasswordExpiresOn="+strPasswordExpiresOn);
		}
		UserVO selectedUser = new UserVO();
		try {
			selectedUser.setUserName(StringUtils.trim(user.getUserName()));
			selectedUser.setPrefix(prefix);
			selectedUser.setFirstName(StringUtils.trim(user.getFirstName()));
			selectedUser.setMiddleName(middleName);
			selectedUser.setLastName(StringUtils.trim(user.getLastName()));			
			selectedUser.setUserStatus(user.getUserStatus());
			selectedUser.setUserType(user.getUserType());					
			selectedUser.setNote(note);
			selectedUser.setLastLoginDate(user.getLastLoginDate());
			selectedUser.setStrLastLoginDate(strLastLoginDate);
			selectedUser.setPasswordExpirationDays(user.getPasswordExpirationDays());
			userDetailsList.add(selectedUser);
			userDetailsList.add(usersGroup);
			Gson gson = new Gson();
			String strUserDetailsList = gson.toJson(userDetailsList);
			response.getWriter().write(strUserDetailsList.toString());			
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	
	@SkipValidation
	public String resetpassword() throws Exception {
		logger.debug("Inside UserAdmin:resetpassword()");
		UserVO searchedUser = new UserVO();
		try{
			String id = StringUtils.trim(request.getParameter("selectedId"));
			logger.debug("User from form -->"+user.getAttributesAsString());
			logger.debug("Input id="+id);
			logger.debug("Input userName = "+user.getUserName());			
			searchedUser = userService.getUserById(id);
			String newPasswordIn = Utilities.encodePassword(StringUtils.trim(user.getPassword()));
			logger.debug("newPasswordIn="+newPasswordIn);
			searchedUser.setOldPassword(searchedUser.getPassword());//Copying current password into oldpassword
			searchedUser.setPassword(newPasswordIn);//Setting new password			
			searchedUser.setPasswordExpirationDays(user.getPasswordExpirationDays());
			searchedUser.setPasswordForceChange(user.isPasswordForceChange());
			searchedUser.setPasswordLastChangeDate(new Date());
			searchedUser.setLastUpdatedBy(userInSession.getUserName());			
			
			userService.updateUser(searchedUser);
			successMessage = getText("resetpasswordsuccess") + " for user "+searchedUser.getUserName();
			logger.info(successMessage);
			//Audit entry
			AuditInfoVO auditInfo = new AuditInfoVO();
			auditInfo.setEntity("USER");
			auditInfo.setActionName("USER_PASSWORD_RESET");
			auditInfo.setActionOrigin(id);
			auditInfo.setMachineKey(ipAddress);
			auditInfo.setActor(userInSession.getUserName());
			auditInfo.setAuditNote("Password resetted for user "+searchedUser.getUserName());
			auditInfoService.addAuditInfo(auditInfo);
			logger.info("Added auditinfo for password reset for "+searchedUser.getUserName());
			return "success";
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
			failureMessage = getText("resetpasswordfailure")+" for user "+searchedUser.getUserName();
			logger.error(failureMessage);
			return "error";
		}
	}
}


