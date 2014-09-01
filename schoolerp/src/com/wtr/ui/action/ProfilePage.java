package com.wtr.ui.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.opensymphony.xwork2.Preparable;
import com.wtr.bl.services.AuditInfoService;
import com.wtr.bl.services.ContactService;
import com.wtr.bl.services.PatientService;
import com.wtr.bl.services.UserService;
import com.wtr.bl.vo.ContactCollectionVO;
import com.wtr.bl.vo.ContactVO;
import com.wtr.bl.vo.PatientVO;
import com.wtr.bl.vo.UserVO;
import com.wtr.ui.util.ResourceLocator;

public class ProfilePage extends BaseAction 
	implements ServletRequestAware, ServletResponseAware,
	Preparable {
	
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger( ProfilePage.class);	

	private HttpServletRequest request;
	private HttpServletResponse response;
	private PatientVO patient = new PatientVO();
	private ContactVO priContact = new ContactVO();
	private ContactVO secContact = new ContactVO();
	private String successMessage;
	private String failureMessage;
	public UserService userService;
	public PatientService patientService;
	public AuditInfoService auditInfoService;
	public ContactService contactService;
	public String ipAddress = "";
	
	// Fields on the page
	private String firstname;
	private String lastname;
	private String middlename;
	private String username;
	private String userStatus;
	private String age;
	private String gender;
	private String membershipType;
	private String employmentType;
	private String enrollmentMode;
	private String relationshipType;
	private String membershipExpirationDate;
	private String policyNumber;
	private String primaryInsured;
	private String insuranceExpirationDate;
	private String issuedBy;
	private String webUrl;
	private String sumAssured;
	private String emailAddress;
	private String note;
	private String enrollmentReferredBy;
	private String companyName;
	private String phoneNoOne;
	private String phoneNoTwo;
	private String flatNo;
	private String locality;
	private String street;
	private String city;
	private String pincode;
	private String state;
	private String country;
	private String passwordExpirationDays;
	private String lastLoginDate;
	private String dateOfBirth;
	private String patientImage;
	
	private String path;
	private String imgPath;
	private String imgFileName;
	
	byte[] imageInBytes = null;
	String imageId;
	
	UserVO userInSession = new UserVO();
	
	public void prepare() throws Exception {
		logger.debug("Inside PatientProfile:prepare()");
		try{
			WebApplicationContext context =
				WebApplicationContextUtils.getRequiredWebApplicationContext(ServletActionContext.getServletContext());
			userService = (UserService)context.getBean("userService");
			auditInfoService = (AuditInfoService)context.getBean("auditInfoService");
			patientService = (PatientService)context.getBean("patientService");
			contactService = (ContactService)context.getBean("contactService");
			logger.debug("In prepare patientService ="+patientService);
			//is client behind something?
			ipAddress = request.getHeader("X-FORWARDED-FOR");
			if (ipAddress == null) {
			   ipAddress = request.getRemoteAddr();
			}
			logger.debug("client's ipAddress ="+ipAddress);
			Object obj = request.getSession().getAttribute("user");
			if(obj!=null){
				userInSession = (UserVO) obj;
			}
			logger.debug("userInSession is "+userInSession.getAttributesAsString());
//			path = context.getServletContext().getRealPath("/");
//			String app = context.getServletContext().getContextPath();
//			path = path.substring(0, path.lastIndexOf(app.split("/")[1]));
			
		}catch(Exception e){
			e.printStackTrace();
		}
		logger.debug("Completing PatientProfile:prepare()");
	}

	public ProfilePage() {
		ResourceLocator resLoc = ResourceLocator.getInstance();
		String photoPath = (resLoc.getAppProps()).getProperty("photos_path");
		System.out.println(photoPath);
		setImgPath(photoPath);
	}
	public String displayProfile() throws Exception{
		logger.debug("ProfilePage: displayProfile()");
		patient = patientService.getPatientById(userInSession.getId());
		ResourceLocator resLoc = ResourceLocator.getInstance();
		String photoPath = (resLoc.getAppProps()).getProperty("photos_path");
		System.out.println(photoPath);
		setPatient(patient);
		setImgPath(photoPath);
		ContactCollectionVO contacts = null;
		ContactVO tempContact = new ContactVO();
		tempContact.setUser(userInSession);
		contacts = contactService.searchUserContacts(tempContact);
		populateContactDetails(contacts);
		logger.debug("patient ="+patient);
		populatePatientDetails(patient, userInSession);
		setToggleMenu("false");
		return "success";
	}

	public String execute() {
		return SUCCESS;
	}

//	public byte[] getCustomImageInBytes() {
//		BufferedImage originalImage;
//		try {
//			originalImage = ImageIO.read(getImageFile(getImageId()));
//			
//			ByteArrayOutputStream baos = new ByteArrayOutputStream();
//			ImageIO.write(originalImage, "jpg", baos);
//			baos.flush();
//			imageInBytes = baos.toByteArray();
//			baos.close();
//		} catch (IOException e){
//			logger.error(e.getMessage());
//		}
//		return imageInBytes;
//	}
//	
//	private File getImageFile(String imageId){
//		File file = new File(getImgPath(),imageId );
//		return file;
//	}
//
//	public String getCustomContentType() {
//		return "image/jpeg";
//	}
//	
//	public String getCustomContentDisposition() {
//		return "anyname.jpg";
//	}
	
	private void populateContactDetails(ContactCollectionVO contacts) {
		if(null != contacts && contacts.size()>0) {
			for (int i = 0; i < contacts.size(); i++) {
				if(contacts.get(i).isPrimaryContact()) {
					setPriContact(contacts.get(i));
				} else {
					setSecContact(contacts.get(i));
				}
			}
		}
	}
	private void populatePatientDetails(PatientVO patient, 
			UserVO user) {
		
		
		setUsername(user.getUserName());
		setFirstname(user.getFirstName());
		setLastname(user.getLastName());
		setUserStatus(user.getUserStatus().toString());
		setMembershipType(patient.getMembershipType().toString());
		setEmploymentType(patient.getEmploymentType().toString());
		setEnrollmentMode(patient.getEnrollmentMode().toString());
		setRelationshipType(patient.getRelationshipType().toString());

		setLastLoginDate(user.getStrLastLoginDate());
		
		setMembershipExpirationDate(patient.getStrMembershipExpirationDate());

		String policyNo = patient.getPolicyNumber();
		if(StringUtils.isNotEmpty(policyNo))
			setPolicyNumber(patient.getPolicyNumber());
		String priInsured = patient.getPrimaryInsured();
		if(StringUtils.isNotEmpty(priInsured))
			setPrimaryInsured(patient.getPrimaryInsured());
		
		setInsuranceExpirationDate(patient.getStrInsuranceExpirationDate());
		setDateOfBirth(patient.getStrDateOfBirth());

		setIssuedBy(patient.getIssuedBy());

		String fnote = patient.getNote();
		if(StringUtils.isNotEmpty(fnote)) {
			setNote(StringUtils.trim(fnote.replaceAll("\\s*[\\r\\n]+\\s*", " ")));
		}
		setWebUrl(patient.getWebURL());
		setSumAssured(String.valueOf(patient.getSumAssured()));
		setNote(patient.getNote());
		setEnrollmentReferredBy(patient.getEnrollmentReferredBy());
		setCompanyName(patient.getCompanyName());
		setAge(calculateAge(new Date()));
		setPasswordExpirationDays(user.getPasswordExpirationDays() + " days");
		setGender(user.getGender());
		setImgFileName(patient.getImageFileName());
	}

	private String calculateAge(Date dob) {
		String age = "";
		
		return age + " years";
	}
	
	public HttpServletRequest getRequest() {
		return request;
	}


	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}


	public HttpServletResponse getResponse() {
		return response;
	}


	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}


	public PatientVO getPatient() {
		return patient;
	}


	public void setPatient(PatientVO patient) {
		this.patient = patient;
	}

	public ContactVO getPriContact() {
		return priContact;
	}


	public void setPriContact(ContactVO priContact) {
		this.priContact = priContact;
	}


	public ContactVO getSecContact() {
		return secContact;
	}


	public void setSecContact(ContactVO secContact) {
		this.secContact = secContact;
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


	public PatientService getPatientService() {
		return patientService;
	}


	public void setPatientService(PatientService patientService) {
		this.patientService = patientService;
	}


	public AuditInfoService getAuditInfoService() {
		return auditInfoService;
	}


	public void setAuditInfoService(AuditInfoService auditInfoService) {
		this.auditInfoService = auditInfoService;
	}


	public ContactService getContactService() {
		return contactService;
	}


	public void setContactService(ContactService contactService) {
		this.contactService = contactService;
	}


	public String getIpAddress() {
		return ipAddress;
	}


	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	

	public UserVO getUserInSession() {
		return userInSession;
	}


	public void setUserInSession(UserVO userInSession) {
		this.userInSession = userInSession;
	}


	public String getFirstname() {
		return firstname;
	}


	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}


	public String getLastname() {
		return lastname;
	}


	public void setLastname(String lastname) {
		this.lastname = lastname;
	}


	public String getMiddlename() {
		return middlename;
	}


	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getUserStatus() {
		return userStatus;
	}


	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}


	public String getMembershipType() {
		return membershipType;
	}


	public void setMembershipType(String membershipType) {
		this.membershipType = membershipType;
	}


	public String getEmploymentType() {
		return employmentType;
	}


	public void setEmploymentType(String employmentType) {
		this.employmentType = employmentType;
	}


	public String getEnrollmentMode() {
		return enrollmentMode;
	}


	public void setEnrollmentMode(String enrollmentMode) {
		this.enrollmentMode = enrollmentMode;
	}


	public String getRelationshipType() {
		return relationshipType;
	}


	public void setRelationshipType(String relationshipType) {
		this.relationshipType = relationshipType;
	}


	public String getMembershipExpirationDate() {
		return membershipExpirationDate;
	}


	public void setMembershipExpirationDate(String membershipExpirationDate) {
		this.membershipExpirationDate = membershipExpirationDate;
	}


	public String getPolicyNumber() {
		return policyNumber;
	}


	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
	}


	public String getPrimaryInsured() {
		return primaryInsured;
	}


	public void setPrimaryInsured(String primaryInsured) {
		this.primaryInsured = primaryInsured;
	}


	public String getInsuranceExpirationDate() {
		return insuranceExpirationDate;
	}


	public void setInsuranceExpirationDate(String insuranceExpirationDate) {
		this.insuranceExpirationDate = insuranceExpirationDate;
	}


	public String getIssuedBy() {
		return issuedBy;
	}


	public void setIssuedBy(String issuedBy) {
		this.issuedBy = issuedBy;
	}


	public String getWebUrl() {
		return webUrl;
	}


	public void setWebUrl(String webUrl) {
		this.webUrl = webUrl;
	}


	public String getSumAssured() {
		return sumAssured;
	}


	public void setSumAssured(String sumAssured) {
		this.sumAssured = sumAssured;
	}


	public String getEmailAddress() {
		return emailAddress;
	}


	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}


	public String getNote() {
		return note;
	}


	public void setNote(String note) {
		this.note = note;
	}


	public String getEnrollmentReferredBy() {
		return enrollmentReferredBy;
	}


	public void setEnrollmentReferredBy(String enrollmentReferredBy) {
		this.enrollmentReferredBy = enrollmentReferredBy;
	}


	public String getCompanyName() {
		return companyName;
	}


	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}


	public String getPhoneNoOne() {
		return phoneNoOne;
	}


	public void setPhoneNoOne(String phoneNoOne) {
		this.phoneNoOne = phoneNoOne;
	}


	public String getPhoneNoTwo() {
		return phoneNoTwo;
	}


	public void setPhoneNoTwo(String phoneNoTwo) {
		this.phoneNoTwo = phoneNoTwo;
	}


	public String getFlatNo() {
		return flatNo;
	}


	public void setFlatNo(String flatNo) {
		this.flatNo = flatNo;
	}


	public String getLocality() {
		return locality;
	}


	public void setLocality(String locality) {
		this.locality = locality;
	}


	public String getStreet() {
		return street;
	}


	public void setStreet(String street) {
		this.street = street;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public String getPincode() {
		return pincode;
	}


	public void setPincode(String pincode) {
		this.pincode = pincode;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}


	public String getCountry() {
		return country;
	}


	public void setCountry(String country) {
		this.country = country;
	}


	
	public void setServletResponse(HttpServletResponse response) {
		// TODO Auto-generated method stub
		this.response = response;
	}


	
	public void setServletRequest(HttpServletRequest request) {
		// TODO Auto-generated method stub
		this.request = request;
	}


	public String getAge() {
		return age;
	}


	public void setAge(String age) {
		this.age = age;
	}


	public String getGender() {
		return gender;
	}


	public void setGender(String gender) {
		this.gender = gender;
	}


	public String getPasswordExpirationDays() {
		return passwordExpirationDays;
	}


	public void setPasswordExpirationDays(String passwordExpirationDays) {
		this.passwordExpirationDays = passwordExpirationDays;
	}


	public String getLastLoginDate() {
		return lastLoginDate;
	}


	public void setLastLoginDate(String lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}


	public String getDateOfBirth() {
		return dateOfBirth;
	}


	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}


	public String getPatientImage() {
		return patientImage;
	}


	public void setPatientImage(String patientImage) {
		this.patientImage = patientImage;
	}


	public String getPath() {
		return path;
	}


	public void setPath(String path) {
		this.path = path;
	}


	public String getImgPath() {
		return imgPath;
	}


	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}


	public String getImgFileName() {
		return imgFileName;
	}


	public void setImgFileName(String imgFileName) {
		this.imgFileName = imgFileName;
	}
	
	public String getImageId(){
		return imageId;
	}
	
	public void setImageId(String imageId){
		this.imageId = imageId;
	}
	
}
