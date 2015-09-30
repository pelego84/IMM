package com.uy.antel.controlador;

import java.util.Date;

import com.uy.antel.modelo.DataTicket;

public class ctrTicket {
	private static ctrTicket instance;
	
	private ctrTicket(){
		
	}
	
	public static ctrTicket getInstance(){
		if (instance==null){
			instance = new ctrTicket();
		}
		return instance;			
	}
	
	public DataTicket altaTicket(int idAuto, Date fechaIniE, int cantMinutos, Date fechaVenta){
		ICtrImporte Iimporte = ctrImporte.getInstance();
		int importe = Iimporte.calcularImporte(cantMinutos, fechaIniE);
		int nroTicket = -1;
		int error= 0;
		try{
			nroTicket = ctrDAO.altaTicket(idAuto, fechaIniE, cantMinutos, fechaVenta, importe);
		}catch(Exception ex){
			error = -1;//TODO asignar error generico
		}
		return new DataTicket(importe, error, "", nroTicket);
	}
	
	
}
