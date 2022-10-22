package it.prova.televisoredaowhitservices.dao.televisore;

import java.util.Date;
import java.util.List;

import it.prova.televisoredaowhitservices.dao.IBaseDAO;
import it.prova.televisoredaowhitservices.model.Televisore;

public interface TelevisoreDAO extends IBaseDAO<Televisore>{
	
	public List<Televisore>allTVsProductsInAIntervalOfdate(Date iniziale, Date finale) throws Exception;
	public Televisore biggerTv()throws Exception;
	public List<Televisore>listOfBrandsTelevisionsProductsInTheLastSixMonths() throws Exception;

}
