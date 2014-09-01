package com.wtr.ui.util;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.Result;
import com.wtr.ui.action.ImageAction;
import com.wtr.ui.action.ProfilePage;

public class CustomImageBytesResult implements Result {

	public void execute(ActionInvocation invocation) throws Exception {
		
		ImageAction action = (ImageAction) invocation.getAction();
		HttpServletResponse response = ServletActionContext.getResponse();
		
		response.setContentType(action.getCustomContentType());
		response.getOutputStream().write(action.getCustomImageInBytes());
		response.getOutputStream().flush();
	}

}
