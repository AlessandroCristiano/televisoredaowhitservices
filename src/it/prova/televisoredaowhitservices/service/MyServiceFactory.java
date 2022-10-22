package it.prova.televisoredaowhitservices.service;

import it.prova.televisoredaowhitservices.dao.televisore.TelevisoreDAOImpl;
import it.prova.televisoredaowhitservices.service.televisore.TelevisoreService;
import it.prova.televisoredaowhitservices.service.televisore.TelevisoreServiceImpl;

public class MyServiceFactory {
	
	public static TelevisoreService getTelevisoreServiceImpl() {
		TelevisoreService televisoreService= new TelevisoreServiceImpl();
		televisoreService.setTelevisoreDao(new TelevisoreDAOImpl());
		return televisoreService;
	}

}
