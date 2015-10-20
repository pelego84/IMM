package com.uy.antel.controlador;


import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import com.uy.antel.beans.BReportePorFecha;
import com.uy.antel.beans.BReporteVentaMensual;


public class ctrReportes {
	private static ctrReportes instance;
	private static String sourceDirName = "C://ReportesJasperReport//";
	
	private ctrReportes(){
		
	}
	
	public static ctrReportes getInstance(){
		if (instance==null){
			instance = new ctrReportes();
		}
		return instance;			
	}
	
	public List<BReporteVentaMensual> getReporteVentaMensual(String anio){
		List<BReporteVentaMensual> reporteMensual = new ArrayList<BReporteVentaMensual>();
        try {
        	reporteMensual = ctrDAO.getReporteVentaMensual(anio);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return reporteMensual;
	}
	
	public List<BReportePorFecha> getReportePorFecha(Date fechaDesde, Date fechaHasta){
		List<BReportePorFecha> reportePorFecha = new ArrayList<BReportePorFecha>();
        try {
        	reportePorFecha = ctrDAO.getReportePorFecha(fechaDesde, fechaHasta);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return reportePorFecha;
	}
}
