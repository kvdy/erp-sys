package com.wtr.ui.action;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.wtr.bl.services.AppointmentService;
import com.wtr.bl.services.AuditInfoService;
import com.wtr.bl.services.ContactService;
import com.wtr.bl.services.DoctorService;
import com.wtr.bl.services.LabService;
import com.wtr.bl.services.PatientService;
import com.wtr.bl.services.ReportInfoService;
import com.wtr.bl.services.UserService;
import com.wtr.bl.vo.PatientVO;
import com.wtr.bl.vo.UserVO;
import com.wtr.ui.util.AppConstants;
import com.wtr.ui.util.ResourceLocator;

public class ImageAction extends ActionSupport implements 
	ServletRequestAware, Preparable {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger( DoctorHomePage.class);
	private HttpServletRequest request;
	private HttpServletResponse response;

	private PatientVO patient = new PatientVO();
	private String path;
	private String imgPath;
	private String imgFileName;

	byte[] imageInBytes = null;
	private String id;
	
	public PatientService patientService;

	public void prepare() throws Exception {
		logger.debug("Inside PatientHomePage:prepare()");
		try{
//			patientService = (PatientService)ResourceLocator.getInstance().getContext().getBean("patientService");
//			logger.debug("In prepare patientService ="+patientService);
			//is client behind something?
		}catch(Exception e){
			e.printStackTrace();
		}
		logger.debug("Completing PatientHomePage:prepare()");
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

	public byte[] getCustomImageInBytes(){
		logger.debug("Patient image file name received: " + getImgFileName());
		ResourceLocator resLoc = ResourceLocator.getInstance();
		String photoPath = (resLoc.getAppProps()).getProperty("photos_path");
		setImgPath(photoPath);
		BufferedImage originalImage;
		
		try {
			File imgDir = new File(getImgPath());
			File defaultImage = new File(getImgPath(),AppConstants.DEFAULT_IMG_FILE);
			File[] matchingFiles = imgDir.listFiles(new FileFilter() {
				
				public boolean accept(File pathname) {
					return pathname.getName().contains(getImgFileName());
				}
			});
			logger.debug("Matching Files: "+matchingFiles.length);
			if(matchingFiles.length == 1) {
				originalImage = ImageIO.read(matchingFiles[0]);
			} else {
				originalImage = ImageIO.read(defaultImage);
			}
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(originalImage, "jpg", baos);
			baos.flush();
			imageInBytes = baos.toByteArray();
			baos.close();
		} catch (IOException e){
			logger.error(e.getMessage());
		}
		return imageInBytes;
	}
	
//	private File getImageFile(String imageId){
//		File file = new File(getImgPath(),imageId );
//		return file;
//	}

	public String getCustomContentType() {
		return "image/jpeg";
	}
	
	public String getCustomContentDisposition() {
		return "anyname.jpg";
	}
	

}
