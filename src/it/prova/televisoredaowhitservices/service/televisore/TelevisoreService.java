package it.prova.televisoredaowhitservices.service.televisore;

import java.util.Date;
import java.util.List;

import it.prova.televisoredaowhitservices.dao.televisore.TelevisoreDAO;
import it.prova.televisoredaowhitservices.dao.televisore.TelevisoreDAOImpl;
import it.prova.televisoredaowhitservices.model.Televisore;

public interface TelevisoreService {
	public void setTelevisoreDao(TelevisoreDAO televisoreDao);
	
	public List<Televisore> listAll() throws Exception;

	public Televisore findById(Long idInput) throws Exception;

	public int aggiorna(Televisore input) throws Exception;

	public int inserisciNuovo(Televisore input) throws Exception;

	public int rimuovi(Televisore input) throws Exception;

	public List<Televisore> findByExample(Televisore input) throws Exception;
	//##################
	public List<Televisore>tuttiITelevisoriProdottiInUnIntervalloDiDate(Date iniziale, Date finale) throws Exception;
	
	public Televisore televisorePiuGrande()throws Exception;
	
	public List<Televisore> listaMarcheTelevisoriProdottiNegliUltimiSeiMesi(Date data) throws Exception;
}