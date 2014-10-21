package com.epam.training.torpedo.beanpostprocessors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import com.epam.training.torpedo.domain.Loggable;

public class AddLoggerBeanPostProcessor implements BeanPostProcessor {

	@Override
	public Object postProcessAfterInitialization(final Object bean, final String beanName)
			throws BeansException {

		if (bean instanceof Loggable) {

			Logger logger = LoggerFactory.getLogger(bean.getClass());
			Loggable loggableBean = (Loggable) bean;

			loggableBean.setLogger(logger);
		}
		return bean;
	}

	@Override
	public Object postProcessBeforeInitialization(final Object bean, final String beanName)
			throws BeansException {
		return bean;
	}

}
