package it.prova.televisoredaowhitservices.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Televisore {
	private Long id;
	private String marca;
	private String modello;
	private Integer pollici;
	private Date dataproduzione;
	
	public Televisore() {
		super();
	}
	
	
	public Televisore(String marca) {
		super();
		this.marca = marca;
	}


	public Televisore(String marca, String modello) {
		super();
		this.marca = marca;
		this.modello = modello;
	}
	

	public Televisore(String marca, String modello, Integer pollici) {
		super();
		this.marca = marca;
		this.modello = modello;
		this.pollici = pollici;
	}

	public Televisore(Long id, String marca, String modello, Integer pollici, Date dataproduzione) {
		super();
		this.id = id;
		this.marca = marca;
		this.modello = modello;
		this.pollici = pollici;
		this.dataproduzione = dataproduzione;
	}
	public Televisore(String marca, String modello, Integer pollici, Date dataproduzione) {
		super();
		this.marca = marca;
		this.modello = modello;
		this.pollici = pollici;
		this.dataproduzione = dataproduzione;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public String getModello() {
		return modello;
	}
	public void setModello(String modello) {
		this.modello = modello;
	}
	public Integer getPollici() {
		return pollici;
	}
	public void setPollici(Integer pollici) {
		this.pollici = pollici;
	}
	public Date getDataproduzione() {
		return dataproduzione;
	}
	public void setDataproduzione(Date dataproduzione) {
		this.dataproduzione = dataproduzione;
	}
	@Override
	public String toString() {
		String dateProduzioneString = dataproduzione!=null ? new SimpleDateFormat("dd/MM/yyyy").format(dataproduzione):" N.D.";
		return "Televisore [id=" + id + ", marca=" + marca + ", modello=" + modello + ", pollici=" + pollici
				+ ", dataproduzione=" + dateProduzioneString + "]";
	}
}
