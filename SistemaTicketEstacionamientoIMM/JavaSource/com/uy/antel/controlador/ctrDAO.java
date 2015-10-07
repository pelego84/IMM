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
	
	
	public static int altaTicket(int idAuto,Date fechaIniE, int cantMinutos, Date fechaVenta, int importe){
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
            PreparedStatement ps_insert_auto = conn.prepareStatement("INSERT INTO ticket(nroTicket, fechaVenta, fechaIniE, cantMinutos, ImporteTotal,  fk_auto) values (?,?,?,?,?,?)");            
            ps_insert_auto.setInt(1, idNuevoTicket);
            ps_insert_auto.setTimestamp(2, sqlfechaVenta);
            ps_insert_auto.setTimestamp(3, sqlfechaIniE);
            ps_insert_auto.setInt(4, cantMinutos);
            ps_insert_auto.setInt(5, importe);
            ps_insert_auto.setInt(6, idAuto);
            ps_insert_auto.executeUpdate();
            
            conn.commit();
            conn.setAutoCommit(true);                
            conn.close();           
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return idNuevoTicket;
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
        	PreparedStatement ps_reporte = conn.prepareStatement("SELECT MONTH(t.fechaVenta) as 'Mes',sum(t.ImporteTotal) as 'ImporteMensual', count(*) as 'CantidadDeTicket' FROM imm.ticket t WHERE YEAR(t.fechaVenta)=? GROUP BY MONTH(t.fechaVenta)");
        	ps_reporte.setString(1, anio);
        	ResultSet rs_reporte = ps_reporte.executeQuery();             
            while (rs_reporte.next()) {            	
            	reporteMensual.add(new BReporteVentaMensual(rs_reporte.getInt("ImporteMensual"),rs_reporte.getInt("Mes"),rs_reporte.getInt("CantidadDeTicket")));
            } 
            rs_reporte.close();                           
            conn.close();           
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return reporteMensual;
	}
	
	

}
