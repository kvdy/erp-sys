package com.wtr.ui.util;

import java.io.File;
import java.util.Date;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.wtr.bl.services.UserService;
import com.wtr.bl.vo.UserStatus;
import com.wtr.bl.vo.UserType;
import com.wtr.bl.vo.UserVO;

public class MyContextListener implements ServletContextListener{

	private static final Logger logger = Logger.getLogger(MyContextListener.class);
	private Properties appProps = new Properties();

	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
	}

	public void contextInitialized(ServletContextEvent sce) {
		ResourceLocator resLoc = ResourceLocator.getInstance();
		try{
			setupLogsFromContext(sce);
			logger.info(Thread.currentThread().getId()+"|Initializing application through contextInitialized...");

			setUpEnvVariables(sce);
			
			resLoc.setAppProps(appProps);

			checkFilePaths();
			
		}catch (Exception exception) {
			logger.fatal("error in populating ResourceLocator", exception);
		}

		try{
			WebApplicationContext context =
				WebApplicationContextUtils.getRequiredWebApplicationContext(sce.getServletContext());
			resLoc.setContext(context);
		}catch (Exception exception) {
			logger.fatal("error in bootstraping", exception);
		}
	}

	//Set Environment Variables
	private void setUpEnvVariables(ServletContextEvent sce) throws Exception{
		Context envCtx = (Context)new InitialContext().lookup("java:comp/env");
		logger.debug("Running environment =>" + envCtx.lookup("env"));
		appProps.setProperty("env", ObjectUtils.toString(envCtx.lookup("env")));
		appProps.setProperty("templates_path", ObjectUtils.toString(envCtx.lookup("templates_path")));
		appProps.setProperty("photos_path", ObjectUtils.toString(envCtx.lookup("photos_path")));
		appProps.setProperty("reports_path", ObjectUtils.toString(envCtx.lookup("reports_path")));
	}

	//Resetting log4j properties
	private void setupLogsFromContext(ServletContextEvent sce) throws Exception{
		String finalFilePath = "";
		String finalLogLevel = "";
		DailyRollingFileAppender loggingFileAppender = (DailyRollingFileAppender)Logger.getRootLogger().getAppender("loggingFileAppender");
		System.out.println("*****"+loggingFileAppender);
		String log4jFilePathFromContext = sce.getServletContext().getInitParameter("wtr.log4j.file.path");
		logger.info("Path in the log4j properties file="+loggingFileAppender.getFile());
		logger.info("Path in context = "+log4jFilePathFromContext);
		if(StringUtils.isNotBlank(log4jFilePathFromContext)){
			finalFilePath = log4jFilePathFromContext;
		}else{
			finalFilePath = loggingFileAppender.getFile();
		}
		loggingFileAppender.setFile(finalFilePath);
		logger.debug("finalFilePath="+finalFilePath);
		appProps.setProperty("log4j-logFilePath", finalFilePath);

		//Setting log level
		String log4jLogLevelFromContext = sce.getServletContext().getInitParameter("wtr.log4j.log.level");
		logger.info("log4jLogLevelFromContext="+log4jLogLevelFromContext);
		logger.info("Level in the log4j properties file="+Logger.getRootLogger().getLevel());
		if(StringUtils.isNotBlank(log4jLogLevelFromContext)&&(StringUtils.equalsIgnoreCase(log4jLogLevelFromContext, "DEBUG")
				|| StringUtils.equalsIgnoreCase(log4jLogLevelFromContext, "INFO")
				|| StringUtils.equalsIgnoreCase(log4jLogLevelFromContext, "WARN")
				|| StringUtils.equalsIgnoreCase(log4jLogLevelFromContext, "ERROR")
				|| StringUtils.equalsIgnoreCase(log4jLogLevelFromContext, "FATAL"))){
			finalLogLevel = log4jLogLevelFromContext;
		}else{
			finalLogLevel = Logger.getRootLogger().getLevel().toString();
		}
		System.out.println("*****"+Logger.getRootLogger());
		Logger.getRootLogger().setLevel(Level.toLevel(finalLogLevel));
		logger.info("finalLogLevel="+finalLogLevel);
		appProps.setProperty("log4j-logLevel", finalLogLevel);
		return;
	}
	
	//Checking filePaths
	private void checkFilePaths(){
		String templatesPath = appProps.getProperty("templates_path");
		File templatesDir = new File(templatesPath);
		System.out.println("templatesDir exists = "+templatesDir.exists());
		if(!templatesDir.exists()){
			new File(templatesPath).mkdir();
		}
		System.out.println("templatesDir writable = "+templatesDir.canWrite());		

		String photosPath = appProps.getProperty("photos_path");
		File photossDir = new File(photosPath);
		System.out.println("photossDir exists = "+photossDir.exists());
		if(!photossDir.exists()){
			new File(photosPath).mkdir();
		}
		System.out.println("photossDir writable = "+photossDir.canWrite());		

		String reportsPath = appProps.getProperty("reports_path");
		File reportsDir = new File(reportsPath);
		System.out.println("reportsDir exists = "+photossDir.exists());
		if(!photossDir.exists()){
			new File(photosPath).mkdir();
		}
		System.out.println("reportsDir writable = "+reportsDir.canWrite());		
		
	}
}
