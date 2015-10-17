package com.uy.antel.beans;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.sql.*;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 



















import com.uy.antel.controlador.ctrReportes;
import com.uy.antel.util.ReportConfigUtil;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.j2ee.servlets.BaseHttpServlet;
 

public abstract class AbstractReportBean {
		 
	public enum TipoReporte {		 
        PDF, HTML, EXCEL, RTF
    }
    private TipoReporte tipoReporte;
    private final String COMPILE_DIR = "/TemplateReportes/";
    private String message;
 
    public AbstractReportBean() {
        super();
        setTipoReporte(TipoReporte.PDF);
    }
 
    protected void prepareReport(JRBeanCollectionDataSource beanColDataSource) throws JRException, IOException {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
 
        ServletContext context = (ServletContext) externalContext.getContext();
        ReportConfigUtil.compileReport(context, getCompileDir(), getCompileFileName());
 
        File reportFile = new File(ReportConfigUtil.getJasperFilePath(context, getCompileDir(), "jasper_report_template.jasper"));
 
        JasperPrint jasperPrint = ReportConfigUtil.fillReport(reportFile, getReportParameters(), beanColDataSource);
        String nom_archivo = TipoReporte.EXCEL.equals(getTipoReporte()) ? (getCompileFileName()+".xls")  : "getCompileFileName()" + "." + getTipoReporte();
        String path_archivo = context.getRealPath(getCompileDir() + nom_archivo);
        
        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        
        JasperExportManager.exportReportToPdfFile(jasperPrint, path_archivo);
        
        byte[] encoded = null;
        if (TipoReporte.PDF.equals(getTipoReporte())){
        	 httpServletResponse.setContentType("application / pdf");
             encoded = JasperExportManager.exportReportToPdf(jasperPrint);
        }else if (TipoReporte.HTML.equals(getTipoReporte())){ 
        	JasperExportManager.exportReportToHtmlFile(jasperPrint, path_archivo);
        	httpServletResponse.setContentType("text/html");
        	encoded = Files.readAllBytes(Paths.get(path_archivo));
        }else if (TipoReporte.EXCEL.equals(getTipoReporte())){  
        	JRXlsExporter exporter = new JRXlsExporter();
        	ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        	exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
        	exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,byteArrayOutputStream);
        	exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND,Boolean.FALSE);
        	exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,Boolean.TRUE);
        	exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS,Boolean.TRUE);
        	exporter.setParameter(JRXlsExporterParameter.IS_COLLAPSE_ROW_SPAN,Boolean.TRUE);
        	exporter.setParameter(JRXlsExporterParameter.IGNORE_PAGE_MARGINS,Boolean.TRUE);
        	exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,Boolean.FALSE);
        	exporter.exportReport();
        	encoded =byteArrayOutputStream.toByteArray();        	
            httpServletResponse.setContentType("application/vnd.ms-excel");
        }
         
       
        httpServletResponse.addHeader("Content-disposition", "inline; filename=" + nom_archivo);
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();

        servletOutputStream.write(encoded);
        servletOutputStream.flush();
        servletOutputStream.close();
        FacesContext.getCurrentInstance().renderResponse();
        FacesContext.getCurrentInstance().responseComplete();
    }
 
    public TipoReporte getTipoReporte() {
		return tipoReporte;
	}

	public void setTipoReporte(TipoReporte tipoReporte) {
		this.tipoReporte = tipoReporte;
	}
 
    protected Map<String, Object> getReportParameters() {
        return new HashMap<String, Object>();
    }
 
    protected String getCompileDir() {
        return COMPILE_DIR;
    }
 
    protected abstract String getCompileFileName();
 
    public String getMessage() {
        return message;
    }
 
    public void setMessage(String message) {
        this.message = message;
    }

}
