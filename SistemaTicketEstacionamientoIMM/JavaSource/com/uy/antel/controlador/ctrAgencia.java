package com.uy.antel.controlador;

import java.util.Date;

import com.uy.antel.modelo.DataAnulacion;
import com.uy.antel.modelo.DataTicket;
import com.uy.antel.util.util;

public class ctrAgencia {

	private static ctrAgencia instance;
	
	private ctrAgencia(){
		
	}
	
	public static ctrAgencia getInstance(){
		if (instance==null){
			instance = new ctrAgencia();
		}
		return instance;			
	}
	
	public boolean validarAgencia(String agencia){
		return ctrDAO.validarAgencia(agencia);
	}
	
	public boolean existeTicketAgencia(int nroTicket,String agencia){
		return ctrDAO.existeTicketAgencia(nroTicket, agencia);
	}
	
	public DataTicket altaTicket(String matricula, String fechaIniE, int cantMinutos, String fechaVenta, String agencia){
		int error = 0;
		String mensaje = "";
		DataTicket result = new DataTicket();
		
		try{
			ICtrValidacion IValidador = ctrValidacion.getInstance();
			error = IValidador.validarEntrada(matricula, fechaIniE, cantMinutos, fechaVenta, agencia);
			
			switch(error){
				case 104:{
					mensaje = "La matricula consta de tres letras y cuatro números (Ejemplo: ABC 1234)";
					break;
				}case 101:{
					mensaje = "La fecha de la solicitud de estacionamiento debe ser en el futuro.";
					break;
				}case 103:{
					mensaje = "La cantidad de minutos debe ser un múltiplo de 30.";
					break;
				}case 105:{
					mensaje = "Las fechas deben respetar el formato yyyy-MM-dd_HH:mm.";
					break;
				}default: {
						if (validarAgencia(agencia)){
							int idAuto = ctrDAO.altaAuto(matricula);				
							ctrTicket ctr = ctrTicket.getInstance();				
							result = ctr.altaTicket(idAuto, util.stringToDate(fechaIniE), cantMinutos, util.stringToDate(fechaVenta), agencia);
						}else{
							error = 100;
							mensaje = "La agencia no es correcta";
						}
					}
			}
		
		}catch(Exception ex){
			error = 201;
			mensaje = "Error de Sistema";
		}
		
		if (error!=0)
			result = new DataTicket(-1, error, mensaje,-1);	
		
		return result;
	}
	
	public DataAnulacion anulacionTicket(int nroTicket, String agencia){
		int error = 0;
		String mensaje = "";
		DataAnulacion result = new DataAnulacion();		
		try{			
			if (validarAgencia(agencia)){
				if (existeTicketAgencia(nroTicket,agencia)){
					ctrTicket ctr = ctrTicket.getInstance();					
					if (!ctr.estaAnuladoTicket(nroTicket)){
						if (!ctr.estaUtilizadoTicket(nroTicket)){
							result = ctr.anularTicket(nroTicket);
						}else{
							error = 103;
							mensaje = "El ticket de identificador " + nroTicket + " ya fue utilizado previamente.";
						}					
					}
					else{
						error = 102;
						mensaje = "El ticket de identificador " + nroTicket + " ya fue anulado previamente.";
					}
				}else{
					error = 101;
					mensaje = "No existe ticket con el identificador " + nroTicket + " en la agencia.";					
				}	
				
			}else{
				error = 100;
				mensaje = "La agencia no es correcta";
			}
		}catch(Exception ex){
			error = 201;
			mensaje = "Error de Sistema";
		}
		
		if (error!=0)
			result = new DataAnulacion(error, mensaje,-1);	
		
		return result;
	}
	
	
	
}
