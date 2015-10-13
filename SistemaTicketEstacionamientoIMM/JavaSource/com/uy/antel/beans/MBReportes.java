package com.uy.antel.beans;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;












import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.j2ee.servlets.BaseHttpServlet;

import com.uy.antel.controlador.ctrReportes;
import com.uy.antel.util.ReportConfigUtil;

public  class MBReportes extends AbstractReportBean{
	
	private final String COMPILE_FILE_NAME = "ReporteVentaMensual";
	   
    @Override
    protected String getCompileFileName() {
        return COMPILE_FILE_NAME;
    }
 
    @Override
    protected Map<String, Object> getReportParameters() {
        Map<String, Object> reportParameters = new HashMap<String, Object>();
 
        reportParameters.put("rtitle", "Hello JasperReports");
 
        return reportParameters;
    }
		
	private String anio;
	private List<BReporteVentaMensual> reporteVentasMensual;

	public MBReportes(){
		
	}

	

	
	
	public String getAnio() {
		return anio;
	}


	public void setAnio(String anio) {
		this.anio = anio;
	}
	
	public List<BReporteVentaMensual> getReporteVentasMensual() {		
		return ctrReportes.getInstance().getReporteVentaMensual("2015");
	}

	public void setReporteVentasMensual(List<BReporteVentaMensual> reporteVentasMensual) {
		this.reporteVentasMensual = reporteVentasMensual;
	}
	
    	
	 public String exportar(){		 	 
		 try {
			  List<BReporteVentaMensual> dataList = ctrReportes.getInstance().getReporteVentaMensual("2015");                   
		      JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(dataList); 
		      super.prepareReport(beanColDataSource);
	        } catch (Exception e) {
	        	e.printStackTrace();
	        }
		 return null;
	 }
	
	
}
