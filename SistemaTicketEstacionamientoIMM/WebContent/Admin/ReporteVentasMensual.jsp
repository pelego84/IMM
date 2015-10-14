<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="f"  uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h"  uri="http://java.sun.com/jsf/html"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<style type="text/css">
	<%@ include file="css/estilos.css" %>
</style>
</head>
<body>
<f:view>
<h:form id="formRepMensual">	
	<h1>Reportes Mensules</h1>	
	<table>
		<tr>
			<td>
				<h:outputLabel>Exportar Reporte</h:outputLabel>
			         <h:selectOneRadio value="#{manejadorReportes.tipoReporte}">
			          <f:selectItem itemValue="PDF" itemLabel="PDF"/>
			          <f:selectItem itemValue="HTML" itemLabel="HTML"/>			          
			    </h:selectOneRadio>
	    	</td>
	    	<td>
    			<h:commandButton onclick="this.form.target='_blank'"  action="#{manejadorReportes.exportar}" value="Obtener Reporte" />
    		</td>
	    </tr>
	</table>
	<h:dataTable value="#{manejadorReportes.reporteVentasMensual}" styleClass="bordered" var="reporteMes">
		<h:column>
	        <f:facet name="header">
	            <h:outputText value="Mes" />
	        </f:facet>
	        <h:outputText value="#{reporteMes.mes}" />
	    </h:column>	
	    <h:column>
	        <f:facet name="header">
	            <h:outputText value="Importe Total" />
	        </f:facet>
	        <h:outputText value="#{reporteMes.importeTotal}" />
	    </h:column>	
	    <h:column>
	        <f:facet name="header">
	            <h:outputText value="Cantidad de Ticket" />
	        </f:facet>
	        <h:outputText value="#{reporteMes.cantTicket}" />
	    </h:column>
	   
	</h:dataTable>
	
	
	</h:form>
</f:view>
</body>
</html>