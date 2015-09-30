package com.uy.antel.modelo;

public class DataTicket {
	int importeTotal;
	int error;
	int nroTicket;
	String mensaje;
	
	public DataTicket(){
		
	}
	
	public DataTicket(int importeTotal, int error, String mensaje, int nroTicket){
		this.importeTotal=importeTotal;
		this.error= error;
		this.mensaje=mensaje;
		this.nroTicket=nroTicket;
	}
	
	public int getNroTicket() {
		return nroTicket;
	}

	public void setNroTicket(int nroTicket) {
		this.nroTicket = nroTicket;
	}
	
	public int getImporteTotal() {
		return importeTotal;
	}
	public void setImporteTotal(int importeTotal) {
		this.importeTotal = importeTotal;
	}
	public int getError() {
		return error;
	}
	public void setError(int error) {
		this.error = error;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	

}
