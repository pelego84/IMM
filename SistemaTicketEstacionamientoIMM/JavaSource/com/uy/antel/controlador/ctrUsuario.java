package com.uy.antel.controlador;

public class ctrUsuario {
	private static ctrUsuario instance;
	
	private ctrUsuario(){
		
	}
	
	public static ctrUsuario getInstance(){
		if (instance==null){
			instance = new ctrUsuario();
		}
		return instance;			
	}
	
	public boolean validaCredenciales(String usuario, String password){
		return false;
	}
	
}
