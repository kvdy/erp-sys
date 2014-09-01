package com.wtr.ui.interceptor;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class LicenseValidationInterceptor implements Interceptor{
	
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(LicenseValidationInterceptor.class);

	public String intercept(ActionInvocation invocation) throws Exception {				
		boolean isValidLicense = false;
		String licenseValidationMsg = "";
		
		//Check license validity here, keeping it true for now
		isValidLicense = true;
		
		if(!isValidLicense){
			logger.error("License not valid : "+licenseValidationMsg);
			logger.error("Redirecting user to login page.");			
			return "backtologin";
		}
		
		return invocation.invoke();
	}
	
	public void init() {
		System.out.println("Initializing LicenseValidationInterceptor...");
		logger.info("Initializing LicenseValidationInterceptor...");
	}
	
	public void destroy() {		
	}

}
