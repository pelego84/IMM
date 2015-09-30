package com.uy.antel.controlador;

import java.util.Date;

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
	
	public DataTicket altaTicket(String matricula, Date fechaIniE, int cantMinutos, Date fechaVenta, String agencia){
		ctrAgencia ctr = ctrAgencia.getInstance();
		return ctr.altaTicket(matricula, fechaIniE, cantMinutos, fechaVenta, agencia);
	}
		
	
	
}
