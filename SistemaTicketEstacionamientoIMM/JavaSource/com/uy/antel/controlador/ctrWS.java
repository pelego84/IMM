package com.uy.antel.controlador;

import java.util.Date;

import com.uy.antel.modelo.DataAnulacion;
import com.uy.antel.modelo.DataTicket;

public class ctrWS {
	
	private static ctrWS instance;
	
	private ctrWS(){
		
	}
	
	public static ctrWS getInstance(){
		if (instance==null){
			instance = new ctrWS();
		}
		return instance;			
	}
	
	public DataTicket altaTicket(String matricula, String fechaIniE, int cantMinutos, String fechaVenta, String agencia){
		ctrAgencia ctr = ctrAgencia.getInstance();	
		return ctr.altaTicket(matricula, fechaIniE, cantMinutos, fechaVenta, agencia);
	}
		
	public DataAnulacion anulacionTicket(int nroTicket, String agencia){
		ctrAgencia ctr = ctrAgencia.getInstance();	
		return ctr.anulacionTicket(nroTicket, agencia);
	}
	
	
}
