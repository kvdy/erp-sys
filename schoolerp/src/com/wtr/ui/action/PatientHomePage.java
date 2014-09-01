package com.wtr.ui.action;

import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.Preparable;
import com.wtr.bl.services.AppointmentService;
import com.wtr.bl.services.AuditInfoService;
import com.wtr.bl.services.ContactService;
import com.wtr.bl.services.DoctorService;
import com.wtr.bl.services.LabService;
import com.wtr.bl.services.PatientService;
import com.wtr.bl.services.UserService;
import com.wtr.bl.util.Constants;
import com.wtr.bl.vo.AppointmentCollectionVO;
import com.wtr.bl.vo.AppointmentVO;
import com.wtr.bl.vo.DoctorCollectionVO;
import com.wtr.bl.vo.LabCollectionVO;
import com.wtr.bl.vo.PatientVO;
import com.wtr.bl.vo.UserVO;
import com.wtr.ui.util.ResourceLocator;
import com.wtr.ui.util.Utilities;

public class PatientHomePage extends BaseAction implements ServletRequestAware,
		ServletResponseAware, Preparable {
	
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger( PatientHomePage.class);
	private HttpServletRequest request;
	private HttpServletResponse response;
	private PatientVO patient = new PatientVO();
	
	public UserService userService;
	public PatientService patientService;
	public AuditInfoService auditInfoService;
	public ContactService contactService;
	public DoctorService doctorService;
	public LabService labService;
	public AppointmentService appointmentService;
	public String ipAddress = "";
	
	private AppointmentCollectionVO userAppointments = new AppointmentCollectionVO();
	private DoctorCollectionVO allDoctors = new DoctorCollectionVO();
	private LabCollectionVO allLabs = new LabCollectionVO();
	
	UserVO userInSession = new UserVO();
	private boolean emptyAppointmentsFlag;
	
	
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
		}catch(Exception e){
			e.printStackTrace();
		}
		logger.debug("Completing PatientHomePage:prepare()");
	}

	
	public String displaypatientHome(){
		logger.debug("HomePage: displaypatientHome()");
		try {
			AppointmentCollectionVO tmpUserAppointments = appointmentService.getAppointmentsByPatientId(userInSession.getId());
			allDoctors = doctorService.getAllDoctors();
			allLabs = labService.getAllLabs();
			if(tmpUserAppointments != null) {
				logger.debug("No. of appointments searched = "+tmpUserAppointments.getSize());
				for(AppointmentVO tmpApmt : tmpUserAppointments) {
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
	
	private boolean isDateInCurrentWeek(Date date) {
		  Calendar currentCalendar = Calendar.getInstance();
		  int week = currentCalendar.get(Calendar.WEEK_OF_YEAR);
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


	public LabCollectionVO getAllLabs() {
		return allLabs;
	}


	public void setAllLabs(LabCollectionVO allLabs) {
		this.allLabs = allLabs;
	}

	
}
