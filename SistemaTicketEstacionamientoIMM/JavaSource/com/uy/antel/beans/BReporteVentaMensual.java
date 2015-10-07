package com.uy.antel.beans;

public class BReporteVentaMensual {
	private int importeTotal;
	private int mes;
	private int cantTicket;
	
	
	public BReporteVentaMensual(){
		
	}
	
	public BReporteVentaMensual(int importeTotal, int mes, int cantTicket){
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


	public int getMes() {
		return mes;
	}


	public void setMes(int mes) {
		this.mes = mes;
	}


	public int getCantTicket() {
		return cantTicket;
	}


	public void setCantTicket(int cantTicket) {
		this.cantTicket = cantTicket;
	}


}
