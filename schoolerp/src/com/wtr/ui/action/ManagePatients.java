package com.wtr.ui.action;

import java.io.IOException;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.google.gson.Gson;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.wtr.bl.services.AppointmentService;
import com.wtr.bl.services.AuditInfoService;
import com.wtr.bl.services.ContactService;
import com.wtr.bl.services.PatientService;
import com.wtr.bl.services.UserService;
import com.wtr.bl.vo.AppointmentCollectionVO;
import com.wtr.bl.vo.AppointmentVO;
import com.wtr.bl.vo.ContactCollectionVO;
import com.wtr.bl.vo.ContactVO;
import com.wtr.bl.vo.DoctorVO;
import com.wtr.bl.vo.PatientCollectionVO;
import com.wtr.bl.vo.PatientVO;
import com.wtr.bl.vo.UserVO;

public class ManagePatients extends BaseAction implements ServletRequestAware,
		ServletResponseAware, Preparable, ModelDriven<PatientVO> {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger( ManagePatients.class);
	private HttpServletRequest request;
	private HttpServletResponse response;
	private PatientVO patient = new PatientVO();

	public UserService userService;
	public PatientService patientService;
	public AuditInfoService auditInfoService;
	public ContactService contactService;
	public AppointmentService appointmentService;
	public String ipAddress = "";
	
	UserVO userInSession = new UserVO();
	private PatientCollectionVO searchedPatients = new PatientCollectionVO();
	private String huserName;
	private String hphoneNumber1;
	private String hphoneNumber2;
	private String hflatNumber;
	private String hlocality;
	private String hstreet;
	private String hcity;
	private String hpincode;
	private String hstate;
	private String hcountry;
	private String hemailAddress;
	private String hinsuranceExpirationDate;
	private String hmembershipExpirationDate;

	
	
	public PatientVO getPatient() {
		return patient;
	}

	public void setPatient(PatientVO patient) {
		this.patient = patient;
	}

	public PatientCollectionVO getSearchedPatients() {
		return searchedPatients;
	}

	public void setSearchedPatients(PatientCollectionVO searchedPatients) {
		this.searchedPatients = searchedPatients;
	}

	public String getHuserName() {
		return huserName;
	}

	public void setHuserName(String huserName) {
		this.huserName = huserName;
	}

	public String getHphoneNumber1() {
		return hphoneNumber1;
	}

	public void setHphoneNumber1(String hphoneNumber1) {
		this.hphoneNumber1 = hphoneNumber1;
	}

	public String getHphoneNumber2() {
		return hphoneNumber2;
	}

	public void setHphoneNumber2(String hphoneNumber2) {
		this.hphoneNumber2 = hphoneNumber2;
	}

	public String getHflatNumber() {
		return hflatNumber;
	}

	public void setHflatNumber(String hflatNumber) {
		this.hflatNumber = hflatNumber;
	}

	public String getHlocality() {
		return hlocality;
	}

	public void setHlocality(String hlocality) {
		this.hlocality = hlocality;
	}

	public String getHstreet() {
		return hstreet;
	}

	public void setHstreet(String hstreet) {
		this.hstreet = hstreet;
	}

	public String getHcity() {
		return hcity;
	}

	public void setHcity(String hcity) {
		this.hcity = hcity;
	}

	public String getHpincode() {
		return hpincode;
	}

	public void setHpincode(String hpincode) {
		this.hpincode = hpincode;
	}

	public String getHstate() {
		return hstate;
	}

	public void setHstate(String hstate) {
		this.hstate = hstate;
	}

	public String getHcountry() {
		return hcountry;
	}

	public void setHcountry(String hcountry) {
		this.hcountry = hcountry;
	}

	public String getHemailAddress() {
		return hemailAddress;
	}

	public void setHemailAddress(String hemailAddress) {
		this.hemailAddress = hemailAddress;
	}

	public String getHinsuranceExpirationDate() {
		return hinsuranceExpirationDate;
	}

	public void setHinsuranceExpirationDate(String hinsuranceExpirationDate) {
		this.hinsuranceExpirationDate = hinsuranceExpirationDate;
	}

	public String getHmembershipExpirationDate() {
		return hmembershipExpirationDate;
	}

	public void setHmembershipExpirationDate(String hmembershipExpirationDate) {
		this.hmembershipExpirationDate = hmembershipExpirationDate;
	}

	
	
	public void prepare() throws Exception {
		logger.debug("Inside ManagePatients:prepare()");
		try {
			WebApplicationContext context =
					WebApplicationContextUtils.getRequiredWebApplicationContext(
							ServletActionContext.getServletContext());
			userService = (UserService)context.getBean("userService");
			auditInfoService = (AuditInfoService)context.getBean("auditInfoService");
			patientService = (PatientService)context.getBean("patientService");
			contactService = (ContactService)context.getBean("contactService");
			appointmentService = (AppointmentService)context.getBean("appointmentService");
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
		}catch(Exception e){
			e.printStackTrace();
		}
		logger.debug("Completing PatientAdmin:prepare()");
	}
	
	public String displayManagePatients() {
		logger.debug("Inside ManagePatients:displayManagePatients");
		try {
			AppointmentCollectionVO appointmentsForDoctor = 
					appointmentService.getAppointmentsByDoctorId(userInSession.getId());
			searchedPatients = getPatientsFromAppointments(appointmentsForDoctor, patientService);
		} catch(Exception e) {
			e.printStackTrace();
			return "error";
		}
		return "success";
	}
	
	public void populateDoctorsPatientDetails() throws Exception {
		logger.debug("Inside ManagePatients:populatePatientDetails()");
		String id = StringUtils.trim(request.getParameter("selectedId"));
		logger.debug("Input id: "+id);
		PatientVO patient = patientService.getPatientById(id);
		List<Object> patientDetailsList = new ArrayList<Object>();
		try{
			PatientVO selectedPatient = patient;
			selectedPatient.setCompanyName(StringUtils.trimToEmpty(selectedPatient.getCompanyName()));
			selectedPatient.setEnrollmentReferredBy(StringUtils.trimToEmpty(selectedPatient.getEnrollmentReferredBy()));
			selectedPatient.setIssuedBy(StringUtils.trimToEmpty(selectedPatient.getIssuedBy()));
			selectedPatient.setNote(StringUtils.trimToEmpty(selectedPatient.getNote()));
			selectedPatient.setPolicyNumber(StringUtils.trimToEmpty(selectedPatient.getPolicyNumber()));
			selectedPatient.setPrimaryInsured(StringUtils.trimToEmpty(selectedPatient.getPrimaryInsured()));
			selectedPatient.setWebURL(StringUtils.trimToEmpty(selectedPatient.getWebURL()));
			
			logger.debug("selectedPatient is "+selectedPatient.getAttributesAsString());
			ContactVO tempContact = new ContactVO();
			UserVO patientUser = userService.getUserById(id);

			UserVO tempUser = new UserVO();
			tempUser.setId(id);
			tempContact.setUser(tempUser);			
			ContactCollectionVO selectedPatientUserContacts = contactService.searchUserContacts(tempContact);
			ContactVO patientUserPrimaryContact = new ContactVO();
			ContactVO patientUserSecondaryContact = new ContactVO();
			
			if(null!=selectedPatientUserContacts && selectedPatientUserContacts.size()>0){
				logger.debug("selectedPatientUserContacts size before ="+selectedPatientUserContacts.size());
				for(int i=0;i<selectedPatientUserContacts.size();i++){
					if(selectedPatientUserContacts.get(i).isPrimaryContact()){
						patientUserPrimaryContact = selectedPatientUserContacts.get(i);						
					}else{
						patientUserSecondaryContact = selectedPatientUserContacts.get(i);
					}
				}
				logger.debug("selectedPatientUserContacts size after ="+selectedPatientUserContacts.size());
			}			
			tempUser.setUserName(patientUser.getUserName());
			tempUser.setFirstName(patientUser.getFirstName());
			tempUser.setMiddleName(patientUser.getMiddleName());
			tempUser.setLastName(patientUser.getLastName());
			selectedPatient.setUser(null);
			String patient_pc_phoneNumber2 = " ";
			if(null!=patientUserPrimaryContact && StringUtils.isNotBlank(patientUserPrimaryContact.getPhoneNumber2())){
				patient_pc_phoneNumber2= StringUtils.trim(patientUserPrimaryContact.getPhoneNumber2());
			}
			logger.debug("patient_pc_phoneNumber2 = "+patient_pc_phoneNumber2);
			patientUserPrimaryContact.setPhoneNumber2(patient_pc_phoneNumber2);
			
			String patient_pc_emailAddress = " ";
			if(null!=patientUserPrimaryContact && StringUtils.isNotBlank(patientUserPrimaryContact.getEmailAddress())){
				patient_pc_emailAddress= StringUtils.trim(patientUserPrimaryContact.getEmailAddress());
			}
			logger.debug("patient_pc_emailAddress = "+patient_pc_emailAddress);
			patientUserPrimaryContact.setEmailAddress(patient_pc_emailAddress);
			
			String patient_pc_flatNumber = " ";
			if(null!=patientUserPrimaryContact && StringUtils.isNotBlank(patientUserPrimaryContact.getFlatNumber())){
				patient_pc_flatNumber= StringUtils.trim(patientUserPrimaryContact.getFlatNumber());
			}
			logger.debug("patient_pc_flatNumber = "+patient_pc_flatNumber);
			patientUserPrimaryContact.setFlatNumber(patient_pc_flatNumber);
			
			String patient_pc_locality = " ";
			if(null!=patientUserPrimaryContact && StringUtils.isNotBlank(patientUserPrimaryContact.getLocality())){
				patient_pc_locality= StringUtils.trim(patientUserPrimaryContact.getLocality());
			}
			logger.debug("patient_pc_locality = "+patient_pc_locality);
			patientUserPrimaryContact.setLocality(patient_pc_locality);
			
			String patient_pc_street = " ";
			if(null!=patientUserPrimaryContact && StringUtils.isNotBlank(patientUserPrimaryContact.getStreet())){
				patient_pc_street= StringUtils.trim(patientUserPrimaryContact.getStreet());
			}
			logger.debug("patient_pc_street = "+patient_pc_street);
			patientUserPrimaryContact.setStreet(patient_pc_street);

			String patient_pc_city = " ";
			if(null!=patientUserPrimaryContact && StringUtils.isNotBlank(patientUserPrimaryContact.getCity())){
				patient_pc_city= StringUtils.trim(patientUserPrimaryContact.getCity());
			}
			logger.debug("patient_pc_city = "+patient_pc_city);
			patientUserPrimaryContact.setCity(patient_pc_city);
			
			String patient_pc_pincode = " ";
			if(null!=patientUserPrimaryContact && StringUtils.isNotBlank(patientUserPrimaryContact.getPincode())){
				patient_pc_pincode= StringUtils.trim(patientUserPrimaryContact.getPincode());
			}
			logger.debug("patient_pc_pincode = "+patient_pc_pincode);
			patientUserPrimaryContact.setPincode(patient_pc_pincode);

			String patient_pc_state = " ";
			if(null!=patientUserPrimaryContact && StringUtils.isNotBlank(patientUserPrimaryContact.getState())){
				patient_pc_state= StringUtils.trim(patientUserPrimaryContact.getState());
			}
			logger.debug("patient_pc_state = "+patient_pc_state);
			patientUserPrimaryContact.setState(patient_pc_state);

			String patient_pc_country = " ";
			if(null!=patientUserPrimaryContact && StringUtils.isNotBlank(patientUserPrimaryContact.getCountry())){
				patient_pc_country= StringUtils.trim(patientUserPrimaryContact.getCountry());
			}
			logger.debug("patient_pc_country = "+patient_pc_country);
			patientUserPrimaryContact.setCountry(patient_pc_country);
			
			String patient_sc_phoneNumber1 = " ";
			if(null!=patientUserSecondaryContact && StringUtils.isNotBlank(patientUserSecondaryContact.getPhoneNumber1())){
				patient_sc_phoneNumber1= StringUtils.trim(patientUserSecondaryContact.getPhoneNumber1());
			}
			logger.debug("patient_sc_phoneNumber1 = "+patient_sc_phoneNumber1);
			patientUserSecondaryContact.setPhoneNumber1(patient_sc_phoneNumber1);
			
			String patient_sc_phoneNumber2 = " ";
			if(null!=patientUserSecondaryContact && StringUtils.isNotBlank(patientUserSecondaryContact.getPhoneNumber2())){
				patient_sc_phoneNumber2= StringUtils.trim(patientUserSecondaryContact.getPhoneNumber2());
			}
			logger.debug("patient_sc_phoneNumber2 = "+patient_sc_phoneNumber2);
			patientUserSecondaryContact.setPhoneNumber2(patient_sc_phoneNumber2);
			
			String patient_sc_emailAddress = " ";
			if(null!=patientUserSecondaryContact && StringUtils.isNotBlank(patientUserSecondaryContact.getEmailAddress())){
				patient_sc_emailAddress= StringUtils.trim(patientUserSecondaryContact.getEmailAddress());
			}
			logger.debug("patient_sc_emailAddress = "+patient_sc_emailAddress);
			patientUserSecondaryContact.setEmailAddress(patient_sc_emailAddress);
			
			String patient_sc_flatNumber = " ";
			if(null!=patientUserSecondaryContact && StringUtils.isNotBlank(patientUserSecondaryContact.getFlatNumber())){
				patient_sc_flatNumber= StringUtils.trim(patientUserSecondaryContact.getFlatNumber());
			}
			logger.debug("patient_sc_flatNumber = "+patient_sc_flatNumber);
			patientUserSecondaryContact.setFlatNumber(patient_sc_flatNumber);
			
			String patient_sc_locality = " ";
			if(null!=patientUserSecondaryContact && StringUtils.isNotBlank(patientUserSecondaryContact.getLocality())){
				patient_sc_locality= StringUtils.trim(patientUserSecondaryContact.getLocality());
			}
			logger.debug("patient_sc_locality = "+patient_sc_locality);
			patientUserSecondaryContact.setLocality(patient_sc_locality);
			
			String patient_sc_street = " ";
			if(null!=patientUserSecondaryContact && StringUtils.isNotBlank(patientUserSecondaryContact.getStreet())){
				patient_sc_street= StringUtils.trim(patientUserSecondaryContact.getStreet());
			}
			logger.debug("patient_sc_street = "+patient_sc_street);
			patientUserSecondaryContact.setStreet(patient_sc_street);

			String patient_sc_city = " ";
			if(null!=patientUserSecondaryContact && StringUtils.isNotBlank(patientUserSecondaryContact.getCity())){
				patient_sc_city= StringUtils.trim(patientUserSecondaryContact.getCity());
			}
			logger.debug("patient_sc_city = "+patient_sc_city);
			patientUserSecondaryContact.setCity(patient_sc_city);
			
			String patient_sc_pincode = " ";
			if(null!=patientUserSecondaryContact && StringUtils.isNotBlank(patientUserSecondaryContact.getPincode())){
				patient_sc_pincode= StringUtils.trim(patientUserSecondaryContact.getPincode());
			}
			logger.debug("patient_sc_pincode = "+patient_sc_pincode);
			patientUserSecondaryContact.setPincode(patient_sc_pincode);

			String patient_sc_state = " ";
			if(null!=patientUserSecondaryContact && StringUtils.isNotBlank(patientUserSecondaryContact.getState())){
				patient_sc_state= StringUtils.trim(patientUserSecondaryContact.getState());
			}
			logger.debug("patient_sc_state = "+patient_sc_state);
			patientUserSecondaryContact.setState(patient_sc_state);

			String patient_sc_country = " ";
			if(null!=patientUserSecondaryContact && StringUtils.isNotBlank(patientUserSecondaryContact.getCountry())){
				patient_sc_country= StringUtils.trim(patientUserSecondaryContact.getCountry());
			}
			logger.debug("patient_sc_country = "+patient_sc_country);
			patientUserSecondaryContact.setCountry(patient_sc_country);
			patientDetailsList.add(selectedPatient);//obj[0]
			patientDetailsList.add(patientUserPrimaryContact);//obj[1]
			patientDetailsList.add(tempUser);//obj[2]
			patientDetailsList.add(patientUserSecondaryContact);//obj[3]

			Gson gson = new Gson();
			String strPatientDetailsList = gson.toJson(patientDetailsList);
			response.getWriter().write(strPatientDetailsList.toString());

		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	private PatientCollectionVO getPatientsFromAppointments(
			AppointmentCollectionVO appointments, PatientService patientService) {
		PatientCollectionVO patientsForDoctor = new PatientCollectionVO();
		for (Iterator iterator = appointments.iterator(); iterator
				.hasNext();) {
			AppointmentVO appointmentVO = (AppointmentVO) iterator.next();
			patientsForDoctor.add(appointmentVO.getPatient());
		}
		return patientsForDoctor;
	}

	
	
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	
	public PatientVO getModel() {
		return patient;
	}

}
