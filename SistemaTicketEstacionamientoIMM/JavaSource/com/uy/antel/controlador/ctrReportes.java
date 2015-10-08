package com.uy.antel.controlador;


import java.io.File;
import java.net.URL;
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
	private static String sourceDirName = "C://ReportesJasperReport//";
	
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
		String sourceFileName = sourceDirName + "jasper_report_template.jasper";
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
            String sourceFileName = sourceDirName  + "jasper_report_template.jasper";   
        	printFileName = JasperFillManager.fillReportToFile(sourceFileName, parameters, beanColDataSource);
               if (printFileName != null) {
                      /**
                      * 1- export to PDF
                      */
                      JasperExportManager.exportReportToPdfFile(printFileName, sourceDirName + "ReporteVentaMensual.pdf");

                      /**
                      * 2- export to HTML
                      */
                      JasperExportManager.exportReportToHtmlFile(printFileName,sourceDirName + "ReporteVentaMensual.html");
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
			String sourceFileName = sourceDirName + "ReporteVentaMensual.jrxml";   
			System.out.println(sourceFileName);
		     JasperCompileManager.compileReportToFile( sourceFileName );
		  } catch (JRException e) {
		     e.printStackTrace(); 
		  }
		  System.out.println( "Fin compilación ..." );
	}
}
