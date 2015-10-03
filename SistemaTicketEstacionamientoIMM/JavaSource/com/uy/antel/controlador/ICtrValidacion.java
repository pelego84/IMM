package com.uy.antel.controlador;

import java.util.Date;

public interface ICtrValidacion {
	public int validarEntrada(String matricula,String fechaIniE, int cantMinutos,String fechaVenta,String agencia);
}
