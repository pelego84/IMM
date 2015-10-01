<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="f"  uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h"  uri="http://java.sun.com/jsf/html"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>
<style type="text/css">
	<%@ include file="css/estilos.css" %>
</style>
</head>
<body>
<f:view>
	<h:form id="formLogin">
	
	<h:messages id="idMsg" globalOnly="true" errorClass="err" warnClass="warn" infoClass="info"></h:messages>
	<table align="center">
		<tr>
			<td cospan="1"><h1>LOGIN</h1></td>
		</tr>
		<tr>
			<td><h:outputText id="idOutputUsu" value="Usuario"/></td>
			<td>
				<h:inputText id="idInputUsu" required="true" requiredMessage="Por favor ingrese su Usuario."  value="#{manejadorlogin.usuario}" />					
				<h:message for="idInputUsu" errorClass="err" />
			</td>
		</tr>
		<tr>
			<td><h:outputText id="idOutputClave"  value="Password"/></td>
			<td>
				<h:inputSecret id="idInputClave" required="true" requiredMessage="Por favor ingrese su Password."   value="#{manejadorlogin.password}"></h:inputSecret>
				<h:message for="idInputClave" errorClass="err" />
			</td>			
		</tr>	
		<tr><td></td><td></td></tr>
		<tr>
			<td></td>
			<td align="left">
				<h:commandButton id="idComButtomLogin"  value="Login" action="#{manejadorlogin.procesarLogin}">
					
				</h:commandButton>
			</td>
		</tr>
	</table>
	</h:form>
</f:view>
</body>
</html>