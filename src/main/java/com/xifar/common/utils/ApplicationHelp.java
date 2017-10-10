package com.xifar.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ApplicationHelp implements ApplicationContextAware {

	private static ApplicationContext context;

	public static Object getBean(String beanId) {
		return context.getBean(beanId);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		context = applicationContext;
	}
}
