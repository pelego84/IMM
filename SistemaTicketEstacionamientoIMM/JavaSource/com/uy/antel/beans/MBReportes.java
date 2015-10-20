package com.uy.antel.beans;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
	
	private String COMPILE_FILE_NAME = "ReporteVentaMensual";
	
	public enum AccionReporte {		 
        MENSUAL,POR_FECHA
    } 
	
	public AccionReporte accionReporte;
	
	   
    public AccionReporte getAccionReporte() {
		return accionReporte;
	}

	public void setAccionReporte(AccionReporte accionReporte) {
		this.accionReporte = accionReporte;
	}

	@Override
    protected String getCompileFileName() {
        return COMPILE_FILE_NAME;
    }
 
    @Override
    protected Map<String, Object> getReportParameters() {        
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();        
        ServletContext context = (ServletContext) externalContext.getContext();
        Map<String, Object> reportParameters = new HashMap<String, Object>();
        reportParameters.put("rimglogo", context.getRealPath("Admin/img/logo_imm.jpg") );       
        if (accionReporte == AccionReporte.MENSUAL){
        	reportParameters.put("rtitle", "Reporte Mensual de Ventas");
        }else if (accionReporte == AccionReporte.POR_FECHA){
            reportParameters.put("rtitle", "Reporte de Ventas");
         }
 
        return reportParameters;
    }
		
	private String anio;
	private List<BReporteVentaMensual> reporteVentasMensual;
	private List<BReportePorFecha> reportePorFecha;
	private Date fechaDesde;
	private Date fechaHasta;	
	

	public MBReportes(){
		
	}
	
	public List<BReportePorFecha> getReportePorFecha() {
		List<BReportePorFecha> result = new ArrayList<BReportePorFecha>();
		if ((fechaDesde!=null && fechaHasta!=null)){			
			result = ctrReportes.getInstance().getReportePorFecha(fechaDesde,fechaHasta);
		}
		return result;
	}

	public void setReportePorFecha(List<BReportePorFecha> reportePorFecha) {
		this.reportePorFecha = reportePorFecha;
	}
	
	public Date getFechaDesde() {
		return fechaDesde;
	}

	public void setFechaDesde(Date fechaDesde) {
		this.fechaDesde = fechaDesde;
	}

	public Date getFechaHasta() {
		return fechaHasta;
	}

	public void setFechaHasta(Date fechaHasta) {
		this.fechaHasta = fechaHasta;
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
	
    	
	 public String exportar(String tipo){		 	 
		 try {			 
			 AccionReporte tipo_enum = AccionReporte.valueOf(tipo);
			 this.accionReporte = tipo_enum;
			 JRBeanCollectionDataSource beanColDataSource = null;
			 if (tipo_enum == AccionReporte.MENSUAL){
				 COMPILE_FILE_NAME = "ReporteVentaMensual";				 
				 List<BReporteVentaMensual> dataList = ctrReportes.getInstance().getReporteVentaMensual("2015");                   
				 beanColDataSource = new JRBeanCollectionDataSource(dataList);			      
			  }else if (tipo_enum == AccionReporte.POR_FECHA){
				  COMPILE_FILE_NAME = "ReportePorFecha";
				  List<BReportePorFecha> dataList = ctrReportes.getInstance().getReportePorFecha(fechaDesde,fechaHasta);                 
				  beanColDataSource = new JRBeanCollectionDataSource(dataList);			      
			 }			 
			 super.prepareReport(beanColDataSource);
	        } catch (Exception e) {
	        	e.printStackTrace();
	        }
		 return null;
	 }
	 
	 public String filtrar(){		 	 
		 try {
			 	getReportePorFecha();
	        } catch (Exception e) {
	        	e.printStackTrace();
	        }
		 return null;
	 }
	
	
}
