package com.uy.antel.controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.uy.antel.beans.BReportePorFecha;
import com.uy.antel.beans.BReporteVentaMensual;


public class ctrDAO {
	private static String LOOKUP_DATASOURSE_MySqlIMM = "java:jboss/datasources/MySqlIMM";
	

	public static Connection getConexion(){
		Connection conn = null;
		try {
			InitialContext initContext;
			try {
				initContext = new InitialContext();
				DataSource ds = (DataSource)initContext.lookup(LOOKUP_DATASOURSE_MySqlIMM);
				conn = ds.getConnection();
			} catch (NamingException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}finally{}
		return conn;
		
	}	
		
	
	public static boolean validarAgencia(String agencia){
		boolean agenciaOK=false;
        try {
        	Connection conn = getConexion();
        	//Verifico si existe en el sistema agencia con ese identificador
        	PreparedStatement ps_agencia = conn.prepareStatement("SELECT idAgencia FROM agencia WHERE identificador=?");
        	ps_agencia.setString(1, agencia);
        	ResultSet rs_agencia = ps_agencia.executeQuery();             
            if (rs_agencia.next()) {            	
            	agenciaOK = true;
            } 
            rs_agencia.close();                           
            conn.close();           
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return agenciaOK;
	}
	
	public static boolean existeTicketAgencia(int nroTicket, String agencia){
		boolean existeTicket=false;
        try {
        	Connection conn = getConexion();
        	//Verifico si existe en el sistema el ticket para esa agencia
        	PreparedStatement ps_ticket = conn.prepareStatement("SELECT nroTicket FROM ticket JOIN agencia on fk_agencia=idAgencia AND identificador=? WHERE nroTicket=?");
        	ps_ticket.setString(1, agencia);
        	ps_ticket.setInt(2, nroTicket);
        	ResultSet rs_ticket = ps_ticket.executeQuery();             
            if (rs_ticket.next()) {            	
            	existeTicket = true;
            } 
            rs_ticket.close();                           
            conn.close();           
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return existeTicket;
	}
	
	public static boolean estaAnuladoTicket(int nroTicket){
		boolean anuladoTicket=false;
        try {
        	Connection conn = getConexion();
        	//Verifico si existe anulacion para ese ticket
        	PreparedStatement ps_ticket = conn.prepareStatement("SELECT nroTicket FROM ticket WHERE nroTicket=? AND !isnull(fk_anulacion)");
        	ps_ticket.setInt(1, nroTicket);
        	ResultSet rs_ticket = ps_ticket.executeQuery();             
            if (rs_ticket.next()) {            	
            	anuladoTicket = true;
            } 
            rs_ticket.close();                           
            conn.close();           
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return anuladoTicket;
	}
	
	public static Date getFechaIniEstacionamientoTicket(int nroTicket){
		Date fechaEstacionamiento = new Date();
        try {
        	Connection conn = getConexion();
        	//Obtengo la fecha de inicio del estacionamiento
        	PreparedStatement ps_ticket = conn.prepareStatement("SELECT fechaIniE FROM ticket WHERE nroTicket=?");
        	ps_ticket.setInt(1, nroTicket);
        	ResultSet rs_ticket = ps_ticket.executeQuery();             
            if (rs_ticket.next()) {            	
            	fechaEstacionamiento = rs_ticket.getTimestamp("fechaIniE");
            } 
            rs_ticket.close();                           
            conn.close();           
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return fechaEstacionamiento;
	}
	
	
	
	public static int altaAuto(String matricula){
        int idNuevoAuto=-1;
        try {
        	Connection conn = getConexion();
        	 //Verifico si existe en el sistema un auto con esa matricula
        	 PreparedStatement ps_auto = conn.prepareStatement("SELECT idAuto FROM auto where matricula=?");
        	 ps_auto.setString(1, matricula);
        	 ResultSet rs_auto = ps_auto.executeQuery();             
             if (rs_auto.next()) {            	
            	idNuevoAuto = rs_auto.getInt("idAuto");
             } else {            	 
            	 conn.setAutoCommit(false);
            	 //Obtengo idAuto de la secuencia
                 PreparedStatement ps_secuencia = conn.prepareStatement("SELECT idAuto FROM secuencias");
                 ResultSet rs_secuencia = ps_secuencia.executeQuery();
                 int secuencia;
                 if (rs_secuencia.next()) {
                     secuencia = rs_secuencia.getInt("idAuto");
                     idNuevoAuto = secuencia;
                     secuencia++;
                 } else {
                     throw new SQLException("No se pudo obtener el identificador de secuencia del auto");
                 }
                 
             	//Actualizo secuencia idAuto
                 PreparedStatement ps_update_secuencia = conn.prepareStatement("update secuencias set idAuto=?");
                 ps_update_secuencia.setInt(1, secuencia);
                 ps_update_secuencia.executeUpdate();
                 
                 //Agrego el nuevo auto
                 PreparedStatement ps_insert_auto = conn.prepareStatement("INSERT INTO auto (idAuto,matricula) values (?,?)");
                 ps_insert_auto.setInt(1, idNuevoAuto);
                 ps_insert_auto.setString(2, matricula);
                 ps_insert_auto.executeUpdate();     
                 conn.commit();
                 conn.setAutoCommit(true);
                 rs_secuencia.close();
             }
              
             rs_auto.close();
             conn.close();           
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return idNuevoAuto;
	}
	
	
	public static int altaTicket(int idAuto,Date fechaIniE, int cantMinutos, Date fechaVenta, int importe, String agencia){
		int idNuevoTicket=-1;
        try {
        	Connection conn = getConexion();
        	conn.setAutoCommit(false);
        	
        	//Obtengo idTicket de la secuencia
            PreparedStatement ps_secuencia = conn.prepareStatement("SELECT idTicket FROM secuencias");
            ResultSet rs_secuencia = ps_secuencia.executeQuery();
            int secuencia;
            if (rs_secuencia.next()) {
                secuencia = rs_secuencia.getInt("idTicket");
                idNuevoTicket = secuencia;
                secuencia++;
            } else {
                throw new SQLException("No se pudo obtener el identificador de secuencia del ticket");
            }
            rs_secuencia.close();
            
        	//Actualizo secuencia idTicket
            PreparedStatement ps_update_secuencia = conn.prepareStatement("update secuencias set idTicket=?");
            ps_update_secuencia.setInt(1, secuencia);
            ps_update_secuencia.executeUpdate();
            
            //Agrego el nuevo Ticket
            java.sql.Timestamp sqlfechaIniE = new java.sql.Timestamp(fechaIniE.getTime());
		    java.sql.Timestamp sqlfechaVenta = new java.sql.Timestamp(fechaVenta.getTime());
            PreparedStatement ps_insert_ticket = conn.prepareStatement("INSERT INTO ticket(nroTicket, fechaVenta, fechaIniE, cantMinutos, ImporteTotal,  fk_auto, fk_agencia) values (?,?,?,?,?,?,(SELECT idAgencia FROM agencia WHERE identificador=?))");            
            ps_insert_ticket.setInt(1, idNuevoTicket);
            ps_insert_ticket.setTimestamp(2, sqlfechaVenta);
            ps_insert_ticket.setTimestamp(3, sqlfechaIniE);
            ps_insert_ticket.setInt(4, cantMinutos);
            ps_insert_ticket.setInt(5, importe);
            ps_insert_ticket.setInt(6, idAuto);
            ps_insert_ticket.setString(7, agencia);
            ps_insert_ticket.executeUpdate();
            
            conn.commit();
            conn.setAutoCommit(true);                
            conn.close();           
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return idNuevoTicket;
	}
	
	public static int anularTicket(int nroTicket){
		int codigoAnulacion=-1;
        try {
        	Connection conn = getConexion();
        	conn.setAutoCommit(false);
        	
        	//Obtengo codigoAnulacion de la secuencia
            PreparedStatement ps_secuencia = conn.prepareStatement("SELECT idAnulacion FROM secuencias");
            ResultSet rs_secuencia = ps_secuencia.executeQuery();
            int secuencia;
            if (rs_secuencia.next()) {
                secuencia = rs_secuencia.getInt("idAnulacion");
                codigoAnulacion = secuencia;
                secuencia++;
            } else {
                throw new SQLException("No se pudo obtener el identificador de secuencia de la anulacion");
            }
            rs_secuencia.close();
            
        	//Actualizo secuencia idAnulacion
            PreparedStatement ps_update_secuencia = conn.prepareStatement("update secuencias set idAnulacion=?");
            ps_update_secuencia.setInt(1, secuencia);
            ps_update_secuencia.executeUpdate();
            
            //Anulo el  Ticket            
            PreparedStatement ps_update_ticket = conn.prepareStatement("UPDATE ticket SET fk_anulacion = ? WHERE nroTicket = ?");            
            ps_update_ticket.setInt(1, codigoAnulacion);
            ps_update_ticket.setInt(2, nroTicket); 
            ps_update_ticket.executeUpdate();
            
            conn.commit();
            conn.setAutoCommit(true);                
            conn.close();           
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return codigoAnulacion;
	}
	
	
	public static boolean validaCredenciales(String usuario, String password){
		boolean usuarioOK=false;
        try {
        	Connection conn = getConexion();
        	//Verifico si existe en el sistema un usuario con esas credenciales
        	PreparedStatement ps_usuario = conn.prepareStatement("SELECT id FROM usuario WHERE usuario=? AND password=?");
        	ps_usuario.setString(1, usuario);
        	ps_usuario.setString(2, password);
        	ResultSet rs_usuario = ps_usuario.executeQuery();             
            if (rs_usuario.next()) {            	
            	usuarioOK = true;
            } 
            rs_usuario.close();                           
            conn.close();           
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return usuarioOK;
	}
	
	public static List<BReporteVentaMensual> getReporteVentaMensual(String anio){
		List<BReporteVentaMensual> reporteMensual = new ArrayList<BReporteVentaMensual>();
        try {
        	Connection conn = getConexion();        	
        	PreparedStatement ps_reporte = conn.prepareStatement("SELECT m.mes as 'Mes',coalesce(SUM(t.importeTotal), 0) as 'ImporteMensual', IFNULL(count(t.nroTicket), 0) as 'CantidadDeTicket' FROM imm.mes m LEFT JOIN imm.ticket t on m.idMes = MONTH(t.fechaVenta) AND YEAR(t.fechaVenta)=? AND isnull(t.fk_anulacion) GROUP BY m.mes ORDER BY m.idMes asc");
        	ps_reporte.setString(1, anio);
        	ResultSet rs_reporte = ps_reporte.executeQuery();             
            while (rs_reporte.next()) {            	
            	reporteMensual.add(new BReporteVentaMensual(rs_reporte.getInt("ImporteMensual"),rs_reporte.getString("Mes"),rs_reporte.getInt("CantidadDeTicket")));
            } 
            rs_reporte.close();                           
            conn.close();           
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return reporteMensual;
	}
	
	public static List<BReportePorFecha> getReportePorFecha(Date fechaDesde, Date fechaHasta){
		List<BReportePorFecha> reportePorFecha = new ArrayList<BReportePorFecha>();
        try {
        	Connection conn = getConexion();        	
        	PreparedStatement ps_reporte = conn.prepareStatement("SELECT coalesce(SUM(t.importeTotal), 0) as 'ImporteTotal', IFNULL(count(t.nroTicket), 0) as 'CantidadDeTicket' FROM ticket t WHERE isnull(t.fk_anulacion) AND t.fechaVenta>=? AND t.fechaVenta<=?");
        	java.sql.Date sqlFechaDesde = new java.sql.Date(fechaDesde.getTime());
        	java.sql.Date sqlFechaHasta = new java.sql.Date(fechaHasta.getTime());
        	ps_reporte.setDate(1, sqlFechaDesde);
        	ps_reporte.setDate(2, sqlFechaHasta);
        	ResultSet rs_reporte = ps_reporte.executeQuery();             
            while (rs_reporte.next()) {            	
            	reportePorFecha.add(new BReportePorFecha(rs_reporte.getInt("ImporteTotal"),rs_reporte.getInt("CantidadDeTicket")));
            } 
            rs_reporte.close();                           
            conn.close();           
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return reportePorFecha;
	}
	

}
