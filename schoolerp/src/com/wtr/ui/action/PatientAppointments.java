package com.wtr.ui.action;

import java.io.IOException;

import java.util.ArrayList;
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
import com.opensymphony.xwork2.Preparable;
import com.wtr.bl.services.AppointmentService;
import com.wtr.bl.services.AuditInfoService;
import com.wtr.bl.services.DoctorService;
import com.wtr.bl.services.LabService;
import com.wtr.bl.services.PatientService;
import com.wtr.bl.services.ReportInfoService;
import com.wtr.bl.services.ReportTypeService;
import com.wtr.bl.services.UserService;
import com.wtr.bl.vo.AppointmentCollectionVO;
import com.wtr.bl.vo.AppointmentVO;
import com.wtr.bl.vo.DoctorCollectionVO;
import com.wtr.bl.vo.DoctorVO;
import com.wtr.bl.vo.LabCollectionVO;
import com.wtr.bl.vo.LabVO;
import com.wtr.bl.vo.PatientCollectionVO;
import com.wtr.bl.vo.PatientVO;
import com.wtr.bl.vo.ReportInfoCollectionVO;
import com.wtr.bl.vo.ReportInfoVO;
import com.wtr.bl.vo.UserVO;

public class PatientAppointments extends BaseAction implements
		ServletRequestAware, ServletResponseAware, Preparable {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger( PatientAppointments.class);
	private HttpServletRequest servletRequest;
	private HttpServletResponse servletResponse;
	
	private AppointmentVO appointment = new AppointmentVO();
	public UserService userService;
	public AppointmentService appointmentService;
	public DoctorService doctorService;
	public AuditInfoService auditInfoService;
	public LabService labService;
	public PatientService patientService;
	public ReportTypeService reportTypeService;
	public ReportInfoService reportInfoService;
	public String ipAddress = "";
	UserVO userInSession = new UserVO();
	private AppointmentCollectionVO scheduledAppointments = new AppointmentCollectionVO();
	private AppointmentCollectionVO completedAppointments = new AppointmentCollectionVO();
	private String appointmentId;
	
	private String appointmentName;
	private String appointmentStatus;
	private String appointmentDateTime;
	private String doctorName;
	private String laboratoryName;
	private String referredTo;
	private String comments;
	private String details;
	private String reportCount;
	
	
	@SkipValidation
	public String displayPatientAppointments() {
		logger.debug("Inside PatientAppointments :: displayPatientAppointments");
		try {
			AppointmentCollectionVO userAppointments = appointmentService.getAppointmentsByPatientId(userInSession.getId());
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
		} catch(Exception e) {
			logger.error(e.getMessage());
			return "error";
		}
		return "success";
	}
	
	public void populateAppointmentDetails() throws Exception {
		String id = StringUtils.trim(servletRequest.getParameter("selectedId"));
		logger.debug("Input id: "+id);
		List<Object> appointmentDetailsList = new ArrayList<Object>();
		AppointmentVO app = appointmentService.getAppointmentById(id);
		String details=" ";
		if(null != app && StringUtils.isNotBlank(app.getDetails())) {
			details = StringUtils.trim(app.getDetails());
		}
		logger.debug("details: "+details);
		String comments = " ";
		if(null != app && StringUtils.isNotBlank(app.getComments())) {
			comments = StringUtils.trim(app.getComments());
		}
		logger.debug("comments: "+comments);
		String referredTo = " ";
		if(null != app && StringUtils.isNotBlank(app.getReferredTo())){
			referredTo = StringUtils.trim(app.getReferredTo());
		}
		logger.debug("referredTo: "+referredTo);
		PatientVO patient = app.getPatient();
		logger.debug("Patient: "+patient);
		UserVO patientUser = userService.getUserById(patient.getId());
		String patientFullName = patientUser.getFirstName() + " " +
				patientUser.getMiddleName() + " " + patientUser.getLastName();
		logger.debug("Patient Fullname: "+patientFullName);
		DoctorVO doctor = app.getDoctor();
		logger.debug("Doctor: "+doctor);
		UserVO doctorUser = userService.getUserById(doctor.getId());
		String doctorFullName = doctorUser.getFirstName() + " " +
				doctorUser.getMiddleName() + " " + doctorUser.getLastName();
		logger.debug("Doctor Fullname: "+doctorFullName);
		LabVO lab = app.getLab();
		String labName = lab.getName();
		logger.debug("labName: "+labName);
		String[] docPatientLabNames = new String[3];
		docPatientLabNames[1] = doctorFullName;
		docPatientLabNames[0] = patientFullName;
		docPatientLabNames[2] = labName;
		AppointmentVO selectedAppointment = new AppointmentVO();
		ReportInfoVO reportInfoVOCrit = new ReportInfoVO();
		AppointmentVO appointmentVOCrit = new AppointmentVO();
		appointmentVOCrit.setId(id);
		reportInfoVOCrit.setAppointment(appointmentVOCrit);
		ReportInfoCollectionVO reportInfoVO = reportInfoService.searchReportInfo(reportInfoVOCrit);
		reportCount=String.valueOf(reportInfoVO.getSize());
		servletRequest.getSession().setAttribute("appointmentId", id);
		try {
			selectedAppointment.setComments(comments);
			selectedAppointment.setDetails(details);
			selectedAppointment.setReferredTo(referredTo);
			selectedAppointment.setStrAppointmentDateTime(app.getStrAppointmentDateTime());
			selectedAppointment.setStatus(app.getStatus());
			selectedAppointment.setLastUpdatedBy(app.getLastUpdatedBy());
			selectedAppointment.setLastUpdatedDate(app.getLastUpdatedDate());
			selectedAppointment.setName(app.getName());
			DoctorCollectionVO allDoctors = doctorService.getAllDoctors();
			ArrayList<String> allDocNames = new ArrayList<String>();
			ArrayList<String> allDocUserNames = new ArrayList<String>();
			if(allDoctors!=null){
				logger.debug("No. of doctors searched = "+allDoctors.size());
				for(DoctorVO tempDoctor : allDoctors) {
					UserVO user = userService.getUserById(tempDoctor.getId());
					allDocNames.add(user.getFirstName().trim()+" "+user.getMiddleName().trim()+" "+user.getLastName().trim());
					allDocUserNames.add(user.getUserName());
				}
			}				
			ArrayList<String> allLabNames = new ArrayList<String>();
			LabCollectionVO allLabs = labService.getAllLabs();
			if(allLabs!=null && allLabs.size()>0){
				for(int i=0;i<allLabs.size();i++){
					LabVO tempLab = allLabs.getLabAt(i);
					allLabNames.add(tempLab.getName());
				}
			}
			
			PatientCollectionVO allPatients = patientService.getAllPatients();
			ArrayList<String> allPatientNames = new ArrayList<String>();
			ArrayList<String> allPatientUserNames = new ArrayList<String>();
			if(allPatients!=null){
				logger.debug("No. of patients searched = "+allPatients.size());
				for(PatientVO tempPatient:allPatients){
					UserVO user = userService.getUserById(tempPatient.getId());
					allPatientNames.add(user.getFirstName().trim()+" "+user.getMiddleName().trim()+" "+user.getLastName().trim());
					allPatientUserNames.add(user.getUserName());
				}
			}			
			appointmentDetailsList.add(selectedAppointment);//obj[0]
			appointmentDetailsList.add(docPatientLabNames);//obj[1]
			appointmentDetailsList.add(allDocNames);//obj[2]
			appointmentDetailsList.add(allPatientNames);//obj[3]
			appointmentDetailsList.add(allLabNames);//obj[4]
			appointmentDetailsList.add(allDocUserNames);//obj[5]
			appointmentDetailsList.add(allPatientUserNames);//obj[6]
			appointmentDetailsList.add(reportCount);//obj[7]
			Gson gson = new Gson();
			String strAppointmentDetailsList = gson.toJson(appointmentDetailsList);
			servletResponse.getWriter().write(strAppointmentDetailsList.toString());			
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	public void prepare() throws Exception {
		logger.debug("Inside display patient appointments.prepare()");
		WebApplicationContext context =
				WebApplicationContextUtils.getRequiredWebApplicationContext(ServletActionContext.getServletContext());
		userService = (UserService)context.getBean("userService");
		auditInfoService = (AuditInfoService)context.getBean("auditInfoService");
		appointmentService = (AppointmentService)context.getBean("appointmentService");
		patientService = (PatientService)context.getBean("patientService");
		doctorService = (DoctorService)context.getBean("doctorService");
		labService = (LabService)context.getBean("labService");
		reportTypeService = (ReportTypeService)context.getBean("reportTypeService");
		reportInfoService = (ReportInfoService)context.getBean("reportInfoService");
		ipAddress = servletRequest.getHeader("X-FORWARDED-FOR");
		if (ipAddress == null) {
		   ipAddress = servletRequest.getRemoteAddr();
		}
		//logger.debug("client's ipAddress ="+ipAddress);
		Object obj = servletRequest.getSession().getAttribute("user");		
		if(obj!=null){
			userInSession = (UserVO) obj;				
		}
	}

	public HttpServletRequest getServletRequest() {
		return servletRequest;
	}

	public void setServletRequest(HttpServletRequest request) {
		this.servletRequest = request;
	}

	public HttpServletResponse getServletResponse() {
		return servletResponse;
	}

	public void setServletResponse(HttpServletResponse response) {
		this.servletResponse = response;
	}

	public AppointmentVO getAppointment() {
		return appointment;
	}

	public void setAppointment(AppointmentVO appointment) {
		this.appointment = appointment;
	}

	public AppointmentCollectionVO getScheduledAppointments() {
		return scheduledAppointments;
	}

	public void setScheduledAppointments(AppointmentCollectionVO scheduledAppointments) {
		this.scheduledAppointments = scheduledAppointments;
	}

	public String getAppointmentId() {
		return appointmentId;
	}

	public void setAppointmentId(String appointmentId) {
		this.appointmentId = appointmentId;
	}

	public String getAppointmentName() {
		return appointmentName;
	}

	public void setAppointmentName(String appointmentName) {
		this.appointmentName = appointmentName;
	}

	public String getAppointmentDateTime() {
		return appointmentDateTime;
	}

	public void setAppointmentDateTime(String appointmentDateTime) {
		this.appointmentDateTime = appointmentDateTime;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public String getLaboratoryName() {
		return laboratoryName;
	}

	public void setLaboratoryName(String laboratoryName) {
		this.laboratoryName = laboratoryName;
	}

	public String getReferredTo() {
		return referredTo;
	}

	public void setReferredTo(String referredTo) {
		this.referredTo = referredTo;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getAppointmentStatus() {
		return appointmentStatus;
	}

	public void setAppointmentStatus(String appointmentStatus) {
		this.appointmentStatus = appointmentStatus;
	}

	public String getReportCount() {
		return reportCount;
	}

	public void setReportCount(String reportCount) {
		this.reportCount = reportCount;
	}

	public AppointmentCollectionVO getCompletedAppointments() {
		return completedAppointments;
	}

	public void setCompletedAppointments(
			AppointmentCollectionVO completedAppointments) {
		this.completedAppointments = completedAppointments;
	}
	

}
