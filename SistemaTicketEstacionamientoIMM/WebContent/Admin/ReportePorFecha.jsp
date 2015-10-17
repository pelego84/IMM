<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="f"  uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h"  uri="http://java.sun.com/jsf/html"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Reporte Por Fecha</title>
<style type="text/css">
	<%@ include file="css/estilos.css" %>
</style>
</head>
<body>
<f:view>
<h:form id="formRepMensual">	
	<h1>Reportes Por Fecha</h1>	
	<table>
		<tr><td><h:commandLink value="Logout" action="#{manejadorlogin.logout}"/></td></tr>
		<tr>
			<td>
				<h:outputLabel>Exportar Reporte</h:outputLabel>
			         <h:selectOneRadio value="#{manejadorReportes.tipoReporte}">
			          <f:selectItem itemValue="PDF" itemLabel="PDF"/>
			          <f:selectItem itemValue="HTML" itemLabel="HTML"/>
			          <f:selectItem itemValue="EXCEL" itemLabel="EXCEL"/>			          
			    </h:selectOneRadio>
	    	</td>
	    	<td>
    			<h:commandButton onclick="this.form.target='_blank'"  action="#{manejadorReportes.exportar('POR_FECHA')}" value="Obtener Reporte" />
    		</td>
	    </tr>
	    <tr>	
	    	<td><h:outputText id="idOutputFechaIni" value="Fecha Desde"/></td>
			<td>
				<h:inputText  id="idInputFechaIni" required="true" immediate="true" requiredMessage="La fecha desde es obligatoria" value="#{manejadorReportes.fechaDesde}">					
					<f:convertDateTime pattern="dd-mm-yyyy" />					
				</h:inputText>				
				<h:message for="idInputFechaIni" errorClass="err"></h:message>
			</td>			
	    </tr>
	    <tr>
	    	<td><h:outputText id="idOutputFechaFin" value="Fecha Hasta"/></td>
			<td>
				<h:inputText  id="idInputFechaFin" required="true" immediate="true" requiredMessage="La fecha hasta es obligatoria"  value="#{manejadorReportes.fechaHasta}">					
					<f:convertDateTime pattern="dd-MM-yyyy" />
				</h:inputText>
				<h:message for="idInputFechaFin" errorClass="err"></h:message>
			</td>    
	    </tr>	    
	    <tr>
	    	<td>
    			<h:commandButton   action="#{manejadorReportes.filtrar}" value="Filtrar" />
    		</td>			
		</tr>	    
	</table>
	
	</br>
	<h:dataTable value="#{manejadorReportes.reportePorFecha}" styleClass="bordered" var="itemReporte">
		<h:column>
	        <f:facet name="header">
	            <h:outputText value="Importe Total" />
	        </f:facet>
	        <h:outputText value="#{itemReporte.importeTotal}" />
	    </h:column>	
	    <h:column>
	        <f:facet name="header">
	            <h:outputText value="Cantidad de Ticket" />
	        </f:facet>
	        <h:outputText value="#{itemReporte.cantTicket}" />
	    </h:column>	   
	</h:dataTable>
	
	
	</h:form>
</f:view>
</body>
</html>