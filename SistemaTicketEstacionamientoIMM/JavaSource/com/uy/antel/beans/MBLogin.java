package com.uy.antel.beans;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.uy.antel.controlador.ctrUsuario;

public class MBLogin {
	private String usuario;
	private String password;
	
	public MBLogin(){
		
	}
	
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String procesarLogin(){
		String destino = "Error";
		ctrUsuario ctr = ctrUsuario.getInstance();
		if (ctr.validaCredenciales(usuario,password))
			destino = "OK";
		else{
			String errorMessage = "El usuario y password no son correctos.";
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,errorMessage, errorMessage);	
			FacesContext.getCurrentInstance().addMessage(null, message);			
		}
		return destino;
	}
}
