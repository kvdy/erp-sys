package com.wtr.ui.action;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.wtr.bl.services.PatientService;
import com.wtr.bl.services.ReportInfoService;
import com.wtr.bl.vo.PatientVO;
import com.wtr.bl.vo.ReportInfoVO;
import com.wtr.ui.util.ResourceLocator;


public class ReportAction extends ActionSupport implements 
	ServletRequestAware, Preparable {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger( DoctorHomePage.class);
	private HttpServletRequest request;
	private HttpServletResponse response;

	private PatientVO patient = new PatientVO();
	private ReportInfoVO reportInfo = new ReportInfoVO();
	private String path;
	private String reportPath;
	private String reportFileName;
	
	private InputStream reportInputStream = null;

	byte[] reportInBytes = null;
	private String id;
	
	public PatientService patientService;
	public ReportInfoService reportInfoService;

	
	public void prepare() throws Exception {
		logger.debug("Inside ReportAction: prepare()");
		try{
			patientService = (PatientService)ResourceLocator.getInstance().getContext().getBean("patientService");
			reportInfoService = (ReportInfoService)ResourceLocator.getInstance().getContext().getBean("reportInfoService");
			logger.debug("In prepare reportInfoService ="+reportInfoService);
			//is client behind something?
		}catch(Exception e){
			e.printStackTrace();
		}
		logger.debug("Completing ReportAction: prepare()");
	}

	public String execute(){
		return SUCCESS;
	}
	
	
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public String getId(){
		return id;
	}
	
	public void setId(String id){
		this.id = id;
	}
	
	
	public PatientVO getPatient() {
		return patient;
	}

	public void setPatient(PatientVO patient) {
		this.patient = patient;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	

	public ReportInfoVO getReportInfo() {
		return reportInfo;
	}

	public void setReportInfo(ReportInfoVO reportInfo) {
		this.reportInfo = reportInfo;
	}

	public String getReportPath() {
		return reportPath;
	}

	public void setReportPath(String reportPath) {
		this.reportPath = reportPath;
	}

	public String getReportFileName() {
		return reportFileName;
	}

	public void setReportFileName(String reportFileName) {
		this.reportFileName = reportFileName;
	}

	public byte[] getReportInBytes() {
		return reportInBytes;
	}

	public void setReportInBytes(byte[] reportInBytes) {
		this.reportInBytes = reportInBytes;
	}

	public byte[] getCustomReportInBytes(){
		logger.debug("Patient Id received: " + getId());
		try {
			if(null != getId()) {
				reportInfo = reportInfoService.getReportInfoById(id);
				
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ResourceLocator resLoc = ResourceLocator.getInstance();
		String reportsPath = (resLoc.getAppProps()).getProperty("reports_path");
		setReportPath(reportsPath);
		
		
		try {
			reportFileName = reportsPath+reportInfo.getRcn()+".pdf";
			reportInputStream = new FileInputStream(reportFileName);
			reportInBytes = IOUtils.toByteArray(reportInputStream);

		} catch (IOException e){
			logger.error(e.getMessage());
		}
		return reportInBytes;
	}
	
	public String getCustomContentType() {
		return "application/pdf";
	}
	
	public String getCustomContentDisposition() {
		return "anyname.pdf";
	}

	public InputStream getReportInputStream() {
		return reportInputStream;
	}

	public void setReportInputStream(InputStream reportInputStream) {
		this.reportInputStream = reportInputStream;
	}
	

}
