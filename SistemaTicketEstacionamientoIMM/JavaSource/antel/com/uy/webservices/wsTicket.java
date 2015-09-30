package antel.com.uy.webservices;

import java.util.Date;

import javax.jws.WebMethod;
import javax.jws.WebService;

import com.uy.antel.controlador.ctrWS;
import com.uy.antel.modelo.DataTicket;

@WebService()
public class wsTicket {

	
	@WebMethod()
	public DataTicket altaTicket(String matricula, Date fechaIniE, int cantMinutos, Date fechaVenta, String agencia){
		ctrWS ctr = new ctrWS();		
		return ctr.altaTicket(matricula, fechaIniE, cantMinutos, fechaVenta, agencia);
	}
}
