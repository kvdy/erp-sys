package com.wtr.ui.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.apache.struts2.StrutsStatics;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.wtr.bl.vo.UserVO;

public class LoggingInterceptor implements Interceptor{
	
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(LoggingInterceptor.class);

	public String intercept(ActionInvocation invocation) throws Exception {				
		String userName = "";
		String ipAddress = "";
		String actionName = invocation.getInvocationContext().getName();

		//Obtaining userName from User object stored in session
		Map<String, Object> sessionAttributes = invocation.getInvocationContext().getSession();		
		UserVO user = (UserVO) sessionAttributes.get("user");
		if(user != null){
			userName = user.getUserName();			
 		}

		//Obtaining ipAddress from the request
		ActionContext context = invocation.getInvocationContext();
	    HttpServletRequest request = (HttpServletRequest) context.get(StrutsStatics.HTTP_REQUEST);
	    if(request!=null){
	    	ipAddress = request.getHeader("X-FORWARDED-FOR");
			if (ipAddress == null) {
				   ipAddress = request.getRemoteAddr();
			}	
	    }				
	    
	    //Adding userName & ipAddress in log4j MDC
		MDC.put("userName", userName);
		MDC.put("ipAddress", ipAddress);
	    
		//Logging execution time for each action
		long startTime = System.currentTimeMillis();
		String result = invocation.invoke();
		long endTime = System.currentTimeMillis();
		logger.info("Time for executing action, "+actionName+" = "+(endTime - startTime) +" ms");
		
		return result;		
	}
	
	public void init() {
		System.out.println("Initializing LoggingInterceptor...");
		logger.info("Initializing LoggingInterceptor...");
	}
	
	public void destroy() {		
	}

}
