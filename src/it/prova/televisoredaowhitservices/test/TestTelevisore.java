package it.prova.televisoredaowhitservices.test;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.EmptyStackException;
import java.util.List;

import it.prova.televisoredaowhitservices.model.Televisore;
import it.prova.televisoredaowhitservices.service.MyServiceFactory;
import it.prova.televisoredaowhitservices.service.televisore.TelevisoreService;

public class TestTelevisore {

	public static void main(String[] args) {
		TelevisoreService televisoreService = MyServiceFactory.getTelevisoreServiceImpl();
		try {

			testInserimentoNuovoTelevisore(televisoreService);
			System.out.println();
			System.out.println("In tabella ci sono " + televisoreService.listAll().size() + " elementi.");
			System.out.println();
			testRimozioneTelevisore(televisoreService);
			System.out.println();
			System.out.println("In tabella ci sono " + televisoreService.listAll().size() + " elementi.");
			System.out.println();
			testFindByExample(televisoreService);
			System.out.println();
			deleteAll(televisoreService);
			testUpdateTelevisore(televisoreService);
			System.out.println();
			testFindById(televisoreService);
			System.out.println();
			deleteAll(televisoreService);
			testTuttiITelevisoriProdottiInUnIntervalloDiDate(televisoreService);
			deleteAll(televisoreService);
			System.out.println();
			testTelevisorePiuGrande(televisoreService);
			deleteAll(televisoreService);
			System.out.println();
			listaMarcheTelevisoriProdottiNegliUltimiSeiMesi(televisoreService);
			deleteAll(televisoreService);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void deleteAll(TelevisoreService televisoreService) throws Exception {
		List<Televisore> listaElementi = televisoreService.listAll();
		for (Televisore elementi : listaElementi) {
			televisoreService.rimuovi(elementi);
		}
	}

	public static void testInserimentoNuovoTelevisore(TelevisoreService televisoreService) throws Exception {
		System.out.println(".......testInserimentoNuovoTelevisore inizio.............");
		Date dataProduzione = new SimpleDateFormat("dd-MM-yyyy").parse("03-10-2022");
		Televisore newTvInstance = new Televisore("Sony", "gtr450", 55, dataProduzione);
		if (televisoreService.inserisciNuovo(newTvInstance) != 1)
			throw new RuntimeException("testInserimentoNuovoTelevisore FAILED ");
		
		System.out.println(".......testInserimentoNuovoTelevisore PASSED.............");
	}

	private static void testRimozioneTelevisore(TelevisoreService televisoreService) throws Exception {
		System.out.println(".......testRimozioneTelevisore inizio.............");

		List<Televisore> interoContenutoTabella = televisoreService.listAll();
		if (interoContenutoTabella.isEmpty() || interoContenutoTabella.get(0) == null)
			throw new Exception("Non ho nulla da rimuovere");

		Long idDelPrimo = interoContenutoTabella.get(0).getId();

		// ricarico per sicurezza con l'id individuato
		Televisore toBeRemoved = televisoreService.findById(idDelPrimo);
		if (televisoreService.rimuovi(toBeRemoved) != 1)
			throw new RuntimeException("testRimozioneTelevisore FAILED ");

		System.out.println(".......testRimozioneTelevisore PASSED.............");
	}

	private static void testFindByExample(TelevisoreService televisoreService) throws Exception {
		System.out.println(".......testFindByExample inizio.............");

		Date dataProduzione = new SimpleDateFormat("dd-MM-yyyy").parse("03-10-2022");
		Televisore newTvInstance = new Televisore("Logitech", "gtr450", 55, dataProduzione);
		if (televisoreService.inserisciNuovo(newTvInstance) != 1)
			throw new RuntimeException("testInserimentoNuovoTelevisore FAILED ");

		List<Televisore> listaElementi = televisoreService.findByExample(new Televisore("Logitech"));
		if (listaElementi.size() != 1)
			throw new Exception("testFindByExample Fallito");

		for (Televisore elementi : listaElementi) {
			System.out.println(elementi);
		}

		System.out.println(".......testFindByExample PASSED.............");
	}

	private static void testUpdateTelevisore(TelevisoreService televisoreService) throws Exception {
		System.out.println(".......testUpdateTelevisore inizio.............");

		// inserisco i dati che poi modifico
		Date dataProduzione = new SimpleDateFormat("dd-MM-yyyy").parse("03-10-2012");
		Televisore prova = new Televisore("asus", "p20", 55, dataProduzione);
		if (televisoreService.inserisciNuovo(prova) != 1)
			throw new RuntimeException("testUpdateTelevisore: inserimento preliminare FAILED ");

		// recupero col findbyexample e mi aspetto di trovarla
		List<Televisore> risultatifindByExample = televisoreService.findByExample(new Televisore("asus", "p20"));
		if (risultatifindByExample.size() != 1)
			throw new RuntimeException("testUpdateTelevisore: testFindByExample FAILED ");

		// mi metto da parte l'id su cui lavorare per il test
		Long idtv = risultatifindByExample.get(0).getId();

		// ricarico per sicurezza con l'id individuato e gli modifico un campo
		String nuovaMarca = "Dgree";
		Televisore toBeUpdated = televisoreService.findById(idtv);
		toBeUpdated.setMarca(nuovaMarca);
		int risultato = televisoreService.aggiorna(toBeUpdated);
		if (risultato != 1) {
			throw new RuntimeException("testUpdateUser FAILED ");
		} else {
			System.out.println("Elementi cambiati:" + risultato);
		}

		System.out.println(".......testUpdateTelevisore PASSED.............");
	}

	private static void testFindById(TelevisoreService televisoreService) throws Exception {
		System.out.println(".......testFindById inizio.............");

		List<Televisore> interoContenutoTabella = televisoreService.listAll();
		if (interoContenutoTabella.isEmpty() || interoContenutoTabella.get(0) == null)
			throw new Exception("Non ho nulla da rimuovere");

		Long idDelPrimo = interoContenutoTabella.get(0).getId();

		Televisore prova = televisoreService.findById(idDelPrimo);
		System.out.println(prova);

		System.out.println(".......testFindById PASSED.............");
	}

	private static void testTuttiITelevisoriProdottiInUnIntervalloDiDate(TelevisoreService televisoreService)
			throws Exception {
		System.out.println(".......testTuttiITelevisoriProdottiInUnIntervalloDiDate inizio.............");
		// inseriamo prima degli elementi
		Date dataProduzione = new SimpleDateFormat("dd-MM-yyyy").parse("03-10-2012");
		Televisore primaTv = new Televisore("huawei", "536", 77, dataProduzione);
		if (televisoreService.inserisciNuovo(primaTv) != 1)
			throw new RuntimeException("testUpdateTelevisore: inserimento preliminare FAILED ");

		Date dataProduzioneSecondaTv = new SimpleDateFormat("dd-MM-yyyy").parse("05-10-2017");
		Televisore secondatv = new Televisore("asus", "p20", 55, dataProduzioneSecondaTv);
		if (televisoreService.inserisciNuovo(secondatv) != 1)
			throw new RuntimeException("testUpdateTelevisore: inserimento preliminare FAILED ");

		// creiamo el date da controllare
		Date dataPrima = new SimpleDateFormat("dd-MM-yyyy").parse("03-10-2011");
		Date dataDopo = new SimpleDateFormat("dd-MM-yyyy").parse("03-10-2018");

		// eseguiamo
		List<Televisore> listaElementi = televisoreService.tuttiITelevisoriProdottiInUnIntervalloDiDate(dataPrima,
				dataDopo);
		for (Televisore elementi : listaElementi)
			System.out.println(elementi);
		System.out.println(".......testTuttiITelevisoriProdottiInUnIntervalloDiDate PASSED.............");
	}

	private static void testTelevisorePiuGrande(TelevisoreService televisoreService) throws Exception {
		System.out.println(".......testTelevisorePiuGrande inizio.............");

		Televisore primaTv = new Televisore("polg", "tfgr", 97, new Date());
		if (televisoreService.inserisciNuovo(primaTv) != 1)
			throw new RuntimeException("testUpdateTelevisore: inserimento preliminare FAILED ");

		Televisore secondatv = new Televisore("hyperx", "652", 75, new Date());
		if (televisoreService.inserisciNuovo(secondatv) != 1)
			throw new RuntimeException("testUpdateTelevisore: inserimento preliminare FAILED ");

		Televisore risultato = televisoreService.televisorePiuGrande();

		System.out.println(risultato);

		System.out.println(".......testTelevisorePiuGrande PASSED.............");
	}

	private static void listaMarcheTelevisoriProdottiNegliUltimiSeiMesi(TelevisoreService televisoreService)throws Exception {
		System.out.println(".......listaMarcheTelevisoriProdottiNegliUltimiSeiMesi inizio.............");

		Date dataprima = new SimpleDateFormat("dd-MM-yyyy").parse("03-10-2020");
		Televisore primaTv = new Televisore("philips", "t45", 75, dataprima);
		if (televisoreService.inserisciNuovo(primaTv) != 1)
			throw new RuntimeException("testUpdateTelevisore: inserimento preliminare FAILED ");

		Date dataSeconda = new SimpleDateFormat("dd-MM-yyyy").parse("05-10-2022");
		Televisore secondatv = new Televisore("asus", "g700", 50, dataSeconda);
		if (televisoreService.inserisciNuovo(secondatv) != 1)
			throw new RuntimeException("testUpdateTelevisore: inserimento preliminare FAILED ");

		Date data = new SimpleDateFormat("dd-MM-yyyy").parse("01-04-2022");
		List<Televisore> listaElementi = televisoreService.listaMarcheTelevisoriProdottiNegliUltimiSeiMesi(data);
		for (Televisore elementi : listaElementi)
			System.out.println(elementi);

		System.out.println(".......listaMarcheTelevisoriProdottiNegliUltimiSeiMesi PASSED.............");
	}
}