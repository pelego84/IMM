package com.uy.antel.beans;

public class MBLogin {
	private String usuario;
	private String password;
	
	public MBLogin(){
		System.out.println("COnstructor login");
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
		System.out.println(usuario);
		System.out.println(password);
		
		String destino;
		if (false)
			destino = "OK";
		else
			destino = "ERROR";
		return destino;
	}
}
