package com.uy.antel.controlador;

import java.util.Date;

public class ctrImporte implements ICtrImporte {

	private static ctrImporte instance;
	
	private ctrImporte(){
		
	}
	
	public static ctrImporte getInstance(){
		if (instance==null){
			instance = new ctrImporte();
		}
		return instance;			
	}
	
	@Override
	public int calcularImporte(int cantidadMinutos, Date fechaIniE) {
		//Definir algun calculo inicial del importe, 16$ cada media hora
		return (int)Math.ceil((cantidadMinutos/30))*16;
	}

}
