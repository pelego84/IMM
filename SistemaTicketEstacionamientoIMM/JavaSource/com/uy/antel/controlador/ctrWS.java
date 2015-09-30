package com.uy.antel.controlador;

import java.util.Date;

import com.uy.antel.modelo.DataTicket;

public class ctrWS {
	
	public ctrWS(){
		
	}
	
	public DataTicket altaTicket(String matricula, Date fechaIniE, int cantMinutos, Date fechaVenta, String agencia){
		ctrAgencia ctr = ctrAgencia.getInstance();
		return ctr.altaTicket(matricula, fechaIniE, cantMinutos, fechaVenta, agencia);
	}
		
	
	
}
