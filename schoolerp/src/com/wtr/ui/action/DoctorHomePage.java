package com.wtr.ui.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.google.gson.Gson;
import com.opensymphony.xwork2.Preparable;
import com.wtr.bl.services.AppointmentService;
import com.wtr.bl.services.AuditInfoService;
import com.wtr.bl.services.ContactService;
import com.wtr.bl.services.DoctorService;
import com.wtr.bl.services.LabService;
import com.wtr.bl.services.PatientService;
import com.wtr.bl.services.PaymentService;
import com.wtr.bl.services.ReportCommentService;
import com.wtr.bl.services.ReportInfoService;
import com.wtr.bl.services.UserService;
import com.wtr.bl.util.Constants;
import com.wtr.bl.vo.AppointmentCollectionVO;
import com.wtr.bl.vo.AppointmentVO;
import com.wtr.bl.vo.ContactCollectionVO;
import com.wtr.bl.vo.ContactVO;
import com.wtr.bl.vo.DoctorCollectionVO;
import com.wtr.bl.vo.DoctorVO;
import com.wtr.bl.vo.LabCollectionVO;
import com.wtr.bl.vo.LabVO;
import com.wtr.bl.vo.PatientCollectionVO;
import com.wtr.bl.vo.PatientVO;
import com.wtr.bl.vo.PaymentCollectionVO;
import com.wtr.bl.vo.PaymentVO;
import com.wtr.bl.vo.ReportCommentCollectionVO;
import com.wtr.bl.vo.ReportCommentVO;
import com.wtr.bl.vo.ReportInfoCollectionVO;
import com.wtr.bl.vo.ReportInfoVO;
import com.wtr.bl.vo.UserVO;
import com.wtr.ui.util.ResourceLocator;
import com.wtr.ui.util.UIConstants;
import com.wtr.ui.util.Utilities;

