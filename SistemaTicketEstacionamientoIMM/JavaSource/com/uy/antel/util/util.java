package com.uy.antel.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class util {

	public static Date stringToDate(String fechaStr) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd_hh:mm");
		Date resultDate = new Date();		
		format.setLenient(false);
	    try {
	    	resultDate = format.parse(fechaStr.trim());
	    } catch (ParseException pe) {
	    	 pe.printStackTrace();
	    }
	    return resultDate;
	}

	public static String dateToString(Date fecha) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd_hh:mm");
		return format.format(fecha);
	}
	
	public static boolean esValidaFecha(String fecha) {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_hh:mm");
	    dateFormat.setLenient(false);
	    try {
	      dateFormat.parse(fecha.trim());
	    } catch (ParseException pe) {
	      return false;
	    }
	    return true;
   }
}
