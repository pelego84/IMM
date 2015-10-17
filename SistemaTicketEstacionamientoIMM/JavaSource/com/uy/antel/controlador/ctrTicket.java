package com.uy.antel.controlador;

import java.util.Date;

import com.uy.antel.modelo.DataAnulacion;
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
	
	public DataTicket altaTicket(int idAuto, Date fechaIniE, int cantMinutos, Date fechaVenta, String agencia){
		ICtrImporte Iimporte = ctrImporte.getInstance();
		int importe = Iimporte.calcularImporte(cantMinutos, fechaIniE);
		int nroTicket = -1;
		int error= 0;
		try{
			nroTicket = ctrDAO.altaTicket(idAuto, fechaIniE, cantMinutos, fechaVenta, importe, agencia);
		}catch(Exception ex){
			error = 201;
		}
		return new DataTicket(importe, error, "", nroTicket);
	}
	
	public DataAnulacion anularTicket(int nroTicket){		
		int codigoAnulacion = -1;
		int error= 0;
		try{
			codigoAnulacion = ctrDAO.anularTicket(nroTicket);
		}catch(Exception ex){
			error = 201;
		}
		return new DataAnulacion(error, "", codigoAnulacion);
	}
	
	
	public boolean estaAnuladoTicket(int nroTicket){
		return ctrDAO.estaAnuladoTicket(nroTicket);
	}
	
	public boolean estaUtilizadoTicket(int nroTicket){
		Date fechaEstacionamiento = ctrDAO.getFechaIniEstacionamientoTicket(nroTicket);
		return fechaEstacionamiento.before(new Date());		
	}
	
	
}
