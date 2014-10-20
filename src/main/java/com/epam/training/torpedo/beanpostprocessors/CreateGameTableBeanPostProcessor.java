package com.epam.training.torpedo.beanpostprocessors;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import com.epam.training.torpedo.domain.GameTable;

public class CreateGameTableBeanPostProcessor implements BeanPostProcessor {

	@Override
	public Object postProcessAfterInitialization(final Object bean, final String beanName)
			throws BeansException {
		return bean;
	}

	@Override
	public Object postProcessBeforeInitialization(final Object bean, final String beanName)
			throws BeansException {

		if (bean instanceof GameTable) {
			GameTable gameTable = (GameTable) bean;
			gameTable.positionShips();

		}
		return bean;
	}

}
