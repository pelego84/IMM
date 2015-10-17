package antel.com.uy.webservices;

import javax.jws.WebMethod;
import javax.jws.WebService;

import com.uy.antel.controlador.ctrWS;
import com.uy.antel.modelo.DataAnulacion;
import com.uy.antel.modelo.DataTicket;

@WebService()
public class wsTicket {

	
	@WebMethod()
	public DataTicket altaTicket(String matricula, String fechaIniE, int cantMinutos, String fechaVenta, String agencia){
		ctrWS ctr = ctrWS.getInstance();		
		return ctr.altaTicket(matricula, fechaIniE, cantMinutos, fechaVenta, agencia);
	}
	
	@WebMethod()
	public DataAnulacion anulacionTicket(int nroTicket, String agencia){
		ctrWS ctr = ctrWS.getInstance();		
		return ctr.anulacionTicket(nroTicket, agencia);
	}
}
