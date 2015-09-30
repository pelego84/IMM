package com.uy.antel.controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


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
	
	public static ResultSet getQuery(Connection conn, String query){
		Statement stm = null;
		ResultSet result = null;
		try{
			stm = (Statement)conn.createStatement();
			result = stm.executeQuery(query);			
		}catch(SQLException e){
			e.printStackTrace();
		}		
		return result;
	}
	
	public static void setQuery(Connection conn, String query){
		Statement stm = null;
		try{
			stm = (Statement)conn.createStatement();
			stm.execute(query);			
		}catch(SQLException e){			
			e.printStackTrace();
		}
	}
	
	public static boolean validarAgencia(String agencia){
		Connection con = getConexion();
		boolean result = false;
		try {
			String queryExisteAgencia = "SELECT idAgencia FROM agencia WHERE identificador= '" + agencia + "';";
			ResultSet resExisteAgencia = getQuery(con, queryExisteAgencia);
			if (resExisteAgencia.first())
				result = true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				con.close();
			} catch (SQLException e) {				
				e.printStackTrace();
			}
		}
		return result;
	}
	
	
	public static int altaAuto(String matricula){
        int idNuevoAuto=-1;
        try {
        	Connection conn = getConexion();
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
            rs_secuencia.close();
            
        	//Actualizo secuencia idAuto
            PreparedStatement ps_update_secuencia = conn.prepareStatement("update secuencias set idAuto=?");
            ps_update_secuencia.setInt(1, secuencia);
            ps_update_secuencia.executeUpdate();
            
            //Agrego el nuevo auto
            PreparedStatement ps_insert_auto = conn.prepareStatement("INSERT INTO auto (idAuto,matricula) values (?,?)");
            ps_insert_auto.setInt(1, secuencia);
            ps_insert_auto.setString(2, matricula);
            ps_insert_auto.executeUpdate();
            
            conn.commit();
            conn.setAutoCommit(true);                
            conn.close();           
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return idNuevoAuto;
	}
	
	public static int altaAuto2(String matricula){
		Connection con = getConexion();
		int result = -1;
		try {
			String qryGetIdAuto = "SELECT idAuto FROM secuencias;";
			ResultSet resIdAuto = getQuery(con, qryGetIdAuto);
			int idAuto = -1;
			if (resIdAuto.first()){
				idAuto = resIdAuto.getInt(1);
				String qryAltaAuto = "INSERT INTO auto (idAuto,matricula) values ("+ idAuto +",'" + matricula + "');";
				setQuery(con, qryAltaAuto);
								
				String qryExisteAgencia = "SELECT idAuto FROM auto WHERE matricula= '" + matricula + "';";
				ResultSet resExisteAgencia = getQuery(con, qryExisteAgencia);
				if (resExisteAgencia.first())
					result = resExisteAgencia.getInt(1);
				
				String qryUpdateId = "UPDATE secuencias SET idAuto= " + (++idAuto);
				setQuery(con, qryUpdateId);
				
			}			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				con.close();
			} catch (SQLException e) {				
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public static int altaTicket(int idAuto,Date fechaIniE, int cantMinutos, Date fechaVenta, int importe){
		Connection con = getConexion();
		int result = -1;
		try {
			String qryGetIdTicket = "SELECT idTicket FROM secuencias;";
			ResultSet resIdTicket = getQuery(con, qryGetIdTicket);
			int idTicket = -1;
			if (resIdTicket.first()){
				idTicket = resIdTicket.getInt(1);
			    java.sql.Date sqlfechaIniE = new java.sql.Date(fechaIniE.getTime());
			    java.sql.Date sqlfechaVenta = new java.sql.Date(fechaVenta.getTime());
				String qryAltaTicket = "INSERT INTO ticket(nroTicket, fechaVenta, fechaIniE, cantMinutos, ImporteTotal,  fk_auto) values ("+ idTicket +",'" + sqlfechaVenta.toString() + "'" +",'" + sqlfechaIniE.toString() + "'," + cantMinutos + "," + importe + "," + idAuto  +");";
				System.out.println(qryAltaTicket);
				setQuery(con, qryAltaTicket);
				result = idTicket;
				
				String qryUpdateId = "UPDATE secuencias SET idTicket= " + (++idTicket);
				setQuery(con, qryUpdateId);
				
			}			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				con.close();
			} catch (SQLException e) {				
				e.printStackTrace();
			}
		}
		return result;
	}

}
