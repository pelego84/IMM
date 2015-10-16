package com.uy.antel.modelo;

public class DataAnulacion {
	int error;
	int nroAnulacion;
	
	String mensaje;
	
	public DataAnulacion(){
		
	}
	
	public DataAnulacion(int error, String mensaje, int nroAnulacion){		
		this.error= error;
		this.mensaje=mensaje;
		this.nroAnulacion=nroAnulacion;
	}
	
	public int getNroAnulacion() {
		return nroAnulacion;
	}

	public void setNroAnulacion(int nroAnulacion) {
		this.nroAnulacion = nroAnulacion;
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
