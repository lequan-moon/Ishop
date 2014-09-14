package com.nn.ishop.server.jdbc;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.nn.ishop.license.PropertyUtil;


/**
 * This class use to provide user the database connection 
 * to reduce resource usage, we provide 
 * connection pooling mechanism
 * @author nvnghia
 *
 */
public class PostgreSQLConnectionPool {
		private static Connection conn = null;
		private static Connection backupConn = null;
		private static final Object lock = new Object();
		private static PropertyUtil propUtil = null;
		private static final Logger log 
			= Logger.getLogger(PostgreSQLConnectionPool.class);

	    private static DatabaseMetaData dbmd = null; 
	    
	    /**
	     * Create and return a Connection to calling method
	     * change the connection properties for best suite
	     * with your system
	     * @return Connection to database
	     *
	     * @throws Exception Exception throwing
	     */
	    public static Connection getConnection() throws Exception {
	        try {
	        	if(conn != null) return conn;
	        	if(propUtil == null )
	        	{
	        		propUtil = new PropertyUtil();
	        	}
	        	synchronized(lock){
	        	/** Loading driver class */
	    		Class.forName(
	    				propUtil.getProperty("database.driver.class"));		    	    
	    	    conn = DriverManager.getConnection(
	    	    		propUtil.getProperty("database.url")
	    	    		,propUtil
	    	    		.getProperty("database.username")
	    	    		,propUtil
	    	    		.getProperty("database.password"));
	    	    dbmd = conn.getMetaData(); 
	    	    /** Print database server information */
	    	    log.info(
	    	    		"Connection to "
	    	    		+ dbmd.getDatabaseProductName() 
	    	    		+ " " 
	    	    		+ dbmd.getDatabaseProductVersion() 
	    	    		+ " successful.\n");	
		        }
	            return conn;
	        } catch (SQLException e) {
	        	log.error(e.getMessage());
	            throw new Exception(e.getMessage());
	        }
	    }
	    
	    public static Connection getBackupConnection() throws Exception {
	        try {
	        	if(backupConn != null) return backupConn;
	        	
	        	if(propUtil == null )
	        	{
	        		propUtil = new PropertyUtil();
	        	}
	        	synchronized(lock){
	        	/** Loading driver class */
	    		Class.forName(
	    				propUtil.getProperty("database.driver.class"));		    	    
	    		backupConn = DriverManager.getConnection(
	    	    		propUtil.getProperty("database.backup.url")
	    	    		,propUtil
	    	    		.getProperty("database.backup.username")
	    	    		,propUtil
	    	    		.getProperty("database.backup.password"));
	    	    dbmd = backupConn.getMetaData(); 
	    	    /** Print database server information */
//	    	    log.info(
//	    	    		"Connection to "
//	    	    		+ dbmd.getDatabaseProductName() 
//	    	    		+ " " 
//	    	    		+ dbmd.getDatabaseProductVersion() 
//	    	    		+ " successful.\n");
		        }
	            return backupConn;
	        } catch (SQLException e) {
	        	log.error(e.getMessage());
	            throw new Exception(e.getMessage());
	        }
	    }
	    public static void releaseSqlResources()
	    {
	    	try {
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
	    }
	    /**
	     * Release connection and resultset 
	     *
	     * @param rs a resultset
	     * @param conn a connection
	     */
	    public static void releaseSqlResources(ResultSet rs, Connection conn) {
	        if (rs != null) {
	            try {
	                rs.close();
	            } catch (SQLException e) {
	                ;
	            }
	        }

	        if (conn != null) {
	            try {
	                conn.close();
	            } catch (SQLException e) {
	                ;
	            }
	        }
	    }
	}
