<%@page import="com.uy.antel.controlador.ctrReportes"%>
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
	
	<table align="center">
		<tr>
			<td cospan="1"><h1>Reportes Mensules</h1></td>
		</tr>		
	</table>
	<% ctrReportes.getInstance().generarReporteVentaMensual("2015"); %>
	</h:form>
</f:view>
</body>
</html>