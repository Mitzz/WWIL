package com.enercon.spring.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ApplicationContextProvider implements ApplicationContextAware{

	private static ApplicationContext context;
    
	private ApplicationContextProvider(){}
	
	private static class SingletonHelper{
		public final static ApplicationContextProvider INSTANCE = new ApplicationContextProvider();
	}
	
	public static ApplicationContextProvider getInstance(){
		return SingletonHelper.INSTANCE;
	}
	
    public ApplicationContext getApplicationContext() {
        return context;
    }
    
    public void setApplicationContext(ApplicationContext ac)
            throws BeansException {
        context = ac;
    }

}
