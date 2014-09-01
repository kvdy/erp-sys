package com.wtr.ui.util;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.Result;
import com.wtr.ui.action.ImageAction;
import com.wtr.ui.action.ProfilePage;
import com.wtr.ui.action.ReportAction;

public class CustomReportBytesResult implements Result {

	
	public void execute(ActionInvocation invocation) throws Exception {
		
		ReportAction action = (ReportAction) invocation.getAction();
		HttpServletResponse response = ServletActionContext.getResponse();
		
		response.setContentType(action.getCustomContentType());
		response.getOutputStream().write(action.getCustomReportInBytes());
		response.getOutputStream().flush();
	}

}