public class DoctorHomePage extends BaseAction implements ServletRequestAware,
		ServletResponseAware, Preparable {
	
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger( DoctorHomePage.class);
	private HttpServletRequest request;
	private HttpServletResponse response;
	private PatientVO searchedPatient = new PatientVO();
	private DoctorVO doctor = new DoctorVO();
	
	
	public UserService userService;
	public PatientService patientService;
	public AuditInfoService auditInfoService;
	public ContactService contactService;
	public ReportInfoService reportInfoService;
	public DoctorService doctorService;
	public LabService labService;
	public AppointmentService appointmentService;
	public ReportCommentService reportCommentService;
	public PaymentService paymentService;
	public String ipAddress = "";
	private String failureMessage;
	private String search_labName="";

	private AppointmentVO patientAppointment = new AppointmentVO();
	private ReportCommentCollectionVO reportComments = new ReportCommentCollectionVO();
	private AppointmentCollectionVO userAppointments = new AppointmentCollectionVO();
	private AppointmentCollectionVO completedAppointments = new AppointmentCollectionVO();
	private AppointmentCollectionVO scheduledAppointments = new AppointmentCollectionVO();
	private DoctorCollectionVO allDoctors = new DoctorCollectionVO();
	private ArrayList<String> allLabs = new ArrayList<String>();
	private ReportInfoCollectionVO patientReports = new ReportInfoCollectionVO();
	private PatientCollectionVO searchedpatients = new PatientCollectionVO();
	private ReportCommentVO reportComment = new ReportCommentVO();
	private ReportInfoVO tmpReportInfoVO = new ReportInfoVO();
	private PaymentCollectionVO onScreenPymtsList = new PaymentCollectionVO();
	private ArrayList<String> dateFilter = new ArrayList<String>();
	private PaymentCollectionVO pymtsForRpt = new PaymentCollectionVO();
	private ContactVO priContact = new ContactVO();
	private ContactVO secContact = new ContactVO();

	
	UserVO userInSession = new UserVO();
	UserVO pUser = new UserVO();
	private boolean emptyAppointmentsFlag;
	
	private String fname;
	private String lname;
	private String id;
	private String reports_path = "";
	private String finalReportsFilePath = "";
	private String reportId;
	private String dateFilterName;
	private String f_dateBegin;
	private String f_dateEnd;
	
	private List<String> myRptCmts = new ArrayList<String>();

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
	private String doctorName;
	private String labName;
	private String patientName;
	
	
	
	
	public void prepare() throws Exception {
		logger.debug("Inside PatientHomePage:prepare()");
		try{
			userService = (UserService)ResourceLocator.getInstance().getContext().getBean("userService");
			auditInfoService = (AuditInfoService)ResourceLocator.getInstance().getContext().getBean("auditInfoService");
			patientService = (PatientService)ResourceLocator.getInstance().getContext().getBean("patientService");
			contactService = (ContactService)ResourceLocator.getInstance().getContext().getBean("contactService");
			labService = (LabService)ResourceLocator.getInstance().getContext().getBean("labService");
			doctorService = (DoctorService)ResourceLocator.getInstance().getContext().getBean("doctorService");
			appointmentService = (AppointmentService)ResourceLocator.getInstance().getContext().getBean("appointmentService");
			reportInfoService = (ReportInfoService)ResourceLocator.getInstance().getContext().getBean("reportInfoService");
			reportCommentService = (ReportCommentService)ResourceLocator.getInstance().getContext().getBean("reportCommentService");
			paymentService = (PaymentService)ResourceLocator.getInstance().getContext().getBean("paymentService");
			reports_path = ResourceLocator.getInstance().getAppProps().getProperty("reports_path");
			logger.debug("In prepare patientService ="+patientService);
			//is client behind something?
			ipAddress = request.getHeader("X-FORWARDED-FOR");
			emptyAppointmentsFlag = false;
			if (ipAddress == null) {
			   ipAddress = request.getRemoteAddr();
			}
			logger.debug("client's ipAddress ="+ipAddress);
			Object obj = request.getSession().getAttribute("user");
			if(obj!=null){
				userInSession = (UserVO) obj;
			}
			logger.debug("userInSession is "+userInSession.getAttributesAsString());
			allLabs = getAllLabsNames();
			dateFilter = getPymtDateFilter();
		}catch(Exception e){
			e.printStackTrace();
		}
		logger.debug("Completing PatientHomePage:prepare()");
	}

	public String displaydoctorHome(){
		logger.debug("HomePage: displaydoctorHome()");

		try {
			AppointmentCollectionVO docAppointments = 
					appointmentService.getAppointmentsByDoctorId(userInSession.getId());
			if (null != docAppointments) {
				logger.debug("No. of appointments searched = "+docAppointments.getSize());
				for(AppointmentVO tmpApmt : docAppointments) {
					boolean thisWeekFlag = isDateInCurrentWeek(
								Utilities.getDateFromString(tmpApmt.getStrAppointmentDateTime(), Constants.APPOINTMENT_DISPLAY_FORMAT));
					boolean nextWeekFlag = isDateInNextWeek(
								Utilities.getDateFromString(tmpApmt.getStrAppointmentDateTime(), Constants.APPOINTMENT_DISPLAY_FORMAT));
					if(thisWeekFlag || nextWeekFlag)
						userAppointments.add(tmpApmt);
				}
				if(userAppointments.getSize() > 0)
					emptyAppointmentsFlag = false;
				else
					emptyAppointmentsFlag = true;
			}
		} catch(Exception e) {
			logger.error(e.getMessage());
			return "error";
		}
		return "success";
	}
	
	public String displayPymtRpt(){
		logger.debug("Inside PaymentAdmin: displayPaymentAdmin()");
		logger.debug("from date: " + getF_dateBegin() + " :: " + getF_dateEnd());
		logger.debug("Filter Options: " + getLabName() + "::" + getDateFilterName());
		String labFilter = getLabName();
		String dtFilter = getDateFilterName();
		AppointmentCollectionVO docAppointments = new AppointmentCollectionVO();
		AppointmentCollectionVO tmpAppointments = new AppointmentCollectionVO();
		PaymentCollectionVO tmpPymts = new PaymentCollectionVO();
		try{
			tmpAppointments = 
					appointmentService.getAppointmentsByDoctorId(userInSession.getId());

			if(labFilter.equals("All Labs")) {
				docAppointments = tmpAppointments;
			} else {
				docAppointments = getApptsByLabName(tmpAppointments, labFilter);
			}
			
			for (int i = 0; i < docAppointments.getSize(); i++) {
				PaymentVO tempPayment = new PaymentVO();
				tempPayment.setAppointment(docAppointments.get(i));
				PaymentCollectionVO tmpPymtRpts = paymentService.searchPayment(tempPayment);
				tmpPymts.addAll(tmpPymtRpts);
			}
			
			pymtsForRpt = getPymtsByDateFilter(dtFilter, tmpPymts);
			if(pymtsForRpt!=null){
				logger.debug("No. of payments searched = "+pymtsForRpt.getSize());				
			}
		}catch(Exception e){
			logger.error(e.getMessage());
			return "error";
		}
		return "success";
	}
	
	public String displaySearchedPatientsList(){
		String retStr = "";
		logger.debug("DoctorHomePage: displaySearchedPatientsList()");
		logger.debug("Patient First Name: " + getFname() + " & Patient Last Name: " + getLname());
		PatientVO tempPatient = new PatientVO();
		UserVO patientUser = new UserVO();
		patientUser.setFirstName(getFname());
		patientUser.setLastName(getLname());
		tempPatient.setUser(patientUser);
		try {
			searchedpatients = patientService.searchPatients(tempPatient);
			if(searchedpatients.size() > 1) {
				logger.debug("Number of Searched patients : " + searchedpatients.getSize());
				retStr = SUCCESS;
			} else if(searchedpatients.size() == 1){
				setId(searchedpatients.get(0).getId());
				retStr = "single";
			} else {
				logger.debug("NO patients found for search criteria");
				failureMessage=getText("nopatientfound");
				retStr = ERROR;
			}
		} catch(Exception e){
			logger.error(e.getMessage());
			return ERROR;
		}
		return retStr;
	}
	public String displaySearchedPatient() {
		String retStr = "";
		logger.debug("HomePage:displaySearchedPatient()");
		
		try {
				String pId = request.getParameter("patientId");
				if(null != id){
					searchedPatient = patientService.getPatientById(getId());
				} else {
					searchedPatient = patientService.getPatientById(pId);
					setId(pId);
				}
				pUser = userService.getUserById(searchedPatient.getId());
				ContactCollectionVO contacts = null;
				ContactVO tempContact = new ContactVO();
				tempContact.setUser(pUser);
				contacts = contactService.searchUserContacts(tempContact);
				populateContactDetails(contacts);
				populatePatientDetails(searchedPatient,pUser);
				userAppointments = appointmentService.getAppointmentsByPatientId(searchedPatient.getId());
				if(userAppointments != null) {
					logger.debug("No. of appointments searched = "+userAppointments.getSize());
					for (int i = 0; i < userAppointments.getSize(); i++) {
						if(userAppointments.get(i).getStatus().name().equals("Complete")) {
							completedAppointments.add(userAppointments.get(i));
						} else if(userAppointments.get(i).getStatus().name().equals("Scheduled")) {
							scheduledAppointments.add(userAppointments.get(i));
						}
					}
				}
				retStr = SUCCESS;
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		logger.debug("Completing DoctorHomePage : displaySearchedPatient()");
		return retStr;
	}
	
	public String displaySearchedPatientReports() {
		logger.debug("Inside DoctorHomePage:displaySearchedPatientreports");
		System.out.println("Appintment Selected: "
				+ request.getParameter("appointmentId"));
		try {
			String appointmentId = request.getParameter("appointmentId");
			patientAppointment = appointmentService
					.getAppointmentById(appointmentId);
			pUser = userService.getUserById(patientAppointment.getPatient().getId());
			firstname = pUser.getFirstName();
			lastname = pUser.getLastName();
			logger.debug("Patient : " + pUser.getFirstName() + " " + pUser.getLastName());
			ReportInfoVO rptVO = new ReportInfoVO();
			rptVO.setAppointment(patientAppointment);
			patientReports = reportInfoService.searchReportInfo(rptVO);
			DoctorVO doctor = patientAppointment.getDoctor();
			PatientVO patient = patientAppointment.getPatient();
			logger.debug("Doctor: " + doctor);
			UserVO doctorUser = userService.getUserById(doctor.getId());
			doctorName = doctorUser.getFirstName() + " "
					+ doctorUser.getMiddleName() + " "
					+ doctorUser.getLastName();
			logger.debug("Doctor Fullname: " + doctorName);
			UserVO patientUser = userService.getUserById(patient.getId());
			patientName = patientUser.getFirstName() + " "
					+ patientUser.getMiddleName() + " "
					+ patientUser.getLastName();
			logger.debug("patient Fullname: " + patientName);
			LabVO lab = patientAppointment.getLab();
			labName = lab.getName();
			logger.debug("labName: " + labName);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return "error";
		}
		return "success";
	}
	
	public String displayReportComments(){
		logger.debug("Inside DoctorHomePage : displayReportComments" );
		try {
		    reportId = request.getParameter("reportId");
			
			tmpReportInfoVO.setId(reportId);
			ReportCommentVO tmpReportCommentVO = new ReportCommentVO();
			tmpReportCommentVO.setReportInfo(tmpReportInfoVO);
			reportComments = reportCommentService.searchReportComments(tmpReportCommentVO);
			tmpReportInfoVO = reportInfoService.getReportInfoById(reportId);
			finalReportsFilePath = reports_path + tmpReportInfoVO.getRcn()+".pdf";
			logger.debug("Report for comments: "+finalReportsFilePath);
			UserVO tmpDocUsr;
			if(null != reportComments){
				for (int i = 0; i < reportComments.getSize(); i++) {					
					String id = reportComments.get(i).getCommentedBy();
					tmpDocUsr = userService.getUserById(id);
					String tmpDocFullName = tmpDocUsr.getPrefix() + " " 
							+ tmpDocUsr.getFirstName() + " " + tmpDocUsr.getLastName();
					reportComments.get(i).setCommentedBy(tmpDocFullName);
				}
			}
		} catch (Exception e){
			logger.error(e.getMessage());
			return ERROR;
		}
		return SUCCESS;
	}

	public void addReportComments(){
		logger.debug("Inside DoctorHomePage:addReportComments()");
		logger.debug("Report Id: "+request.getParameter("reportId"));
		logger.debug("Report Comment: "+request.getParameter("comment"));
		try {
			if(null != request.getParameter("comment")) {
				reportComment.setComment(request.getParameter("comment"));
				reportComment.setCommentedDate(new Date());
				reportComment.setCommentedBy(userInSession.getId());
				ReportInfoVO rptInfo = reportInfoService.getReportInfoById(request.getParameter("reportId"));
				reportComment.setReportInfo(rptInfo);
				reportCommentService.addReportComment(reportComment);
				logger.debug("comment added successfully");
				
				reportComments = reportCommentService.searchReportComments(reportComment);
				logger.debug("report comments size = "+reportComments.size());
				
				UserVO tmpDocUsr;
				logger.debug("here 1");
				if(null != reportComments){
					logger.debug("inside if");
					for (int i = 0; i < reportComments.getSize(); i++) {						
						String id = reportComments.get(i).getCommentedBy();
						tmpDocUsr = userService.getUserById(id);
						String tmpDocFullName = tmpDocUsr.getPrefix() + " " 
								+ tmpDocUsr.getFirstName() + " " + tmpDocUsr.getLastName();
						reportComments.get(i).setCommentedBy(tmpDocFullName);
					}
					logger.debug("adding reportCommentsList");
					
					Gson gson = new Gson();
					logger.debug("gson = "+gson);
					String strReportCommentsList = gson.toJson(reportComments);
					logger.debug("strReportCommentsList ="+strReportCommentsList);
					response.getWriter().write(strReportCommentsList.toString());
				}
			}
		} catch (Exception e){
			logger.debug(e.getMessage());
			
		}
		
		
	}
	boolean isDateInCurrentWeek(Date date) {
		  Calendar currentCalendar = Calendar.getInstance();
		  int week = currentCalendar.get(Calendar.WEEK_OF_YEAR);
		  int year = currentCalendar.get(Calendar.YEAR);
		  Calendar targetCalendar = Calendar.getInstance();
		  targetCalendar.setTime(date);
		  int targetWeek = targetCalendar.get(Calendar.WEEK_OF_YEAR);
		  int targetYear = targetCalendar.get(Calendar.YEAR);
		  return week == targetWeek && year == targetYear;
		}
	
	boolean isDateInPreviousWeek(Date date) {
		Calendar currentCalendar = Calendar.getInstance();
		int week = currentCalendar.get(Calendar.WEEK_OF_YEAR) - 1;
		int year = currentCalendar.get(Calendar.YEAR);
		Calendar targetCalendar = Calendar.getInstance();
		targetCalendar.setTime(date);
		int targetWeek = targetCalendar.get(Calendar.WEEK_OF_YEAR);
		int targetYear = targetCalendar.get(Calendar.YEAR);
		return week == targetWeek && year == targetYear;
	}
	
	private boolean isDateInNextWeek(Date date){
		Calendar currentCalendar = Calendar.getInstance();
		int week = currentCalendar.get(Calendar.WEEK_OF_YEAR) + 1;
		int year = currentCalendar.get(Calendar.YEAR);
		Calendar targetCalendar = Calendar.getInstance();
		targetCalendar.setTime(date);
		int targetWeek = targetCalendar.get(Calendar.WEEK_OF_YEAR);
		int targetYear = targetCalendar.get(Calendar.YEAR);
		return week == targetWeek && year == targetYear;
	}
	
	private boolean isDateInCurrentMonth(Date date){
		Calendar currentCalendar = Calendar.getInstance();
		int month = currentCalendar.get(Calendar.MONTH);
		int year = currentCalendar.get(Calendar.YEAR);
		Calendar targetCalendar = Calendar.getInstance();
		targetCalendar.setTime(date);
		int targetMonth = targetCalendar.get(Calendar.MONTH);
		int targetYear = targetCalendar.get(Calendar.YEAR);
		return month == targetMonth && year == targetYear;
	}
	
	private boolean isDateInPreviousMonth(Date date){
		Calendar currentCalendar = Calendar.getInstance();
		int month = currentCalendar.get(Calendar.MONTH) - 1;
		int year = currentCalendar.get(Calendar.YEAR);
		Calendar targetCalendar = Calendar.getInstance();
		targetCalendar.setTime(date);
		int targetMonth = targetCalendar.get(Calendar.MONTH);
		int targetYear = targetCalendar.get(Calendar.YEAR);
		return month == targetMonth && year == targetYear;
	}

	private boolean isDateInCustomDateRange(Date date){
		return date.after(Utilities.getDateFromString(getF_dateBegin(),UIConstants.DATE_FORMAT)) 
				&& date.before(Utilities.getDateFromString(getF_dateEnd(),UIConstants.DATE_FORMAT));
	}
	private void populatePatientDetails(PatientVO patient, 
			UserVO user) {
		
		setUsername(user.getUserName());
		setFirstname(user.getFirstName());
		setLastname(user.getLastName());
		setMembershipType(patient.getMembershipType().toString());
		setEmploymentType(patient.getEmploymentType().toString());
		setEnrollmentMode(patient.getEnrollmentMode().toString());
		setRelationshipType(patient.getRelationshipType().toString());

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
		setSumAssured(String.valueOf(patient.getSumAssured()));
		setNote(patient.getNote());
		setEnrollmentReferredBy(patient.getEnrollmentReferredBy());
		setCompanyName(patient.getCompanyName());
		setGender(user.getGender());
	}

	private ArrayList<String> getAllLabsNames(){
		ArrayList<String> allLabNames = new ArrayList<String>();
		try{
			LabCollectionVO allLabs = labService.getAllLabs();
			if(allLabs!=null && allLabs.size()>0){
				logger.debug("No. of Labs searched in getAllLabsNames()= "+allLabs.size());
				for(int i=0;i<allLabs.size();i++){
					LabVO tempLab = allLabs.getLabAt(i);
					allLabNames.add(tempLab.getName());
				}
			}	
		}catch(Exception e){
			e.printStackTrace();
			logger.debug(e.getMessage());
		}
		return allLabNames;
	}

	private ArrayList<String> getPymtDateFilter(){
		ArrayList<String> tmpDateFilter = new ArrayList<String>();
		tmpDateFilter.add("Current Week");
		tmpDateFilter.add("Current Month");
		tmpDateFilter.add("Current Year");
		tmpDateFilter.add("Previous Week");
		tmpDateFilter.add("Previous Month");
		tmpDateFilter.add("Custom Date Range");
		return tmpDateFilter;
	}
	
	private AppointmentCollectionVO getApptsByLabName(AppointmentCollectionVO appts, String lab) {
		AppointmentCollectionVO tmpApptVO = new AppointmentCollectionVO();
		for (int i = 0; i < appts.getSize(); i++) {
			if(appts.get(i).getLab().getName().equals(lab)){
				tmpApptVO.add(appts.get(i));
			}
		}
		return tmpApptVO;
	}
	
	private PaymentCollectionVO getPymtsByDateFilter(String dteFilter, PaymentCollectionVO pymts){
		PaymentCollectionVO retPymts = new PaymentCollectionVO();
		if(dteFilter.equals("All Dates")) {
			retPymts = pymts;
		} else if(dteFilter.equals("Current Week")) {
			for (int i = 0; i < pymts.getSize(); i++) {
				if(isDateInCurrentWeek(pymts.get(i).getPaymentDate()))
					retPymts.add(pymts.get(i));
			}
		} else if(dteFilter.equals("Previous Week")) {
			for (int i = 0; i < pymts.getSize(); i++) {
				if(isDateInPreviousWeek(pymts.get(i).getPaymentDate()))
					retPymts.add(pymts.get(i));
			}
		} else if(dteFilter.equals("Current Month")) {
			for (int i = 0; i < pymts.getSize(); i++) {
				if(isDateInCurrentMonth(pymts.get(i).getPaymentDate()))
					retPymts.add(pymts.get(i));
			} 
		} else if(dteFilter.equals("Previous Month")) {
			for (int i = 0; i < pymts.getSize(); i++) {
				if(isDateInPreviousMonth(pymts.get(i).getPaymentDate()))
					retPymts.add(pymts.get(i));
			}
		} else if(dteFilter.equals("Custom Date Range")) {
			for (int i = 0; i < pymts.getSize(); i++) {
				if(isDateInCustomDateRange(pymts.get(i).getPaymentDate()))
					retPymts.add(pymts.get(i));
			}
		}
		
		return retPymts;
	}
	
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
	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}


	public AppointmentCollectionVO getUserAppointments() {
		return userAppointments;
	}


	public void setUserAppointments(AppointmentCollectionVO userAppointments) {
		this.userAppointments = userAppointments;
	}


	public boolean isEmptyAppointmentsFlag() {
		return emptyAppointmentsFlag;
	}


	public void setEmptyAppointments(boolean emptyAppointmentsFlag) {
		this.emptyAppointmentsFlag = emptyAppointmentsFlag;
	}


	public DoctorCollectionVO getAllDoctors() {
		return allDoctors;
	}


	public void setAllDoctors(DoctorCollectionVO allDoctors) {
		this.allDoctors = allDoctors;
	}


	public ArrayList<String> getAllLabs() {
		return allLabs;
	}


	public void setAllLabs(ArrayList<String> allLabs) {
		this.allLabs = allLabs;
	}

	public DoctorVO getDoctor() {
		return doctor;
	}

	public void setDoctor(DoctorVO doctor) {
		this.doctor = doctor;
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

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getFname() {
		return fname;
	}
	
	public void setFname(String fname){
		this.fname = fname;
	}
	
	public String getLname(){
		return lname;
	}
	
	public void setLname(String lname){
		this.lname = lname;
	}

	public AppointmentVO getPatientAppointment() {
		return patientAppointment;
	}

	public void setPatientAppointment(AppointmentVO patientAppointment) {
		this.patientAppointment = patientAppointment;
	}

	public ReportInfoCollectionVO getPatientReports() {
		return patientReports;
	}

	public void setPatientReports(ReportInfoCollectionVO patientReports) {
		this.patientReports = patientReports;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public String getLabName() {
		return labName;
	}

	public void setLabName(String labName) {
		this.labName = labName;
	}

	public PatientCollectionVO getSearchedpatients() {
		return searchedpatients;
	}

	public void setSearchedpatients(PatientCollectionVO searchedpatients) {
		this.searchedpatients = searchedpatients;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public UserVO getpUser() {
		return pUser;
	}

	public void setpUser(UserVO pUser) {
		this.pUser = pUser;
	}

	public ReportCommentCollectionVO getReportComments() {
		return reportComments;
	}

	public void setReportComments(ReportCommentCollectionVO reportComments) {
		this.reportComments = reportComments;
	}

	public String getReports_path() {
		return reports_path;
	}

	public void setReports_path(String reports_path) {
		this.reports_path = reports_path;
	}

	public String getFinalReportsFilePath() {
		return finalReportsFilePath;
	}

	public void setFinalReportsFilePath(String finalReportsFilePath) {
		this.finalReportsFilePath = finalReportsFilePath;
	}

	public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	public ReportCommentVO getReportComment() {
		return reportComment;
	}

	public void setReportComment(ReportCommentVO reportComment) {
		this.reportComment = reportComment;
	}

	public List<String> getMyRptCmts() {
		return myRptCmts;
	}

	public void setMyRptCmts(List<String> myRptCmts) {
		this.myRptCmts = myRptCmts;
	}

	public ReportInfoVO getTmpReportInfoVO() {
		return tmpReportInfoVO;
	}

	public void setTmpReportInfoVO(ReportInfoVO tmpReportInfoVO) {
		this.tmpReportInfoVO = tmpReportInfoVO;
	}

	public PaymentCollectionVO getOnScreenPymtsList() {
		return onScreenPymtsList;
	}

	public void setOnScreenPymtsList(PaymentCollectionVO onScreenPymtsList) {
		this.onScreenPymtsList = onScreenPymtsList;
	}

	public ArrayList<String> getDateFilter() {
		return dateFilter;
	}

	public void setDateFilter(ArrayList<String> dateFilter) {
		this.dateFilter = dateFilter;
	}

	public PaymentCollectionVO getPymtsForRpt() {
		return pymtsForRpt;
	}

	public void setPymtsForRpt(PaymentCollectionVO pymtsForRpt) {
		this.pymtsForRpt = pymtsForRpt;
	}

	public String getDateFilterName() {
		return dateFilterName;
	}

	public void setDateFilterName(String dateFilterName) {
		this.dateFilterName = dateFilterName;
	}

	public String getF_dateBegin() {
		return f_dateBegin;
	}

	public void setF_dateBegin(String f_dateBegin) {
		this.f_dateBegin = f_dateBegin;
	}

	public String getF_dateEnd() {
		return f_dateEnd;
	}

	public void setF_dateEnd(String f_dateEnd) {
		this.f_dateEnd = f_dateEnd;
	}

	public AppointmentCollectionVO getCompletedAppointments() {
		return completedAppointments;
	}

	public void setCompletedAppointments(
			AppointmentCollectionVO completedAppointments) {
		this.completedAppointments = completedAppointments;
	}

	public AppointmentCollectionVO getScheduledAppointments() {
		return scheduledAppointments;
	}

	public void setScheduledAppointments(
			AppointmentCollectionVO scheduledAppointments) {
		this.scheduledAppointments = scheduledAppointments;
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

	public String getFailureMessage() {
		return failureMessage;
	}

	public void setFailureMessage(String failureMessage) {
		this.failureMessage = failureMessage;
	}
	
	
	

}
