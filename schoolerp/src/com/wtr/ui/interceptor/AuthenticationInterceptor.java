package com.wtr.ui.interceptor;

import java.util.Map;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.wtr.bl.vo.UserVO;

public class AuthenticationInterceptor implements Interceptor{
	
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(AuthenticationInterceptor.class);

	public String intercept(ActionInvocation invocation) throws Exception {				
		Map<String, Object> sessionAttributes = invocation.getInvocationContext().getSession();		
		UserVO user = (UserVO) sessionAttributes.get("user");	
		//System.out.println("user ="+user);
		
		/* Commented for future implementation
		if(user == null){	
			logger.error("Session Expired/Invalid, redirecting user to login page.");			
			return "backtologin";
		}
		*/
		return invocation.invoke();
	}
	
	public void init() {
		System.out.println("Initializing AuthenticationInterceptor...");
		logger.info("Initializing AuthenticationInterceptor...");
	}
	
	public void destroy() {		
	}

}
