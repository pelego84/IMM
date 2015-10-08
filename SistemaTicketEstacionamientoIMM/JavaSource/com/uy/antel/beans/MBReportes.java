package com.uy.antel.beans;

import java.io.IOException;
import java.util.List;







import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.uy.antel.controlador.ctrReportes;

public class MBReportes {
	public enum TipoReporte {		 
        PDF, HTML, EXCEL, RTF
    }	
	private String anio;
	private List<BReporteVentaMensual> reporteVentasMensual;
	private TipoReporte tipoReporte;

	public MBReportes(){
		
	}

	public TipoReporte getTipoReporte() {
		return tipoReporte;
	}

	public void setTipoReporte(TipoReporte tipoReporte) {
		this.tipoReporte = tipoReporte;
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
		 ctrReportes.getInstance().generarReporteVentaMensual("2015");
		 /*ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		 HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
		 HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();		 
		 response.sendRedirect("C://ReportesJasperReport//" + "ReporteVentaMensual" + "."  + getTipoReporte());		
		 */
		 return null;
	 }
	
	
}
