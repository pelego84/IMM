package com.uy.antel.controlador;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import com.uy.antel.beans.BReporteVentaMensual;


public class ctrReportes {
	private static ctrReportes instance;
	private static String sourceFileName = "C://Tools//jasperreports-6.1.1//tests//ReporteVentaMensual.jrxml";
	
	private ctrReportes(){
		
	}
	
	public static ctrReportes getInstance(){
		if (instance==null){
			instance = new ctrReportes();
		}
		return instance;			
	}
	
	private List<BReporteVentaMensual> getReporteVentaMensual(String anio){
		List<BReporteVentaMensual> reporteMensual = new ArrayList<BReporteVentaMensual>();
        try {
        	reporteMensual = ctrDAO.getReporteVentaMensual(anio);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return reporteMensual;
	}
	
	public void generarReporteVentaMensual(String anio){
		compilaReporte();
		completarArchivoConBeans(anio);
		generaArchivosReporte(anio);
	}
	
	private void completarArchivoConBeans(String anio){
		sourceFileName = "C://Tools//jasperreports-6.1.1//tests//jasper_report_template.jasper";
		List<BReporteVentaMensual> dataList = getReporteVentaMensual(anio);                   
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(dataList);
        Map parameters = new HashMap();
        try {
               JasperFillManager.fillReportToFile(sourceFileName, parameters, beanColDataSource);
        } catch (JRException e) {
               e.printStackTrace();
        }
	}
	
	private void generaArchivosReporte(String anio){
		String printFileName = null;
		List<BReporteVentaMensual> dataList = getReporteVentaMensual(anio);                   
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(dataList);
        Map parameters = new HashMap();       
        try {
               printFileName = JasperFillManager.fillReportToFile(sourceFileName, parameters, beanColDataSource);
               if (printFileName != null) {
                      /**
                      * 1- export to PDF
                      */
                      JasperExportManager.exportReportToPdfFile(printFileName, "C://Tools//jasperreports-6.1.1//tests//ReporteVentaMensual.pdf");

                      /**
                      * 2- export to HTML
                      */
                      JasperExportManager.exportReportToHtmlFile(printFileName, "C://Tools//jasperreports-6.1.1//tests//ReporteVentaMensual.html");
               }
        } catch (JRException e) {
               e.printStackTrace();
        }
	}
	
	private void compilaReporte(){
		System.out.println(  "Inicio compilación del diseño del reporte ..." );
		try {
		     /**
		  * Compile the report to a file name same as
		  * the JRXML file name
		  */
		     JasperCompileManager.compileReportToFile( sourceFileName );
		  } catch (JRException e) {
		     e.printStackTrace(); 
		  }
		  System.out.println( "Fin compilación ..." );
	}
}
