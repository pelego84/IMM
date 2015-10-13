package com.uy.antel.beans;
import java.io.File;
import java.io.IOException;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 









import com.uy.antel.controlador.ctrReportes;
import com.uy.antel.util.ReportConfigUtil;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
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
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
        HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
 
        ReportConfigUtil.compileReport(context, getCompileDir(), getCompileFileName());
 
        File reportFile = new File(ReportConfigUtil.getJasperFilePath(context, getCompileDir(), "jasper_report_template.jasper"));
 
        JasperPrint jasperPrint = ReportConfigUtil.fillReport(reportFile, getReportParameters(), beanColDataSource);
 
        if (getTipoReporte().equals(TipoReporte.HTML)) {
        	String fileHtml = context.getRealPath(getCompileDir() + "ReporteVentasMensual.html");
        	JasperExportManager.exportReportToHtmlFile(jasperPrint, fileHtml);
        	response.setContentType("text/html");
        	
        	byte[] encoded = Files.readAllBytes(Paths.get(fileHtml));
        	String contentHtmlFile = new String(encoded, Charset.defaultCharset());
        	
        	PrintWriter out = response.getWriter();
            out.write(contentHtmlFile);
            out.flush();
            out.close();
            
        } else if (getTipoReporte().equals(TipoReporte.EXCEL)) {
            ReportConfigUtil.exportReportAsExcel(jasperPrint, response.getWriter());
        } else {
            request.getSession().setAttribute(BaseHttpServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE, jasperPrint);
            response.sendRedirect(request.getContextPath() + "/servlets/report/" + getTipoReporte());
        }
 
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
