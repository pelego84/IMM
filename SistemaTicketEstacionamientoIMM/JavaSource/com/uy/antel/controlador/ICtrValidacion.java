package com.uy.antel.controlador;

import java.util.Date;

public interface ICtrValidacion {
	public int validarEntrada(String matricula,Date fechaIniE, int cantMinutos,Date fechaVenta,String agencia);
}
