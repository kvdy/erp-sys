package com.wtr.ui.action;

import java.io.ByteArrayOutputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
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
import com.wtr.bl.services.ReportTypeKeyService;
import com.wtr.bl.services.ReportTypeService;
import com.wtr.bl.services.ReportVariableService;
import com.wtr.bl.services.UserService;
import com.wtr.bl.vo.AppointmentVO;
import com.wtr.bl.vo.AuditInfoVO;
import com.wtr.bl.vo.DoctorVO;
import com.wtr.bl.vo.LabVO;
import com.wtr.bl.vo.PatientVO;
import com.wtr.bl.vo.ReportInfoCollectionVO;
import com.wtr.bl.vo.ReportInfoVO;
import com.wtr.bl.vo.ReportStatus;
import com.wtr.bl.vo.ReportTypeCollectionVO;
import com.wtr.bl.vo.ReportTypeKeyVO;
import com.wtr.bl.vo.ReportTypeVO;
import com.wtr.bl.vo.ReportVariableCollectionVO;
import com.wtr.bl.vo.ReportVariableVO;
import com.wtr.bl.vo.UserVO;
import com.wtr.ui.util.ResourceLocator;
import com.wtr.ui.util.UIConstants;

public class PatientReports extends BaseAction implements ServletRequestAware,
		ServletResponseAware, Preparable {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(PatientReports.class);
	private HttpServletRequest servletRequest;
	private HttpServletResponse servletResponse;
	private ReportInfoVO reportInfo = new ReportInfoVO();
	private ReportInfoCollectionVO patientReports = new ReportInfoCollectionVO();
	public UserService userService;
	public AppointmentService appointmentService;
	public DoctorService doctorService;
	public AuditInfoService auditInfoService;
	public LabService labService;
	public PatientService patientService;
	public ReportTypeService reportTypeService;
	public ReportInfoService reportInfoService;
	public ReportTypeKeyService reportTypeKeyService;
	public ReportVariableService reportVariableService;
	public String ipAddress = "";
	public String templates_path = "";
	UserVO userInSession = new UserVO();
	private String reportinfo_update_hreportTypeId;
	private ReportInfoCollectionVO searchedReportInfos = new ReportInfoCollectionVO();
	private String reportInfoId;
	private String reportInfoName;
	public byte[] pdfBytes = null;
	private String appointmentId;
	private AppointmentVO patientAppointment;
	private String doctorName;
	private String labName;
	private String patientName;
	private String failureMessage;
	private String reports_path = "";
	private FileInputStream reportInputStream;
	private String reportStatus;

	public ReportInfoVO getReportInfo() {
		return reportInfo;
	}

	public void setReportInfo(ReportInfoVO reportInfo) {
		this.reportInfo = reportInfo;
	}

	public String getTemplates_path() {
		return templates_path;
	}

	public void setTemplates_path(String templates_path) {
		this.templates_path = templates_path;
	}

	public UserVO getUserInSession() {
		return userInSession;
	}

	public void setUserInSession(UserVO userInSession) {
		this.userInSession = userInSession;
	}

	public String getReportinfo_update_hreportTypeId() {
		return reportinfo_update_hreportTypeId;
	}

	public void setReportinfo_update_hreportTypeId(
			String reportinfo_update_hreportTypeId) {
		this.reportinfo_update_hreportTypeId = reportinfo_update_hreportTypeId;
	}

	public ReportInfoCollectionVO getSearchedReportInfos() {
		return searchedReportInfos;
	}

	public void setSearchedReportInfos(
			ReportInfoCollectionVO searchedReportInfos) {
		this.searchedReportInfos = searchedReportInfos;
	}

	public String getReportInfoId() {
		return reportInfoId;
	}

	public void setReportInfoId(String reportInfoId) {
		this.reportInfoId = reportInfoId;
	}

	public String getReportInfoName() {
		return reportInfoName;
	}

	public void setReportInfoName(String reportInfoName) {
		this.reportInfoName = reportInfoName;
	}

	public byte[] getPdfBytes() {
		return pdfBytes;
	}

	public void setPdfBytes(byte[] pdfBytes) {
		this.pdfBytes = pdfBytes;
	}

	public HttpServletRequest getServletRequest() {
		return servletRequest;
	}

	public HttpServletResponse getServletResponse() {
		return servletResponse;
	}

	public ReportInfoCollectionVO getPatientReports() {
		return patientReports;
	}

	public void setPatientReports(ReportInfoCollectionVO patientReports) {
		this.patientReports = patientReports;
	}

	public String getAppointmentId() {
		System.out.println("Selected appointment : " + appointmentId);
		return appointmentId;
	}

	public void setAppointmentId(String appointmentId) {
		this.appointmentId = appointmentId;
	}

	public AppointmentVO getPatientAppointment() {
		return patientAppointment;
	}

	public void setPatientAppointment(AppointmentVO patientAppointment) {
		this.patientAppointment = patientAppointment;
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
	

	public String getReports_path() {
		return reports_path;
	}

	public void setReports_path(String reports_path) {
		this.reports_path = reports_path;
	}
	
	public String getReportStatus() {
		return reportStatus;
	}

	public void setReportStatus(String reportStatus) {
		this.reportStatus = reportStatus;
	}

	
	public void prepare() throws Exception {
		logger.debug("In PatientReports : prepare()");
		WebApplicationContext context = WebApplicationContextUtils
				.getRequiredWebApplicationContext(ServletActionContext
						.getServletContext());
		userService = (UserService) context.getBean("userService");
		auditInfoService = (AuditInfoService) context
				.getBean("auditInfoService");
		appointmentService = (AppointmentService) context
				.getBean("appointmentService");
		patientService = (PatientService) context.getBean("patientService");
		doctorService = (DoctorService) context.getBean("doctorService");
		labService = (LabService) context.getBean("labService");
		reportTypeService = (ReportTypeService) context
				.getBean("reportTypeService");
		reportInfoService = (ReportInfoService) context
				.getBean("reportInfoService");
		reportTypeKeyService = (ReportTypeKeyService) context
				.getBean("reportTypeKeyService");
		reportVariableService = (ReportVariableService) context
				.getBean("reportVariableService");
		templates_path = ResourceLocator.getInstance().getAppProps().getProperty("templates_path");
		logger.debug("Report Template Path: "+templates_path);
		reports_path = ResourceLocator.getInstance().getAppProps().getProperty("reports_path");
		ipAddress = servletRequest.getHeader("X-FORWARDED-FOR");
		if (ipAddress == null) {
			ipAddress = servletRequest.getRemoteAddr();
		}
		Object obj = servletRequest.getSession().getAttribute("user");
		if (obj != null) {
			userInSession = (UserVO) obj;
		}
	}

	public String displayPatientReports() {
		logger.debug("Inside PatientReports:displaypatientreports");
		System.out.println("Appintment Selected: "
				+ servletRequest.getParameter("appointmentId"));
		try {
			String appointmentId = servletRequest.getParameter("appointmentId");
			patientAppointment = appointmentService
					.getAppointmentById(appointmentId);
			ReportInfoVO rptVO = new ReportInfoVO();
			rptVO.setAppointment(patientAppointment);
			patientReports = reportInfoService.searchReportInfo(rptVO);
			DoctorVO doctor = patientAppointment.getDoctor();
			
			logger.debug("Doctor: " + doctor);
			
			UserVO doctorUser = userService.getUserById(doctor.getId());
			doctorName = doctorUser.getFirstName() + " "
					+ doctorUser.getMiddleName() + " "
					+ doctorUser.getLastName();
			logger.debug("Doctor Fullname: " + doctorName);
			
			LabVO lab = patientAppointment.getLab();
			labName = lab.getName();
			logger.debug("labName: " + labName);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return "error";
		}
		return "success";
	}

	public void populateReportInfoDetails() throws Exception {
		String id = StringUtils.trim(servletRequest.getParameter("selectedId"));
		logger.debug("Input id:" + id);
		List<Object> reportInfoDetailsList = new ArrayList<Object>();
		ReportInfoVO reportInfo = reportInfoService.getReportInfoById(id);
		ReportInfoVO selectedReportInfo = new ReportInfoVO();
		try {
			selectedReportInfo.setName(reportInfo.getName().trim());
			selectedReportInfo.setReportType(reportInfo.getReportType());
			selectedReportInfo.setRcn(reportInfo.getRcn());
			selectedReportInfo.setReportStatus(reportInfo.getReportStatus());
			selectedReportInfo.setCreatedDate(reportInfo.getCreatedDate());
			String createdBy = "";
			if (StringUtils.isNotEmpty(reportInfo.getCreatedBy())) {
				createdBy = reportInfo.getCreatedBy();
			}
			selectedReportInfo.setCreatedBy(createdBy);
			selectedReportInfo.setDeletedDate(reportInfo.getDeletedDate());
			String deletedBy = "";
			if (StringUtils.isNotEmpty(reportInfo.getDeletedBy())) {
				deletedBy = reportInfo.getDeletedBy();
			}
			selectedReportInfo.setDeletedBy(deletedBy);
			selectedReportInfo
					.setLastViewedDate(reportInfo.getLastViewedDate());
			String lastViewedBy = "";
			if (StringUtils.isNotEmpty(reportInfo.getLastViewedBy())) {
				lastViewedBy = reportInfo.getLastViewedBy();
			}
			selectedReportInfo.setLastViewedBy(lastViewedBy);
			String reportTypeName = reportInfo.getReportType().getName();
			logger.debug("Report Type: " + reportTypeName);
			ReportTypeCollectionVO allReportTypes = reportTypeService
					.getAllReportTypes();
			ArrayList<String> allReportTypeNames = new ArrayList<String>();
			ArrayList<String> allReportTypeIds = new ArrayList<String>();
			if (allReportTypes != null) {
				logger.debug("No. of report type searched = "
						+ allReportTypes.size());
				for (ReportTypeVO reportType : allReportTypes) {
					allReportTypeNames.add(reportType.getName());
					allReportTypeIds.add(reportType.getId());
				}
			}
			ReportVariableVO tempRV = new ReportVariableVO();
			ReportTypeKeyVO tempRTK = new ReportTypeKeyVO();
			tempRV.setReportInfo(selectedReportInfo);
			tempRV.setReportTypeKey(tempRTK);
			ReportVariableCollectionVO reportVariablesForReportInfo = reportVariableService
					.searchReportVariables(tempRV);
			logger.debug("reportVariablesForReportInfo "
					+ reportVariablesForReportInfo);
			String reportVariableForReportInfoPresent = "no";
			if (null != reportVariablesForReportInfo
					&& reportVariablesForReportInfo.size() > 0) {
				logger.debug("reportVariableForReportInfo size ="
						+ reportVariablesForReportInfo.size());
				reportVariableForReportInfoPresent = "yes";
			}
			logger.debug("reportVariableForReportInfoPresent ="
					+ reportVariableForReportInfoPresent);
			reportInfoDetailsList.add(allReportTypeNames);// obj[0]
			reportInfoDetailsList.add(allReportTypeIds);// obj[1]
			reportInfoDetailsList.add(selectedReportInfo);// obj[2]
			reportInfoDetailsList.add(reportTypeName);// obj[3]
			reportInfoDetailsList.add(reportVariableForReportInfoPresent);// obj[4]
			Gson gson = new Gson();
			String strReportInfoDetailsList = gson
					.toJson(reportInfoDetailsList);
			servletResponse.getWriter().write(
					strReportInfoDetailsList.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String displayViewReport() throws Exception {
		logger.debug("Inside ReportInfoAdmin: displayViewReport()");
		String id = StringUtils.trim(servletRequest.getParameter("selectedId"));
		logger.debug("Input id="+id);
		failureMessage = "";
		if(StringUtils.isEmpty(id)){
			throw new Exception("ReportInfo id blank can not proceed");
		}		
		
		ReportInfoVO tempReportInfo=null;
		ServletOutputStream outputStream = null;
		String rcn ="";
		InputStream inputStream = null;					
		byte[] template = null;
		try {
			tempReportInfo = reportInfoService.getReportInfoById(id);
			logger.debug("tempReportInfo ="+tempReportInfo.getAttributesAsString());
			rcn = reportInfoService.getReportInfoById(id).getRcn();
			
			if(StringUtils.isNotBlank(rcn)){
				String finalReportFilePath = "";	
				finalReportFilePath = reports_path + rcn+".pdf";
				logger.debug("finalReportFilePath ="+finalReportFilePath);
				try{
					reportInputStream = new FileInputStream(finalReportFilePath);
					pdfBytes = IOUtils.toByteArray(reportInputStream);
					if(pdfBytes!=null && pdfBytes.length>0){	
						logger.debug("Report already available");						
					}else{
						logger.debug("Could not find report, creating new one.");						
					}
					logger.debug("reportInputStream="+reportInputStream);
				}catch(Exception e){
					logger.debug("Error while getting report, "+e.getMessage());					
				}
			}
			if(pdfBytes!=null && pdfBytes.length>0){	
				//do nothing PDF is already available
			}else{
				//Check if report variables are available
				ReportVariableVO tempReportVariable = new ReportVariableVO();
				tempReportVariable.setReportInfo(tempReportInfo);			
				ReportVariableCollectionVO variables = reportVariableService.searchReportVariables(tempReportVariable);
				logger.debug("variables size ="+variables.size());
				if(variables==null || variables.size()==0){
					failureMessage = "Report can not be generated without report variables.";
					logger.error(failureMessage);
					return "error";
				}
				try{
					ReportTypeVO tempReportType = tempReportInfo.getReportType();
					logger.debug("tempReportType ="+tempReportType.getAttributesAsString());
					String fileName = tempReportType.getFileName();
					logger.debug("fileName ="+fileName);
					if(StringUtils.isBlank(fileName)){
						failureMessage = "No template found in the system for reporttype "+tempReportType.getName()+", ";
						throw new Exception(failureMessage);
					}else{
						String finalFilePath = templates_path+fileName;				
						logger.debug("finalFilePath ="+finalFilePath);
						inputStream = new FileInputStream(finalFilePath);
						template =  IOUtils.toByteArray(inputStream);
						if(template == null || template.length < 1){
							failureMessage = "Could not find template in the system for reporttype "+tempReportType.getName()+", ";
							throw new Exception(failureMessage);
						}
					}
				}catch(Exception e1){
					e1.printStackTrace();
					logger.error(e1.getMessage());
					failureMessage = failureMessage + getText("displayviewreportfailure");
					logger.error(failureMessage);
					return "error";
				}
				//Genrate report
				try{
					String createdByUserName = userInSession.getUserName();
					logger.debug("createdByUserName ="+createdByUserName);
				    pdfBytes = reportInfoService.runReport(id, createdByUserName, template);
				    logger.debug("Called BL to run the report");
				    if(pdfBytes!=null && pdfBytes.length>0){				    	
				    	FileOutputStream reportOutputStream = new FileOutputStream(new File(reports_path + rcn+".pdf"));
				    	IOUtils.write(pdfBytes, reportOutputStream);
				    	reportInputStream =  new FileInputStream(reports_path + rcn+".pdf");
				    	logger.debug("Stored "+rcn+".pdf");
				    	logger.debug("reportInputStream ="+reportInputStream);
				    }
				}catch(Exception e2){
					e2.printStackTrace();
					logger.error(e2.getMessage());
					failureMessage = getText("displayviewreportfailure");
					logger.error(failureMessage);
					return "error";
				}			
			}//end of else			
			if(pdfBytes!=null && pdfBytes.length>0){
				System.out.println("pdfBytes size =" +pdfBytes.length);
				//rcn = reportInfoService.getReportInfoById(id).getRcn();
				ByteArrayOutputStream baos = new ByteArrayOutputStream(pdfBytes.length);
				baos.write(pdfBytes, 0, pdfBytes.length);
				outputStream = servletResponse.getOutputStream() ;
		        baos.writeTo(outputStream);
		        servletResponse.setHeader("Content-Disposition", "attachment; filename=\""+rcn+".pdf\"");
		        servletResponse.setContentType("application/pdf");
			}else{
				failureMessage = getText("displayviewreportfailure");
				logger.error(failureMessage);
				return "error";
			}
			//updating reportinfo with last viewed by
			tempReportInfo.setLastViewedBy(userInSession.getUserName());
			tempReportInfo.setLastViewedDate(new Date());
			reportInfoService.updateReportInfo(tempReportInfo);
			//Adding audit info
			AuditInfoVO auditInfo = new AuditInfoVO();
			auditInfo.setEntity("REPORTINFO");
			auditInfo.setActionName("VIEW_REPORT");
			auditInfo.setActionOrigin(id);
			auditInfo.setMachineKey(ipAddress);
			if(StringUtils.isNotBlank(userInSession.getUserName())){
				auditInfo.setActor(userInSession.getUserName());
			}else{
				auditInfo.setActor("System");
			}
			auditInfo.setAuditNote("Report "+tempReportInfo.getName()+" with RCN="+tempReportInfo.getRcn()+" has been viewed by the user "+userInSession.getUserName());
			auditInfo.setAppName(UIConstants.APP_NAME);
			auditInfoService.addAuditInfo(auditInfo);
			logger.info("Added auditinfo for viewing Report "+tempReportInfo.getName()+" with RCN="+tempReportInfo.getRcn()+" by the use "+userInSession.getUserName());			
		} catch (Exception e) {
			logger.debug("In displayViewReport, Error while running report for reportInfoId = "+id);
			e.printStackTrace();
		}
		finally{
			if(null!=outputStream){
				outputStream.flush();
				outputStream.close();
			}
		}
		return "success";
	}

	
	public void setServletResponse(HttpServletResponse servletResponse) {
		this.servletResponse = servletResponse;

	}

	
	public void setServletRequest(HttpServletRequest servletRequest) {
		this.servletRequest = servletRequest;
	}

}
