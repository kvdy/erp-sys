package com.wtr.ui.action;

import java.sql.Connection;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.opensymphony.xwork2.Preparable;
import com.wtr.bl.services.AuditInfoService;
import com.wtr.ui.util.ResourceLocator;

public class EnvConfig extends BaseAction implements Preparable{
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger( EnvConfig.class);
	public AuditInfoService auditInfoService;
	private Properties appProps = new Properties();
	private String message;

	public Properties getAppProps() {
		return appProps;
	}
	public void setAppProps(Properties appProps) {
		this.appProps = appProps;
	}
	
	public void prepare() throws Exception {		
		logger.debug("Inside EnvConfig:prepare()");
		WebApplicationContext context = 
			WebApplicationContextUtils.getRequiredWebApplicationContext(ServletActionContext.getServletContext());		
		auditInfoService = (AuditInfoService)context.getBean("auditInfoService");
		appProps = ResourceLocator.getInstance().getAppProps();
	}	
	
	public String displayEnvConfig(){
		logger.debug("EnvConfig: display()");
		String key = "schooldb";
		Connection conn = null;
		try{
			String URL = auditInfoService.testConnection();
			if(StringUtils.isNotEmpty(URL)){
				appProps.put("schooldb", "Successfully connected to "+URL);			
			}
		}catch(Exception e){
			logger.error(e.getMessage());
			message = "Failed -->"+ " Error while making database connection, please check logs.";
			appProps.put(key, message);
		}finally{
			if(conn!=null){
				try{
					conn.close();
				}catch(Exception e){
					logger.error("Error while closing connection!");
				}
				conn = null;
			}
		}
		loadSystemProps();
		//appProps.list(System.out);
		return "success";
	}
	
	private void loadSystemProps(){	
		appProps.put("java.version",System.getProperty("java.version"));
		appProps.put("java.class.path",System.getProperty("java.class.path"));
		appProps.put("java.home",System.getProperty("java.home"));
		appProps.put("file.separator",System.getProperty("file.separator"));		
		appProps.put("path.separator",System.getProperty("path.separator"));
		appProps.put("os.arch",System.getProperty("os.arch"));
		appProps.put("os.name",System.getProperty("os.name"));
		appProps.put("os.version",System.getProperty("os.version"));
	}
	
	
	
}

	

