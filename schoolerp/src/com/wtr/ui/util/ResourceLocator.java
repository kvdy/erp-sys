package com.wtr.ui.util;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;

public class ResourceLocator {
	private static ResourceLocator me;
	private Properties appProps = new Properties();
	private static final Logger logger = Logger.getLogger(ResourceLocator.class);
	WebApplicationContext context = null;
	
	// Use of singleton to load properties files
	private ResourceLocator() {
		try {
			logger.info("Singleton instance of ResourceLocator");	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static ResourceLocator getInstance() {
		synchronized (ResourceLocator.class) {
			if (me == null) {
				me = new ResourceLocator();
			}
			return me;
		}
	}

	public Properties getAppProps() {
		return appProps;
	}

	public void setAppProps(Properties appProps) {
		this.appProps = appProps;
	}

	public WebApplicationContext getContext() {
		return context;
	}

	public void setContext(WebApplicationContext context) {
		this.context = context;
	}
	
	
}