package com.uy.antel.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletContext;

import net.sf.jasperreports.engine.JRAbstractExporter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

public class ReportConfigUtil {
	/**
     * PRIVATE METHODS
     */
    private static void setCompileTempDir(ServletContext context, String uri) {
        System.setProperty("jasper.reports.compile.temp", context.getRealPath(uri));
    }
 
    /**
     * PUBLIC METHODS
     */
    public static boolean compileReport(ServletContext context, String compileDir, String filename) throws JRException {
        String jasperFileName = context.getRealPath(compileDir + filename + ".jasper");
        File jasperFile = new File(jasperFileName);
 
        if (jasperFile.exists()) {
            return true; // jasper file already exists, do not compile again
        }
        try {
            // jasper file has not been constructed yet, so compile the xml file
            setCompileTempDir(context, compileDir); 
            String xmlFileName = jasperFileName.substring(0, jasperFileName.indexOf(".jasper")) + ".jrxml";
            JasperCompileManager.compileReportToFile(xmlFileName);            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
 
    public static JasperPrint fillReport(File reportFile, Map<String, Object> parameters, JRBeanCollectionDataSource beanColDataSource) throws JRException {
        parameters.put("BaseDir", reportFile.getParentFile()); 
        JasperPrint jasperPrint = JasperFillManager.fillReport(reportFile.getPath(), parameters, beanColDataSource); 
        return jasperPrint;
    }
 
    public static String getJasperFilePath(ServletContext context, String compileDir, String jasperFile) {
        return context.getRealPath(compileDir + jasperFile);
    }
 
    private static void exportReport(JRAbstractExporter exporter, JasperPrint jasperPrint, PrintWriter out) throws JRException {
         exporter.exportReport();
    }
 
    public static void exportReportAsHtml(JasperPrint jasperPrint, PrintWriter out) throws JRException {
    	
    }
 
    public static void exportReportAsExcel(JasperPrint jasperPrint, PrintWriter out) throws JRException, FileNotFoundException, IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        OutputStream outputfile = new FileOutputStream(new File("d:/output/JasperReport1.xls"));//make sure to have the directory. excel file will export here
        outputfile.write(output.toByteArray());
    }
}
