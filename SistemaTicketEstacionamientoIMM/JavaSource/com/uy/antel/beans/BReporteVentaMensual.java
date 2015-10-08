package com.uy.antel.beans;

public class BReporteVentaMensual {
	private int importeTotal;
	private String mes;
	private int cantTicket;
	
	
	public BReporteVentaMensual(){
		
	}
	
	public BReporteVentaMensual(int importeTotal, String mes, int cantTicket){
		this.importeTotal = importeTotal;
		this.mes = mes;
		this.cantTicket = cantTicket;
	}
	
	public int getImporteTotal() {
		return importeTotal;
	}


	public void setImporteTotal(int importeTotal) {
		this.importeTotal = importeTotal;
	}


	public String getMes() {
		return mes;
	}


	public void setMes(String mes) {
		this.mes = mes;
	}


	public int getCantTicket() {
		return cantTicket;
	}


	public void setCantTicket(int cantTicket) {
		this.cantTicket = cantTicket;
	}


}
