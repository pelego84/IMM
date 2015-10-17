package com.uy.antel.beans;

public class BReportePorFecha {
	private int importeTotal;
	private int cantTicket;
	
	
	public BReportePorFecha(){
		
	}
	
	public BReportePorFecha(int importeTotal, int cantTicket){
		this.importeTotal = importeTotal;
		this.cantTicket = cantTicket;
	}
	
	public int getImporteTotal() {
		return importeTotal;
	}


	public void setImporteTotal(int importeTotal) {
		this.importeTotal = importeTotal;
	}


	public int getCantTicket() {
		return cantTicket;
	}


	public void setCantTicket(int cantTicket) {
		this.cantTicket = cantTicket;
	}


}
