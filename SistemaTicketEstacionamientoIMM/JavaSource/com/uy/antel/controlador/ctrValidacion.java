package com.uy.antel.controlador;

import java.util.Date;
import java.util.regex.*;

import com.uy.antel.util.util;

public class ctrValidacion implements ICtrValidacion {

	private static ctrValidacion instance;
	
	private ctrValidacion(){
		
	}
	
	public static ctrValidacion getInstance(){
		if (instance==null){
			instance = new ctrValidacion();
		}
		return instance;			
	}
	
	private boolean validarMatricula(String matricula){
		boolean ok = (matricula.length()==7);
		if (ok){ 
			Pattern pIni3Letras = Pattern.compile("[a-zA-Z][a-zA-Z][a-zA-Z][0-9][0-9][0-9]");
			Matcher m = pIni3Letras.matcher(matricula);
		    ok = m.find();	
		}
		return ok;
	}
	
	private boolean validarFechainiE(Date fechaVenta){
		return fechaVenta.after(new Date());
	}
	
	private boolean validarCantidadMinutos(int cantMinutos){
		return (cantMinutos%30==0);
	}
	
	private boolean validarFormatoFecha(String fecha){
		//La fecha debe tener el formato "yyyy-MM-dd_HH:mm"
		return util.esValidaFecha(fecha);
	}
	
	
	@Override
	public int validarEntrada(String matricula, String fechaIniE,	int cantMinutos, String fechaVenta, String agencia) {
		int error = 0;		
		if (!validarMatricula(matricula))
			error = 104;
		else if (!validarFormatoFecha(fechaIniE) || !validarFormatoFecha(fechaVenta))
			error = 105;
		else if (!validarFechainiE(util.stringToDate(fechaIniE)))
			error = 101;
		else if (!validarCantidadMinutos(cantMinutos))
			error = 103;
		
		return error;
	}

}
